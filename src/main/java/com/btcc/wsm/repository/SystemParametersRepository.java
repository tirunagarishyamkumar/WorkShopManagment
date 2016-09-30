package com.btcc.wsm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
//import java.util.List;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.btcc.wsm.model.SystemParameter;

public interface SystemParametersRepository extends
		CrudRepository<SystemParameter, Integer> {

	@Query("select sp from SystemParameter sp where isDeleted = 0")
	List<SystemParameter> findAll();

	@Query("select propertyValue from SystemParameter sp where propertyName='bankCharges'")
	String findBankCharges();

	@Query("select propertyValue from SystemParameter sp where propertyName='ibgCharges'")
	String findIbgCharges();

	@Query("select sp from SystemParameter sp where  sp.propertyName=? and isDeleted = 0")
	SystemParameter findByPropertyName(String propertyName);

	@Query("select sp from SystemParameter sp where   sp.propertyName=? and isDeleted = 0 and sp.id not in(?) ")
	SystemParameter checkPropertyName(String propertyName, int id);

	@Query("select propertyValue from SystemParameter sp where propertyName='fileStoragePath'")
	String findFileStoragePath();

	@Query("select propertyValue from SystemParameter sp where propertyName='archiveFileStoragePath'")
	String findArchiveFileStoragePath();
}
