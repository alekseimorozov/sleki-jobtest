package ru.jobtest.chat.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jobtest.chat.entities.ChatRoom;
import ru.jobtest.chat.entities.Message;

import java.util.List;

@Repository
public interface MessageDao extends CrudRepository<Message, Long> {
    List<Message> findAllByChatRoom(ChatRoom chatRoom);

    void deleteAllMessagesByChatRoom(ChatRoom chatRoom);
}