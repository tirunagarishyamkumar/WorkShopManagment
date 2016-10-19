package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.AccessRights;
import com.btcc.wsm.repository.AccessRightsRepository;
import com.btcc.wsm.repository.CustomRepository;
import com.btcc.wsm.service.AccessRightsService;
import com.btcc.wsm.util.WSMException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by siva on 10/9/2016.
 */
public class AccessRightsServiceImpl implements AccessRightsService {

    final static Logger logger = LogManager.getLogger(AccessRightsServiceImpl.class);


    @Resource
    private AccessRightsRepository accessRightsRepository;
    @Resource
    private CustomRepository customRepository;




    @Transactional(rollbackFor={Exception.class,WSMException.class})
    public AccessRights create(AccessRights accessRights) {
        try{
            AccessRights accessRightsToBeCreated = accessRights;

            logger.info("AccessRightsServiceImpl.create:New Access Right is saving into database with Name :"+accessRights.getAccessRights());
            return accessRightsRepository.save(accessRightsToBeCreated);
        }catch (Exception e) {
            logger.error("AccessRightsServiceImpl.create:Error while saving new Access Right :"+accessRights.getAccessRights(), e);
            throw new WSMException("err.accessRights.create", e);
        }
    }


    @Transactional(rollbackFor=Exception.class)
    public AccessRights delete(int id){
        try{
            AccessRights accessRights = accessRightsRepository.findOne(id);

            if(accessRights != null){
                accessRights.setDeleted(true);
                customRepository.deleteRolesAccessRights(id);
                logger.info("AccessRightsServiceImpl.delete: Access Right is deleting from database with Name :"+accessRights.getAccessRights());

                accessRightsRepository.save(accessRights);
            }
            return accessRights;
        }catch(Exception e) {
            logger.error("AccessRightsServiceImpl.delete:Error while deleting new Access Right :"+id, e);
            throw new WSMException("err.accessRights.delete", e);
        }
    }


    public List<AccessRights> findAll() {
        List<AccessRights> accessRightsList = accessRightsRepository.findAll();
        return accessRightsList;
    }

    @Transactional(rollbackFor={Exception.class,WSMException.class})
    public AccessRights update(AccessRights accessRights){
        try{
            AccessRights accessRightsToBeUpdated = accessRightsRepository.findOne(accessRights.getId());

            if(accessRightsToBeUpdated != null)
            {
                accessRightsToBeUpdated.setId(accessRights.getId());
                accessRightsToBeUpdated.setAccessRights(accessRights.getAccessRights());
                accessRightsToBeUpdated.setDescription(accessRights.getDescription());
                accessRightsToBeUpdated.setLastModifiedBy(accessRights.getLastModifiedBy());
                accessRightsToBeUpdated.setLastModifiedTime(new Date());
                accessRightsRepository.save(accessRightsToBeUpdated);
            }
            return accessRightsToBeUpdated;
        }catch (Exception e) {
            logger.error("AccessRightsServiceImpl.update:Error while updating Access Right :"+accessRights.getAccessRights(), e);
            throw new WSMException("err.accessRights.update", e);
        }
    }


    @Transactional
    public AccessRights findById(int id){
        AccessRights accessRights = accessRightsRepository.findOne(id);

        if(accessRights == null)
            throw new WSMException("No AccessRight Found");

        return accessRights;
    }


    public Set<AccessRights> findAllInSet() {
        Set<AccessRights> resultSet = accessRightsRepository.findAllInSet();
        return resultSet;
    }


    public List<AccessRights> findAccessRightsByAccessRight(String accessRight) {
        String accessRightSearchTerm = "%" + accessRight + "%";
        return accessRightsRepository.findByAccessRightLike(accessRightSearchTerm);
    }


    public AccessRights findAccessRights(String accessRight) {
        AccessRights accessRights = accessRightsRepository.findByAccessRight(accessRight);
        return accessRights;
    }


    public  boolean checkAccessRights(String accessRight){
        AccessRights accessRights = accessRightsRepository.findByAccessRights(accessRight);
        if(accessRights!= null)
            return true;
        return false;
    }


    public boolean findAccessRightsByNameById(String accessRight, int Id) {

        AccessRights accessRights = accessRightsRepository.findAccessRightsByNameById(accessRight, Id);
        if(accessRights!= null)
            return true;
        return false;

    }


}
