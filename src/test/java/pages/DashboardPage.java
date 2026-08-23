package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By userDropdown = By.cssSelector(".oxd-userdropdown-tab");
    private final By logoutLink = By.linkText("Logout");

    public DashboardPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public boolean isLoaded() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userDropdown)).isDisplayed();
    }

    public void navigateToModule(String moduleName) {
        By locator = By.xpath(String.format(
                "//a[contains(@class,'oxd-main-menu-item')]//span[contains(@class,'oxd-main-menu-item--name') and normalize-space()='%s']",
                moduleName));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void logout() {
        driver.findElement(userDropdown).click();
        driver.findElement(logoutLink).click();
    }
}