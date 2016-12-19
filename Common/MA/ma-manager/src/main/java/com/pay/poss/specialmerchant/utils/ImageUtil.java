package com.pay.poss.specialmerchant.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**	图片处理工具类	
 *  @author lIWEI
 *  @Date 2011-12-18
 *  @Description
 */
@SuppressWarnings("restriction")
public class ImageUtil {
	private static Log logger = LogFactory.getLog(ImageUtil.class);
	
	public static final int SMALL_WIDTH = 150;//缩略图宽度
	public static final int SMALL_HEIGHT = 150;//缩略图高度
	
	public static void imageConvert(String fullPath,String smallImageFullPath) {
		try{
			File _file = new File(fullPath); // 读入文件　
			Image src = javax.imageio.ImageIO.read(_file); // 构造Image对象　　
			//设置缩略图宽高
			BufferedImage tag = new BufferedImage(SMALL_WIDTH, SMALL_HEIGHT,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, SMALL_WIDTH, SMALL_HEIGHT, null); // 绘制缩小后的图
			FileOutputStream out = new FileOutputStream(smallImageFullPath); // 输出到文件流　　
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag); // 近JPEG编码　
			out.close();
		}catch(Exception e){
			logger.error("上传小图出现异常：[" + e + "]");
		}
	}
	
	public static void imageConvertForStream(InputStream inputStream,String smallImageFullPath) {
		try{
			Image src = javax.imageio.ImageIO.read(inputStream); // 构造Image对象　　
			//设置缩略图宽高
			BufferedImage tag = new BufferedImage(SMALL_WIDTH, SMALL_HEIGHT,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, SMALL_WIDTH, SMALL_HEIGHT, null); // 绘制缩小后的图
			FileOutputStream out = new FileOutputStream(smallImageFullPath); // 输出到文件流　　
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag); // 近JPEG编码　
			out.close();
		}catch(Exception e){
			logger.error("上传小图出现异常：[" + e + "]");
		}
	}
	
	public static void scaleImage(String fullPath,String smallImageFullPath){
		try {
			File _file = new File(fullPath); // 读入文件　
			Image src = javax.imageio.ImageIO.read(_file);
			int width = 0;
			int height = 0;
			if(src.getWidth(null) > 650){
				width = 650;
				height  = (int)(650*src.getHeight(null)/src.getWidth(null) );
			}else{
				width = src.getWidth(null);
				height = src.getHeight(null);
			}
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, width, height, null); // 绘制缩小后的图
			FileOutputStream out = new FileOutputStream(smallImageFullPath); // 输出到文件流　　
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag); // 近JPEG编码　
			out.close();
//            System.out.println("windth="+width+",height="+height);
		} catch (IOException e) {
			logger.error("上传图出现异常：[" + e + "]");
		} 
	}
	public static void scaleImageForStream(InputStream inputStream,String smallImageFullPath){
		try {
			Image src = javax.imageio.ImageIO.read(inputStream); // 构造Image对象
			int width = 0;
			int height = 0;
			if(src.getWidth(null) > 650){
				width = 650;
				height  = (int)(650*src.getHeight(null)/src.getWidth(null) );
			}else{
				width = src.getWidth(null);
				height = src.getHeight(null);
			}
			BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(src, 0, 0, width, height, null); // 绘制缩小后的图
			FileOutputStream out = new FileOutputStream(smallImageFullPath); // 输出到文件流　　
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			encoder.encode(tag); // 近JPEG编码　
			out.close();
//            System.out.println("windth="+width+",height="+height);
		} catch (IOException e) {
			logger.error("上传图出现异常：[" + e + "]");
		} 
	}
	public static void main(String[] arg){
		ImageUtil.scaleImage("E:\\data\\upload\\specialmerchant\\smlc11_small_big.jpg", "E:\\data\\upload\\specialmerchant\\smlc11_small_big_ratio.jpg");
	}
}
