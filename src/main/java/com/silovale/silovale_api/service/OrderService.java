package com.silovale.silovale_api.service;

import com.silovale.silovale_api.domain.Order;
import com.silovale.silovale_api.domain.Product;
import com.silovale.silovale_api.model.OrderDTO;
import com.silovale.silovale_api.repos.OrderRepository;
import com.silovale.silovale_api.repos.ProductRepository;
import com.silovale.silovale_api.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(final OrderRepository orderRepository,
            final ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("id"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long id) {
        return orderRepository.findById(id)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getId();
    }

    public void update(final Long id, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }

    public void delete(final Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO mapToDTO(final Order order, final OrderDTO orderDTO) {
        orderDTO.setId(order.getId());
        orderDTO.setAmount(order.getAmount());
        orderDTO.setQuantity(order.getQuantity());
        orderDTO.setBuyerId(order.getBuyerId());
        orderDTO.setProductId(order.getProductId() == null ? null : order.getProductId().getId());
        return orderDTO;
    }

    private Order mapToEntity(final OrderDTO orderDTO, final Order order) {
        order.setAmount(orderDTO.getAmount());
        order.setQuantity(orderDTO.getQuantity());
        order.setBuyerId(orderDTO.getBuyerId());
        final Product productId = orderDTO.getProductId() == null ? null : productRepository.findById(orderDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("productId not found"));
        order.setProductId(productId);
        return order;
    }

}
