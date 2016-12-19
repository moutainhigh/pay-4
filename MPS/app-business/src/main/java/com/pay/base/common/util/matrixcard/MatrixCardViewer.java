package com.pay.base.common.util.matrixcard;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import javax.swing.ImageIcon;
import javax.swing.text.MaskFormatter;

import com.pay.base.common.helper.matrixcard.MatrixCardCfg;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/** 
 * <p>Title</p> 
 * 
 * Description 动态口令卡图片相关 by green.hong.
 * 
 * @version $ Revision: 1.2 $  $ Date: 2009-12-4 上午10:17:03 $
 */ 
public class MatrixCardViewer {
	
	public static final URL RESOURCE_PATH = MatrixCardViewer.class
			.getResource("card.png");
	
	public static final URL RESOURCE_DEMO_PATH = MatrixCardViewer.class
	.getResource("carddemo.png");
	
	private static ImageIcon imgIcon = new ImageIcon(RESOURCE_PATH);
	
	private static ImageIcon imgIconDemo = new ImageIcon(RESOURCE_DEMO_PATH);

	//单元格与边界之间基本宽度
	public static final int BASE_X = 32;

	//单元格与边界之间基本高度
	public static final int BASE_Y = 118;

	//每格宽度
	public static final int FILL_WIDTH = 31;

	//每格高度
	public static final int FILL_HEIGHT = 22;

	//序列号起始宽度
	public static final int SERIAL_NO_BASE_X = 80;

	//序列号起始高度
	public static final int SERIAL_NO_BASE_Y = 70;

	//有效期的起始宽度
	public static final int DATE_BASE_X = 70;

	//有效期的起始高度
	public static final int DATE_BASE_Y = 250;

	//序列号的颜色
	private static final Color SERIAL_NO_COLOR = new Color(51, 51, 51);

	//随机数的颜色
	private static final Color FILL_RANDOM_COLOR = new Color(55, 55, 55);
	
	private static final int RANDOM_BASE_X = 30;
	
	private static final int RANDOM_BASE_Y = 103;
	
	private static final int RANDOM_POS_OFFSET = 2;
	
	//填充数字尺寸
	private static final int FONT_SIZE = 13;
	//底部提示颜色
	//private static final Color VALID_INFO_COLOR = new Color(49, 42, 42);

	//private final  static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
	
