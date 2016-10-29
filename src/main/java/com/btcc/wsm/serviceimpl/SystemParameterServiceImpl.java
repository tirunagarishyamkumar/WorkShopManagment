package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.SystemParameter;
import com.btcc.wsm.repository.SystemParametersRepository;
import com.btcc.wsm.service.SystemParameterService;
import com.btcc.wsm.util.WSMException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by siva on 23/9/2016.
 */

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

    final static Logger logger = LogManager.getLogger(SystemParameterServiceImpl.class);
    @Resource
    private SystemParametersRepository systemParameterRepository;

    private Map<String,String> parameterMap=new HashMap<String,String>();

    @PostConstruct
    public void init() {

        List<SystemParameter> systemParametersList=findAll();
        for(SystemParameter systemParameters:systemParametersList){
            parameterMap.put(systemParameters.getPropertyName(), systemParameters.getPropertyValue());
        }
    }


    public SystemParameter create(SystemParameter systemParameters) {
        try {
            SystemParameter systemParametersToBeCreated = systemParameters;

            SystemParameter systemParameterFromDb = systemParameterRepository.save(systemParametersToBeCreated);
            return systemParameterFromDb;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    public SystemParameter delete(int id) {
        SystemParameter systemParameters = systemParameterRepository.findOne(id);
        if(systemParameters != null){
            systemParameters.setDeleted(true);
            logger.info("SystemParametersServiceImpl.delete:System Parameters is deleting from the database with Name :"+systemParameters.getPropertyName()+"and "+systemParameters.getPropertyValue());
            systemParameterRepository.save(systemParameters);
            parameterMap.remove(systemParameters.getPropertyName());
        }
        return systemParameters;
    }

    public List<SystemParameter> findAll() {

        List<SystemParameter> systemParametersList = systemParameterRepository.findAllByIsDeletedOrderByCreationTimeDesc(false);
        return systemParametersList;
    }

    public SystemParameter update(SystemParameter systemParameters) {

        SystemParameter systemParametersToBeUpdated = systemParameterRepository.findOne(systemParameters.getId());
        if(systemParametersToBeUpdated != null){
            systemParametersToBeUpdated.setId(systemParameters.getId());
            systemParametersToBeUpdated.setActive(systemParameters.isActive());
            systemParametersToBeUpdated.setPropertyName(systemParameters.getPropertyName());
            systemParametersToBeUpdated.setPropertyValue(systemParameters.getPropertyValue());
            systemParametersToBeUpdated.setDescription(systemParameters.getDescription());
            systemParametersToBeUpdated.setLastModifiedBy(systemParameters.getLastModifiedBy());
            systemParametersToBeUpdated.setLastModifiedTime(new java.util.Date());
            logger.info("SystemParametersServiceImpl.update:System Parameters is updating into database with Name :"+systemParameters.getPropertyName()+"and:"+systemParameters.getPropertyValue());
            systemParameterRepository.save(systemParametersToBeUpdated);

        }

        return systemParametersToBeUpdated;
    }

    public SystemParameter findById(int id) {
        SystemParameter systemParameters = systemParameterRepository.findOne(id);
        if(systemParameters == null)
            throw new WSMException("No System Parameters Found");
        return systemParameters;
    }

    public boolean checkPropertyName(String propertyName) {
        SystemParameter systemParameters = systemParameterRepository.findByPropertyName(propertyName);
        return systemParameters != null;
    }

    public boolean checkPropertyNameWithId(String propertyName, int id) {
        SystemParameter systemParameters = systemParameterRepository.checkPropertyName(propertyName, id);
        return systemParameters != null;
    }

    public String findByPropertyName(String propertyName) {
        return systemParameterRepository.findByPropertyName(propertyName).getPropertyValue();
    }

    public String getSystemParameterName(String propertyName) {

        String prpertyValue=null;
        if(StringUtils.equals(parameterMap.get(propertyName), null)){
            prpertyValue=findByPropertyName(propertyName);
            parameterMap.put(propertyName, prpertyValue);
        }else{
            prpertyValue=parameterMap.get(propertyName);
        }
        return prpertyValue;
    }

    public SystemParameter getByPropertyNameAndIsDeleted(String propertyName, boolean isDeletedFlag) {
        return null;
    }


}
