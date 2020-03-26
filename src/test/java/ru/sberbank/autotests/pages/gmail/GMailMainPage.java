package ru.sberbank.autotests.pages.gmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.sberbank.autotests.pages.AbstractPage;

import java.util.HashSet;
import java.util.Set;

public class GMailMainPage extends AbstractPage {

    @FindBy(how = How.CSS, using = "ul.header__nav--ltr a[ga-event-action='sign in']")
    private WebElement signInButton;

    public GMailMainPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void navigateTo() {
        //TODO: вынести в конфиги урлу
        webDriver.get("https://www.google.com/intl/ru/gmail/about/#");
        //PageFactory.initElements(webDriver, this);
    }

    public void goToAuthForm() {
        Set<String> windowsBefore = new HashSet<>(webDriver.getWindowHandles());
        signInButton.click();
        Set<String> windowsAfter = new HashSet<>(webDriver.getWindowHandles());
        windowsAfter.removeAll(windowsBefore);
        if (windowsAfter.size() != 1) {
            throw new IllegalStateException("Ожидалось, что после перехода к форме аутентификации появится ровно одна новая вкладка браузера. Фактическое кол-во вркладок = " + windowsAfter);
        }
        webDriver.switchTo().window(windowsAfter.iterator().next());
    }

}
