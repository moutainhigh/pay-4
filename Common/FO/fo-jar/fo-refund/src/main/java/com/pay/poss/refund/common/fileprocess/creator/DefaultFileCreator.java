 /** @Description 
 * @project 	poss-reconcile
 * @file 		DefualtFileCreator.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-7-27		Henry.Zeng			Create 
*/
package com.pay.poss.refund.common.fileprocess.creator;

import java.io.File;
import java.util.Calendar;

import org.springframework.beans.BeanUtils;

import com.pay.poss.refund.model.FileInfo;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-7-27
 * @see 
 */
public class DefaultFileCreator extends FileAbstraceCreator {
	@Override
	/**
	 * 创建文件目录
	 */
	public String createFileFolder(final FileInfo fileInfo) {
		final String path = getFilePath(fileInfo);
		final String fileFolder = fileInfo.getFileFolder(); // getFileFolder();取文件目录存放对账文件的providerCode
		String fullPath = "";
		if (!path.endsWith(File.separator)) {
			fullPath = path + File.separator + fileFolder;
		}
		final File file = new File(fullPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		logger.debug(this.getClass().getName()+"createFileFolder.fullPath"+fullPath);
		return fileFolder;
	}
	@Override
	/**
	 * 得到文件目录,的文件目录格式是yyyyMMdd
	 */
	public String getFileFolder() {
		final Calendar c = Calendar.getInstance();
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);
		final StringBuffer sb = new StringBuffer();
		sb.append(year).append(
				month < 10 ? ("0" + month) : String.valueOf(month)).append(
				day < 10 ? ("0" + day) : String.valueOf(day));
		logger.debug(this.getClass().getName()+"getFileFolder.fileFolder"+sb.toString());
		return sb.toString();
	}
	@Override
	/**
	 * 创建文件编号,一般默认为当天日期yyyyMMddHHmiss+随机数
	 */
	public String createFileId() {
		
		final Calendar c = Calendar.getInstance();
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);
		final int hour = c.get(Calendar.HOUR_OF_DAY);
		final int minute = c.get(Calendar.MINUTE);
		final int second = c.get(Calendar.SECOND);
		final int milisecond = c.get(Calendar.MILLISECOND);

		final StringBuffer sb = new StringBuffer();
		sb.append(year).append(
				month < 10 ? ("0" + month) : String.valueOf(month)).append(
				day < 10 ? ("0" + day) : String.valueOf(day)).append(
				hour < 10 ? ("0" + hour) : String.valueOf(hour)).append(
				minute < 10 ? ("0" + minute) : String.valueOf(minute)).append(
				second < 10 ? ("0" + second) : String.valueOf(second)).append(
				milisecond);
				//.append(Math.abs(String.valueOf(Math.random()).hashCode()));
		
		return sb.toString();
	}
	@Override
	/**
	 * 得到文件路径,文件路径是从缓存中取
	 */
	public String getFilePath(final FileInfo fileInfo) {
		String filePath = "";
		if (fileInfo.getFileType() != null) {
			filePath = (String) this.getFilePathMap().get(
					fileInfo.getFileType());
		}
		return filePath;
	}
	

	/**
	 * 获取在今天之前的某天的日期
	 * 
	 * @param days
	 *            在今天之前多少天，例如：1-昨天，2-前天，依此类推
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getDateBeforeGivenDays(final int days) {
		final Calendar c = Calendar.getInstance();
		c.setTimeInMillis(c.getTimeInMillis() - days * 24 * 3600 * 1000);
		final int year = c.get(Calendar.YEAR);
		final int month = c.get(Calendar.MONTH) + 1;
		final int day = c.get(Calendar.DAY_OF_MONTH);

		final StringBuffer sb = new StringBuffer();
		sb.append(year).append(
				month < 10 ? ("0" + month) : String.valueOf(month)).append(
				day < 10 ? ("0" + day) : String.valueOf(day));

		return sb.toString();
	}


}
