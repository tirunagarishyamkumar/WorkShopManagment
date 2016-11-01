package com.btcc.wsm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the job_sheet_detail database table.
 * 
 */
@Entity
@Table(name="job_sheet_detail")
@NamedQuery(name="JobSheetDetail.findAll", query="SELECT j FROM JobSheetDetail j")
public class JobSheetDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private BigDecimal amount;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_time")
	private Date creationTime;

	private byte isDeleted;

	@Column(name="item_id")
	private int itemId;

	private BigDecimal price;

	private int qty;

	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="user_id")
	private int userId;

	//bi-directional many-to-one association to JobSheet
	@ManyToOne
	@JoinColumn(name="job_sheet_id")
	private JobSheet jobSheet;

	public JobSheetDetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationTime() {
		return this.creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getItemId() {
		return this.itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQty() {
		return this.qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public JobSheet getJobSheet() {
		return this.jobSheet;
	}

	public void setJobSheet(JobSheet jobSheet) {
		this.jobSheet = jobSheet;
	}

}