/** @Description 
 * @project 	fo-batchinfo
 * @file 		BaseWithdrawFileGenerator.java 
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
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.fundout.bankfile.common.util.BankFileUtil;
import com.pay.fundout.bankfile.common.util.CreatorFileDirUtil;
import com.pay.fundout.bankfile.generator.BankFileGenerator;
import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.fundout.bankfile.generator.model.FileSummaryModel;
import com.pay.fundout.batchinfo.service.genbatch.AbstractBatchFileGenerator;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.poss.base.amount.NumberToStringForChineseMoney;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.common.properties.MyOSSConfiguration;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * <p>
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-11-9
 * @see
 */

public class FundoutBatchFileGenerator extends AbstractBatchFileGenerator {

	private static final int WITHDRAW_WORKORDER_STATUS_1 = 1; // 已出批次
	private static final String BATCH_FILE_TYPE_11 = "11"; // 内部概要文件类型
	private static final String BATCH_FILE_TYPE_12 = "12"; // 内部详细文件类型
	private static final String BATCH_FILE_TYPE_21 = "21"; // 银行概要文件类型
	private static final String FILE_SUBFIX = ".xls";
	
	
//	private static final String ACCT_TYPE_ENTERPRISE = "1";	//对公
//	private static final String ACCT_TYPE_PERSON = "0";		//对私
//	private static final String ACCT_TYPE_ALL = "ALL";		//全部

