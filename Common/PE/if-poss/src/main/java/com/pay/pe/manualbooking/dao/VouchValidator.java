package com.pay.pe.manualbooking.dao;

import java.util.List;

import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchValidateMessage;


public interface VouchValidator {
	
	List<VouchValidateMessage> validate(VouchDataDto vouchDataDto);

}
