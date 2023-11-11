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
    void setUp(){
        MockitoAnnotations.openMocks((this));
        orderService = new OrderService(orderRepository);
    }


    @Test
    void testFindAll(){
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "6 de junio", 1500.30));
        orders.add(new Order(2, "15 de septiembre", 653.78));
        when(orderRepository.findAll(Sort.by("id"))).thenReturn(orders);

        // When
        List<OrderDTO> orderDTOs = orderService.findAll();

        // Then
        assertThat(orderDTOs).isNotEmpty();
        assertThat(orderDTOs).hasSize(orders.size());
        // You can add more specific assertions for the mapping logic if needed
    }


    @Test
    void testCreate(){
        // Given
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setIdOrder(3);
        orderDTO.setDateOrder("12 de febrero");
        orderDTO.setTotal(450.50);
        Order orderToReturnFromRepository = new Order();
        orderToReturnFromRepository.setId((long) 10000);

        when(orderRepository.save(any(Order.class))).thenReturn(orderToReturnFromRepository);


        // When
        Long result = orderService.create(orderDTO);

        // Then
        assertInstanceOf(Long.class, result);
    }


    @Test
    public void testUpdate(){
        // Datos de prueba
        Long orderId = (long) 10000;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setIdOrder(4);
        orderDTO.setDateOrder("21 de agosto");
        orderDTO.setTotal(345.60);

        // Mock del repositorio
        Order existingOrder = new Order();
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        // Llamada al método a probar
        orderService.update(orderId, orderDTO);

        // Verificar
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(existingOrder);

        // Verifica que el método mapToEntity haya sido llamado con los argumentos correctos
        assertEquals(4, existingOrder.getIdOrder());
        assertEquals("21 de agosto", existingOrder.getDateOrder());
    }


    @Test
    public void testDelete(){
        // Datos de prueba
        Long orderId = (long) 10000;

        // Llamar al método a probar 
        orderService.delete(orderId);

        // Verificación
        verify(orderRepository, times(1)).deleteById(orderId);
    }


    @Test
    public void testGet(){
        // Datos prueba
        Long orderId = (long) 10000;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setIdOrder(5);
        orderDTO.setDateOrder("28 de enero");
        orderDTO.setTotal(456.30);

        // Convertir OrderDTO a Order
        Order order = new Order();
        order.setIdOrder(orderDTO.getIdOrder());
        order.setDateOrder(orderDTO.getDateOrder());

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        // Llamada al método a probar 
        OrderDTO result = orderService.get(orderId);

        // Verificación 
        verify(orderRepository, times(1)).findById(orderId);

        // Verifica que el resultado sea el esperado 
        assertEquals(5, result.getIdOrder());
        assertEquals("28 de enero", result.getDateOrder());
    }


    @Test
    public void testGetNotFound(){
        // Datos de prueba
        Long orderId = (long) 10000;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        // LLamada al método a probar que debería lanzar NotfoundExeption
        assertThrows(NotFoundException.class, () -> orderService.get(orderId));
    }
}