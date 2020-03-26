package ru.sberbank.autotests.utils;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

public class WebDriverUtils {
    private WebDriverUtils() {
    }

    public static boolean isElementExists(WebElement element) {
        try {
            element.getTagName();
            return true;
        } catch (NoSuchElementException | StaleElementReferenceException ex) {
            return false;
        }

    }
}
