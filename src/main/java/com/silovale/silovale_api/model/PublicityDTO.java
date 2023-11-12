package com.silovale.silovale_api.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PublicityDTO {
    private Long id;

    @NotNull
    @Size(min = 255)
    private String namePublicity;

    @NotNull
    @Size(min = 255)
    private String descriptionPublicity;

    @NotNull
    @Size(min = 50)
    private String startDate;

    @NotNull
    @Size(min = 50)
    private String endDate;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNamePublicity(){
        return namePublicity;
    }

    public void setNamePublicity(final String namePublicity){
        this.namePublicity = namePublicity;
    }

    public String getDescriptionPublicity(){
        return descriptionPublicity;
    }

    public void setDescriptionPublicity(final String descriptionPublicity){
        this.descriptionPublicity = descriptionPublicity;
    }

    public String getstartDate (){
        return startDate;
    }

    public void setStartDate (final String startDate){
        this.startDate = startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public void setEndDate(final String endDate){
        this.endDate = endDate;
    }
}
