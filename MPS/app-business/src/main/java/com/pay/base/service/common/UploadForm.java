package com.pay.base.service.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm implements Serializable{
	
	private static final long serialVersionUID = 4333741291993213948L;
	
	private List<MultipartFile> file;
	/**
	 * 子目录
	 */
	private String chlidPath;
	
	/**
	 * 是否生成缩略图
	 */
	private boolean isResize=false;
	/**
	 * 压缩的宽
	 */
	private int resizeWidth;
	/**
	 * 压缩的高
	 */
	private int resizeHeight;
	/**
	 * 压缩像素比
	 */
	private float quality=(float)0.7;
	
	
	/**
	 * 是否加水印
	 */
	private boolean isPress=false;
	/**
	 * 水印文字
	 */
	private String pressText;
	/**
	 * 水印文字字体
	 */
	private String fontName="宋体";
	/**
	 * 水印文字字体样式
	 */
	private int fontStyle=16;
	/**
	 * 水印文字字体大小
	 */
	private int fontSize=36;

	public List<MultipartFile> getFile() {
		return file;
	}
	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}
	public String getChlidPath() {
		return chlidPath;
	}
	public void setChlidPath(String chlidPath) {
		this.chlidPath = chlidPath;
	}
	public boolean isResize() {
		return isResize;
	}
	public void setResize(boolean isResize) {
		this.isResize = isResize;
	}
	public int getResizeWidth() {
		return resizeWidth;
	}
	public void setResizeWidth(int resizeWidth) {
		this.resizeWidth = resizeWidth;
	}
	public int getResizeHeight() {
		return resizeHeight;
	}
	public void setResizeHeight(int resizeHeight) {
		this.resizeHeight = resizeHeight;
	}
	public float getQuality() {
		return quality;
	}
	public void setQuality(float quality) {
		this.quality = quality;
	}
	public boolean isPress() {
		return isPress;
	}
	public void setPress(boolean isPress) {
		this.isPress = isPress;
	}
	public String getPressText() {
		return pressText;
	}
	public void setPressText(String pressText) {
		this.pressText = pressText;
	}
	public String getFontName() {
		return fontName;
	}
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	public int getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	public int getFontSize() {
		return fontSize;
	}
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	
	
}
