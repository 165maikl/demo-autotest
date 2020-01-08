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

public class SortGeoZoneTest {
    private WebDriver driver;
    private WebDriverWait wait;
    int numberOfCountries;
    List<WebElement> listOfCountries, listOfGeoZone;
    WebElement currentCountry;
    List<String> textListOfGeoZones, textSortListOfGeoZones;


    @BeforeMethod
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
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
        driver.get("http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones");


        //определение списка стран
        listOfCountries = driver.findElements(By.cssSelector(".row"));
        //сохраняем количество стран
        numberOfCountries = listOfCountries.size();


        //проходим по списку стран
        for (int i = 0; i < numberOfCountries; i++) {
            listOfCountries = driver.findElements(By.cssSelector(".row"));
            //выбираем страну
            currentCountry = listOfCountries.get(i);
            currentCountry.findElement(By.cssSelector("a")).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));

            //определение списка геозон
            listOfGeoZone = driver.findElements(By.cssSelector("[id=table-zones] tr td:nth-child(3) [selected=selected]"));

            //создаем текстовый список геозон
            textListOfGeoZones = new ArrayList<String>();
            for (WebElement e : listOfGeoZone) {
                textListOfGeoZones.add(e.getText());
            }

            //создаем сортированную копию текстового списка геозон
            textSortListOfGeoZones = new ArrayList<String>(textListOfGeoZones);
            Collections.sort(textSortListOfGeoZones);

//                //проверяем вывод в консоль текстового списка геозон
//                for (int l = 0; l < 5; l++) {
//                    System.out.println(textListOfGeoZones.get(l));
//                }

//                //проверяем обратный порядок сортировки
//                Collections.reverse(textListOfGeoZones);

//                //проверяем вывод в консоль отсортированного текстового списка геозон
//                for (int m = 0; m < 5; m++) {
//                    System.out.println(textSortListOfGeoZones.get(m));
//                }

            //сравниваем список и отсортированный текстовый список геозон
            Assert.assertEquals(textListOfGeoZones, textSortListOfGeoZones);

            //переходим на предыдущую страницу
            driver.navigate().back();
        }
    }


    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}
