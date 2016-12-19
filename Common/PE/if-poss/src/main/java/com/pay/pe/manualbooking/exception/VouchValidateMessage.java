package com.pay.pe.manualbooking.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 手工记帐申请数据验证消息
 */
public class VouchValidateMessage {	
	private static final Log LOG = LogFactory.getLog(VouchValidateMessage.class);
	public static final String MESSAGE_FILE_NAME = "context/pe/vouch_message.properties";
	
	private static final String FILE_NOT_EXCEL_MESSAGE = "file_not_excel_message";
	private static final String FILE_IS_EMPTY_MESSAGE = "file_is_empty_message";
	private static final String RECORDS_EMPTY_MESSAGE = "records_empty_message";
	private static final String RECORD_SIZE_EXCEED_MESSAGE = "record_size_exceed_message";
	private static final String ACCOUNT_IS_EMPTY_MESSAGE = "account_is_empty_message";
	private static final String ACCOUNT_IS_NOT_NUMERIC_MESSAGE = "account_is_not_numeric_message";
	private static final String ACCOUNT_NOT_EXIST_MESSAGE = "account_not_exist_message";
	private static final String ACCOUNT_IS_FROZEN_MESSAGE = "account_is_frozen_message";
	private static final String ACCOUNT_FUND_IN_DISABLE_MESSAGE = "account_fund_in_disable_message";
	private static final String ACCOUNT_FUND_OUT_DISABLE_MESSAGE = "account_fund_out_disable_message";
	private static final String NOT_ONE_SIDE_AMOUNT_MESSAGE = "not_one_side_amount_message";
	private static final String AMOUNT_IS_EMPTY_MESSAGE = "amount_is_empty_message";
	private static final String AMOUNT_IS_NOT_NUMERIC_MESSAGE = "amount_is_not_numeric_message";
	private static final String AMOUNT_IS_ZERO_MESSAGE = "amount_is_zero_message";
	private static final String AMOUNT_EXCEED_TWO_SCALE_MESSAGE = "amount_exceed_two_scale_message";
	private static final String AMOUNT_TOO_LARGE_MESSAGE = "amount_too_large_message";
	private static final String CR_TOTAL_NOT_EQUAL_DR_TOTAL_MESSAGE = "cr_total_not_equal_dr_total_message";
	private static final String REMARK_TOO_LONG_MESSAGE = "remark_too_long_message";
	private static final String ITEM_SEQ_MESSAGE = "item_seq_message";
	
