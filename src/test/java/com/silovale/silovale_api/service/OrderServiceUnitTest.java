package com.silovale.silovale_api.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import com.silovale.silovale_api.domain.Order;
import com.silovale.silovale_api.model.OrderDTO;
import com.silovale.silovale_api.repos.OrderRepository;
import com.silovale.silovale_api.util.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceUnitTest {
    
    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }
}
