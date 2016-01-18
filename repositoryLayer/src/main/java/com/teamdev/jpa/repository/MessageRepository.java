package com.teamdev.jpa.repository;

import com.teamdev.jpa.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends GenericRepository<Message, Long> {

}
