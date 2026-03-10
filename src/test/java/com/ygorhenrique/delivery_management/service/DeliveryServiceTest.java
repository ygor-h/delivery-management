package com.ygorhenrique.delivery_management.service;

import com.ygorhenrique.delivery_management.domain.exception.CustomerNotFoundException;
import com.ygorhenrique.delivery_management.domain.model.customer.Customer;
import com.ygorhenrique.delivery_management.domain.model.delivery.Delivery;
import com.ygorhenrique.delivery_management.dto.AddressDTO;
import com.ygorhenrique.delivery_management.dto.DeliveryDTO;
import com.ygorhenrique.delivery_management.dto.DeliveryResponseDTO;
import com.ygorhenrique.delivery_management.repository.CustomerRepository;
import com.ygorhenrique.delivery_management.repository.DeliveryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    private DeliveryDTO createDeliveryDTO() {
        AddressDTO addressDTO = new AddressDTO(
                "Rua das Flores",
                364,
                "Centro",
                "Santo André",
                "SP",
                "09000-000"
        );

        return new DeliveryDTO(
                "Teste",
                1L,
                LocalDateTime.now(),
                addressDTO,
                "Teste"
        );
    }

    @Test
    public void shouldCreateDeliverySuccessfully() {
        Customer customer = new Customer(
                1L,
                "João",
                "11900000000",
                "joao@email.com"
        );
        DeliveryDTO deliveryDTO = createDeliveryDTO();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        DeliveryResponseDTO response = deliveryService.createDelivery(deliveryDTO);

        Assertions.assertEquals("Teste", response.description());
        Assertions.assertEquals("Teste", response.receiver());
        Assertions.assertEquals("João", response.customer());
    }

    @Test
    public void shouldThrowExceptionWhenCustomerNotFound() {
        DeliveryDTO deliveryDTO = createDeliveryDTO();

        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        CustomerNotFoundException exception = Assertions.assertThrows(
                CustomerNotFoundException.class,
                () -> deliveryService.createDelivery(deliveryDTO));
        Assertions.assertEquals("Cliente não encontrado", exception.getMessage());
        verify(deliveryRepository, never()).save(any());
    }
}
