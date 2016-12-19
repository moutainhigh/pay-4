package com.pay.fundout.bankfile.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.pay.fundout.bankfile.common.util.CreatorFileDirUtil;
import com.pay.fundout.bankfile.generator.helper.FileGenerateResult;
import com.pay.fundout.bankfile.generator.model.FileDetailMode;
import com.pay.fundout.bankfile.generator.model.FileSummaryModel;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.util.StringUtil;
abstract public  class AbstractBankFileGenerator implements BankFileGenerator {
	protected static Log logger = LogFactory.getLog(AbstractBankFileGenerator.class);
	protected List<String> supportBusiCode = new ArrayList<String>()
	{
		private static final long serialVersionUID = -5511995223964185922L;
		{
			add("0");add("3");add("4");
		}
	}; 
	
	protected DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	protected DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
	protected DateFormat df3 = new SimpleDateFormat("yyyyMMdd");
	
	protected static final String TXT_FILE_SUBFIX = ".txt";
	protected static final String XLS_FILE_SUBFIX = ".xls";

	protected static final String BPT_FILE_SUBFIX = ".bpt";
	
	/**
	 * UTF-8编码
	 */
	public static final String ENCODING_UTF_8 = "UTF-8";
	
	/**
	 * ANSI编码
	 */
	public static final String ENCODING_ANSI = "GBK";
	
	@Override
	public List<String> supportBusiTypeCode() {
		return supportBusiCode;
	}

