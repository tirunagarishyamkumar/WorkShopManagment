package com.btcc.wsm.repository;



import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.btcc.wsm.model.SystemAuditTrail;

public interface SystemAuditTrailRepository extends CrudRepository<SystemAuditTrail, Integer>{

	
	 @Query("select sat from SystemAuditTrail sat where isDeleted = ?")
	 List<SystemAuditTrail> findByisDeleted(int x);

	 @Query("select sat from SystemAuditTrail sat where isDeleted = 0")
	 List<SystemAuditTrail> findALL();
	 
	/* @Query("select l from SystemAuditTrail l where date = ?")
	  List<SystemAuditTrail> findByDate(Date x);*/
	
	 @Query("select sat from SystemAuditTrail sat where isDeleted = 0 and activity =? and actiondate between ? and  ? ")	 
	 List<SystemAuditTrail> findByDate(String activity,Date date,Date dates);

	
	
}
