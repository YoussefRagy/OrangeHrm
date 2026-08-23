package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EmergencyContactsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By emergencyContactsTab = By.xpath("//a[text()='Emergency Contacts']");
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By nameField = By.xpath("//label[text()='Name']/../following-sibling::div//input");
    private final By relationshipField = By.xpath("//label[text()='Relationship']/../following-sibling::div//input");
    private final By mobileField = By.xpath("(//label[text()='Mobile']/../following-sibling::div//input)[1]");
    private final By saveButton = By.xpath("//button[text()=' Save ']");
    private final By savedContactRow = By.cssSelector(".oxd-table-card");

    public EmergencyContactsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openTab() {
        wait.until(ExpectedConditions.elementToBeClickable(emergencyContactsTab)).click();
    }

    public void addContact(String name, String relationship, String mobile) {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(relationshipField).sendKeys(relationship);
        driver.findElement(mobileField).sendKeys(mobile);
        driver.findElement(saveButton).click();
    }

    public boolean isContactSaved() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(savedContactRow)).isDisplayed();
    }
}