package ru.jobtest.chat.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.jobtest.chat.TestUtils.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EntityTest {
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("User successfully persisted into DB")
    void persistUserTest() {
        User expected = newUser("Test User");
        entityManager.persistAndFlush(expected);
        assertEquals(expected, entityManager.find(User.class, expected.getId()));
    }

    @Test
    @DisplayName("ChatRoom successfully persisted into DB")
    void persistChatRoomTest() {
        User testUser = newUser("Test User");
        entityManager.persistAndFlush(testUser);
        ChatRoom expected = newChatRoom("Test chat room",testUser);
        entityManager.persistAndFlush(expected);
        assertEquals(expected, entityManager.find(ChatRoom.class, expected.getId()));
    }

    @Test
    void persistMessageTest() {
        User author = newUser("Test Author");
        entityManager.persistAndFlush(author);
        ChatRoom testChat = newChatRoom("Test chat room", author);
        entityManager.persistAndFlush(testChat);
        Message expected = newMessage(testChat, author, "test message");
        entityManager.persistAndFlush(expected);
        assertEquals(expected, entityManager.find(Message.class, expected.getId()));
    }
}