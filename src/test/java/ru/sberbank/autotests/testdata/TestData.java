package ru.sberbank.autotests.testdata;

import ru.sberbank.autotests.testdata.dao.UserDao;
import ru.sberbank.autotests.testdata.dao.impl.UserDaoFromPropertiesImpl;

public enum TestData {
    INSTANCE;
    private final UserDao userDao;

    TestData() {
        userDao = new UserDaoFromPropertiesImpl();
    }

    public static TestData testData() {
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