	@Override
	public BatchFileInfo generateBankDetailFile(List<FileDetailMode> bankDetailInfoList,
			Map<String, String> fileInfo) throws PossException {
		
		if (null == bankDetailInfoList || bankDetailInfoList.isEmpty() || null == fileInfo) {
			return null;
		}
		FileGenerateResult fileGenerateResult = new FileGenerateResult(fileInfo, bankDetailInfoList);
		
		buildBankDetailList(fileGenerateResult);
		
		return fileGenerateResult.getBatchFileInfo();
	}
	/**
	 * 构建生成文件对象
	 * @param fileGenerateResult
	 */
	abstract protected void buildBankDetailList(FileGenerateResult fileGenerateResult);
	
	
	/**
	 * 通过vm模板Velocity引擎生成文件
	 */
	protected void writeVelocityFile(FileGenerateResult fileGenerateResult){
		VelocityEngine engine = new VelocityEngine(); 
		Properties properties = new Properties();
		logger.info("fileGenerateResult.getTemplatePath()-"+fileGenerateResult.getTemplatePath());
		properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,fileGenerateResult.getTemplatePath()); //此处的
		engine.init(properties);
		Template t = engine.getTemplate(fileGenerateResult.getTemplateName(),"GBK");
		VelocityContext ctx = new VelocityContext();
		Map<String,Object> ctxMap = fileGenerateResult.getCtxMap();
		if(ctxMap.get("orderList") != null){
			ctx.put("orderList", ctxMap.get("orderList"));
		}
		if(ctxMap.get("summer") != null ){
			ctx.put("summer", ctxMap.get("summer"));
		}
	    BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileGenerateResult.getGenFullPath())), fileGenerateResult.getGenFileEncoding()));
			t.merge(ctx, bw);
			bw.flush();
		} catch (Exception e) {
			LogUtil.error(this.getClass(),"生成银行明细文件出现异常", OPSTATUS.EXCEPTION, fileGenerateResult.getBatchNum(),
					"", e.getMessage(), ExceptionCodeEnum.CONTEXT_EXCEPTION.getCode(), e.getMessage());
		}finally{
		    try {
		    	if(bw != null){
		    		bw.close();
		    	}
			} catch (IOException e) {
				logger.error("写入文件失败" + e.getMessage());
				bw = null;
			}
		}
	}
	/**
	 * 通过xls模板jxs引擎生成文件
	 */
	protected void writeExcelFile(FileGenerateResult fileGenerateResult){
		XLSTransformer transformer = new XLSTransformer();
		Map<String,Object> ctxMap = fileGenerateResult.getCtxMap();
		Workbook workbook = null;
		OutputStream outputStream = null;
		try {
			workbook = transformer.transformXLS(new FileInputStream(new File(fileGenerateResult.getTemplatePath()+File.separator+fileGenerateResult.getTemplateName())), ctxMap);
			File file = new File(fileGenerateResult.getGenFullPath());
		    outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception e) {
			LogUtil.error(this.getClass(),"生成银行明细文件出现异常", OPSTATUS.EXCEPTION, fileGenerateResult.getBatchNum(),
					"", e.getMessage(), ExceptionCodeEnum.CONTEXT_EXCEPTION.getCode(), e.getMessage());
		}finally{
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		
	}
	
	/**
	 * 构建批次文件信息
	 * @param fileGenerateResult
	 * @param summaryModel
	 */
	protected void buildBatchFileInfo(FileGenerateResult fileGenerateResult,FileSummaryModel summaryModel){
		BatchFileInfo batchFileInfo = new BatchFileInfo();

		batchFileInfo.setBatchNum(fileGenerateResult.getBatchNum());// 批次号
		batchFileInfo.setFilePath(fileGenerateResult.getGenFullPath().substring(fileGenerateResult.getGenBasePath().length()));// 文件路径
		batchFileInfo.setFileName(CreatorFileDirUtil
				.getFileNameWithoutTime(fileGenerateResult.getGenFullPath())); // 文件名
		batchFileInfo.setFileType(Long.valueOf(BATCH_FILE_TYPE_22)); // 文件类型
		batchFileInfo.setAllAmount(summaryModel.getTotalAmount().longValue());// 总金额
		batchFileInfo.setAllCount(summaryModel.getTotalCount());// 总笔数
		batchFileInfo.setBankCode(summaryModel.getBankCode());// 银行编码

		// 操作人 当前登录用户
		String userKy = fileGenerateResult.getUserKy();
		if (StringUtil.isEmpty(userKy)) {
			userKy = "systemAdmin";
		}
		batchFileInfo.setOperators(userKy);// 操作人标识
		batchFileInfo.setUpdateTime(new Date()); // 操作时间
		batchFileInfo.setBatchFileStatus(2l);// 文件已生成
		batchFileInfo.setGenerateTime(new Date());// 生成时间
		fileGenerateResult.setBatchFileInfo(batchFileInfo);
	}
	/**
	 * 获取文件全路径并生成相应的路径
	 * @param fileGenerateResult
	 */
	protected void genFoFullPath(FileGenerateResult fileGenerateResult){
		fileGenerateResult.setGenBasePath(new StringBuilder().append(fileGenerateResult.getGenBasePath()).append(File.separator).append("withdraw").toString());
		String fullPath = new StringBuilder().append(fileGenerateResult.getGenBasePath()).toString();
		Calendar c = Calendar.getInstance();
		fullPath = CreatorFileDirUtil.createFilePath(c, fullPath)
				+ fileGenerateResult.getBatchNum() + File.separator;
		File file = new File(fullPath);
		file.mkdirs();
		fullPath = fullPath + fileGenerateResult.getFileName() + fileGenerateResult.getFileSubfix();
		fileGenerateResult.setGenFullPath(fullPath);
	}
	
	/**
	 * 获取充退文件全路径并生成相应的路径
	 * @param fileGenerateResult
	 */
	protected void genRfFullPath(FileGenerateResult fileGenerateResult){
		fileGenerateResult.setGenBasePath(new StringBuilder().append(fileGenerateResult.getGenBasePath()).append("/").append("refund").toString());
		String fullPath = new StringBuilder().append(fileGenerateResult.getGenBasePath()).toString();
		Calendar c = Calendar.getInstance();
		fullPath = CreatorFileDirUtil.createFilePath(c, fullPath)
				+ fileGenerateResult.getBatchNum() + "/";
		File file = new File(fullPath);
		file.mkdirs();
		fullPath = fullPath + fileGenerateResult.getFileName() + fileGenerateResult.getFileSubfix();
		fileGenerateResult.setGenFullPath(fullPath);
	}
	
}
