package com.anny.wsm.util;



import com.anny.wsm.model.Users;

import javax.faces.context.FacesContext;

public class FacesUtil {

    // Getters
    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    // Setters
    public static void setSessionMapValue(String key, Object value) {
      Users user =  (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
      if(user!=null){
    	  System.out.println("User is set in session : "+user.getUsername());
      }
    }

}