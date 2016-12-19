/**
 * 
 */
package com.pay.rm.facade.impl;

import com.pay.rm.blackwhitelist.service.BlackWhiteListService;
import com.pay.rm.facade.BlackListService;

/**
 * @author chaoyue
 *
 */
public class BlackListServiceImpl implements BlackListService {

	private BlackWhiteListService blackWhiteListService;

	public void setBlackWhiteListService(BlackWhiteListService blackWhiteListService) {
		this.blackWhiteListService = blackWhiteListService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.rm.facade.BlackListService#blackListCheck(java.lang.String)
	 */
	@Override
	public String blackListCheck(String content) {
		// TODO Auto-generated method stub
		return null;
	}

}
