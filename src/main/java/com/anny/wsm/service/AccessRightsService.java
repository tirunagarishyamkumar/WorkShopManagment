package com.anny.wsm.service;

import com.anny.wsm.model.AccessRights;

import java.util.List;
import java.util.Set;

/**
 * Created by sivak_000 on 10/9/2016.
 */
public interface AccessRightsService {

    AccessRights create(AccessRights accessRights);
    AccessRights delete(int id);
    List<AccessRights> findAll();
    AccessRights update(AccessRights accessRights);
    AccessRights findById(int id);
    Set<AccessRights> findAllInSet();
    List<AccessRights> findAccessRightsByAccessRight(String accessRight);
    AccessRights findAccessRights(String accessRight);
    boolean checkAccessRights(String accessRight);
    boolean findAccessRightsByNameById(String accessRight,int Id);
}
