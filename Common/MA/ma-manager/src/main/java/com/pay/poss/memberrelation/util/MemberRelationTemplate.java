package com.pay.poss.memberrelation.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**   
* @Title: VouchDataTemplate.java 
* @Package com.pay.poss.memberrelation.util 
* @Description: 关联用户模板对象
* @author cf
* @date 2011-10-11 上午09:37:57 
* @version V1.0   
*/
public class MemberRelationTemplate {
	
	private static final Log LOG = LogFactory.getLog(MemberRelationTemplate.class);
	public static final String TEMPLATE_FILE_NAME = "context/ma/template.xls";
	
	private byte[] value;

	private static MemberRelationTemplate instance;
	
	private MemberRelationTemplate(byte[] value) {
		this.value = value;
	}
	
	static {
		LOG.info("Start");
		
		InputStream in = null;
		try {
			in = MemberRelationTemplate.class
				.getClassLoader().getResourceAsStream(TEMPLATE_FILE_NAME);
			
			int length = in.available();
			if (length == -1) {
				LOG.info("fail to read member relation template file, because file is empty！");
				throw new RuntimeException("fail to read member relation template file, because file is empty！");
			}
			
			byte[] value = new byte[length];
			in.read(value, 0, length);
		
			instance = new MemberRelationTemplate(value);
		} catch (IOException e) {
			LOG.info("fail to read member relation template file", e);
			throw new RuntimeException("fail to read member relation template file", e);
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					LOG.info("fail to shut down member relation template file stream", e);
				}
			}
		}
		
		LOG.info("End");
	}
	
	public static MemberRelationTemplate getInstance() {
		return instance;
	}
	
	public byte[] getValue() {
		return value;
	}
}
