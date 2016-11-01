package com.btcc.wsm.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the job_sheet database table.
 * 
 */
@Entity
@Table(name="job_sheet")
@NamedQuery(name="JobSheet.findAll", query="SELECT j FROM JobSheet j")
public class JobSheet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="creation_time")
	private Date creationTime;

	private BigDecimal discount;

	private byte isDeleted;

	@Column(name="job_sheet_number")
	private String jobSheetNumber;

	private String name;

	private BigDecimal netamount;

	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	private Date sheetdate;

	private String status;

	private BigDecimal totalamount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	@Column(name="updated_by")
	private String updatedBy;

	//bi-directional many-to-one association to JobSheetDetail
	@OneToMany(mappedBy="jobSheet")
	private List<JobSheetDetail> jobSheetDetails;

	public JobSheet() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public byte getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getJobSheetNumber() {
		return this.jobSheetNumber;
	}

	public void setJobSheetNumber(String jobSheetNumber) {
		this.jobSheetNumber = jobSheetNumber;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getNetamount() {
		return this.netamount;
	}

	public void setNetamount(BigDecimal netamount) {
		this.netamount = netamount;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getSheetdate() {
		return this.sheetdate;
	}

	public void setSheetdate(Date sheetdate) {
		this.sheetdate = sheetdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalamount() {
		return this.totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
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

	public List<JobSheetDetail> getJobSheetDetails() {
		return this.jobSheetDetails;
	}

	public void setJobSheetDetails(List<JobSheetDetail> jobSheetDetails) {
		this.jobSheetDetails = jobSheetDetails;
	}

	public JobSheetDetail addJobSheetDetail(JobSheetDetail jobSheetDetail) {
		getJobSheetDetails().add(jobSheetDetail);
		jobSheetDetail.setJobSheet(this);

		return jobSheetDetail;
	}

	public JobSheetDetail removeJobSheetDetail(JobSheetDetail jobSheetDetail) {
		getJobSheetDetails().remove(jobSheetDetail);
		jobSheetDetail.setJobSheet(null);

		return jobSheetDetail;
	}

}