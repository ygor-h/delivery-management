package com.ygorhenrique.delivery_management.domain.model.delivery;

import com.ygorhenrique.delivery_management.domain.model.address.Address;
import com.ygorhenrique.delivery_management.domain.model.customer.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@NoArgsConstructor
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "scheduled_time",nullable = false)
    private LocalDateTime scheduledAt;
    @Embedded
    private Address address;
    @Column(nullable = false)
    private String receiver;
    @Column(nullable = false)
    @Setter(AccessLevel.PACKAGE)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if(this.status == null) {
            this.status = DeliveryStatus.PENDING;
        }
    }
}
