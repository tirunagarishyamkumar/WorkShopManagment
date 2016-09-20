package com.anny.wsm.web.managedbean;


import com.anny.wsm.model.AccessRights;
import com.anny.wsm.model.Users;
import com.anny.wsm.service.AccessRightsService;
import com.anny.wsm.service.SystemAuditTrailRecordService;
import com.anny.wsm.service.UsersService;
import com.anny.wsm.util.FacesUtil;
import com.anny.wsm.util.SystemAuditTrailActivity;
import com.anny.wsm.util.SystemAuditTrailLevel;
import com.anny.wsm.util.WSMException;
import com.anny.wsm.web.datamodel.AccessRightsDataModel;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

@Component
@ViewScoped
public class AccessRightsManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger
			.getLogger(AccessRightsManagedBean.class);

	@Autowired
	private UsersService usersService;

	@Autowired
	AccessRightsService accessRightsService;

	private List<AccessRights> accessRightsList;
	private AccessRightsDataModel accessRightsDataModel;
	private AccessRights newAccessRights = new AccessRights();
	private AccessRights selectedAccessRights = new AccessRights();
	private boolean insertDelete = false;
	private List<AccessRights> searchAccessRights;

	private String searchAccessRight = "";

	private Users loggedInUser;

	@Autowired
	private SystemAuditTrailRecordService systemAuditTrailRecordService;

	public AccessRightsService getAccessRightsService() {
		return accessRightsService;
	}

	public void setAccessRightsService(AccessRightsService accessRightsService) {
		this.accessRightsService = accessRightsService;
	}

	public List<AccessRights> getAccessRightsList() {

		if (accessRightsList == null) {

			accessRightsList = accessRightsService.findAll();
		}
		return accessRightsList;
	}

	public void setAccessRightsList(List<AccessRights> accessRightsList) {
		this.accessRightsList = accessRightsList;
	}

	public AccessRightsDataModel getAccessRightsDataModel() {
		return new AccessRightsDataModel(getAccessRightsList());
	}

	public void setAccessRightsDataModel(
			AccessRightsDataModel accessRightsDataModel) {
		this.accessRightsDataModel = accessRightsDataModel;
	}

	public AccessRights getNewAccessRights() {
		return newAccessRights;
	}

	public void setNewAccessRights(AccessRights newAccessRights) {
		this.newAccessRights = newAccessRights;
	}

	public void doCreateAccessRights() {
		System.out.println("AccessRight Create Method Called");
		if (getAccessRightsService().checkAccessRights(
				newAccessRights.getAccessRights())) {
			System.out.println("AccessRight  already Exist");
			FacesMessage msg = new FacesMessage("FAILURE : Access Right "
					+ newAccessRights.getAccessRights() + " Already Exists");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else {
			try {
				logger.info("AccessRightsManagedBean.doCreateAccessRights is creating AccessRight in database "
						+ newAccessRights.getAccessRights());

				loggedInUser = (Users) FacesUtil
						.getSessionMapValue("LOGGEDIN_USER");

				newAccessRights.setDeleted(false);
				newAccessRights.setCreatedBy(getLoggedInUser().getUsername());
				newAccessRights.setCreationTime(new java.util.Date());
				getAccessRightsService().create(newAccessRights);
				setInsertDelete(true);
				if (accessRightsList == null || insertDelete == true) {
					accessRightsList = accessRightsService.findAll();
				}
				systemAuditTrailRecordService.log(
						SystemAuditTrailActivity.CREATED,
						SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
						getLoggedInUser().getUsername(), getLoggedInUser()
								.getUsername()
								+ " has created new AccessRight "
								+ newAccessRights.getAccessRights());

				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("SUCCESS : Access Right  "
								+ newAccessRights.getAccessRights()
								+ "  has been created."));
				newAccessRights = new AccessRights();
				// new Refresh().refreshPage();
			} catch (WSMException e) {
				logger.error(
						"AccessRightsManagedBean.doCreateAccessRights: Error while creating Role: "
								+ newAccessRights.getAccessRights(), e);
				FacesMessage msg = new FacesMessage(
						"Error in creating  : AccessRight "
								+ newAccessRights.getAccessRights());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public AccessRights getSelectedAccessRights() {
		return selectedAccessRights;
	}

	public void setSelectedAccessRights(AccessRights selectedAccessRights) {
		this.selectedAccessRights = selectedAccessRights;
	}

	public void doUpdateAccessRights() {
		if (getAccessRightsService().findAccessRightsByNameById(
				selectedAccessRights.getAccessRights(),
				selectedAccessRights.getId())) {
			System.out.println("AccessRight  already Exist");
			FacesMessage msg = new FacesMessage("FAILURE : Access Right "
					+ selectedAccessRights.getAccessRights()
					+ "  already Exists");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		} else {

			try {
				logger.info("AccessRightsManagedBean.doUpdateAccessRights is updating  AccessRight in database "
						+ selectedAccessRights.getAccessRights());

				loggedInUser = (Users) FacesUtil
						.getSessionMapValue("LOGGEDIN_USER");

				System.out.println("New UserAccess:"
						+ selectedAccessRights.getAccessRights());
				System.out.println("Id:" + selectedAccessRights.getId());
				System.out.println("Description:"
						+ selectedAccessRights.getDescription());
				selectedAccessRights.setLastModifiedBy(getLoggedInUser()
						.getUsername());
				accessRightsService.update(selectedAccessRights);
				setInsertDelete(true);
				if (accessRightsList == null || insertDelete == true) {
					accessRightsList = accessRightsService.findAll();
				}
				systemAuditTrailRecordService.log(
						SystemAuditTrailActivity.UPDATED,
						SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
						getLoggedInUser().getUsername(), getLoggedInUser()
								.getUsername()
								+ " has updated AccessRight "
								+ selectedAccessRights.getAccessRights());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("SUCCESS : Access Right "
								+ selectedAccessRights.getAccessRights()
								+ " has been updated."));
			} catch (WSMException e) {
				logger.error(
						"AccessRightsManagedBean.doUpdateAccessRights: Error while updating AccessRight: "
								+ selectedAccessRights.getAccessRights(), e);
				FacesMessage msg = new FacesMessage(
						"Error in upating : AccessRight "
								+ selectedAccessRights.getAccessRights());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	void checkBeforeUpdate() {
		loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
		HashSet<String> accessRights = usersService
				.getAccessRightsMapForUser(getLoggedInUser().getUsername());
		if (accessRights.contains("SY0403E")
				|| accessRights.contains("SY0403D")) {
			if ((getLoggedInUser().getUsername().equalsIgnoreCase("system"))
					&& selectedAccessRights.getCreatedBy().equalsIgnoreCase(
							"system")) {
				RequestContext.getCurrentInstance().execute(
						"PF('accessRightsDialog').show()");
			} else {
				if (!(getLoggedInUser().getUsername()
						.equalsIgnoreCase("system"))
						&& !(selectedAccessRights.getCreatedBy()
								.equalsIgnoreCase("system"))) {
					RequestContext.getCurrentInstance().execute(
							"PF('accessRightsDialog').show()");
				} else {
					RequestContext.getCurrentInstance().execute(
							"PF('accessRightsDialog').hide()");
					FacesMessage msg = new FacesMessage(
							"You Dont have Rights to Update this Record");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return;
				}
			}
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('accessRightsDialog').hide()");
			FacesMessage msg = new FacesMessage(
					"You Dont have Rights to Update this Record");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

	}

	public void onRowSelect(SelectEvent event) {
		setSelectedAccessRights((AccessRights) event.getObject());
		checkBeforeUpdate();
	}

	public void doDeleteAccessRights() {
		try {
			logger.info("AccessRightsManagedBean.doDeleteAccessRights is deleting  AccessRight from database "
					+ selectedAccessRights.getAccessRights());

			loggedInUser = (Users) FacesUtil
					.getSessionMapValue("LOGGEDIN_USER");
			getAccessRightsService().delete(selectedAccessRights.getId());
			setInsertDelete(true);
			if (accessRightsList == null || insertDelete == true) {
				accessRightsList = accessRightsService.findAll();
			}
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.DELETED,
					SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
					getLoggedInUser().getUsername(), getLoggedInUser()
							.getUsername()
							+ " has deleted AccessRight "
							+ selectedAccessRights.getAccessRights());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("SUCCESS : Access Right "
							+ selectedAccessRights.getAccessRights()
							+ " has been deleted."));
			// new Refresh().refreshPage();
		} catch (WSMException e) {
			logger.error(
					"AccessRightsManagedBean.doDeleteAccessRights: Error while deleting  AccessRight from database: "
							+ selectedAccessRights.getAccessRights(), e);
			FacesMessage msg = new FacesMessage(
					"Error in deleting : AccessRight "
							+ selectedAccessRights.getAccessRights());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);

		}
	}

	public boolean isInsertDelete() {
		return insertDelete;
	}

	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}

	public List<AccessRights> getSearchAccessRights() {
		return searchAccessRights;
	}

	public void setSearchAccessRights(List<AccessRights> searchAccessRights) {
		this.searchAccessRights = searchAccessRights;
	}

	public String getSearchAccessRight() {
		return searchAccessRight;
	}

	public void setSearchAccessRight(String searchAccessRight) {
		this.searchAccessRight = searchAccessRight;
	}

	public void searchAccessRightName() {
		if (getSearchAccessRight() == null
				|| getSearchAccessRight().trim().equals("")) {
			this.accessRightsList = null;
			this.accessRightsDataModel = null;
		} else {
			this.accessRightsList = accessRightsService
					.findAccessRightsByAccessRight(getSearchAccessRight());
			this.accessRightsDataModel = null;
		}
	}

	public Users getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public SystemAuditTrailRecordService getSystemAuditTrailRecordService() {
		return systemAuditTrailRecordService;
	}

	public void setSystemAuditTrailRecordService(
			SystemAuditTrailRecordService systemAuditTrailRecordService) {
		this.systemAuditTrailRecordService = systemAuditTrailRecordService;
	}

	public void showDailogue() {

		RequestContext.getCurrentInstance().execute(
				"PF('newAccessRightsDialog').show()");
		return;

	}

}
