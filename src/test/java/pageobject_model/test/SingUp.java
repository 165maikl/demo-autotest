package pageobject_model.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;

public class SingUp{
    private WebDriver driver;
    private WebDriverWait wait;

    String firstName = "Mihas";
    String lastName = "Ivanov";
    String adress = "Chkalov str. 15";
    String postCode = "220070";
    String city = "Minsk";
    String phone = "+375333341424";
    String pasword = "12345";
    String eMailName, testString;

    @BeforeMethod
    public void start(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver,5);
    }

    @Test
    public void mySingUp() throws InterruptedException {
        //открыть главную страницу магазина
        driver.get("http://localhost/litecart/");
        //ожидание загрузки страницы
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product.column.shadow.hover-light")));
        //перейти по ссылке New customer click here
        driver.findElement(By.xpath("//tr[5]/td/a")).click();

        //читаем текущее время - добавляем его к фамилии и имеем уникальный e-mail и пароль каждый раз
        Calendar calendar = Calendar.getInstance();
        int h = calendar.get(calendar.HOUR_OF_DAY);
        int m = calendar.get(calendar.MINUTE);
        int s = calendar.get(calendar.SECOND);

        eMailName = firstName + Integer.toString(h) + Integer.toString(m) + Integer.toString(s);

        //заполняем обязательные поля
        driver.findElement(By.cssSelector("[name=firstname")).sendKeys(firstName);
        driver.findElement(By.cssSelector("[name=lastname")).sendKeys(lastName);
        driver.findElement(By.cssSelector("[name=address1")).sendKeys(adress);
        driver.findElement(By.cssSelector("[name=postcode")).sendKeys(postCode);
        driver.findElement(By.cssSelector("[name=city")).sendKeys(city);
        driver.findElement(By.cssSelector("[name=email")).sendKeys(eMailName+"@mail.com");
        driver.findElement(By.cssSelector("[name=phone")).sendKeys(phone);
        driver.findElement(By.cssSelector("[name=password")).sendKeys(pasword);
        driver.findElement(By.cssSelector("[name=confirmed_password")).sendKeys(pasword);

        //нажимаем на кнопку Create Account
        driver.findElement(By.name("create_account")).click();
        wait = new WebDriverWait(driver,10);

        //проверяем что мы залогинились - на странице должен быть соответствующий раздел
        //который имеет заголовок Account
        testString = driver.findElement(By.cssSelector("[id=box-account] .title")).getText();

        Assert.assertTrue(testString.compareTo("Account")==0);

        //отлогиниваемся
        driver.findElement(By.cssSelector("[href*=logout]")).click();

        wait = new WebDriverWait(driver,10);

        //проверяем что мы отлогинились - на странице должен быть соответствующий раздел
        //который имеет заголовок Login
        testString = driver.findElement(By.cssSelector("[id=box-account-login] .title")).getText();

        Assert.assertTrue(testString.compareTo("Login")==0);

        //логинимся под созданным пользователем
        driver.findElement(By.name("email")).sendKeys(eMailName+"@mail.com");
        driver.findElement(By.name("password")).sendKeys(eMailName);
        driver.findElement(By.name("login")).click();

        wait = new WebDriverWait(driver,10);

        //проверяем что мы залогинились - на странице должен быть соответствующий раздел
        //который имеет заголовок Account
        testString = driver.findElement(By.cssSelector("[id=box-account] .title")).getText();
        Assert.assertTrue(testString.compareTo("Account")==0);

        //отлогиниваемся
        driver.findElement(By.cssSelector("[href*=logout]")).click();

        //проверяем что мы отлогинились - на странице должен быть соответствующий раздел
        //который имеет заголовок Login
        testString = driver.findElement(By.cssSelector("[id=box-account-login] .title")).getText();
        Assert.assertTrue(testString.compareTo("Login")==0);
    }

    @AfterMethod
    public void stop(){
        driver.quit();
        driver = null;
    }
}
