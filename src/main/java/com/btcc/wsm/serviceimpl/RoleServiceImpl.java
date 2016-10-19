package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.AccessRights;
import com.btcc.wsm.model.Role;
import com.btcc.wsm.repository.RoleRepository;
import com.btcc.wsm.repository.UsersRepository;
import com.btcc.wsm.repository.CustomRepository;
import com.btcc.wsm.service.RoleService;
import com.btcc.wsm.util.WSMException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sivak_000 on 10/9/2016.
 */
public class RoleServiceImpl implements RoleService {

    public static String role="ROLE_USER";

    @Autowired
    private CustomRepository customrep;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private UsersRepository usersRepository;

    private Logger log = LogManager.getLogger(this.getClass());


    @Transactional(rollbackFor ={Exception.class,WSMException.class})
    public Role create(Role role) {
        try{
            Role roleToBeCreated = role;
            log.info("RoleServiceImpl.create:New Role is saving into database with Name :"+roleToBeCreated.getRole());
            return roleRepository.save(roleToBeCreated);
        }catch (Exception e) {
            log.error("RoleServiceImpl.create:Error while saving new Role :"+role.getRole(), e);
            throw new WSMException("err.role.create", e);
        }
    }


    @Transactional(rollbackFor=WSMException.class)
    public Role delete(int id){
        try{
            Role role = roleRepository.findOne(id);
            role.setDeleted(true);
            customrep.deleteUserRoles(id);
            customrep.deleteAccessRightsRoles(id);
            log.info("RoleServiceImpl.delete:Role is deleting from database with Name :"+role.getRole());
            roleRepository.save(role);
            return role;
        }catch(Exception e) {
            log.error("RoleServiceImpl.delete:Error while deleting  Role :"+id, e);
            throw new WSMException("err.role.delete", e);
        }
    }



    public List<Role> findAll() {
        List<Role> resultList = roleRepository.findAll();
        return resultList;
    }


    public Set<Role> findAllInSet() {
        Set<Role> resultSet = roleRepository.findAllInSet();
        return resultSet;
    }


    @Transactional(rollbackFor={Exception.class,WSMException.class})
    public Role update(Role role){
        try{
            Role roleToBeUpdated = roleRepository.findOne(role.getId());
            if(roleToBeUpdated != null){
                roleToBeUpdated.setId(role.getId());
                roleToBeUpdated.setRole(role.getRole());
                roleToBeUpdated.setDescription(role.getDescription());
                roleToBeUpdated.setLastModifiedBy(role.getLastModifiedBy());
                roleToBeUpdated.setLastModifiedTime(new Date());
                Set<AccessRights> accessRightsSet = new HashSet<AccessRights>();
                accessRightsSet.addAll(role.getAccessRights());
                roleToBeUpdated.setAccessRights(accessRightsSet);
                log.info("RoleServiceImpl.update:Role is updating into database with Name :"+roleToBeUpdated.getRole());
                roleRepository.save(roleToBeUpdated);
            }
            return roleToBeUpdated;
        }catch (Exception e) {
            log.error("RoleServiceImpl.update:Error while updating Role :"+role.getRole(), e);
            throw new WSMException("err.role.update", e);
        }
    }



    public Role findByRole(String role)  {
        Role foundRole = roleRepository.findByRole(role);

        if(foundRole == null)
            throw new WSMException();

        return foundRole;
    }


    @Transactional
    public Role findById(int id) {
        Role role = roleRepository.findOne(id);

        if(role == null)
            throw new WSMException();
        return role;
    }


    public List<Role> findRoleByRoleName(String role) {
        String roleSearchTerm = "%" + role + "%";
        return roleRepository.findByRoleLike(roleSearchTerm);
    }


    public List<String> findRoleNamesByUsername(String username) {
        List<String> roleNameList = roleRepository.findRoleNamesByUsername(username);
        return roleNameList;
    }

    public boolean checkRole(String role){
        Role  roles= roleRepository.findByRole(role);
        if(roles!=null)
            return true;
        return false;

    }



    public boolean findRoleByNameById(String role, int Id) {
        System.out.println("Role Name"+role);
        Role roles = roleRepository.findRoleByNameById(role, Id);
        if(roles!= null)
            return true;
        return false;
    }
}
