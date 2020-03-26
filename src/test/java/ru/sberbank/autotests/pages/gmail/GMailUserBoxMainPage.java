package ru.sberbank.autotests.pages.gmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.sberbank.autotests.pages.AbstractPage;

import static ru.sberbank.autotests.utils.WebDriverUtils.isElementExists;

public class GMailUserBoxMainPage extends AbstractPage {
    @FindBy(how = How.CSS, using = "div[data-tooltip='Входящие'] a[title='Входящие']")
    private WebElement inMessagesButton;

    public GMailUserBoxMainPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean isCurrentOpenedPage() {
        return isElementExists(inMessagesButton);
    }
}
