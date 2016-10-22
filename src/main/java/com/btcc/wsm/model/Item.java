package com.btcc.wsm.model;

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

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", itemCode='" + itemCode + '\'' +
                ", itemValue='" + itemValue + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", creationTime=" + creationTime +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}
