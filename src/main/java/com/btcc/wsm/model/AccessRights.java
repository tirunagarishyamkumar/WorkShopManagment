package com.btcc.wsm.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="access_right")
public class AccessRights implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@Column(name="name", nullable=false , length=45)
	private String accessRights;
	
	@Column(name="description" , nullable=false , length=255)
	private String description; 
	
	@Column(name="created_by",nullable=true)
	private String createdBy;
	
	@Column(name="creation_time",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationTime;
	
	@Column(name="updated_by",nullable=true)
	private String lastModifiedBy;
	
	@Column(name="update_time",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedTime;
	
	private boolean isDeleted = false;
	private boolean active=true;
	   

  /*  public AccessRights(int id, String accessRights, String description,
			String createdBy, Date creationTime, String lastModifiedBy,
			Date lastModifiedTime, boolean isDeleted) {
		super();
		this.id = id;
		this.accessRights = accessRights;
		this.description = description;
		this.createdBy = createdBy;
		this.creationTime = creationTime;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedTime = lastModifiedTime;
		this.isDeleted = isDeleted;
	}*/

	public AccessRights(){
    	
    }
	
	@Column(name="isDeleted", columnDefinition="NUMBER(1)") 
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}		
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	
	
	public String getAccessRights() {
		return accessRights;
	}
	
	public void setAccessRights(String accessRights) {
		this.accessRights = accessRights;
	}
	
	
	public String getDescription() {
		return description;
	}
	

	public void setDescription(String description) {
		this.description = description;
	}
	
	

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
			

	public Date getCreationTime() {
		return creationTime;
	}
	
	public String getCreatedBy() {
		return createdBy;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}
	
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}


	@Column(name = "isDeleted", columnDefinition = "NUMBER(1)")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public boolean equals(Object obj) {
		AccessRights inputAccessRights = (AccessRights) obj;
		if(getId() == inputAccessRights.getId()) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public int hashCode() {
		return id;
	}
		
	 
}