	@Override
	protected List<FileSummaryModel> calcMasterInfo(String batchNum) throws PossException {
		try {
			List<FileSummaryModel> result = daoService.findByQuery("bfwithdraw.fundout-withdraw-queryWithdrawMasterinfo", batchNum);

			int totalCount = 0;
			BigDecimal totalAmount = new BigDecimal(0);
			for (FileSummaryModel withdrawMasterInfo : result) {
				withdrawMasterInfo.setBatchNum(batchNum);

				totalCount = totalCount + withdrawMasterInfo.getTotalCount();
				totalAmount = totalAmount.add(withdrawMasterInfo.getTotalAmount());
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

	@Override
	protected Map<String,Object> generateBankDetailFile(BankFileGenerator generator, Map<String, String> fileInfo, String bankCode) throws PossException {
		// 获取银行明细信息
		String batchNum = fileInfo.get("BATCH_NUM");
		List<String> busiTypes = generator.supportBusiTypeCode();
		String acctType = generator.supportTradeType();
		List<FileDetailMode> bankDetailList = bulidBankDetailList(batchNum, bankCode, busiTypes,acctType);
		
		if(null != bankDetailList && !bankDetailList.isEmpty()){
			FileDetailMode temp = null;
			for(Object obj : bankDetailList){
				temp = (FileDetailMode)obj;
				
				try{
					//add by mmzhang 提现 通过BankNumber取开户行名称
					if(null==temp.getBankNumber() || ""==temp.getBankNumber())
					{
						logger.info("提现处理时，开户行号码BankNumber为null!");
					}
					String bankBranch=temp.getBankBranch();
					if(null==bankBranch || ""==bankBranch )
					{
						BankBrancheInfoDto bankInfo=new BankBrancheInfoDto();
						bankInfo.setBankNumber(temp.getBankNumber());
						List<BankBrancheInfoDto> bankBrancheInfList= this.bankBrancheInfoService.findByCondition(bankInfo);
						if(null != bankBrancheInfList && !bankBrancheInfList.isEmpty())
						{
							bankBranch=bankBrancheInfList.get(0).getBankKaihu();
						}
					}
					//add end by mmzhang
					temp.setCityName(this.cityService.findByCityCode(Integer.valueOf(temp.getBankCity())).getCityname());
					temp.setProvinceName(this.provinceService.findById(Integer.valueOf(temp.getBankProvince())).getProvincename());
					temp.setBankName(this.bankService.getBankById(temp.getBankKy()));
					temp.setUnionBankCode(getUnionBankCode(temp));
					temp.setBankBranch(bankBranch);
				}catch(Exception e){
					logger.error("获取城市中文名称出现异常!(getBankCity=" + temp.getBankCity(), e);
					temp.setCityName(StringUtil.null2String(temp.getBankCity())+"");
					temp.setProvinceName(StringUtil.null2String(temp.getBankProvince())+"");
					temp.setBankName(StringUtil.null2String(temp.getBankKy()));
					temp.setUnionBankCode(temp.getBankKy());
				}
			}
		}
		// 生成银行明细文件
		BatchFileInfo batchFileInfo = generator.generateBankDetailFile(bankDetailList, fileInfo);
//		"/opt/upload/fo/withdraw"
		String basePath =fileInfo.get("BATCH_FILE_PATH")+"/withdraw";
		File file=new File(basePath+batchFileInfo.getFilePath());
		if("1".equals(fileInfo.get("STORED_ON_ALIYUN"))){
			putFileOnAliyun(batchFileInfo.getFilePath().replace("\\", "/"),file);
		}
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bankDetailList", bankDetailList);
		resultMap.put("batchFileInfo", batchFileInfo);
		return resultMap;
	}
	public static void putFileOnAliyun(String dbRelativePath,File dest){
		//added by yanshichuan stored file on aliyun
		String ossKey=MyOSSConfiguration.getStrProperties("mpsposs.oss.key");
		String ossRootDir="mpsposs";//MyOSSConfiguration.getStrProperties("mpsposs.oss.rootdir");
		String ossSubDir="withdraw";//MyOSSConfiguration.getStrProperties("mpsposs.oss.withdrawSubdir");
		MyOSS oss = new MyOSS(ossKey);
		JSONObject rawToken;
		try {
			if(StringUtils.isBlank(ossSubDir)){
				ossSubDir="withdraw";
			}
			rawToken = oss.init(ossSubDir);
			OSSClient client = oss.getOSSClient();
			if(dbRelativePath.startsWith("/")){
				dbRelativePath=dbRelativePath.substring(1);
			}
			client.putObject(rawToken.getString("bucket"), ossRootDir+"/"+ossSubDir+"/"+dbRelativePath, dest);
		} catch (UnsupportedOperationException e) {
//			logger.error("put file on aliyun oss error:"+e.getMessage());
		} catch (MyOSSException e) {
//			logger.error("put file on aliyun oss error:"+e.getMessage());
		} catch (IOException e) {
//			logger.error("put file on aliyun oss error:"+e.getMessage());
		}
		if(dest.exists()){
			dest.delete();
		}
	}
	private List<FileDetailMode> bulidBankDetailList(String batchNum, String bankCode, List<String> busiTypes,String acctType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("BANK_KY", bankCode);
		params.put("BATCH_NUM", batchNum);
		params.put("ACCTTYPE",acctType);

		StringBuffer busis = new StringBuffer();
		for (String string : busiTypes) {
			busis.append("'").append(string).append("',");
		}

		String busiType = "";
		if (0 != busis.length()) {
			busiType = busis.deleteCharAt(busis.length() - 1).toString();
		}
		params.put("busiTypes", busiType);
		String sql = "bfwithdraw.fundout-withdraw-queryWithdrawDetailinfo";
		if("10015001".equals(bankCode) //广发
				||"10010001".equals(bankCode)){//邮储
			sql = sql + "-"+ "10015001";
		}
		return daoService.findByQuery(sql, params);
	}

	@Override
	protected List<FileDetailMode> bulidFileDetailList(String batchNum) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("BATCH_NUM", batchNum);
		return daoService.findByQuery("bfwithdraw.fundout-withdraw-queryWithdrawDetailinfo", params);
	}

	private FileSummaryModel bulidBankSummary(String batchNum, String bankCode, List<String> busiTypes,String acctType) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("BANK_KY", bankCode);
		params.put("BATCH_NUM", batchNum);
		params.put("ACCTTYPE",acctType);

		StringBuffer busis = new StringBuffer();
		for (String string : busiTypes) {
			busis.append("'").append(string).append("',");
		}

		String busiType = "";
		if (0 != busis.length()) {
			busiType = busis.deleteCharAt(busis.length() - 1).toString();
		}
		params.put("busiTypes", busiType);
		String sql = "bfwithdraw.fundout-withdraw-queryWithdrawSummaryinfo";
		if("10015001".equals(bankCode) //广发
				||"10010001".equals(bankCode)){//邮储
			sql = sql + "-"+ "10015001";
		}
		return (FileSummaryModel) daoService.findObjectByCriteria(sql, params);
	}

	@Override
	protected BatchFileInfo generateBankSummerFile(BankFileGenerator generator, Map<String, String> fileInfo, String bankCode) throws PossException {
		String batchNum = fileInfo.get("BATCH_NUM");
		FileSummaryModel fileSummaryModel = bulidBankSummary(batchNum, bankCode, generator.supportBusiTypeCode(),generator.supportTradeType());
		if(fileSummaryModel != null && fileSummaryModel.getTotalCount() == 0) return null; 
		fileSummaryModel.setBatchNum(batchNum);
		fileSummaryModel.setBankCode(bankCode);
		// 获取银行名称
		try{
			fileSummaryModel.setBankName(bankService.getBankById(bankCode.substring(0, 8)));
		}catch(Exception e){
			logger.error("获取银行名称出现异常!(bankCode=" + bankCode + ")" + e.getMessage(),e);
			fileSummaryModel.setBankName(bankCode);
		}
		Workbook workbook = null;
		try {
			fileInfo.put("filetype", BATCH_FILE_TYPE_21);
			// 转换金额
			if (null != fileSummaryModel && null != fileSummaryModel.getTotalAmount()) {
				fileSummaryModel.setShowTotalAmount(fileSummaryModel.getTotalAmount().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP));
			}
			
			XLSTransformer transformer = new XLSTransformer();
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("dto", fileSummaryModel);
			beans.put("newDate", DateUtil.formatDateTime("yyyy-MM-dd", new Date()));
			beans.put("chinessAmount", NumberToStringForChineseMoney.getChineseMoneyStringForBigDecimal(fileSummaryModel.getShowTotalAmount()));
			workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"bankSummerTemplate.xls")), beans);
		} catch (Exception e) {
			logger.error("银行概要文件生成出现异常:" + e.getMessage(), e);
			throw new PossException("银行概要文件生成出现异常", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		}
		
		fileInfo.put("BANK_CODE", bankCode);
		String filePath = CreatorFileDirUtil.writeWorkBookFile(workbook, fileInfo,FILE_SUBFIX);

		return CreatorFileDirUtil.bulidBatchFileInfo(fileSummaryModel, filePath, fileInfo);
	}

	@Override
	protected BatchFileInfo generateInnerDetailFile(FileSummaryModel fileSummaryModel, List<FileDetailMode> innerDetailList,
			Map<String, String> fileInfo) throws PossException {
		fileInfo.put("filetype", BATCH_FILE_TYPE_12);

		XLSTransformer transformer = null;
		Workbook workbook = null; // 最终输出文件
		String filePath = "".intern(); // 文件路径

		try {
			// 如果参数为空则直接返回
			if (null == innerDetailList || innerDetailList.isEmpty() || null == fileInfo) {
				Map<String, Object> paramsMap = new HashMap<String, Object>();
				paramsMap.put("masterInfo", fileSummaryModel);
				paramsMap.put("detailList", new ArrayList<FileDetailMode>());
				transformer = new XLSTransformer();
				workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"bankinnertemplates.xls")), paramsMap);
				fileInfo.put("BANK_CODE",fileSummaryModel.getBankCode());
				filePath = CreatorFileDirUtil.writeWorkBookFile(workbook, fileInfo,FILE_SUBFIX);
				return CreatorFileDirUtil.bulidBatchFileInfo(fileSummaryModel, filePath, fileInfo);
			}

			FileDetailMode tempInfo = null; // 记录文件内容
			FileSummaryModel summaryModel = null; // 记录文件头信息
			Map<String, Object> paramsMap = null; // 模板文件参数集合
			List<Object> detailList = null; // 文件内容集合

			Workbook tempWb = null; // 临时文件
			boolean flag = false;

			int size = innerDetailList.size();
			String bankCode = "".intern(); // 银行
			String batchNum = "".intern(); // 批次
			String bankName = "".intern(); // 银行名称

			int tempCount = 0;
			BigDecimal sumAmount = new BigDecimal(0);
			Date  createTime=null; 
			for (int i = 0; i < size; i++) {
				tempInfo = (FileDetailMode) innerDetailList.get(i);
				if(tempInfo.getAcctType()!=null){
					Long acctType = tempInfo.getAcctType();
					String currencyCode = AcctTypeEnum.getAcctCurrencyByCode(acctType.intValue());
					tempInfo.setCurrencyCode(currencyCode);
				}
				createTime=tempInfo.getCreateTime();
				// 转换省份和城市
				if (null != tempInfo) {
					try{
						tempInfo.setProvinceName(this.provinceService.findById(Integer.valueOf(tempInfo.getBankProvince())).getProvincename());
					}catch(Exception e){
						logger.error("获取省份中文名称出现异常!(bankProvince=" + tempInfo.getBankProvince(), e);
						tempInfo.setProvinceName(StringUtil.null2String(tempInfo.getBankProvince()));
					}
					
					try{
						tempInfo.setCityName(this.cityService.findByCityCode(Integer.valueOf(tempInfo.getBankCity())).getCityname());
					}catch(Exception e){
						logger.error("获取城市中文名称出现异常!(bankProvince=" + tempInfo.getBankProvince(), e);
						tempInfo.setCityName(StringUtil.null2String(tempInfo.getBankCity())+"");
					}

					if (null != tempInfo.getAmount()) {
						tempInfo.setShowAmount(tempInfo.getAmount().divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN).toString());
					}
					
					try{
						tempInfo.setBankName(this.bankService.getBankById(tempInfo.getBankKy()));
					}catch(Exception e){
						logger.error("获取银行名称出现异常(bankCode=" + tempInfo.getBankKy() + "):" + e.getMessage(), e);
						tempInfo.setBankName(StringUtil.null2String(tempInfo.getBankKy()));
					}
				} else {
					continue;
				}

				if (0 == i) {
					bankCode = tempInfo.getWithdrawBankCode();
					try {
						if (!StringUtil.isEmpty(bankCode)) {
							bankName = bankService.getBankById(bankCode);
						} else {
							bankName = "" + i;
						}
					} catch (Exception e) {
						logger.error("获取银行名称出现异常(bankCode=" + bankCode + "):" + e.getMessage(), e);
						bankName = bankCode;
					}
					batchNum = tempInfo.getBatchNum();
					summaryModel = new FileSummaryModel();
					summaryModel.setBankCode(bankCode);
					if (StringUtil.isEmpty(bankName)) {
						summaryModel.setBankName(bankCode);
					} else {
						summaryModel.setBankName(bankName);
					}
					summaryModel.setBatchNum(batchNum);
					sumAmount = new BigDecimal(0);
					detailList = new LinkedList<Object>();
				}

				if (bankCode.equals(tempInfo.getWithdrawBankCode())) {
					sumAmount = sumAmount.add(tempInfo.getAmount());					
					tempCount++;
					detailList.add(tempInfo);
				} else {
					summaryModel.setTotalCount(tempCount);
					summaryModel.setTotalAmount(sumAmount.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN));
					paramsMap = new HashMap<String, Object>();
					paramsMap.put("masterInfo", summaryModel);
					paramsMap.put("createTime", createTime);
					paramsMap.put("batchNum", batchNum);
					//Arrays.sort(detailList.toArray());
					paramsMap.put("detailList", detailList);
					transformer = new XLSTransformer();
					// 以第一次生成文件作为最终输出文件，如含有多个银行，则其他银行生成的sheet添加到第一个文件里
					if (!flag) {
						workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"bankinnertemplates.xls")), paramsMap);

						if (StringUtil.isEmpty(bankName)) {
							workbook.setSheetName(workbook.getActiveSheetIndex(), bankCode);
						} else {
							workbook.setSheetName(workbook.getActiveSheetIndex(), bankName);
						}

						flag = true;
					} else {
						tempWb = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"bankinnertemplates.xls")), paramsMap);
						if (StringUtil.isEmpty(bankName)) {
							CreatorFileDirUtil.creatNewExcel(tempWb.getSheetAt(0), workbook, bankCode);
						}else{
							CreatorFileDirUtil.creatNewExcel(tempWb.getSheetAt(0), workbook, bankName);
						}
					}

					// 初始化另一页的值
					tempCount = 0;
					bankCode = tempInfo.getWithdrawBankCode();
					try {
						if (!StringUtil.isEmpty(bankCode)) {
							bankName = bankService.getBankById(bankCode);
						} else {
							bankName = "" + i;
						}
					} catch (Exception e) {
						logger.error("获取银行名称出现异常(bankCode=" + bankCode + "):" + e.getMessage(), e);
						bankName = bankCode;
					}
					batchNum = tempInfo.getBatchNum();
					summaryModel = new FileSummaryModel();
					summaryModel.setBankCode(bankCode);
					if (StringUtil.isEmpty(bankName)) {
						summaryModel.setBankName(bankCode);
					} else {
						summaryModel.setBankName(bankName);
					}
					summaryModel.setBatchNum(batchNum);
					sumAmount = tempInfo.getAmount();
					detailList = new LinkedList<Object>();
					detailList.add(tempInfo);
					tempCount++;
				}

				// 如果为最后一条数据，则生成文件
				if (i == (size - 1)) {
					summaryModel.setTotalCount(tempCount);
					summaryModel.setTotalAmount(sumAmount.divide(new BigDecimal(1000), 2, BigDecimal.ROUND_DOWN));
					paramsMap = new HashMap<String, Object>();
					//Arrays.sort(detailList.toArray());
					paramsMap.put("masterInfo", summaryModel);
					paramsMap.put("detailList", detailList);
					SimpleDateFormat sd=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					String format = sd.format(createTime);
					paramsMap.put("createTime", format);
					transformer = new XLSTransformer();
					if (!flag) {
						logger.info("------生成文件参数:" + paramsMap.toString());
						workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"bankinnertemplates.xls")), paramsMap);
						if (StringUtil.isEmpty(bankName)) {
							workbook.setSheetName(workbook.getActiveSheetIndex(), bankCode);// 为当前选择页设置名称
						} else {
							workbook.setSheetName(workbook.getActiveSheetIndex(), bankName);// 为当前选择页设置名称
						}
						flag = true;
					} else {
						tempWb = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"bankinnertemplates.xls")), paramsMap);
						if (StringUtil.isEmpty(bankName)) {
							CreatorFileDirUtil.creatNewExcel(tempWb.getSheetAt(0), workbook, bankCode);
						}else{
							CreatorFileDirUtil.creatNewExcel(tempWb.getSheetAt(0), workbook, bankName);
						}
					}
				}
			}

			filePath = CreatorFileDirUtil.writeWorkBookFile(workbook, fileInfo,FILE_SUBFIX);
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

	@Override
	protected BatchFileInfo generateInnerSummerFile(FileSummaryModel fileSummaryModel, Map<String, String> fileInfo) throws PossException {
		if (null == fileSummaryModel || null == fileInfo) {
			return null;
		}
		String batchNum = fileInfo.get("BATCH_NUM");
		List<FileSummaryModel> result = daoService.findByQuery("bfwithdraw.fundout-withdraw-queryWithdrawMasterinfoList", batchNum);
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumFractionDigits(2);
		
		for (FileSummaryModel temp : result) {
			if(temp.getAcctType()!=null){
				String currencyCode = AcctTypeEnum.getAcctCurrencyByCode(temp.getAcctType().intValue());	
				temp.setCurrencyCode(currencyCode);
			}
			//result.add(temp);
		}

		fileInfo.put("filetype", BATCH_FILE_TYPE_11);
		
		long sumAmount = 0l;
		int totalCount = 0;
		for (FileSummaryModel tempInfo : result) {
				totalCount += tempInfo.getTotalCount();
				tempInfo.setBatchNum(batchNum);
			
			// 转换金额
			if ( null != tempInfo.getTotalAmount()) {
				sumAmount =+ tempInfo.getTotalAmount().longValue();
				tempInfo.setShowTotalAmount(BankFileUtil.divideNum(tempInfo.getTotalAmount(),new BigDecimal(1000)));
			}
			// 获取银行名称
			if (null != tempInfo.getBankCode()) {
				try{
					tempInfo.setBankName(bankService.getBankById(tempInfo.getBankCode()));
				}catch(Exception e){
					logger.error("获取银行中文名称时出现异常!(bankCode=" + tempInfo.getBankCode() + ")" + e.getMessage(),e);
					tempInfo.setBankName(StringUtil.null2String(tempInfo.getBankCode()));
				}
			}
			String showTotalAmount = nf.format(tempInfo.getShowTotalAmount());
			tempInfo.setShowAmount(showTotalAmount);
		}
		
		BigDecimal amount = BankFileUtil.divideNum(new BigDecimal(sumAmount),new BigDecimal(1000));
		
		XLSTransformer transformer = new XLSTransformer();
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("infoList", result);
		beans.put("count",Integer.valueOf(totalCount));
		beans.put("sumAmount",amount);
		beans.put("newDate", DateUtil.formatDateTime("yyyy-MM-dd", new Date()));
		beans.put("chinessAmount", NumberToStringForChineseMoney.getChineseMoneyStringForBigDecimal(amount));
	//	beans.put("chinessAmount", NumberToStringForChineseMoney.getChineseMoneyStringForBigDecimal(amount));
		
		
		
		Workbook workbook = null;
		try {
			workbook = transformer.transformXLS(new FileInputStream(new File(CommonConfiguration.getStrProperties("genfile.template.path")+File.separator+"bankInnerSummerTemplate.xls")), beans);
		} catch (Exception e) {
			logger.error("内部概要文件生成出现异常:" + e.getMessage(), e);
			throw new PossException("内部概要文件生成出现异常", ExceptionCodeEnum.UN_KNOWN_EXCEPTION, e);
		}
		fileInfo.put("BANK_CODE","INNER");
		String filePath = CreatorFileDirUtil.writeWorkBookFile(workbook, fileInfo,FILE_SUBFIX);

		return CreatorFileDirUtil.bulidBatchFileInfo(fileSummaryModel, filePath, fileInfo);
	}

	@Override
	protected void updateWorkorder(String batchNum) throws PossException {
		if (StringUtil.isEmpty(batchNum)) {
			return;
		}
		FileDetailMode params = new FileDetailMode();
		params.setBatchNum(batchNum);
		params.setStatus(WITHDRAW_WORKORDER_STATUS_1);
		daoService.update("bfwithdraw.updateWithdrawWorkOrderStatus", params);
	}

	@Override
	protected List<String> calcChannel(String batchNum) throws PossException {
		return daoService.findByQuery("bfwithdraw.fundout-withdraw-queryChannels", batchNum);
	}

}
