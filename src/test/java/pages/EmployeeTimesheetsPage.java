package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeTimesheetsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By employeeTimesheetsTab = By.xpath("//a[text()='Employee Timesheets']");
    private final By employeeNameField = By.xpath("(//label[text()='Employee Name']/../following-sibling::div//input)[1]");
    private final By viewButton = By.xpath("//button[normalize-space()='View']");
    private final By approveButton = By.xpath("//button[normalize-space()='Approve']");
    private final By statusBadge = By.cssSelector(".orangehrm-status");

    public EmployeeTimesheetsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openTab() {
        wait.until(ExpectedConditions.elementToBeClickable(employeeTimesheetsTab)).click();
    }

    public void filterByEmployee(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameField)).sendKeys(name);
        driver.findElement(viewButton).click();
    }

    public void approve() {
        wait.until(ExpectedConditions.elementToBeClickable(approveButton)).click();
    }

    public String getStatus() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(statusBadge)).getText();
    }
}
