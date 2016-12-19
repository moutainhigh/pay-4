 /** @Description 
 * @project 	poss-withdraw
 * @file 		FoAbstractParaser.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-21		Henry.Zeng			Create 
*/
package com.pay.fundout.withdraw.service.result.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fundout.batchinfo.service.parfile.FileParseService;
import com.pay.fundout.withdraw.dao.fileservice.QueryBatchFileDao;
import com.pay.fundout.withdraw.dao.result.WdReconcileResultDao;
import com.pay.fundout.withdraw.dto.result.WithdrawImportFileDTO;
import com.pay.fundout.withdraw.model.result.WithdrawImportFile;
import com.pay.fundout.withdraw.model.result.WithdrawImportRecord;
import com.pay.fundout.withdraw.service.result.ParserFileHandlerService;
import com.pay.poss.base.exception.PlatformDaoException;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;

/**
 * <p>文件解析上传抽象类</p>
 * @author Henry.Zeng
 * @since 2010-9-21
 * @see 
 */
public class ParserFileHandlerServiceImpl implements ParserFileHandlerService{
	protected transient Log log = LogFactory.getLog(getClass());
	
	private FileParseService fileParseService;
	private QueryBatchFileDao queryBatchFileDao;
	private WdReconcileResultDao importResultDao;
	
	/**
	 * @param fileParseService the fileParseService to set
	 */
	public void setFileParseService(FileParseService fileParseService) {
		this.fileParseService = fileParseService;
	}
	
	public void setQueryBatchFileDao(final QueryBatchFileDao queryBatchFileDao) {
		this.queryBatchFileDao = queryBatchFileDao;
	}
	
	//set注入
	public void setImportResultDao(final WdReconcileResultDao importResultDao) {
		this.importResultDao = importResultDao;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String,Object> parserFileRdTx(WithdrawImportFileDTO importFileDto) throws PossException{
		try {
			WithdrawImportFile importFile = new WithdrawImportFile();
			BeanUtils.copyProperties(importFileDto, importFile);
			
			// 1更新批次文件信息为已导入
			/**
			 *  如果导入的是银行结果文件：批次文件状态 8---->4  
			 *  如果导入的是复核银行结果文件：批次文件状态 3---->9
			 */
			BatchFileInfo batchFileInfoParam = new BatchFileInfo();
			batchFileInfoParam.setBatchNum(importFile.getBatchNum());
			batchFileInfoParam.setFileKy(importFile.getgFileKy());
			if("FC".equals(importFile.getBussinessType())){//复核导入
				batchFileInfoParam.setOldBatchFileStatus(3);
				batchFileInfoParam.setBatchFileStatus(9L);
			}else if("FR".equals(importFile.getBussinessType())){//结果导入
				//batchFileInfoParam.setOldBatchFileStatus(8);
				batchFileInfoParam.setBatchFileStatus(4L);
				batchFileInfoParam.setImportTime(new Date());
			}
			boolean intStatusFetch = queryBatchFileDao.update("fundoutStatus.fo_updateBatchFileInfoByFileky",batchFileInfoParam);
			if(!intStatusFetch){
				log.error("导入银行文件：批次文件状态"+ batchFileInfoParam.getOldBatchFileStatus()+"---->"+4+"失败batchNum:"+batchFileInfoParam.getBatchNum()+" fileKy:"+batchFileInfoParam.getFileKy());
				throw new PossException("导入银行文件：批次文件状态"+ batchFileInfoParam.getOldBatchFileStatus()+"---->"+4+
						"失败batchNum:"+batchFileInfoParam.getBatchNum()+" fileKy:"+batchFileInfoParam.getFileKy(),
						ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			}		
			Map<String,Object> validataInfo = new HashMap<String,Object>(1);
			validataInfo.put("validataInfo", "scuess");
			
			// 2保存文件信息表
			long count = this.saveFileInfo(importFile);
			if(count == 0) {
				log.error("保存文件信息表失败:"+importFile);
				throw new PossException("保存文件信息表失败:"+importFile,ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			}
			importFile.setFileKy(count);
			// 3 文件解析
			Map<String,String> paramsFileInfo = new HashMap<String, String>();
			paramsFileInfo.put("FILE_PATH",importFile.getFilePath());
			paramsFileInfo.put("FILE_NAME",importFile.getFileName());
			paramsFileInfo.put("BANK_CODE",importFile.getBankCode());
			paramsFileInfo.put("BUSINESS_TYPE",importFile.getBussinessType()+"_");
			paramsFileInfo.put("FILE_NO",importFile.getFileKy().toString());
			paramsFileInfo.put("BATCH_NUM",importFile.getBatchNum());
			paramsFileInfo.put("TRADE_TYPE",importFile.getTradeType());
			paramsFileInfo.put("G_FILE_KY",importFile.getgFileKy().toString());
			
			//4解析文件,并保存信息
			Map<String,Object> parseMap = fileParseService.fileParse(paramsFileInfo);
			Integer status = (Integer)parseMap.get("parserStatus");
			if(status == null || 0 != status.intValue()){
				List<String> msgList = (List<String>)parseMap.get("msgList");
				StringBuffer sb = new StringBuffer();
				if(null != msgList){
					for (String string : msgList) {
						sb.append(string).append(";");
					}
				}
				throw new PossException("详细信息：" + sb.toString(),ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
			}
			// 5 调用存储过程对账
			Map<String,Object> param = new HashMap<String, Object>();
			param.put("in_g_file_ky", importFile.getgFileKy());
			param.put("in_file_ky", importFile.getFileKy());
			param.put("in_batch_num",importFile.getBatchNum());
			param.put("file_busi_type",Long.valueOf(importFile.getTradeType()));
			if("FR".equals(importFile.getBussinessType())){
				param.put("in_category",2l);
			}else{
				param.put("in_category",1l);
			}
			param.put("in_bank_code", importFile.getBankCode());
			// 该处为提现对账的存储过程
			count = this.callProc(param);
			if(count == 0){
				validataInfo.put("validataInfo", "调用存储过程失败");
			}
			return validataInfo;
		} catch (PossException e) {
			log.info("解析文件出现异常..." + e.getMessage(),e);
			throw new PossException("解析文件出现异常..." + e.getMessage(),ExceptionCodeEnum.UN_KNOWN_EXCEPTION,e);
		}
		
	}
	/**
	 * 保存文件信息表
	 * @param <T>
	 * @param pojo
	 * @return
	 */
	long saveFileInfo(WithdrawImportFile withdrawImportFile) {
		//TODO 查询文件名称是否重复操作在sql语句处理
		long count = importResultDao.saveWithdrawImportFile(withdrawImportFile);
		return count;
		
	}
	/**
	 * 保存导入文件信息表
	 * @param <T>
	 * @param pojo
	 * @return
	 */
	long saveFileImportRecordInfo(List<WithdrawImportRecord> withdrawImportRecordList) {
		try{
			importResultDao.saveFileImportRecordInfo(withdrawImportRecordList);
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
	int updateFileInfo(WithdrawImportFile importFile) {
		int count = importResultDao.updateImportFileInfo(importFile);
		return count;
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
	int callProc(Map<String,Object> param) throws PossException{
		try{
			log.debug("param====="+param);
			int count = 1;
			param = importResultDao.callProc(param);
			if(param != null){
				if("00".equals(param.get("out_rss_val"))){
					count = 0;
				}
			}
			return count;
		}catch (PlatformDaoException e) {
			log.error(e.getMessage(),e);
			return 0;
		}
	}
	
}
