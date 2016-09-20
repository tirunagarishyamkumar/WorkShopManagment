package com.btcc.wsm.repository;

import java.util.List;

import com.btcc.wsm.model.AccessLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AccessLogRepository extends CrudRepository<AccessLog, Integer> {

	@Query("select al from AccessLog al where isDeleted = 0")
	List<AccessLog> findAll();

}
