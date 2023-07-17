package com.example.demo.repository;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterfaceRepo extends JpaRepository<User, Integer> {
//    @Override
//    default void flush() {
//        // Add implementation if required
//    }

    @Query(value="SELECT USER_NAME FROM USERS WHERE USER_ID =:user_id",nativeQuery = true)
    String getAll(Integer user_id);

}

