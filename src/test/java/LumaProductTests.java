import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LumaProductTests {

    static WebDriver driver;
    String siteURL = "https://magento.softwaretestingboard.com/";
    WebDriverWait wait;
    static WebDriverFactory.BrowserType browserType;

    @BeforeAll
    static void setupClass() {
        // Initialize the WebDriver based on the desired browser
         browserType = WebDriverFactory.BrowserType.CHROME;
    }

    @BeforeEach
    void setupTest() {
        driver = WebDriverFactory.createWebDriver(browserType);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(siteURL);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/words.csv", numLinesToSkip = 1)
    void testName(String word) {
        WebElement input = driver.findElement(By.id("search"));
        input.sendKeys(word);
        input.sendKeys(Keys.ENTER);

        List<WebElement> products = driver.findElements(By.cssSelector("li.item.product.product-item"));

        // get first product
        String name = products.get(0).findElement(By.cssSelector("a.product-item-link")).getText();

        // click on the first product to open it in detail
        products.get(0).click();

        String detailName = driver.findElement(By.cssSelector("span[data-ui-id]")).getText();

        assertThat(name).isEqualTo(detailName);

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/words.csv", numLinesToSkip = 1)
    void testPrice(String word) {
        driver.get("https://magento.softwaretestingboard.com/");

        WebElement input = driver.findElement(By.id("search"));
        input.sendKeys(word);
        input.sendKeys(Keys.ENTER);

        List<WebElement> products = driver.findElements(By.cssSelector("li.item.product.product-item"));

        // get first product
        String price = products.get(0).findElement(By.cssSelector("span.price")).getText();

        // click on the first product to open it in detail
        products.get(0).click();

        // fix stale element exception
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.price")));
        String detailPrice = element.getText();

        assertThat(price).isEqualTo(detailPrice);
    }
}