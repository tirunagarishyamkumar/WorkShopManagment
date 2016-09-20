package com.btcc.wsm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Type;


@Entity
@Table(name="SystemAuditTrail")
public class SystemAuditTrail implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	@Column(name="id", nullable=false, unique=true)
	private int id;
	
	@Column(name="actionDate", nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	
	@Column(name="actorUserId", nullable=false)
	private int actorUserId;
	
	@Column(name="actorUsername", nullable=false)
	private String actorUsername;
	
	@Column(name="logLevel", nullable=false)
	private String logLevel;
	
	@Column(name="activity", nullable=false)
	private String activity;
	
	@Column(name="description",nullable=false)
	private String description;
	
	private boolean isDeleted = false;
	
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
	
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	

	public int getActorUserId() {
		return actorUserId;
	}
	public void setActorUserId(int actorUserId) {
		this.actorUserId = actorUserId;
	}
	
	
	public String getActorUsername() {
		return actorUsername;
	}
	public void setActorUsername(String actorUsername) {
		this.actorUsername = actorUsername;
	}
	

	public String getLogLevel() {
		return logLevel;
	}
	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
	

	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((activity == null) ? 0 : activity.hashCode());
		result = prime * result + actorUserId;
		result = prime * result
				+ ((actorUsername == null) ? 0 : actorUsername.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + (isDeleted ? 1231 : 1237);
		result = prime * result
				+ ((logLevel == null) ? 0 : logLevel.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemAuditTrail other = (SystemAuditTrail) obj;
		if (activity == null) {
			if (other.activity != null)
				return false;
		} else if (!activity.equals(other.activity))
			return false;
		if (actorUserId != other.actorUserId)
			return false;
		if (actorUsername == null) {
			if (other.actorUsername != null)
				return false;
		} else if (!actorUsername.equals(other.actorUsername))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (isDeleted != other.isDeleted)
			return false;
		if (logLevel == null) {
			if (other.logLevel != null)
				return false;
		} else if (!logLevel.equals(other.logLevel))
			return false;
		return true;
	}
	

	
	

}
