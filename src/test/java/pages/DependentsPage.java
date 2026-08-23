package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DependentsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By dependentsTab = By.xpath("//a[text()='Dependents']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By nameField = By.xpath("//label[text()='Name']/../following-sibling::div//input");
    private final By relationshipDropdown = By.xpath("//label[text()='Relationship']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By dobField = By.xpath("//label[text()='Date of Birth']/../following-sibling::div//input");
    private final By saveButton = By.xpath("//button[text()=' Save ']");
    private final By savedDependentRow = By.cssSelector(".oxd-table-card");

    public DependentsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openTab() {
        wait.until(ExpectedConditions.elementToBeClickable(dependentsTab)).click();
    }

    public void addDependent(String name, String relationship, String dob) {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(relationshipDropdown).click();
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", relationship));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        driver.findElement(dobField).sendKeys(dob);
        driver.findElement(saveButton).click();
    }

    public boolean isDependentSaved() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(savedDependentRow)).isDisplayed();
    }
}
