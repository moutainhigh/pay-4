package com.pay.poss.refund.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import com.pay.poss.refund.model.RefundBatchDTO;
import com.pay.util.DateUtil;

public class CreatorFileDirUtil {
	
	private static Log logger = LogFactory.getLog(CreatorFileDirUtil.class);
	private static final int RANDOM_INT = 1000;
	private static final String FILE_PATH_SEPARATOR_CH = "/";
	private static final String REFUND_DIR_NAME = "refund";
	
	/**
	 * 根据日期的year,month在basePath下创建file要存放的目录，便于管理及避免重名。 以'\'结尾
	 * 
	 * @param c
	 * @param basePath
	 * @return basePath\year\month\
	 */
	public static String createFilePath(Calendar c, String basePath) {
		StringBuffer newPath = new StringBuffer();
		newPath.append(basePath);
		if (!basePath.endsWith("/")) {
			newPath.append("/");
		}
		int t = c.get(Calendar.YEAR);
		newPath.append(t);
		String currDate = DateUtil.formatDateTime("yyyyMMdd", new Date());
		newPath.append("/").append(currDate).append("/");

		return newPath.toString();
	}
	
	/**
	 * 根据日期的day, h,m,s创建file的new filename,避免文件重名
	 * 
	 * @param c
	 * @param fileName
	 * @return filename with timestamp
	 */
	public static String createFileName(Calendar c, String fileName) {
		StringBuffer newFileName = new StringBuffer();
		int t, i;
		/*
		i = fileName.lastIndexOf(".");
		if (i != -1) {
			newFileName.append(fileName.substring(0, i));
		} else {
			newFileName.append(fileName);
		}*/
		newFileName.append(fileName);
		/*t = c.get(Calendar.DAY_OF_MONTH);
		newFileName.append("_").append(t);
		t = c.get(Calendar.HOUR_OF_DAY);
		newFileName.append(t);
		t = c.get(Calendar.MINUTE);
		newFileName.append(t);
		t = c.get(Calendar.SECOND);
		newFileName.append(t);*/
		t = c.get(Calendar.ZONE_OFFSET);
		newFileName.append("_").append(t);
		Random random = new Random();
		newFileName.append(random.nextInt(RANDOM_INT));
		
		/*if (i != -1) {
			newFileName.append(fileName.substring(i));
		}*/
		return newFileName.toString();
	}
	
	/**
	 * 根据filename获得不含时间的文件名
	 * 
	 * @param fileName
	 *            上传时添加了timestamp的文件名
	 * @return filename without timestamp
	 */
	public static String getFileNameWithoutTime(String fileName) {
		StringBuffer newFileName = new StringBuffer();
		int i_, iDot; // index of '_' and '.'
//		i_ = fileName.lastIndexOf("/");
		i_ = fileName.lastIndexOf("/");
		if(i_ != -1) {
			fileName = fileName.substring(i_+1);
		}
		/*i_ = fileName.lastIndexOf("_");
		iDot = fileName.lastIndexOf(".");
		if(i_ != -1)
			newFileName.append(fileName.substring(0, i_));
		else
			newFileName.append(fileName.substring(0, iDot));
		if (iDot != -1) {
			newFileName.append(fileName.substring(iDot));
		}
		return newFileName.toString();
		*/
		return fileName;
	}
	
