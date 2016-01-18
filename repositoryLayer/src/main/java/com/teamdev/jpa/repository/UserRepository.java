package com.teamdev.jpa.repository;

import com.teamdev.jpa.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByPasswordAndName(String password, String name);

    void deleteById(long id);
}
