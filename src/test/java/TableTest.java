import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class TableTest {
    private static WebDriver driver;


    @BeforeMethod
    public void beforeSetUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }


    @Test
    public static void findListOfElements() {
        driver.get("https://www.w3schools.com/html/html_tables.asp");
        WebElement tableElement = driver.findElement(By.xpath("//table[@id='customers']"));

        Table table = new Table(tableElement, driver);

        System.out.println("Rows number is: " + table.getRows().size());

        System.out.println(table.getValueFromCell(2, 3));
        System.out.println(table.getValueFromCell(4, 1));

        System.out.println(table.getValueFromCell(4, "Company"));
        System.out.println(table.getValueFromCell(1, "Country"));
        System.out.println(table.getValueFromCell(2, "Contact"));



    }

    @AfterMethod
    public static void teatDown() {
        driver.quit();
    }
}
