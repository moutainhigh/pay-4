package com.pay.pe.manualbooking.service;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataPostingException;

public interface AccService {
	public AcctAttribDto getAcctAttribInfo(String acctCode);
	
	public String posting(VouchDataDto vouchDataDto) throws java.lang.RuntimeException, VouchDataPostingException ;
	
	public boolean isAccountCodeNotExist(String acctCode) ;
}
