package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.ReportsPage;
import utils.ConfigReader;
import utils.JsonDataReader;

import java.util.Map;

public class EmployeeReportTest extends BaseTest {

    @Test(description = "Admin generates and exports the Employee Contact Info report filtered by job title")
    @Story("Reports — Employee Report Generation and Export")
    @Severity(SeverityLevel.MINOR)
    public void generateAndExportEmployeeReport() {
        Map<String, Object> data = JsonDataReader.readSingle("testdata/reportFilters.json");

        LoginPage loginPage = new LoginPage(driver, wait);
        DashboardPage dashboardPage = loginPage.loginAsAdmin(
                ConfigReader.get("adminUsername"), ConfigReader.get("adminPassword"));

        logger.info("Opening PIM Reports");
        dashboardPage.navigateToModule("PIM");
        ReportsPage reportsPage = new ReportsPage(driver, wait);
        reportsPage.openReport("Employee Contact info");
        reportsPage.filterByJobTitle((String) data.get("jobTitle"));
        reportsPage.generate();

        Assert.assertTrue(reportsPage.isReportGenerated(), "Report did not generate");

        logger.info("Exporting the report");
        reportsPage.export();
    }
}
