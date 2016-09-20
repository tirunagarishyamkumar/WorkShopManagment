package com.btcc.wsm.model;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;

@Entity
@Table(name="users")
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
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="email", nullable=false)	
	private String email;

	
	@Column(name="controlUnit", nullable=false)
	private String controlUnit;
	
	@Column(name="branchNo", nullable=false)
	private String branchNo;
	
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
	@JoinTable(name = "UserToRole" , joinColumns = { @JoinColumn(name = "userId", referencedColumnName = "id" )},
	inverseJoinColumns=
			{@JoinColumn (name = "userRoleId", referencedColumnName = "id" ) } )
	private Set<Role> userRoles = new HashSet<Role>();
	
	
	private boolean enabled=false;
	
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
	

	
	
	public String getControlUnit() {
		return controlUnit;
	}
	public void setControlUnit(String controlUnit) {
		this.controlUnit = controlUnit;
	}
	

	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	

	public Set<Role> getUserRoles() {
		return userRoles;
	}
	
	public void setUserRoles(Set<Role> userRoles) {
		this.userRoles = userRoles;
	}
	
	
}

