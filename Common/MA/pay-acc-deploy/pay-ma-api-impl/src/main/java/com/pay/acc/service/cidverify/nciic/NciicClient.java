/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.service.cidverify.nciic;

import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.cidverify.INciicClient;
import com.pay.acc.service.cidverify.nciic.cxf.NciicServices;
import com.pay.acc.service.cidverify.nciic.cxf.NciicServicesPortType;

/**
 * 公安实名认证基础服务类
 * 使用cxf 本地调用
 * @author fjl
 * @date 2011-4-29
 */
public class NciicClient implements INciicClient {
	
	private String fileUrl = "/opt/pay/config/app/license.txt";
	
	private final Log log = LogFactory.getLog(NciicClient.class);
	
	@Override
	public String executeCidVerify(String condition) throws Exception {
		
		NciicServices ns = new NciicServices();
        NciicServicesPortType port = ns.getNciicServicesHttpPort(); 

        String inLicense = readFile();
        String res = port.nciicCheck(inLicense, condition);
        
        if(log.isInfoEnabled()){
        	log.info("nciicCheck.result=" + res);
        }
		return res;
	}
	
	private String readFile(){
    	try {
			BufferedReader reader = new BufferedReader(new FileReader(fileUrl));
			String txt = reader.readLine();
			return txt;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }


	/**
	 * @return the fileUrl
	 */
	public String getFileUrl() {
		return fileUrl;
	}
	
	/**
	 * @param fileUrl the fileUrl to set
	 */
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

}
