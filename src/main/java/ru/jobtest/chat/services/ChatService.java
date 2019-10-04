package ru.jobtest.chat.services;

import ru.jobtest.chat.entities.ChatRoom;
import ru.jobtest.chat.entities.Message;
import ru.jobtest.chat.entities.User;

import java.util.List;

public interface ChatService {
    /**
     * Finds user by user_id
     *
     * @param userId
     * @return User or null
     */
    User findUserById(Long userId);

    /**
     * Finds ChatRoom by chat_id
     *
     * @param chatId
     * @return ChatRoom or null
     */
    ChatRoom findChatRoomById(Long chatId);

    /**
     * Find all messages for given chatRoom
     *
     * @param chatRoom
     * @return List of messages or empty list
     */
    List<Message> findAllMessagesForChatRoom(ChatRoom chatRoom);

    /**
     * Save Message into database
     *
     * @param message
     */
    void saveMessage(Message message);

    /**
     * Find User by name, if does not found - create and save new User
     *
     * @param userName
     * @return User, that was found or created
     */
    User findOrCreate(String userName);

    /**
     * Find List of all ChatRoom
     *
     * @return list of ChatRoom
     */
    List<ChatRoom> findAllChats();

    /**
     * Deletes ChatRoom
     *
     * @param chatRoom
     */
    void deleteChatRoom(ChatRoom chatRoom);

    /**
     * Save new ChatRomm into DB
     * @param chatRoom
     * @return
     */
    ChatRoom addChatRoom(ChatRoom chatRoom);
}
