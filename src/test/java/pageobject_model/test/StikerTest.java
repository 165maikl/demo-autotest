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

import java.util.List;

public class StikerTest {
    private WebDriver driver;
    private WebDriverWait wait;

    int prodQuantity, stickerQuantity;
    WebElement productUnit;
    List<WebElement> prodList, stickerList;

    @BeforeMethod
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,5);
    }

    @Test
    public void myCheckStiker(){
        //открыть главную страницу магазина
        driver.get("http://localhost/litecart/");
        //ожидание загрузки страницы
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product.column.shadow.hover-light")));
        //определение списка товаров на главной странице
        prodList = driver.findElements(By.cssSelector(".product.column.shadow.hover-light"));
        //сохраняем количество товаров
        prodQuantity = prodList.size();

        //проходим по списку товаров
        for (int i = 0; i < prodQuantity; i++) {
            prodList = driver.findElements(By.cssSelector("li.product"));
            productUnit = prodList.get(i);
            //определение списка стикеров (полосок) у товара
            stickerList = productUnit.findElements(By.cssSelector(".sticker"));
            //определение количества стикеров у товара
            stickerQuantity = stickerList.size();
            //проверка на наличие у товара одного стикера
            Assert.assertTrue(stickerQuantity == 1);
        }
    }

    @AfterMethod
    public void stop(){
        driver.quit();
        driver = null;
    }
}
