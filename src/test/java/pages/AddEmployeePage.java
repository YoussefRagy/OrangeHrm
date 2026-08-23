package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddEmployeePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By addEmployeeTab = By.xpath("//a[text()='Add Employee']");
    private final By firstNameField = By.name("firstName");
    private final By lastNameField = By.name("lastName");
    private final By employeeIdField = By.cssSelector(".oxd-grid-item.oxd-grid-item--gutters:nth-child(3) input");
    private final By createLoginToggle = By.cssSelector(".oxd-switch-input");
    private final By usernameField = By.xpath("(//label[text()='Username']/../following-sibling::div//input)[1]");
    private final By passwordField = By.xpath("//label[text()='Password']/../following-sibling::div//input");
    private final By confirmPasswordField = By.xpath("//label[text()='Confirm Password']/../following-sibling::div//input");
    private final By userRoleDropdown = By.xpath("//label[text()='User Role']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By statusDropdown = By.xpath("//label[text()='Status']/../following-sibling::div//div[@class='oxd-select-text-input']");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By successToast = By.cssSelector(".oxd-toast-content--success");

    public AddEmployeePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void openAddEmployeeForm() {
        wait.until(ExpectedConditions.elementToBeClickable(addEmployeeTab)).click();
    }

    public void createEmployee(String firstName, String lastName, String employeeId) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(employeeIdField).clear();
        driver.findElement(employeeIdField).sendKeys(employeeId);
    }

    public void enableLoginDetails() {
        driver.findElement(createLoginToggle).click();
    }

    public void enterLoginCredentials(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(confirmPasswordField).sendKeys(password);
    }

    public void selectUserRole(String role) {
        driver.findElement(userRoleDropdown).click();
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", role));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void selectStatus(String status) {
        driver.findElement(statusDropdown).click();
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", status));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void save() {
        driver.findElement(saveButton).click();
    }

    public boolean isSaveSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).isDisplayed();
    }
}
