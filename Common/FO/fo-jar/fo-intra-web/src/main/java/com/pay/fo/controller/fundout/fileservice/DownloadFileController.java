/** @Description 
 * @project 	poss-withdraw
 * @file 		DownloadFileController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-8		Henry.Zeng			Create 
 */
package com.pay.fo.controller.fundout.fileservice;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;
import com.pay.fileserver.tokenlib.MyOSSURLParser;
import com.pay.fo.controller.fundout.WithdrawBaseController;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.fileservice.QueryBatchFileService;
import com.pay.poss.base.common.properties.CommonConfiguration;
import com.pay.poss.base.common.properties.MyOSSConfiguration;
import com.pay.poss.base.model.BatchFileInfo;

/**
 * <p>
 * 下载文件Controller
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-8
 * @see
 */
public class DownloadFileController extends WithdrawBaseController {
	
	private QueryBatchFileService queryBatchFileService;
	
	public void setQueryBatchFileService(
			final QueryBatchFileService queryBatchFileService) {
		this.queryBatchFileService = queryBatchFileService;
	}
	
	public ModelAndView downloadFile(HttpServletRequest request,
			HttpServletResponse response) {

		BufferedOutputStream output = null;

		BufferedInputStream input = null;
		try {
			
			Long fileType = Long.valueOf(request.getParameter("fileType"));
			
			String batchNum = request.getParameter("batchNum");
			String channelCode = request.getParameter("channelCode");
			String bussinessType = request.getParameter("bussinessType");
			String bankCode = request.getParameter("bankCode");
			String fileKy = request.getParameter("gFileKy");
			BatchFileInfo fileInfo = queryBatchFileService.downloadCallBackRdtx(batchNum,fileType,channelCode,bankCode,fileKy,bussinessType);
//			FileInfoDTO fileInfo = new FileInfoDTO();
//
//			bind(request, fileInfo, "fileInfo", null);
//			
//			String filePath = CommonConfiguration.getStrProperties("batchFilePath")+File.separator+"withdraw" + fileInfo.getFilePath();
//			
//			fileInfo.setFilePath(filePath);
//
//			log.info("filePath : " + filePath);
			String ossKey=MyOSSConfiguration.getStrProperties("mpsposs.oss.key");
			MyOSS oss = new MyOSS(ossKey);
			String ossRootDir="mpsposs";//MyOSSConfiguration.getStrProperties("mpsposs.oss.rootdir");
			String ossSubDir="withdraw";//MyOSSConfiguration.getStrProperties("mpsposs.oss.withdrawSubdir");
			//MyOSSConfiguration.getStrProperties("mpsposs.oss.url");
			
			JSONObject rawToken;
			InputStream inputStream=null;
			try {
				rawToken = oss.init(ossSubDir,3600*5);
				String url=oss.getFullPath("");
				OSSClient client = oss.getOSSClient();
				String fileURL=fileInfo.getFilePath().replace("\\", "/");
				if(!fileURL.startsWith(ossSubDir)){
					if(!fileURL.startsWith("/")){
						fileURL="/"+fileURL;
					}
					fileURL=ossSubDir+fileURL;
				}
				fileURL=url+ossRootDir+"/"+fileURL;
				OSSObject obj = client.getObject(rawToken.getString("bucket"), new MyOSSURLParser(fileURL).getPathNoLeadingSlash());
				inputStream= obj.getObjectContent();
			
			} catch (UnsupportedOperationException e) {
				logger.error("put file on aliyun oss error:"+e.getMessage());
			} catch (MyOSSException e) {
				logger.error("put file on aliyun oss error:"+e.getMessage());
			} catch (IOException e) {
				logger.error("put file on aliyun oss error:"+e.getMessage());
			}
			
//			File file = new File(URLDecoder.decode(filePath, "UTF-8"));
//			response.reset();
			response.setContentType("application/octet-stream; charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ new String(fileInfo.getFileName().getBytes("UTF-8"),
							"ISO-8859-1"));
			
//			response.setContentLength((int) file.length());

			byte[] buffer = new byte[2048];

			// 写缓冲区：
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(inputStream);
			int n = (-1);
			while ((n = input.read(buffer, 0, 2048)) > -1) {
				output.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			
			/**
			 * 下载失败提示
			 */
			try{
				String msg = "下载失败" ;
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print("<script>alert('"+msg+"');window.history.go(-1);</script>");
			}catch(Exception ex){
				
			}
			
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
				response.setStatus(HttpServletResponse.SC_OK);
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public OperatorlogDTO buildOperatorLog() {
		// TODO Auto-generated method stub
		return null;
	}

}
