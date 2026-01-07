package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.LostFoundItem;
import com.example.demo.service.ItemService;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    // ➕ ADD ITEM (WITH OR WITHOUT IMAGE)
    @PostMapping(consumes = "multipart/form-data")
    public LostFoundItem addItem(
            @RequestPart("item") LostFoundItem item,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) throws Exception {

        if (image != null && !image.isEmpty()) {

            // sanitize filename
            String original = image.getOriginalFilename();
            String cleanName = original.replaceAll("\\s+", "_");

            String fileName = System.currentTimeMillis() + "_" + cleanName;

            Path path = Paths.get("uploads/" + fileName);
            Files.createDirectories(path.getParent());
            Files.write(path, image.getBytes());

            item.setImageUrl("/uploads/" + fileName);
        }

        item.setStatus("OPEN");
        return service.reportItem(item);
    }

    // 📋 GET ALL ITEMS
    @GetMapping
    public List<LostFoundItem> getAllItems() {
        return service.getAllItems();
    }

    // ✅ CLAIM ITEM (ASK NAME)
    @PutMapping("/{id}/claim")
    public void claimItem(
            @PathVariable Long id,
            @RequestParam String name
    ) {
        service.markClaimed(id, name);
    }

    // ❌ DELETE ITEM
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        service.deleteItem(id);
    }
}
