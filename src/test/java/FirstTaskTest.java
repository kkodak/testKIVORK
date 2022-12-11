import lib.HttpServerManager;
import lib.Timeout;
import lib.page.LoginPage;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class FirstTaskTest {
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
        // we'll try to click at button 3 times. if each time stale element ref exception occurs,
        // then we do not see exception and test proceeds as if click action has been made.
        // As an option we can throw exception on last try
        for (int i = 0; i < 3; i++) {// 3 tries
            try {        // wait login button to be displayed, then click
                wait.until(ExpectedConditions.visibilityOfElementLocated(loginBtn)).click();
                break;
            } catch (StaleElementReferenceException e) {
                // try again
            }
        }
        assertTrue("Login button is shown",driver.findElement(loginBtn).isDisplayed());
    }

    @AfterClass
    public static void quitBrowser() {
        driver.quit();
        server.stopServer();
    }
}
