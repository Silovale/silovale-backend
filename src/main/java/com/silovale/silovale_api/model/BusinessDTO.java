package com.silovale.silovale_api.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class BusinessDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String businessName;

    @Size(max = 255)
    private String description;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String phone;

    @NotNull
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(final String businessName) {
        this.businessName = businessName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(final Long userId) {
        this.userId = userId;
    }

}
