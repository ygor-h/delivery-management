package com.ygorhenrique.delivery_management.domain.exception;

public class DeliveryNotFoundException extends RuntimeException {
    public DeliveryNotFoundException(Long id) {
        super("Entrega não encontrada com ID: "+id);
    }
}
