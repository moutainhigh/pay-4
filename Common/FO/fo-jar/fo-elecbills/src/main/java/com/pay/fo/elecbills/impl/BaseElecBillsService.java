/**
 *  File: BaseElecBillsService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-5   Sany        Create
 *
 */
package com.pay.fo.elecbills.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.elecbills.ElecBillsService;
import com.pay.fo.elecbills.PositionDto;
import com.pay.fo.elecbills.dto.ElecBillsDto;
/**
 * 
 */
public abstract class BaseElecBillsService implements ElecBillsService {
	
	/** X坐标偏移 **/
	private int offsetX;
	
	/** Y坐标偏移 **/
	private int offsetY;
	
	/**
	 * 页面路径
	 */
	private String pagePath;
	
	/**
	 * 回单图片路径
	 */
	private String imgPath;
	
	/** 协议代付方用户名 **/
	private String agreementPayName;
	
	/** 交易类型 **/
	private String tradeType;
	
	/**
	 * 获取前台展示数据
	 * @param map
	 * @return
	 */
	protected abstract ElecBillsDto getViewData(Map<String,Object> map) throws Exception;
	
	protected transient Log log = LogFactory.getLog(getClass());
	
	@Override
	public Map<String, Object> layoutBills(Map<String, Object> serialNo) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pagePath", pagePath);
		try {
			map.put("viewData", getViewData(serialNo));
		} catch (Exception e) {
			log.error("获取电子回单自定义数据出错",e);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BufferedImage generateBills(Map<String, Object> serialNo) throws Exception {
		File file = new File(imgPath);
		Font font = new Font("宋体", 20, 12);
		Image src = ImageIO.read(file);
		int wideth = src.getWidth(null);
		int height = src.getHeight(null);
		BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.drawImage(src, 0, 0, wideth, height, null);
		graphics.setColor(Color.black);
		graphics.setFont(font);
		ElecBillsDto elecBillsDto = getViewData(serialNo);
		Class<?> clazz = elecBillsDto.getClass();
		List<PositionDto> list = (List<PositionDto>) serialNo.get("position");
		for (PositionDto position : list) {
			Method m = clazz.getMethod(getMethodName(position.getFieldName()));
			Object fieldValue = m.invoke(elecBillsDto);
			if (fieldValue == null){
				continue;
			}
			if (fieldValue.toString().length() > 21) {
				String value1 = fieldValue.toString().substring(0,21);
				String value2 = fieldValue.toString().substring(21);
				graphics.drawString(value1, position.getX()+offsetX, position.getY()+offsetY-font.getSize()/2-1);
				graphics.drawString(value2, position.getX()+offsetX, position.getY()+offsetY+font.getSize()/2+1);
				
			}else {
				graphics.drawString(fieldValue.toString(), position.getX()+offsetX, position.getY()+offsetY);
			}
		}
		graphics.dispose();
		return image;
	}
	
	/**
	 * 获取get方法
	 * @param field
	 * @return
	 */
	private String getMethodName(String field) {
		if (field == null) {
			return "";
		}
		return "get" + field.substring(0,1).toUpperCase()+field.substring(1);
	}
	
	/**
	 * 金额add
	 * @param amount1
	 * @param amount2
	 * @return
	 */
	protected String amountAdd(String amount1,String amount2) {
		Long amountL1 = StringUtils.isNotEmpty(amount1) ? Long.valueOf(amount1) : 0;
		Long amountL2 = StringUtils.isNotEmpty(amount2) ? Long.valueOf(amount2) : 0;
		return String.valueOf(amountL1 + amountL2);
	}
	
	/**
	 * 去掉时间后面的".0"
	 * @param time
	 * @return
	 */
	protected String formatCreteTime(String time) {
		if (StringUtils.isBlank(time)) {
			return "";
		}
		if (time.indexOf(".") > 0)
			return time.substring(0,time.indexOf("."));
		else
			return time;
	}
	
	/**
	 * @param xOffset the xOffset to set
	 */
	public void setxOffset(int offsetX) {
		this.offsetX = offsetX;
	}
	
	/**
	 * @param yOffset the yOffset to set
	 */
	public void setyOffset(int offsetY) {
		this.offsetY = offsetY;
	}


	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}


	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}


	public void setAgreementPayName(String agreementPayName) {
		this.agreementPayName = agreementPayName;
	}


	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}


	public String getAgreementPayName() {
		return agreementPayName;
	}


	public String getTradeType() {
		return tradeType;
	}
	
}
