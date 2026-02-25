package com.ygorhenrique.delivery_management.controller;

import com.ygorhenrique.delivery_management.dto.DeliveryDTO;
import com.ygorhenrique.delivery_management.dto.DeliveryResponseDTO;
import com.ygorhenrique.delivery_management.dto.DeliveryUpdateDTO;
import com.ygorhenrique.delivery_management.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("deliveries")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryResponseDTO> createDelivery(@Valid @RequestBody DeliveryDTO deliveryDTO) {
        DeliveryResponseDTO response = deliveryService.createDelivery(deliveryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }

    @GetMapping
    public ResponseEntity<List<DeliveryResponseDTO>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> patchDelivery(@PathVariable Long id, @RequestBody DeliveryUpdateDTO deliveryUpdateDTO) {
        return ResponseEntity.ok(deliveryService.patchDelivery(id, deliveryUpdateDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO>  updateDelivery(@PathVariable Long id, @Valid @RequestBody DeliveryDTO deliveryDTO) {
        return ResponseEntity.ok(deliveryService.updateDelivery(id, deliveryDTO));
    }
}
