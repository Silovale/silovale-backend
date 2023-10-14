package com.silovale.silovale_api.repos;

import com.silovale.silovale_api.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {
}
