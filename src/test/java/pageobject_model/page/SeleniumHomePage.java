package pageobject_model.page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumHomePage {

    private static final String HOMEPAGE_URL = "https://selenium.dev";
    private WebDriver driver;

    @FindBy (id = "gsc-i-id1")
    private WebElement searchInput;

    public SeleniumHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SeleniumHomePage openPage(){
        driver.get(HOMEPAGE_URL);
        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("gsc-i-id1")));
        return this;
    }

    public SeleniumSearchResultsPage searchForTerms (String term){
        searchInput.sendKeys(term + Keys.ENTER);
        return new SeleniumSearchResultsPage(driver, term);
    }
}
