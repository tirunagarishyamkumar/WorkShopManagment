package com.btcc.wsm.web.managedbean;

import com.btcc.wsm.model.Role;
import com.btcc.wsm.model.Users;
import com.btcc.wsm.service.RoleService;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.util.FacesUtil;
import com.btcc.wsm.util.SystemAuditTrailLevel;
import com.btcc.wsm.service.UsersService;
import com.btcc.wsm.util.SystemAuditTrailActivity;
import com.btcc.wsm.util.WSMException;
import com.btcc.wsm.web.datamodel.UsersDataModel;
import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.*;



@Component
@ViewScoped
public class UsersManagedBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(UsersManagedBean.class);

	List<String> users = new ArrayList<String>();
	private Users selectedUser = new Users();
	private Users newUser = new Users();
	private List<Users> usersList;
	private UsersDataModel usersDataModel;
	private Users loggedInUser;
	private Set<Role> roleSet = null;
	private Set<Role> assignedRoleSet = new HashSet<Role>();
	private Set<Role> unassignedRoleSet = new HashSet<Role>();
	private DualListModel<Role> dualRoleList = new DualListModel<Role>();
	// private Boolean dmlOperationPerformed=Boolean.FALSE;
	private boolean insertDelete = false;
	@Autowired
	private UsersService usersService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SystemAuditTrailRecordService systemAuditTrailRecordService;



	public Users getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(Users loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public Users getNewUser() {
		return newUser;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public Set<Role> getRoleSet() {
		return roleSet;
	}

	public void setRoleSet(Set<Role> roleSet) {
		this.roleSet = roleSet;
	}

	public Set<Role> getAssignedRoleSet() {
		return assignedRoleSet;
	}

	public void setAssignedRoleSet(Set<Role> assignedRoleSet) {
		this.assignedRoleSet = assignedRoleSet;
	}

	public Set<Role> getUnassignedRoleSet() {
		return unassignedRoleSet;
	}

	public void setUnassignedRoleSet(Set<Role> unassignedRoleSet) {
		this.unassignedRoleSet = unassignedRoleSet;
	}

	public DualListModel<Role> getDualRoleList() {
		return dualRoleList;
	}

	public void setDualRoleList(DualListModel<Role> dualRoleList) {
		this.dualRoleList = dualRoleList;
	}

	public void setNewUser(Users newUser) {
		this.newUser = newUser;
	}

	public Users getSelectedUser() {
		return selectedUser;
	}

	public SystemAuditTrailRecordService getSystemAuditTrailRecordService() {
		return systemAuditTrailRecordService;
	}

	public void setSystemAuditTrailRecordService(
			SystemAuditTrailRecordService systemAuditTrailRecordService) {
		this.systemAuditTrailRecordService = systemAuditTrailRecordService;
	}

	public void setSelectedUser(Users selectedUser) {
		this.selectedUser = selectedUser;
		this.assignedRoleSet = this.selectedUser.getUserRoles();
		roleSet = null;
		roleSet = roleService.findAllInSet();
		this.unassignedRoleSet.addAll(this.roleSet);
		this.unassignedRoleSet.removeAll(this.assignedRoleSet);
		List<Role> unassignedRoleList = new ArrayList<Role>();
		unassignedRoleList.addAll(unassignedRoleSet);
		List<Role> assignedRoleList = new ArrayList<Role>();
		assignedRoleList.addAll(assignedRoleSet);
		this.dualRoleList = new DualListModel<Role>(unassignedRoleList,
				assignedRoleList);
	}

	public UsersDataModel getUsersDataModel() {

		return new UsersDataModel(getUsersList());
	}

	public void setUsersDataModel(UsersDataModel usersDataModel) {
		this.usersDataModel = usersDataModel;
	}

	public List<Users> getUsersList() {
		if (usersList == null) {
			usersList = usersService.findAll();
		}
		return usersList;
	}

	public void setUsersList(List<Users> usersList) {
		this.usersList = usersList;
	}

	public UsersService getUsersService() {
		return usersService;
	}

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void doCreateUser() {
		/*
		 * if(getUsersService().checkUserName(newUser.getUsername())){
		 * System.out.println("UserName  already Exist"); FacesMessage msg = new
		 * FacesMessage("FAILURE: User Name " +
		 * newUser.getUsername()+" has been Already Created");
		 * msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		 * FacesContext.getCurrentInstance().addMessage(null, msg); return; }
		 * else{
		 */
		try {
			logger.info("UsersManagedBean.doCreateUser is creating new user:"
					+ newUser.getUsername());
			System.out.println("Getting logged in user from session..");
			loggedInUser = (Users) FacesUtil
					.getSessionMapValue("LOGGEDIN_USER");
			String newUsername = newUser.getUsername();

			/*
			 * LdapContext context=null; try{ context
			 * =activeDirectoryService.getLdapContext(); } catch (Exception nex)
			 * { String msg=("LDAP Connection: FAILED");
			 * ApplLogger.getLogger().error(msg); throw new Exception(msg);
			 * 
			 * // nex.printStackTrace(); } boolean
			 * valid=activeDirectoryService.checkUserinAD(newUsername,context);
			 * 
			 * if(!valid){ String msg="User doesn't exist in active directory";
			 * System.out.println(msg); ApplLogger.getLogger().error(msg);
			 * FacesMessage msg1 = new FacesMessage("ERROR : "+msg);
			 * msg1.setSeverity(FacesMessage.SEVERITY_ERROR);
			 * FacesContext.getCurrentInstance().addMessage(null, msg1); }
			 * 
			 * else {
			 */

			newUser.setEnabled(true);
			/*
			 * PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			 * String hashedPassword =
			 * passwordEncoder.encode(newUser.getPassword());
			 * newUser.setPassword(hashedPassword);
			 */
			newUser.setCreatedBy(getLoggedInUser().getUsername());
			newUser.setCreationTime(new Date());
			List<Role> selectedRoleList = dualRoleList.getTarget();
			HashSet<Role> selectedRoleSet = new HashSet<Role>();
			selectedRoleSet.addAll(selectedRoleList);
			newUser.setUserRoles(selectedRoleSet);
			usersService.create(newUser);
			setInsertDelete(true);
			if (usersList == null || insertDelete == true) {
				usersList = usersService.findAll();
			}
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.CREATED,
					SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
					getLoggedInUser().getUsername(),
					getLoggedInUser().getUsername() + " has created new User"
							+ newUser.getUsername());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("SUCCESS : User  " + newUser.getUsername()
							+ " has been created."));
			newUser = new Users();

		} catch (Exception e) {
			if (e instanceof DataIntegrityViolationException) {
				logger.error(
						"Error while adding new user to System", e);
				FacesMessage msg = new FacesMessage(
						"FAILURE : Username  Already Exist.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			} else {
				logger.error(
						"Error while adding new user to System", e);
				FacesMessage msg = new FacesMessage(
						"FAILURE: Not able to add user currently to System");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		}
		/* } */
	}

	void checkBeforeUpdate() {
		loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
		HashSet<String> accessRights = usersService
				.getAccessRightsMapForUser(getLoggedInUser().getUsername());
		if (accessRights.contains("SY0401E")
				|| accessRights.contains("SY0401D")) {
			if ((getLoggedInUser().getUsername().equalsIgnoreCase("system"))
					&& selectedUser.getCreatedBy().equalsIgnoreCase("system")) {
				RequestContext.getCurrentInstance().execute(
						"PF('userDialog').show()");
			} else {
				if (!(getLoggedInUser().getUsername()
						.equalsIgnoreCase("system"))
						&& !(selectedUser.getCreatedBy()
								.equalsIgnoreCase("system"))) {
					RequestContext.getCurrentInstance().execute(
							"PF('userDialog').show()");
				} else {
					RequestContext.getCurrentInstance().execute(
							"PF('userDialog').hide()");
					FacesMessage msg = new FacesMessage(
							"You Dont have Rights to Update this Record");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					FacesContext.getCurrentInstance().addMessage(null, msg);
					return;
				}
			}
		} else {
			RequestContext.getCurrentInstance().execute(
					"PF('userDialog').hide()");
			FacesMessage msg = new FacesMessage(
					"You Dont have Rights to Update this Record");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return;
		}

	}

	public void onRowSelect(SelectEvent event) {

		Users userSelected = (Users) event.getObject();
		setSelectedUser(usersService.findByUsername(userSelected.getUsername()));

		checkBeforeUpdate();

	}

	/*
	 * public void onRowSelect(SelectEvent event) {
	 * 
	 * setSelectedUser((Users) event.getObject()); getSelectedControlUnits();
	 * checkBeforeUpdate();
	 * 
	 * }
	 */

	public void doInitializeForm() {
		newUser = new Users();
		// Get the groups from the database
		// rolesList = roleService.findAll();
	}

	public void doUpdateUser() {

		try {
			logger.info("UsersManagedBean.doUpdateUser is updating new user:"
					+ selectedUser.getUsername());

			loggedInUser = (Users) FacesUtil
					.getSessionMapValue("LOGGEDIN_USER");

			List<Role> selectedRoleList = dualRoleList.getTarget();
			HashSet<Role> selectedRoleSet = new HashSet<Role>();
			selectedRoleSet.addAll(selectedRoleList);
			selectedUser.setUserRoles(selectedRoleSet);
			selectedUser.setLastModifiedBy(loggedInUser.getUsername());
			getUsersService().update(selectedUser);
			setInsertDelete(true);
			if (usersList == null || insertDelete == true) {
				usersList = usersService.findAll();
			}
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.UPDATED,
					SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
					getLoggedInUser().getUsername(),
					getLoggedInUser().getUsername() + " has Updated User"
							+ selectedUser.getUsername());
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage("SUCCESS : User "
							+ selectedUser.getUsername()
							+ "  has been updated."));
		} catch (Exception e) {
			logger.error(
					"UsersManagedBean.doUpdateUser:Error while updating  user: "
							+ selectedUser.getUsername(), e);
			FacesMessage msg = new FacesMessage(
					"FAILURE : Not able to update user in the System ");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public void deleteUser() {
		System.out.println("Username is going to be deleted is : "
				+ selectedUser.getUsername());
		if (!"admin".equalsIgnoreCase(selectedUser.getUsername())
				&& usersService.deleteUser(selectedUser.getUsername()) > 0)
			System.out.println("Deleted successfully");
		else
			System.out.println("Could not delete. Try later..");
	}

	public void populatePickList() {
		roleSet = roleService.findAllInSet();
		unassignedRoleSet = new HashSet<Role>();
		this.unassignedRoleSet.addAll(this.roleSet);
		List<Role> unassignedRoleList = new ArrayList<Role>();
		unassignedRoleList.addAll(unassignedRoleSet);
		List<Role> assignedRoleList = new ArrayList<Role>();
		this.dualRoleList = new DualListModel<Role>(unassignedRoleList,
				assignedRoleList);

	}

	public void doDeleteUser() {
		System.out.println("Username is going to be deleted is : "
				+ selectedUser.getUsername());
		/*
		 * if (!"admin".equalsIgnoreCase(selectedUser.getUsername()) &&
		 * usersService.deleteUser(selectedUser.getUsername()) > 0)
		 * System.out.println("Deleted successfully"); else
		 * System.out.println("Could not delete. Try later..");
		 */

		try {
			logger.info("UsersManagedBean.doDeleteUser is deleting new user:"
					+ selectedUser.getUsername());

			loggedInUser = (Users) FacesUtil
					.getSessionMapValue("LOGGEDIN_USER");

			getUsersService().delete(selectedUser.getId());
			setInsertDelete(true);
			if (usersList == null || insertDelete == true) {
				usersList = usersService.findAll();
			}
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.UPDATED,
					SystemAuditTrailLevel.INFO, getLoggedInUser().getId(),
					getLoggedInUser().getUsername(),
					getLoggedInUser().getUsername() + " has deleted User"
							+ selectedUser.getUsername());
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage("SUCCESS : User "
									+ selectedUser.getUsername()
									+ " has been deleted."));

			// FacesContext.getCurrentInstance().addMessage(null, new
			// FacesMessage("Info",getExcptnMesProperty("info.role.delete")));
			// new Refresh().refreshPage();
		} catch (WSMException e) {
			logger.error(
					"UsersManagedBean.doDeleteUser:Error while deleting  user: "
							+ selectedUser.getUsername(), e);
			FacesMessage msg = new FacesMessage(
					"FAILURE : Not able to delete user in the System");
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

	public void showDailogue() {
		populatePickList();
		RequestContext.getCurrentInstance().execute(
				"PF('newUserDialog').show()");
		return;

	}



}
