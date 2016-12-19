package com.pay.poss.specialmerchant.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FileProcessUtil {
	private static Log logger = LogFactory.getLog(FileProcessUtil.class);
	/**
	 * 得到文件目录,文件目录格式是yyyyMMdd,20120625
	 */
	public static String getFileFolder() {
		final Calendar c = Calendar.getInstance();
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);
		final StringBuffer sb = new StringBuffer();
		sb.append(year).append(
				month < 10 ? ("0" + month) : String.valueOf(month)).append(
				day < 10 ? ("0" + day) : String.valueOf(day));
		logger.debug("getFileFolder.fileFolder"+sb.toString());
		return sb.toString();
	}
	/**创建目录
	 * @param filePath：根目录
	 * @param fileFolder：文件目录
	 */
	public static void createFileFolder(final String filePath , final String fileFolder) {
		String fullPath = "";
		if (!filePath.endsWith(File.separator)) {
			fullPath = filePath + File.separator + fileFolder;
		}
		final File file = new File(fullPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.debug("FileProcessUtil.createFileFolder.fullPath"+fullPath);
	}
	public static void createFileFolder(final String filePath) {
		String fullPath = "";
		if (!filePath.endsWith(File.separator)) {
			fullPath = filePath;
		}
		final File file = new File(fullPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.debug("FileProcessUtil.createFileFolder.fullPath"+fullPath);
	}
	/**删除指定的文件
	 * @param fullPath
	 * @return
	 */
	public static boolean deleteFile(final String fullPath){
		final File file = new File(fullPath);
	    if(file.exists()){
		    return file.delete();
	    }
	    return false;
	}
	/**判断指定文件是否已经存在
	 * @param fullPath
	 * @return
	 */
	public static boolean fileIsExist(final String fullPath){
		final File file=new File(fullPath); 
		if(file.exists()) {
			return true;
		}
		return false;
	}
	/**本地保存文件
	 * @param inputStream
	 * @param fullPath
	 * @throws Exception
	 */
	public static void writeFile(final InputStream inputStream,final String fullPath) throws Exception {
		OutputStream out = null;
		try {
			out = new BufferedOutputStream( new FileOutputStream(fullPath));

			int bytesRead = 0;
			final byte[] buffer = new byte[1024];
			while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
				out.write(buffer, 0, bytesRead);// 将文件写入写入磁盘；
			}
		} catch (Exception ioe) {
			logger.error("IO流操作异常 [" + ioe + "]");
			throw new Exception("IO流操作异常" + ioe.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
				logger.error("IO流操作异常 [" + ioe + "]");
				throw new Exception("IO流操作异常" + ioe.getMessage());
			}
		}

	}
	public static boolean isImageFile(String fileName){
		if(! fileName.endsWith(".")){
			String fileSuff = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
			return  ("jpg,gif,jpeg,bmp,png").indexOf(fileSuff) >= 0; 
		}
		return false;
	}
	public static void main(String[] args){
		System.out.println(FileProcessUtil.getFileFolder());
		
		String logoPath = "/file/specialmerchant/smlc11_small.jpg";
		//图片
		if(StringUtils.isNotEmpty(logoPath)){
			int idx = logoPath.lastIndexOf(".");
			if(idx >= 0){
				String logoPathBig = logoPath.substring(0, idx) + "_big" + logoPath.substring(idx);
				System.out.println(logoPathBig);// /file/specialmerchant/smlc11_small_big.jpg
			}
		}
		String str = "hello world ";
		System.out.println(str.replaceAll("hello", "world"));
		
		String spMerchantLogoBig = logoPath.substring(0, logoPath.lastIndexOf(".")) + "_big" + logoPath.substring(logoPath.lastIndexOf("."));
		System.out.println(spMerchantLogoBig);
	}
}
