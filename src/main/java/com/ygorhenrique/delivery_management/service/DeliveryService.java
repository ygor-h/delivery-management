package com.ygorhenrique.delivery_management.service;

import com.ygorhenrique.delivery_management.domain.exception.CustomerNotFoundException;
import com.ygorhenrique.delivery_management.domain.exception.DeliveryNotFoundException;
import com.ygorhenrique.delivery_management.domain.model.address.Address;
import com.ygorhenrique.delivery_management.domain.model.customer.Customer;
import com.ygorhenrique.delivery_management.domain.model.delivery.Delivery;
import com.ygorhenrique.delivery_management.dto.DeliveryDTO;
import com.ygorhenrique.delivery_management.dto.DeliveryResponseDTO;
import com.ygorhenrique.delivery_management.dto.DeliveryUpdateDTO;
import com.ygorhenrique.delivery_management.repository.CustomerRepository;
import com.ygorhenrique.delivery_management.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final CustomerRepository customerRepository;

    public DeliveryService(DeliveryRepository deliveryRepository, CustomerRepository customerRepository) {
        this.deliveryRepository = deliveryRepository;
        this.customerRepository = customerRepository;
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

    public DeliveryResponseDTO createDelivery(DeliveryDTO deliveryDTO) {
        Customer customer = customerRepository.findById(deliveryDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado"));

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

    public List<DeliveryResponseDTO> getAllDeliveries() {
        return deliveryRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public void deleteDelivery(Long id) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException(id)
        );
        deliveryRepository.delete(delivery);
    }

    public DeliveryResponseDTO patchDelivery(Long id, DeliveryUpdateDTO deliveryUpdateDTO) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException(id)
        );

        if (deliveryUpdateDTO.description() != null) {
            delivery.setDescription(deliveryUpdateDTO.description());
        }

        if (deliveryUpdateDTO.receiver() != null) {
            delivery.setReceiver(deliveryUpdateDTO.receiver());
        }

        if (deliveryUpdateDTO.scheduledAt() != null) {
            delivery.setScheduledAt(deliveryUpdateDTO.scheduledAt());
        }

        if (deliveryUpdateDTO.customerId() != null) {
            Customer customer = customerRepository.findById(deliveryUpdateDTO.customerId())
                    .orElseThrow(() -> new CustomerNotFoundException("Cliente não encontrado"));

            delivery.setCustomer(customer);
        }

        if (deliveryUpdateDTO.address() != null) {
            Address address = new Address(
                    deliveryUpdateDTO.address().getStreet(),
                    deliveryUpdateDTO.address().getNumber(),
                    deliveryUpdateDTO.address().getNeighborhood(),
                    deliveryUpdateDTO.address().getCity(),
                    deliveryUpdateDTO.address().getState(),
                    deliveryUpdateDTO.address().getZipcode()
            );

            delivery.setAddress(address);
        }

        Delivery saved = deliveryRepository.save(delivery);

        return toResponseDTO(saved);
    }

    public DeliveryResponseDTO updateDelivery(Long id, DeliveryDTO deliveryDTO) {
        Delivery delivery = deliveryRepository.findById(id).orElseThrow(
                () -> new DeliveryNotFoundException(id)
        );

        Customer customer = customerRepository.findById(deliveryDTO.getCustomerId()).orElseThrow(
                () -> new CustomerNotFoundException("Cliente não encontrado")
        );

        Address address = new Address(
                deliveryDTO.getAddress().getStreet(),
                deliveryDTO.getAddress().getNumber(),
                deliveryDTO.getAddress().getNeighborhood(),
                deliveryDTO.getAddress().getCity(),
                deliveryDTO.getAddress().getState(),
                deliveryDTO.getAddress().getZipcode()
        );

        delivery.setDescription(deliveryDTO.getDescription());
        delivery.setCustomer(customer);
        delivery.setScheduledAt(deliveryDTO.getScheduledAt());
        delivery.setReceiver(deliveryDTO.getReceiver());
        delivery.setAddress(address);

        Delivery saved = deliveryRepository.save(delivery);

        return toResponseDTO(saved);
    }
}
