package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--start-maximized");
                options.addArguments("--remote-allow-origins=*");
                driver.set(new ChromeDriver(options));
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                driver.set(new FirefoxDriver(options));
                driver.get().manage().window().maximize();
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                options.addArguments("--start-maximized");
                driver.set(new EdgeDriver(options));
            }
            default -> throw new RuntimeException("Unsupported browser: " + browser
                    + ". Supported values: chrome, firefox, edge");
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}