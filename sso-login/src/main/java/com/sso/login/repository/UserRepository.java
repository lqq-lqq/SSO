package com.sso.login.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.sso.login.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}