package com.teamdev.jpa.repository;

import com.teamdev.jpa.model.ChatRoom;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends GenericRepository<ChatRoom, Long> {

}
