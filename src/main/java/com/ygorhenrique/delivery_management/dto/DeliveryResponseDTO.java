package com.ygorhenrique.delivery_management.dto;

import java.time.LocalDateTime;

public record DeliveryResponseDTO(Long id, String description, String receiver, LocalDateTime scheduledAt, String street, Integer number, String neighborhood, String city, String state, String zipcode) {
}
