/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import net.sf.jxls.transformer.Row;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fundout.util.AmountUtils;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public class BatchPay2BankTemplateUtils {

	/**
	 * 银行卡0-9
	 */
	private final static String BANKACCT_PATTERN = "^[0-9]+$";

	public static final String TEMPLATEID = "MP2BANKT0001";// 批付到银行基础模板

	/**
	 * 获取业务批次号
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String getBusinessBatchNo(Sheet sheet) {
		String batchId = "";
		try {
			Cell[] cells = sheet.getRow(0);
			if (cells != null) {
				batchId = parseCellToString(cells[2]);
			}
		} catch (Exception e) {
			LogUtil.info(BatchPay2BankTemplateUtils.class, "解析批量付款模板",
					OPSTATUS.EXCEPTION, "", "读取excl格式错误");
		}
		return batchId;
	}

	/**
	 * 将单元格内容转换为String 转换异常返回空
	 * 
	 * @param cell
	 * @return
	 */
	public static String parseCellToString(Cell cell) {

		String content = "";
		if (null != cell) {
			
			content = cell.getContents();
			content = StringUtil.null2String(content);
			/*try {
				switch (cell.) {// 获得单元格数据类型
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
				case HSSFCell.CELL_TYPE_FORMULA:
					content = String.valueOf(cell.getNumericCellValue());
					break;
				default:
					content = cell.getStringCellValue();
				}
			} catch (Throwable e) {
				LogUtil.info(BatchPay2BankTemplateUtils.class, "解析批量付款模板",
						OPSTATUS.EXCEPTION, "", "读取excl格式错误");
			}
			content = StringUtil.null2String(content);*/
		}
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
	 * 
	 * @param content
	 * @param title
	 * @param length
	 * @param detail
	 * @return
	 */
	public static String validateLength(String content, String title,
			int length, BatchPaymentToBankReqDetailDTO detail) {
		int temp = 0;
		try {
			temp = content.getBytes("utf8").length;
		} catch (Exception e) {
			length = 0;
			LogUtil.info(BatchPay2BankTemplateUtils.class, "解析批量付款模板",
					OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
		}
		String recontent = content;
		if (length > 3 && length < temp) {
			try {
				byte[] tempByte = content.getBytes("utf8");
				recontent = new String(Arrays.copyOfRange(tempByte, 0,
						length - 3))
						+ "..";
			} catch (UnsupportedEncodingException e) {
				LogUtil.info(BatchPay2BankTemplateUtils.class, "解析批量付款模板",
						OPSTATUS.EXCEPTION, "", "字符编码转utf8异常");
			}
			detail.setErrorMsg(title + "不能大于" + length + "字符");
		}
		return recontent;
	}

	/**
	 * 验证模板
	 * 
	 * @param row
	 * @return
	 */
	public static boolean validateHeadRow(Row row) {
		
		List<Cell> cells = row.getCells();
		if (null != cells && !cells.isEmpty()) {
			for(Cell cell : cells){
				String content = parseCellToString(cell);
				int index = cell.getColumn();
				if (index > BatchPaymentToBankReqDetailDTO.title_columns_value.length
						|| index < 0) {
					return false;
				}
				if (!content
						.contains(BatchPaymentToBankReqDetailDTO.title_columns_value[index])) {
					return false;
				}
				return true;
			}

		}
		return false;
	}

	/**
	 * 将excel行转化为对象
	 * 
	 * @param row
	 * @return
	 */
	public static BatchPaymentToBankReqDetailDTO getBatchPaymentToBankReqDetail(
			Cell[] cells) {

		BatchPaymentToBankReqDetailDTO detail = new BatchPaymentToBankReqDetailDTO();

		int columns_size = detail.title_columns_propties.length;
		StringBuilder properties = new StringBuilder();
		for (int i = 0; i < columns_size; i++) {
			
			if (null != cells && cells.length == columns_size) {
				Cell cell = cells[i];
				String content = parseCellToString(cell);
				properties.append(content);
				BeanWrapper beanWrapper = new BeanWrapperImpl(detail);
				if("payeeBankAcctCode".equalsIgnoreCase(detail.title_columns_propties[i])){
					content= content.replaceAll(" ", "").replaceAll("　", "");
				}
				content = validateLength(content,
						detail.title_columns_value[i],
						detail.title_columns_length[i], detail);
				beanWrapper.setPropertyValue(detail.title_columns_propties[i],
						content);
			}
		}
		if (StringUtil.isEmpty(properties.toString())) {
			detail = null;
		}
		return detail;
	}

	/**
	 * 判断请求处理的记录是否有效，验证是否是空行
	 * 
	 * @param detail
	 * @return
	 */
	public static boolean isValidDetail(BatchPaymentToBankReqDetailDTO detail) {
		boolean flage = false;
		if (!StringUtil.isNull(detail.getPayeeBankProvinceName())
				|| !StringUtil.isNull(detail.getPayeeBankCityName())
				|| !StringUtil.isNull(detail.getPayeeBankName())
				|| !StringUtil.isNull(detail.getPayeeOpeningBankName())
				|| !StringUtil.isNull(detail.getPayeeName())
				|| !StringUtil.isNull(detail.getPayeeBankAcctCode())
				|| !StringUtil.isNull(detail.getTradeType())
				|| !StringUtil.isNull(detail.getRequestAmount())
				|| !StringUtil.isNull(detail.getForeignOrderId())
				|| !StringUtil.isNull(detail.getRemark())) {
			return true;
		}
		return flage;
	}

	/**
	 * 验证单笔信息是否合法
	 * 
	 * @param detail
	 */
	public static void validateRequestDetail(
			BatchPaymentToBankReqDetailDTO detail, RCLimitResultDTO rule,
			Map<String, Integer> provinceCode, Map<String, Integer> cityCode,
			Map<String, String> bankCode) {
		// 设置默认属性
		detail.setValidStatus(0);
		try {

			if (!AmountUtils.checkAmount(detail.getRequestAmount())) {
				detail.setPaymentAmount(0L);
				detail.setErrorMsg("金额格式不正确");
				return;
			}

			Long amount = AmountUtils.toLongAmount(detail.getRequestAmount());
			detail.setPaymentAmount(amount);

			if (amount.longValue() <= 0L) {
				detail.setErrorMsg("金额不正确");
				detail.setPaymentAmount(0L);
				return;
			}

		} catch (Exception e) {
			detail.setErrorMsg("金额格式不正确");
			detail.setPaymentAmount(0L);
			return;
		}

		// 增加判断金额必须大于0才能支付
		if (rule.getSingleLimit() < detail.getPaymentAmount().longValue()) {
			detail.setErrorMsg("单笔最多付款"
					+ AmountUtils.numberFormat(rule.getSingleLimit()) + "元");
			return;
		}

		Integer proCode = null;
		if (!StringUtil.isNull(detail.getPayeeBankProvinceName())) {// 省份
			proCode = provinceCode.get(detail.getPayeeBankProvinceName());
			if (null == proCode) {
				detail.setErrorMsg("开户银行所属省份信息不正确");
				return;
			} else {
				detail.setPayeeBankProvince(String.valueOf(proCode));
			}
		} else {
			detail.setErrorMsg("开户银行所属省份不能为空");
			return;
		}

		Integer city = 1;
		if (!StringUtil.isNull(detail.getPayeeBankCityName())) {// 城市
			city = cityCode.get(detail.getPayeeBankProvince()
					+ detail.getPayeeBankCityName());
			if (null == city) {
				detail.setErrorMsg("开户银行所属城市信息不正确");
				return;
			} else {
				detail.setPayeeBankCity(String.valueOf(city));
			}
		} else {
			detail.setErrorMsg("开户银行所属城市不能为空");
			return;
		}

		if (StringUtil.isNull(detail.getPayeeBankName())) {
			detail.setErrorMsg("银行名称不能为空");
			return;
		} else {
			String payeeBankCode = "";
			String bankNameCode = bankCode.get(detail.getPayeeBankName());
			try {
				payeeBankCode = bankNameCode;
			} catch (Exception e) {
				payeeBankCode = null;
			}
			if (null != payeeBankCode) {
				detail.setPayeeBankCode(payeeBankCode);
			} else {
				detail.setErrorMsg("银行名称不正确");
				return;
			}
		}

		if (StringUtil.isNull(detail.getPayeeOpeningBankName())) {
			detail.setErrorMsg("开户行名称不能为空");
			return;
		} else if (validateLengError(detail.getPayeeOpeningBankName(), 60)) {
			detail.setErrorMsg("开户行名称长度不能大于60个字符");
			return;
		}

		if (StringUtil.isNull(detail.getTradeType())) {
			detail.setErrorMsg("收款方账户类型不能为空");
			return;
		} else if (validateLengError(detail.getTradeType(), 1)) {
			detail.setErrorMsg("收款方账户类型不正确");
			return;
		}

		// B 企业；C 个人
		if (!"C".equalsIgnoreCase(detail.getTradeType())
				&& !"B".equalsIgnoreCase(detail.getTradeType())) {
			detail.setErrorMsg("收款方账户类型不正确");
			return;
		}

		if (StringUtil.isNull(detail.getPayeeName())) {
			detail.setErrorMsg("收款方姓名不能为空");
			return;
		} else if (validateLengError(detail.getPayeeName(), 50)) {
			detail.setErrorMsg("收款方姓名长度不能大于50个字符");
			return;
		}

		if (StringUtil.isNull(detail.getPayeeBankAcctCode())) {
			detail.setErrorMsg("银行账号不能为空");
			return;
		} else if ((detail.getPayeeBankAcctCode().length() < 8)
				|| detail.getPayeeBankAcctCode().length() > 32
				|| !checkBankAcctCode(detail.getPayeeBankAcctCode())) {
			detail.setErrorMsg("银行账号不正确");
			return;
		}
		detail.setPayeeBankAcctCode(detail.getPayeeBankAcctCode().replaceAll(" ", "").replaceAll("　", ""));

		// 备注
		if (!StringUtil.isNull(detail.getRemark())
				&& validateLengError(detail.getRemark(), 100)) {
			detail.setErrorMsg("备注长度超出限制");
			return;
		}
		// 商家订单号
		if (!StringUtil.isNull(detail.getForeignOrderId())
				&& validateLengError(detail.getForeignOrderId(), 30)) {
			detail.setErrorMsg("商家订单号长度超出限制");
			return;
		}
		detail.setValidStatus(1);
	}

	/**
	 * 判断银行卡是否正常，全部为数字的为合法卡号.
	 * 
	 * @param bankAcctId
	 * @return
	 */
	public static boolean checkBankAcctCode(String bankAcctCode) {
		boolean ret = false;
		try {
			Pattern p = Pattern.compile(BANKACCT_PATTERN);
			Matcher m = p.matcher(bankAcctCode);
			ret = m.matches();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

	public static boolean validateLengError(String content, int max) {
		int len = 0;
		for (int i = 0; i < content.length(); i++) {
			if (content.toCharArray()[i] > 255) {
				len += 2;
			} else {
				len++;
			}
		}
		if (len > max) {
			return true;
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println("1111 1111 1111 1111".replaceAll(" ", "").replaceAll("　", ""));
		System.out.print(trim("你好　"));
		System.out.println("|");
	}

}
