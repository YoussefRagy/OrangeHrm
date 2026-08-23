package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TimesheetPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By myTimesheetTab = By.xpath("//a[text()='My Timesheets']");
    private final By addRowButton = By.xpath("//button[normalize-space()='+ Add Row']");
    private final By projectDropdown = By.cssSelector(".oxd-table-row .oxd-select-text-input");
    private final By mondayHourInput = By.cssSelector(".oxd-table-row input[type='text']:nth-of-type(1)");
    private final By submitButton = By.xpath("//button[normalize-space()='Submit']");
    private final By statusBadge = By.cssSelector(".orangehrm-status");

    public TimesheetPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openMyTimesheet() {
        wait.until(ExpectedConditions.elementToBeClickable(myTimesheetTab)).click();
    }

    public void addProjectRow(String project) {
        wait.until(ExpectedConditions.elementToBeClickable(addRowButton)).click();
        driver.findElement(projectDropdown).click();
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", project));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void enterHours(String hours) {
        driver.findElement(mondayHourInput).sendKeys(hours);
    }

    public void submit() {
        driver.findElement(submitButton).click();
    }

    public String getStatus() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(statusBadge)).getText();
    }
}