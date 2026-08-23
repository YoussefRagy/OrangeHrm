package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PersonalDetailsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By mobileField = By.xpath("//label[text()='Mobile']/../following-sibling::div//input");
    private final By maritalStatusDropdown = By.xpath("//label[text()='Marital Status']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By nationalityDropdown = By.xpath("//label[text()='Nationality']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By saveButton = By.xpath("//button[text()=' Save ']");
    private final By successToast = By.cssSelector(".oxd-toast-content--success");

    public PersonalDetailsPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void updateMobile(String mobile) {
        var field = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileField));
        field.clear();
        field.sendKeys(mobile);
    }

    public void selectMaritalStatus(String status) {
        driver.findElement(maritalStatusDropdown).click();
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", status));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void selectNationality(String nationality) {
        driver.findElement(nationalityDropdown).click();
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", nationality));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void save() {
        driver.findElement(saveButton).click();
    }

    public boolean isSaveSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).isDisplayed();
    }

    public String getMobile() {
        return driver.findElement(mobileField).getAttribute("value");
    }
}
