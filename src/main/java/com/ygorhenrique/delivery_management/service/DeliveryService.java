package com.ygorhenrique.delivery_management.service;

import com.ygorhenrique.delivery_management.domain.exception.BusinessException;
import com.ygorhenrique.delivery_management.domain.exception.DeliveryNotFoundException;
import com.ygorhenrique.delivery_management.domain.model.address.Address;
import com.ygorhenrique.delivery_management.domain.model.customer.Customer;
import com.ygorhenrique.delivery_management.domain.model.delivery.Delivery;
import com.ygorhenrique.delivery_management.dto.DeliveryDTO;
import com.ygorhenrique.delivery_management.dto.DeliveryResponseDTO;
import com.ygorhenrique.delivery_management.repository.CustomerRepository;
import com.ygorhenrique.delivery_management.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, CustomerRepository customerRepository) {
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
    }

    public DeliveryResponseDTO createDelivery(DeliveryDTO deliveryDTO) {
        Customer customer = customerRepository.findById(deliveryDTO.getCustomerId())
                .orElseThrow(() -> new BusinessException("Cliente não encontrado"));

        Delivery delivery = buildDelivery(deliveryDTO, customer);

        Delivery savedDelivery = deliveryRepository.save(delivery);

        return toResponseDTO(savedDelivery);
    }

    private Delivery buildDelivery(DeliveryDTO deliveryDTO, Customer customer) {
        Address address = new Address(
                deliveryDTO.getAddress().getStreet(),
                deliveryDTO.getAddress().getNumber(),
                deliveryDTO.getAddress().getNeighborhood(),
                deliveryDTO.getAddress().getCity(),
                deliveryDTO.getAddress().getState(),
                deliveryDTO.getAddress().getZipcode()
        );

        return new Delivery(
                deliveryDTO.getDescription(),
                customer,
                deliveryDTO.getScheduledAt(),
                address,
                deliveryDTO.getReceiver()
        );
    }

    public DeliveryResponseDTO getDeliveryById(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException(id)
        );
        return toResponseDTO(delivery);
    }

    private DeliveryResponseDTO toResponseDTO(Delivery delivery) {
        return new DeliveryResponseDTO(
                delivery.getId(),
                delivery.getDescription(),
                delivery.getReceiver(),
                delivery.getScheduledAt(),
                delivery.getAddress().getStreet(),
                delivery.getAddress().getNumber(),
                delivery.getAddress().getNeighborhood(),
                delivery.getAddress().getCity(),
                delivery.getAddress().getState(),
                delivery.getAddress().getZipcode()
        );
    }
}
