package com.pay.base.service.paychain.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.app.common.helper.AppConf;
import com.pay.util.DESUtil;
import com.pay.base.common.enums.PayChainEnum;
import com.pay.base.common.enums.PayChainValidateMsgEnum;
import com.pay.base.dao.paychain.PayChainDAO;
import com.pay.base.dto.PayChainDetailDto;
import com.pay.base.dto.PayChainPayInfo;
import com.pay.base.dto.PayChainPaymentDto;
import com.pay.base.dto.PayChainPaymentParamDto;
import com.pay.base.dto.PayChainStatDto;
import com.pay.base.dto.PayChainStatParamDto;
import com.pay.base.dto.PayChainStatRecordDto;
import com.pay.base.exception.ResultToMachException;
import com.pay.base.model.PayChain;
import com.pay.base.service.paychain.PayChainPayInfoService;
import com.pay.base.service.paychain.PayChainService;



public class PayChainServiceImpl implements PayChainService{
    private static final Log logger = LogFactory.getLog(PayChainServiceImpl.class.getSimpleName());
    private static final String payChainKey=AppConf.get(AppConf.payChainKey)==null?"":AppConf.get(AppConf.payChainKey);
    private PayChainDAO payChainDao;
    private PayChainPayInfoService payChainPayInfoService;
    private static SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd");
   
    
    
    /**
	 * @param payChainPayInfoService the payChainPayInfoService to set
	 */
	public void setPayChainPayInfoService(
			PayChainPayInfoService payChainPayInfoService) {
		this.payChainPayInfoService = payChainPayInfoService;
	}

	@Override
    public Long createPayChain(PayChain payChain){
    	return payChainDao.create(payChain);
    }
	
    public void setPayChainDao(PayChainDAO payChainDao) {
		this.payChainDao = payChainDao;
	}
    
    
    
    public String generatePayChainNum(){
    	return sdf.format(new Date())+payChainDao.generateChainNum();
    }
    
    public Date getOverDate(int day){
		return payChainDao.getOverDate(day);
	}
    
	public String encryptChainNum(String chainNum){
    	String e1=DESUtil.encrypt(chainNum,payChainKey);
    	return DESUtil.encrypt(e1,payChainKey);
    }
    
    
    public String decryptChainNum(String encryChainNum){
    	String d1=DESUtil.decrypt(encryChainNum,payChainKey);
    	return DESUtil.decrypt(d1,payChainKey);
    }
    
    public String getNextPayChainNum(){
    	return sdf.format(new Date())+payChainDao.getMaxChainNum();
    }

	@Override
	public PayChain getPayChainByChainNum(String chainNum) {
		return payChainDao.getPayChin(chainNum);
	}

	@Override
	public PayChainValidateMsgEnum validate(String chainNum) {
		
		PayChain payChain = getPayChainByChainNum(chainNum);
		if(payChain == null){
			return PayChainValidateMsgEnum.NOT_EXISTS;
		}
		
		if(PayChainEnum.CLOSED.getValue() == payChain.getStatus().intValue()){
			return PayChainValidateMsgEnum.CLOSED;
		}
		
		if(payChain.getOverdueDate()!= null ){
			Date now = new Date();
			if(now.after(payChain.getOverdueDate())){
				return PayChainValidateMsgEnum.EXPIRED;
			}
		}
		
		return PayChainValidateMsgEnum.LEGAL;
	}
    

	@Override
	public PayChainStatDto queryPayChainStat(
			PayChainStatParamDto  paramDto) {
		//查询记录
		return payChainDao.queryPayChainRecords(paramDto);
	}

	@Override
	public int queryPayChainStatCount(PayChainStatParamDto paramDto) {
		
		return payChainDao.queryPayChainRecordsCount(paramDto); 
	}

