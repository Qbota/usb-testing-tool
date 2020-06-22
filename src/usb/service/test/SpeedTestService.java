package usb.service.test;

import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import usb.model.TestResult;

public interface SpeedTestService {

    TestResult runTest(String targetPath, ProgressBar progressBar) throws Exception;

}
