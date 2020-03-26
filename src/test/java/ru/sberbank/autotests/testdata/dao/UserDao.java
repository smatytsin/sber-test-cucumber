package ru.sberbank.autotests.testdata.dao;

import ru.sberbank.autotests.testdata.domain.User;

public interface UserDao {

    User getUserByCredentialsAlias(String alias);
}
