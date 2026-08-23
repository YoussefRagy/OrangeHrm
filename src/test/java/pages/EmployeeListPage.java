package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmployeeListPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By employeeListTab = By.xpath("//a[text()='Employee List']");
    private final By employeeNameSearch = By.xpath("(//label[text()='Employee Name']/../following-sibling::div//input)[1]");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By firstResultLink = By.cssSelector(".oxd-table-card .oxd-table-cell a");

    public EmployeeListPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openEmployeeListTab() {
        wait.until(ExpectedConditions.elementToBeClickable(employeeListTab)).click();
    }

    public void searchByName(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameSearch)).sendKeys(name);
        driver.findElement(searchButton).click();
    }

    public void openFirstResult() {
        wait.until(ExpectedConditions.elementToBeClickable(firstResultLink)).click();
    }
}