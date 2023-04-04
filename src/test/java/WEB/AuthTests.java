package WEB;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class AuthTests {
    static FirefoxDriver driver;

    private  static Long time = 5l;

    @BeforeEach
    void init() throws IOException {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--incognito");
        options.addArguments("start-maximized");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
        driver.get("https://test-stand.gb.ru/login");
    }

    @AfterEach
    void close() {
        driver.quit();
    }

    // Валидный логин и пароль
    @Test
    void ValidLoginPassword() {
        WebElement Username = driver.findElement(By.xpath("//input[@type='text']"));
        Username.sendKeys("lena_moseichuk@mail.ru");
        WebElement Password = driver.findElement(By.xpath("//input[@type='password']"));
        Password.sendKeys("73f993467c");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.cssSelector(".content")));
    }

    // Невалидный логин
    @Test
    void InValidLogin() {
        WebElement Username = driver.findElement(By.xpath("//input[@type='text']"));
        Username.sendKeys("ElenaMoseychuk");
        WebElement Password = driver.findElement(By.xpath("//input[@type='password']"));
        Password.sendKeys("73f993467c");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.xpath("//p[contains(text(),'Invalid credentials')]")));
    }

    // Невалидный пароль
    @Test
    void InValidPassword() {
        WebElement Username = driver.findElement(By.xpath("//input[@type='text']"));
        Username.sendKeys("lena_moseichuk@mail.ru");
        WebElement Password = driver.findElement(By.xpath("//input[@type='password']"));
        Password.sendKeys("^&***^^^^567");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.xpath("//p[contains(text(),'Invalid credentials')]")));
    }

    // Пустые поля логин и пароль
    @Test
    void EmptyLoginPassword() {
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.xpath("//p[contains(text(),'Invalid credentials')]")));
    }

    // Невалидное граничное значение логина 2 буквы
    @Test
    void invalidShortLoginTwoLetters(){
        WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
        userName.sendKeys("Em");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("73f993467c");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.xpath("//p[contains(text(),'Invalid credentials')]")));
    }

    // Валидное граничное значение логина 3 буквы
    @Test
    void validShortLoginThreeLetters(){
        WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
        userName.sendKeys("mos");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("bf4f534a44");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.cssSelector(".content")));
       }

    // Валидное граничное значение логина 4 буквы
    @Test
    void validShortLoginFourLetters(){
        WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
        userName.sendKeys("emos");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("f6f1eba5b7");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.cssSelector(".content")));
    }

    // Валидное граничное значение логина 19 букв
    @Test
    void validLongLoginNineteenLetters(){
        WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
        userName.sendKeys("eeeeeeeeeeeeeeeeeee");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("2a5858a5a5");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.cssSelector(".content")));
    }

    // Валидное граничное значение логина 20 букв
    @Test
    void validLongLoginTwentyLetters(){
        WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
        userName.sendKeys("aaaaaaaaaaaaaaaaaaaa");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("22d42eb002");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();
        Assertions.assertNotNull(driver.findElement(By.cssSelector(".content")));
    }

    // Невалидное граничное значение логина 21 буква
    @Test
    void invalidLongLoginTwentyOneLetters(){
        WebElement userName = driver.findElement(By.xpath("//input[@type='text']"));
        userName.sendKeys("mmmmmmmmmmmmmmmmmmmmm");
        WebElement password = driver.findElement(By.xpath("//input[@type='password']"));
        password.sendKeys("73f993467c");
        WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
        button.click();

        Assertions.assertNotNull(driver.findElement(By.xpath("//p[contains(text(),'Invalid credentials')]")));
    }
}
