package ru.jobtest.chat;

import ru.jobtest.chat.entities.ChatRoom;
import ru.jobtest.chat.entities.Message;
import ru.jobtest.chat.entities.User;

public class TestUtils {

    public static User newUser(String name) {
        return User.builder().name(name).build();
    }

    public static ChatRoom newChatRoom(String name, User user) {
        return ChatRoom.builder().name(name).admin(user).build();
    }

    public static Message newMessage(ChatRoom chatRoom, User author, String text) {
        return Message.builder()
                .chatRoom(chatRoom)
                .author(author)
                .text(text)
                .build();
    }
}