	/**
	 * 根据HSSFWorkbook及basePath，将HSSFWorkbook写入到basePath下的相应目录
	 * 
	 * @param HSSFWorkbook
	 *            workbook 生成的文件
	 * @param basePath
	 *            context-poss.properties中配置的路径
	 * @return 包含文件名的newPath，如：/2008/10/hello_81822.txt,start with '/'.
	 */
	public static String writeFile(HSSFWorkbook workbook, Map<String, String> fileInfo) {
		
		String basePath = fileInfo.get("BATCH_FILE_PATH");
		String fileFormat = fileInfo.get("BATCH_FILENAME_SUBFIX");
		String businessType = fileInfo.get("BATCH_TASK_TYPE");
		String fileType = "1";
		String batchNum = fileInfo.get("BATCH_NUM");
		
		if (null == workbook) {
			logger.debug("workbook为空");
			return null;
		}
		if(StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		try {
			//String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath);
			File file = new File(basePath);
			file.mkdirs();
			String name = CreatorFileDirUtil.createFileName(c, "");
			newPath = basePath + businessType + fileType + name + batchNum + fileFormat;
			file = new File(newPath);
			OutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("写入文件失败" + e.getMessage());
		}
		newPath = newPath.substring(index);
		if(!newPath.startsWith("/")) {
			newPath = "/"+newPath;
		}
		return newPath;
	}
	
	/**
	 * 
	 * @param list
	 * @param fileInfo
	 * @return
	 */
	public static String writeFile(List<RefundBatchDTO> list, Map<String, String> fileInfo) {
		
		String basePath = fileInfo.get("BATCH_FILE_PATH");
		String fileFormat = ".txt";
		String businessType = fileInfo.get("BATCH_TASK_TYPE");
		String fileType = "1";
		String batchNum = fileInfo.get("BATCH_NUM");
		
		if (null == list) {
			logger.debug("workbook为空");
			return null;
		}
		if(StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		try {
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath);
			File file = new File(basePath);
			file.mkdirs();
			String name = CreatorFileDirUtil.createFileName(c, "");
			newPath = basePath + businessType + fileType + name + batchNum + fileFormat;
			BufferedWriter bw = new BufferedWriter(new FileWriter(newPath));
			for (RefundBatchDTO refundBatchDTO : list) {
				StringBuffer sb = new StringBuffer();
				String applyRemark = refundBatchDTO.getApplyRemark();
				BigDecimal applyAmount = refundBatchDTO.getApplyAmount();
				sb.append("|");
				sb.append(applyRemark);
				sb.append("|");
				sb.append(applyAmount);
				bw.write(sb.toString());
				bw.newLine();
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("写入文件失败" + e.getMessage());
		}
		newPath = newPath.substring(index);
		if(!newPath.startsWith("/")) {
			newPath = "/"+newPath;
		}
		return newPath;
	}
	
	/**
	 * 写入文本文件
	 * @param fileContent
	 * @param fileInfo
	 * @return
	 */
	public static String writeFile(String fileContent,Map<String, String> fileInfo){
		String basePath = fileInfo.get("BATCH_FILE_PATH") + FILE_PATH_SEPARATOR_CH + REFUND_DIR_NAME;
		String fileFormat = fileInfo.get("BATCH_FILENAME_SUBFIX");
		String fileName = fileInfo.get("FILE_NAME");
		String batchNum = fileInfo.get("BATCH_NUM");
		
		if (StringUtils.isEmpty(fileContent)) {
			logger.debug("文件内容为空");
			return null;
		}
		if(StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		OutputStream outputStream = null;
		try {
			//String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath) + batchNum + FILE_PATH_SEPARATOR_CH;
			File file = new File(basePath);
			file.mkdirs();
			newPath = basePath + fileName + fileFormat;
			file = new File(newPath);
			outputStream = new FileOutputStream(file);
			outputStream.write(fileContent.getBytes());
		} catch (Exception e) {
			logger.error("写入文件失败" + e.getMessage());
		}finally{
			if(outputStream != null){
				try{
					outputStream.close();
				}catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		newPath = newPath.substring(index);
		if(!newPath.startsWith("/")) {
			newPath = "/"+newPath;
		}
		return newPath;
	}
	
	/**
	 * 实现jxlsWorkBook写入文件
	 * @param workbook
	 * @param fileInfo
	 * @return
	 */
	public static String writeWorkBookFile(Workbook workbook, Map<String, String> fileInfo) {
		
		String basePath = fileInfo.get("BATCH_FILE_PATH") + FILE_PATH_SEPARATOR_CH + REFUND_DIR_NAME;
		String fileFormat = fileInfo.get("BATCH_FILENAME_SUBFIX");
		String businessType = fileInfo.get("BATCH_TASK_TYPE");
		String fileType = "1";
		String batchNum = fileInfo.get("BATCH_NUM");
		
		if (null == workbook) {
			logger.debug("workbook为空");
			return null;
		}
		if(StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		OutputStream outputStream = null;
		try {
			//String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath) + batchNum + FILE_PATH_SEPARATOR_CH;
			File file = new File(basePath);
			file.mkdirs();
			String name = CreatorFileDirUtil.createFileName(c, "");
			newPath = basePath + businessType + fileType + name + batchNum + fileFormat;
			file = new File(newPath);
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception e) {
			logger.error("写入文件失败" + e.getMessage());
		}finally{
			if(outputStream != null){
				try{
					outputStream.close();
				}catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		newPath = newPath.substring(index);
		if(!newPath.startsWith("/")) {
			newPath = "/"+newPath;
		}
		return newPath;
	}
	
}
