package com.shop.sukuna.service;

import com.shop.sukuna.domain.Inventory;
import org.springframework.stereotype.Service;
import com.shop.sukuna.domain.request.ReqInventoryDTO;
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

    public ReqInventoryDTO updateInventory(Inventory inventory, ReqInventoryDTO reqInventoryDTO) {
        inventory.setQuantity(inventory.getQuantity() + reqInventoryDTO.getQuantity());
        this.inventoryRepository.save(inventory);
        reqInventoryDTO.setProductId(inventory.getProduct().getId());
        reqInventoryDTO.setQuantity(inventory.getQuantity());
        return reqInventoryDTO;
    }

}
