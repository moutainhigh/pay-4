/**
 *  File: FileTransfUtil.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-27      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.common.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.util.StringUtil;

/**
 * @author jason_wang
 * 
 */
public class FileTransfUtil {

	private static final String RESOURCE_FILE_PATH = "D:\\bankfileinfos\\BankInfo_20101213103227.csv";
	private static final String TARGET_FILE_PATH = "D:\\bankfileinfos\\output\\";
	private static final String CITY_FILE_PATH = "D:\\bankfileinfos\\cityrelations.csv";
	private static final String PROVINCE_FILE_PATH = "D:\\bankfileinfos\\provinceCodes.csv";
	private static final String BANK_FILE_PATH = "D:\\bankfileinfos\\bankCode.txt";
	private static Map<String, String> cityMap = new HashMap<String, String>();
	private static Map<String, String> provinceMap = new HashMap<String, String>();
	private static Map<String,String> bankMap = new HashMap<String,String>();

	public  void loadProvincesAndCitys() throws FileNotFoundException, IOException {
		//加载城市信息
		List<String> result = IOUtils.readLines(new FileInputStream(CITY_FILE_PATH), "UTF-8");		
		String[] temps = null;
		for (String line : result) {
			if(StringUtil.isEmpty(line)){
				continue;
			}
			temps = line.split(",");
			if(2 > temps.length){
				continue;
			}
			cityMap.put(temps[1],temps[0]);
			
		}
		
		//加载省份信息
		result = IOUtils.readLines(new FileInputStream(PROVINCE_FILE_PATH), "UTF-8");
		for (String line : result) {
			if(StringUtil.isEmpty(line)){
				continue;
			}
			temps = line.split(",");
			if(2 > temps.length){
				continue;
			}
			provinceMap.put(temps[1],temps[0]);
		}
		
		//加载银行信息
		result = IOUtils.readLines(new FileInputStream(BANK_FILE_PATH),"UTF-8");
		for (String line : result) {
			if(StringUtil.isEmpty(line)){
				continue;
			}
			temps = line.split(",");
			if(2 > temps.length){
				continue;
			}
			bankMap.put(temps[0],temps[1]);
		}
	}
	
	public void luceneResourceFileTransf(){
		BufferedWriter bw = null;
		try {
			List<String> result = IOUtils.readLines(new FileInputStream(RESOURCE_FILE_PATH), "GBK");
			File file = new File(TARGET_FILE_PATH + "bankfile.csv");
			if(!file.exists()){
				file.createNewFile();
			}
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"GBK"));
			SearchResultInfoDTO info = null;
			String provinceCode = "".intern();
			String tempProvinceCode = "".intern();
			String cityCode = "".intern();
			String bankCode = "".intern();
			StringBuffer temp = null;
			int size = result.size();
			List<String> tempList = new ArrayList<String>();
			String line = null;
			for (int i = 1; i < size; i++ ) {
				line = result.get(i);
				if(StringUtil.isEmpty(line)){
					continue;
				}
				info = tokenLine(line);
				if(null == info.getBankNo() && 12 != info.getBankNo().length()){
					continue;
				}
				bankCode = info.getBankNo().substring(0,3);
				provinceCode = info.getBankNo().substring(3,5);
				tempProvinceCode = info.getBankNo().substring(3,6);
				cityCode = info.getBankNo().substring(3,7);
				temp = new StringBuffer();
				if(bankMap.containsKey(bankCode)){
					temp.append(bankMap.get(bankCode)).append(",");
				}else{
					temp.append(info.getBankName()).append(",");
				}
				
				if(provinceMap.containsKey(tempProvinceCode)){
					temp.append(StringUtil.null2String(provinceMap.get(tempProvinceCode))).append(",");
				}else{
					temp.append(StringUtil.null2String(provinceMap.get(provinceCode))).append(",");
				}
				if(cityMap.containsKey(cityCode)){
					temp.append(StringUtil.null2String(cityMap.get(cityCode))).append(",");
				}else{
					if(!tempList.contains(cityCode)){
						tempList.add(cityCode);
					}
					temp.append(cityCode).append(",");
				}
				temp.append(info.getBankNo()).append(",");
				temp.append(info.getBankName()).append(",");
				temp.append(info.getBankName());
				temp.append(info.getMark()).append(",");		
				temp.append(provinceCode).append(",");
				temp.append(cityCode).append(",");
				temp.append("\r\n");
				bw.write(temp.toString());
			}
			
