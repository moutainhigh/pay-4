/**
 *  <p>File: AbstractFileParser.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.fundout.bankfile.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.bankfile.parser.helper.FileParseResult;
import com.pay.fundout.bankfile.parser.model.FileParserMode;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * <p></p>
 * @author zengli
 * @since 2011-6-1
 * @see 
 */
public abstract class AbstractBaseFileParser implements BaseFileParser {
	
	private static final String FILE_PATH_SEPARATR_CH1 = "/";
	protected static final int BANK_STATUS_1 = 101;
	protected static final int BANK_STATUS_2 = 102;
	protected static final int BANK_STATUS_3 = 103;
	private static final int FILE_STATUS_3 = 3; // 解析文件失败
	private static final int FILE_STATUS_2 = 2; // 解析文件成功
	protected static final int FILE_INFO_STATUS_DEFUALT = 1;	//有效

	/**
	 * 解析成功
	 */
	public static final Integer TXT_FILE_READ_SUCCESS = 0;
	/**
	 * 文件格式错误
	 */
	public static final Integer TXT_FILE_FORMAT_ERROR = 1;
	
	private Log logger = LogFactory.getLog(AbstractBaseFileParser.class);
	/* (non-Javadoc)
	 * @see com.pay.fundout.bankfile.spi.parser.BaseFileParser#parserFileInfo(java.util.Map)
	 */
	@Override
	public Map<String, Object> parserFileInfo(Map<String, String> fileInfo)
			throws PossException {
		String filePath = fileInfo.get("FILE_PATH");
		String fileName = fileInfo.get("FILE_NAME");
		String fileNo =   fileInfo.get("FILE_NO");
		String batchNum = fileInfo.get("BATCH_NUM");
		String bankCode = fileInfo.get("BANK_CODE");
		String gFileKy = fileInfo.get("G_FILE_KY");
		
		String fileFullPath = "".intern();
		if (filePath.endsWith(File.separator) || filePath.endsWith(FILE_PATH_SEPARATR_CH1)) {
			fileFullPath = filePath + fileName;
		} else {
			fileFullPath = filePath + File.separator + fileName;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FileParserMode parserFileInfo = new FileParserMode();
		
		parserFileInfo.setFileNo(new Long(fileNo));

		FileParseResult fileParseResult = new FileParseResult();
		fileParseResult.setBatchNum(batchNum);
		fileParseResult.setBankCode(bankCode);
		fileParseResult.setgFileKy(Long.valueOf(gFileKy));
		fileParseResult.setFileKy(Long.valueOf(fileNo));
		fileParseResult.setWorkName(fileName.substring(fileName.lastIndexOf("."),fileName.length()));
		
		File file = new File(fileFullPath);
		
		try {
			fileParseResult.setTargetFile(new FileInputStream(file));
			parserFile(fileParseResult);//调用groovy文件中的解析方法
			
			if(fileParseResult.getErrorMsg() != null && fileParseResult.getErrorMsg().size() > 0){
				logger.info("解析文件出现异常..." + fileInfo );
				resultMap.put("msgList", fileParseResult.getErrorMsg());
				parserFileInfo.setStatus(FILE_STATUS_3);
				resultMap.put("parserStatus",TXT_FILE_FORMAT_ERROR);
			}else{
				resultMap.put("parserStatus",TXT_FILE_READ_SUCCESS);
				parserFileInfo.setStatus(FILE_STATUS_2);
			}
			resultMap.put("parserFileInfo", parserFileInfo);
			resultMap.put("detailList", fileParseResult.getDetailList());
			if(logger.isInfoEnabled()){
				logger.info("resultMap"+resultMap);
			}
			return resultMap;
		} catch (FileNotFoundException e) {
			logger.info("文件不存在..." + fileInfo+"stack info : "+e);
			throw new PossException("解析异常,没有找到对应的文件",ExceptionCodeEnum.NOT_FOUND_FILE,e);
		} catch (Exception e) {
			logger.info("groovy解析异常" + fileInfo+"stack info : "+e);
			if(e.getMessage().indexOf("a valid OLE2 document")>=0)
			{//add by davis.guo at 2016-09-06
				throw new PossException("groovy解析异常,文件保存格式不正确，请另存后再上传。",ExceptionCodeEnum.NOT_FOUND_FILE,e);
			}
			else
			throw new PossException("groovy解析异常..",ExceptionCodeEnum.NOT_FOUND_FILE,e);
		}
	}
	/**
	 * 
	 * @param fileParseResult
	 */
	abstract protected void parserFile(FileParseResult fileParseResult) throws Exception;
	
	
	
}
