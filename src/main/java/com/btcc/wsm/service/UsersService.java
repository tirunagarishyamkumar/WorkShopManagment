package com.btcc.wsm.service;

import com.btcc.wsm.model.Users;

import java.util.HashSet;
import java.util.List;

/**
 * Created by siva on 10/9/2016.
 */
public interface UsersService {

        Users create(Users users) throws Exception;
        List<Users> findAll();
        List<Users> findAllUsers();
        List<String> findUsersList(String username);
        boolean findUserExistInApplication(String username);
        HashSet<String> getAccessRightsMapForUser(String username);
        Users changePassword(Users users, String oldPassword, String newPassword);
        Users update(Users users);
        Users findByUsername(String username);
        int deleteUser(String username);
        Users delete(int id);




}
