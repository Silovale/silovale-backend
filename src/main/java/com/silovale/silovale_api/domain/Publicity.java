package com.silovale.silovale_api.domain;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Publicity {
    
    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String namePublicity;

    @Column(nullable = false)
    private String descriptionPublicity;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Publicity (String namePublicity, String descriptionPublicity, String startDate, String endDate){
        this.namePublicity = namePublicity;
        this.descriptionPublicity = descriptionPublicity;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Publicity (){

    }

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

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}