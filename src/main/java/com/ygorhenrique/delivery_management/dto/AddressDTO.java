package com.ygorhenrique.delivery_management.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddressDTO {
    @NotBlank(message = "Rua é obrigatória")
    private String street;
    @NotNull(message = "Número é obrigatório")
    @Positive(message = "Número deve ser maior que zero")
    private Integer number;
    @NotBlank(message = "Bairro é obrigatório")
    private String neighborhood;
    @NotBlank(message = "Cidade é obrigatória")
    private String city;
    @NotBlank(message = "Estado é obrigatório")
    private String state;
    @NotBlank(message = "CEP é obrigatório")
    private String zipcode;
}
