package com.pay.poss.externalmanager.service;

import com.pay.poss.externalmanager.dto.IsMemberLegal;

public interface IExternalService {
	public IsMemberLegal isMemberAndAccountActiv(String email,String orgCode);
	public IsMemberLegal isMemberLegal(String memberName,String loginName);
	
}
