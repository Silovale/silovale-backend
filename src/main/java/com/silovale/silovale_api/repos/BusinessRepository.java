package com.silovale.silovale_api.repos;

import com.silovale.silovale_api.domain.Business;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BusinessRepository extends JpaRepository<Business, Long> {
}
