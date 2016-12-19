package com.pay.acc.identityverify;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.pay.acc.common.utils.IdentityVerifyUtil;
import com.pay.acc.message.MessageService;
import com.pay.acc.message.Server;
import com.pay.util.MapUtil;

/**
 * 实名认证服务
 */
@Service
public class IdentityVerifyServiceImpl implements IdentityVerifyService {

	private Log logger = LogFactory.getLog(getClass());
	
	@Resource
	private MessageService messageService;
	@Resource
	private Server server;
	
	@Value("${idcard.verify.merchantNo}")
	private String userId; // 合作商编号
	@Value("${idcard.verify.desKey}")
	private String desKey; // des密钥
	@Value("${idcard.verify.md5Key}")
	private String md5Key; // md5密钥
	
	private final static String RESULT_TAG_NAME = "auResultCode"; // 验证结果标签名
	private final static String PATTERN = "yyyy-MM-dd HH:mm:ss"; // 日期格式
	private final static String ENCODING = "UTF-8"; // 姓名url编码方式
	private final static String SUCCESS = "SUCCESS"; // 验证成功标志
	
	@Override
	public String invoke(Map<String, String> params) {
		if (logger.isDebugEnabled()) {
			logger.debug("[Http]调用实名认证接口开始");
		}
		
		String response = null;
		try {
			String reqParams = MapUtil.map2string(params);
			// 发送消息
			response = messageService.sendMessage(server, reqParams);
		} catch (Exception e) {
			logger.error("[Http]调用实名认证接口失败，", e);
			throw new RuntimeException(e);
		}finally{
			if (logger.isDebugEnabled()) {
				logger.debug("[Http]调用实名认证接口结束");
			}
		}
		return response;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String handleResponse(String response) {
		if(response == null || response.trim().length() == 0){
			if(logger.isInfoEnabled()){
				logger.info("未接收到响应报文");
			}
			return null;
		}
		if(logger.isInfoEnabled()){
			logger.info("解析响应xml报文开始");
		}
		
		String result = null;
		try {
			Document doc = DocumentHelper.parseText(response); // 将字符串转为XML文档
			Element root = doc.getRootElement(); // 获取根节点
//			String rootName = root.getName();
//			System.out.println("root:" + rootName);
			List<Element> list = root.elements();
			if (list != null && list.size() > 0) {
				for (Element element : list) {
					String name = element.getName(); // 获取标签名
					String text = element.getText(); // 获取标签值
					if(RESULT_TAG_NAME.equals(name)){
						result = text;
					}
					if (logger.isInfoEnabled()) {
						logger.info(name + ":" + text);
					}
				}
			}
		} catch (DocumentException e) {
			logger.error("解析响应xml报文异常：" + e);
			throw new RuntimeException(e);
		} finally {
			if(logger.isInfoEnabled()){
				logger.info("解析响应xml报文结束");
			}
		}
		return result;
	}
	
	@Override
	public Map<String, String> generateReqParams(String name, String cardNo){
		Map<String, String> params = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat format = new SimpleDateFormat(PATTERN);
		
		String coopOrderNo = String.valueOf(System.currentTimeMillis()); // 合作商订单号
		
		String encName = null; // 使用UTF-8编码之后的姓名
		try {
			encName = URLEncoder.encode(name, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String auName = IdentityVerifyUtil.encode(encName, desKey); // 待验证的用户真实姓名
		
		String auId = IdentityVerifyUtil.encode(cardNo, desKey); // 待验证的用户真实证件号
		
		String reqDate = format.format(new Date()); // 日期 [yyyy-MM-dd HH:mm:ss]
		String ts = coopOrderNo; // 系统时间戳
		
		//拼接待加密字符串
		sb.append("userId").append(userId);
		sb.append("coopOrderNo").append(coopOrderNo);
		sb.append("auName").append(encName);
		sb.append("auId").append(cardNo);
		sb.append("ts").append(ts);
		sb.append(md5Key);
		String sign = IdentityVerifyUtil.md5Sign(sb.toString()); // 签名字符串
		
		params.put("userId", userId);
		params.put("coopOrderNo", coopOrderNo);
		params.put("auName", auName);
		params.put("auId", auId);
		params.put("reqDate", reqDate);
		params.put("ts", ts);
		params.put("sign", sign);
		
		return params;
	}

	@Override
	public boolean validate(String name, String cardNo) {
		if(StringUtils.isBlank(name) || StringUtils.isBlank(cardNo)){
			return false;
		}
		Map<String, String> params = generateReqParams(name, cardNo);
		String response = invoke(params);
		String result = handleResponse(response);
		return SUCCESS.equals(result); // 验证结果 SUCCESS/FAILED
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	public String getMd5Key() {
		return md5Key;
	}

	public void setMd5Key(String md5Key) {
		this.md5Key = md5Key;
	}
}
