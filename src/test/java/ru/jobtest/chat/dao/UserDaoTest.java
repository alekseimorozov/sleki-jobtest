package ru.jobtest.chat.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.jobtest.chat.entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static ru.jobtest.chat.TestUtils.newUser;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDaoTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserDao userDao;

    @Test
    @DisplayName("Returns User if exists")
    void findByNameTest() {
        String testName = "Expected User";
        User expected = newUser(testName);
        entityManager.persistAndFlush(expected);
        User actual = userDao.findByName(testName).orElse(null);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Returns null if does not exists")
    void findByNameNotExistsTest() {
        User actual = userDao.findByName("Not Exists").orElse(null);
        assertNull(actual);
    }
}