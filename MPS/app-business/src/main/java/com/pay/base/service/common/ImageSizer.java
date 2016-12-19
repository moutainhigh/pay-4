package com.pay.base.service.common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图像压缩工具
 *
 */
public class ImageSizer {
    public static final MediaTracker tracker = new MediaTracker(new Component() {
        private static final long serialVersionUID = 1234162663955668507L;} 
    );
    /**
     * @param originalFile 原图像
     * @param resizedFile 压缩后的图像
     * @param width 图像宽
     * @param format 图片格式 jpg, png, gif(非动画)
     * @throws IOException
     */
    public static void resize(File originalFile, File resizedFile, int width, int newheight,String format) throws IOException {
        if(format!=null && "gif".equals(format.toLowerCase())){
        	resize(originalFile, resizedFile, width, 1);
        	return;
        }
        FileInputStream fis = new FileInputStream(originalFile);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int readLength = -1;
        int bufferSize = 1024;
        byte bytes[] = new byte[bufferSize];
        while ((readLength = fis.read(bytes, 0, bufferSize)) != -1) {
            byteStream.write(bytes, 0, readLength);
        }
        byte[] in = byteStream.toByteArray();
        fis.close();
        byteStream.close();
        
    	Image inputImage = Toolkit.getDefaultToolkit().createImage( in );
        waitForImage( inputImage );
        int imageWidth = inputImage.getWidth( null );
        if ( imageWidth < 1 ) 
           throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );
        int imageHeight = inputImage.getHeight( null );
        if ( imageHeight < 1 ) 
           throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );
        
        // Create output image.
        int height = -1;
        double scaleW = (double) imageWidth / (double) width;
        double scaleY = (double) imageHeight / (double) height;
        if (scaleW >= 0 && scaleY >=0) {
            if (scaleW > scaleY) {
                height = -1;
            } else {
                width = -1;
            }
        }
        Image outputImage = inputImage.getScaledInstance( width, newheight, java.awt.Image.SCALE_DEFAULT);
        checkImage( outputImage );        
        encode(new FileOutputStream(resizedFile), outputImage, format);        
    }    

    /** Checks the given image for valid width and height. */
    private static void checkImage( Image image ) {
       waitForImage( image );
       int imageWidth = image.getWidth( null );
       if ( imageWidth < 1 ) 
          throw new IllegalArgumentException( "image width " + imageWidth + " is out of range" );
       int imageHeight = image.getHeight( null );
       if ( imageHeight < 1 ) 
          throw new IllegalArgumentException( "image height " + imageHeight + " is out of range" );
    }

    /** Waits for given image to load. Use before querying image height/width/colors. */
    private static void waitForImage( Image image ) {
       try {
          tracker.addImage( image, 0 );
          tracker.waitForID( 0 );
          tracker.removeImage(image, 0);
       } catch( InterruptedException e ) { e.printStackTrace(); }
    } 

    /** Encodes the given image at the given quality to the output stream. */
    private static void encode( OutputStream outputStream, Image outputImage, String format ) 
       throws java.io.IOException {
       int outputWidth  = outputImage.getWidth( null );
       if ( outputWidth < 1 ) 
          throw new IllegalArgumentException( "output image width " + outputWidth + " is out of range" );
       int outputHeight = outputImage.getHeight( null );
       if ( outputHeight < 1 ) 
          throw new IllegalArgumentException( "output image height " + outputHeight + " is out of range" );

       // Get a buffered image from the image.
       BufferedImage bi = new BufferedImage( outputWidth, outputHeight,
          BufferedImage.TYPE_INT_RGB );                                                   
       Graphics2D biContext = bi.createGraphics();
       biContext.drawImage( outputImage, 0, 0, null );
       ImageIO.write(bi, format, outputStream);
       outputStream.flush();   
       outputStream.close();
    } 
    
	/**
	 * 缩放gif图片
	 * @param originalFile 原图片
	 * @param resizedFile 缩放后的图片
	 * @param newWidth 宽度
	 * @param quality 缩放比例 (等比例)
	 * @throws IOException
	 */
    private static void resize(File originalFile, File resizedFile, int newWidth, float quality) throws IOException {
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        } 
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null; 
        int iWidth = i.getWidth(null);
        int iHeight = i.getHeight(null); 
        if (iWidth > iHeight) {
            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight) / iWidth, Image.SCALE_SMOOTH);
        } else {
            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight, newWidth, Image.SCALE_SMOOTH);
        } 
        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage(); 
        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null),
                                                        BufferedImage.TYPE_INT_RGB); 
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics(); 
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose(); 
        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1-(softenFactor*4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null); 
        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);        
        // Encodes image as a JPEG data stream
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out); 
        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage); 
        param.setQuality(quality, true); 
        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);
    }
    
    /** 
    * 
    * @param f 图片所在的文件夹路径 
    * @param filelist 图片路径 
    * @param ext 扩展名 
    * @param n 图片名 
    * @param w 目标宽 
    * @param h 目标高 
    * @param per 百分比 
    */
    private static String  Tosmallerpic(String f,File filelist,String ext,String n,int w,int h,float per){ 
        Image src; 
        try { 
            src = javax.imageio.ImageIO.read(filelist); //构造Image对象 
            String result_midname=n.substring(0,n.indexOf("."))+ext+n.substring(n.indexOf("."));
           String img_midname=f+"/"+n.substring(0,n.indexOf("."))+ext+n.substring(n.indexOf(".")); 
           System.out.println(result_midname);
           int old_w=src.getWidth(null); //得到源图宽 
           int old_h=src.getHeight(null); 
           int new_w=0; 
           int new_h=0; //得到源图长 
           if(old_w<=w && old_h<=h)return "";
           double w2=(old_w*1.00)/(w*1.00); 
           double h2=(old_h*1.00)/(h*1.00); 

           //图片跟据长宽留白，成一个正方形图。 
           BufferedImage oldpic; 
           if(old_w>old_h) 
           { 
               oldpic=new BufferedImage(old_w,old_w,BufferedImage.TYPE_INT_RGB); 
           }else{if(old_w<old_h){ 
               oldpic=new BufferedImage(old_h,old_h,BufferedImage.TYPE_INT_RGB); 
           }else{ 
                oldpic=new BufferedImage(old_w,old_h,BufferedImage.TYPE_INT_RGB); 
           } 
           } 
            Graphics2D g = oldpic.createGraphics(); 
            g.setColor(Color.white); 
            if(old_w>old_h) 
            { 
                g.fillRect(0, 0, old_w, old_w); 
                g.drawImage(src, 0, (old_w - old_h) / 2, old_w, old_h, Color.white, null); 
            }else{ 
                if(old_w<old_h){ 
                g.fillRect(0,0,old_h,old_h); 
                g.drawImage(src, (old_h - old_w) / 2, 0, old_w, old_h, Color.white, null); 
                }else{ 
                    //g.fillRect(0,0,old_h,old_h); 
                    g.drawImage(src.getScaledInstance(old_w, old_h,  Image.SCALE_SMOOTH), 0,0,null); 
                } 
            }              
            g.dispose(); 
            src = oldpic; 
            //图片调整为方形结束 
           if(old_w>w) 
           new_w=(int)Math.round(old_w/w2); 
           else 
               new_w=old_w; 
           if(old_h>h) 
           new_h=(int)Math.round(old_h/h2);//计算新图长宽 
           else 
               new_h=old_h; 
           BufferedImage tag = new BufferedImage(new_w,new_h,BufferedImage.TYPE_INT_RGB);        
           //tag.getGraphics().drawImage(src,0,0,new_w,new_h,null); //绘制缩小后的图 
           tag.getGraphics().drawImage(src.getScaledInstance(new_w, new_h,  Image.SCALE_SMOOTH), 0,0,null); 
           FileOutputStream newimage=new FileOutputStream(img_midname); //输出到文件流 
           JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage); 
           JPEGEncodeParam jep=JPEGCodec.getDefaultJPEGEncodeParam(tag); 
            /* 压缩质量 */ 
           jep.setQuality(per, true); 
           encoder.encode(tag, jep); 
           //encoder.encode(tag); //近JPEG编码 
           newimage.close(); 
           return result_midname;
           } catch (IOException ex) { 
        	   ex.printStackTrace();
        }
           return "";
}
    
    
    /** *
     * 把图片印刷到图片上
     * 
     * @param pressImg --
     *            水印文件
     * @param targetImg --
     *            目标文件
     * @param x
     *            --x坐标
     * @param y
     *            --y坐标
     */
    public final static void pressImage(String pressImg, String targetImg,
            int x, int y) {
        try {
            //目标文件
            File _file = new File(targetImg);
            System.out.println("file: "+_file.toString()+">>>>"+targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            //水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                    (height - height_biao) / 2, wideth_biao, height_biao, null);
            //水印文件结束
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /** *//**
     * 打印文字水印图片
     * 
     * @param pressText
     *            --文字
     * @param targetImg --
     *            目标图片
     * @param fontName --
     *            字体名
     * @param fontStyle --
     *            字体样式
     * @param color --
     *            字体颜色
     * @param fontSize --
     *            字体大小
     * @param x --
     *            偏移量
     * @param y
     */
    
    public static void pressText(String pressText, String targetImg,
            String fontName, int fontStyle, int fontSize) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // String s="www.qhd.com.cn";
            g.setColor(Color.RED);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            int x=wideth-wideth/6*4;
            int y=height-height/6;
          
           
            g.drawString(pressText, wideth - fontSize - x, height - fontSize
                    / 2 - y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(image);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    /**
     * 压缩图片 高清
     * @param urlPath 原目标文件的文件夹路径
     * @param targetFile 原目标文件的File
     * @param width 压缩后的宽
     * @param height 压缩后的高
     */
    public static String resizePic(String urlPath,File targetFile,int width,int height){
    	return Tosmallerpic(urlPath,targetFile,"_small",targetFile.getName(),width,height,(float)0.7); 
    }
    
    
    /**
     * 批量压缩图片
     * @param urlPath
     * @param width
     * @param height
     */
    public static void batchPic(String urlPath,int width,int height){
    	 File file=new File(urlPath); 
    	 if(file.exists()){
    		 File[] filelist=file.listFiles(); 
           for(int i=0;i<filelist.length;i++) 
           { 
               String n=filelist[i].getName(); 
               Tosmallerpic(urlPath,filelist[i],"_small",n,width,height,(float)0.7); 
           } 
    	 }
    }
    
    public static void main(String[] args) {
    	String t="E:/data/upload/zfl"; 
    	System.out.println(t.replace("\\", "/"));
    	//ImageSizer.batchPic(t, 400, 400);
    	String f="E:/data/upload/zfl/Koala.jpg";
        File file=new File(f); 
        Tosmallerpic(t,file,"_smaller",file.getName(),310,310,(float)0.7); 
//        if(file.exists()) 
//        { 
//            File[] filelist=file.listFiles(); 
//            for(int i=0;i<filelist.length;i++) 
//            { 
//                String n=filelist[i].getName(); 
//                System.out.println(n);
//                //Tosmallerpic(f,filelist[i],"_middle",n,185,160,(float)0.7); 
//                //Tosmallerpic(f,filelist[i],"_small",n,45,45,(float)0.7); 
//                Tosmallerpic(f,filelist[i],"_smaller",n,310,310,(float)0.7); 
//            } 
//        }
    	//pressImage("D:\\123.gif","D:\\aa.gif", 0, 0);
    	//pressText("刘若英,我很好,后来...","D:\\aa.gif", "宋体", 16, 10, 16, 200, 300);
    	//ImageSizer.pressText("刘若英,后来...",f,"宋体",16, 36);
   }
    
    
    
}
