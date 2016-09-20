package com.anny.wsm.web.managedbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agrobank.bft.app.context.FacesUtil;
import com.agrobank.bft.constants.SystemParametersConstants;
import com.agrobank.bft.datamodel.SystemParametersDataModel;
import com.agrobank.bft.model.SystemParameters;
import com.agrobank.bft.model.Users;
import com.agrobank.bft.quartzscheduler.HouseKeepingQuartzScheduler;
import com.agrobank.bft.systemparameters.service.SystemParametersService;
import com.agrobank.bft.user.audit.logging.SystemAuditTrailActivity;
import com.agrobank.bft.user.audit.logging.SystemAuditTrailLevel;
import com.agrobank.bft.user.audit.logging.SystemAuditTrailRecordService;
import com.agrobank.bft.users.service.UsersService;
import com.agrobank.bft.util.BFTException;

@Component
@ViewScoped
public class SystemParameterManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsersService usersService;
	@Autowired
	SystemParametersService systemParametersService;
	final static Logger logger = Logger
			.getLogger(SystemParameterManagedBean.class);

	private List<SystemParameters> systemParamatersList;
	private SystemParametersDataModel systemParametersDataModel;
	private SystemParameters newSystemParameters = new SystemParameters();
	private SystemParameters selectedSystemParameters = new SystemParameters();
	private boolean insertDelete = false;
	private Users loggedInUser;

	@Autowired
	private SystemAuditTrailRecordService systemAuditTrailRecordService;

	public SystemParametersService getSystemParametersService() {
		return systemParametersService;
	}

	public void setSystemParametersService(
			SystemParametersService systemParametersService) {
		this.systemParametersService = systemParametersService;
	}

	public SystemParameters getNewSystemParameters() {
		return newSystemParameters;
	}

	public void setNewSystemParameters(SystemParameters newSystemParameters) {
		this.newSystemParameters = newSystemParameters;
	}

	public Users getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public SystemParametersDataModel getSystemParametersDataModel() {

		return new SystemParametersDataModel(getSystemParamatersList());
	}

	public void setSystemParametersDataModel(
			SystemParametersDataModel systemParametersDataModel) {
		this.systemParametersDataModel = systemParametersDataModel;
	}

	public boolean isInsertDelete() {
		return insertDelete;
	}

	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}

	public List<SystemParameters> getSystemParamatersList() {

		if (systemParamatersList == null) {
			systemParamatersList = systemParametersService.findAll();
		}

		return systemParamatersList;
	}

	public void setSystemParamatersList(
			List<SystemParameters> systemParamatersList) {
		this.systemParamatersList = systemParamatersList;
	}

	public SystemParameters getSelectedSystemParameters() {
		return selectedSystemParameters;
	}

	public void setSelectedSystemParameters(
			SystemParameters selectedSystemParameters) {
		this.selectedSystemParameters = selectedSystemParameters;
	}

	public SystemAuditTrailRecordService getSystemAuditTrailRecordService() {
		return systemAuditTrailRecordService;
	}

	public void setSystemAuditTrailRecordService(
			SystemAuditTrailRecordService systemAuditTrailRecordService) {
		this.systemAuditTrailRecordService = systemAuditTrailRecordService;
	}

	public void doCreateSystemParameter() {
		if (getSystemParametersService().checkPropertyName(
				newSystemParameters.getPropertyName())) {
			System.out.println("PropetyName already Exist");
			FacesMessage msg = new FacesMessage("FAILURE : SystemParameters "
					+ newSystemParameters.getPropertyName() + " Already Exists");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else {

			try {
				logger.info("SystemParameterManagedBean.doCreateSystemParameter is creating property name: "
						+ newSystemParameters.getPropertyName()
						+ "and propertyValue"
						+ newSystemParameters.getPropertyValue());
				loggedInUser = (Users) FacesUtil
						.getSessionMapValue("LOGGEDIN_USER");
				newSystemParameters.setDeleted(false);
				newSystemParameters.setCreatedBy(getLoggedInUser()
						.getUsername());
				newSystemParameters.setCreationTime(new java.util.Date());
				getSystemParametersService().create(newSystemParameters);
				setInsertDelete(true);
				systemAuditTrailRecordService.log(
						SystemAuditTrailActivity.CREATED,
						SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
						getLoggedInUser().getUsername(), getLoggedInUser()
								.getUsername()
								+ " has created new System Paramenter "
								+ newSystemParameters.getPropertyName());
				// schedule house keeping archive
				String propertyName = newSystemParameters.getPropertyName();
				if (propertyName
						.equals(SystemParametersConstants.HOUSE_KEEPING_PERIOD)) {
					HouseKeepingQuartzScheduler
							.scheduleQuartzJob(newSystemParameters);
				}
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										"SUCCESS : New System Parameter record created"));
				newSystemParameters = new SystemParameters();
				if (systemParamatersList == null || insertDelete == true) {
					systemParamatersList = systemParametersService.findAll();
				}

			} catch (BFTException e) {

				logger.error(
						"SystemParameterManagedBean.doCreateSystemParameter: Error while creating property name: "
								+ newSystemParameters.getPropertyName()
								+ "and propertyValue"
								+ newSystemParameters.getPropertyValue(), e);
				FacesMessage msg = new FacesMessage(
						"Error in creating  propertyName:"
								+ newSystemParameters.getPropertyName()
								+ "and propertyValue"
								+ newSystemParameters.getPropertyValue());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public void doUpdateSystemParameter() {
		if (getSystemParametersService().checkPropertyNameWithId(
				selectedSystemParameters.getPropertyName(),
				selectedSystemParameters.getId())) {
			FacesMessage msg = new FacesMessage("FAILURE : SystemParameters "
					+ selectedSystemParameters.getPropertyName()
					+ "  Already Exists");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;

		} else {
			try {
				logger.info("SystemParameterManagedBean.doUpdateSystemParameter is updating  property name: "
						+ selectedSystemParameters.getPropertyName()
						+ "and propertyValue"
						+ selectedSystemParameters.getPropertyValue());
				loggedInUser = (Users) FacesUtil
						.getSessionMapValue("LOGGEDIN_USER");
				selectedSystemParameters.setLastModifiedBy(getLoggedInUser()
						.getUsername());
				systemParametersService.update(selectedSystemParameters);
				setInsertDelete(true);
				if (systemParamatersList == null || insertDelete == true) {
					systemParamatersList = systemParametersService.findAll();
				}
				systemAuditTrailRecordService.log(
						SystemAuditTrailActivity.UPDATED,
						SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
						getLoggedInUser().getUsername(), getLoggedInUser()
								.getUsername()
								+ " has updated System Paramenter "
								+ selectedSystemParameters.getPropertyName());
				String propertyName = selectedSystemParameters
						.getPropertyName();
				if (propertyName
						.equals(SystemParametersConstants.HOUSE_KEEPING_PERIOD)) {
					HouseKeepingQuartzScheduler
							.reScheduleQuartzJob(selectedSystemParameters);
				}
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("SUCCESS : SystemParameters "
								+ selectedSystemParameters.getPropertyName()
								+ " has been updated "));
			} catch (BFTException e) {
				logger.error(
						"SystemParameterManagedBean.doUpdateSystemParameter:Error while Updating property name: "
								+ selectedSystemParameters.getPropertyName()
								+ "and propertyValue"
								+ selectedSystemParameters.getPropertyValue(),
						e);
				FacesMessage msg = new FacesMessage(
						"Error in Updating : SystemParameters "
								+ selectedSystemParameters.getPropertyName());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		}
	}

	public void doDeleteSystemParameter() {
		try {
			logger.info("SystemParameterManagedBean.doDeleteSystemParameter is deleting  property name: "
					+ selectedSystemParameters.getPropertyName()
					+ "and propertyValue"
					+ selectedSystemParameters.getPropertyValue());
			loggedInUser = (Users) FacesUtil
					.getSessionMapValue("LOGGEDIN_USER");
			getSystemParametersService().delete(
					selectedSystemParameters.getId());
			setInsertDelete(true);
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.DELETED,
					SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
					getLoggedInUser().getUsername(), getLoggedInUser()
							.getUsername()
							+ " has deleted System Paramenter "
							+ selectedSystemParameters.getPropertyName());
			String propertyName = selectedSystemParameters.getPropertyName();
			if (propertyName
					.equals(SystemParametersConstants.HOUSE_KEEPING_PERIOD)) {
				HouseKeepingQuartzScheduler
						.unScheduleQuartzJob(selectedSystemParameters);
			}
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"SUCCESS : System Parameter record deleted"));
			if (systemParamatersList == null || insertDelete == true) {
				systemParamatersList = systemParametersService.findAll();
			}
		} catch (BFTException e) {
			logger.error(
					"SystemParameterManagedBean.doDeleteSystemParameter:Error while Deleting property name: "
							+ selectedSystemParameters.getPropertyName()
							+ "and propertyValue"
							+ selectedSystemParameters.getPropertyValue(), e);
			FacesMessage msg = new FacesMessage(
					"Error in deleting : SystemParameters "
							+ selectedSystemParameters.getPropertyName());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	void checkBeforeUpdate() {
		loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
		HashSet<String> accessRights = usersService
				.getAccessRightsMapForUser(getLoggedInUser().getUsername());
		if (accessRights.contains("SY01E") || accessRights.contains("SY01D")) {
			if ((getLoggedInUser().getUsername().equalsIgnoreCase("system"))
					&& selectedSystemParameters.getCreatedBy()
							.equalsIgnoreCase("system")) {
				RequestContext.getCurrentInstance().execute(
						"PF('systemParameterDialog').show()");
			} else {
				if (!(getLoggedInUser().getUsername()
						.equalsIgnoreCase("system"))
						&& !(selectedSystemParameters.getCreatedBy()
								.equalsIgnoreCase("system"))) {
					RequestContext.getCurrentInstance().execute(
							"PF('systemParameterDialog').show()");
				} else {
					RequestContext.getCurrentInstance().execute(
							"PF('systemParameterDialog').hide()");
					FacesMessage msg = new FacesMessage(
							"You Dont have Rights to Update this Record");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return;
				}
			}
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('systemParameterDialog').hide()");
			FacesMessage msg = new FacesMessage(
					"You Dont have Rights to Update this Record");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

	}

	public void onRowSelect(SelectEvent event) {
		setSelectedSystemParameters((SystemParameters) event.getObject());
		checkBeforeUpdate();
	}

	public void showDailogue() {

		RequestContext.getCurrentInstance().execute(
				"PF('newSystemParametersDialog').show()");
		return;

	}

}