	@Override
	public HSSFWorkbook exportPayChainStat(
			PayChainStatParamDto paramDto, String templatePath) throws ResultToMachException, FileNotFoundException, IOException{
		
		paramDto.setPageNo(1);
		int maxDownloads = 5000;
		paramDto.setPageSize(maxDownloads);
		int resultRows =  this.queryPayChainStatCount(paramDto);
		if(resultRows> maxDownloads ){//数据量太大，请重新选择条件下载
			throw new ResultToMachException("记录数量过大，当前记录"+resultRows+">"+"最大限制记录"+maxDownloads);
		}
		paramDto.setPageNo(1);
		paramDto.setPageSize(5000);
		PayChainStatDto payChainStatDto  = this.queryPayChainStat(paramDto);
		List<PayChainStatRecordDto> list = payChainStatDto.getPayChainStatRecordDtos();
	    HSSFWorkbook book ;
	  //创建workbook
	    book = new HSSFWorkbook(new FileInputStream(templatePath));
	    HSSFFont fontHei = book.createFont();
	    fontHei.setFontHeightInPoints( (short)11);
	    fontHei.setFontName("黑体");
   	    // 取得第一张表
        HSSFSheet sheet = book.getSheetAt(0);
        int ROWNUM_DATA = 2;    // 数据的起始行位置
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i=0 ;
        for(; i<list.size();i++){
       	 PayChainStatRecordDto record = (PayChainStatRecordDto)list.get(i);
            HSSFRow row = sheet.createRow(ROWNUM_DATA+i);
             int j = 0;	
             row.createCell(j++).setCellValue(record.getEffectiveMsg());
             row.createCell(j++).setCellValue(sdf.format(record.getCreateDate()));
             row.createCell(j++).setCellValue(sdf.format(record.getOverdueDate()));
             row.createCell(j++).setCellValue((record.getChainNumber()));
             row.createCell(j++).setCellValue((record.getPayChainName()));
             HSSFCell cell4 = row.createCell(j++);
             cell4.setCellStyle(cellAlign(book,0));
             cell4.setCellValue(record.getCount());
             HSSFCell cell5 = row.createCell(j++);
             cell5.setCellStyle(cellAlign(book,0));
             cell5.setCellValue(record.getSum());
             
             HSSFCell cell6 = row.createCell(j++);
             cell6.setCellStyle(cellAlign(book,0));
             cell6.setCellValue(record.getStatusMsg());
             
        };
        HSSFRow row = sheet.createRow(ROWNUM_DATA+i);
        HSSFCell cell3  = row.createCell(4);
        cell3.setCellStyle(cellAlign(book,0));
        cell3.setCellValue("总计");
        HSSFCell cell4 = row.createCell(5);
        cell4.setCellStyle(cellAlign(book,0));
        cell4.setCellValue(payChainStatDto.getRecordsPaySum());
        HSSFCell cell5 = row.createCell(6);
        cell5.setCellStyle(cellAlign(book,0));
        cell5.setCellValue(payChainStatDto.getRecordsAmountSum());;
        return book;
    
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
	public PayChainDetailDto queryPayChainDetail(PayChainPaymentParamDto paramDto) {
		
		//查询支付链信息
		PayChain payChain =  this.getPayChainByChainNum(paramDto.getPayChainNumber());
//		Long memberCode = payChain.getMemberCode();
		PayChainPayInfo payChainPayInfo =  payChainPayInfoService.get(payChain);
		
		//查询成功付款人信息
		PayChainDetailDto payChainDetailDto =  payChainDao.queryPayChainDetail(paramDto);
		payChainDetailDto.setPayChainPayInfo(payChainPayInfo);
		
		payChain = this.getPayChainByChainNum(paramDto.getPayChainNumber()); 
		payChainDetailDto.setPayChainUrl(payChain.getPayChainUrl());
		
		return payChainDetailDto;
		
	}

	/* (non-Javadoc)
	 * @see com.pay.base.service.paychain.PayChainService#exportPayChainDetail(com.pay.base.dto.PayChainPaymentParamDto, java.lang.String)
	 */
	@Override
	public HSSFWorkbook exportPayChainDetail(
			PayChainPaymentParamDto paramDto, String templatePath,String serverContextPath)
			throws ResultToMachException, Exception {
		paramDto.setPageNo(1);
		int maxDownloads = 5000;
		paramDto.setPageSize(maxDownloads);
		int resultRows =  payChainDao.queryPayChainPaymentCount(paramDto.getPayChainNumber());
		if(resultRows> maxDownloads ){//数据量太大，请重新选择条件下载
			throw new ResultToMachException("记录数量过大，当前记录"+resultRows+">"+"最大限制记录"+maxDownloads);
		}
		paramDto.setPageNo(1);
		paramDto.setPageSize(5000);
		PayChainDetailDto payChainDetailDto  = queryPayChainDetail(paramDto);
		List<PayChainPaymentDto> list = payChainDetailDto.getPayChainPaymentDtos();
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
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        
    
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
        cellLink.setCellValue(serverContextPath+payChainDetailDto.getPayChainUrl());
        
        String [] titles = "有效时长,链接生成时间,过期时间,收款链接编号,收款名称,收款描述,收款方名称,收款方地址,联系电话".split(",");
      //设置标题1
        row = sheet.createRow(++ROWNUM_DATA);
        for(int p=0;p<titles.length;p++){
        	 HSSFCell cell = row.createCell(p);
             cell.setCellStyle(styleHei);
             cell.setCellValue(titles[p]);
            
        } 
        titles = null;
      //设置内容 1
        PayChainPayInfo   paychainInfo =  payChainDetailDto.getPayChainPayInfo();
        String[] paychainInfoArray = {
        		paychainInfo.getEffectiveDesc(),
        		sdf.format(paychainInfo.getCreateDate()),
        		sdf.format(paychainInfo.getOverdueDate()),
        		paychainInfo.getPayChainCode(),
        		paychainInfo.getPayChainName(),
        		paychainInfo.getReceiptDesc(),
        		paychainInfo.getPayeeName(),
        		paychainInfo.getPayeeAddr().replaceAll("&nbsp;", " "),
        		paychainInfo.getPayeeTel()
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
        
        int i=0 ;
        for(; i<list.size();i++){
       	 PayChainPaymentDto record = (PayChainPaymentDto)list.get(i);
       	 	row = sheet.createRow(++ROWNUM_DATA);
             row.createCell(0).setCellValue(sdf.format(record.getPayDate()));
             row.createCell(1).setCellValue(record.getGatewayNo());
             row.createCell(2).setCellValue((record.getPaymentName()));
             HSSFCell cell3 = row.createCell(3);
             cell3.setCellValue((record.getPayAmount()));
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
        cell2.setCellValue("共"+payChainDetailDto.getRecordsPaySum()+"条记录");
        HSSFCell cell3 = row.createCell(3);
        cell3.setCellStyle(cellAlign(book,2));
        cell3.setCellValue("￥"+payChainDetailDto.getRecordsAmountSum());;
        return book;
	}

	@Override
	public boolean closePayChainRdTx(String payChainNumer) {
		return payChainDao.updatePayChainStatus(payChainNumer,2) == 1;//关闭是2
	}

	@Override
	public boolean updateEffectiveDate(String payNum, int effectiveType) {
		
		return payChainDao.updateEffDate(payNum, effectiveType) == 1;
	}
	
	
	
	
}
