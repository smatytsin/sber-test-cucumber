package ru.sberbank.autotests.glue;

import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.Если;
import io.cucumber.java.ru.То;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sberbank.autotests.pages.gmail.GMailAuthPage;
import ru.sberbank.autotests.pages.gmail.GMailMainPage;
import ru.sberbank.autotests.pages.gmail.GMailUserBoxMainPage;
import ru.sberbank.autotests.testdata.domain.User;

import static org.junit.Assert.*;
import static ru.sberbank.autotests.common.PagesPool.pagesPool;
import static ru.sberbank.autotests.common.WebDriverManager.webDriverManager;
import static ru.sberbank.autotests.testdata.TestData.testData;

public class CommonSteps {

    @Допустим("^пользователь открывает браузер и переходит на страницу аутентификации почтового сервиса Gmail$")
    public void openAuthPage() {
        GMailMainPage mainPage = pagesPool().getPage(webDriverManager().currentDriver(), GMailMainPage.class);
        mainPage.navigateTo();
        mainPage.goToAuthForm();
    }

    @Если("^пользователь вводит для аутентификации (.*)$")
    public void loginWithCredentials(String credentialsAlias) {
        User usr = testData().getUserDao().getUserByCredentialsAlias(credentialsAlias);
        GMailAuthPage authPage = pagesPool().getPage(webDriverManager().currentDriver(), GMailAuthPage.class);
        authPage.authAsUser(usr);
    }

    @То("^Gmail (не |)предоставляет доступ к почтовому ящику пользователя$")
    public void checkAuthResult(String failAuthFlag) {
        GMailUserBoxMainPage userMailBoxPage = pagesPool().getPage(webDriverManager().currentDriver(), GMailUserBoxMainPage.class);
        if (failAuthFlag.isEmpty()) {
            assertTrue(userMailBoxPage.isCurrentOpenedPage());
        } else {
            assertFalse(userMailBoxPage.isCurrentOpenedPage());
        }
    }

    @То("^Gmail выводит сообщение о неудачной попытке аутентификации$")
    public void checkAuthErrMsg() {
        WebDriver driver = webDriverManager().currentDriver();
        GMailAuthPage authPage = pagesPool().getPage(driver, GMailAuthPage.class);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(d -> !authPage.getAuthErrorMessage().isEmpty());
        assertEquals("Неверный пароль. Повторите попытку или нажмите на ссылку \"Забыли пароль?\", чтобы сбросить его.", authPage.getAuthErrorMessage());
    }
}
