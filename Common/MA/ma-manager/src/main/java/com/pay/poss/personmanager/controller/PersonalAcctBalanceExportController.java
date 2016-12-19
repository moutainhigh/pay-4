/**
 * 
 */
package com.pay.poss.personmanager.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.personmanager.dto.PersonalAcctBalanceDto;
import com.pay.poss.personmanager.dto.PersonalAcctTradeTotalDto;
import com.pay.poss.personmanager.formbean.PersonSearchFormBean;
import com.pay.poss.personmanager.service.PersonMemberService;
/**
 * @Description 账户余额明细导出
 * @project 	poss-membermanager
 * @file 		PersonalAcctBalanceExportControllerr.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-2		tianqing_wang			Create
 * 2011-06-02		戴德荣
 */
public class PersonalAcctBalanceExportController extends MultiActionController {
	
	private Log log = LogFactory.getLog(PersonalAcctBalanceExportController.class);
	private PersonMemberService personMemberService;

	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return null;
    }

	@SuppressWarnings("unchecked")
	public ModelAndView doExport(HttpServletRequest request,HttpServletResponse resp,
			PersonSearchFormBean personSearchFormBean) throws Exception {
		
    	Map<String,Object> paraMap = new HashMap<String,Object>();
    	
    	String dealType=personSearchFormBean.getDealType();
    	
    	paraMap.put("dealType", dealType);
    	
    	paraMap.put("acctCode", personSearchFormBean.getAcctCode());
    	
		paraMap.put("startDate", personSearchFormBean.getStartDate());
		paraMap.put("endDate", personSearchFormBean.getEndDate());
		paraMap.put("loginName", personSearchFormBean.getLoginName());
		paraMap.put("type", personSearchFormBean.getType());
		
		paraMap.put("balanceStrat", personSearchFormBean.getBalanceStrat());
		paraMap.put("balanceEnd", personSearchFormBean.getBalanceEnd());
		
    	Page<PersonalAcctBalanceDto> page = PageUtils.getPage(request);
    	page.setPageNo(1);
    	page.setPageSize(6000);
    	List<PersonalAcctBalanceDto> list  = personMemberService.queryPersonalAcctBalanceList(paraMap,page);
    	List<PersonalAcctTradeTotalDto> total = personMemberService.queryPeraonalAcctTradeTotal(paraMap);
    	
    	if(list != null){
    		for (PersonalAcctBalanceDto dto : list){
        		Map balanceMap = new HashMap(2);
        		balanceMap.put("dealid", dto.getOrderNumber());
        		balanceMap.put("acctCode", dto.getAcctCode());
        		if(dto.getDealType().equals(String.valueOf(PayForEnum.FEE_AMOUNT.getCode()))){
        			balanceMap.put("status", 1);
				}else{
					balanceMap.put("status", 0);
				}
        		balanceMap.put("dealCode", dto.getDealCode());
        		String strBalance = personMemberService.queryBalance(balanceMap);
        		dto.setStrBalance(strBalance);
        		dto.setDealType(PayForEnum.get(Integer.parseInt(dto.getDealType())).getMessage());
        	}
    	}
    	
    	 Calendar c = Calendar.getInstance();
    	 SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-ddHHmmss");
         String date = f.format(c.getTime());
    	 String fileName = "余额明细"+date+".xls";// 导出的文件名
         String fullPath = request.getSession().getServletContext().getRealPath("/WEB-INF/jsp/personmanager/balance.xls");
         HSSFWorkbook book ;
         OutputStream os = null ;
         //创建workbook
         try {
          //创建workbook
        	 book = new HSSFWorkbook(new FileInputStream(fullPath));
             // 取得第一张表
             HSSFSheet sheet = book.getSheetAt(0);
             int ROWNUM_DATA = 2;    // 数据的起始行位置

             // 数据样式 style
             HSSFCellStyle style = book.createCellStyle();
             // 设置这些样式
             style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
             style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
             style.setBorderRight(HSSFCellStyle.BORDER_THIN);
             style.setBorderTop(HSSFCellStyle.BORDER_THIN);
             style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
             HSSFFont font = book.createFont();
             font.setFontHeightInPoints((short) 11);
             font.setFontName("宋体");
             style.setFont(font);
             
             // 数据样式 居中
             HSSFCellStyle styleCenter = book.createCellStyle();
             styleCenter.setBorderBottom(HSSFCellStyle.BORDER_THIN);
             styleCenter.setBorderLeft(HSSFCellStyle.BORDER_THIN);
             styleCenter.setBorderRight(HSSFCellStyle.BORDER_THIN);
             styleCenter.setBorderTop(HSSFCellStyle.BORDER_THIN);
             styleCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
             styleCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER);
             styleCenter.setFont(font);
             
             // 保存两位小数的数字
             HSSFCellStyle styleNum2 = book.createCellStyle();
             styleNum2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
             styleNum2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
             styleNum2.setBorderRight(HSSFCellStyle.BORDER_THIN);
             styleNum2.setBorderTop(HSSFCellStyle.BORDER_THIN);
             styleNum2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
             styleNum2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
             HSSFFont fontNum2 = book.createFont();
             fontNum2.setFontHeightInPoints( (short)11);
             fontNum2.setFontName("宋体");
             styleNum2.setFont(fontNum2);
             HSSFDataFormat df = book.createDataFormat();
             styleNum2.setDataFormat(df.getFormat("###,###,###,###,##0.00"));


             ////////////////////////////开始创建表///////////////////////////
             // 写入数据
             if(list!=null ){
                 for(int i=0; i<list.size();i++){
                	 PersonalAcctBalanceDto domain = (PersonalAcctBalanceDto)list.get(i);
                     HSSFRow row = sheet.createRow(ROWNUM_DATA+i);
                     //交易时间
                     HSSFCell rowCell_0= row.createCell((0));
                    // rowCell_0.setEncoding(HSSFCell.ENCODING_UTF_16);
                     rowCell_0.setCellStyle(styleCenter);
                     rowCell_0.setCellValue(domain.getPayDate());
                     //交易流水号
                     HSSFCell rowCell_1= row.createCell((1));
                    // rowCell_1.setEncoding(HSSFCell.ENCODING_UTF_16);
                     rowCell_1.setCellStyle(styleCenter);
                     rowCell_1.setCellValue(domain.getOrderNumber());
                     //交易类型
                     HSSFCell rowCell_2= row.createCell((2));
                     rowCell_2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                     //rowCell_2.setEncoding(HSSFCell.ENCODING_UTF_16);
                     rowCell_2.setCellStyle(styleCenter);
                     rowCell_2.setCellValue(domain.getDealType());

                     //交易对方账号
                     /*
                     rowCell_3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                     //rowCell_3.setEncoding(HSSFCell.ENCODING_UTF_16);
                     rowCell_3.setCellStyle(styleCenter);
                     rowCell_3.setCellValue(domain.getAnother());
                     */
                     //收入
                     HSSFCell rowCell_3= row.createCell((3));
                    // rowCell_4.setEncoding(HSSFCell.ENCODING_UTF_16);
                     rowCell_3.setCellStyle(styleNum2);
                     rowCell_3.setCellValue(domain.getNumberRevenue());
                     //支出
                     HSSFCell rowCell_4= row.createCell((4));
                     //rowCell_5.setEncoding(HSSFCell.ENCODING_UTF_16);
                     rowCell_4.setCellStyle(styleNum2);
                     rowCell_4.setCellValue(domain.getNumberPay());
                     
                     //
                     HSSFCell rowCell_5= row.createCell((5));
                     //rowCell_5.setEncoding(HSSFCell.ENCODING_UTF_16);
                     rowCell_5.setCellStyle(styleNum2);
                     rowCell_5.setCellValue(domain.getStrBalance());

                     if(i==list.size()-1){
                    	 if(total!=null && total.size()>0){
                        	 PersonalAcctTradeTotalDto totalDto =  total.get(0);
                        	 HSSFRow rowLast = sheet.createRow(ROWNUM_DATA+i+1);
                        	 HSSFCell last2 = rowLast.createCell((2));
                        	 last2.setCellStyle(styleNum2);
                        	 last2.setCellValue("总计");
                        	 HSSFCell last3 = rowLast.createCell((3));
                        	 last3.setCellStyle(styleNum2);
                        	 last3.setCellValue(totalDto.getTotalRevenue());
                        	 HSSFCell last4 = rowLast.createCell((4));
                        	 last4.setCellStyle(styleNum2);
                        	 last4.setCellValue(totalDto.getTotalPay());
                         }
                     }
                 }
             }
             // 设置格式
             // 输出
             resp.setContentType("application/msexcel;charset=GBK");
             resp.addHeader("Content-Disposition", (new StringBuilder("attachment;   filename=\""))
                     .append(new String(fileName.getBytes("GB2312"), "ISO-8859-1")).append("\"").toString());
             os = resp.getOutputStream();
             book.write(os);
             os.flush();
         
         } catch (FileNotFoundException e) {
             e.printStackTrace();
             log.error("写excel报错，文件不存在", e);
         } catch (IOException e) {
             e.printStackTrace();
             log.error("写excel报错IO", e);
         }finally{
        	    os.close();
         }
    	return null;
    
	}

	public void setPersonMemberService(PersonMemberService personMemberService) {
		this.personMemberService = personMemberService;
	}
}
