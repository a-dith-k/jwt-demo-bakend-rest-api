package com.adith.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adith.demo.entities.ProfileImageEntity;
import com.adith.demo.entities.UserEntity;

public interface ProfileImageRepository extends JpaRepository<ProfileImageEntity,Integer> {

    ProfileImageEntity findByUser(UserEntity user);
    
}
