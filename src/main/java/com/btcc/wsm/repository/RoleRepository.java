package com.btcc.wsm.repository;

import com.btcc.wsm.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Integer> {

	/*@Query("select r from Role r where isDeleted = 0")
	List<Role> findAll();*/

	List<Role> findAllByIsDeletedOrderByCreationTimeDesc(boolean isDeleted);

	@Query("select r from Role r where role=? and isDeleted=0")
    Role findByRole(String role);

	@Query("select r from Role r where isDeleted = 0 and r.active=1")
	Set<Role> findAllInSet();

	@Query("select r from Role r where role like ? and isDeleted=0 and active=1")
	List<Role> findByRoleLike(String role);

	@Query("select r.role from Users u join u.userRoles r where u.username = :username and isDeleted=0")
	List<String> findRoleNamesByUsername(@Param("username") String username);

	@Query("select r from Role r where   r.role=? and  isDeleted=0 and r.id not in(?) ")
    Role findRoleByNameById(String role, int id);

	@Query("select r from Role r where r.id=? and  isDeleted = 0 and active=1")
    Role checkRoleActive(int id);
}
