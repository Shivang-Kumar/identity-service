package com.practice.microservices.identity_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.microservices.identity_service.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>  {

}
