package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {

    protected static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        String browser = ConfigReader.get("browser");
        logger.info("Starting {} browser", browser);

        DriverManager.initDriver(browser);
        driver = DriverManager.getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.getInt("implicitWait")));
        wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicitWait")));

        driver.get(ConfigReader.get("url"));
        logger.info("Navigated to {}", ConfigReader.get("url"));
    }


    private void captureScreenshot(String testName) {
        try {
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.createDirectories(Paths.get("screenshots"));
            String destination = "screenshots/" + testName + "_" + System.currentTimeMillis() + ".png";
            Files.copy(source.toPath(), Paths.get(destination));
            logger.info("Screenshot saved to {}", destination);
        } catch (IOException e) {
            logger.error("Failed to capture screenshot", e);
        }
    }
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.error("Test failed: {}", result.getName());
            captureScreenshot(result.getName());
        }
        DriverManager.quitDriver();
    }
}