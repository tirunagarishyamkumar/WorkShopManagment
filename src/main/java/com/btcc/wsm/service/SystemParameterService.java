package com.btcc.wsm.service;

import com.btcc.wsm.model.SystemParameter;

import java.util.List;

/**
 * Created by siva on 23/9/2016.
 */
public interface SystemParameterService {


    SystemParameter create(SystemParameter systemParameter);
    SystemParameter delete(int id);
    List<SystemParameter> findAll();
    SystemParameter update(SystemParameter systemParameter);
    SystemParameter findById(int id);
    boolean  checkPropertyName(String propertyName);
    boolean  checkPropertyNameWithId(String propertyName,int id);
    String findByPropertyName(String propertyName);
    String getSystemParameterName(String propertyName);
    SystemParameter getByPropertyNameAndIsDeleted(String propertyName,boolean isDeletedFlag);

}
