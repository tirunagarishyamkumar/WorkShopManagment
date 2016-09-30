package com.btcc.wsm.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
@Table(name="accessLog")
public class AccessLog implements Serializable{	

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue( strategy=GenerationType.AUTO )
	@Column(name="id", nullable=false, unique=true)
	private int accessLogId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="action_date")
	private Date actionDate;
	
	@Column(name="level")
	private String level;
	
	@Column(name="activity")
	private String activity;
	
	@Column(name="description")
	private String description;
	
	@Column(name="actor_user_id")
	private int actorUserId;
	
	@Column(name="actor_username")
	private String actorUsername;
	
	
	private boolean isDeleted = false;   
	   


	public AccessLog(){
    	
    }
	@Column(name="is_deleted", columnDefinition="TINYINT(1)") 
	@Type(type="org.hibernate.type.NumericBooleanType")
	public boolean isDeleted() {
		return isDeleted;
	}
	
	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	
	}
	public int getAccessLogId() {
		return accessLogId;
	}
	
	public void setAccessLogId(int accessLogId) {
		this.accessLogId = accessLogId;
	}
	
	public Date getActionDate() {
		return actionDate;
	}
	
	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setLevel(String level) {
		this.level = level;
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
		
	public int getActorUserId() {
		return actorUserId;
	}
	public void setActorUserId(int actorUserId) {
		this.actorUserId = actorUserId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getActorUsername() {
		return actorUsername;
	}
	
	public void setActorUsername(String actorUsername) {
		this.actorUsername = actorUsername;
	}
	
	
	static DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	
	public String getFormatedDate(){
		return format.format(actionDate);
	}		
		
	
	
	
	public String toString() {
		return "Agents [accessLogId=" + accessLogId + ", actionDate=" + actionDate
				+ ", level=" + level + ", activity="
				+ activity + ", description=" + description + ", actorUserId="
				+ actorUserId + ", actorUsername=" + actorUsername + "," + "]";
	}
	
	
}



