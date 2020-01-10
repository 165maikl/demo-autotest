package pageobject_model.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageobject_model.page.SeleniumHomePage;

public class AddSeleniumDev {
    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup(){

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test (description = "Just first test, JIRA binding can be here")
    public void commonSearchTermResultsAreNotEmpty() {

        int expectedSearchResultsNumber = new SeleniumHomePage(driver)
                .openPage()
                .searchForTerms ("selenium java")
                .countResultNumberWithSearchTerm();


        Assert.assertTrue(expectedSearchResultsNumber >0,"Search results are empty!");
    }

     @AfterMethod (alwaysRun = true)
     public void browserTearDown (){
         driver.quit();
         driver=null;
     }
}