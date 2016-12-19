
package com.pay.pe.reconciliation.util;

/**
 *  File: ContinueFTP.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-26   liwei     Create
 *
 */


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**	
 *  @author lIWEI
 *  @Date 2011-9-26
 *  @Description 支持断点续传的FTP实用类
 *  @version 0.1 实现基本断点上传下载  
 *  @version 0.2 实现上传下载进度汇报  
 *  @version 0.3 实现中文目录创建及中文文件创建，添加对于中文的支持
 */
public class FTPUtils {
	private static final Log log = LogFactory.getLog(FTPUtils.class);
	
	private static  final FTPUtils ftpUtils = new FTPUtils();
	
	public static int Remote_File_Noexist=1;
	
    private static FTPClient ftpClient;   
      
    public static FTPUtils getFtpUtils(){
    	ftpClient = new FTPClient();
    	return ftpUtils;
    }
    
    private FTPUtils(){ 
        //设置将过程中使用到的命令输出到控制台   
        //this.ftpClient.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));   
    }   
       
    /** *//**  
     * 连接到FTP服务器  
     * @param hostname 主机名  
     * @param port 端口  
     * @param username 用户名  
     * @param password 密码  
     * @return 是否连接成功  
     * @throws IOException  
     */  
    public boolean connect(String hostname,int port,String username,String password) throws IOException{   
        ftpClient.connect(hostname, port);   
        ftpClient.setControlEncoding("GBK");   
        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){   
            if(ftpClient.login(username, password)){   
                return true;   
            }   
        }   
        disconnect();   
        return false;   
    }   
       
    /** *//**  
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报  
     * @param remote 远程文件路径  
     * @param local 本地文件路径  
     * @return 上传的状态  
     * @throws IOException  
     */  
    private String download(String remote,String local) throws IOException{   
        //设置被动模式   
        ftpClient.enterLocalPassiveMode();   
        //设置以二进制方式传输   
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
        String result;   
           
        //检查远程文件是否存在   
        FTPFile[] files = ftpClient.listFiles(new String(remote.getBytes("GBK"),"iso-8859-1"));   
        if(files.length != 1){   
            log.info("远程文件不存在");   
            return "DownloadStatus.Remote_File_Noexist";   
        }   
           
        long lRemoteSize = files[0].getSize();  
        File f = new File(local);   
        //本地存在文件，进行断点下载   
        if(f.exists()){   
            long localSize = f.length();   
            //判断本地文件大小是否大于远程文件大小   
            if(localSize >= lRemoteSize){   
            	log.info("本地文件大于远程文件，下载中止");   
                return "DownloadStatus.Local_Bigger_Remote";   
            }   
            //进行断点续传，并记录状态   
            FileOutputStream out = new FileOutputStream(f,true);   
            ftpClient.setRestartOffset(localSize);   
            InputStream in = ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
            byte[] bytes = new byte[1024];   
            long step = lRemoteSize /100;   
            long process=localSize /step;   
            int c;   
            while((c = in.read(bytes))!= -1){   
                out.write(bytes,0,c);   
                localSize+=c;   
                long nowProcess = localSize /step;   
                if(nowProcess > process){   
                    process = nowProcess;   
                    //if(process % 10 == 0)  //更新文件下载进度,值存放在process变量中 
                        //System.out.println("下载进度："+process);   
                }   
            }   
            in.close();   
            out.close();   
            boolean isDo = ftpClient.completePendingCommand();   
            if(isDo){   
                result = "DownloadStatus.Download_From_Break_Success";   
            }else {   
                result = "DownloadStatus.Download_From_Break_Failed";   
            }   
        }else {   
            OutputStream out = new FileOutputStream(f);   
            InputStream in= ftpClient.retrieveFileStream(new String(remote.getBytes("GBK"),"iso-8859-1"));   
            byte[] bytes = new byte[1024];   
            long step = lRemoteSize /100;   
            long process=0;   
            long localSize = 0L;   
            int c;   
            while((c = in.read(bytes))!= -1){   
                out.write(bytes, 0, c);   
                localSize+=c;   
                long nowProcess = localSize /step;   
                if(nowProcess > process){   
                    process = nowProcess;   
                    //if(process % 10 == 0)   //更新文件下载进度,值存放在process变量中
                        //System.out.println("下载进度："+process);   
                }   
            }   
            in.close();   
            out.close();   
            boolean upNewStatus = ftpClient.completePendingCommand();   
            if(upNewStatus){   
                result = "DownloadStatus.Download_New_Success";   
            }else {   
                result = "DownloadStatus.Download_New_Failed";   
            }   
        }   
        return result;   
    }   
    
    
	/**
	 * FTP上传文件
	 * @param remoteFileFullPath 远程文件全路径（包含文件名）
	 * @param localFileFullPath  本地文件全路径（包含文件名）
	 * @param isSupportedAppend  是否支持续传
	 * @return
	 */
	public boolean uploadFile(String remoteFileFullPath, String localFileFullPath,boolean isSupportedAppend) {
		boolean success = false;

		try {

			// 设置PassiveMode传输
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制流的方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.setControlEncoding("GBK");
			String path = remoteFileFullPath.substring(0,
					remoteFileFullPath.lastIndexOf("/"));
			String fileName = remoteFileFullPath.substring(remoteFileFullPath
					.lastIndexOf("/") + 1);
			File localFile = new File(localFileFullPath);
			if (!ftpClient.changeWorkingDirectory(path)) {
				String[] paths = path.split("/");
				if (null == paths[0] || "".equals(paths[0])) {
					paths[0] = "/";
				}
				String tmp = null;
				for (int i = 0; i < paths.length; i++) {
					tmp = paths[i];
					if (null == tmp || "".equals(tmp)) {
						continue;
					}

					if (!ftpClient.changeWorkingDirectory(tmp)) {
						ftpClient.makeDirectory(tmp);
						ftpClient.changeWorkingDirectory(tmp);
					}
				}
			}
			//续传逻辑
			if(isSupportedAppend){
				FTPFile[] files = ftpClient.listFiles(new String(fileName
						.getBytes("GBK"), "iso-8859-1"));
				if (files.length == 1) {
					long remoteSize = files[0].getSize();
					long localSize = localFile.length();
	
					if (remoteSize == localSize) {
						log.info("File_Exits");
						
					} else if (remoteSize > localSize) {
						log.info("remoteFile > localFile");
					}else{
						success = uploadFile(fileName, localFile, remoteSize);
					}
				}else{
					success = uploadFile(fileName, localFile, 0);
				}
			}else{
				success = uploadFile(fileName, localFile, 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return success;
	}

	/**
	 * FTP上传文件
	 * @param remoteFile 远程文件名称
	 * @param localFile  本地文件
	 * @param remoteSize 已上传文件大小
	 * @return
	 */
	public boolean uploadFile(String remoteFile, File localFile, long remoteSize) {
		RandomAccessFile raf = null;
		OutputStream out = null;
		boolean result = false;
		long localFileSize = localFile.length();
		try {
			long localreadbytes = 0L;
			raf = new RandomAccessFile(localFile, "r");
			// 断点续传
			if (remoteSize > 0) {
				out = ftpClient.appendFileStream(new String(remoteFile.getBytes("GBK"), "iso-8859-1"));
				ftpClient.setRestartOffset(remoteSize);
				raf.seek(remoteSize);
				localreadbytes = remoteSize;
			}else{
				out = ftpClient.storeFileStream(new String(remoteFile.getBytes("GBK"), "iso-8859-1"));
			}
			byte[] bytes = new byte[1024];
			int c;
			while ((c = raf.read(bytes)) != -1) {
				out.write(bytes, 0, c);
				localreadbytes += c;
				log.info("上传文件大小:" + localFileSize + "(byte),已上传:"
						+ localreadbytes + "(byte)"); // 汇报上传状态
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
				raf = null;
			}
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				out = null;
			}
			try {
				result = ftpClient.completePendingCommand();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;

	}

    
    public boolean upload(String server,String user,String password,
    		String local,String remote){
		boolean isConnectSuccess = false;
        try {
			isConnectSuccess = connect(server, 21, user, password);
		} catch (Throwable e) {
			log.error("连接ftp服务器异常：：："+e.getMessage()+"   "+"  "+server +"  "+user);
		} 
		try{
			String filePath = "";
			if(isConnectSuccess){
				File file = new File(local);
				if(file.isDirectory()){
					File[] files = file.listFiles();
					for(File f : files){
						if(f.isFile()){
							filePath  = local+"/"+f.getName();
							remote = remote+"/"+f.getName();
							isConnectSuccess = uploadFile(remote,filePath,false);
						}
					}
					return isConnectSuccess;
				}else{
					return uploadFile( remote+"/"+file.getName(),local,false);
				}
			}
		}catch (Throwable e) {
			e.printStackTrace();
		}finally{
			try {
				disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
    }
    /** *//**  
     * 断开与远程服务器的连接  
     * @throws IOException  
     */  
    public void disconnect() throws IOException{   
        if(ftpClient.isConnected()){   
            ftpClient.disconnect();   
        }   
    }   
       
    /** *//**  
     * 递归创建远程服务器目录  
     * @param remote 远程服务器文件绝对路径  
     * @param ftpClient FTPClient 对象  
     * @return 目录创建是否成功  
     * @throws IOException  
     */  
    public String createDirecroty(String remote,FTPClient ftpClient) throws IOException{   
        String status = "UploadStatus.Create_Directory_Success";   
        String directory = remote.substring(0,remote.lastIndexOf("/")+1);   
        if(!directory.equalsIgnoreCase("/")&&!ftpClient.changeWorkingDirectory(new String(directory.getBytes("GBK"),"iso-8859-1"))){   
            //如果远程目录不存在，则递归创建远程服务器目录   
            int start=0;   
            int end = 0;   
            if(directory.startsWith("/")){   
                start = 1;   
            }else{   
                start = 0;   
            }   
            end = directory.indexOf("/",start);   
            while(true){   
                String subDirectory = new String(remote.substring(start,end).getBytes("GBK"),"iso-8859-1");   
                if(!ftpClient.changeWorkingDirectory(subDirectory)){   
                    if(ftpClient.makeDirectory(subDirectory)){   
                        ftpClient.changeWorkingDirectory(subDirectory);   
                    }else {   
                        System.out.println("创建目录失败");   
                        return "UploadStatus.Create_Directory_Fail";   
                    }   
                }   
                start = end + 1;   
                end = directory.indexOf("/",start);   
                   
                //检查所有目录是否创建完毕   
                if(end <= start){   
                    break;   
                }   
            }   
        }   
        return status;   
    }   
       
       
    public static void main(String[] args) {   
        try {   
            //myFtp.connect("192.168.24.100", 21, "ftp", "ftp");   
        	//myFtp.connect("10.2.169.201", 21, "pay_test", "pay.com@123456");
//          myFtp.ftpClient.makeDirectory(new String(" 电视剧".getBytes("GBK"),"iso-8859-1"));   
//          myFtp.ftpClient.changeWorkingDirectory(new String(" 电视剧".getBytes("GBK"),"iso-8859-1"));   
//          myFtp.ftpClient.makeDirectory(new String(" 走西口".getBytes("GBK"),"iso-8859-1"));   
//          System.out.println(myFtp.upload("http://www.5a520.cn /yw.flv", "/yw.flv",5));   
//          System.out.println(myFtp.upload("http://www.5a520.cn /走西口24.mp4","/央视走西口/新浪网/走西口 24.mp4"));   
            //System.out.println(myFtp.download("/ 央视走西口/新浪网/走西口24.mp4", "f:\\ftp\\走西口242.mp4")); 
            //System.out.println(myFtp.download("/2010/07/23/09/ZHPEP0000000010320100723093851932_沪EM9902_02_A_hptp.jpg", "f:\\ftp1\\ZHPEP0000000010320100723093851932_沪EM9902_02_A_hptp.jpg"));   
        	//System.out.println(myFtp.download("/home/pay_test/liwei/111.txt", "f:\\fromftp\\111.txt")); 
        	//System.out.println(myFtp.download("/home/pay_test/liwei", "f:\\fromftp",null)); 
            //myFtp.disconnect();   
//        	System.out.println(FTPUtils.getFtpUtils().existsFile("10.2.169.201", "pay_test", "pay.com@123456", "/home/pay_test/B_ABC01_2012030118102027.txt"));
        	FTPUtils.getFtpUtils().download("10.2.169.220", "oracle", "zxcv&(_MNB#_1234(%)", "dz_mobile_20120329160228.zip", "/home/oracle/tocenter/data/mobile_dz_file", "/opt");
        } catch (Exception e) {   
        	e.printStackTrace();
            System.out.println("连接FTP出错："+e.getMessage());   
        }   
    } 
    
    public boolean existsFile(String server,String user,String password,String remotePath){
    	boolean isConnectSuccess = true;
    	try {
			isConnectSuccess = connect(server, 21, user, password);
		} catch (Throwable e) {
			log.error("连接ftp服务器异常：：："+e.getMessage()+"   "+"  "+server +"  "+user);
			isConnectSuccess = false;
		} 
		
		if(isConnectSuccess){
			try {
				//设置被动模式   
		        ftpClient.enterLocalPassiveMode();   
		        //设置以二进制方式传输   
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
		           
		        FTPFile[] files = ftpClient.listFiles(new String(remotePath.getBytes("GBK"),"iso-8859-1"));   
		        if(files.length == 0){   
		            log.info("远程文件不存在");   
		            isConnectSuccess = false;   
		        } 
				log.info("loadOver:::"+remotePath+" "+server );
			} catch (Throwable e) {
				log.error("下载失败：：："+e.getMessage());
			}   
            try {
            	disconnect();
			} catch (Throwable e) {
				log.error("关闭ftp服务器异常：：："+server);
			}   
		}
		return isConnectSuccess;
    }
    
    /** *//**  liwei 
     * 从FTP服务器上下载文件,支持断点续传，上传百分比汇报  
     * @param remotePath 远程文件路径  
     * @param localPath 本地文件路径  
     * @param datePath 本地文件路径  /2011/09/26/
     * @return 上传的状态  
     * @throws IOException  
     */  
    public boolean download(String server,String user,String password,
    		String fileName,String remotePath,String localPath) throws IOException{  
    	boolean resultSuccess = false;
    	String resultString="";
		boolean isConnectSuccess = false;
        try {
			isConnectSuccess = connect(server, 21, user, password);
		} catch (IOException e) {
			log.error("连接ftp服务器异常：：："+e.getMessage()+"   "+"  "+server +"  "+user);
		} 
		if(isConnectSuccess){
			try {
				//设置被动模式   
		        ftpClient.enterLocalPassiveMode();   
		        //设置以二进制方式传输   
		        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);   
		           
		        //检查本地文件是否存在   
		        File file = new File(localPath);
		        
		        if(!file.exists()){
					boolean issuccess = file.mkdirs();
					if (issuccess) {
						log.info("目录创建成功：：："+localPath);
					}
		        }
		        FTPFile[] files = ftpClient.listFiles(new String(remotePath.getBytes("GBK"),"iso-8859-1"));   
		        if(files.length == 0){   
		            log.info("远程文件不存在");   
		            return false;   
		        } 
		        
		        	resultString = download(remotePath+"/"+fileName,localPath+"/"+fileName);
		        	if ("DownloadStatus.Local_Bigger_Remote".equals(resultString)
		        			|| "DownloadStatus.Download_New_Success".equals(resultString)) {
	        			resultSuccess = true;
	        		}
//		        } 
				log.info("loadOver:::"+remotePath+" "+server +resultString);
			} catch (IOException e) {
				log.error("下载失败：：："+e.getMessage());
			}   
            try {
            	disconnect();
			} catch (IOException e) {
				log.error("关闭ftp服务器异常：：："+server);
			}   
		}
		return resultSuccess;
	}
}
