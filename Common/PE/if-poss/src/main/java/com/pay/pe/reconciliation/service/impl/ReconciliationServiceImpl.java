package com.pay.pe.reconciliation.service.impl;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.reconciliation.service.ReconciliationService;
import com.pay.pe.reconciliation.util.FTPUtils;
import com.pay.pe.reconciliation.util.ZipUtil;

public class ReconciliationServiceImpl implements ReconciliationService {

	private Log log = LogFactory.getLog(this.getClass());
	//private MpayLoadServiceApi mpayLoadServiceApi;
	private String server;
	private String user;
	private String password;
	private String remotePath;
	private String localPath;
	private String targetPath;



	@Override
	public String generateLocalFilePath(String fileName) {
		try {
			boolean resultSuccess =FTPUtils.getFtpUtils().download(server, user, password, fileName, remotePath, localPath);
			if(resultSuccess){
				if(ZipUtil.getZipUtil().unZipFile(localPath+"/"+fileName, targetPath)){					
					return targetPath+fileName.substring(0,fileName.indexOf("."))+".txt";
				}
			}		
		} catch (IOException e) {
			log.error(e);
		}		
		return null;
	}
	
	public static void main(String[] args){
		String fileName="dz_mobile_20120329160228.zip";
		System.out.println(fileName.substring(0,fileName.indexOf(".")));
		
	}

//	@Override
//	public String getFileNameByDate(String startDate, String endDate) {
//		MpayOrderDTO mpayOrderDTO=new MpayOrderDTO();
//		mpayOrderDTO.setTradeType("87");
//		mpayOrderDTO.setOrderId(startDate);
//		mpayOrderDTO.setCardNo(endDate);
//		mpayOrderDTO.setTradeDate(startDate);
//		try {
//			mpayOrderDTO =mpayLoadServiceApi.queryMpayInfo(mpayOrderDTO);
//			if(null!=mpayOrderDTO){
//				if("00".equalsIgnoreCase(mpayOrderDTO.getReturnCode())){
//					return mpayOrderDTO.getReconciliationfileName();
//				}
//			}
//		} catch (BusinessException e) {
//			log.error(e);
//		}
//		return null;
//	}
	

//	public void setMpayLoadServiceApi(MpayLoadServiceApi mpayLoadServiceApi) {
//		this.mpayLoadServiceApi = mpayLoadServiceApi;
//	}

	public void setServer(String server) {
		this.server = server;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setRemotePath(String remotePath) {
		this.remotePath = remotePath;
	}


	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	
}
