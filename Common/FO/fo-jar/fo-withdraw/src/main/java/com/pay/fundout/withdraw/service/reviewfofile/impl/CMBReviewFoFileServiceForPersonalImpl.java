package com.pay.fundout.withdraw.service.reviewfofile.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.fundout.withdraw.service.reviewfofile.AbstractReviewFoFileService;
import com.pay.fundout.withdraw.service.reviewfofile.dto.ReviewFoFileDTO;

/**
 * @author lIWEI
 * @Date 2011-4-15
 * @Description 招商银行文件解析器对私
 * @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class CMBReviewFoFileServiceForPersonalImpl extends AbstractReviewFoFileService {
	
	protected transient Log log = LogFactory.getLog(getClass());

	/**
	 * 招商银行提现复核
	 * 
	 * 对私： 帐号,户名,金额,状态,注释,提示,开户行,开户地
	 * 6225881215667659,倪佳,0.01,数据录入,W000100310000490,,招商银行,
	 * 6225881215667659,倪佳,0.02,数据录入,W000100310000491,,招商银行,
	 * 6225881215667659,周媛,0.01,数据录入,W000100310000492,,招商银行,
	 * 6225881215667659,周媛,"1,230.02",数据录入,W000100310000493,,招商银行,
	 */
	@Override
	protected List<ReviewFoFileDTO> parserImportFile(InputStream inputStream) {
		final BufferedReader buffered = new BufferedReader(new InputStreamReader(inputStream));// 导入文件的缓冲流
		String line;// 文件的当前行
		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();// 解析结果
		try {
			line = buffered.readLine();
			line = buffered.readLine();
			ReviewFoFileDTO reviewFoFileDTO = null;
			while (line != null && line.length() != 0) {
				String[] lineArray = line.split(",");
				reviewFoFileDTO = new ReviewFoFileDTO();
				reviewFoFileDTO.setUsage(lineArray[6]);// 附言用途，含订单流水
				reviewFoFileDTO.setAmount(new BigDecimal(lineArray[4]));// 金额
				reviewFoFileDTO.setPayeeAccountNo(lineArray[0]);// 收款人账号
				reviewFoFileDTO.setPayeeName(lineArray[1]);// 收款人名称
				reviewFoFileDTOs.add(reviewFoFileDTO);
				line = buffered.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(buffered);
			IOUtils.closeQuietly(inputStream);
		}
		return reviewFoFileDTOs;
	}

	/** 
	 * 卡号    收款人名字    金额       提现流水号
	 *  ${order.cardNo} ${order.accountName} ${order.amount}${order.withdrawalSeqNo}
	 */
	@Override
	protected List<ReviewFoFileDTO> parserLoadLocalFile(InputStream inputStream) {

		final List<ReviewFoFileDTO> reviewFoFileDTOs = new ArrayList<ReviewFoFileDTO>();// 解析结果
		try {
			int rowIndex = 1;
			HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
			HSSFSheet mainSheet = workbook.getSheetAt(0);
			HSSFRow row = mainSheet.getRow(rowIndex);// start from the second row.
			ReviewFoFileDTO reviewFoFileDTO = null;
			int allRowCount = mainSheet.getPhysicalNumberOfRows();
			while (rowIndex < allRowCount) {
				reviewFoFileDTO = new ReviewFoFileDTO();

				// 附言用途，含订单流水
				if (row.getCell(4) != null && StringUtils.isNotEmpty(row.getCell(4).getStringCellValue())) {
					reviewFoFileDTO.setUsage((row.getCell(4)).getStringCellValue());
				}
				// 金额
				if (row.getCell(3) != null && StringUtils.isNotEmpty(row.getCell(3).getStringCellValue())) {// 金额
					reviewFoFileDTO.setAmount(new BigDecimal(row.getCell(3).getNumericCellValue()).multiply(new BigDecimal(1000)).divide(new BigDecimal(1), 0,
							BigDecimal.ROUND_HALF_UP));
				}
				// 收款人账号
				if (row.getCell(1) != null && StringUtils.isNotEmpty(row.getCell(1).getStringCellValue())) {
					reviewFoFileDTO.setPayeeAccountNo((row.getCell(1)).getStringCellValue());
				}
				// 收款人名称
				if (row.getCell(2) != null && StringUtils.isNotEmpty(row.getCell(2).getStringCellValue())) {
					reviewFoFileDTO.setPayeeName((row.getCell(2)).getStringCellValue());
				}
				reviewFoFileDTOs.add(reviewFoFileDTO);
				rowIndex++;
				row = mainSheet.getRow(rowIndex);
			}
		} catch (Exception e) {
			log.error(this.getClass().getName() + "----------:" + e.getMessage(), e);
		}
		return reviewFoFileDTOs;
	}

}
