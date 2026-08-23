package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ReportsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By reportSearchField = By.cssSelector("input[placeholder='Type for hints...']");
    private final By reportSearchResult = By.xpath("//div[@role='option']//span[text()='Employee Contact info']");
    private final By jobTitleDropdown = By.xpath("//label[text()='Job Title']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By generateButton = By.xpath("//button[normalize-space()='Generate']");
    private final By exportButton = By.xpath("//button[normalize-space()='Export']");
    private final By reportTable = By.cssSelector(".oxd-table-body");

    public ReportsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openReport(String reportName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(reportSearchField)).sendKeys(reportName);
        wait.until(ExpectedConditions.elementToBeClickable(reportSearchResult)).click();
    }

    public void filterByJobTitle(String jobTitle) {
        driver.findElement(jobTitleDropdown).click();
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", jobTitle));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void generate() {
        driver.findElement(generateButton).click();
    }

    public boolean isReportGenerated() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(reportTable)).isDisplayed();
    }

    public void export() {
        wait.until(ExpectedConditions.elementToBeClickable(exportButton)).click();
    }
}
