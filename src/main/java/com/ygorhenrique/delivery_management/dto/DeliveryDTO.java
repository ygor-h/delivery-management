package com.ygorhenrique.delivery_management.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeliveryDTO {
    @NotNull(message = "Descrição é obrigatória")
    private String description;
    @NotNull(message = "Cliente é obrigatório")
    private Long customerId;
    @NotNull(message = "Horário de entrega é obrigatório")
    private LocalDateTime scheduledAt;
    @Valid
    @NotNull(message = "Dados de endereço são obrigatórios")
    private AddressDTO address;
    @NotNull(message = "Nome do receptor é obrigatório")
    private String receiver;
}
