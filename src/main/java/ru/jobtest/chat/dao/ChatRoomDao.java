package ru.jobtest.chat.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.jobtest.chat.entities.ChatRoom;

@Repository
public interface ChatRoomDao extends CrudRepository<ChatRoom, Long> {
}