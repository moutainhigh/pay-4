package com.pay.fundout.bankfile.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.fundout.bankfile.generator.model.FileSummaryModel;
import com.pay.poss.base.common.properties.MyOSSConfiguration;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

public class CreatorFileDirUtil {

	private static Log logger = LogFactory.getLog(CreatorFileDirUtil.class);
	private static final int RANDOM_INT = 1000;
	private static final String FILE_PATH_SEPARATOR_CH = "/";
	private static final String WITHDRAW_DIR_NAME = "withdraw";
	private static final String REFUND_DIR_NAME = "refund";
	private static final String SEPARATOR_CH = "_";
	private static final int BATCH_STATUS_FILE_GENERATORED = 2; // 批次文件已生成

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
		int t;
		newFileName.append(fileName);
		t = c.get(Calendar.ZONE_OFFSET);
		newFileName.append("_").append(t);
		Random random = new Random();
		newFileName.append(random.nextInt(RANDOM_INT));

		/*
		 * if (i != -1) { newFileName.append(fileName.substring(i)); }
		 */
		if (logger.isDebugEnabled()) {
			logger.debug(newFileName.toString());
		}
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
		int i_;
		i_ = fileName.lastIndexOf("/");
		if (i_ != -1) {
			fileName = fileName.substring(i_ + 1);
		}
		return fileName;
	}
	/**
	 * 根据filename获得不含时间的文件名,避免系统不同导致不一致
	 * 
	 * @param fileName
	 *            上传时添加了timestamp的文件名
	 * @return filename without timestamp
	 */
	public static String getFileNameWithoutTime2(String fileName) {
		int i_;
		i_ = fileName.lastIndexOf("\\");
		if (i_ != -1) {
			fileName = fileName.substring(i_ + 1);
		}
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
	public static String writeFile(HSSFWorkbook workbook,
			Map<String, String> fileInfo) {

		String basePath = fileInfo.get("BATCH_FILE_PATH");
		String fileFormat = fileInfo.get("BATCH_FILENAME_SUBFIX");
		String businessType = fileInfo.get("BATCH_TASK_TYPE");
		String fileType = "1";
		String batchNum = fileInfo.get("BATCH_NUM");

		if (null == workbook) {
			logger.debug("workbook为空");
			return null;
		}
		if (StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		OutputStream outputStream = null;
		try {
			// String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath);
			File file = new File(basePath);
			file.mkdirs();
			String name = CreatorFileDirUtil.createFileName(c, "");
			newPath = basePath + businessType + fileType + name + batchNum
					+ fileFormat;
			file = new File(newPath);
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception e) {
			logger.error("写入文件失败" + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		newPath = newPath.substring(index);
		if (!newPath.startsWith("/")) {
			newPath = "/" + newPath;
		}
		return newPath;
	}

	/**
	 * 实现jxlsWorkBook写入文件
	 * 
	 * @param workbook
	 * @param fileInfo
	 * @return
	 */
	public static String writeWorkBookFile(Workbook workbook,
			Map<String, String> fileInfo,String fileSubfix) {

		String basePath = fileInfo.get("BATCH_FILE_PATH")
				+ FILE_PATH_SEPARATOR_CH + WITHDRAW_DIR_NAME;
		String fileFormat = fileSubfix;
		
		String businessType = fileInfo.get("BATCH_TASK_TYPE");
		String batchNum = fileInfo.get("BATCH_NUM");
		String bankCode = fileInfo.get("BANK_CODE");
		String filetype = fileInfo.get("filetype");
		String storedOnAliyun=fileInfo.get("STORED_ON_ALIYUN");
		if (null == workbook) {
			logger.debug("workbook为空");
			return null;
		}
		if (StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		OutputStream outputStream = null;
		File file=null;
		try {
			// String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath)
					+ batchNum + FILE_PATH_SEPARATOR_CH;
			file= new File(basePath);
			file.mkdirs();
			String name = CreatorFileDirUtil.createFileName(c, "");
			StringBuffer tempSb = new StringBuffer();
			tempSb.append(basePath);
			tempSb.append(businessType);
			tempSb.append(SEPARATOR_CH).append(name);
			tempSb.append(SEPARATOR_CH).append(batchNum);
			tempSb.append(SEPARATOR_CH).append(bankCode);
			tempSb.append(SEPARATOR_CH).append(filetype);
			tempSb.append(fileFormat);
			newPath = tempSb.toString();
			file = new File(newPath);
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception e) {
			logger.error("写入文件失败" + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		newPath = newPath.substring(index);
		if("1".equals(storedOnAliyun)){
			putFileOnAliyun(newPath,file);
		}
		if (!newPath.startsWith("/")) {
			newPath = "/" + newPath;
		}
		return newPath;
	}
	public static void putFileOnAliyun(String dbRelativePath,File dest){
		//added by yanshichuan stored file on aliyun
		String ossKey=MyOSSConfiguration.getStrProperties("mpsposs.oss.key");
		String ossRootDir="mpsposs";//MyOSSConfiguration.getStrProperties("mpsposs.oss.rootdir");
		String ossSubDir="withdraw";//MyOSSConfiguration.getStrProperties("mpsposs.oss.withdrawSubdir");
		MyOSS oss = new MyOSS(ossKey);
		JSONObject rawToken;
		try {
			if(StringUtils.isBlank(ossSubDir)){
				ossSubDir="withdraw";
			}
			rawToken = oss.init(ossSubDir);
			OSSClient client = oss.getOSSClient();
			if(dbRelativePath.startsWith("/")){
				dbRelativePath=dbRelativePath.substring(1);
			}
			client.putObject(rawToken.getString("bucket"), ossRootDir+"/"+ossSubDir+"/"+dbRelativePath, dest);
		} catch (UnsupportedOperationException e) {
			logger.error("put file on aliyun oss error:"+e.getMessage());
		} catch (MyOSSException e) {
			logger.error("put file on aliyun oss error:"+e.getMessage());
		} catch (IOException e) {
			logger.error("put file on aliyun oss error:"+e.getMessage());
		}
		if(dest.exists()){
			dest.delete();
		}
	}
	/**
	 * 写入文本文件
	 * 
	 * @param fileContent
	 * @param fileInfo
	 * @return
	 */
	public static String writeFile(String fileContent,
			Map<String, String> fileInfo,String encoding,String fileSubfix) {
		String basePath = fileInfo.get("BATCH_FILE_PATH")
				+ FILE_PATH_SEPARATOR_CH + WITHDRAW_DIR_NAME;
		
		String fileFormat = fileSubfix;
		String fileName = fileInfo.get("FILE_NAME");
		String batchNum = fileInfo.get("BATCH_NUM");

		if (StringUtils.isEmpty(fileContent)) {
			logger.debug("文件内容为空");
			return null;
		}
		if (StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		OutputStream outputStream = null;
		try {
			// String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath)
					+ batchNum + FILE_PATH_SEPARATOR_CH;
			File file = new File(basePath);
			file.mkdirs();
			newPath = basePath + fileName + fileFormat;
			file = new File(newPath);
			outputStream = new FileOutputStream(file);
			//TODO 暂时修改为UTF-8编码以后根据银行要求进行修改
			outputStream.write(fileContent.getBytes(encoding));
		} catch (Exception e) {
			logger.error("写入文件失败" + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		newPath = newPath.substring(index);
		if (!newPath.startsWith("/")) {
			newPath = "/" + newPath;
		}
		return newPath;
	}

	public static BatchFileInfo bulidBatchFileInfo(
			FileSummaryModel summaryModel, String filePath,
			Map<String, String> fileInfo) {
		BatchFileInfo batchFileInfo = new BatchFileInfo();

		batchFileInfo.setBatchNum(fileInfo.get("BATCH_NUM"));// 批次号
		batchFileInfo.setFilePath(filePath);// 文件路径
		batchFileInfo.setFileName(CreatorFileDirUtil
				.getFileNameWithoutTime(filePath)); // 文件名
		String filetype = fileInfo.get("filetype");
		if (StringUtils.isNotEmpty(filetype)) {
			batchFileInfo.setFileType(new Long(filetype)); // 文件类型
		} else {
			batchFileInfo.setFileType(new Long(1)); // 文件类型
		}

		batchFileInfo.setAllAmount(summaryModel.getTotalAmount().longValue());// 总金额
		batchFileInfo.setAllCount(summaryModel.getTotalCount());// 总笔数
		batchFileInfo.setBankCode(summaryModel.getBankCode());// 银行编码

		// 操作人 当前登录用户
		String userKy = fileInfo.get("USER_KY");
		if (StringUtil.isEmpty(userKy)) {
			userKy = "systemAdmin";
		}
		batchFileInfo.setOperators(userKy);// 操作人标识
		batchFileInfo.setUpdateTime(new Date()); // 操作时间
		batchFileInfo
				.setBatchFileStatus(new Long(BATCH_STATUS_FILE_GENERATORED));// 文件已生成
		batchFileInfo.setGenerateTime(new Date());// 生成时间
		return batchFileInfo;
	}

	/**
	 * 写入文本文件
	 * 
	 * @param fileContent
	 * @param fileInfo
	 * @return
	 */
	public static String writeFileForRefund(String fileContent,
			Map<String, String> fileInfo) {
		String basePath = fileInfo.get("BATCH_FILE_PATH")
				+ FILE_PATH_SEPARATOR_CH + REFUND_DIR_NAME;
		String fileFormat = fileInfo.get("BATCH_FILENAME_SUBFIX");
		String fileName = fileInfo.get("FILE_NAME");
		String batchNum = fileInfo.get("BATCH_NUM");
		String storedOnAliyun=fileInfo.get("STORED_ON_ALIYUN");
		if (StringUtils.isEmpty(fileContent)) {
			logger.debug("文件内容为空");
			return null;
		}
		if (StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		OutputStream outputStream = null;
		File file=null;
		try {
			// String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath)
					+ batchNum + FILE_PATH_SEPARATOR_CH;
			 file= new File(basePath);
			file.mkdirs();
			newPath = basePath + fileName + fileFormat;
			file = new File(newPath);
			outputStream = new FileOutputStream(file);
			outputStream.write(fileContent.getBytes());
		} catch (Exception e) {
			logger.error("写入文件失败" + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		newPath = newPath.substring(index);
		if("1".equals(storedOnAliyun)){
			putFileOnAliyun(newPath,file);
		}
		if (!newPath.startsWith("/")) {
			newPath = "/" + newPath;
		}
		return newPath;
	}

	/**
	 * 实现jxlsWorkBook写入文件
	 * 
	 * @param workbook
	 * @param fileInfo
	 * @return
	 */
	public static String writeWorkBookFileForRefund(Workbook workbook,
			Map<String, String> fileInfo) {

		String basePath = fileInfo.get("BATCH_FILE_PATH")
				+ FILE_PATH_SEPARATOR_CH + REFUND_DIR_NAME;
		String fileFormat = fileInfo.get("BATCH_FILENAME_SUBFIX");
		String businessType = fileInfo.get("BATCH_TASK_TYPE");
		String fileType = "1";
		String batchNum = fileInfo.get("BATCH_NUM");

		if (null == workbook) {
			logger.debug("workbook为空");
			return null;
		}
		if (StringUtils.isEmpty(basePath)) {
			logger.debug("没有设置基本路径! 请在context-poss.properties中设置上传路径!");
			return null;
		}
		String newPath = null;
		int index = basePath.length();
		OutputStream outputStream = null;
		try {
			// String name = uploadFile.getName();
			Calendar c = Calendar.getInstance();
			basePath = CreatorFileDirUtil.createFilePath(c, basePath)
					+ batchNum + FILE_PATH_SEPARATOR_CH;
			File file = new File(basePath);
			file.mkdirs();
			String name = CreatorFileDirUtil.createFileName(c, "");
			newPath = basePath + businessType + fileType + name + batchNum
					+ fileFormat;
			file = new File(newPath);
			outputStream = new FileOutputStream(file);
			workbook.write(outputStream);
		} catch (Exception e) {
			logger.error("写入文件失败" + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					outputStream = null;
					logger.error("写入文件失败" + e.getMessage());
				}
			}
		}
		newPath = newPath.substring(index);
		if (!newPath.startsWith("/")) {
			newPath = "/" + newPath;
		}
		return newPath;
	}

	/**
	 * 
	 * @param sheet
	 *            excel表sheet
	 * @throws IOException
	 */

	public static void creatNewExcel(Sheet sheet, Workbook wb, String sheetName)
			throws IOException {

		if(StringUtils.isEmpty(sheetName)){
			int size = wb.getNumberOfSheets();
			sheetName = "sheet" + size;
		}
		// 创建新的excel
		Sheet sheetCreat = wb.createSheet(sheetName);

		int firstRow = sheet.getFirstRowNum();
		int lastRow = sheet.getLastRowNum();
		for (int i = firstRow; i <= lastRow; i++) {
			// 创建新建excel Sheet的行
			Row rowCreat = sheetCreat.createRow(i);

			// 取得源有excel Sheet的行
			Row row = sheet.getRow(i);

			// 单元格式样
			CellStyle cellStyle = null;

			int firstCell = row.getFirstCellNum();
			int lastCell = row.getLastCellNum();
			for (int j = firstCell; j < lastCell; j++) {

				// 自动适应列宽
				sheetCreat.autoSizeColumn(j);

				// new一个式样
				cellStyle = wb.createCellStyle();

				// 设置边框线型
				cellStyle.setBorderTop(row.getCell(j).getCellStyle()
						.getBorderTop());
				cellStyle.setBorderBottom(row.getCell(j).getCellStyle()
						.getBorderBottom());
				cellStyle.setBorderLeft(row.getCell(j).getCellStyle()
						.getBorderLeft());
				cellStyle.setBorderRight(row.getCell(j).getCellStyle()
						.getBorderRight());

				// 设置内容位置:例水平居中,居右，居工
				cellStyle.setAlignment(row.getCell(j).getCellStyle()
						.getAlignment());
				// 设置内容位置:例垂直居中,居上，居下
				cellStyle.setVerticalAlignment(row.getCell(j).getCellStyle()
						.getVerticalAlignment());

				// 自动换行
				cellStyle.setWrapText(row.getCell(j).getCellStyle()
						.getWrapText());
				rowCreat.createCell(j).setCellStyle(cellStyle);

				// 设置单元格高度
				rowCreat.getCell(j).getRow().setHeight(
						row.getCell(j).getRow().getHeight());

				// 单元格类型
				switch (row.getCell(j).getCellType()) {
				case Cell.CELL_TYPE_STRING:
					String strVal = removeInternalBlank(row.getCell(j)
							.getStringCellValue());
					rowCreat.getCell(j).setCellValue(strVal);
					break;
				case Cell.CELL_TYPE_NUMERIC:
					rowCreat.getCell(j).setCellValue(
							row.getCell(j).getNumericCellValue());
					break;
				case Cell.CELL_TYPE_FORMULA:
					try {
						rowCreat.getCell(j).setCellValue(
								String.valueOf(row.getCell(j)
										.getNumericCellValue()));
					} catch (IllegalStateException e) {
						try {
							rowCreat.getCell(j).setCellValue(
									String.valueOf(row.getCell(j)
											.getRichStringCellValue()));
						} catch (Exception ex) {
							rowCreat.getCell(j).setCellValue("公式出错");
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * 去除字符串内部空格
	 */
	public static String removeInternalBlank(String s) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(s);
		char str[] = s.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			if (str[i] == ' ') {
				sb.append(' ');
			} else {
				break;
			}
		}
		String after = m.replaceAll("");
		return sb.toString() + after;
	}
	
	/**
	 * 获取文件后缀
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName){
		if(StringUtil.isEmpty(fileName)){
			return null;
		}
		int suffixIndex = fileName.lastIndexOf(".");
		if(-1 == suffixIndex){
			return null;
		}
		return fileName.substring(suffixIndex,fileName.length());
	}
}
