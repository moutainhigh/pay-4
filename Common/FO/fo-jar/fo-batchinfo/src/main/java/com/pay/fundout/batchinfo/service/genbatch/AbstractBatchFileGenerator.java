/** @Description 
 * @project 	fo-batchinfo
 * @file 		AbstractFileGenerator.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		zengli			Create 
 */
package com.pay.fundout.batchinfo.service.genbatch;

import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.Assert;

import com.pay.fundout.bankfile.common.util.BankFileUtil;
import com.pay.fundout.bankfile.generator.AbstractBankFileGenerator;
import com.pay.fundout.bankfile.generator.BankFileGenerator;
import com.pay.fundout.bankfile.generator.impl.DefaultBankFileGenerator;
import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.fundout.bankfile.generator.model.FileSummaryModel;
import com.pay.fundout.batchinfo.service.model.BatchFileRecord;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.ISequenceable;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.lucene.common.util.Provinces;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.lucene.service.LuceneService;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchInfo;
import com.pay.util.StringUtil;


public abstract class AbstractBatchFileGenerator implements
		BatchFileGenerateService {
	protected transient Log logger = LogFactory.getLog(getClass());
	protected BaseDAOImpl daoService = (BaseDAOImpl) ContextService
			.getBean(BaseDAO.BEAN_ID);
	protected LuceneService luceneService;
	protected BankService bankService;
	protected CityService cityService;
	protected ProvinceService provinceService;
	private BankFileGenerator defaultBankFileGenerator = new DefaultBankFileGenerator();
	private TransactionTemplate transactionTemplate;
	protected BankBrancheInfoService bankBrancheInfoService;
	
/*	private WithdrawAuditDao withdrawAuditDao = (WithdrawAuditDao) ContextService
			.getBean("fundout-withraw-withdrawAuditDao");
	*/

	/**
	 * @param transactionTemplate
	 *            the transactionTemplate to set
	 */
	public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}


	public void setBankBrancheInfoService(
			BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}


	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	/**
	 * @param luceneService
	 *            the luceneService to set
	 */
	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}
