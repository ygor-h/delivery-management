package com.ygorhenrique.delivery_management.domain;

import com.ygorhenrique.delivery_management.domain.exception.BusinessException;
import com.ygorhenrique.delivery_management.domain.model.address.Address;
import com.ygorhenrique.delivery_management.domain.model.customer.Customer;
import com.ygorhenrique.delivery_management.domain.model.delivery.Delivery;
import com.ygorhenrique.delivery_management.domain.model.delivery.DeliveryStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DeliveryTest {
    private Delivery createDelivery() {
        Customer customer = new Customer(
                100L,
                "Joãozinho",
                "11900000000",
                "joaozinho@email.com"
        );
        Address address = new Address(
                "Rua das Flores",
                364,
                "Centro",
                "Santo André",
                "SP",
                "09000-000"
        );
        LocalDateTime dateTime = LocalDateTime.now();
        return new Delivery(
                "Teste",
                customer,
                dateTime,
                address,
                "Julia"
        );
    }

    @Test
    public void shouldConfirmDeliveryWhenStatusIsOutForDelivery() {
        Delivery delivery = createDelivery();
        delivery.marksAsOutForDelivery();

        delivery.confirmDelivery();

        Assertions.assertEquals(DeliveryStatus.DELIVERED, delivery.getStatus());
        Assertions.assertNotNull(delivery.getDeliveredAt());
    }

    @Test
    public void shouldNotConfirmDeliveryWhenStatusIsPending() {
        Delivery delivery = createDelivery();

        Assertions.assertThrows(BusinessException.class, delivery::confirmDelivery);
    }

    @Test
    public void shouldNotMarkAsOutForDeliveryWhenStatusIsDelivered() {
        Delivery delivery = createDelivery();

        delivery.marksAsOutForDelivery();
        delivery.confirmDelivery();

        Assertions.assertThrows(BusinessException.class, delivery::marksAsOutForDelivery);
    }

    @Test
    public void shouldNotConfirmDeliveryWhenStatusIsAlreadyDelivered() {
        Delivery delivery = createDelivery();

        delivery.marksAsOutForDelivery();
        delivery.confirmDelivery();

        Assertions.assertThrows(BusinessException.class, delivery::confirmDelivery);
    }

    @Test
    public void shouldNotConfirmDeliveryWhenStatusIsFailed() {
        Delivery delivery = createDelivery();

        delivery.marksAsOutForDelivery();
        delivery.markAsFailed();

        Assertions.assertThrows(BusinessException.class, delivery::confirmDelivery);
    }

}
