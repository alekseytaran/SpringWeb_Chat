package com.teamdev.jpa.repository;

import com.teamdev.jpa.model.AuthenticationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthenticationRepository extends CrudRepository<AuthenticationToken, Long> {

    AuthenticationToken findByAccessToken(String token);

}
