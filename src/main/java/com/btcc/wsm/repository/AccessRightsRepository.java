package com.btcc.wsm.repository;

import java.util.List;
import java.util.Set;

import com.btcc.wsm.model.AccessRights;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface AccessRightsRepository extends CrudRepository<AccessRights, Integer>{

	/*@Query("select ar from AccessRights ar where isDeleted = 0")
	 List<AccessRights> findAll();*/
	List<AccessRights> findAllByIsDeletedOrderByCreationTimeDesc(boolean isDeleted);
	
	@Query("select ar from AccessRights ar where isDeleted = 0 and active=1")
	 Set<AccessRights> findAllInSet();
	
	@Query("select ar from AccessRights ar where accessRights like ? and isDeleted = 0 and active=1")
	List<AccessRights> findByAccessRightLike(String accessRight);
	
	@Query("select ar from AccessRights ar where accessRights = :accessRight and isDeleted = 0 and active=1")
    AccessRights findByAccessRight(@Param("accessRight")String accessRight);
	
	@Query("select ar from AccessRights ar where ar.accessRights=? and isDeleted=0")
    AccessRights findByAccessRights(String accessRight);
	
	@Query("select ar from AccessRights ar where ar.accessRights=? and isDeleted = 0 and ar.id not in(?) ")
    AccessRights findAccessRightsByNameById(String accessRights, int id);
	
	
}