			System.out.println("size=" + tempList.size());
			for(String str:tempList){
				System.out.println(str);
			}
		} catch (FileNotFoundException e) {
			LogUtil.error(FileTransfUtil.class,"联行号文件不存在",
					OPSTATUS.EXCEPTION,"","",e.getMessage(),"",e.getMessage());
		} catch (IOException e) {
			LogUtil.error(FileTransfUtil.class,"加载城市和省份编码出现异常",
					OPSTATUS.EXCEPTION,"","",e.getMessage(),"",e.getMessage());
		} finally{
			if(null != bw){
				try {
					bw.close();
				} catch (IOException e) {
					LogUtil.error(FileTransfUtil.class,"关闭文件流出现异常",
							OPSTATUS.EXCEPTION,"","",e.getMessage(),"",e.getMessage());
				}
			}
		}
		
	}

	public static SearchResultInfoDTO tokenLine(String line) {
		String[] result = line.split(",");
		SearchResultInfoDTO data = new SearchResultInfoDTO();
		data.setBankNo(trim(result[0]));
		data.setBankName(trim(result[1]));
		data.setClearBankNo(trim(result[2]));
		data.setStatus(trim(result[3]));
		if (result.length > 4)
			data.setCcpc(trim(result[4]));
		if (result.length > 5)
			data.setCityCode(trim(result[5]));
		if (result.length > 6)
			data.setMark(trim(result[6]));
		return data;
	}

	public static String trim(String value) {
		return value == null ? "" : value.trim();
	}
	
	public static void getBankCodes(){
		BufferedWriter bw = null;
		try{		
			List<String> result = IOUtils.readLines(new FileInputStream(RESOURCE_FILE_PATH), "GBK");
			String[] temps = null;
			String bankNo = "".intern();
			String bankCode = "".intern();
			Map<String,String> tempMap = new TreeMap<String, String>();
			for (String line : result) {
				if(StringUtil.isEmpty(line)){
					continue;
				}
				
				temps = line.trim().split(",");
				bankNo = temps[0];
				bankCode = bankNo.substring(0,3);
				if(!tempMap.containsKey(bankCode)){
					tempMap.put(bankCode,temps[1]);
				}
			}
			
		
			String fileName = TARGET_FILE_PATH + "bankCode.txt";
			File file = new File(fileName);
			if(!file.exists()){
				file.createNewFile();
			}
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"GBK"));
			Set<String> set = tempMap.keySet();
			StringBuffer temp = null;
			for (String string : set) {
				temp = new StringBuffer();
				temp.append(string).append(",");
				temp.append(tempMap.get(string)).append("\r\n");
				bw.write(temp.toString());
			}
		} catch (FileNotFoundException e) {
			LogUtil.error(FileTransfUtil.class,"联行号文件不存在",
					OPSTATUS.EXCEPTION,"","",e.getMessage(),"",e.getMessage());
		} catch (IOException e) {
			LogUtil.error(FileTransfUtil.class,"加载城市和省份编码出现异常",
					OPSTATUS.EXCEPTION,"","",e.getMessage(),"",e.getMessage());
		} finally{
			if(null != bw){
				try {
					bw.close();
				} catch (IOException e) {
					LogUtil.error(FileTransfUtil.class,"关闭文件流出现异常",
							OPSTATUS.EXCEPTION,"","",e.getMessage(),"",e.getMessage());
				}
			}
		}
	}
	
	public static String replaceStr(String resource,String str){
		if(StringUtils.isEmpty(resource)){
			return "";
		}else if(StringUtils.isEmpty(str)){
			return resource;
		}else{
			if(resource.endsWith(str)){
				return resource.replaceFirst(str,"");
			}else{
				return resource;
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		FileTransfUtil trans =new FileTransfUtil();
		trans.loadProvincesAndCitys();
		trans.luceneResourceFileTransf();	
		
		System.out.println("中国工商银行".indexOf("工商"));
		
		System.out.println(FileTransfUtil.replaceStr("湖南省","省"));
		System.out.println("湖南省".substring(0,1));
		
	}
}
