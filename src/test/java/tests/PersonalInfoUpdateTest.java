package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import pages.PersonalDetailsPage;
import utils.ConfigReader;
import utils.JsonDataReader;

import java.util.Map;

public class PersonalInfoUpdateTest extends BaseTest {

    @Test(description = "Employee updates personal details and Admin verifies the change was saved")
    @Story("PIM — Personal Information Update and Verification")
    @Severity(SeverityLevel.NORMAL)
    @Description("Updates mobile/marital status/nationality, then re-opens the record to confirm persistence")
    public void updateAndVerifyPersonalInformation() {
        Map<String, Object> data = JsonDataReader.readSingle("testdata/personalInfoUpdate.json");

        logger.info("Logging in and opening PIM > Employee List");
        LoginPage loginPage = new LoginPage(driver, wait);
        DashboardPage dashboardPage = loginPage.loginAsAdmin(
                ConfigReader.get("adminUsername"), ConfigReader.get("adminPassword"));
        dashboardPage.navigateToModule("PIM");

        EmployeeListPage employeeListPage = new EmployeeListPage(driver, wait);
        employeeListPage.openEmployeeListTab();
        employeeListPage.searchByName((String) data.get("employeeFullName"));
        employeeListPage.openFirstResult();

        logger.info("Updating personal details");
        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(driver, wait);
        personalDetailsPage.updateMobile((String) data.get("mobile"));
        personalDetailsPage.selectMaritalStatus((String) data.get("maritalStatus"));
        personalDetailsPage.selectNationality((String) data.get("nationality"));
        personalDetailsPage.save();

        Assert.assertTrue(personalDetailsPage.isSaveSuccessful(), "Personal details were not saved");

        logger.info("Reloading the record to verify persistence");
        driver.navigate().refresh();
        Assert.assertEquals(personalDetailsPage.getMobile(), data.get("mobile"),
                "Mobile number was not persisted after save");
    }
}
