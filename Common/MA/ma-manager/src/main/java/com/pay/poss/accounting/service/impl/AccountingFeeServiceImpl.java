/**
 * 
 */
package com.pay.poss.accounting.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.inf.dao.Page;
import com.pay.poss.accounting.dao.AccountingFeeDao;
import com.pay.poss.accounting.dto.AccountingFeeDto;
import com.pay.poss.accounting.dto.AccountingFeeParamDto;
import com.pay.poss.accounting.service.AccountingFeeService;
import com.pay.poss.paychainmanager.enums.ResultTooMuchException;
import com.pay.util.DateUtil;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		AccountingFeeServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date				Author			Changes
 * 2012-3-2			DDR				Create
 */
public class AccountingFeeServiceImpl implements AccountingFeeService {

	private AccountingFeeDao accountingFeeDao;
	
	
	public void setAccountingFeeDao(AccountingFeeDao accountingFeeDao) {
		this.accountingFeeDao = accountingFeeDao;
	}


	/* (non-Javadoc)
	 * @see com.pay.poss.accounting.service.AccountingFeeService#search(com.pay.poss.base.dao.model.Page, com.pay.poss.accounting.dto.AccountingFeeParamDto)
	 */
	@Override
	public Page<AccountingFeeDto> search(Page<AccountingFeeDto> paramPage,
			AccountingFeeParamDto dto) {
		return accountingFeeDao.findPage(paramPage, dto);
	}


	/**
	 * 工作表设置对齐样式
	 * @param book
	 * @param direction 0是居中，1左 2右
	 * @param direction
	 * @author DDR
	 */
	private static HSSFCellStyle cellAlign(HSSFWorkbook book,int direction){
		//设置单元格默认样式
	    HSSFCellStyle styleCenter = book.createCellStyle();
//        styleCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//        styleCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//        styleCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
//        styleCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		styleCenter.setAlignment(direction == 1 ? HSSFCellStyle.ALIGN_LEFT
				: (direction == 2 ? HSSFCellStyle.ALIGN_RIGHT
						: HSSFCellStyle.ALIGN_CENTER));
        return styleCenter;
	}
	
	@Override
	public HSSFWorkbook exportFeeListExcel(AccountingFeeParamDto dto,
			String templatePath) throws ResultTooMuchException,  IOException {
		
		int maxDownloads = 5000;
		int totalCount=accountingFeeDao.countSearch(dto);
		if(totalCount> maxDownloads ){//数据量太大，请重新选择条件下载
			throw new ResultTooMuchException("记录数量过大，当前记录"+totalCount+">"+"最大限制记录"+maxDownloads);
		}

		
		Page<AccountingFeeDto> pageParam = new Page<AccountingFeeDto>();
		pageParam.setPageNo(1);
		pageParam.setPageSize(5000);
		Page<AccountingFeeDto> page = accountingFeeDao.findPage(pageParam, dto);
	    HSSFWorkbook book ;
	  //创建workbook
	    book = new HSSFWorkbook(new FileInputStream(templatePath));
	    //创建黑体字体 
	    HSSFFont fontHei = book.createFont();
	    fontHei.setFontHeightInPoints( (short)11);
	    fontHei.setFontName("黑体");
	    HSSFCellStyle styleHei = cellAlign(book,0);
        styleHei.setFont(fontHei);
        
        HSSFFont fontHeiRed = book.createFont();
        fontHeiRed.setFontHeightInPoints( (short)11);
        fontHeiRed.setFontName("黑体");
	    fontHeiRed.setColor(HSSFFont.COLOR_RED);
	    HSSFCellStyle stylefontHeiRedColor = cellAlign(book,0);
	    stylefontHeiRedColor.setFont(fontHeiRed);
        
        
   	    // 取得第一张表
        HSSFSheet sheet = book.getSheetAt(0);
        int ROWNUM_DATA = 1;    // 数据的起始行位置 
     
//        HSSFRow row = sheet.createRow(++ROWNUM_DATA);
        String fmt = "yyyy-MM-dd HH:mm:ss";
        DecimalFormat dfd = new DecimalFormat("0.00");
        
        
        for(AccountingFeeDto feeDto: page.getResult()){
        	int p = 0;
         HSSFRow row = sheet.createRow(++ROWNUM_DATA);
       	 	HSSFCell cell = row.createCell(p++);
            cell.setCellValue(DateUtil.dateToStr(feeDto.getCreateDate(), fmt));
            
        	cell = row.createCell(p++);
            cell.setCellValue(feeDto.getUserTypeMsg());
            
            cell = row.createCell(p++);
            cell.setCellValue(feeDto.getMemberCode());
            
            cell = row.createCell(p++);
            cell.setCellValue(feeDto.getAcctName());
            
            cell = row.createCell(p++);
            cell.setCellValue(feeDto.getDealTypeMsg());
            
            cell = row.createCell(p++);
            cell.setCellValue(dfd.format(feeDto.getOrderAmount()));
            
            cell = row.createCell(p++);
            cell.setCellValue(dfd.format(feeDto.getFee()));
             
        }
        
        System.out.println("--->"+ROWNUM_DATA);
        return book;
	}

}
