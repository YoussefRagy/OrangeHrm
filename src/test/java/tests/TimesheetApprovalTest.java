package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.EmployeeTimesheetsPage;
import pages.LoginPage;
import pages.TimesheetPage;
import utils.ConfigReader;
import utils.JsonDataReader;

import java.util.Map;

public class TimesheetApprovalTest extends BaseTest {

    @Test(description = "Employee submits a weekly timesheet; a manager reviews and approves it")
    @Story("Time — Timesheet Submission and Approval")
    @Severity(SeverityLevel.NORMAL)
    public void submitAndApproveTimesheet() {
        Map<String, Object> data = JsonDataReader.readSingle("testdata/timesheet.json");

        LoginPage loginPage = new LoginPage(driver, wait);
        DashboardPage dashboardPage = loginPage.loginAsAdmin(
                ConfigReader.get("adminUsername"), ConfigReader.get("adminPassword"));

        logger.info("Submitting timesheet");
        dashboardPage.navigateToModule("Time");
        TimesheetPage timesheetPage = new TimesheetPage(driver, wait);
        timesheetPage.openMyTimesheet();
        timesheetPage.addProjectRow((String) data.get("project"));
        timesheetPage.enterHours((String) data.get("mondayHours"));
        timesheetPage.submit();
        Assert.assertEquals(timesheetPage.getStatus(), "Submitted", "Timesheet was not submitted");

        logger.info("Approving timesheet as manager");
        dashboardPage.navigateToModule("Time");
        EmployeeTimesheetsPage employeeTimesheetsPage = new EmployeeTimesheetsPage(driver, wait);
        employeeTimesheetsPage.openTab();
        employeeTimesheetsPage.filterByEmployee(ConfigReader.get("adminUsername"));
        employeeTimesheetsPage.approve();

        Assert.assertEquals(employeeTimesheetsPage.getStatus(), "Approved", "Timesheet was not approved");
    }
}