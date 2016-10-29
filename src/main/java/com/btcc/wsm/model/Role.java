package com.btcc.wsm.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="role")
public class Role implements Serializable{

	 /* */
	private static final long serialVersionUID = 1L;
	//role
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@Column(name="role", nullable=false)
	private String role;

	
	@Column(name="description", nullable=true)
	private String description;
	
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
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "role_access_right" , joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id" )},
	inverseJoinColumns=
			{@JoinColumn (name = "access_right_id", referencedColumnName = "id" ) } )
	private Set<AccessRights> accessRights = new HashSet<AccessRights>();
	 
	
	private boolean isDeleted= false;
	private boolean active=true;
	
	public Role(){
		
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
	
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}

 
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setLastModifiedTime(java.util.Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public java.util.Date getCreationTime() {
		return creationTime;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public java.util.Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}


	public Set<AccessRights> getAccessRights() {
		return accessRights;
	}


	public void setAccessRights(Set<AccessRights> accessRights) {
		this.accessRights = accessRights;
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
		Role inputRole = (Role) obj;
		if(getId() == inputRole.getId()) {
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
