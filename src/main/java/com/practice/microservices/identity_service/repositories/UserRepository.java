package com.practice.microservices.identity_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.microservices.identity_service.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String>  {

}
