package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.DependentsPage;
import pages.EmergencyContactsPage;
import pages.EmployeeListPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.JsonDataReader;

import java.util.Map;

public class EmergencyContactsTest extends BaseTest {

    @Test(description = "HR adds an emergency contact and a dependent to an employee record")
    @Story("PIM — Emergency Contacts and Dependent Management")
    @Severity(SeverityLevel.NORMAL)
    public void addEmergencyContactAndDependent() {
        Map<String, Object> data = JsonDataReader.readSingle("testdata/emergencyContacts.json");

        LoginPage loginPage = new LoginPage(driver, wait);
        DashboardPage dashboardPage = loginPage.loginAsAdmin(
                ConfigReader.get("adminUsername"), ConfigReader.get("adminPassword"));
        dashboardPage.navigateToModule("PIM");

        EmployeeListPage employeeListPage = new EmployeeListPage(driver, wait);
        employeeListPage.openEmployeeListTab();
        employeeListPage.searchByName((String) data.get("employeeFullName"));
        employeeListPage.openFirstResult();

        logger.info("Adding emergency contact");
        EmergencyContactsPage emergencyContactsPage = new EmergencyContactsPage(driver, wait);
        emergencyContactsPage.openTab();
        emergencyContactsPage.addContact(
                (String) data.get("contactName"), (String) data.get("relationship"), (String) data.get("mobile"));
        Assert.assertTrue(emergencyContactsPage.isContactSaved(), "Emergency contact was not saved");

        logger.info("Adding dependent");
        DependentsPage dependentsPage = new DependentsPage(driver, wait);
        dependentsPage.openTab();
        dependentsPage.addDependent(
                (String) data.get("dependentName"), (String) data.get("dependentRelationship"), (String) data.get("dependentDob"));
        Assert.assertTrue(dependentsPage.isDependentSaved(), "Dependent was not saved");
    }
}