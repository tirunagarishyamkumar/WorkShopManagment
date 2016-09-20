package com.anny.wsm.serviceimpl;

import com.anny.wsm.model.AccessRights;
import com.anny.wsm.model.Role;
import com.anny.wsm.model.Users;
import com.anny.wsm.repository.UsersRepository;
import com.anny.wsm.service.UsersService;
import com.anny.wsm.util.WSMException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by siva on 10/9/2016.
 */
public class UsersServiceImpl implements UsersService {


    @Resource
    private UsersRepository usersRepository;


    private Logger log = Logger.getLogger(this.getClass());


    @Transactional(rollbackFor = { Exception.class })
    public Users create(Users users) throws Exception{

		/* Users usersToBeCreated = users;*/

		 /*return usersRepository.save(usersToBeCreated);*/

        try{
            Users usersToBeCreated;

            usersToBeCreated =	usersRepository.findByUsername(users.getUsername());
            // add to if clause && usersToBeCreated.isEnabled()
            if(usersToBeCreated!=null ){
                throw new DataIntegrityViolationException("User already exists");
            }else{
                usersToBeCreated =	usersRepository.findPreviouslyExistingUser(users.getUsername());
                if(usersToBeCreated!=null){


                    usersToBeCreated.setEnabled(true);
                    usersToBeCreated.setLastModifiedBy(users.getLastModifiedBy());
                    usersToBeCreated.setLastModifiedTime(new Date());
                    log.info("UsersServiceImpl.create:New user is saving in database with name :"+users.getUsername());
                    usersRepository.save(usersToBeCreated);
                    return usersToBeCreated;
                }
                else{
                    log.info("UsersServiceImpl.create:New user is saving in database with name :"+users.getUsername());

                    return usersRepository.save(users);
                }
            }
        }catch(Exception e){
            log.error("UsersServiceImpl.create: Error in creating new user in database with name:"+users.getUsername(),e);
            if(e instanceof DataIntegrityViolationException)
                throw new DataIntegrityViolationException(e.getMessage());
            throw e;
        }
    }


    public List<String> findUsersList(String username) {
        List<String> s1 = new ArrayList<String>();

        String usernameSearchTerm = username + "%";
        s1= usersRepository.findUserList(usernameSearchTerm);


        return s1;
    }

    public List<Users> findAll() {
        List<Users> usersList = usersRepository.findAll();
        return usersList;
    }






    public boolean findUserExistInApplication(String username) {
        int count = usersRepository.findUsernameExists(username);
        if(count==1)
            return true;

        return false;
    }

    public HashSet<String> getAccessRightsMapForUser(String userId)  {
        HashSet<String> accessRightsSet = new HashSet<String>();

        Users user = usersRepository.findByUsername(userId);




        Set<Role> roleSet =  user.getUserRoles();

        Iterator<Role> roleIterator = roleSet.iterator();
        while(roleIterator.hasNext()) {
            Role currentRole = roleIterator.next();
            Set<AccessRights> currentRoleAccessRightSet = currentRole.getAccessRights();
            Iterator<AccessRights> currentRoleAccessRightIterator = currentRoleAccessRightSet.iterator();
            while(currentRoleAccessRightIterator.hasNext()) {
                AccessRights currentAccessRights = currentRoleAccessRightIterator.next();
                accessRightsSet.add(currentAccessRights.getAccessRights());
            }
        }


        return accessRightsSet;
    }



    @Transactional(rollbackFor={Exception.class,WSMException.class})
    public Users changePassword(Users users, String oldPassword, String newPassword){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(!passwordEncoder.matches(oldPassword, users.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        String hashedPassword = passwordEncoder.encode(newPassword);
        users.setPassword(hashedPassword);

        try{
            Users usersToBeUpdated = usersRepository.findByUsername(users.getUsername());
            if(usersToBeUpdated != null){
                usersToBeUpdated.setUsername(users.getUsername());
                usersToBeUpdated.setPassword(users.getPassword());
                usersToBeUpdated.setName(users.getName());
                usersToBeUpdated.setEmail(users.getEmail());
                usersToBeUpdated.setControlUnit(users.getControlUnit());
                usersToBeUpdated.setBranchNo(users.getBranchNo());
                usersToBeUpdated.setLastModifiedBy(users.getLastModifiedBy());
                usersToBeUpdated.setLastModifiedTime(new Date());
                Set<Role> roleSet = new HashSet<Role>();
                roleSet.addAll(users.getUserRoles());
                usersToBeUpdated.setUserRoles(roleSet);
                log.info("UsersServiceImpl.update: User is updating in database with name :"+users.getUsername());
                usersRepository.save(usersToBeUpdated);
            }
            return usersToBeUpdated;
        }catch(Exception e){
            log.error("UsersServiceImpl.update:Error while updating user :"+users.getUsername(), e);
            throw new WSMException("err.role.update", e);
        }
    }


    @Transactional(rollbackFor={Exception.class,WSMException.class})
    public Users update(Users users) {
        try{
            Users usersToBeUpdated = usersRepository.findByUsername(users.getUsername());
            if(usersToBeUpdated != null){
                usersToBeUpdated.setUsername(users.getUsername());
                //usersToBeUpdated.setPassword(users.getPassword());
                usersToBeUpdated.setName(users.getName());
                usersToBeUpdated.setEmail(users.getEmail());
                usersToBeUpdated.setControlUnit(users.getControlUnit());
                usersToBeUpdated.setBranchNo(users.getBranchNo());
                usersToBeUpdated.setLastModifiedBy(users.getLastModifiedBy());
                usersToBeUpdated.setLastModifiedTime(new Date());
                Set<Role> roleSet = new HashSet<Role>();
                roleSet.addAll(users.getUserRoles());
                usersToBeUpdated.setUserRoles(roleSet);
                log.info("UsersServiceImpl.update: User is updating in database with name :"+users.getUsername());
                usersRepository.save(usersToBeUpdated);
            }
            return usersToBeUpdated;
        }catch(Exception e){
            log.error("UsersServiceImpl.update:Error while updating user :"+users.getUsername(), e);
            throw new WSMException("err.role.update", e);
        }
    }


    public Users findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Transactional
    public int deleteUser(String username) {
        return usersRepository.deleteUser(username);
    }

    @Transactional
    public Users delete(int id) {
        try{

            Users users = usersRepository.findOne(id);

            if(users != null){
                users.setEnabled(false);
                HashSet<Role> roleSet = new HashSet<Role>();
                users.setUserRoles(roleSet);
                log.info("UsersServiceImpl.delete: User is deleting in database with name :"+users.getUsername());
                usersRepository.save(users);

            }
            return users;
        }catch(Exception e) {
            throw new WSMException();
        }
    }



    public List<Users> findAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

	/*@Override
	public boolean checkUserName(String userName){
		Users users=usersRepository.findByUsername(userName);
		if(users!=null)
			return true;
		return false;

	}*/
}
