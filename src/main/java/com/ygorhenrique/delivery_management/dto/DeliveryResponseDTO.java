package com.ygorhenrique.delivery_management.dto;

import com.ygorhenrique.delivery_management.domain.model.delivery.DeliveryStatus;

import java.time.LocalDateTime;

public record DeliveryResponseDTO(Long id, DeliveryStatus status, LocalDateTime deliveredAt, String description, String customer, String receiver, LocalDateTime scheduledAt, String street, Integer number, String neighborhood, String city, String state, String zipcode) {
}
