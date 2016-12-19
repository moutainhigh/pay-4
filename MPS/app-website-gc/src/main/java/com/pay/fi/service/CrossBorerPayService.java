package com.pay.fi.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import com.pay.fi.dto.KfPayTrade;
import com.pay.fileserver.tokenlib.MyOSSException;

public interface CrossBorerPayService {
	/**
	 * 上传文件是否和模版一致性验证
	 * 
	 * @param verify
	 * @param type
	 * @return
	 */
	int fileVerify(ArrayList<ArrayList<String>> verify, String type);

	/**
	 * 上传文件明细验证
	 * 
	 * @param verify
	 * @param type
	 * @return
	 */
	Map<String, Object> fileAnalysis(ArrayList<ArrayList<String>> verify, InputStream successtemplate,
			KfPayTrade kfPayTrade) throws IOException, UnsupportedOperationException, MyOSSException;

	void savePayFileByKfResource(String string, String key, String fullPath, String type,String fileName);

	void savePaySuccessFileByKfResource(String string, String key, String fullPath, String typeName);
}
