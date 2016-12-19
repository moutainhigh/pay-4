package com.pay.poss.memberrelation.exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 关联申请数据验证消息
 */
public class VouchRelationValidateMessage {	
	private static final Log LOG = LogFactory.getLog(VouchRelationValidateMessage.class);
	public static final String MESSAGE_FILE_NAME = "context/ma/vouch_message.properties";
	
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
			inputStream = VouchRelationValidateMessage.class.getClassLoader().getResourceAsStream(MESSAGE_FILE_NAME);
			
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
	
	private VouchRelationValidateMessage(final String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String toString() {
		return message;
	}
	
	public static VouchRelationValidateMessage createRecordsEmptyMessage() {
		String message = messages.getProperty(RECORDS_EMPTY_MESSAGE);
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createFileNotExcelMessage() {
		String message = messages.getProperty(FILE_NOT_EXCEL_MESSAGE);
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createRecordSizeExceedMessage() {
		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createAmountEmailIsNullMessage(int i) {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "第 " + i + ": 行账户email不能为空";
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createEmailTooLongMessage(int i) {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "第 " + i + ": 行账户email超过了字符限制";
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createSunMemberCodeNotExistsMessage(int i) {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "第 " + i + ": 行账户email在系统不存在";
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createMemberCodeIsEmptyMessage() {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "利安总店会员 号不能为空";
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createMemberCodeNotLongMessage() {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "利安总店会员号格式不正确";
		return new VouchRelationValidateMessage(message);
	}
	public static VouchRelationValidateMessage createMemberCodeNotExistsMessage() {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "利安总店会员号在系统中不存在";
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createStoreNumbersIsNullMessage(int i) {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "第 " + i + ": 行门店编号不能为空";
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createStoreNumbersTooLongMessage(int i) {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "第 " + i + ": 行门店编号超过了字符限制";
		return new VouchRelationValidateMessage(message);
	}
	
	public static VouchRelationValidateMessage createStoreAddressIsNullMessage(int i) {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "第 " + i + ": 行门店地址不能为空";
		return new VouchRelationValidateMessage(message);
	}
	public static VouchRelationValidateMessage createStoreAddressTooLongMessage(int i) {
//		String message = messages.getProperty(RECORD_SIZE_EXCEED_MESSAGE);
		String message = "第 " + i + ": 行门店地址超过了字符限制";
		return new VouchRelationValidateMessage(message);
	}
	

	public static VouchRelationValidateMessage createValidateMessage(String message) {
		return new VouchRelationValidateMessage(message);
	}
	
}
