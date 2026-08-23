package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserManagementPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By usersTab = By.xpath("//a[text()='Users']");
    private final By searchUsernameField = By.xpath("//label[text()='Username']/../following-sibling::div//input");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By resultRow = By.cssSelector(".oxd-table-card");
    private final By statusCell = By.xpath("(//div[@role='row'][2]//div[@role='cell'])[5]");

    public UserManagementPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openUsersTab() {
        wait.until(ExpectedConditions.elementToBeClickable(usersTab)).click();
    }

    public void searchByUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameField)).sendKeys(username);
        driver.findElement(searchButton).click();
    }

    public boolean verifyEmployeeCreated() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(resultRow)).isDisplayed();
    }

    public String getUserStatus() {
        return driver.findElement(statusCell).getText();
    }
}