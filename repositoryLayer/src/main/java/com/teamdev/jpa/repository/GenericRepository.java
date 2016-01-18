package com.teamdev.jpa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface GenericRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {


}
