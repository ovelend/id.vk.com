package core.vk.utils;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LogListener implements ITestListener {

    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private ByteArrayOutputStream response = new ByteArrayOutputStream();

    private PrintStream requestVar = new PrintStream(request, true);
    private PrintStream responseVar = new PrintStream(response, true);

    public void onTestStart(ITestResult result) {
        // not implemented
    }

    public void onTestSuccess(ITestResult result) {
        // not implemented
    }

    public void onTestFailure(ITestResult result) {
        // not implemented
    }

    public void onTestSkipped(ITestResult result) {
        // not implemented
    }
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // not implemented
    }

    public void onTestFailedWithTimeout(ITestResult result) {
        onTestFailure(result);
    }

    public void onStart(ITestContext context) {
        // not implemented
    }

    public void onFinish(ITestContext context) {
        // not implemented
    }

}
