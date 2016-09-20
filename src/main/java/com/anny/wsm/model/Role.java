package com.anny.wsm.model;



import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


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
	
	@Column(name="createdBy",nullable=true)
	private String createdBy;
	
	
	@Column(name="creationTime",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date creationTime;
	
	@Column(name="lastModifiedBy",nullable=true)
	private String lastModifiedBy;
	
	@Column(name="lastModifiedTime",nullable=true)
	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date lastModifiedTime;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "RoleToAccessRights" , joinColumns = { @JoinColumn(name = "roleId", referencedColumnName = "id" )},
	inverseJoinColumns=
			{@JoinColumn (name = "accessRightsId", referencedColumnName = "id" ) } )
	private Set<AccessRights> accessRights = new HashSet<AccessRights>();
	 
	
	private boolean isDeleted= false;
	
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