/*	@SuppressWarnings("unchecked")
	@Override
	public  void createFileRecord(Task task){
		Long fileKy = (Long) daoService.findObjectByCriteria(
				"bankfile.queryBatchFileInfoKy", "");
		Long fileKy=Long.parseLong(System.getProperty("fileKy"));
		String batchNum = task.getBatchNum();//批次号
		WithdrawAuditQuery auditQuery = new WithdrawAuditQuery();
		auditQuery.setStatus("4"); // 复核通过
		auditQuery.setBatchStatus2("1");
		auditQuery.setBatchNo(batchNum);
		List<WithdrawQueryOrder> wq = withdrawAuditDao.findWdOrder(
				"WF.withdrawAuditQueryByBatchNum", auditQuery);
	
		List<BatchFileRecord> batchFileRecords=new ArrayList<BatchFileRecord>();
		for (WithdrawQueryOrder withdrawQueryOrder : wq) {
			BatchFileRecord bf = new BatchFileRecord();
			bf.setgFileKy(fileKy);//主外键
			bf.setTradeSeq(withdrawQueryOrder.getOrderDto().getSequenceId());
			bf.setTradeAmount(withdrawQueryOrder.getOrderDto().getPayerAmount());

			bf.setAccountNo(withdrawQueryOrder.getOrderDto().getBankAcct());
			bf.setAccountName(withdrawQueryOrder.getOrderDto().getAccountName());
			bf.setTradeDate(withdrawQueryOrder.getOrderDto().getCreateTime());
			bf.setRemark(withdrawQueryOrder.getOrderDto().getOrderRemarks());
			bf.setStauts(1);
			bf.setBankBranch(withdrawQueryOrder.getOrderDto()
					.getPayeeBankName());
			batchFileRecords.add(bf);
		}
		daoService.batchCreate("bankfile.insertBatchFileRecords",
				batchFileRecords);
	}
	*/
	@SuppressWarnings("unchecked")
	@Override
	public void generateBatchFile(Map<String, String> fileInfo)
			throws PossException {

		Assert.notNull(fileInfo, "文件信息必须不为空");

		String batchNum = fileInfo.get("BATCH_NUM");

		if (StringUtils.isEmpty(batchNum)) {
			throw new IllegalArgumentException("无效参数 [" + fileInfo + "]");
		}
		/**
		 * 获取生成器
		 */
		Set<BankFileGenerator> generators = getGenerators(batchNum);
		if (generators.isEmpty()) {
			logger.error("不存在有效生成器,请检查配置参数或确认本批次是否有业务数据要生成文件.系统采用默认生成器,将生成空文件. ["+ fileInfo + "]");
			generators.add(defaultBankFileGenerator);
		}

		// 计算概要信息
		List<FileSummaryModel> fileSummarys = calcMasterInfo(batchNum);

		BatchFileInfo batchFileInfo = null;
		
		//把文件存到aliyun上            added by yanshichuan
		fileInfo.put("STORED_ON_ALIYUN","1");
		for (FileSummaryModel fileSummaryModel : fileSummarys) {
			// 生成内部文件
			if ("TOTAL_BANK".equals(fileSummaryModel.getBankCode())) {
				// 内部概要
				batchFileInfo = generateInnerSummerFile(fileSummaryModel,
						fileInfo);
				if (batchFileInfo != null) {
					Long fileKy = (Long) daoService.findObjectByCriteria(
							"bankfile.queryBatchFileInfoKy", "");
					System.setProperty("fileKy", fileKy+"");
					batchFileInfo.setFileKy(fileKy);
					batchFileInfo.setChannelCode("1");
					batchFileInfo.setFileBusiType("I");
					saveBatchFileInfo(batchFileInfo);
				}
				List<FileDetailMode> innerDetailList = bulidFileDetailList(batchNum);
				// 生成内部明细文件
				batchFileInfo = generateInnerDetailFile(fileSummaryModel,
						innerDetailList, fileInfo);
				if (batchFileInfo != null) {
					Long fileKy = (Long) daoService.findObjectByCriteria(
							"bankfile.queryBatchFileInfoKy", "");
					batchFileInfo.setFileKy(fileKy);
					batchFileInfo.setChannelCode("2");
					batchFileInfo.setFileBusiType("I");
					saveBatchFileInfo(batchFileInfo);
				}
			} else {
				String busiType = "".intern();
				String identity = "".intern();
				String tradeType = "".intern();
				List<Object> bankDetailList = null;
				// 迭代生成银行文件
				for (BankFileGenerator generator : generators) {
					if (generator.supportBank(fileSummaryModel.getBankCode())) {
						identity = ((ISequenceable) daoService.getSequenceGenerator()).nextStringValue();
						busiType = transfString(generator.supportBusiTypeCode());
						tradeType = transfAcctType(generator.supportTradeType());
						// 生成银行文件
						batchFileInfo = generateBankSummerFile(generator,
								fileInfo, fileSummaryModel.getBankCode());
						if (batchFileInfo != null) {
							Long fileKy = (Long) daoService.findObjectByCriteria(
									"bankfile.queryBatchFileInfoKy", "");
							batchFileInfo.setFileKy(fileKy);
							batchFileInfo.setChannelCode(identity);
							batchFileInfo.setBusiType(busiType);
							batchFileInfo.setFileBusiType(tradeType);
							saveBatchFileInfo(batchFileInfo);
						}
						// 生成银行明细文件
						Map<String, Object> resultMap = generateBankDetailFile(
								generator, fileInfo, fileSummaryModel
										.getBankCode());
						batchFileInfo = (BatchFileInfo) resultMap
								.get("batchFileInfo");
						if (batchFileInfo != null) {
							Long fileKy = (Long) daoService.findObjectByCriteria(
									"bankfile.queryBatchFileInfoKy", "");
							// 充退不记录该表
							if (busiType != null
									&& !"R".equals(batchFileInfo.getBusiType())) {
								batchFileInfo.setBusiType(busiType);
								bankDetailList = (List<Object>) resultMap
										.get("bankDetailList");
								// 插入生成文件明细表
								saveBatchFileRecord(bankDetailList, fileKy);
							}
							batchFileInfo.setChannelCode(identity);
							batchFileInfo.setFileBusiType(tradeType);
							batchFileInfo.setFileKy(fileKy);
							saveBatchFileInfo(batchFileInfo);
						}
					}else{
						logger.error("bank generator not support ..."+ generator + batchNum);
					}
				}
			}
		}
		BatchInfo batchInfo = new BatchInfo();
		batchInfo.setBatchNum(batchNum);
		updateBatchInfo(batchInfo);
	}
	
	
	/**
	 * 保存文件生成明细表
	 * 
	 * @param bankDetailList
	 * @param fileKy
	 */
	private void saveBatchFileRecord(List<Object> bankDetailList, Long fileKy) {
		List<BatchFileRecord> batchFileRecords = new ArrayList<BatchFileRecord>();
		if (bankDetailList != null && !bankDetailList.isEmpty()) {
			FileDetailMode innerDetailModel = null;
			BatchFileRecord batchFileRecord = null;
			List<String> updateList = new ArrayList<String>();
			for (Object obj : bankDetailList) {
				batchFileRecord = new BatchFileRecord();
				batchFileRecord.setgFileKy(fileKy);
				innerDetailModel = (FileDetailMode) obj;
				batchFileRecord.setAccountName(BankFileUtil
						.trimAllWhitespace(innerDetailModel.getAccountName()));
				batchFileRecord.setAccountNo(innerDetailModel.getBankAcct());
				batchFileRecord.setBankBranch(innerDetailModel.getBankBranch());
				//batchFileRecord.setBankBranch(innerDetailModel.getBankName());
				batchFileRecord.setRemark(innerDetailModel.getOrderRemarks());
				BigDecimal amount = innerDetailModel.getAmount();
				if (amount == null) {
					amount = innerDetailModel.getApplyAmount();
				}
				batchFileRecord.setTradeAmount(Long.valueOf(amount.toString()));
				batchFileRecord.setTradeSeq(Long.valueOf(innerDetailModel
						.getOrderSeq()));
				batchFileRecords.add(batchFileRecord);
				updateList.add(innerDetailModel.getOrderSeq());
			}
			daoService.batchCreate("bankfile.insertBatchFileRecords",
					batchFileRecords);

			// 给工单打上小批次
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderSeqList", updateList);
			params.put("fileKy", fileKy);
			daoService.update("bankfile.giveWorderOrderFileKy", params);
		}
	}

	private void saveBatchFileInfo(final BatchFileInfo batchFileInfo) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					daoService.create("bankfile.insertBatchFileInfoToGenerator",
							batchFileInfo);
				} catch (Exception e) {
					logger.error("保存批次对应文件错误 [" + batchFileInfo + "]", e);
					status.setRollbackOnly();
				}
			}
		});
	}

	private Set<BankFileGenerator> getGenerators(String batchNum) {
		Set<BankFileGenerator> result = new HashSet<BankFileGenerator>();

		List<String> classNames = null;
		try {
			classNames = calcChannel(batchNum);
		} catch (PossException e1) {
			logger.error("根据批次号获取解析器出现错误 [" + batchNum + "]", e1);
		}
		logger.info("解析器信息...." + classNames.toString());

		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class<?> groovyClass;
		for (String className : classNames) {
			try {
				groovyClass = loader.parseClass(new File(className));
				Object groovyObject = groovyClass.newInstance();
				if (groovyObject instanceof AbstractBankFileGenerator) {
					AbstractBankFileGenerator baseGenerator = (AbstractBankFileGenerator) groovyObject;
					result.add(baseGenerator);
				}
			} catch (Exception e) {
				logger.error("根据批次号获取解析器出现错误 [" + batchNum + "]", e);
				continue;
			}
		}

		return result;
	}

	private void updateBatchInfo(BatchInfo batchInfo) throws PossException {
		batchInfo.setStatus(4L);
		daoService.update("bankfile.fo-UpdateBatchInfo", batchInfo);
	}

	protected abstract List<FileDetailMode> bulidFileDetailList(String batchNum)
			throws PossException;

	protected abstract void updateWorkorder(String batchNum)
			throws PossException;

	protected abstract BatchFileInfo generateBankSummerFile(
			BankFileGenerator generator, Map<String, String> fileInfo,
			String bankCode) throws PossException;

	protected abstract BatchFileInfo generateInnerDetailFile(
			FileSummaryModel fileSummaryModel,
			List<FileDetailMode> innerDetailList, Map<String, String> fileInfo)
			throws PossException;

	protected abstract BatchFileInfo generateInnerSummerFile(
			FileSummaryModel fileSummaryModel, Map<String, String> fileInfo)
			throws PossException;

	protected abstract Map<String, Object> generateBankDetailFile(
			BankFileGenerator generator, Map<String, String> fileInfo,
			String bankCode) throws PossException;

	protected abstract List<String> calcChannel(String batchNum)
			throws PossException;

	protected abstract List<FileSummaryModel> calcMasterInfo(String batchNum)
			throws PossException;

	private String transfAcctType(String acctType) {
		if (StringUtils.isNotEmpty(acctType)) {
			if ("ALL".equals(acctType)) {
				return "2";
			} else {
				return acctType;
			}
		} else {
			return "2";
		}
	}

	private String transfString(List<String> list) {
		if (list.isEmpty()) {
			return "";
		} else {
			StringBuffer strBuf = new StringBuffer();
			for (String string : list) {
				strBuf.append(string).append(",");
			}
			if (0 != strBuf.length()) {
				return strBuf.deleteCharAt(strBuf.length() - 1).toString();
			} else {
				return "";
			}
		}
	}

	/**
	 * 获取联行号
	 * 
	 * @param fileDetailMode
	 * @return
	 */
	protected String getUnionBankCode(FileDetailMode fileDetailMode) {
		String unionBankCode = "0";
		if (StringUtils.isNotEmpty(fileDetailMode.getBankBranch())) {
			
			

			String bankNumber = fileDetailMode.getBankNumber();
			if (!StringUtil.isEmpty(bankNumber) && !fileDetailMode.getBankKy().equals("10003001")) {
				return bankNumber;
			}

			// update by ch-ma
			SearchParamInfoDTO paramInfoDTO = new SearchParamInfoDTO();
			paramInfoDTO.setResultSize(10);
			paramInfoDTO.setBankName(fileDetailMode.getBankName());
			paramInfoDTO.setProvinceName(fileDetailMode.getProvinceName());
			paramInfoDTO.setCityName(fileDetailMode.getCityName());
			paramInfoDTO.setBankKaihu(fileDetailMode.getBankBranch());

			// 判断是否需特定银行联号
			if ("10003001".equals(fileDetailMode.getWithdrawBankCode()) && fileDetailMode.getBankKy().equals(fileDetailMode.getWithdrawBankCode())) {
				
				if("0".equals(String.valueOf(fileDetailMode.getTradeType()))){
					if(Provinces.SHANGHAI_NAME.equals(paramInfoDTO.getProvinceName())){
						paramInfoDTO.setType(2);
					}else{
						paramInfoDTO.setType(1);
					}
				}else{
					paramInfoDTO.setType(2);
				}
			} else {
				paramInfoDTO.setType(1);
			}
			unionBankCode = luceneService.searchBankUnionCode(paramInfoDTO);
		}
		return unionBankCode;
	}

}
