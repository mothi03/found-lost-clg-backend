package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.LostFoundItem;

public interface ItemRepository extends JpaRepository<LostFoundItem, Long> {
}
