package ru.jobtest.chat.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.jobtest.chat.entities.ChatRoom;
import ru.jobtest.chat.entities.Message;
import ru.jobtest.chat.entities.User;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static ru.jobtest.chat.TestUtils.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MessageDaoTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private MessageDao messageDao;

    @Test
    void findAllByChatRoomTest() {
        User userOne = newUser("One");
        User userTwo = newUser("Two");
        entityManager.persistAndFlush(userOne);
        entityManager.persistAndFlush(userTwo);
        ChatRoom chatRoomOne = newChatRoom("chat one", userOne);
        ChatRoom chatRoomTwo = newChatRoom("chat two", userTwo);
        ChatRoom chatRoomEmpty = newChatRoom("chat empty", userTwo);
        entityManager.persistAndFlush(chatRoomOne);
        entityManager.persistAndFlush(chatRoomTwo);
        entityManager.persistAndFlush(chatRoomEmpty);
        Message messageOne = newMessage(chatRoomOne, userOne, "First message of One");
        Message messageTwo = newMessage(chatRoomOne, userOne, "Second message of One");
        Message messageThree = newMessage(chatRoomOne, userTwo, "First message of Two");
        Message messageFour = newMessage(chatRoomOne, userTwo, "Second message of Two");
        Message otherMessageOne = newMessage(chatRoomTwo, userTwo, "First other message of Two");
        Message otherMessageTwo = newMessage(chatRoomTwo, userOne, "First other message of One");
        entityManager.persistAndFlush(messageOne);
        entityManager.persistAndFlush(messageTwo);
        entityManager.persistAndFlush(messageThree);
        entityManager.persistAndFlush(messageFour);
        entityManager.persistAndFlush(otherMessageOne);
        entityManager.persistAndFlush(otherMessageTwo);
        assertThat(messageDao.findAllByChatRoom(chatRoomOne))
                .hasSize(4)
                .containsExactly(messageOne, messageTwo, messageThree, messageFour);
        assertThat(messageDao.findAllByChatRoom(chatRoomTwo))
                .hasSize(2)
                .containsExactly(otherMessageOne, otherMessageTwo);
        assertThat(messageDao.findAllByChatRoom(chatRoomEmpty))
                .hasSize(0);
    }

    @Test
    public void deleteAllMessagesByChatRoomTest() {
        User userOne = newUser("One");
        User userTwo = newUser("Two");
        entityManager.persistAndFlush(userOne);
        entityManager.persistAndFlush(userTwo);
        ChatRoom chatRoomOne = newChatRoom("chat one", userOne);
        ChatRoom chatRoomTwo = newChatRoom("chat two", userTwo);
        ChatRoom chatRoomEmpty = newChatRoom("chat empty", userTwo);
        entityManager.persistAndFlush(chatRoomOne);
        entityManager.persistAndFlush(chatRoomTwo);
        entityManager.persistAndFlush(chatRoomEmpty);
        Message messageOne = newMessage(chatRoomOne, userOne, "First message of One");
        Message messageTwo = newMessage(chatRoomOne, userOne, "Second message of One");
        Message messageThree = newMessage(chatRoomOne, userTwo, "First message of Two");
        Message messageFour = newMessage(chatRoomOne, userTwo, "Second message of Two");
        Message otherMessageOne = newMessage(chatRoomTwo, userTwo, "First other message of Two");
        Message otherMessageTwo = newMessage(chatRoomTwo, userOne, "First other message of One");
        entityManager.persistAndFlush(messageOne);
        entityManager.persistAndFlush(messageTwo);
        entityManager.persistAndFlush(messageThree);
        entityManager.persistAndFlush(messageFour);
        entityManager.persistAndFlush(otherMessageOne);
        entityManager.persistAndFlush(otherMessageTwo);
        messageDao.deleteAllMessagesByChatRoom(chatRoomOne);
        assertThat(messageDao.findAllByChatRoom(chatRoomOne)).hasSize(0);
        assertThat(messageDao.findAllByChatRoom(chatRoomTwo))
                .hasSize(2)
                .containsExactly(otherMessageOne, otherMessageTwo);
    }
}