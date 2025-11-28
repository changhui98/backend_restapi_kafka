package com.gbg.orderservice.infrastructure.repoisotry;

import com.gbg.orderservice.domain.entity.Order;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserId(UUID userId);

    @Query("SELECT o from Order o join fetch o.items where o.orderId = :uuid")
    Optional<Order> findWithItemsById(UUID uuid);
}
