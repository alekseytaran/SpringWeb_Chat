package com.teamdev.jpa.repository;

import com.teamdev.jpa.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
        Set<Message> findByChatRoomId(Long chatRoomId);
}
