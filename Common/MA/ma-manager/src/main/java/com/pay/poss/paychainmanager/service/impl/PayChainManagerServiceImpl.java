/**
 * 
 */
package com.pay.poss.paychainmanager.service.impl;

import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.inf.dao.Page;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.poss.paychainmanager.dao.PayChainManagerDao;
import com.pay.poss.paychainmanager.dto.PayChainExternalLog;
import com.pay.poss.paychainmanager.dto.PayChainManagerDto;
import com.pay.poss.paychainmanager.dto.PayChainStatDto;
import com.pay.poss.paychainmanager.enums.EffectiveTypeEnum;
import com.pay.poss.paychainmanager.enums.ResultTooMuchException;
import com.pay.poss.paychainmanager.service.PayChainManagerService;
import com.pay.poss.paychainmanager.util.EffectiveDateUtil;

/**
 * @Description 支付链管理service
 * @project 	ma-manager
 * @file 		PayChainManagerServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2011-09-20		tianqing_wang				Create
 */
@SuppressWarnings("unchecked")
public class PayChainManagerServiceImpl  implements PayChainManagerService {
	private PayChainManagerDao payChainManagerDao;
	private ProvinceService provinceService;

	private CityService cityService;

	
	@Override
	public String getEnterMemberAdress(Integer regions, Integer city,
			String addr, Integer zip) {
		 StringBuffer address = new StringBuffer();

         if(null!=regions){
        		ProvinceDTO provinceDTO = provinceService.findById(regions);        		
        		address.append(provinceDTO.getProvincename()).append("&nbsp;&nbsp;");
         }
         if(null!=city){        	 
        	 CityDTO cityDTO = cityService.findByCityCode(city); 
             address.append(cityDTO.getCityname()).append("&nbsp;&nbsp;");
         }
         if( StringUtils.isNotEmpty(addr)){
             address.append(addr);
         }
         if(zip != null){
         	address.append("&nbsp;("+zip+")");
         }
		return address.toString();
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
	public HSSFWorkbook downExcelPayChainDetail(PayChainExternalLog externalLog,PayChainManagerDto paramDto,String templatePath)throws ResultTooMuchException, Exception{
		externalLog.setPageStartRow(1);
		int maxDownloads = 5000;
		externalLog.setPageEndRow(maxDownloads);
		int totalCount=payChainManagerDao.countPayChainExternalLog(externalLog);
		if(totalCount> maxDownloads ){//数据量太大，请重新选择条件下载
			throw new ResultTooMuchException("记录数量过大，当前记录"+totalCount+">"+"最大限制记录"+maxDownloads);
		}
		externalLog.setPageStartRow(0);
		externalLog.setPageEndRow(maxDownloads);
		List<PayChainExternalLog> list=payChainManagerDao.queryPayChainExternalLog(externalLog);
		
		PayChainManagerDto  chainDto=this.queryPayChainManagerDto(paramDto);
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
    
        //写title
        HSSFRow row = sheet.createRow(++ROWNUM_DATA);
        HSSFCell cellBgT1 = row.createCell(0);
        cellBgT1.setCellValue("支付链收款链接信息");
        cellBgT1.setCellStyle(stylefontHeiRedColor);
        
        row = sheet.createRow(++ROWNUM_DATA);
        HSSFCell cellLinkTile = row.createCell(0);
        cellLinkTile.setCellValue("支付链收款链接地址");
        cellLinkTile.setCellStyle(styleHei);
        
        HSSFCell cellLink = row.createCell(1);
        cellLink.setCellValue(chainDto.getPaychainUrl());
        
        String [] titles = "有效时长,链接生成时间,过期时间,收款链接编号,收款项目,收款方名称,收款方地址,联系电话".split(",");
      //设置标题1
        row = sheet.createRow(++ROWNUM_DATA);
        for(int p=0;p<titles.length;p++){
        	 HSSFCell cell = row.createCell(p);
             cell.setCellStyle(styleHei);
             cell.setCellValue(titles[p]);
            
        } 
        titles = null;
        String addr=this.getEnterMemberAdress(chainDto.getRegions(), chainDto.getCity(), chainDto.getAddress(), chainDto.getZip());
      //设置内容 1
        String[] paychainInfoArray = {
        		chainDto.getEffectiveDateName(),
        		chainDto.getStrCreateDate(),
        		chainDto.getStrOverdueDate(),
        		chainDto.getPayChainNumber(),
        		chainDto.getReceiptDescription(),
        		chainDto.getZhName(),
        		addr.replaceAll("&nbsp;", " "),
        		chainDto.getTel()
        }; 
        row = sheet.createRow(++ROWNUM_DATA);
        for(int p=0;p<paychainInfoArray.length;p++){
       	 HSSFCell cell = row.createCell(p);
            cell.setCellStyle(cellAlign(book,0));
            cell.setCellValue(paychainInfoArray[p]);
       } 
        paychainInfoArray = null;
        
     
        row = sheet.createRow(++ROWNUM_DATA);//先空一行
        
       // 支付链收款记录信息
        
        //写title 2
        row = sheet.createRow(++ROWNUM_DATA);
        HSSFCell cellBgT2 = row.createCell(0);
        cellBgT2.setCellValue("支付链收款记录信息");
        cellBgT2.setCellStyle(stylefontHeiRedColor);
        //设置记录title
        row = sheet.createRow(++ROWNUM_DATA);
        titles = "付款时间,网关支付流水号,付款方名称,支付金额,备注".split(",");
        for(int p=0;p<titles.length;p++){
       	 HSSFCell cell = row.createCell(p);
            cell.setCellStyle(styleHei);
            cell.setCellValue(titles[p]);
       } 
        //设置记录内容
        for(int p=0;p<list.size();p++ ){
        	PayChainExternalLog record =list.get(p);
       	 	row = sheet.createRow(++ROWNUM_DATA);
             row.createCell(0).setCellValue(record.getUpdateDate());
             row.createCell(1).setCellValue(record.getGatewayNo());
             row.createCell(2).setCellValue((record.getPayerName()));
             HSSFCell cell3 = row.createCell(3);
             cell3.setCellValue((record.getAmount()));
             cell3.setCellStyle(cellAlign(book,2));
             HSSFCell cell4 = row.createCell(4);
             cell4.setCellStyle(cellAlign(book,0));
             cell4.setCellValue(record.getRemark());
             
        };
        row = sheet.createRow(++ROWNUM_DATA);
        HSSFCell cell1  = row.createCell(0);
        cell1.setCellStyle(cellAlign(book,2));
        cell1.setCellValue("总计");
        HSSFCell cell2 = row.createCell(1);
        cell2.setCellStyle(cellAlign(book,0));
        cell2.setCellValue("共"+chainDto.getTotal()+"条记录");
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellStyle(cellAlign(book,2));
        cell3.setCellValue("￥"+chainDto.getStrTotalAmount());;
        return book;
	}
	
	@Override
	public PayChainManagerDto queryPayChainManagerDto(PayChainManagerDto paramDto) {
		paramDto.setPageStartRow(0);
		paramDto.setPageEndRow(2);
		List<PayChainManagerDto> dtoList= payChainManagerDao.queryPayChainByCondition(paramDto);	
		if(null!=dtoList && dtoList.size()>0){
			return dtoList.get(0);
		}
		return new PayChainManagerDto();
	}
	
	
	public List<PayChainManagerDto> queryPayChainByCondition(PayChainManagerDto paramDto,Page  page){
		Integer count = payChainManagerDao.countPayChainByCondition(paramDto);
		PayChainManagerDto dto = setPage(paramDto,page,count);
		if(null == dto) return null;
		return payChainManagerDao.queryPayChainByCondition(dto);	
	}

	@SuppressWarnings("unchecked")
	public Integer updatePayChainStatus(Map paramMap){
		return payChainManagerDao.updatePayChainStatus(paramMap);
	}
	
	public Integer modifyPayChainDate(Long id,EffectiveTypeEnum typeEnum){
		
		PayChainManagerDto dto = payChainManagerDao.findById(id);
		Date createDate = dto.getCreateDate();
		Date overdueDate = EffectiveDateUtil.getEndDate(typeEnum, createDate);
		
		Map<String ,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("id", id);
		paramMap.put("effectiveDate", typeEnum.getType());
		paramMap.put("overdueDate", overdueDate);
		
		return payChainManagerDao.updatePayChainDate(paramMap);
	}
	
	
	private PayChainManagerDto  setPage(PayChainManagerDto dto,Page  page,Integer totalCount){
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
        if (null == page || totalCount == 0) {
			  return null;
        }
        page.setTotalCount(totalCount);
        pageEndRow = page.getPageNo() * page.getPageSize();
        if ((page.getPageNo() - 1) == 0) {
        	pageStartRow = 0;
        }else {
        	pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
        }
        dto.setPageStartRow(pageStartRow);
        dto.setPageEndRow(pageEndRow);
		return dto;
	}
	
	public PayChainManagerDao getPayChainManagerDao() {
		return payChainManagerDao;
	}
	public void setPayChainManagerDao(PayChainManagerDao payChainManagerDao) {
		this.payChainManagerDao = payChainManagerDao;
	}

	@Override
	public PayChainStatDto queryPayChainSum(PayChainManagerDto paramDto) {		
		return this.payChainManagerDao.queryPayChainSum(paramDto);
	}

	
	private PayChainExternalLog  setPage(PayChainExternalLog dto,Page  page,Integer totalCount){
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
        if (null == page || totalCount == 0) {
			  return null;
        }
        page.setTotalCount(totalCount);
        pageEndRow = page.getPageNo() * page.getPageSize();
        if ((page.getPageNo() - 1) == 0) {
        	pageStartRow = 0;
        }else {
        	pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
        }
        dto.setPageStartRow(pageStartRow);
        dto.setPageEndRow(pageEndRow);
		return dto;
	}
	
	@Override
	public List<PayChainExternalLog> queryPayChainExternalLog(PayChainExternalLog externalLog,Page  page) {
		int totalCount=payChainManagerDao.countPayChainExternalLog(externalLog);
		setPage(externalLog, page, totalCount);
		return payChainManagerDao.queryPayChainExternalLog(externalLog);
	}

	
	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}





}
