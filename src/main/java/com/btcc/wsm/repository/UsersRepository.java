package com.btcc.wsm.repository;

import com.btcc.wsm.model.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Integer> {

	
	@Query("select u from Users u where username=? and enabled=1")
    Users findByUsername(String username);

	@Query("select u from Users u where username=? and enabled=0")
    Users findPreviouslyExistingUser(String username);

	@Query("select u  from Users u where enabled=1")
	List<Users> findAll();

	@Query("select username from Users u where username like ? and enabled=1  and active=1")
	List<String> findUserList(String username);

	@Query("select count(*) from Users where username=? and enabled=1 and active=1")
	int findUsernameExists(String username);

	@Modifying
	@Query("update Users u set enabled=1 where username=?")
	int deleteUser(String username);

	@Query("select count(*) from Users where username=? and enabled=1 and active=1")
	int findUsernameActive(String username);

}
