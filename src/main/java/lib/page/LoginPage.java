package lib.page;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.FieldDecorator;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.lang.reflect.Field;

public class LoginPage extends BasePage{

    @FindBy(css="button[type='submit']")
    private WebElement loginBtn;

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void init() {
        PageFactory.initElements(webDriver,this);
    }

    public void clickLogin(){
        // wait login button to be displayed
        wait.until(ExpectedConditions.visibilityOf(loginBtn));
        // we'll try to click at button 3 times. if each time stale element exception occurs,
        // then we do not see exception and test proceeds as if click action has been made.
        // As an option we can throw exception on last try
        for (int i = 0; i < 3; i++) {// 3 tries
            try {
                loginBtn.click();
                break;
            } catch (StaleElementReferenceException e) {
                // try again
            }
        }

    }
}
