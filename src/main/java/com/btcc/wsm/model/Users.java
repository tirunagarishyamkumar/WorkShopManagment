package com.btcc.wsm.model;


import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
public class Users implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@Column(name="username", nullable=false,unique=true)
	private String username;
	
	@Column(name="password", nullable=true)
	private String password;
	
	@Column(name="fullname", nullable=false)
	private String name;
	
	@Column(name="email", nullable=false)	
	private String email;

	
	/*@Column(name="controlUnit", nullable=false)
	private String controlUnit;*/
	
	/*@Column(name="branchNo", nullable=false)
	private String branchNo;*/
	
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
	@JoinTable(name = "user_role" , joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id" )},
	inverseJoinColumns=
			{@JoinColumn (name = "role_id", referencedColumnName = "id" ) } )
	private Set<Role> userRoles = new HashSet<Role>();
	
	
	private boolean enabled=false;
	private boolean active=true;
	
	public Users(){
		
	}
	
	@Column(name="enabled", columnDefinition="TINYINT(1)") 
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	

	
	
	/*public String getControlUnit() {
		return controlUnit;
	}
	public void setControlUnit(String controlUnit) {
		this.controlUnit = controlUnit;
	}*/
	

	/*public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}*/
	

	public Set<Role> getUserRoles() {
		return userRoles;
	}
	
	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}

	@Column(name = "isDeleted", columnDefinition = "NUMBER(1)")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}

