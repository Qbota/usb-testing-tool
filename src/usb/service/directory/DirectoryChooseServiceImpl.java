package usb.service.directory;

import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.File;

public class DirectoryChooseServiceImpl implements DirectoryChooseService{


    @Override
    public String getDirectoryPath(Window stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file =  directoryChooser.showDialog(stage);
        if(file != null)
            return file.getAbsolutePath();
        return "";
    }
}
