package com.btcc.wsm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name="system_parameter")
public class SystemParameter  implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@Column(name="property_name", nullable=false , length=30)
	private String propertyName;
	
	@Lob
	@Column(name="property_value", nullable=false)
	private String propertyValue;    	
	
	@Column(name="description" , nullable=false , length=255)
	private String description; 
	
	@Column(name="created_by",nullable=true)
	private String createdBy;
	
	@Column(name="creation_time",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date creationTime;
	
	@Column(name="updated_by",nullable=true)
	private String lastModifiedBy;
	
	@Column(name="updated_time",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date lastModifiedTime;
	
	private boolean isDeleted = false;
	
    public SystemParameter(){
    	
    }
  
     public int getId() {
 		return id;
 	}

 	public void setId(int id) {
 		this.id = id;
 	}

 	public String getPropertyName() {
 		return propertyName;
 	}

 	public void setPropertyName(String propertyName) {
 		this.propertyName = propertyName;
 	}

 	public String getPropertyValue() {
 		return propertyValue;
 	}

 	public void setPropertyValue(String propertyValue) {
 		this.propertyValue = propertyValue;
 	}
 	
 	 	
 	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
 	
	public String getCreatedBy() {
		return createdBy;
	}
 	
 	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
 	
 	public java.util.Date getCreationTime() {
		return creationTime;
	}
 	
 	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
 	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}
 	
 	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
 	
 	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}
 	
 	
 	@Column(name="isDeleted", columnDefinition="NUMBER(1)") 
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}		
	
 	@Override
	public boolean equals(Object obj) {
		SystemParameter inputSystemParameters = (SystemParameter) obj;
		if(getId() == inputSystemParameters.getId()) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public String toString() {
		return "SystemParameters [propertyName=" + propertyName
				+ ", propertyValue=" + propertyValue + ", description="
				+ description + "]";
	}
	
	
	
		
 }
