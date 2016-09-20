package com.btcc.wsm.repository;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;

@Component
public class WSMCustomRepository extends JdbcDaoSupport {




	   final static Logger logger = Logger.getLogger(WSMCustomRepository.class);

	      public void deleteUserRoles(int id){
			 this.getJdbcTemplate().update("delete from UserToRole where userRoleId="+id);
		   }
		   
		  public void deleteRolesAccessRights(int id){
				 this.getJdbcTemplate().update("delete from RoleToAccessRights where accessRightsId="+id);
			   }
			   
		  public void deleteAccessRightsRoles(int id){
				 this.getJdbcTemplate().update("delete from RoleToAccessRights where roleId="+id);
			   }



		  
		  
		  
}
