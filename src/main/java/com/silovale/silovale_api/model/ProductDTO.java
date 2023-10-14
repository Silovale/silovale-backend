package com.silovale.silovale_api.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class ProductDTO {

    private Long id;

    @NotNull
    private Double price;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    @Size(max = 255)
    private String category;

    @Size(max = 255)
    private String image;

    @NotNull
    private Long businessId;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(final String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(final Long businessId) {
        this.businessId = businessId;
    }

}
