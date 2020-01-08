package pageobject_model.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortCountriesTest {
    private WebDriver driver;
    private WebDriverWait wait;
    int numberOfCountries;
    List<WebElement> listOfCountries;
    List<String> textListOfCountries, textSortListOfCountries;


    @BeforeMethod
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,10);
    }

    @Test
    public void myLeftMenuClick() {
        //открыть страницу
        driver.get("http://localhost/litecart/admin/");
        //подождать пока загрузится страница со значением атрибута "username"
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("username")));
        //найти поля и залогиниться
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
        //переход на страницу выполнения теста
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        //определение списка стран
        listOfCountries = driver.findElements(By.xpath("//td[5]/a"));
        //сохраняем количество стран
        numberOfCountries = listOfCountries.size();

        //создаем текстовый список стран
        textListOfCountries = new ArrayList<String>();
        for(WebElement e : listOfCountries){
            textListOfCountries.add(e.getText());
        }

        //создаем сортированную копию текстового списка стран
        textSortListOfCountries = new ArrayList<String>(textListOfCountries);
        Collections.sort(textSortListOfCountries);

        //сравниваем значения текстового список стран и его отсортированной копии
        Assert.assertEquals(textListOfCountries, textSortListOfCountries);
    }

    @AfterMethod
    public void stop(){
        driver.quit();
        driver = null;
    }
}
