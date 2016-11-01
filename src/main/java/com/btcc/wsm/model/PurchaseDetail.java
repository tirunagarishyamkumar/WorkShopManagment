package com.btcc.wsm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the purchase_detail database table.
 * 
 */
@Entity
@Table(name="purchase_detail")
@NamedQuery(name="PurchaseDetail.findAll", query="SELECT p FROM PurchaseDetail p")
public class PurchaseDetail implements Serializable {
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

	//bi-directional many-to-one association to Purchase
	@ManyToOne
	private Purchase purchase;

	public PurchaseDetail() {
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

	public Purchase getPurchase() {
		return this.purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}

}