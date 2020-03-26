package ru.sberbank.autotests.pages;

import org.openqa.selenium.WebDriver;

public abstract class AbstractPage {
    protected final WebDriver webDriver;

    public AbstractPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

}
