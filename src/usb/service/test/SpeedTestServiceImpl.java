package usb.service.test;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import usb.model.TestResult;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SpeedTestServiceImpl implements SpeedTestService{

    private static final String TEMP_DIRECTORY = "/home/jakub/temp/temp";//Paths.get(".").toAbsolutePath().normalize().toString();
    private static final int NUMBER_OF_FILES = 25;
    private static final long MEGA_BYTE = 1024 * 1024;
    private static final long FILE_SIZE = MEGA_BYTE * 10;


    @Override
    public TestResult runTest(String targetPath, ProgressBar progressBar) throws Exception{
        double downloadResult = testDownloadSpeed(targetPath, progressBar);
        double uploadResult = testUploadSpeed(targetPath, progressBar);
        return new TestResult(downloadResult, uploadResult);
    }

    private double testDownloadSpeed(String targetPath, ProgressBar progressBar) throws Exception{
        String targetFile = targetPath + File.separator + "temp";
        createFilesInTargetDirectory(targetFile);
        List<Long> results = measureTimeOfCopyingAllFiles(targetFile, TEMP_DIRECTORY , progressBar);
        cleanFilesAfterTesting(targetFile);
        return calculateMeanSpeedInMBPerSecond(results);
    }

    private double testUploadSpeed(String targetPath, ProgressBar progressBar) throws Exception {
        String targetFile = targetPath + File.separator + "temp";
        createFilesInTargetDirectory(TEMP_DIRECTORY);
        List<Long> results = measureTimeOfCopyingAllFiles(TEMP_DIRECTORY, targetFile ,progressBar);
        cleanFilesAfterTesting(targetFile);
        return calculateMeanSpeedInMBPerSecond(results);
    }

    private double calculateMeanSpeedInMBPerSecond(List<Long> times){
        Long sumOfSizes = NUMBER_OF_FILES * FILE_SIZE / MEGA_BYTE;
        Double sumOfTimes = times.stream().mapToDouble(time -> (double) time / 1000).sum();
        return sumOfSizes / sumOfTimes;
    }

    private List<Long> measureTimeOfCopyingAllFiles(String source, String target, ProgressBar progressBar) throws Exception{
        List<Long> times = new ArrayList<>();
        for(int i =0; i <  NUMBER_OF_FILES; i++ ){
            Date start = new Date();
            copyFile(source+i, target+i);
            Date end = new Date();
            times.add(end.getTime() - start.getTime());
            progressBar.setProgress(progressBar.getProgress() + (double)1 / (NUMBER_OF_FILES * 2));
        }
        return times;
    }

    private void copyFile(String sourcePath, String destinationPath) throws Exception{
        File source = new File(sourcePath);
        File destination = new File(destinationPath);

        FileInputStream is = new FileInputStream(source);
        FileOutputStream os = new FileOutputStream(destination);
        byte[] buffer = new byte[1024];
        int length;

        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }
    }

    private void createFilesInTargetDirectory(String targetFile) throws IOException {
        for(int i = 0; i < NUMBER_OF_FILES; i++){
            RandomAccessFile file = new RandomAccessFile(targetFile+i, "rw");
            file.setLength(FILE_SIZE);
        }
    }

    private void cleanFilesAfterTesting(String targetFile) {
        for(int i = 0; i < NUMBER_OF_FILES; i++){
            File file = new File(targetFile+i);
            file.delete();
            file = new File(TEMP_DIRECTORY+i);
            file.delete();
        }
    }



}
