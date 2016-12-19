package com.pay.pe.manualbooking.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * 手工记账模板数据封装对象
 */
public class VouchDataTemplate {
	
	private static final Log LOG = LogFactory.getLog(VouchDataTemplate.class);
	public static final String TEMPLATE_FILE_NAME = "context/pe/manualbooking_template.xls";
	
	private byte[] value;

	private static VouchDataTemplate instance;
	
	private VouchDataTemplate(byte[] value) {
		this.value = value;
	}
	
	static {
		LOG.info("Start");
		
		InputStream in = null;
		try {
			in = VouchDataTemplate.class
				.getClassLoader().getResourceAsStream(TEMPLATE_FILE_NAME);
			
			int length = in.available();
			if (length == -1) {
				LOG.debug("fail to read vouch template file, because file is empty！");
				throw new RuntimeException("fail to read vouch template file, because file is empty！");
			}
			
			byte[] value = new byte[length];
			in.read(value, 0, length);
		
			instance = new VouchDataTemplate(value);
		} catch (IOException e) {
			LOG.debug("fail to read vouch template file", e);
			throw new RuntimeException("fail to read vouch template file", e);
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					LOG.debug("fail to shut down vouch template file stream", e);
				}
			}
		}
		
		LOG.info("End");
	}
	
	public static VouchDataTemplate getInstance() {
		return instance;
	}
	
	public byte[] getValue() {
		return value;
	}
}
