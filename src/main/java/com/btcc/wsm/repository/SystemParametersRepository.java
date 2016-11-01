package com.btcc.wsm.repository;

import com.btcc.wsm.model.SystemParameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

//import java.util.List;
//import org.springframework.data.jpa.repository.Query;

public interface SystemParametersRepository extends
		CrudRepository<SystemParameter, Integer> {

	List<SystemParameter> findAllByIsDeletedOrderByCreationTimeDesc(boolean isDeleted);

	@Query("select sp from SystemParameter sp where  sp.propertyName=? and isDeleted = 0")
    SystemParameter findByPropertyName(String propertyName);

	@Query("select sp from SystemParameter sp where   sp.propertyName=? and isDeleted = 0 and sp.id not in(?) ")
    SystemParameter checkPropertyName(String propertyName, int id);


}
