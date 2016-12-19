package com.pay.app.dao.subscribe;

import java.util.List;

import com.pay.app.dto.SubscribeTypeDto;
import com.pay.app.model.SubscribeType;
import com.pay.inf.dao.BaseDAO;

/**
 * 订阅类型
 * 
 * @author jim_chen
 * @version 2010-10-12
 */
public interface SubscribeTypeDAO extends BaseDAO {

	/**
	 * @param subscribeTypeDto
	 * @return
	 */
	List<SubscribeType> querySubscribeTypeList(SubscribeTypeDto subscribeTypeDto);

	/**
	 * 统计订阅类型的纪录
	 * 
	 * @param subscribeTypeDto
	 * @return
	 */
	int countSubscribeType(SubscribeTypeDto subscribeTypeDto);
}
