package com.shop.sukuna.service;

import org.springframework.stereotype.Service;

import com.shop.sukuna.domain.Inventory;
import com.shop.sukuna.repository.InventoryRepository;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public Inventory createInventory(Inventory inventory) {
        return this.inventoryRepository.save(inventory);
    }

}
