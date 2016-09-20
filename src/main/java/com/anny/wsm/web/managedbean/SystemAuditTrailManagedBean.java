package com.anny.wsm.web.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;

import com.anny.wsm.model.SystemAuditTrail;
import com.anny.wsm.service.SystemAuditTrailRecordService;
import com.anny.wsm.web.datamodel.SystemAuditTrailDataModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@ViewScoped
public class SystemAuditTrailManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger
			.getLogger(SystemAuditTrailManagedBean.class);

	@Autowired
	SystemAuditTrailRecordService systemAuditTrailRecordService;

	private List<SystemAuditTrail> systemAuditTrailList;
	private SystemAuditTrailDataModel systemAuditTrailDataModel;
	private boolean insertDelete = false;

	public List<SystemAuditTrail> getSystemAuditTrailList() {
		if (systemAuditTrailList == null || insertDelete == true) {
			systemAuditTrailList = systemAuditTrailRecordService.findAll();
		}
		return systemAuditTrailList;
	}

	public void setSystemAuditTrailList(
			List<SystemAuditTrail> systemAuditTrailList) {
		this.systemAuditTrailList = systemAuditTrailList;
	}

	public SystemAuditTrailDataModel getSystemAuditTrailDataModel() {
		return new SystemAuditTrailDataModel(getSystemAuditTrailList());
	}

	public void setSystemAuditTrailDataModel(
			SystemAuditTrailDataModel systemAuditTrailDataModel) {
		this.systemAuditTrailDataModel = systemAuditTrailDataModel;
	}

	public boolean isInsertDelete() {
		return insertDelete;
	}

	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}

}
