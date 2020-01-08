package pageobject_model.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class LeftMenuClick {
    private WebDriver driver;
    private WebDriverWait wait;
    int menuQuantity, submenuQuantity;
    List<WebElement> menuPoints, submenuPoints;
    WebElement menuPoint, submenuPoint;

    @BeforeMethod
    public void start(){
        driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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

        //определение списка пунктов меню
        menuPoints = driver.findElements(By.id("app-"));
        //сохраняем количество пунктов меню
        menuQuantity = menuPoints.size();

        //проходим по пунктам меню
        for (int i = 0; i < menuQuantity; i++) {
            menuPoints = driver.findElements(By.id("app-"));
            //выбираем пункт меню
            menuPoint= menuPoints.get(i);
            //кликаем по меню
            menuPoint.click();

            //обновляем список
            menuPoints = driver.findElements(By.id("app-"));
            //выбираем пункт меню
            menuPoint = menuPoints.get(i);
            //определение списка пунктов подменю
            submenuPoints = menuPoint.findElements(By.cssSelector("[id^=doc-]"));
            //сохраняем количество пунктов подменю
            submenuQuantity = submenuPoints.size();

            //подменю есть
            if(submenuQuantity > 0) {
                for (int j = 0; j < submenuQuantity; j++) {
                    //обновляем список
                    menuPoints = driver.findElements(By.id("app-"));
                    //выбираем пункт меню
                    menuPoint = menuPoints.get(i);
                    //определение списка пунктов подменю
                    submenuPoints = menuPoint.findElements(By.cssSelector("[id^=doc-]"));
                    //выбираем пункт подменю
                    submenuPoint = submenuPoints.get(j);
                    //кликаем по подменю
                    submenuPoint.click();
                    //проверка наличия заголовка
                    driver.findElement(By.cssSelector("h1"));
                }

            //подменю нет
            } else {
                //проверка наличия заголовка
                driver.findElement(By.cssSelector("h1"));
            }
        }
    }

    @AfterMethod
    public void stop(){
        driver.quit();
        driver = null;
    }
}
