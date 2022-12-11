import lib.HttpServerManager;
import lib.Timeout;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class FourthTaskTest {
    private static WebDriver driver;
    private static HttpServerManager server;
    private static WebDriverWait wait;


    @BeforeClass
    public static void setupBrowser() throws IOException {
        driver = new ChromeDriver();
        server = new HttpServerManager();
        wait = new WebDriverWait(driver, Duration.ofSeconds(Timeout.defaultTimeout));


    }

    @Test
    public void test() throws InterruptedException {
        driver.get(server.getServerUrl());
        By loginBtn= By.cssSelector("button[type='submit']");

        // click at Login button, it could redirect on new page in case all fields are filled, but it does not.
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn)).click();

        // wait page stop refreshing
        wait.until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return isPageLoaded(driver);
            }
        });

        // is it still the same page?  we can check here error messages which are expected
        assertEquals("Login", driver.getTitle());
    }

    @AfterClass
    public static void quiteBrowser() {
        driver.quit();
        server.stopServer();
    }

    protected boolean isPageLoaded(WebDriver driver) {
        String result = String.valueOf( ((JavascriptExecutor) driver).executeScript(
                "if (typeof window != 'undefined'){return document.readyState; }else { return 'nothing';}"));
        return result.equals("complete") || result.equals("interactive")
                || result.equals("loaded");
    }


}
