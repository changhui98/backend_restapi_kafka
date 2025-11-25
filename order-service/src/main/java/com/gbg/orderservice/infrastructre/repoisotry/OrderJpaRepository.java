package com.gbg.orderservice.infrastructre.repoisotry;

import com.gbg.orderservice.domain.entity.Order;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<Order, UUID> {

}
