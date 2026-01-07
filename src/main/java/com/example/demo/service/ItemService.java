package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.LostFoundItem;
import com.example.demo.repo.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public LostFoundItem reportItem(LostFoundItem item) {
        return repo.save(item);
    }

    public List<LostFoundItem> getAllItems() {
        return repo.findAll();
    }

    public void markClaimed(Long id) {
        LostFoundItem item = repo.findById(id).orElseThrow();
        item.setStatus("CLAIMED");
        repo.save(item);
    }
    public void markClaimed(Long id, String claimedBy) {
        LostFoundItem item = repo.findById(id).orElseThrow();
        item.setStatus("CLAIMED");
        item.setClaimedBy(claimedBy);
        repo.save(item);
    }
    public void deleteItem(Long id) {
        repo.deleteById(id);
    }


}
