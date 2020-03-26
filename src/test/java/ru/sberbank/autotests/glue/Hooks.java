package ru.sberbank.autotests.glue;

import io.cucumber.java.After;
import io.cucumber.java.Before;

import static ru.sberbank.autotests.common.PagesPool.pagesPool;
import static ru.sberbank.autotests.common.WebDriverManager.webDriverManager;

public class Hooks {
    @Before(order = 0)
    public void cleanPagesPool() {
        pagesPool().cleanPool();
    }

    @After(order = 10000)
    public void quitWebDriver() {
        webDriverManager().killCurrentDriver();
    }

}
