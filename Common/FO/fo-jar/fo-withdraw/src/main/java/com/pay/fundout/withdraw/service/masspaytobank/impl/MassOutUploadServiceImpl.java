/**
 *  File: MassOutUploadServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.common.NotMatchTemplateException;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportDetailDTO;
import com.pay.fundout.withdraw.service.masspaytobank.MassOutUploadService;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytoBankService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.poss.service.inf.input.ProvinceCityFacadeService;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.util.StringUtil;


public class MassOutUploadServiceImpl implements MassOutUploadService {
	
	/**
     * 银行卡0-9 
     */
	private final static String BANKACCT_PATTERN  ="^[0-9]+$";
	
	private final static String AMOUNT_PATTERN = "^(0(\\.\\d{0,2})?|([1-9]+[0]*)+(\\.\\d{0,2})?)$";

	private BankInfoFacadeService bankInfoFacadeService;
	
	private ProvinceCityFacadeService provinceCityFacadeService;
	
	private MassPaytoBankService massPaytoBankService;
	
	private Pay2BankValidateService pay2BankValidateService;
	
	private static final  String TEMPLATEID = "MP2BANKT0001";//批付到银行基础模板
	private static final  String[] headRow = new String[]{"开户银行所属省份","开户银行所属城市","银行名称","开户行名称","收款方账户类型","收款方姓名","收款方银行账号","金额","备注","商家订单号"};

	
	public void setPay2BankValidateService(
			Pay2BankValidateService pay2BankValidateService) {
		this.pay2BankValidateService = pay2BankValidateService;
	}

	public void setProvinceCityFacadeService(
			ProvinceCityFacadeService provinceCityFacadeService) {
		this.provinceCityFacadeService = provinceCityFacadeService;
	}

	public void setBankInfoFacadeService(BankInfoFacadeService bankInfoFacadeService) {
		this.bankInfoFacadeService = bankInfoFacadeService;
	}
	
	public void setMassPaytoBankService(MassPaytoBankService massPaytoBankService) {
		this.massPaytoBankService = massPaytoBankService;
	}
	
	private Map<String, Integer> provinceCode ;
	 
	private Map<String, Integer> cityCode;
	 
	private Map<String,String> bankCode;
	
	 public Map<String, Integer> getProvinceCode() {
			return provinceCode;
	}

	public void setProvinceCode(List<ProvinceDTO> provinecList) {
		Map<String, Integer> code = new HashMap<String, Integer>();
		if(provinecList == null || provinecList.size()==0){
			return;
		}
		for(ProvinceDTO pro: provinecList){
			code.put(pro.getProvincename(), pro.getProvincecode());
		}
		this.provinceCode = code;
	}

	public Map<String, Integer> getCityCode() {
		return cityCode;
	}

	public void setCityCode(Map<String, Integer> cityCode) {
		this.cityCode = cityCode;
	}

	public Map<String, String> getBankCode() {
		return bankCode;
	}

	/**
	 * 通过银行名称初始化银行	 
	 * @param list
	 */
	public void setBankCode(List<Bank> list) {
		Map<String, String> code = new HashMap<String, String>();
		if(list == null || list.size()==0){
			return;
		}
		for(Bank bank: list){
			code.put(bank.getBankName(), bank.getBankId()+"");
		}
		this.bankCode = code;
	}
	

	/**
	 * 批量直付上传.
	 * @company sys.com.
	 * @author sean_yi
	 * @version 1.0
	 * @throws NotMatchTemplateException 
	 */
	@Override
	public MassPaytobankImportBaseDTO buildUploadOrders(byte[] file,int maxSize) throws IOException, NotMatchTemplateException {
		MassPaytobankImportBaseDTO mass = new MassPaytobankImportBaseDTO();
		List<MassPaytobankImportDetailDTO> resultList = new ArrayList<MassPaytobankImportDetailDTO>();
		HSSFWorkbook book = new HSSFWorkbook(new ByteArrayInputStream(file));
		HSSFSheet sheet = book.getSheetAt(0);
		if(sheet == null){
			return mass;
		}
		mass.setBusinessNo(this.getBatchId(sheet));//设置业务编号
		//sheet.getLastRowNum() 取索引值
		int totalRows = sheet.getLastRowNum()+1;
		if(totalRows>maxSize+2){
			mass.setOutMaxSize(true);
			return mass;
		}
		//TODO 与银行文件生成模板整合，支持模板多样化
		MassPaytobankImportDetailDTO detail = null; 
		for(int i=0 ;i< totalRows;i++){
			HSSFRow  row  =  sheet.getRow(i);
			// 校验模板
			if(i==0){
				String tmpId =row.getCell(0).getStringCellValue();
				if(!TEMPLATEID.equalsIgnoreCase(tmpId)){
					throw new NotMatchTemplateException("上传模板不正确");
				}
				continue;
				
			}
			if(i==1){
				validateHeadRow(row);
				continue;
			}
			
			detail = this.getMassPaytobankDetail(row);
			if(this.isMassPaytobankDetail(detail)){
				resultList.add(detail);
			}
		 }
		mass.setMassPaytoBankImportDetails(resultList);
		return mass;
	}
	
	private void  validateHeadRow(HSSFRow row) throws NotMatchTemplateException{
		Iterator<HSSFCell> cells = row.iterator();
		while (cells.hasNext()) {
			HSSFCell cell =  cells.next();
			String content = this.passCellToString(cell);	
			validateColumn(content,cell.getColumnIndex());
		}
	}
	
	private void validateColumn(String src,int index) throws NotMatchTemplateException{
		if(index>headRow.length||index<0){
			throw new NotMatchTemplateException("上传模板不正确");
		}
		if(!src.contains(headRow[index])){
			throw new NotMatchTemplateException("上传模板不正确");
		}
	}
	
	/**
	 * 得到批次号
	 * 
	 * @return
	 * @throws Exception
	 */
	private String getBatchId(HSSFSheet sheet) {
		String batchId = "";
		HSSFRow row = null;
		try {
			row = sheet.getRow(0);
			if (row != null) {
				batchId = this.passCellToString(row.getCell(2));
			}
		} catch (Exception e) {
			LogUtil.info(MassOutUploadServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "", "获取batchId异常");
		}
		return batchId;
	}
	
	/**
	 * 将excel行转化为对象
	 * @param row
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private MassPaytobankImportDetailDTO getMassPaytobankDetail(HSSFRow  row){
		MassPaytobankImportDetailDTO detail = new MassPaytobankImportDetailDTO();
		Iterator<HSSFCell> cells = row.iterator();
		while (cells.hasNext()) {
			HSSFCell cell =  cells.next();
			String content = this.passCellToString(cell);	
			if(cell.getColumnIndex() == 0){
				content = this.validateLength(content, "开户银行所属省份", 50, detail);
				detail.setProvinceName(content);
			}else if (cell.getColumnIndex() == 1) {
				content = this.validateLength(content, "开户银行所属城市", 50, detail);
				detail.setCityName(content);
			}else if(cell.getColumnIndex() == 2){
				content = this.validateLength(content, "银行名称", 50, detail);
				detail.setBankName(content);
			}else if(cell.getColumnIndex() == 3){
				content = this.validateLength(content, "开户行名称", 100, detail);
				detail.setOpeningBankName(content);
			}else if(cell.getColumnIndex() == 4){
				content = this.validateLength(content, "收款方账户类型", 1, detail);
				detail.setTradeType(content);
			}else if(cell.getColumnIndex() == 5){
				content = this.validateLength(content, "收款方姓名", 50, detail);
				detail.setPayeeName(content);
			}else if(cell.getColumnIndex() == 6){
				content = this.validateLength(content, "收款方银行账号", 32, detail);
				detail.setPayeeBankAcct(content);
			}else if(cell.getColumnIndex() == 7){
				detail.setUploadAmount(content);
			}else if(cell.getColumnIndex() == 8){
				content = this.validateLength(content, "备注", 150, detail);
				detail.setRemark(content);
			}else if(cell.getColumnIndex() == 9){
				content = this.validateLength(content, "商家订单号", 50, detail);
				detail.setBusinessOrderId(content);
			}
		}
		return detail;
	}
	
	private String validateLength(String content,String title, int length,MassPaytobankImportDetailDTO detail){
		int temp = 0;
		try {
			temp = content.getBytes("utf8").length;
		} catch (Exception e) {
			length = 0;
			LogUtil.info(MassOutUploadServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
		}
		String recontent =content;
		if(length < temp){
			try {
				byte[] tempByte = content.getBytes("utf8");
				recontent = new String(Arrays.copyOfRange(tempByte, 0, length-3))+"...";
			} catch (UnsupportedEncodingException e) {
				LogUtil.info(MassOutUploadServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
			}
			detail.setErrorMessage(title + "不能大于"+ length + "字符");
		}
		return recontent;
	}
	
	/**
	 * 将单元格内容转换为String 
	 * 转换异常返回空
	 * @param cell
	 * @return
	 */
	private String passCellToString(HSSFCell cell){
		String content ="";
		try {
			switch (cell.getCellType()) {//获得单元格数据类型
			case HSSFCell.CELL_TYPE_NUMERIC: 
				content = String.valueOf(cell.getNumericCellValue());
				break;
			case HSSFCell.CELL_TYPE_STRING:
				content = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BLANK:
				content = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN:
				content = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				content = cell.getStringCellValue();
			}
		}catch (Exception e) {
			LogUtil.info(MassOutUploadServiceImpl.class, "批量付款到银行", OPSTATUS.EXCEPTION, "", "读取excl格式错误");
		}
		return content.trim();
	}
	
	
	/**
	 * 判断上传的行是否有效
	 * @param detail
	 * @return
	 */
	private boolean isMassPaytobankDetail(MassPaytobankImportDetailDTO detail){
		boolean flage = false;
		if(!StringUtil.isEmpty(detail.getProvinceName()) || !StringUtil.isEmpty(detail.getCityName()) 
			|| !StringUtil.isEmpty(detail.getBankName()) || !StringUtil.isEmpty(detail.getOpeningBankName())
			|| !StringUtil.isEmpty(detail.getPayeeName()) || !StringUtil.isEmpty(detail.getPayeeBankAcct()) 
			|| !StringUtil.isEmpty(detail.getTradeType())|| !StringUtil.isEmpty(detail.getUploadAmount())
			|| !StringUtil.isEmpty(detail.getBusinessOrderId())|| !StringUtil.isEmpty(detail.getRemark())){
			return true;
		}
		return flage;
	}

	/**
	 * 验证批量上传信息
	 */
	@Override
	public MassPaytobankImportBaseDTO validateMassOutDetails(MassPaytobankImportBaseDTO mass) {
		List<MassPaytobankImportDetailDTO> details = mass.getMassPaytoBankImportDetails();
		List<MassPaytobankImportDetailDTO> errorDetails = new ArrayList<MassPaytobankImportDetailDTO>();
		if(details == null || details.size() < 1){
			return null;
		}
		int totalNum = 0;
		int validNum = 0;
		long totalAmount = 0;
		long validAmount = 0;
		long totalFee =0;
		//TODO 定义单独的验证工具类，启动时初始化相关信息
		
		//加载银行
		List<Bank> banks = bankInfoFacadeService.getWithdrawBanks();
		this.setBankCode(banks);
		//加载所有省份
		List<ProvinceDTO> proList = provinceCityFacadeService.getAllProvince();
		this.setProvinceCode(proList);
		//加载城市
		Map<String, Integer> cityMap = new HashMap<String, Integer>();
		for(ProvinceDTO pro : proList){
			List<CityDTO> cityList = provinceCityFacadeService.getProvincesCity(pro.getProvincecode());
			cityMap = proCityMap(pro.getProvincecode(), cityMap, cityList);
		}
		this.setCityCode(cityMap);
		//循环验证单笔上传信息
		RCLimitResultDTO rule = massPaytoBankService.getRCLimitAmount(mass.getPayerMomberCode());
		if(rule == null){
			return null;
		}
		for(MassPaytobankImportDetailDTO detail : details){
			//TODO 采用验证框架来验证基本信息
			this.validateSingleOrder(detail,rule);
			
			if(detail.getValidateStatus().intValue() == 1){ //统计有效记录和有效金额
				long fee = this.getFee(mass, detail);
				detail.setFee(fee);
				validNum = validNum + 1;
				validAmount = validAmount + detail.getAmount().longValue();
				totalFee = totalFee + Math.abs(fee);
			}else{
				errorDetails.add(detail);
			}
			//统计总记录数 和金额
			totalNum = totalNum + 1;
			totalAmount = totalAmount + detail.getAmount().longValue();
		}
		mass.setTotalFee(totalFee);
		mass.setTotalNum(totalNum);
		mass.setTotalAmount(totalAmount);
		mass.setValidNum(validNum);
		mass.setValidAmount(validAmount);
		if(errorDetails.size()>0){
			mass.setMassPaytobankErrors(errorDetails);
		}
		return mass;
	}
	
	/**
	 * 获取费用
	 * @param mass
	 * @param detail
	 * @return
	 */
	private long getFee(MassPaytobankImportBaseDTO mass,MassPaytobankImportDetailDTO detail){
		return massPaytoBankService.caculateFee(detail.getAmount(),mass.getIsPayerPayFee(),String.valueOf(mass.getPayerMomberCode()));
	}
	
	/**
	 * 验证单笔信息是否合法
	 * @param detail
	 */
	private void validateSingleOrder(MassPaytobankImportDetailDTO detail, RCLimitResultDTO rule) {
		//设置默认属性
		detail.setValidateStatus(0);
		try {
			Long amount = (long)(Double.valueOf(detail.getUploadAmount()) * 1000);
			detail.setAmount(amount);
			if (!checkAmount(detail.getUploadAmount())) {
				detail.setAmount(0l);
				detail.setErrorMessage("金额格式不正确");
				return;
			}else if(amount.longValue()<=0L){
				detail.setErrorMessage("金额不正确");
				return;
			}
			
		} catch (Exception e) {
			detail.setErrorMessage("金额格式不正确");
			detail.setAmount(0l);
			return;
		}
		//如果错误信不为空则直接不验证
		if( !StringUtil.isEmpty(detail.getErrorMessage())){
			return;
		}
		// 增加判断金额必须大于0才能支付
		if(rule.getSingleLimit() < detail.getAmount().longValue()){ 
			detail.setErrorMessage("单笔最多付款"+numberFormat(toBigDecimalAmount(rule.getSingleLimit()))+"元");
			return;
		}
		
		Integer proCode = null;
		if (!StringUtil.isEmpty(detail.getProvinceName())){// 省份	
			proCode = provinceCode.get(detail.getProvinceName());
			if (null == proCode) {
				detail.setErrorMessage("开户银行所属省份信息不正确");
				return;
			} else {
				detail.setProvinceCode(proCode);
			}
		}else{
			detail.setErrorMessage("开户银行所属省份信息必须填写");
			return;
		}
		
		Integer city = 1;
		if (!StringUtil.isEmpty(detail.getCityName())) {// 城市
			city = cityCode.get(detail.getProvinceCode()+detail.getCityName());
			if (null == city) {
				detail.setErrorMessage("开户银行所属城市信息不正确");
				return;
			}else{
				detail.setCityCode(city);
			}
		}else{
			detail.setErrorMessage("开户银行所属城市信息必须填写");
			return;
		}
		
		if (StringUtil.isEmpty(detail.getBankName()) || detail.getBankName().length() > 30) {
			detail.setErrorMessage("银行名称不正确");
			return ;
		}else{
			Integer bankNameId = 1;
			String bankNameCode = bankCode.get(detail.getBankName());
			try {
				bankNameId = Integer.valueOf(bankNameCode);
			} catch (Exception e) {
				bankNameId = null;
			}
			if(null != bankNameId){
				detail.setBankCode(String.valueOf(bankNameId));
				if(StringUtil.isNull(getOutBankCode(String.valueOf(bankNameId)))){
					detail.setErrorMessage("暂不支持付款到"+detail.getBankName());
					return;
				}
			}else{
				detail.setErrorMessage("银行名称不正确");
				return;
			}
		}
		
		if (StringUtil.isEmpty(detail.getOpeningBankName()) || detail.getOpeningBankName().length() > 30 ) {
			detail.setErrorMessage("开户行名称不正确");
			return;
		}
		
		if (StringUtil.isEmpty(detail.getTradeType()) || detail.getTradeType().length() > 1 ) {
			detail.setErrorMessage("收款方账户类型不正确");
			return;
		}
		
		//B 企业；C 个人
		if(!"C".equalsIgnoreCase(detail.getTradeType())&&!"B".equalsIgnoreCase(detail.getTradeType())){
			detail.setErrorMessage("收款方账户类型不正确");
			return;
		}
		
		if (StringUtil.isEmpty(detail.getPayeeName()) ||  detail.getPayeeName().length() > 90) {
			detail.setErrorMessage("收款方姓名不正确");
			return;
		}
//		else{
//			try {
//				length = detail.getPayeeName().getBytes("utf8").length;
////				if( length < 15){
////					detail.setErrorMessage("收款方姓名必须是企业名称");
////					return;
////				}
//			} catch (UnsupportedEncodingException e) {
//				detail.setErrorMessage("收款方姓名编码异常");
//				LogUtil.info(MassOutUploadServiceImpl.class, "批量付款到银行收款方姓名", OPSTATUS.EXCEPTION, "", "");
//				return;
//			}
//		}
		
		if (StringUtil.isEmpty(detail.getPayeeBankAcct()) || (detail.getPayeeBankAcct().length() < 8 )|| detail.getPayeeBankAcct().length() > 32|| !checkBankAcctId(detail.getPayeeBankAcct())) {
			detail.setErrorMessage("银行账号不正确");
			return;
		}
		
		if("C".equalsIgnoreCase(detail.getTradeType())){
			if(!StringUtil.isNull(pay2BankValidateService.validateCardBin(detail.getPayeeBankAcct(), detail.getBankCode()))){
				detail.setErrorMessage("银行账号不正确");
				return;
			}
		}
		// 备注
		if (!StringUtil.isEmpty(detail.getRemark()) && detail.getRemark().length() > 50) {
			detail.setErrorMessage("备注长度超出限制");
			return;
		}
		// 商家订单号
		if (!StringUtil.isEmpty(detail.getRemark()) && detail.getBusinessOrderId().length() > 30) {
			detail.setErrorMessage("商家订单号长度超出限制");
			return;
		}
		detail.setValidateStatus(1);
	}

	private Map<String, Integer> proCityMap(Integer proCode,Map<String, Integer> map,List<CityDTO> cityList){
		if(cityList ==null || cityList.size() ==0){
			return map;
		}
		for(CityDTO city : cityList){
			map.put(proCode+city.getCityname(), city.getCitycode());
		}
		return map;
	}
	

	/**
	 * 判断银行卡是否正常，全部为数字的为合法卡号.
	 * 
	 * @param bankAcctId
	 * @return
	 */
	private boolean checkBankAcctId(String bankAcctId) {
		boolean ret = false;
		try {
			Pattern p = Pattern.compile(BANKACCT_PATTERN);
			Matcher m = p.matcher(bankAcctId);
			ret = m.matches();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	
	/**
	 * 验证金额是否合法
	 * @param amountStr
	 * @return
	 */
	private boolean checkAmount(String amountStr){
		boolean ret = false;
		try {
			Pattern p = Pattern.compile(AMOUNT_PATTERN);
			Matcher m = p.matcher(amountStr);
			ret = m.matches();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}
	private ConfigBankService configBankService;
	private String getOutBankCode(String bankCode){
		
		Map<String,Object> map = new HashMap<String,Object>();
		//目的银行编号
		map.put("targetBankId", bankCode);
		//出款方式
		map.put("foMode", "1");
		//出款业务
		map.put("fobusiness", WithdrawOrderBusiType.MASSPAY2BANK.getCode());//3 付款到银行
		
		return StringUtil.null2String(configBankService.queryFundOutBank2Withdraw(map));
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}
	
	/**
	 * 金额转BigDecimal
	 * @param amount
	 * @return
	 */
	protected BigDecimal toBigDecimalAmount(Long amount){
		Long tmpAmount = amount;
		if (tmpAmount == null) {
			tmpAmount = 0L;
		}
		return new BigDecimal(tmpAmount).divide(new BigDecimal(1000),2, BigDecimal.ROUND_HALF_UP);

	}
	
	private static String numberFormat(BigDecimal num){
		 NumberFormat formatter   =   new   DecimalFormat( "#,###,##0.00"); 
		 if(num==null){
			 return "NULL";
		 }
		 return formatter.format(num.doubleValue());
	}
}
