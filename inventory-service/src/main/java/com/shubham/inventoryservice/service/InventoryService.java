package com.shubham.inventoryservice.service;

import com.shubham.inventoryservice.dto.InventoryResDto;
import com.shubham.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public List<InventoryResDto> isInStock(List<String> skuCodes) {
        log.info("Checking Inventory");
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream().map(
                inventory -> InventoryResDto.builder()
                        .isInStock(inventory.getQuantity() > 0)
                        .skuCode(inventory.getSkuCode())
                        .build()).toList();
    }
}
