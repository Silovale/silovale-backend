package com.silovale.silovale_api.repos;

import com.silovale.silovale_api.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {
}
