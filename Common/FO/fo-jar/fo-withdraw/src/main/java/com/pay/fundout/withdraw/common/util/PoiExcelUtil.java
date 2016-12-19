package com.pay.fundout.withdraw.common.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.pay.fundout.withdraw.model.schedule.WithdrawMasterInfo;

public class PoiExcelUtil {

	private static InputStream getTemplatePath(){
//		InputStream inputStream = new PoiExcelUtil().getClass().getResourceAsStream("/com.pay/pay/fundout/withdraw/common/util/refund.xls");
		InputStream inputStream  = new PoiExcelUtil().getClass().getResourceAsStream("/properties/refund.xls");
		return inputStream;
	}
	
	public static String processExcel(Map<String, String> fileInfo,WithdrawMasterInfo withdrawMasterInfo) {
		String newPath = "";
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook(getTemplatePath());
			HSSFSheet sheet = wb.getSheetAt(0); // 得到第一个工作薄

			for (Row row : sheet) {
				for (Cell cell : row) {
					String strValue = cell.getStringCellValue();
					String value = processReg(strValue);
					if ("batchNum".equalsIgnoreCase(value)) {
						cell.setCellValue(processReplace(strValue,withdrawMasterInfo.getBatchNum()));
					}
					if ("totalAmount".equalsIgnoreCase(value)) {
						BigDecimal amount = withdrawMasterInfo.getTotalAmount();
						if (amount.compareTo(new BigDecimal(0)) >= 0) {
							amount = amount.divide(new BigDecimal(1000));
							amount = amount.setScale(2, BigDecimal.ROUND_HALF_UP);
							String totalAmount = String.valueOf(amount);
							cell.setCellValue(processReplace(strValue,totalAmount));
						} else {
							cell.setCellValue(processReplace(strValue, ""));
						}
					}
					if ("totalCount".equalsIgnoreCase(value)) {
						Integer totalCount = withdrawMasterInfo.getTotalCount();
						if (0 != totalCount)
							cell.setCellValue(processReplace(strValue,totalCount.toString()));
						else
							cell.setCellValue("");
					}
					if ("bankCode".equalsIgnoreCase(value)) {
						String bankCode = withdrawMasterInfo.getBankCode();
						if (StringUtils.isNotEmpty(bankCode))
							cell.setCellValue(processReplace(strValue,bankCode));
						else
							cell.setCellValue("");
					}
				}
			}
			newPath = CreatorFileDirUtil.writeFile(wb, fileInfo);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newPath;
	}

	public static String processReg(String strValue) {
		int index = 0;
		int lastIndex = 0;
		if (StringUtils.isNotEmpty(strValue)) {
			index = strValue.indexOf("$");
			lastIndex = strValue.lastIndexOf("}");
			if (index != -1 && lastIndex != -1)
				return strValue.substring(index + 3, lastIndex);
		}
		return "";
	}

	public static String processReplace(String strValue, String value) {
		String reg = "\\$F\\{.+\\}";
		return strValue.replaceFirst(reg, value);
	}
}
