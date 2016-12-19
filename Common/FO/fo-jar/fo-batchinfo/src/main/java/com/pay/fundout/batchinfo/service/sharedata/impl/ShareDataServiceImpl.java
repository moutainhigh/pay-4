package com.pay.fundout.batchinfo.service.sharedata.impl;

import java.util.List;
import java.util.Map;

import com.pay.fundout.batchinfo.dao.sharedata.BlackListDAO;
import com.pay.fundout.batchinfo.dao.sharedata.BlackListFileDAO;
import com.pay.fundout.batchinfo.service.model.BlackList;
import com.pay.fundout.batchinfo.service.model.BlackListFile;
import com.pay.fundout.batchinfo.service.sharedata.ShareDataService;
import com.pay.inf.dao.Page;

/**
 * 数据共享服务实现类
 * @author limeng
 *
 */
public class ShareDataServiceImpl implements ShareDataService {

	private BlackListDAO blackListDAO;
	
	private BlackListFileDAO blackListFileDAO;
	
	public void setBlackListDAO(BlackListDAO blackListDAO) {
		this.blackListDAO = blackListDAO;
	}

	public void setBlackListFileDAO(BlackListFileDAO blackListFileDAO) {
		this.blackListFileDAO = blackListFileDAO;
	}

	@Override
	public void createBlackList(BlackList blackList) {
		this.blackListDAO.create(blackList);
	}
	
	@Override
	public long createBlackListFile(BlackListFile blackListFile) {
		return (Long) this.blackListFileDAO.create(blackListFile);
	}
	
	@Override
	public boolean updateBlackListFile(BlackListFile blackListFile) {
		return this.blackListFileDAO.update(blackListFile);
	}

	@Override
	public BlackList findById(String id) {
		return this.blackListDAO.findById(Long.valueOf(id));
	}
	
	@Override
	public BlackListFile findBlackListFileById(long id) {
		return this.blackListFileDAO.findById(id);
	}

	@Override
	public BlackListFile findDealingFile() {
		List<BlackListFile> files = blackListFileDAO.findByQuery("blacklistfile.findDealingFile", null);
		if(files != null && files.size() > 0){
			return (BlackListFile)files.get(0);
		}
		return null;
	}
	
	@Override
	public Page<BlackListFile> queryBlackListFile(Page page, Map params) {
		return this.blackListFileDAO.findByQuery("blacklistfile.queryBlackListFile", page, params);
	}

}
