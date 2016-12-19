package com.pay.poss.refund.common.util;

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

import com.pay.poss.refund.model.RefundBatchInfoDTO;

public class PoiExcelUtil {
	
	private static InputStream getTemplatePath(){
		/*FileInputStream fileInputStream = null;
		try {
			StringBuilder stringBuilder = new StringBuilder();
			String separator = File.separator;
			stringBuilder.append(separator).append("opt").append(separator).append("pay").append(separator).append("config").append(separator)
			.append("poss").append(separator).append("refund.xls");
			fileInputStream = new FileInputStream(new File(stringBuilder.toString()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}*/
//		InputStream inputStream = new PoiExcelUtil().getClass().getResourceAsStream("/com/sys/pay/poss/refund/common/util/refund.xls");
		InputStream inputStream  = new PoiExcelUtil().getClass().getResourceAsStream("/properties/refund.xls");
		return inputStream;
	}
	

	public static String processExcel(Map<String, String> fileInfo,
			RefundBatchInfoDTO refundBatchInfoDTO) {
		String newPath = "";
		
		try {
			HSSFWorkbook wb = new HSSFWorkbook(getTemplatePath());
			HSSFSheet sheet = wb.getSheetAt(0); // 得到第一个工作薄

			for (Row row : sheet) {
				for (Cell cell : row) {
					String strValue = cell.getStringCellValue();
					String value = processReg(strValue);
					if ("batchNum".equalsIgnoreCase(value)) {
						cell.setCellValue(processReplace(strValue,
								refundBatchInfoDTO.getBatchNum()));
					}
					if ("totalAmount".equalsIgnoreCase(value)) {
						BigDecimal amount = refundBatchInfoDTO.getTotalAmount();
						if (amount.compareTo(new BigDecimal(0)) >= 0) {
							String totalAmount = String.valueOf(amount);
							cell.setCellValue(processReplace(strValue,totalAmount));
						} else {
							cell.setCellValue(processReplace(strValue, ""));
						}
					}
					if ("totalCount".equalsIgnoreCase(value)) {
						Integer totalCount = refundBatchInfoDTO.getTotalCount();
						if (0 != totalCount)
							cell.setCellValue(processReplace(strValue,
									totalCount.toString()));
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
