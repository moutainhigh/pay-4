/**
 * 
 */
package com.pay.fo.order.service.batchpay2acct;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fundout.util.AmountUtils;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class ParseBatchPay2AcctTemplateUtils {
	
	public static final String TEMPLATEID="MP2ACCTT001";
	public static final String[] HEADROW = new String[]{"商户流水号","收款方姓名","收款方帐户","付款金额","备注"};

	/**
	 * 获取业务批次号
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getBusinessBatchNo(HSSFSheet sheet) {
		String batchId = "";
		HSSFRow row = null;
		try {
			row = sheet.getRow(0);
			if (row != null) {
				batchId = parseCellToString(row.getCell(2));
			}
		} catch (Exception e) {
			LogUtil.info(ParseBatchPay2AcctTemplateUtils.class, "解析批量付款模板", OPSTATUS.EXCEPTION, "", "读取excl格式错误");
		}
		return batchId;
	}
	
	/**
	 * 将单元格内容转换为String 
	 * 转换异常返回空
	 * @param cell
	 * @return
	 */
	public static String parseCellToString(HSSFCell cell){
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
		}catch (Throwable e) {
			LogUtil.info(ParseBatchPay2AcctTemplateUtils.class, "解析批量付款模板", OPSTATUS.EXCEPTION, "", "读取excl格式错误");
		}
		content = StringUtil.null2String(content);
		return trim(content);
	}
	private static String trim(String src){
		if(src==null){
			return "";
		}
		String dest = src.trim();
		while(src.startsWith("　")){
			dest = dest.replaceFirst("　", "");
		}
		while(dest.endsWith("　")){
			dest = dest.substring(0, dest.length()-1);
		}
		return dest;
	}
	
	/**
	 * 验证字段长度
	 * @param content
	 * @param title
	 * @param length
	 * @param detail
	 * @return
	 */
	public static String validateLength(String content,String title, int length,BatchPaymentToBankReqDetailDTO detail){
		int temp = 0;
		try {
			temp = content.getBytes("utf8").length;
		} catch (Exception e) {
			length = 0;
			LogUtil.info(ParseBatchPay2AcctTemplateUtils.class, "解析批量付款模板", OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
		}
		String recontent =content;
		if(length < temp){
			try {
				byte[] tempByte = content.getBytes("utf8");
				recontent = new String(Arrays.copyOfRange(tempByte, 0, length-3))+"..";
			} catch (UnsupportedEncodingException e) {
				LogUtil.info(ParseBatchPay2AcctTemplateUtils.class, "解析批量付款模板", OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
			}
			detail.setErrorMsg(title + "不能大于"+ length + "字符");
		}
		return recontent;
	}
	
	/**
	 * 验证模板
	 * @param row
	 * @return
	 */
	public static boolean  validateHeadRow(HSSFRow row){
		Iterator<HSSFCell> cells = row.iterator();
		if(cells.hasNext()) {
			HSSFCell cell =  cells.next();
			String content = parseCellToString(cell);	
			int index = cell.getColumnIndex();
			if(index>HEADROW.length||index<0){
				return false;
			}
			if(!content.contains(HEADROW[index])){
				return false;
			}
			return true;
		}
		return false;
	}
	
	
	/**
	 * 判断请求处理的记录是否有效，验证是否是空行
	 * @param detail
	 * @return
	 */
	public static boolean isValidDetail(BatchPaymentToAcctReqDetailDTO detail){
		boolean flage = false;
		if(!StringUtil.isEmpty(detail.getPayeeName()) 
			|| !StringUtil.isEmpty(detail.getPayeeLoginName()) || !StringUtil.isEmpty(detail.getRequestAmount())
			|| !StringUtil.isEmpty(detail.getRemark())){
			return true;
		}
		return flage;
	}
	
	
	/**
	 * 将excel行转化为对象
	 * @param row
	 * @return
	 */
	public static BatchPaymentToAcctReqDetailDTO getgetBatchPaymentToAcctReqDetail(HSSFRow  row){
		BatchPaymentToAcctReqDetailDTO detail = new BatchPaymentToAcctReqDetailDTO();
		Iterator<HSSFCell> cells = row.iterator();
		while (cells.hasNext()) {
			HSSFCell cell =  cells.next();
			String content = parseCellToString(cell);	
			if(cell.getColumnIndex() == 0){
				content = validateLength(content, "商户流水号", 30, detail);
				detail.setForeignOrderId(content);
			}else if (cell.getColumnIndex() == 1) {
				content = validateLength(content, "收款方姓名", 256, detail);
				detail.setPayeeName(content);
			}else if(cell.getColumnIndex() == 2){
				content = validateLength(content, "收款方帐户", 256, detail);
				detail.setPayeeLoginName(content);
			}else if(cell.getColumnIndex() == 3){
				content = validateLength(content, "付款金额", 19, detail);
				detail.setRequestAmount(content);
			}else if(cell.getColumnIndex() == 4){
				content = validateLength(content, "备注", 150, detail);
				detail.setRemark(content);
			}
		}
		return detail;
	}
	
	/**
	 * 验证字段长度
	 * @param content
	 * @param title
	 * @param length
	 * @param detail
	 * @return
	 */
	public static String validateLength(String content,String title, int length,BatchPaymentToAcctReqDetailDTO detail){
		int temp = 0;
		try {
			temp = content.getBytes("utf8").length;
		} catch (Exception e) {
			length = 0;
			LogUtil.info(ParseBatchPay2AcctTemplateUtils.class, "解析批量付款模板", OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
		}
		String recontent =content;
		if(length < temp){
			try {
				byte[] tempByte = content.getBytes("utf8");
				recontent = new String(Arrays.copyOfRange(tempByte, 0, length-3))+"...";
			} catch (UnsupportedEncodingException e) {
				LogUtil.info(ParseBatchPay2AcctTemplateUtils.class, "解析批量付款模板", OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
			}
			detail.setErrorMsg(title + "不能大于"+ length + "字符");
		}
		return recontent;
	}
	
	
	/**
	 * 验证单笔信息是否合法
	 * @param detail
	 */
	public static void validateRequestDetail(BatchPaymentToAcctReqDetailDTO detail, RCLimitResultDTO rule) {
		//设置默认属性
		detail.setValidateStatus(0);
		
		try {
			
			if (!AmountUtils.checkAmount(detail.getRequestAmount())) {
				detail.setPaymentAmount(0L);
				detail.setErrorMsg("金额格式不正确");
				return;
			}
			
			Long amount = AmountUtils.toLongAmount(detail.getRequestAmount());
			detail.setPaymentAmount(amount);
			
			if(amount.longValue()<=0L){
				detail.setErrorMsg("金额不正确");
				detail.setPaymentAmount(0L);
				return;
			}
			
		} catch (Exception e) {
			detail.setErrorMsg("金额格式不正确");
			detail.setPaymentAmount(0L);
			return;
		}
		
		//增加判断金额必须大于0才能支付
		if(rule.getSingleLimit() < detail.getPaymentAmount().longValue()){ 
			detail.setErrorMsg("单笔最多付款"+AmountUtils.numberFormat(rule.getSingleLimit())+"元");
			return;
		}
		
		// 商家订单号
		if (!StringUtil.isEmpty(detail.getForeignOrderId()) && validateLengError(detail.getForeignOrderId(),30)) {
			detail.setErrorMsg("商家订单号长度超出限制");
			return;
		}
		
		if (StringUtil.isEmpty(detail.getPayeeName())){
			detail.setErrorMsg("收款方姓名不能为空");
			return;
		}else if(validateLengError(detail.getPayeeName(),50)) {
			detail.setErrorMsg("收款方姓名长度不能大于50个字符");
			return;
		}
		
		if (StringUtil.isEmpty(detail.getPayeeLoginName())){
			detail.setErrorMsg("收款方账户不能为空");
			return;
		}else if(validateLengError(detail.getPayeeName(),50)) {
			detail.setErrorMsg("收款方账户长度不能大于50个字符");
			return;
		}
			
		
		if(detail.getPayeeLoginName().contains("@")){
			if(!IDContentUtil.validateEmail(detail.getPayeeLoginName())){
				detail.setErrorMsg("收款账户不是11位手机号或Email格式");
				return;
			}
		}else{
			if(!IDContentUtil.validateMobile(detail.getPayeeLoginName())){
				detail.setErrorMsg("收款账户不是11位手机号或Email格式");
				return;
			}
		}
		
		
		// 备注
		if (!StringUtil.isEmpty(detail.getRemark()) && validateLengError(detail.getRemark(), 100)) {
			detail.setErrorMsg("备注长度超出限制");
			return;
		}
		
		detail.setValidateStatus(1);
	}
	
	
	public static boolean validateLengError(String content,int max){
		int len = 0;
		for (int i = 0; i < content.length(); i++) {
			if (content.toCharArray()[i] > 255) {
				len += 2;
			} else {
				len++;
			}
		}
		if(len>max){
			return true;
		}
		return false;
	}
	

}
