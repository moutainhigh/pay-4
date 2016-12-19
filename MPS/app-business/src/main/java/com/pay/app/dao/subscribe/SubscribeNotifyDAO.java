package com.pay.app.dao.subscribe;

import java.util.List;
import java.util.Map;

import com.pay.app.model.SubscribeNotify;
import com.pay.inf.dao.BaseDAO;

/**
 * @author jim_chen
 * @version 2010-10-8
 */
public interface SubscribeNotifyDAO extends BaseDAO {

	/**
	 * @param subscribeNotify
	 * @return
	 */
	List<SubscribeNotify> querySubscribeNotifyList(Map<String, Object> paramMap);

	/**
	 * @param paramMap
	 * @return
	 */
	int countSubscribeNotifyByMap(Map<String, Object> paramMap);

	/**
	 * 批量新增
	 * 
	 * @param subList
	 * @return
	 */
	boolean batchInsert(List<SubscribeNotify> subList);

	/**
	 * 批量更新
	 * 
	 * @param subList
	 * @return
	 */
	boolean batchUpdateRdTx(List<SubscribeNotify> subList);
}
