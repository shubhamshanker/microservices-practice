package com.shubham.inventoryservice.controller;

import com.shubham.inventoryservice.service.InventoryService;
import com.shubham.inventoryservice.dto.InventoryResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;
    @GetMapping
    public List<InventoryResDto> isInStock(@RequestParam List<String> skuCodes){
        log.info("Received inventory check request for skuCode: {}", skuCodes);
        return inventoryService.isInStock(skuCodes);
    }

}
