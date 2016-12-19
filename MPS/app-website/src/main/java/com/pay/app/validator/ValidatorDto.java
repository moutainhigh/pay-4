package com.pay.app.validator;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class ValidatorDto implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private String value=null;
    
    public boolean hasErrors(){
        return (StringUtils.isNotBlank(getError()));
    }
    
    public void rejectValue(String value){
        this.value=value;
    }

    public String getError() {
        return value;
    }

  
    
    
}
