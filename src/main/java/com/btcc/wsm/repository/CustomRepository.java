package com.btcc.wsm.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class CustomRepository extends JdbcDaoSupport {




	   final static Logger logger = LogManager.getLogger(CustomRepository.class);

	      public void deleteUserRoles(int id){
			 this.getJdbcTemplate().update("delete from user_role where role_id="+id);
		   }
		   
		  public void deleteRolesAccessRights(int id){
				 this.getJdbcTemplate().update("delete from role_access_right where access_right_id="+id);
			   }
			   
		  public void deleteAccessRightsRoles(int id){
				 this.getJdbcTemplate().update("delete from role_access_right where role_Id="+id);
			   }



		  
		  
		  
}
