package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddEmployeePage;
import pages.DashboardPage;
import pages.LoginPage;
import pages.UserManagementPage;
import utils.ConfigReader;
import utils.JsonDataReader;

import java.util.Map;

public class EmployeeCreationTest extends BaseTest {

    @Test(description = "HR creates an employee, assigns a role, and the role's access is verified")
    @Story("PIM and Admin — Employee Creation and Role Assignment")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Admin adds a new employee with login details and a user role, then the new user's status is verified in User Management")
    public void createEmployeeAndAssignRole() {
        Map<String, Object> data = JsonDataReader.readSingle("testdata/employeeCreation.json");

        logger.info("Logging in as Admin");
        LoginPage loginPage = new LoginPage(driver, wait);
        DashboardPage dashboardPage = loginPage.loginAsAdmin(
                ConfigReader.get("adminUsername"), ConfigReader.get("adminPassword"));
        Assert.assertTrue(dashboardPage.isLoaded(), "Dashboard did not load after admin login");

        logger.info("Navigating to PIM > Add Employee");
        dashboardPage.navigateToModule("PIM");
        AddEmployeePage addEmployeePage = new AddEmployeePage(driver, wait);
        addEmployeePage.openAddEmployeeForm();
        addEmployeePage.createEmployee(
                (String) data.get("firstName"), (String) data.get("lastName"), (String) data.get("employeeId"));

        logger.info("Enabling login details and assigning credentials");
        addEmployeePage.enableLoginDetails();
        addEmployeePage.enterLoginCredentials((String) data.get("username"), (String) data.get("password"));
        addEmployeePage.selectUserRole((String) data.get("userRole"));
        addEmployeePage.selectStatus((String) data.get("status"));
        addEmployeePage.save();

        Assert.assertTrue(addEmployeePage.isSaveSuccessful(), "Employee record was not saved successfully");

        logger.info("Verifying the new user in Admin > User Management > Users");
        dashboardPage.navigateToModule("Admin");
        UserManagementPage userManagementPage = new UserManagementPage(driver, wait);
        userManagementPage.openUsersTab();
        userManagementPage.searchByUsername((String) data.get("username"));

        Assert.assertTrue(userManagementPage.verifyEmployeeCreated(), "New user was not found in User Management");
        Assert.assertEquals(userManagementPage.getUserStatus(), data.get("status"),
                "User status does not match the assigned status");
    }
}