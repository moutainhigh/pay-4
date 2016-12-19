package com.pay.fundout.batchinfo.service.antimoney;

import com.pay.fundout.batchinfo.service.model.MessageObject;
import com.pay.inf.dao.Page;

/**
 * 反洗钱报文服务
 * @author limeng
 *
 */
public interface DocService {

	/**
	 * 创建报文
	 * @param message
	 */
	public void createMessage(MessageObject message);
	
	/**
	 * 根据主键找到对应的反洗钱实体记录
	 * @param id
	 * @return
	 */
	public MessageObject findById(Long id);
	
	/**
	 * 查询反洗钱报文文件
	 * @param page 分页参数
	 * @param params 查询参数
	 * @return
	 */
	public Page<MessageObject> queryMessageFile(Page page, MessageObject object);
}