	//private static ResourceBundle messages;
	private static Properties messages;
	static {
		LOG.debug("Start");
		InputStream inputStream = null;
		try {
			inputStream = VouchValidateMessage.class.getClassLoader().getResourceAsStream(MESSAGE_FILE_NAME);
			
			messages = new Properties();
			LOG.debug("Before load!");
			messages.load(inputStream);
			LOG.debug("After load!");
			LOG.debug("Messages : " + messages);
		} catch (IOException e) {
			LOG.debug("Fails to load vouch message!", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOG.debug("Fails to close input stream!", e);
				}
			}
		}
		LOG.debug("End");
		/*messages = new Properties();
		messages.put(FILE_NOT_EXCEL_MESSAGE, "不是Excel文件，不支持Excel2007");
		messages.put(FILE_IS_EMPTY_MESSAGE, "文件内容不能为空");
		messages.put(RECORDS_EMPTY_MESSAGE, "记录不能为空");
		messages.put(RECORD_SIZE_EXCEED_MESSAGE, "记录数过多，不能多余200条");
		messages.put(ACCOUNT_IS_EMPTY_MESSAGE, "帐户不能为空");
		messages.put(ACCOUNT_IS_NOT_NUMERIC_MESSAGE, "帐户不是数字");
		messages.put(ACCOUNT_NOT_EXIST_MESSAGE, "帐户不存在");
		messages.put(ACCOUNT_IS_FROZEN_MESSAGE, "帐户已冻结");
		messages.put(ACCOUNT_FUND_IN_DISABLE_MESSAGE, "帐户止入");
		messages.put(ACCOUNT_FUND_OUT_DISABLE_MESSAGE, "帐户止出");
		messages.put(NOT_ONE_SIDE_AMOUNT_MESSAGE, "借贷不能同时有金额或空");
		messages.put(AMOUNT_IS_EMPTY_MESSAGE, "金额不能为空");
		messages.put(AMOUNT_IS_NOT_NUMERIC_MESSAGE, "金额不是数字");
		messages.put(AMOUNT_IS_ZERO_MESSAGE, "金额不能为零");
		messages.put(CR_TOTAL_NOT_EQUAL_DR_TOTAL_MESSAGE, "借贷总计数额不相等");
		messages.put(REMARK_TOO_LONG_MESSAGE, "备注数据过长");
		messages.put(ITEM_SEQ_MESSAGE, "记录序号");*/
		
	}
	
	
	private String message;
	
	private VouchValidateMessage(final String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return message;
	}
	
	public static VouchValidateMessage createRecordsEmptyMessage() {
		String message = messages.getProperty(RECORDS_EMPTY_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createRecordSizeExceedMessage() {
		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
//		String message ="记录数过多，不能多余2条";
		
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAccountIsEmptyMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(ACCOUNT_IS_EMPTY_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAccountIsNotNumericMessage(int i, String accountCode) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + accountCode + messages.getProperty(ACCOUNT_IS_NOT_NUMERIC_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAccountNotExistMessage(int i, String accountCode) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + accountCode + messages.getProperty(ACCOUNT_NOT_EXIST_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAccountIsFrozenMessage(int i, String accountCode) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + accountCode + messages.getProperty(ACCOUNT_IS_FROZEN_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	
	public static VouchValidateMessage createAccountIsFrozenMessage( String accountCode) {
		String message = accountCode + messages.getProperty(ACCOUNT_IS_FROZEN_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAccountFundInDisableMessage(int i, String accountCode) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + accountCode + messages.getProperty(ACCOUNT_FUND_IN_DISABLE_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAccountFundInDisableMessage(String accountCode) {
		String message = accountCode + messages.getProperty(ACCOUNT_FUND_IN_DISABLE_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	
	public static VouchValidateMessage createAccountFundOutDisableMessage(int i, String accountCode) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + accountCode + messages.getProperty(ACCOUNT_FUND_OUT_DISABLE_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAccountFundOutDisableMessage(String accountCode) {
		String message = accountCode + messages.getProperty(ACCOUNT_FUND_OUT_DISABLE_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createNotOneSideAmountMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(NOT_ONE_SIDE_AMOUNT_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAmountIsEmptyMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(AMOUNT_IS_EMPTY_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAmountIsNotNumericMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(AMOUNT_IS_NOT_NUMERIC_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAmountIsZeroMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(AMOUNT_IS_ZERO_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAmountLessZeroMessage(int i) {
		String message = "第 " + i + "　行科目金额不能小于０！";
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAmountExceedTwoScaleMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(AMOUNT_EXCEED_TWO_SCALE_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAmountTooLargeMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(AMOUNT_TOO_LARGE_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAcctCodeMatchAcctNameMessage(int i) {
		String message = "第 " + i + "　行科目账户与科目名称不匹配！";
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createCrRecordlNotEqualDrRecordMessage() {
		String message = "借贷记账数不匹配";
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createCrTotalNotEqualDrTotalMessage() {
		String message = messages.getProperty(CR_TOTAL_NOT_EQUAL_DR_TOTAL_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createCrNotEqualDrMessage(int i) {
		String message = "第 " + i + ": 行与第" +(i-1)+" 行不是一借一贷";
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createCrAmountNotEqualDrAmountMessage(int i) {
		String message = "第 " + i + ": 行与第" +(i-1)+" 行借贷金额不相等";
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createFileNotExcelMessage() {
		String message = messages.getProperty(FILE_NOT_EXCEL_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createFileIsEmptyMessage() {
		String message = messages.getProperty(FILE_IS_EMPTY_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createRemarkTooLongMessage(int i) {
		String message = messages.getProperty(ITEM_SEQ_MESSAGE)
			+ i + ": " + messages.getProperty(REMARK_TOO_LONG_MESSAGE);
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAcctNameNullMessage(int i) {
		String message = "第 " + i + ": 行 帐户名称不能为空";
		return new VouchValidateMessage(message);
	}
	
	public static VouchValidateMessage createAcctNameToolagerMessage(int i) {
		String message = "第 " + i + ": 行 帐户名称不能超过50个字符";
		return new VouchValidateMessage(message);
	}
	public static VouchValidateMessage createValidateMessage(String message) {
		return new VouchValidateMessage(message);
	}
	
}
