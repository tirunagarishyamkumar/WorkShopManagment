package com.btcc.wsm.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by siva on 1/10/2016.
 */

@Entity
@Table(name="item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue( strategy=GenerationType.AUTO )
    @Column(name="id", nullable=false, unique=true)
    private int id;

    @Column(name="item_code", nullable=false)
    private String itemCode;

    @Column(name="item_value", nullable=false)
    private String itemValue;

    @Column(name="item_name", nullable=false)
    private String itemName;

    @Column(name="created_by",nullable=true)
    private String createdBy;


    @Column(name="creation_time",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date creationTime;

    @Column(name="updated_by",nullable=true)
    private String lastModifiedBy;

    @Column(name="update_time",nullable=true)
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date lastModifiedTime;

    private boolean isDeleted = false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Column(name="isDeleted", columnDefinition="NUMBER(1)")
    @Type(type="org.hibernate.type.NumericBooleanType")
    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", itemValue='" + itemValue + '\'' +
                ", itemName='" + itemName + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", creationTime=" + creationTime +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
