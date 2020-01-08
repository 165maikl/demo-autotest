package pageobject_model.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class CheckProductTest {
    private WebDriver driver;
    private WebDriverWait wait;

    String firstName = "Mihas";
    String prefix;
    String prodName;
    String validFrom;
    String validTo;

    @BeforeMethod
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
    }

    @Test
    public void myCheckNewProduct() throws InterruptedException {

        //открыть страницу
        driver.get("http://localhost/litecart/admin/");
        //найти поле для ввода логина и ввести "admin"
        driver.findElement(By.name("username")).sendKeys("admin");
        //найти поле для ввода пароля и ввести "admin"
        driver.findElement(By.name("password")).sendKeys("admin");
        //найти кнопку логина и нажать на нее
        driver.findElement(By.name("login")).click();
        //подождать пока не загрузится страница с заголовком "My Store"
        wait.until(titleIs("My Store"));


        //читаем текущее время - добавляем его к фамилии и имеем уникальный e-mail и пароль каждый раз
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(calendar.HOUR_OF_DAY);
        int m = calendar.get(calendar.MINUTE);
        int s = calendar.get(calendar.SECOND);

        prefix = Integer.toString(h) + Integer.toString(m) + Integer.toString(s);
        prodName = firstName + " " + prefix;


        int y = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int d = calendar.get(calendar.DAY_OF_MONTH);

        validFrom = Integer.toString(y);
        validTo = Integer.toString(y+2);

        //делаем корректировку даты (приводим к формату дд.мм.гггг)
        if(month < 10) {
            validFrom = Integer.toString(month) + "-0" + validFrom;
            validTo = Integer.toString(month) + "-0" + validTo;
        } else {
            validFrom = Integer.toString(month) + "-" + validFrom;
            validTo = Integer.toString(month) + "-" + validTo;
        }

        if(d < 10) {
            validFrom = "-0" + Integer.toString(d) + validFrom;
            validTo = "-0" + Integer.toString(d) + validTo;
        } else {
            validFrom = Integer.toString(d) + "-" + validFrom;
            validTo = Integer.toString(d) + "-" + validTo;
        }

        //открываем каталог
        driver.findElement(By.cssSelector("[href*=catalog]")).click();
//        driver.findElement(By.linkText("Catalog")).click();

        //открываем форму регистрации нового продукта
        driver.findElement(By.linkText("Add New Product")).click();
        wait = new WebDriverWait(driver,10);
        //устанавливаем статус Enabled
        driver.findElement(By.name("status")).click();
        //вводим название товара
        driver.findElement(By.name("name[en]")).clear();
        driver.findElement(By.name("name[en]")).sendKeys(prodName);
        //вводим код товара + устанавливаем категорию Rubber Ducks
        driver.findElement(By.name("code")).sendKeys(prefix + Keys.TAB + Keys.TAB + Keys.SPACE);
        //устанавливаем категорию Rubber Ducks
//        driver.findElement(By.cssSelector("[data-name*=Rubber]")).click();
        //устанавливаем группу Unisex
        driver.findElement(By.xpath("(//input[@name='product_groups[]'])[3]")).click();
        //устанавливаем количество 1
        driver.findElement(By.name("quantity")).sendKeys("1");
        //устанавливаем дату начала годности
        driver.findElement(By.name("date_valid_from")).sendKeys(validFrom);
        //устанавливаем дату конца годности
        driver.findElement(By.name("date_valid_to")).sendKeys(validTo);

        //переходим на вкладку Information
        driver.findElement(By.linkText("Information")).click();
        wait = new WebDriverWait(driver,10);
        //выбираем корпорацию
        new Select(driver.findElement(By.name("manufacturer_id"))).selectByVisibleText("ACME Corp.");
        //вводим ключевое слово
        driver.findElement(By.name("keywords")).sendKeys("Duck");
        //задаем краткое описание
        driver.findElement(By.name("short_description[en]")).sendKeys("Duck");
        //задаем описание
        driver.findElement(By.name("description[en]")).sendKeys(prodName + " is cool!");
        // задаем заголовок
        driver.findElement(By.name("head_title[en]")).sendKeys(prodName);
        // задаем метаописание
        driver.findElement(By.name("meta_description[en]")).sendKeys("666666666");

        // переходим на вкладку Data
        driver.findElement(By.linkText("Data")).click();
        wait = new WebDriverWait(driver,10);
        // заполняем поле SKU
        driver.findElement(By.name("sku")).sendKeys(prefix);
        // заполняем поле GTIN
        driver.findElement(By.name("gtin")).sendKeys(prefix);
        // заполняем поле TARIC
        driver.findElement(By.name("taric")).sendKeys(prefix);
        // задаем вес
        driver.findElement(By.name("weight")).sendKeys("1");
        // задаем размеры
        driver.findElement(By.name("dim_x")).sendKeys("10");
        driver.findElement(By.name("dim_y")).sendKeys("10");
        driver.findElement(By.name("dim_z")).sendKeys("10");
        // задаем атрибуты
        driver.findElement(By.name("attributes[en]")).sendKeys("None");

        // переходим на вкладку Prices
        driver.findElement(By.linkText("Prices")).click();
        wait = new WebDriverWait(driver,10);
        // задаем цену
        driver.findElement(By.name("purchase_price")).sendKeys("13");
        // выбираем валюту
        new Select(driver.findElement(By.name("purchase_price_currency_code"))).selectByVisibleText("Euros");
        // задаем цену в долларах
        driver.findElement(By.name("gross_prices[USD]")).sendKeys("20");

        // сохраняем продукт
        driver.findElement(By.name("save")).click();
        wait = new WebDriverWait(driver,10);

        // Проверяем наличие такого элемента на странице
        driver.findElement(By.linkText(prodName));
    }

    @AfterMethod
    public void stop(){
        driver.quit();
        driver = null;
    }
}