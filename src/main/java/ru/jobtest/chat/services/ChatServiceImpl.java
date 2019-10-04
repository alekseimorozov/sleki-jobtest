package ru.jobtest.chat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.jobtest.chat.dao.ChatRoomDao;
import ru.jobtest.chat.dao.MessageDao;
import ru.jobtest.chat.dao.UserDao;
import ru.jobtest.chat.entities.ChatRoom;
import ru.jobtest.chat.entities.Message;
import ru.jobtest.chat.entities.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private UserDao userDao;
    private ChatRoomDao chatRoomDao;
    private MessageDao messageDao;

    @Autowired
    public ChatServiceImpl(UserDao userDao, ChatRoomDao chatRoomDao, MessageDao messageDao) {
        this.userDao = userDao;
        this.chatRoomDao = chatRoomDao;
        this.messageDao = messageDao;
    }

    @Override
    @Transactional
    public User findUserById(Long userId) {
        return userDao.findById(userId).orElse(null);
    }

    @Override
    @Transactional
    public ChatRoom findChatRoomById(Long chatId) {
        return chatRoomDao.findById(chatId).orElse(null);
    }

    @Override
    @Transactional
    public ChatRoom addChatRoom(ChatRoom chatRoom) {
        return chatRoomDao.save(chatRoom);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> findAllMessagesForChatRoom(ChatRoom chatRoom) {
        return messageDao.findAllByChatRoom(chatRoom);
    }

    @Override
    @Transactional
    public void saveMessage(Message message) {
        messageDao.save(message);
    }

    @Override
    @Transactional(readOnly = true)
    public User findOrCreate(String userName) {
        User user = userDao.findByName(userName).orElse(null);
        return user == null ? userDao.save(User.builder().name(userName).build()) : user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoom> findAllChats() {
        List<ChatRoom> result = new ArrayList<>();
        chatRoomDao.findAll().forEach(result::add);
        return result;
    }

    @Override
    @Transactional
    public void deleteChatRoom(ChatRoom chatRoom) {
        messageDao.deleteAllMessagesByChatRoom(chatRoom);
        chatRoomDao.delete(chatRoom);
    }
}