	/**
	 * 展示一张生成数据后的动态口令卡图片
	 * @param matrixCard
	 * @return
	 * @throws ParseException
	 */
	public static byte[] showUserCard(MatrixCardDto matrixCard) {
		byte[] stream = null;
		
		if (imgIcon == null) {
			return stream;
		}
		BufferedImage bmg = new BufferedImage(imgIcon.getIconWidth(), imgIcon
				.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Image image = imgIcon.getImage();
		
		Graphics2D g = bmg.createGraphics(); 
		g.fillRect(0,0, imgIcon.getIconWidth(), imgIcon.getIconHeight());
		g.drawImage(image, 0, 0, null);
		//设置序列号
		g.setFont(new Font("Dialog", Font.BOLD, 14));
		g.setColor(SERIAL_NO_COLOR); 
		String serialNo = matrixCard.getSerialNo();
		MaskFormatter mf = null;
		try{
			mf = new MaskFormatter("AAA AAA AAA AAA AAA");
			mf.setValueContainsLiteralCharacters(false);
			serialNo = mf.valueToString(serialNo);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		g.drawString(serialNo, SERIAL_NO_BASE_X, SERIAL_NO_BASE_Y);
		String[] contents = matrixCard.getMatrixData().split(",");
		//循环根据坐标设置内容
		int posX;
		int posY;
		g.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
		g.setColor(FILL_RANDOM_COLOR);
		for (int i = 0; i < contents.length; i++) {
			posX = i % MatrixCardCfg.COLS * FILL_WIDTH + BASE_X;
			posY = i / MatrixCardCfg.COLS * FILL_HEIGHT + BASE_Y;
			g.drawString(contents[i], posX + (i % MatrixCardCfg.COLS / 2), posY);
		}
/*		g.setFont(new Font("Luxi Sans", Font.PLAIN, 12));
		g.setColor(VALID_INFO_COLOR);
		//填充底部验证有效期数据
		Date tmpDate = MatrixCardUtil.getBindLastValidDate(matrixCard.getCreationDate());
		String validDateStr = dateFormat.format(tmpDate); 
		//validDateStr = "请在" + validDateStr + "之前将本卡与您的账号进行绑定，一张卡只能绑定一个账号";
		validDateStr = "请您妥善保管口令卡，避免因遗失或被盗造成不必要损失。";
		g.drawString(validDateStr, DATE_BASE_X, DATE_BASE_Y);*/
		g.dispose();
		bmg.flush();
		//调用创建二进制流
		stream = createByteStream(bmg);
		bmg = null;
		return stream;
	}

	
	/**
	 * SHOW一张需要填写数据的提示图片，中间需填充部分以颜色标明
	 * @param p1 第一格坐标
	 * @param p2 第二格坐标
	 * @param p3 第三格坐标
	 * @return
	 */
	public static byte[] showPosition(String p1, String p2, String p3) {
		byte[] stream = null;
		//ImageIcon imgIcon = new ImageIcon(RESOURCE_PATH);
		if (imgIcon == null) {
			return stream;
		}
		BufferedImage bmg = new BufferedImage(imgIcon.getIconWidth(), imgIcon
				.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Image image = imgIcon.getImage();
		Graphics2D g = bmg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imgIcon.getIconWidth(), imgIcon.getIconHeight());
		g.drawImage(image, 0, 0, null);
		g.setColor(FILL_RANDOM_COLOR);
		g.setFont(new Font("Dialog", Font.BOLD, 14));
		//转移坐标
		int pos[] = MatrixCardUtil.convertPosToNumber(p1);
		//X坐标为 第几格*每格宽度  + 基本宽度
//		int posY = (pos[0] - 1) * FILL_HEIGHT + RANDOM_BASE_Y - pos[0] / 3;
		int posY = (pos[0] - 1) * FILL_HEIGHT + RANDOM_BASE_Y ;  
//		int posX = (pos[1] - 1) * FILL_WIDTH + RANDOM_BASE_X + pos[1] / 3;
		int posX = (pos[1] - 1) * FILL_WIDTH + RANDOM_BASE_X ;

		//画第一单元格
		g.setColor(FILL_RANDOM_COLOR);
		g.fillRect(posX, posY, FILL_WIDTH, FILL_HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString(p1, posX, posY + RANDOM_POS_OFFSET + FONT_SIZE);
		//画第二单元格
		
		pos = MatrixCardUtil.convertPosToNumber(p2);
		posY = (pos[0] - 1) * FILL_HEIGHT + RANDOM_BASE_Y ;
		posX = (pos[1] - 1) * FILL_WIDTH + RANDOM_BASE_X ;
		g.setColor(FILL_RANDOM_COLOR);
		g.fillRect(posX, posY, FILL_WIDTH, FILL_HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString(p2, posX, posY + RANDOM_POS_OFFSET + FONT_SIZE);
		//画第三单元格
		pos = MatrixCardUtil.convertPosToNumber(p3);
		posY = (pos[0] - 1) * FILL_HEIGHT + RANDOM_BASE_Y ;
		posX = (pos[1] - 1) * FILL_WIDTH + RANDOM_BASE_X ;
		g.setColor(FILL_RANDOM_COLOR); 
		g.fillRect(posX, posY, FILL_WIDTH, FILL_HEIGHT);
		g.setColor(Color.WHITE);
		g.drawString(p3, posX, posY + RANDOM_POS_OFFSET + FONT_SIZE);
		g.dispose();
		bmg.flush();
		//调用创建二进制流
		stream = createByteStream(bmg);
		bmg = null;
		
		return stream;
	}
	
	//创建图片流
	private static byte[] createByteStream(BufferedImage bmg) {
		byte[] stream = null;
		ByteArrayOutputStream ba = new ByteArrayOutputStream();
		//转为二进制编码
		JPEGImageEncoder encoder = null;
		JPEGEncodeParam param = null;
		try {
			encoder = JPEGCodec.createJPEGEncoder(ba);
			param = encoder.getDefaultJPEGEncodeParam(bmg);
			param.setQuality(0.75f, true);
			encoder.setJPEGEncodeParam(param);
			encoder.encode(bmg);
			
			stream = ba.toByteArray();
			ba.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			ba = null;
			encoder = null;
			param = null;
		}
		return stream;
	}

	/**
	 * 直接展示图片
	 * @return
	 * @throws IOException 
	 * @throws IOException
	 */
	public static byte[] showDemo() throws IOException {
		byte[] stream = null;
		
		if (imgIconDemo == null) {
			return stream;
		}
		BufferedImage bmg = new BufferedImage(imgIconDemo.getIconWidth(), imgIconDemo
				.getIconHeight(), BufferedImage.TYPE_INT_RGB);
		Image image = imgIconDemo.getImage();
		Graphics2D g = bmg.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		bmg.flush();
		//调用创建二进制流
		stream = createByteStream(bmg);
		
		return stream;
	}

}
