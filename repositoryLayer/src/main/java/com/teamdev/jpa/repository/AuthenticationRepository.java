package com.teamdev.jpa.repository;

import com.teamdev.jpa.model.AuthenticationToken;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationRepository extends GenericRepository<AuthenticationToken, Long> {

    AuthenticationToken findByToken(String token);

    void deleteById(long id);

}
