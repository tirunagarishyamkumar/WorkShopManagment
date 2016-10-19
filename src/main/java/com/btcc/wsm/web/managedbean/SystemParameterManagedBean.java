package com.btcc.wsm.web.managedbean;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import com.btcc.wsm.model.SystemParameter;
import com.btcc.wsm.model.Users;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.service.SystemParameterService;
import com.btcc.wsm.service.UsersService;
import com.btcc.wsm.util.FacesUtil;
import com.btcc.wsm.util.SystemAuditTrailActivity;
import com.btcc.wsm.util.SystemAuditTrailLevel;
import com.btcc.wsm.util.WSMException;
import com.btcc.wsm.web.datamodel.SystemParametersDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@ViewScoped
public class SystemParameterManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UsersService usersService;
	@Autowired
	SystemParameterService systemParametersService;
	final static Logger logger = LogManager
			.getLogger(SystemParameterManagedBean.class);

	private List<SystemParameter> systemParametersList;
	private SystemParametersDataModel systemParametersDataModel;
	private SystemParameter newSystemParameters = new SystemParameter();
	private SystemParameter selectedSystemParameters = new SystemParameter();
	private boolean insertDelete = false;
	private Users loggedInUser;

	@Autowired
	private SystemAuditTrailRecordService systemAuditTrailRecordService;

	public SystemParameterService getSystemParametersService() {
		return systemParametersService;
	}

	public void setSystemParametersService(
			SystemParameterService systemParametersService) {
		this.systemParametersService = systemParametersService;
	}

	public SystemParameter getNewSystemParameters() {
		return newSystemParameters;
	}

	public void setNewSystemParameters(SystemParameter newSystemParameters) {
		this.newSystemParameters = newSystemParameters;
	}

	public Users getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public SystemParametersDataModel getSystemParametersDataModel() {

		return new SystemParametersDataModel(getSystemParametersList());
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

	public List<SystemParameter> getSystemParametersList() {

		if (systemParametersList == null) {
			systemParametersList = systemParametersService.findAll();
		}

		return systemParametersList;
	}

	public void setSystemParametersList(
			List<SystemParameter> systemParamatersList) {
		this.systemParametersList = systemParamatersList;
	}

	public SystemParameter getSelectedSystemParameters() {
		return selectedSystemParameters;
	}

	public void setSelectedSystemParameters(
			SystemParameter selectedSystemParameters) {
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

				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(
										"SUCCESS : New System Parameter record created"));
				newSystemParameters = new SystemParameter();
				if (systemParametersList == null || insertDelete == true) {
					systemParametersList = systemParametersService.findAll();
				}

			} catch (WSMException e) {

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

	public void doUpdateSystemParameter() {

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
				if (systemParametersList == null || insertDelete == true) {
					systemParametersList = systemParametersService.findAll();
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

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("SUCCESS : SystemParameters "
								+ selectedSystemParameters.getPropertyName()
								+ " has been updated "));
			} catch (WSMException e) {
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

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(
							"SUCCESS : System Parameter record deleted"));
			if (systemParametersList == null || insertDelete == true) {
				systemParametersList = systemParametersService.findAll();
			}
		} catch (WSMException e) {
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


	public void onRowSelect(SelectEvent event) {
		setSelectedSystemParameters((SystemParameter) event.getObject());

	}

	public void showDialogue() {

		RequestContext.getCurrentInstance().execute(
				"PF('newSystemParametersDialog').show()");
		return;

	}

}
