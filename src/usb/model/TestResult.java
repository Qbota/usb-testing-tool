package usb.model;

public class TestResult {

    private Double downloadResult;
    private Double uploadResult;

    public Double getDownloadResult() {
        return downloadResult;
    }

    public void setDownloadResult(Double downloadResult) {
        this.downloadResult = downloadResult;
    }

    public Double getUploadResult() {
        return uploadResult;
    }

    public void setUploadResult(Double uploadResult) {
        this.uploadResult = uploadResult;
    }

    public TestResult(Double downloadResult, Double uploadResult) {
        this.downloadResult = downloadResult;
        this.uploadResult = uploadResult;
    }
}
