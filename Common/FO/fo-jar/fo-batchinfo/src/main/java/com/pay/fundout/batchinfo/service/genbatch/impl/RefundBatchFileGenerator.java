/** @Description 
 * @project 	fo-batcfileinfo
 * @file 		BaseRefundFileGenerator.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-9		zengli			Create 
 */
package com.pay.fundout.batchinfo.service.genbatch.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.pay.fundout.bankfile.common.util.CreatorFileDirUtil;
import com.pay.fundout.bankfile.generator.BankFileGenerator;
import com.pay.fundout.bankfile.generator.model.FileSummaryModel;
import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.fundout.batchinfo.service.genbatch.AbstractBatchFileGenerator;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.util.DateUtil;

/**
 * <p>
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-9
 * @see
 */
public class RefundBatchFileGenerator extends AbstractBatchFileGenerator {

	private static final int REFUND_WORKORDER_STATUS_1 = 1; // 已出批次
//	private static final String BATCH_FILE_TYPE_11 = "11"; // 内部概要文件类型
	private static final String BATCH_FILE_TYPE_12 = "12"; // 内部详细文件类型
//	private static final String BATCH_FILE_TYPE_21 = "21"; // 银行概要文件类型

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.fundout.bankfile.service.AbstractFileGenerator#
	 * bulidInnerDetailList(java.lang.String)
	 */
	@Override
	protected List<FileDetailMode> bulidFileDetailList(String batchNum) throws PossException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("BATCH_NUM", batchNum);
		return daoService.findByQuery("bfrefund.selectRefundBatchDTOByBatchNumAndBankCode", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.bankfile.service.AbstractFileGenerator#calcMasterInfo
	 * (java.lang.String)
	 */
	@Override
	protected List<FileSummaryModel> calcMasterInfo(String batchNum) throws PossException {
		try {
			List<FileSummaryModel> result = daoService.findByQuery("bfrefund.calcMasterInfoByBatchNum", batchNum);

			int totalCount = 0;
			BigDecimal totalAmount = new BigDecimal(0);
			for (FileSummaryModel tempInfo : result) {
				tempInfo.setBatchNum(batchNum);

				totalCount = totalCount + tempInfo.getTotalCount();
				totalAmount = totalAmount.add(tempInfo.getTotalAmount());
			}

			FileSummaryModel temp = new FileSummaryModel();
			temp.setBankCode("TOTAL_BANK");
			temp.setBatchNum(batchNum);
			temp.setTotalAmount(totalAmount);
			temp.setTotalCount(totalCount);

			result.add(temp);

			return result;
		} catch (Exception e) {
			logger.error("计算概要信息出现错误 [batchNum=" + batchNum + "]", e);
			throw new PossException("计算概要信息出现错误 [batchNum=" + batchNum + "]", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.fundout.bankfile.service.AbstractFileGenerator#
	 * generateInnerSummerFile
	 * (com.pay.fundout.bankfile.model.FileSummaryModel, java.util.Map)
	 */
	@Override
	protected BatchFileInfo generateInnerSummerFile(FileSummaryModel fileSummaryModel, Map<String, String> fileInfo) throws PossException {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.bankfile.service.AbstractFileGenerator#updateWorkorder
	 * (java.lang.String)
	 */
	@Override
	protected void updateWorkorder(String batchNum) throws PossException {
		FileDetailMode params = new FileDetailMode();
		params.setBatchNum(batchNum);
		params.setStatus(REFUND_WORKORDER_STATUS_1);// SQL_MAP已经设置过该值.
		daoService.update("bfrefund.updateWOBatchNumWithAuto", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.fundout.bankfile.service.AbstractFileGenerator#
	 * generateBankDetailFile
	 * (com.pay.fundout.bankfile.model.FileSummaryModel,
	 * com.pay.fundout.bankfile.spi.BankFileGenerator, java.util.Map)
	 */
	@Override
	protected Map<String,Object> generateBankDetailFile(BankFileGenerator generator, Map<String, String> fileInfo, String bankCode) throws PossException {
		// 获取银行明细信息
		String batchNum = fileInfo.get("BATCH_NUM");
		List<FileDetailMode> bankDetailList = bulidBankDetailList(batchNum, bankCode);
		// 生成银行明细文件
		BatchFileInfo batchFileInfo = generator.generateBankDetailFile(bankDetailList, fileInfo);
		batchFileInfo.setBusiType("R");
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bankDetailList", bankDetailList);
		resultMap.put("batchFileInfo", batchFileInfo);
		return resultMap;
	}

	private List<FileDetailMode> bulidBankDetailList(String batchNum, String bankCode) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("BANK_CODE", bankCode);
		params.put("BATCH_NUM", batchNum);
		return daoService.findByQuery("bfrefund.selectRefundBatchDTOByBatchNumAndBankCode", params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.fundout.bankfile.service.AbstractFileGenerator#
	 * generateInnerDetailFile
	 * (com.pay.fundout.bankfile.model.FileSummaryModel, java.util.List,
	 * java.util.Map)
	 */
	@Override
	protected BatchFileInfo generateInnerDetailFile(FileSummaryModel fileSummaryModel, List<FileDetailMode> innerDetailList,
			Map<String, String> fileInfo) throws PossException {
		fileInfo.put("BATCH_FILENAME_SUBFIX", ".xls");
		fileInfo.put("filetype", BATCH_FILE_TYPE_12);// 内部详细文件
		
		Map<String, Object> paramsMap = null; // 模板文件参数集合
		XLSTransformer transformer = null;
		Workbook workbook = null; // 最终输出文件
		String filePath = "".intern();

		try {
			// 如果参数为空则直接返回
			if (null == innerDetailList || innerDetailList.isEmpty() || null == fileInfo) {
				paramsMap = new HashMap<String, Object>();
				fileSummaryModel.setBankName(" ");
				fileSummaryModel.setBankCode(" ");
				fileSummaryModel.setShowTotalAmount(new BigDecimal(0));
				fileSummaryModel.setWithdrawBankCode(" ");
				paramsMap.put("masterInfo", fileSummaryModel);
				paramsMap.put("detailList", new ArrayList<FileDetailMode>());
				transformer = new XLSTransformer();
				workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"refundbankinnertemplates.xls")), paramsMap);
				filePath = CreatorFileDirUtil.writeWorkBookFileForRefund(workbook, fileInfo);
				return CreatorFileDirUtil.bulidBatchFileInfo(fileSummaryModel, filePath, fileInfo);
			}

			
			FileDetailMode tempInfo = null; // 记录文件内容
			FileSummaryModel masterInfo = null; // 记录文件头信息
			List<Object> detailList = null; // 文件内容集合
			Workbook tempWb = null; // 临时文件
			boolean flag = false;

			int size = innerDetailList.size();
			String bankCode = "".intern(); // 银行
			String batchNum = "".intern(); // 批次

			int tempCount = 0;
			BigDecimal sumAmount = null;

			for (int i = 0; i < size; i++) {
				tempInfo = (FileDetailMode) innerDetailList.get(i);
				
				if (0 == i) {
					bankCode = tempInfo.getRechargeBank();
					batchNum = tempInfo.getBatchNum();
					masterInfo = new FileSummaryModel();
					masterInfo.setBankCode(bankCode);
					masterInfo.setBatchNum(batchNum);
					if(StringUtils.isEmpty(tempInfo.getBankName())){
						masterInfo.setBankName(masterInfo.getBankCode());
					}else{
						masterInfo.setBankName(tempInfo.getBankName());
					}
					sumAmount = new BigDecimal(0);
					detailList = new ArrayList<Object>();
				}

				if (bankCode.equals(tempInfo.getRechargeBank())) {
					sumAmount = sumAmount.add(tempInfo.getApplyAmount());
					tempInfo.setApplyAmount(tempInfo.getApplyAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
					tempInfo.setRechargeAmount(tempInfo.getRechargeAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
					if(null != tempInfo.getApplyTimes()){
						tempInfo.setRefundDate(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",tempInfo.getApplyTimes()));
					}
					tempCount++;
					detailList.add(tempInfo);
				} else {
					masterInfo.setTotalCount(tempCount);
					masterInfo.setTotalAmount(sumAmount.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN));
					paramsMap = new HashMap<String, Object>();
					paramsMap.put("masterInfo", masterInfo);
					paramsMap.put("detailList", detailList);
					transformer = new XLSTransformer();
					// 以第一次生成文件作为最终输出文件，如含有多个银行，则其他银行生成的sheet添加到第一个文件里
					if (!flag) {
						workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"refundbankinnertemplates.xls")), paramsMap);
						workbook.setSheetName(workbook.getActiveSheetIndex(), masterInfo.getBankName());
						flag = true;
					} else {
						tempWb = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"refundbankinnertemplates.xls")), paramsMap);
						
						CreatorFileDirUtil.creatNewExcel(tempWb.getSheetAt(0), workbook, masterInfo.getBankName());
					}

					// 初始化另一页的值
					tempCount = 0;
					bankCode = tempInfo.getRechargeBank();
					batchNum = tempInfo.getBatchNum();
					masterInfo = new FileSummaryModel();
					masterInfo.setBankCode(bankCode);
					masterInfo.setBatchNum(batchNum);
					if(StringUtils.isEmpty(tempInfo.getBankName())){
						masterInfo.setBankName(masterInfo.getBankCode());
					}else{
						masterInfo.setBankName(tempInfo.getBankName());
					}
					sumAmount = new BigDecimal(0);
					sumAmount = sumAmount.add(tempInfo.getApplyAmount());
					detailList = new ArrayList<Object>();
					tempInfo.setApplyAmount(tempInfo.getApplyAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
					tempInfo.setRechargeAmount(tempInfo.getRechargeAmount().divide(new BigDecimal(1000),2,BigDecimal.ROUND_DOWN));
					if(null != tempInfo.getApplyTimes()){
						tempInfo.setRefundDate(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",tempInfo.getApplyTimes()));
					}
					detailList.add(tempInfo);
					
					tempCount++;
				}

				// 如果为最后一条数据，则生成文件
				if (i == (size - 1)) {
					masterInfo.setTotalCount(tempCount);
					masterInfo.setTotalAmount(sumAmount.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN));
					paramsMap = new HashMap<String, Object>();
					paramsMap.put("masterInfo", masterInfo);
					paramsMap.put("detailList", detailList);
					transformer = new XLSTransformer();
					if (!flag) {
						workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"refundbankinnertemplates.xls")), paramsMap);
						workbook.setSheetName(workbook.getActiveSheetIndex(), masterInfo.getBankName());
						flag = true;
					} else {
						tempWb = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"refundbankinnertemplates.xls")), paramsMap);
						CreatorFileDirUtil.creatNewExcel(tempWb.getSheetAt(0), workbook, masterInfo.getBankName());
					}
				}
			}

			filePath = CreatorFileDirUtil.writeWorkBookFileForRefund(workbook, fileInfo);
			masterInfo.setTotalAmount(sumAmount);
		} catch (ParsePropertyException e) {
			logger.error("内部明细文件生成出现异常:" + e.getMessage(), e);
			throw new PossException("内部明细文件生成出现异常", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		} catch (InvalidFormatException e) {
			logger.error("内部明细文件生成出现异常:" + e.getMessage(), e);
			throw new PossException("内部明细文件生成出现异常", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		} catch (IOException e) {
			logger.error("内部明细文件生成出现异常:" + e.getMessage(), e);
			throw new PossException("内部明细文件生成出现异常", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		} catch (Exception e) {
			logger.error("内部明细文件生成出现异常:" + e.getMessage(), e);
			throw new PossException("内部明细文件生成出现异常", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		}
		return CreatorFileDirUtil.bulidBatchFileInfo(fileSummaryModel, filePath, fileInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fundout.bankfile.service.AbstractFileGenerator#calcChannel
	 * (java.lang.String)
	 */
	@Override
	protected List<String> calcChannel(String batchNum) throws PossException {
		List<String> result = new ArrayList<String>();
		try {
			List<String> banks = daoService.findByQuery("bfrefund.fundout-refund-queryBanks", batchNum);
			StringBuffer buffer = new StringBuffer();
			for (String bankCode : banks) {
				buffer.append("'R_" + bankCode + "',");
			}
			String bankCodes = "";
			if (0 != buffer.length()) {
				bankCodes = buffer.deleteCharAt(buffer.length() - 1).toString();
			}
			if(StringUtils.isNotEmpty(bankCodes)){
				result = daoService.findByQuery("bfrefund.queryGeneratorsByCode",bankCodes);
			}
		} catch (Exception e) {
			logger.error("计算出款渠道编码列表错误 [" + batchNum + "]", e);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.fundout.bankfile.service.AbstractFileGenerator#
	 * generateBankSummerFile
	 * (com.pay.fundout.bankfile.spi.generator.BankFileGenerator,
	 * java.util.Map, java.lang.String)
	 */
	@Override
	protected BatchFileInfo generateBankSummerFile(BankFileGenerator generator, Map<String, String> fileInfo, String bankCode) throws PossException {
		// TODO Auto-generated method stub
		return null;
	}

}
