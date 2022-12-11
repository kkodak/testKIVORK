import lib.HttpServerManager;
import lib.Timeout;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ThirdTaskTest {
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
        By googleBtn = By.xpath("//button[text()='Google me!']");

        //Click on 'Google me', user should be redirected on new tab with google.com
        wait.until(ExpectedConditions.visibilityOfElementLocated(googleBtn)).click();

        // get current window handler
        String parentWindow = driver.getWindowHandle();
        //find new window handler and switch to it
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(parentWindow)) {
                driver.switchTo().window(windowHandle);
            }
        }
        // check that this new window shows Google page
        assertEquals("Google", driver.getTitle());
        // close new window
        driver.close();
        //switch back to old window
        driver.switchTo().window(parentWindow);
        // check that this window shows old page
        assertEquals("Login", driver.getTitle());

    }

    @AfterClass
    public static void quiteBrowser() {
        driver.quit();
        server.stopServer();
    }
}
