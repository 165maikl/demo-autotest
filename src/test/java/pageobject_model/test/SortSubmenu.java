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

public class SortSubmenu {
    private WebDriver driver;
    private WebDriverWait wait;
    int numberOfCountries, submenuQuantity;
    List<WebElement> listOfCountries, submenuPoints, submenuPoints1;
    WebElement currentCountry, submenuPoint;
    List<String> textListOfSubmenu, textSortListOfSubmenu;

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
        driver.get("http://localhost/litecart/admin/?app=countries&doc=countries");

        //определение списка стран
        listOfCountries = driver.findElements(By.xpath("//td[5]/a"));
        //сохраняем количество стран
        numberOfCountries = listOfCountries.size();

        //проходим по списку стран
        for (int i = 0; i < numberOfCountries; i++) {
            listOfCountries = driver.findElements(By.xpath("//td[5]/a"));
            //выбираем страну
            currentCountry = listOfCountries.get(i);
            currentCountry.click();
            //ждем загрузки контента
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));

            //определение списка пунктов подменю
            submenuPoints = driver.findElements(By.cssSelector("#table-zones tr"));
            //сохраняем количество пунктов подменю
            submenuQuantity = submenuPoints.size();

            //подменю есть
            if (submenuQuantity > 2) {
                //определение списка пунктов подменю
                submenuPoints1 = driver.findElements(By.xpath("//*[@id='table-zones']/tbody/tr/td[3]"));
                //создаем текстовый список подменю
                textListOfSubmenu = new ArrayList<String>();
                for (WebElement e : submenuPoints1) {
                    textListOfSubmenu.add(e.getText());
                }

                //обрезаем пустую запись в текстовом списке подменю
                textListOfSubmenu.removeAll(Collections.singleton(""));

                //создаем отсортированную копию текстового списка подменю
                textSortListOfSubmenu = new ArrayList<String>(textListOfSubmenu);
                Collections.sort(textSortListOfSubmenu);

                //сравниваем текстовый список подменю с его отсортированной копией
                Assert.assertEquals(textListOfSubmenu, textSortListOfSubmenu);

                //переходим на предыдущую страницу
                driver.navigate().back();

                // подменю нет
            } else {
                // переходим на предыдущую страницу
                driver.navigate().back();
            }
        }
    }

    @AfterMethod
    public void stop() {
        driver.quit();
        driver = null;
    }
}
