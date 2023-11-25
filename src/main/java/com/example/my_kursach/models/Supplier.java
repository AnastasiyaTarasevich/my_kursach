package com.example.my_kursach.models;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Collection;


@Entity
public class Supplier {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "idsupplier")
    private int idsupplier;
    @Basic
    @Column(name = "company_name")
    private String companyName;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "contact_data")
    private String contactData;
    @Basic
    @Column(name = "rating")
    private Integer rating;
    @Basic
    @Column(name = "id_user")
    private Integer idUser;
//    @OneToOne(cascade = CascadeType.REMOVE, mappedBy = "supplier")
//    private User user;
    public int getIdsupplier() {
        return idsupplier;
    }

    public void setIdsupplier(int idsupplier) {
        this.idsupplier = idsupplier;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactData() {
        return contactData;
    }

    public void setContactData(String contactData) {
        this.contactData = contactData;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }


}
