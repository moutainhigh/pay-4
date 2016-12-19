 /** @Description 
 * @project 	poss-withdraw
 * @file 		FoAbstractParaser.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
*/
package com.pay.fundout.reconcile.common.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pay.fundout.reconcile.common.parser.impl.FoUnionPayParserFileImpl;
import com.pay.fundout.reconcile.common.util.ReconcileConstants;
import com.pay.fundout.reconcile.dao.fileservice.QueryReconcileFileDao;
import com.pay.fundout.reconcile.dao.fileservice.QueryReconcileRecordDao;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportFile;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportRecord;
import com.pay.inf.dto.PageMsgDto;
import com.pay.poss.base.common.fileprocess.io.FileHandler;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.PossUntxException;

/**
 * <p>文件解析上传抽象类</p>
 * @author Henry.Zeng
 * @since 2010-9-21
 * @see 
 */
public final class FoParserFileHandler {
	 
	protected transient Log log = LogFactory.getLog(getClass());
	
	private QueryReconcileFileDao queryReconcileFileDao;
	
	private QueryReconcileRecordDao reconcileRecordDao;
	
	public void setReconcileRecordDao(
		QueryReconcileRecordDao reconcileRecordDao) {
	    this.reconcileRecordDao = reconcileRecordDao;
	}
	
	public void setQueryReconcileFileDao(QueryReconcileFileDao queryReconcileFileDao) {
		this.queryReconcileFileDao = queryReconcileFileDao;
	}
	
	/**
	 * 解析文件
	 * @param orginalFile
	 * @param importFile
	 * @param msgMap
	 * @return
	 * @throws PossUntxException
	 */
	public Map<String,Object> parserFile(CommonsMultipartFile orginalFile,
				ReconcileImportFile importFile,Map<String,PageMsgDto> msgMap) 
					throws PossUntxException{
		
		 // TODO 目前采用这种方式做,以后可以优化到规则引擎实现
		 Map<String,Object> validataInfo = new HashMap<String,Object>(1);
		 
		//PageMsgDTO pageMsgDTO;
		 
		// 1 保存文件信息表		
		long count = this.saveFile(importFile,orginalFile);
		if(count == 0) {
			// pageMsgDTO = msgMap.get("saveFileInfoError");
			// validataInfo.put("validDataInfo", pageMsgDTO.getMessage());
		    	validataInfo.put("validDataInfo", "保存文件失败!");
		    	importFile.setStatus(new Long(ReconcileConstants.RECONCILE_FILE_STATUS_2));
		    	this.saveFileInfo(importFile);
			return validataInfo;
		}
		//设置为上传文件成功
		importFile.setStatus(new Long(ReconcileConstants.RECONCILE_FILE_STATUS_1));
		this.saveFileInfo(importFile);		
		
		// 2 文件解析
		List<ReconcileImportRecord> arrayList ;
		try {
			FoParserFile foParserFile = new FoUnionPayParserFileImpl() ;//FoParserFileFactory.createFoParserFile("rc_" + importFile.getWithdrawBankId());
			arrayList = foParserFile.parserFile2List(orginalFile.getInputStream(), importFile);
		}catch(Exception e){
			log.error(e.getMessage(),e);
			//pageMsgDTO =  msgMap.get("parserFile2ListError");
			// validataInfo.put("validDataInfo", pageMsgDTO.getMessage());
			validataInfo.put("validDataInfo","文件解析失败！");
			importFile.setStatus(new Long(ReconcileConstants.RECONCILE_FILE_STATUS_4));
		    	this.updateFileInfo(importFile);
			return validataInfo;
		}
		//设置为文件解析成功
		importFile.setStatus(new Long(ReconcileConstants.RECONCILE_FILE_STATUS_3));
		
		//设置文件编号
		for(ReconcileImportRecord temp : arrayList){
		    temp.setFileId(importFile.getFileId());
		}
		
		// 3 入库到import表
		count = this.saveFileImportRecordInfo(arrayList);
		if(count == 0) {
			//pageMsgDTO =  msgMap.get("saveFileImportInfotError");
			//validataInfo.put("validDataInfo", pageMsgDTO.getMessage());
		    	validataInfo.put("validDataInfo","导入银行对账文件出现重复数据!");
		    	importFile.setStatus(new Long(ReconcileConstants.RECONCILE_FILE_STATUS_6));
		    	this.updateFileInfo(importFile);
			return validataInfo;
		}
		//设置为导入数据成功
		importFile.setStatus(new Long(ReconcileConstants.RECONCILE_FILE_STATUS_5));
		
		//更新文件信息
		this.updateFileInfo(importFile);
		
		/*// 5 调用存储过程对账
		Map<String,Object> param = new HashMap<String, Object>();
//		param.put("in_batch_num",importFile.getBatchNum() );
//		param.put("in_bank_code", importFile.getBankCode());
//		param.put("in_file_ky", importFile.getFileKy());
		count = this.callProc(param);
		if(count == 0){
			pageMsgDTO =  map.get("callProcError");
			validataInfo.put("validataInfo", pageMsgDTO.getMessage());
			return validataInfo;
		}*/	
		
		validataInfo.put("validDataInfo","文件上传成功!");
		return validataInfo;
	}
	/**
	 * 保存文件信息表
	 * @param <T>
	 * @param pojo
	 * @return
	 */
	long saveFile(ReconcileImportFile reconcileImportFile,CommonsMultipartFile orginalFile) {
		//TODO 查询文件名称是否重复操作在sql语句处理
	    boolean flag = queryReconcileFileDao.reconcileFileNameExist(reconcileImportFile);
	    if(flag){
		return 0;
	    }
	    //保存文件
	    FileHandler.getFileHandler().uploadFile(orginalFile);	    
	    return 1;		
	}
	
	//保存文件信息
	private void saveFileInfo(ReconcileImportFile reconcileImportFile){
	    queryReconcileFileDao.addReconcileFileInfo(reconcileImportFile);
	}
	/**
	 * 保存导入文件信息表
	 * @param <T>
	 * @param pojo
	 * @return
	 */
	long saveFileImportRecordInfo(List<ReconcileImportRecord> withdrawImportRecordList) {
		try{
		    reconcileRecordDao.insert(withdrawImportRecordList);
		    return 1;
		}catch (PlatformDaoException e) {
			log.error(e.getMessage(), e);
			return 0;
		}
	}
	/**
	 * 更新文件信息状态
	 * @param <T>
	 * @param pojo
	 * @return
	 */
	private void updateFileInfo(ReconcileImportFile importFile) {
	    ReconcileImportFile temp = new ReconcileImportFile();
	    temp.setStatus(importFile.getStatus());
	    temp.setFileId(importFile.getFileId());
	    queryReconcileFileDao.updateImportFile(temp);
	}
	
	/**
	 * 【in_batch_num  :批次号
	 *   in_bank_code  :银行编号
	 *   in_file_ky    :文件编号 
	 *   out_rss_val   :返回结果
	 *   】
	 * 调用存储过程
	 * @param param
	 * @return
	 * @throws PossUntxException
	 */
	int callProc(Map<String,Object> param) throws PossUntxException{
		try{
			log.debug("param====="+param);
			int count = 1;
//			param = importResultDao.callProc(param);
//			if(param != null){
//				if("00".equals(param.get("out_rss_val"))){
//					count = 0;
//				}
//			}
			return count;
		}catch (PlatformDaoException e) {
			log.error(e.getMessage(),e);
			return 0;
		}
	}
	
}
