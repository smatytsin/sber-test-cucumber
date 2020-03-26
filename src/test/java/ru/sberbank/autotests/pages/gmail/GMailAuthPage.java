package ru.sberbank.autotests.pages.gmail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import ru.sberbank.autotests.pages.AbstractPage;
import ru.sberbank.autotests.testdata.domain.User;

public class GMailAuthPage extends AbstractPage {
    @FindBy(how = How.ID, using = "identifierId")
    private WebElement loginInput;
    @FindBy(how = How.CSS, using = "div#password input[name='password']")
    private WebElement passwordInput;
    @FindBy(how = How.ID, using = "identifierNext")
    private WebElement nextToPasswortButton;
    @FindBy(how = How.ID, using = "passwordNext")
    private WebElement loginButton;
    @FindBy(how = How.CSS, using = "div[aria-live='assertive'")
    private WebElement message;

    public GMailAuthPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void authAsUser(User user) {
        loginInput.sendKeys(user.getEmail());
        nextToPasswortButton.click();
        passwordInput.sendKeys(user.getPassword());
        loginButton.click();
    }

    public String getAuthErrorMessage() {
        return message.getText();
    }
}
