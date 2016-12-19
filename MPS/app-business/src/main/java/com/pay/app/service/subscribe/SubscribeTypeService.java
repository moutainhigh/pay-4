package com.pay.app.service.subscribe;

import java.util.List;

import com.pay.app.dto.SubscribeTypeDto;

public interface SubscribeTypeService {
	
	int countSebscribeType(SubscribeTypeDto dto);

	List<SubscribeTypeDto> querySubscribeTypeDtoList(SubscribeTypeDto dto,Long memberCode);
}
