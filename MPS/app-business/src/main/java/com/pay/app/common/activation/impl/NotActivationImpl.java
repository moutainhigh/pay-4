/**
 *  File: NotActivationImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-23   single_zhang     Create
 *
 */
package com.pay.app.common.activation.impl;

import com.pay.app.common.activation.NotActivation;

/**
 * 
 */
public class NotActivationImpl implements NotActivation{
	
       public  boolean backPage(String flag){
    	   if(flag.equals("app")){
    		   return true;
    	   }else{
    		   return false;
    	   }
       }
}
