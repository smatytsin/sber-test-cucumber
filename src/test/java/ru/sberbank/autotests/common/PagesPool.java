package ru.sberbank.autotests.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import ru.sberbank.autotests.pages.AbstractPage;

import java.util.LinkedHashMap;
import java.util.Map;

public enum PagesPool {
    INSTANCE;

    private ThreadLocal<Map<String, AbstractPage>> pagesPool = ThreadLocal.withInitial(LinkedHashMap::new);

    public static PagesPool pagesPool() {
        return INSTANCE;
    }

    public <T extends AbstractPage> T getPage(WebDriver webDriver, Class<T> clazz) {
        Map<String, AbstractPage> pages = pagesPool.get();
        AbstractPage res = pages.get(clazz.getName());
        if (res == null) {
            T newPage = PageFactory.initElements(webDriver, clazz);
            pages.put(clazz.getName(), newPage);
            return newPage;
        }
        T existedPage = clazz.cast(res);
        PageFactory.initElements(webDriver, existedPage);
        return existedPage;
    }

    public void cleanPool() {
        pagesPool.remove();
    }
}
