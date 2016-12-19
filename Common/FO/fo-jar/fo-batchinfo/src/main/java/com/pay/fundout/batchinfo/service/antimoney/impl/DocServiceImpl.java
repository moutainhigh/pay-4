package com.pay.fundout.batchinfo.service.antimoney.impl;

import com.pay.fundout.batchinfo.dao.antimoney.DocDAO;
import com.pay.fundout.batchinfo.service.antimoney.DocService;
import com.pay.fundout.batchinfo.service.model.MessageObject;
import com.pay.inf.dao.Page;

/**
 * 数据共享服务实现类
 * @author limeng
 *
 */
public class DocServiceImpl implements DocService {

	private DocDAO docDAO;
	
	public void setDocDAO(DocDAO docDAO) {
		this.docDAO = docDAO;
	}

	@Override
	public void createMessage(MessageObject message) {
		this.docDAO.create(message);
	}
	
	@Override
	public MessageObject findById(Long id) {
		return this.docDAO.findById(id);
	}
	
	@Override
	public Page<MessageObject> queryMessageFile(Page page, MessageObject object) {
		return this.docDAO.findByQuery("antimoney.queryMessageFile", page, object);
	}
}
