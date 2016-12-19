 /** @Description 
 * @project 	poss-refund
 * @file 		UnionPayPareseReconcileImpl.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-15		sunsea.li		Create 
*/
package com.pay.poss.refund.common.parser.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.refund.common.parser.ParserReconcile;
import com.pay.poss.refund.model.RefundImportFile;
import com.pay.poss.refund.model.RefundImportRecord;
import com.pay.poss.refund.model.WebRefundUploadDTO;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;
/**
 * 
 * <p>银联充退解析器</p>
 * @author sunsea_li
 * @since 2010-9-15
 * @see
 */
public class UnionPayPareseReconcileImpl extends AbstractParserReconcile implements ParserReconcile{
	
	/**xml文件解析
	 * @param orginalFile
	 * @param refundImportFile
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public List<RefundImportRecord> parserFileXml(final CommonsMultipartFile orginalFile ,final RefundImportFile refundImportFile) throws DocumentException, IOException{
		SAXReader saxReader = new SAXReader();
		final List<RefundImportRecord> arrayList = new ArrayList<RefundImportRecord>();
		Document document = saxReader.read(orginalFile.getInputStream());
		Element root = document.getRootElement();
		Element rowdata = root.element("ROWDATA");
		RefundImportRecord refundImportRecord = null;
		for(Iterator<Element> rowElement = rowdata.elementIterator("ROW"); rowElement.hasNext(); ) {
			refundImportRecord = new RefundImportRecord();
			Element row = rowElement.next();
			//交易日期
			refundImportRecord.setBusiTime(DateUtil.parse(dataForamt,row.attributeValue("CpDateTime")));
			
			//银行交易金额
			refundImportRecord.setBankAmount(new BigDecimal(row.attributeValue("TransAmt")));
			
			refundImportRecord.setFileKy(refundImportFile.getFileKy());
			refundImportRecord.setBankCode(refundImportFile.getBankCode());
			arrayList.add(refundImportRecord);
		}
		
		return arrayList;
	}
	
	
	/**调试excel文件解析
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		File file = new File("D:\\充退结果文件\\充退银行结果文件 (2).xls");
		
		FileInputStream inputStream = new FileInputStream(file);
		int rowIndex = 1;
		HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
	    HSSFSheet mainSheet = workbook.getSheetAt(0);
	    HSSFRow row = mainSheet.getRow(rowIndex);// start from the second row.
	    List<RefundImportRecord> arrayList = new ArrayList<RefundImportRecord>();
	    RefundImportRecord refundImportRecord = null;
	    int allCount = mainSheet.getPhysicalNumberOfRows();
	    while (rowIndex<allCount-2) {
	    	if (row.getCell((short) 3) != null && !StringUtil.isEmpty((row.getCell((short) 3)).toString()) ) {
	    		refundImportRecord = new RefundImportRecord();
				//refundImportRecord.setBatchNum(new String(new Date().getTime()).longValue());
				if (row.getCell((short) 3) != null && !StringUtil.isEmpty((row.getCell((short) 3)).toString()) ) {//订单明细流水
					refundImportRecord.setOrderSeq(new Long( (row.getCell((short) 3)).toString() ));
				}
				if (row.getCell((short) 4) != null && !StringUtil.isEmpty((row.getCell((short) 4)).toString())) {//银行交易金额
					refundImportRecord.setBankAmount(new BigDecimal( (row.getCell((short) 4)).toString() ));
				}
				if (row.getCell((short) 5) != null && !StringUtil.isEmpty((row.getCell((short) 5)).toString())) {//收款人
					refundImportRecord.setBankAcctName( (row.getCell((short) 5)).toString() );
				}
				if (row.getCell((short) 6) != null && !StringUtil.isEmpty((row.getCell((short) 6)).toString()) ) {//银行交易流水
					refundImportRecord.setBankSeq( (row.getCell((short) 6)).toString() );
				}
				if (row.getCell((short) 7) != null && !StringUtil.isEmpty((row.getCell((short) 7)).toString())) {//银行备注
					refundImportRecord.setBankRemark( (row.getCell((short) 7)).toString() );
				}
				if (row.getCell((short) 8) != null && !StringUtil.isEmpty((row.getCell((short) 8)).toString())) {//银行状态
					refundImportRecord.setBankStatus(new Integer( (row.getCell((short) 8)).toString() ));
				}
				if (row.getCell((short) 9) != null && !StringUtil.isEmpty((row.getCell((short) 9)).toString()) ) {//银行交易时间
					//refundImportRecord.setBusiTime( DateUtil.parse(dataForamt, row.getCell((short)2).toString()) );
					refundImportRecord.setBusiTime( DateUtil.parse("yyyyMMddHHmmss", row.getCell((short)9).toString()) );
				}
		        arrayList.add(refundImportRecord);
	    	}
	    	
	        rowIndex++;
	        row = mainSheet.getRow(rowIndex);
	    }
	}
	
	/**excel文件解析
	 * @param orginalFile
	 * @param refundImportFile
	 * @return
	 * @throws IOException
	 */
	private List<RefundImportRecord> parserFileXls(CommonsMultipartFile orginalFile , RefundImportFile refundImportFile) throws IOException{
		int rowIndex = 1;
		HSSFWorkbook workbook = new HSSFWorkbook(orginalFile.getInputStream());
	    HSSFSheet mainSheet = workbook.getSheetAt(0);
	    HSSFRow row = mainSheet.getRow(rowIndex);// start from the second row.
	    List<RefundImportRecord> arrayList = new ArrayList<RefundImportRecord>();
	    RefundImportRecord refundImportRecord = null;
	    int allCount = mainSheet.getPhysicalNumberOfRows();
	    while (rowIndex<allCount-2) {
	    	if (row.getCell((short) 3) != null && !StringUtil.isEmpty((row.getCell((short) 3)).toString()) ) {
	    		refundImportRecord = new RefundImportRecord();
		    	refundImportRecord.setFileKy(refundImportFile.getFileKy());
				refundImportRecord.setBankCode(refundImportFile.getBankCode());
				refundImportRecord.setBatchNum(refundImportFile.getBatchNum());
				if (row.getCell((short) 3) != null && !StringUtil.isEmpty((row.getCell((short) 3)).toString()) ) {//订单明细流水
					refundImportRecord.setOrderSeq(new Long( (row.getCell((short) 3)).toString() ));
				}
				if (row.getCell((short) 4) != null && !StringUtil.isEmpty((row.getCell((short) 4)).toString())) {//银行交易金额
					refundImportRecord.setBankAmount(new BigDecimal( (row.getCell((short) 4)).toString() ).multiply(new BigDecimal(1000)));
				}
				if (row.getCell((short) 5) != null && !StringUtil.isEmpty((row.getCell((short) 5)).toString())) {//收款人
					refundImportRecord.setBankAcctName( (row.getCell((short) 5)).toString() );
				}
				if (row.getCell((short) 6) != null && !StringUtil.isEmpty((row.getCell((short) 6)).toString()) ) {//银行交易流水
					refundImportRecord.setBankSeq( (row.getCell((short) 6)).toString() );
				}
				if (row.getCell((short) 7) != null && !StringUtil.isEmpty((row.getCell((short) 7)).toString())) {//银行备注
					refundImportRecord.setBankRemark( (row.getCell((short) 7)).toString() );
				}
				if (row.getCell((short) 8) != null && !StringUtil.isEmpty((row.getCell((short) 8)).toString())) {//银行状态
					refundImportRecord.setBankStatus(new Integer( (row.getCell((short) 8)).toString() ));
				}
				if (row.getCell((short) 9) != null && !StringUtil.isEmpty((row.getCell((short) 9)).toString()) ) {//银行交易时间
					refundImportRecord.setBusiTime( DateUtil.parse("yyyyMMddHHmmss", row.getCell((short)9).toString()) );
				}
				refundImportRecord.setStatus(new Integer(1));
		        arrayList.add(refundImportRecord);
	    	}
	    	
	        rowIndex++;
	        row = mainSheet.getRow(rowIndex);
	    }
		return arrayList;
	}
	
	@Override
	public List<RefundImportRecord> parserFile(WebRefundUploadDTO webRefundUploadDTO)throws PossUntxException{
		List<RefundImportRecord> arrayList = new ArrayList<RefundImportRecord>();
		final CommonsMultipartFile orginalFile = webRefundUploadDTO.getOrginalFile();
		final RefundImportFile refundImportFile = webRefundUploadDTO.getRefundImportFile();
		try {
			arrayList = parserFileXls(orginalFile, refundImportFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PossUntxException("解析异常"+e.getMessage(),ExceptionCodeEnum.ILLEGAL_PARAMETER);
		} 
		return arrayList;
	}
}
