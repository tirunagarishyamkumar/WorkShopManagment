package com.btcc.wsm.web.managedbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.btcc.wsm.model.AccessRights;
import com.btcc.wsm.model.Role;
import com.btcc.wsm.model.Users;
import com.btcc.wsm.service.AccessRightsService;
import com.btcc.wsm.service.RoleService;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.util.FacesUtil;
import com.btcc.wsm.util.SystemAuditTrailActivity;
import com.btcc.wsm.util.SystemAuditTrailLevel;
import com.btcc.wsm.util.WSMException;
import com.btcc.wsm.web.datamodel.RoleDataModel;
import com.btcc.wsm.service.UsersService;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
@RequestScoped
public class RoleManagedBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UsersService usersService;

	@Autowired
	RoleService roleService;

	final static Logger logger = Logger.getLogger(RoleManagedBean.class);

	@Autowired
	private AccessRightsService accessRightsService;

	private List<Role> roleList;
	private RoleDataModel roleDataModel;
	private Role newRole = new Role();
	private Role selectedRole = new Role();
	private boolean insertDelete = false;
	private List<Role> searchRole;
	private Set<AccessRights> accessRightsSet = null;
	private Set<AccessRights> assignedAccessRightsSet = new HashSet<AccessRights>();
	private Set<AccessRights> unassignedAccessRightsSet = new HashSet<AccessRights>();
	private DualListModel<AccessRights> dualAccessRightsList = new DualListModel<AccessRights>();

	private String searchRoleName = "";

	private Users loggedInUser;

	@Autowired
	private SystemAuditTrailRecordService systemAuditTrailRecordService;

	/*
	 * @PostConstruct public void init(){ accessRightsSet =
	 * accessRightsService.findAllInSet(); }
	 */

	public RoleService getRoleService() {
		return roleService;
	}

	public SystemAuditTrailRecordService getSystemAuditTrailRecordService() {
		return systemAuditTrailRecordService;
	}

	public void setSystemAuditTrailRecordService(
			SystemAuditTrailRecordService systemAuditTrailRecordService) {
		this.systemAuditTrailRecordService = systemAuditTrailRecordService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Role> getRoleList() {

		if (roleList == null) {
			roleList = roleService.findAll();
		}
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public RoleDataModel getRoleDataModel() {
		return new RoleDataModel(getRoleList());
	}

	public void setRoleDataModel(RoleDataModel roleDataModel) {
		this.roleDataModel = roleDataModel;
	}

	public Role getNewRole() {
		return newRole;
	}

	public void setNewRole(Role newRole) {
		this.newRole = newRole;
	}

	public void doCreateRole() {
		System.out.println("doCreateRole is called");
		if (getRoleService().checkRole(newRole.getRole())) {
			System.out.println("Role Already Created");
			FacesMessage msg = new FacesMessage("FAILURE : Role "
					+ newRole.getRole() + " Already Exists.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} else {

			try {

				logger.info("RoleManagedBean.doCreateRole is creating roles "
						+ newRole.getRole());
				loggedInUser = (Users) FacesUtil
						.getSessionMapValue("LOGGEDIN_USER");

				newRole.setDeleted(false);
				newRole.setCreatedBy(getLoggedInUser().getUsername());
				newRole.setCreationTime(new java.util.Date());
				List<AccessRights> selectedAccessRightsList = dualAccessRightsList
						.getTarget();
				HashSet<AccessRights> selectedAccessRightsSet = new HashSet<AccessRights>();
				selectedAccessRightsSet.addAll(selectedAccessRightsList);
				newRole.setAccessRights(selectedAccessRightsSet);
				getRoleService().create(newRole);
				setInsertDelete(true);
				if (roleList == null || insertDelete == true) {
					roleList = roleService.findAll();
				}

				systemAuditTrailRecordService.log(
						SystemAuditTrailActivity.CREATED,
						SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
						getLoggedInUser().getUsername(), getLoggedInUser()
								.getUsername()
								+ " has created new Group "
								+ newRole.getRole());
				FacesContext.getCurrentInstance().addMessage(
						null,
						new FacesMessage("SUCCESS : Group  "
								+ newRole.getRole() + " has been created."));
				newRole = new Role();
			} catch (WSMException e) {
				logger.error(
						"RoleManagedBean.doCreateRole: Error while creating Role: "
								+ newRole.getRole(), e);
				FacesMessage msg = new FacesMessage(
						"Error in creating  : Role " + newRole.getRole());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);

			}
		}
	}

	public Role getSelectedRole() {
		return selectedRole;
	}

	/*
	 * public void setSelectedRole(Role selectedRole) { this.selectedRole =
	 * selectedRole; }
	 */

	public void setSelectedRole(Role selectedRole) {
		this.selectedRole = selectedRole;
		this.assignedAccessRightsSet = this.selectedRole.getAccessRights();
		this.accessRightsSet = null;
		accessRightsSet = accessRightsService.findAllInSet();
		this.unassignedAccessRightsSet.addAll(this.accessRightsSet);
		this.unassignedAccessRightsSet.removeAll(this.assignedAccessRightsSet);
		List<AccessRights> unassignedAccessRightsList = new ArrayList<AccessRights>();
		unassignedAccessRightsList.addAll(unassignedAccessRightsSet);
		List<AccessRights> assignedAccessRightsList = new ArrayList<AccessRights>();
		assignedAccessRightsList.addAll(assignedAccessRightsSet);
		this.dualAccessRightsList = new DualListModel<AccessRights>(
				unassignedAccessRightsList, assignedAccessRightsList);
	}

	public void doUpdateRole() {
		if (getRoleService().findRoleByNameById(selectedRole.getRole(),
				selectedRole.getId())) {
			System.out.println("Role Already Created");
			FacesMessage msg = new FacesMessage("FAILURE : Role "
					+ selectedRole.getRole() + " Already Exists");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;

		} else {

			try {
				logger.info("RoleManagedBean.doUpdateRole is updating roles "
						+ selectedRole.getRole());

				loggedInUser = (Users) FacesUtil
						.getSessionMapValue("LOGGEDIN_USER");

				System.out.println("New Role:" + selectedRole.getRole());
				System.out.println("Id:" + selectedRole.getId());
				System.out.println("Description:"
						+ selectedRole.getDescription());
				selectedRole.setLastModifiedBy(getLoggedInUser().getUsername());
				List<AccessRights> selectedAccessRightsList = dualAccessRightsList
						.getTarget();
				HashSet<AccessRights> selectedAccessRightsSet = new HashSet<AccessRights>();
				selectedAccessRightsSet.addAll(selectedAccessRightsList);
				selectedRole.setAccessRights(selectedAccessRightsSet);
				getRoleService().update(selectedRole);
				setInsertDelete(true);
				if (roleList == null || insertDelete == true) {
					roleList = roleService.findAll();
				}
				systemAuditTrailRecordService.log(
						SystemAuditTrailActivity.UPDATED,
						SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
						getLoggedInUser().getUsername(), getLoggedInUser()
								.getUsername()
								+ " has update Group "
								+ selectedRole.getRole());
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage("SUCCESS : Group  "
										+ selectedRole.getRole()
										+ " has been updated"));
			} catch (WSMException e) {
				logger.error(
						"RoleManagedBean.doUpdateRole: Error while updating Role: "
								+ selectedRole.getRole(), e);
				FacesMessage msg = new FacesMessage(
						"Error in updating  : Role " + selectedRole.getRole());
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
	}

	public void populatePickList() {
		accessRightsSet = accessRightsService.findAllInSet();
		unassignedAccessRightsSet = new HashSet<AccessRights>();
		this.unassignedAccessRightsSet.addAll(this.accessRightsSet);
		List<AccessRights> unassignedAccessRightsList = new ArrayList<AccessRights>();
		unassignedAccessRightsList.addAll(unassignedAccessRightsSet);
		List<AccessRights> assignedAccessRightsList = new ArrayList<AccessRights>();
		this.dualAccessRightsList = new DualListModel<AccessRights>(
				unassignedAccessRightsList, assignedAccessRightsList);

	}


	public void onRowSelect(SelectEvent event) {

		Role roleSelected = (Role) event.getObject();
		setSelectedRole(roleService.findByRole(roleSelected.getRole()));

	}

	public void doDeleteRole() {
		try {
			logger.info("RoleManagedBean.doDeleteRole is deleting roles "
					+ selectedRole.getRole());
			loggedInUser = (Users) FacesUtil
					.getSessionMapValue("LOGGEDIN_USER");

			getRoleService().delete(selectedRole.getId());
			setInsertDelete(true);
			if (roleList == null || insertDelete == true) {
				roleList = roleService.findAll();
			}
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.UPDATED,
					SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
					getLoggedInUser().getUsername(), getLoggedInUser()
							.getUsername()
							+ " has deleted Group "
							+ selectedRole.getRole());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("SUCCESS : Group  "
							+ selectedRole.getRole() + " has been deleted."));
			// new Refresh().refreshPage();
		} catch (WSMException e) {
			logger.error(
					"RoleManagedBean.doDeleteRole: Error while deleting Role: "
							+ selectedRole.getRole(), e);
			FacesMessage msg = new FacesMessage("Error in deleting  : Role "
					+ selectedRole.getRole());
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public List<Role> getSearchRole() {
		return searchRole;
	}

	public void setSearchRole(List<Role> searchRole) {
		this.searchRole = searchRole;
	}

	public boolean isInsertDelete() {
		return insertDelete;
	}

	public void setInsertDelete(boolean insertDelete) {
		this.insertDelete = insertDelete;
	}

	public String getSearchRoleName() {
		return searchRoleName;
	}

	public void setSearchRoleName(String searchRoleName) {
		this.searchRoleName = searchRoleName;
	}

	public void searchRole() {

		if (getSearchRoleName() == null
				|| getSearchRoleName().trim().equals("")) {
			this.roleList = null;
			this.roleDataModel = null;
		} else {
			this.roleList = roleService.findRoleByRoleName(getSearchRoleName());
			this.roleDataModel = null;
		}

	}

	public Users getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public AccessRightsService getAccessRightsService() {
		return accessRightsService;
	}

	public void setAccessRightsService(AccessRightsService accessRightsService) {
		this.accessRightsService = accessRightsService;
	}

	public Set<AccessRights> getAccessRightsSet() {
		return accessRightsSet;
	}

	public void setAccessRightsSet(Set<AccessRights> accessRightsSet) {
		this.accessRightsSet = accessRightsSet;
	}

	public Set<AccessRights> getAssignedAccessRightsSet() {
		return assignedAccessRightsSet;
	}

	public void setAssignedAccessRightsSet(
			Set<AccessRights> assignedAccessRightsSet) {
		this.assignedAccessRightsSet = assignedAccessRightsSet;
	}

	public Set<AccessRights> getUnassignedAccessRightsSet() {
		return unassignedAccessRightsSet;
	}

	public void setUnassignedAccessRightsSet(
			Set<AccessRights> unassignedAccessRightsSet) {
		this.unassignedAccessRightsSet = unassignedAccessRightsSet;
	}

	public DualListModel<AccessRights> getDualAccessRightsList() {
		return dualAccessRightsList;
	}

	public void setDualAccessRightsList(
			DualListModel<AccessRights> dualAccessRightsList) {
		this.dualAccessRightsList = dualAccessRightsList;
	}

	public void showDailogue() {
		populatePickList();
		RequestContext.getCurrentInstance().execute(
				"PF('newRoleDialog').show()");

		return;

	}

}
