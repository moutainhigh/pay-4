package com.pay.poss.memberrelation.service;

import java.util.List;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.VouchRelationValidateMessage;



public interface RelationValidator {
	
	List<VouchRelationValidateMessage> validate(List<RelationDataDto> relationDataDto);

}
