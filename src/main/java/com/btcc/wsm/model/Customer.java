package com.btcc.wsm.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * The persistent class for the customer database table.
 *
 */
@Entity
@Table(name="customer")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int id;

    @Column(name="account_number")
    private String accountNumber;

    private String address;

    @Lob
    private String capacity;

    @Column(name="chassis_no")
    private String chassisNo;

    private String city;

    private String color;

    private String country;

    @Column(name="created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="creation_time")
    private Date creationTime;

    private String email;

    private String engine;

    @Column(name="home_number")
    private String homeNumber;

    private boolean isDeleted=false;

    @Column(name="mobile_number")
    private String mobileNumber;

    private String name;

    private int postalcode;

    private String state;

    @Column(name="taxid_no")
    private String taxidNo;

    private String term;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="update_time")
    private Date updateTime;

    @Column(name="updated_by")
    private String updatedBy;

    @Column(name="isDeleted", columnDefinition="NUMBER(1)")
    @Type(type="org.hibernate.type.NumericBooleanType")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    public Customer() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCapacity() {
        return this.capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getChassisNo() {
        return this.chassisNo;
    }

    public void setChassisNo(String chassisNo) {
        this.chassisNo = chassisNo;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEngine() {
        return this.engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getHomeNumber() {
        return this.homeNumber;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostalcode() {
        return this.postalcode;
    }

    public void setPostalcode(int postalcode) {
        this.postalcode = postalcode;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTaxidNo() {
        return this.taxidNo;
    }

    public void setTaxidNo(String taxidNo) {
        this.taxidNo = taxidNo;
    }

    public String getTerm() {
        return this.term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
