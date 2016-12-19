package com.pay.fundout.batchinfo.service.sharedata;

import java.util.Map;

import com.pay.fundout.batchinfo.service.model.BlackList;
import com.pay.fundout.batchinfo.service.model.BlackListFile;
import com.pay.inf.dao.Page;

/**
 * 数据共享服务
 * @author limeng
 *
 */
public interface ShareDataService {

	/**
	 * 创建黑名单
	 * @param blackList
	 */
	public void createBlackList(BlackList blackList);
	
	/**
	 * 创建黑名单文件
	 * @param blackListFile
	 * @return
	 */
	public long createBlackListFile(BlackListFile blackListFile);
	
	/**
	 * 更新黑名单文件
	 * @param blackListFile
	 * @return
	 */
	public boolean updateBlackListFile(BlackListFile blackListFile);
	
	/**
	 * 根据主键找到对应的黑名单实体记录
	 * @param id
	 * @return
	 */
	public BlackList findById(String id);
	
	/**
	 * 根据主键找到对应的黑名单文件实体记录
	 * @param id
	 * @return
	 */
	public BlackListFile findBlackListFileById(long id);
	
	/**
	 * 找到复核条件可以被写入的xml文件记录，规则：没被下载过，并且当前行数小于1000
	 * @return
	 */
	public BlackListFile findDealingFile();
	
	/**
	 * 查询黑名单文件
	 * @param page 分页参数
	 * @param params 查询参数
	 * @return
	 */
	public Page<BlackListFile> queryBlackListFile(Page page, Map params);
}
