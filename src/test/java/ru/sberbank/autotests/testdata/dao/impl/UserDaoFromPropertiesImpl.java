package ru.sberbank.autotests.testdata.dao.impl;

import ru.sberbank.autotests.common.Init;
import ru.sberbank.autotests.testdata.dao.UserDao;
import ru.sberbank.autotests.testdata.domain.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDaoFromPropertiesImpl implements UserDao {
    private static Map<String, String> aliasesToPropPrefixesDict;

    static {
        aliasesToPropPrefixesDict = new HashMap<>();
        aliasesToPropPrefixesDict.put("корректные email и пароль", "testdata.correct.credentials");
        aliasesToPropPrefixesDict.put("корректный email и неправильный пароль", "testdata.incorrect.credentials.wrong.pass");
    }

    @Override
    public User getUserByCredentialsAlias(String alias) {
        String propPrefix = aliasesToPropPrefixesDict.get(alias);
        if (propPrefix == null) {
            throw new IllegalArgumentException("Не удалось найти тестовые данные пользователя по алиасу " + alias);
        }
        String email = Optional.ofNullable(Init.getProperty(propPrefix + ".email"))
                .orElseThrow(() -> new IllegalArgumentException("Не удалось получить email для пользователя по алиасу " + alias));
        String password = Optional.ofNullable(Init.getProperty(propPrefix + ".password"))
                .orElseThrow(() -> new IllegalArgumentException("Не удалось получить пароль для пользователя по алиасу " + alias));
        return new User(email, password);
    }
}
