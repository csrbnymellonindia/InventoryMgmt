package com.example.demo.repository;

import com.example.demo.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    @Query(value="SELECT * FROM Category",nativeQuery = true)
    List<Category> getAll();
}
