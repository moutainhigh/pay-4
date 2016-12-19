package com.pay.app.service.subscribe;

import java.util.List;
import java.util.Map;

import com.pay.app.dto.SubscribeNotifyDto;
import com.pay.app.model.SubscribeNotify;
import com.pay.base.exception.matrixcard.MatrixCardException;

/**
 * @author jim_chen
 * @version 2010-10-12
 */
public interface SubscribeNotifyService {

	/**
	 * 根据会员号，订阅类型确定一条记录 (同 一类型，同一会员只能订阅（站内信，短信，邮件中的一种）)
	 * 
	 * @param memberCode
	 * @param type
	 * @return
	 */
	SubscribeNotify querySubscribeNotifyByType(Long memberCode, Long type);

	SubscribeNotifyDto saveSubscribeNotify(SubscribeNotifyDto dto);

	/**保存订阅纪录
	 * @param dtoList
	 * @return
	 * @throws MatrixCardException
	 */
	boolean saveSubscribeNotifyRdTx(List<SubscribeNotifyDto> dtoList) throws MatrixCardException;

	/**批量新增
	 * @param subscribeNotifyList
	 * @return
	 * @throws MatrixCardException
	 */
	boolean batchInsertRdTx(List<SubscribeNotify> subscribeNotifyList) throws MatrixCardException;

	/**批量更新
	 * @param subscribeNotifyList
	 * @return
	 * @throws MatrixCardException
	 */
	boolean batchUpdateRdTx(List<SubscribeNotify> subscribeNotifyList) throws MatrixCardException;
	
	/**
	 * @param paramMap
	 * @return
	 */
	SubscribeNotify querySubscribeNotify(Map<String, Object> paramMap);

}
