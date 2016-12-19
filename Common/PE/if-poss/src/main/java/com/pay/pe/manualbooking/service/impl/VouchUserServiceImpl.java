package com.pay.pe.manualbooking.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;

import com.pay.pe.manualbooking.service.VouchUserService;

/**
 * 手工记账用户服务实现
 */
public class VouchUserServiceImpl implements VouchUserService {	
	private static final Log LOG = LogFactory.getLog(VouchUserServiceImpl.class);			
	public String getCurrentUser() {
		String userName = "";
		try {
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication auth = securityContext.getAuthentication();
			UserDetails userCtx = (UserDetails) auth.getPrincipal();
			userName = userCtx.getUsername();
			LOG.info("getCurrentUser=="+userName);
		} catch (Throwable e) {
			LOG.error("fails to get user", e);
			throw new RuntimeException("get user fails", e);
		}
		return userName;
	}
}
