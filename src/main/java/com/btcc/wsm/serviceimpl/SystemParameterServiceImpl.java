package com.btcc.wsm.serviceimpl;

import com.btcc.wsm.model.SystemParameter;
import com.btcc.wsm.service.SystemParameterService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by siva on 23/9/2016.
 */

@Service
public class SystemParameterServiceImpl implements SystemParameterService{

    public SystemParameter create(SystemParameter systemParameter) {
        return null;
    }

    public SystemParameter delete(int id) {
        return null;
    }

    public List<SystemParameter> findAll() {
        return null;
    }

    public SystemParameter update(SystemParameter systemParameter) {
        return null;
    }

    public SystemParameter findById(int id) {
        return null;
    }

    public boolean checkPropertyName(String propertyName) {
        return false;
    }

    public boolean checkPropertyNameWithId(String propertyName, int id) {
        return false;
    }

    public String findByPropertyName(String propertyName) {
        return null;
    }

    public String getSystemParameterName(String propertyName) {
        return null;
    }

    public SystemParameter getByPropertyNameAndIsDeleted(String propertyName, boolean isDeletedFlag) {
        return null;
    }
}
