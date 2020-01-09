package pageobject_model.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SeleniumSearchResultsPage {
    private WebDriver driver;
    private String searchTerm;

    @FindBy (id = ".gsc-webResult.gsc-result")
    private List<WebElement> generalSearchResults;

    public SeleniumSearchResultsPage(WebDriver driver, String searchTerm){
        this.searchTerm = searchTerm;
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public int countResultNumberWithSearchTerm(){
        List<WebElement> resultsNumberWithSearchTerm = driver.findElements(By.linkText("Selenium Projects"));
        System.out.println("Search results number for requested term:" + resultsNumberWithSearchTerm.size());
        return resultsNumberWithSearchTerm.size();
    }
}

