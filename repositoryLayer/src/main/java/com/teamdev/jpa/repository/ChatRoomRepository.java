package com.teamdev.jpa.repository;

import com.teamdev.jpa.model.ChatRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {

}
