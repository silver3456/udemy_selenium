//Урок Баранцева 7.3
//Все протестировал, код работает


import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class SwitchBetweenWindows {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private final By LINK_BUTTON = By.xpath("//span[text()='Работа над ошибками']");


    @BeforeMethod
    public void beforeSetUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void testSwitchWindows() throws InterruptedException {
        driver.get("https://yandex.ru/");
        Thread.sleep(5000);
        String originalWindow = driver.getWindowHandle();
        Set<String> existingWindows = driver.getWindowHandles();
        driver.findElement(LINK_BUTTON).click();

        String newWindow = wait.until(anyWindow(existingWindows));
        driver.switchTo().window(newWindow);
        driver.close();
        driver.switchTo().window(originalWindow);
    }

    private ExpectedCondition<String> anyWindow(final Set<String> existingWindows) {
        return new ExpectedCondition<String>() {
            @NullableDecl
            public String apply(@NullableDecl WebDriver driver) {
                Set<String> handles = driver.getWindowHandles();
                handles.removeAll(existingWindows);
                return handles.size() > 0 ? handles.iterator().next() : null;
            }
        };
    }

    @AfterMethod
    public void teatDown() {
        driver.quit();
        driver = null;
    }
}
