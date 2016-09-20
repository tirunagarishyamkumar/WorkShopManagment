package com.anny.wsm.web.datamodel;
import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.anny.wsm.model.SystemParameter;

public class SystemParametersDataModel extends ListDataModel<SystemParameter> implements SelectableDataModel<SystemParameter>{

     public SystemParametersDataModel(){
    	 
     }
     
     public SystemParametersDataModel(List<SystemParameter> data){
    	 super(data);
     }
     
     @SuppressWarnings("unchecked")
 	public SystemParameter getRowData(String rowKey) {
 		
 		List<SystemParameter> systemParametersList = (List<SystemParameter>)getWrappedData();
 		Integer rowKeyInt = Integer.parseInt(rowKey);
 		  for(SystemParameter systemParameters : systemParametersList) {
 	          if(systemParameters.getId() == rowKeyInt) {
 	              return systemParameters;
 	          }
 	      }
 		return null;
 	}
     
     public Object getRowKey(SystemParameter systemParameters) {
 		
 		return systemParameters.getId();
 	}
}
