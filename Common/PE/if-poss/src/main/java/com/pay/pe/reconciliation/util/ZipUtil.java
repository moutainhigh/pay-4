/**
 *  File: UnZipUtil.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-10-12   曾利                      Create
 *
 */
package com.pay.pe.reconciliation.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.ISevenZipInArchive;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.util.Assert;

/**
 * 解压缩文件 支持 zip rar 7-zip工具类
 */
public class ZipUtil {

	final private static ZipUtil zipUtil = new ZipUtil();

	private ZipUtil() {
	};

	public static ZipUtil getZipUtil() {
		return zipUtil;
	}

	private Log log = LogFactory.getLog(ZipUtil.class);

	/**
	 * 解压缩文件 支持 zip rar 7-zip
	 * 
	 * @param sourceZipFile
	 * @param destinationDir
	 * @param password
	 */
	public boolean unzipDirWithPassword(final String sourceZipFile,
			final String destinationDir, final String password) {

		Assert.notNull(sourceZipFile, "sourceZipFile must not be null。");
		Assert.notNull(destinationDir, "destinationDir must not be null。");

		if (log.isInfoEnabled()) {
			log.info("解压参数验证:sourceZipFile:" + sourceZipFile
					+ ",destinationDir:" + destinationDir + ",password:"
					+ password);
		}

		RandomAccessFile randomAccessFile = null;
		ISevenZipInArchive inArchive = null;
		try {
			randomAccessFile = new RandomAccessFile(sourceZipFile, "r");
			inArchive = SevenZip.openInArchive(null, // autodetect archive type
					new RandomAccessFileInStream(randomAccessFile));

			// Getting simple interface of the archive inArchive
			ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();

			for (final ISimpleInArchiveItem item : simpleInArchive
					.getArchiveItems()) {
				final int[] hash = new int[] { 0 };
				if (!item.isFolder()) {
					ExtractOperationResult result;
					result = item.extractSlow(new ISequentialOutStream() {
						public int write(final byte[] data)
								throws SevenZipException {
							try {
								if (item.getPath().indexOf(File.separator) != -1) {
									String path = destinationDir
											+ File.separator
											+ item.getPath().substring(
													0,
													item.getPath().lastIndexOf(
															File.separator));
									File folderExisting = new File(path);
									if (!folderExisting.exists())
										new File(path).mkdirs();
								}
								if (!new File(destinationDir + File.separator
										+ item.getPath()).exists()) {
									File folderExisting = new File(
											destinationDir);
									if (!folderExisting.exists()) {
										folderExisting.mkdirs();
									}
									new File(destinationDir).createNewFile();
								}
								OutputStream out = new FileOutputStream(
										destinationDir + File.separator
												+ item.getPath());
								out.write(data);
								out.close();
							} catch (Exception e) {
								log.error("解压错误." + e.getMessage(), e);
								throw new SevenZipException("解压异常"
										+ e.getMessage(), e);
							}
							hash[0] |= Arrays.hashCode(data);
							return data.length; // Return amount of proceed data
						}
					}, password); // / password.
					if (result == ExtractOperationResult.OK) {
						log.info(String.format("%9X | %s", hash[0],
								item.getPath()));
					} else {
						log.error("Error extracting item: ");
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			log.error("解压错误." + e.getMessage(), e);
			return false;
		} finally {
			if (inArchive != null) {
				try {
					inArchive.close();
				} catch (SevenZipException e) {
					log.error("Error closing archive:." + e.getMessage(), e);
					return false;
				}
			}
			if (randomAccessFile != null) {
				try {
					randomAccessFile.close();
				} catch (IOException e) {
					log.error("Error closing file:." + e.getMessage(), e);
					return false;
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	public boolean unZipFile(String sourcePath, String targetPath) {
		int buffer = 1024;
		try {
			ZipFile zipFile = new ZipFile(sourcePath, "GBK");

			Enumeration emu = zipFile.getEntries();
			while (emu.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) emu.nextElement();
				// 会把目录作为一个file读出一次，所以只建立目录就可以，之下的文件还会被迭代到。
				String filename = entry.getName();
				if (entry.isDirectory()) {
					new File(targetPath + filename).mkdirs();
					continue;
				}
				BufferedInputStream bis = new BufferedInputStream(
						zipFile.getInputStream(entry));
				File file = new File(targetPath + filename);
				// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
				// 而这个文件所在的目录还没有出现过，所以要建出目录来。
				File parent = file.getParentFile();
				if (parent != null && (!parent.exists())) {
					parent.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos, buffer);
				int count;
				byte data[] = new byte[buffer];
				while ((count = bis.read(data, 0, buffer)) != -1) {
					bos.write(data, 0, count);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
			zipFile.close();
			return true;
		} catch (Exception e) {
			log.error("解压错误." + e.getMessage(), e);
			return false;
		}

	}

	public boolean gzipFile(String sourcePath,String targetPath,String fileNames[]){
		  try {
		       for(String fileName : fileNames){
		            String outFileName = targetPath+"/"+fileName + ".gz";
		            GZIPOutputStream out = null;
		            out = new GZIPOutputStream(new FileOutputStream(outFileName));
		            FileInputStream in = null;
		            in = new FileInputStream(sourcePath+"/"+fileName);
	
		            byte[] buf = new byte[1024];
		            int len;
		            while((len = in.read(buf)) > 0) {
		                out.write(buf, 0, len);
		            }
		            in.close();
		            out.finish();
		            out.close();
		       }
		       return true;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return false;
	        }

	}
	
	public boolean unGZipFile(String sourcePath, String targetPath,String fileName) {
		try {
			FileInputStream fin = new FileInputStream(sourcePath); // 得以文件输入流
			
			GZIPInputStream gzin = new GZIPInputStream(fin); // 得到压缩输入流
			
			File file = new File(targetPath);
			// 加入这个的原因是zipfile读取文件是随机读取的，这就造成可能先读取一个文件
			// 而这个文件所在的目录还没有出现过，所以要建出目录来。
			if (file != null && (!file.exists())) {
				file.mkdirs();
			}
			FileOutputStream fout = new FileOutputStream(targetPath+File.separator+fileName); // 得到文件输出流
			byte[] buf = new byte[1024]; // 缓冲区大小
			int num;
			while ((num = gzin.read(buf, 0, buf.length)) != -1) { // 如果文件未读完
				fout.write(buf, 0, num); // 写入缓冲数据到输出流
			}
			gzin.close(); // 关闭压缩输入流
			fout.close(); // 关闭文件输出流
			fin.close(); // 关闭文件输入流
			return true;
		} catch (Exception ex) {
			ex.printStackTrace(); // 打印出错信息
			return false;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ZipUtil.getZipUtil().unzipDirWithPassword("d:/opt/upload/ecard/1008.rar",
		// "d:/opt/upload/ecard/temp/", null);
		String [] fileNames = {"dz_mobile_20120329160228"};
//		ZipUtil.getZipUtil().unGZipFile("/opt/B_DTL01_20120202.gz", "/opt/temp", "dz_mobile_20120329160228");
		
//		ZipUtil.getZipUtil().unGZipFile("/opt/B_DTL01_20120202.gz", "/opt/temp", "dz_mobile_20120329160228");

		ZipUtil.getZipUtil().unZipFile("/opt/dz_mobile_20120329160228.zip", "/opt/temp/");
		// ZipUtil.getZipUtil().unzipDirWithPassword("d:/opt/upload/ecard/1008.7z",
		// "d:/opt/upload/ecard/temp/", null);

	}

}
