package com.btcc.wsm.web.managedbean;


import com.btcc.wsm.model.Users;
import com.btcc.wsm.service.AccessLogService;
import com.btcc.wsm.service.SystemAuditTrailRecordService;
import com.btcc.wsm.service.UsersService;
import com.btcc.wsm.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;

@Component(value="loginAuthenticationBean")
@Scope("session")
@SessionScoped
public class LoginAuthenticationBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	final static Logger logger = Logger.getLogger(LoginAuthenticationBean.class);

	@Autowired
	private UsersService usersService;
	private HashSet<String> accessRightsSet = new HashSet<String>();
	private String username;
	private String password;
	private Boolean wrongPassword;
	
	private Users actorUsers;
	private String oldPassword;
	private String newPassword;
	private String nextSchedulerTime;
	
	private Date today;
	
	
	@Autowired
	private AccessLogService accessLogService;
	
	@Autowired
	private SystemAuditTrailRecordService systemAuditTrailRecordService;
	

	
	
	public Users getActorUsers() {
		return actorUsers;
	}
	public void setActorUsers(Users actorUsers) {
		this.actorUsers = actorUsers;
	}
	
	public LoginAuthenticationBean() {
		logger.info("@@@@@@@@@@@ LoginAuthenticationBean object created... @@@@@@@@@");
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public SystemAuditTrailRecordService getSystemAuditTrailRecordService() {
		return systemAuditTrailRecordService;
	}
	public void setSystemAuditTrailRecordService(
			SystemAuditTrailRecordService systemAuditTrailRecordService) {
		this.systemAuditTrailRecordService = systemAuditTrailRecordService;
	}
	public Boolean getWrongPassword() {
		return wrongPassword;
	}
	public void setWrongPassword(Boolean wrongPassword) {
		this.wrongPassword = wrongPassword;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public UsersService getUsersService() {
		return usersService;
	}
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
	public AccessLogService getAccessLogService() {
		return accessLogService;
	}
	public void setAccessLogService(AccessLogService accessLogService) {
		this.accessLogService = accessLogService;
	}
	
	
	
	public Date getToday() {
		return new Date();
	}
	public void setToday(Date today) {
		this.today = today;
	}
	public String getNextSchedulerTime() {
		return nextSchedulerTime;
	}
	public void setNextSchedulerTime(String nextSchedulerTime) {
		this.nextSchedulerTime = nextSchedulerTime;
	}
	
	
	
	public String doLogin() throws ServletException, IOException{
		if(!checkUserExistInDatabase()){
			logger.info("Username not found in the application database : "+username);
			FacesMessage msg = new FacesMessage("ERROR : User not registered","username not found in database"); 
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				
			FacesContext.getCurrentInstance().addMessage(null, msg);
		/*	FacesContext.getCurrentInstance().addMessage("login.jsf", msg); 
			FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);*/
			//FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.jsf");
			// redirect("login.jsf?faces-redirect=true");
			//return "/login.jsf?faces-redirect=true";
			return "error";
		}
		else{
			
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(!passwordEncoder.matches(password,getUsersService().findByUsername(getUsername()).getPassword()))
			{
				FacesMessage msg = new FacesMessage("ERROR : Wrong password"); 
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				FacesContext.getCurrentInstance().addMessage(null, msg);
				/*FacesContext.getCurrentInstance().addMessage("login.jsf", msg); 
				FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);*/
				//redirect("login.jsf?faces-redirect=true");
				//FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.jsf");
				return "error";
			}
			
		}	
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		RequestDispatcher requestDispatcher = ((ServletRequest) context.getRequest()).getRequestDispatcher("/j_spring_security_check");
		requestDispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();		
		FacesContext.getCurrentInstance().responseComplete();
		
		return null;
		
		
	}
	
	public void loadUserIntoSession()
	{
		
		Authentication auth=SecurityContextHolder.getContext().getAuthentication();
		 Users loggedInUser = (Users) FacesUtil.getSessionMapValue("LOGGEDIN_USER");
		if(loggedInUser == null) {
			setUsername(auth.getName());
			initUsers();
		//	initEmployee();
			populateAccessRightsSet();
			logger.info("Logged in Username is=====> : "+username);
			accessLogService.log(AccessLogActivity.LOGIN, AccessLogLevel.INFO, actorUsers.getId(), getUsername(), getUsername() + " has successfully logged in to the system.");
		//	auditTrail.log(SystemAuditTrailActivity.LOGIN, SystemAuditTrailLevel.INFO, getActorUsers().getId(), getUsername(), getUsername() + " has successfully logged in to the system.");
			
		
		} 
	}
	

	
	private boolean checkUserExistInDatabase() {
		try{
		return usersService.findUserExistInApplication(username);
		}catch(Exception e){
			logger.error("Caught Database Exception while checking username in Database for user :"+username,e);
		}
		return false;
	}
	public void doLogout() throws IOException{
		//TODO Write logic to log out 
		accessLogService.log(AccessLogActivity.LOGOUT, AccessLogLevel.INFO, actorUsers.getId(), getUsername(), getUsername() + " has successfully logged out of the system.");
		SecurityContextHolder.clearContext();
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		logger.info("Logged out successsfuly...!");
		username=null;
		password=null;
		FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/login.jsf");
	 }
	
	private void populateAccessRightsSet() {
		try {
			accessRightsSet = usersService.getAccessRightsMapForUser(username);
		} catch(Exception e) {
			logger.error("Error while populating access rights for user :"+username,e);
		}
	}
	
	public Boolean hasAccess(String key) {		
		if (key != null && accessRightsSet.contains(key)) {
				return true;
		}		
		return false;
	}
	
	public void hasPageAccess(String key) {
		if (key != null && !accessRightsSet.contains(key)) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/accessdenied.jsf");
				} catch(IOException e) {
					e.printStackTrace();
				}
		}		
	}
	
	
	
	public void doUpdatePassword() {
		try {
			actorUsers = usersService.changePassword(actorUsers, oldPassword, getNewPassword());
			systemAuditTrailRecordService.log(SystemAuditTrailActivity.UPDATED, SystemAuditTrailLevel.INFO, getActorUsers().getId(), getActorUsers().getUsername(), getActorUsers().getUsername() + " has updated his/her password");
			FacesMessage msg = new FacesMessage("Info : New password saved and current session logout in 10 seconds please login with new passowrd.");  
			msg.setSeverity(FacesMessage.SEVERITY_INFO);
	        FacesContext.getCurrentInstance().addMessage(null, msg);	     
		} catch(Exception e) {
			FacesMessage msg = new FacesMessage("Error : "+ e.getLocalizedMessage());  
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, msg);  
	        systemAuditTrailRecordService.log(SystemAuditTrailActivity.UPDATED, SystemAuditTrailLevel.ERROR, getActorUsers().getId(), getActorUsers().getUsername(), getActorUsers().getUsername() + " tried to update his/her password but failed due to '" + e.getMessage() + "'");
		}
	}
	
	private void initUsers() {
		try {
			actorUsers = getUsersService().findByUsername(getUsername());
			FacesUtil.setSessionMapValue("LOGGEDIN_USER", actorUsers);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void keepSessionAlive(){
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) fc.getExternalContext().getRequest();
		request.getSession();
	}
	
	public static void redirect(String url) throws IOException {
	    ExternalContext ctx = FacesContext.getCurrentInstance()
	        .getExternalContext();
	    if (url.startsWith("http://") || url.startsWith("https://")
	            || url.startsWith("/")) {
	        ctx.redirect(url);
	    } else {
	        ctx.redirect(ctx.getRequestContextPath() + "/" + url);
	    }
	}
	
	
	
}}