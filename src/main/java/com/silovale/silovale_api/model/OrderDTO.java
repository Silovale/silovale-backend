package com.silovale.silovale_api.model;

import jakarta.persistence.Column;
import org.springframework.data.annotation.CreatedDate;

import java.time.OffsetDateTime;


public class OrderDTO {

    private Long id;

    @Column(nullable =  false)
    private int idOrder;

    @Column(nullable = false)
    private String dateOrder;

    @Column(nullable = false)
    private double total;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;


    public Long getId() {
        return id;
    }

    public int getIdOrder(){
        return idOrder;
    }

    public void setIdOrder(final int idOrder){
        this.idOrder = idOrder;
    }
    
    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(final String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(final double total) {
        this.total = total;
    }

}
