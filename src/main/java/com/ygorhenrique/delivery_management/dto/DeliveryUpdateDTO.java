package com.ygorhenrique.delivery_management.dto;

import lombok.Getter;

import java.time.LocalDateTime;

public record DeliveryUpdateDTO(String description, Long customerId, LocalDateTime scheduledAt, String receiver, AddressDTO address) {
}
