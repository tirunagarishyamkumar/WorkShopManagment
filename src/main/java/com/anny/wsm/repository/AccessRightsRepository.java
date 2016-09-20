package com.anny.wsm.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.anny.wsm.model.AccessRights;




public interface AccessRightsRepository extends CrudRepository<AccessRights, Integer>{

	@Query("select ar from AccessRights ar where isDeleted = 0")
	 List<AccessRights> findAll();
	
	@Query("select ar from AccessRights ar where isDeleted = 0")
	 Set<AccessRights> findAllInSet();
	
	@Query("select ar from AccessRights ar where accessRights like ? and isDeleted = 0")
	List<AccessRights> findByAccessRightLike(String accessRight);
	
	@Query("select ar from AccessRights ar where accessRights = :accessRight and isDeleted = 0")
    AccessRights findByAccessRight(@Param("accessRight")String accessRight);
	
	@Query("select ar from AccessRights ar where ar.accessRights=? and isDeleted=0")
    AccessRights findByAccessRights(String accessRight);
	
	@Query("select ar from AccessRights ar where ar.accessRights=? and isDeleted = 0 and ar.id not in(?) ")
    AccessRights findAccessRightsByNameById(String accessRights, int id);
	
	
}
