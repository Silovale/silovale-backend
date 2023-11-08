package com.silovale.silovale_api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public class UserDTO {

    private Long id;

    @NotEmpty
    @NotNull
    @Size(max = 255)
    private String email;

    @NotEmpty
    @Size(min = 8)
    private String password;
    
    @Size(max = 255)
    private String dni;

    @Size(max = 255)
    private String ruc;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String lastname;

    @Size(max = 255)
    private String businessName;

    @Size(max = 255)
    private String address;

    private LocalDate registrationDate;

    private Integer userRol;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(final String dni) {
        this.dni = dni;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(final String ruc) {
        this.ruc = ruc;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(final String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(final LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Integer getUserRol() {
        return userRol;
    }

    public void setUserRol(final Integer userRol) {
        this.userRol = userRol;
    }

}
