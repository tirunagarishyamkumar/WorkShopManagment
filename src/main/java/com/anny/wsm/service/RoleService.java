package com.anny.wsm.service;

import com.anny.wsm.model.Role;

import java.util.List;
import java.util.Set;

/**
 * Created by sivak_000 on 10/9/2016.
 */
public interface RoleService {

    Role create(Role role);
    Role delete(int id);
    List<Role> findAll();
    Role update(Role role);
    Role findById(int id);
    Role findByRole(String role);
    List<Role> findRoleByRoleName(String role);
    Set<Role> findAllInSet();
    List<String> findRoleNamesByUsername(String username);

    boolean checkRole(String role);
    boolean findRoleByNameById(String role,int Id);
}
