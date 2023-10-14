package com.silovale.silovale_api.model;

import jakarta.validation.constraints.NotNull;


public class OrderDTO {

    private Long id;

    @NotNull
    private Integer amount;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer buyerId;

    @NotNull
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(final Integer amount) {
        this.amount = amount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(final Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(final Long productId) {
        this.productId = productId;
    }

}
