package com.ygorhenrique.delivery_management.repository;

import com.ygorhenrique.delivery_management.domain.model.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
