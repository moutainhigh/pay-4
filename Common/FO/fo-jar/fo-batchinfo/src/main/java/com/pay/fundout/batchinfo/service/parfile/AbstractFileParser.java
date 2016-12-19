/**
 *  File: AbstractFileParser.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-10      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.batchinfo.service.parfile;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.bankfile.parser.BaseFileParser;
import com.pay.fundout.bankfile.parser.model.FileParserMode;
import com.pay.fundout.bankfile.parser.model.WithdrawImportRecordModel;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.env.data.IDataService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.CodeMapping;
import com.pay.util.StringUtil;

/**
 * @author Jason_wang
 *
 */
public abstract class AbstractFileParser implements FileParseService {

	protected transient Log logger = LogFactory.getLog(getClass());
	
	protected BaseDAO iBaseDao;
	
	private IDataService dataService;
	
	public void setiBaseDao(BaseDAO iBaseDao) {
		this.iBaseDao = iBaseDao;
	}
	
	/**
	 * @param dataService
	 *            the dataService to set
	 */
	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}
	
	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.service.FileParseService#fileParse(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> fileParse(Map<String, String> fileInfo) throws PossException {
		//获取文件路径、文件名、银行编号、业务类型、文件编号
		String filePath = fileInfo.get("FILE_PATH");
		String fileName = fileInfo.get("FILE_NAME");
		String bankCode = fileInfo.get("BANK_CODE");
		String businessType = fileInfo.get("BUSINESS_TYPE");
		String fileNo = fileInfo.get("FILE_NO");
		String tradeType = fileInfo.get("TRADE_TYPE");
		String gFileKy = fileInfo.get("G_FILE_KY");
		
		if(StringUtil.isEmpty(filePath) || StringUtil.isEmpty(fileName) ||
				StringUtil.isEmpty(bankCode) || StringUtil.isEmpty(businessType) ||
				StringUtil.isEmpty(fileNo)){
			logger.info("文件参数信息为空..." + fileInfo);
			throw new PossException("文件参数信息为空..." + fileInfo,ExceptionCodeEnum.NOT_FOUND_EXCEPTION);
		}
		
		//获取解析器
		BaseFileParser parsers = getParser(bankCode,businessType,tradeType);
		if(null == parsers){
			logger.info("获取文件解析器为空..." + fileInfo);
			throw new PossException("获取文件解析器为空..." + fileInfo,ExceptionCodeEnum.NOT_FOUND_EXCEPTION);
		}
		
		//解析文件
		Map<String,Object> infoMap = parsers.parserFileInfo(fileInfo);
		if(infoMap.isEmpty()){
			logger.info("文件信息为空..." + fileInfo);
			throw new PossException("文件信息为空..." + fileInfo,ExceptionCodeEnum.NOT_FOUND_EXCEPTION);
		}
		//TODO  加事务控制，确认修文件唯一性
		int count = 0 ;
		List<String> failList = (List<String>) infoMap.get("msgList");
		if(failList == null || failList.isEmpty()){
			//没有流水号，特殊处理
			if("10001001".equals(bankCode) ||   //工行文件
					"10005001".equals(bankCode)  ||  //交行文件
						("10004001".equals(bankCode)) //建行批量转账
							|| "10014001".equals(bankCode)  //光大
							|| "10003001".equals(bankCode)  //中行
							|| "10013001".equals(bankCode)  //中信
							|| "10002001".equals(bankCode)  //农行
							|| "10006001".equals(bankCode)  //招商
							|| "10012001".equals(bankCode)  //华夏
							|| "10016001".equals(bankCode)  //深发
							|| "10017001".equals(bankCode)  //平安
							|| "10001005".equals(bankCode)  //工行上海
							|| "10008001".equals(bankCode)  //兴业银行
							|| "10007001".equals(bankCode)  //民生银行
			){
				processBankFileInfo(infoMap,gFileKy);
			}else{
				
				List<WithdrawImportRecordModel> detailList = (List<WithdrawImportRecordModel>)infoMap.get("detailList");
				Map<Long,Long> map = new HashMap<Long, Long>();
				for(WithdrawImportRecordModel withdrawImportRecordModel : detailList){
					if(map.containsValue(withdrawImportRecordModel.getOrderSeq())){
						count++;
						failList.add("出现重复交易流水号,请重新核对,交易流水号是:"+withdrawImportRecordModel.getOrderSeq()+"\n");
					}else{
						map.put(withdrawImportRecordModel.getOrderSeq(), withdrawImportRecordModel.getOrderSeq());
					}
				}
			}
			if(count == 0) {
				//保存解析数据
				FileParserMode fileParser = saveFileInfoRecord(infoMap,fileInfo);
				//修改文件状态
				updateFileInfo(fileParser);
				
			}else{
				infoMap.put("parserStatus",1);
			}
			
			return infoMap;
		}else{
			return infoMap;
		}
		
	}
	/**
	 * 更新文件信息表
	 * @param fileParser
	 * @throws PossException
	 */
	protected abstract void updateFileInfo(FileParserMode fileParser) throws PossException;
	/**
	 * 保存文件明细表
	 * @param resultList
	 * @param fileInfo
	 * @return
	 * @throws PossException
	 */
	protected abstract FileParserMode saveFileInfoRecord(Map<String, Object> resultList, Map<String, String> fileInfo) throws PossException;

	private BaseFileParser getParser(String bankCode, String businessType , String tradeType )throws PossException{
		String code = "".intern() + tradeType+"_" +businessType + bankCode;
		CodeMapping codeMapping = dataService.getCodeMapping("PARSER", "BANK_FILE",code);
		if(codeMapping==null){
			logger.error("没有找到对应的解析groovy文件 [" + code + "]");
			return null;
		}
		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		String filePath = codeMapping.getValue();
		Class<?> groovyClass;
		try {
			groovyClass = loader.parseClass(new File(filePath));
			BaseFileParser groovyObject = (BaseFileParser) groovyClass.newInstance();
			return groovyObject;
		} catch (Exception e) {
			logger.error("加载groovy文件异常 [" + code + "]",e);
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void processBankFileInfo(Map<String, Object> resultList,String gFileKy){
		List<WithdrawImportRecordModel> detailList = (List<WithdrawImportRecordModel>)resultList.get("detailList");
		if(detailList.isEmpty()){
			logger.info("文件信息为空...");
		}else{
			for(WithdrawImportRecordModel temp:detailList){
				//1.根据金额，收款方账号，收款方名称 去batch_file_record（生成文件记录表）中查trade_seq，file_ky，status=1&&rownum=1
				Long tradeSeq = (Long) iBaseDao.findObjectByCriteria("bfwithdraw.fundout-withdraw-queryFileInfoRecord", temp);
				if(null!=tradeSeq){
					temp.setOrderSeq(tradeSeq);
					//2.若查到了交易流水就改batch_file_record表status =2
					Map<String,Object> paras = new HashMap<String,Object>();
					paras.put("oldStatus", 1);
					paras.put("newStatus", 2);
					paras.put("tradeSeq", tradeSeq);
					iBaseDao.update("bfwithdraw.updateWithdrawFileRecordStatus",paras);
				}
			}
			//*3.还原batch_file_record表status =1
			Map<String,Object> paras2 = new HashMap<String,Object>();
			paras2.put("oldStatus", 2);
			paras2.put("newStatus", 1);
			paras2.put("gFileKy", gFileKy);
			iBaseDao.update("bfwithdraw.updateWithdrawFileRecordStatus",paras2);
		}
	}

}
