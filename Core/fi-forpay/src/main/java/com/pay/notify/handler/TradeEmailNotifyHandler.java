/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.notify.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.sender.JmsSender;
import com.pay.notify.model.NotifyTemplate;
import com.pay.notify.model.OrderEmailNotifyCriteria;
import com.pay.notify.service.NotifyTemplateService;
import com.pay.poss.base.common.constants.SendMailConstants;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * @author Davis.guo at 2016-09-02
 */
public class TradeEmailNotifyHandler implements EventHandler {

	private static final Log logger = LogFactory.getLog(TradeEmailNotifyHandler.class) ;
	//注入
	private JmsSender jmsSender;
	private NotifyTemplateService notifyTemplateService;
	private HessianInvokeService orderCenterInvokeService;
	
	/**
	 * 获取开通邮件通知的商户信息
	 * 
	 * @param paraMap
	 * @return
	 */
	private Map orderEmailNotifyQuery(Map<String, Object> paraMap) {
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.NOTICE.getCode());//GATEWAY
		String result = orderCenterInvokeService.invoke(
				SerCode.TXNCORE_ORDER_EMAIL_NOTIFY_QUERY.getCode(), sysTraceNo,
				SystemCodeEnum.NOTICE.getCode(),//GATEWAY
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}

	/**
	 * 查询网关订单详情
	 * 
	 * @param paraMap
	 * @return
	 */
	private List<Map> queryTradeOrderDetail(String partnerId, String maxTradeOrderNo) {


		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("partnerId", partnerId);//根据会员号查询
		//TODO 要不要过滤支付状态？
		paraMap.put("tradeOrderNo", maxTradeOrderNo);
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.NOTICE.getCode());
		String result = orderCenterInvokeService.invoke(
				SerCode.TXNCORE_TRADE_PAYMENT_ORDER_DETAIL.getCode(), sysTraceNo,
				SystemCodeEnum.NOTICE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		if(resultMap.get("result") != null)
		{
			return (List<Map>) resultMap.get("result");
		}
		
		return null;
	}
	
	/***
	 * 商户邮件通知配置数据修改
	 * @param paraMap
	 * @return
	 */
	private Map orderEmailNotifyUpdate(Map<String, Object> paraMap) {
		
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.NOTICE.getCode());
		String result = orderCenterInvokeService.invoke(
				SerCode.TXNCORE_ORDER_EMAIL_NOTIFY_UPDATE.getCode(), sysTraceNo,
				SystemCodeEnum.NOTICE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		
		return resultMap;
	}
	
	/**
	 * 修改开通状态
	 * @param operateStatus
	 * @return
	 */
	public Map updateStatus(int operateStatus){
		Map paraMap = new HashMap<String, Object>() ;
		Map resultMap = null;
		try{
			paraMap.put("operateStatus", operateStatus) ;
			paraMap.put("type", "status") ;//只更新操作状态
			resultMap = orderEmailNotifyUpdate(paraMap) ;
		}catch(Throwable t){logger.error("@@@TradeEmailNotifyHandler.updateStatus", t);}
		return resultMap ;
	}

	/**
	 * 修改最大网关订单号
	 * @param id
	 * @param maxTradeOrderNo
	 * @return
	 */
	public Map updateMaxOrderNo(String id, String maxTradeOrderNo){
		Map paraMap = new HashMap<String, Object>() ;
		Map resultMap = null;
		try{
			paraMap.put("id", id) ;
			paraMap.put("maxTradeOrderNo", maxTradeOrderNo) ;
			paraMap.put("type", "maxno") ;//只更新最大网关订单号
			resultMap = orderEmailNotifyUpdate(paraMap) ;
		}catch(Throwable t){logger.error("@@@TradeEmailNotifyHandler.updateMaxOrderNo", t);}
		return resultMap ;
	}

	/**
	 * 查找已经开通邮件通知的记录
	 * @param operateStatus
	 * @return
	 */
	public List<Map> queryNotifyEmail(){
		Map paraMap = new HashMap<String, Object>() ;
		Map resultMap = null;
		List<Map> listMap = new ArrayList<Map>();
		try{
			OrderEmailNotifyCriteria oenCriteria = new OrderEmailNotifyCriteria();
			oenCriteria.setOpenFlag(1);//已经开通邮件通知
			oenCriteria.setOperateStatus(0);//已经执行完全
			paraMap = new HashMap<String, Object>() ;
			paraMap.put("orderEmailNotifyCriteria", oenCriteria);
			paraMap.put("type", "all") ;
			resultMap = orderEmailNotifyQuery(paraMap);//获取开通邮件通知的商户信息
			listMap = (List<Map>) resultMap.get("result") ;
		}catch(Throwable t){logger.error("@@@TradeEmailNotifyHandler.query", t);}
		return listMap ;
	}
	
	private String preMaxTradeOrderNo = "0";//上一次发送的网关订单号
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			//1、获取开通邮件通知的商户信息select * from ORDER_EMAIL_NOTIFY where OPEN_FLAG=1 and OPERATE_STATUS=0
			List<Map> listMap = queryNotifyEmail() ;
			if(CollectionUtils.isNotEmpty(listMap))
			{
				logger.info("###获取开通邮件通知的商户信息完成!");
				//2、上锁：修改操作状态为1（正在执行），避免并发操作。upeate ORDER_EMAIL_NOTIFY set OPERATE_STATUS=1 where OPEN_FLAG = 1
				updateStatus(1);
				logger.info("#################################上锁完成，避免并发操作,修改操作状态为正在进行中...");
				String id = "";
				String partnerId = "";
				try{
					for(Map oen : listMap)
					{
						List<Map> tradeDetailMap = null;
						id = oen.get("id").toString();
						partnerId = oen.get("memberCode").toString();//会员号
						String maxTradeOrderNo = oen.get("maxTradeOrderNo").toString();//当前最大网关订单号
						if(!StringUtil.isEmpty(partnerId))
						{
							if(maxTradeOrderNo==null || maxTradeOrderNo.length()<19 || maxTradeOrderNo.indexOf("102")<0)
							{
								continue;//无效网关订单跳过不处理
							}
							//3、获取订单数据where partner_id = ? and TRADE_ORDER_NO > ?
							tradeDetailMap = queryTradeOrderDetail(partnerId, maxTradeOrderNo);
							if(tradeDetailMap != null && tradeDetailMap.size()>0)
							{
								logger.info("###获取订单数据完成!");
								Map<String,String> contextMap = genBodyContent(tradeDetailMap);//4、生成邮件数据
								String maxOrderNo = "0";
								String emailAddress = "";
								if(contextMap.get("maxTradeOrderNo") != null)
									maxOrderNo = contextMap.get("maxTradeOrderNo").toString();
								if(!"0".equalsIgnoreCase(maxOrderNo) && !preMaxTradeOrderNo.equalsIgnoreCase(maxOrderNo))
								{
									logger.info("###生成邮件数据完成!");
									if(oen.get("emailNotify") != null)
										emailAddress = oen.get("emailNotify").toString();
									else if(oen.get("emailCompany") != null)
										emailAddress = oen.get("emailCompany").toString();
									//5、调用邮件进行发送 //TODO 怎么判断邮件是否发送成功？
									sendMail(emailAddress, contextMap.get("websiteName"), contextMap.get("bodyContent"));
									logger.info("###邮件已经发送!");
									//更新最大网关订单
									updateMaxOrderNo(id, maxOrderNo);
									logger.info("###更新最大网关订单完成!maxOrderNo="+maxOrderNo);
									preMaxTradeOrderNo = maxOrderNo;
								}
							}
						}
					}
				}catch(Exception e){e.printStackTrace();}

			}

			updateStatus(0);//5、解锁：修改通知状态为开启
			logger.info("#################################解锁，修改操作状态为已经完成!");
			
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode()) ;
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc()) ;
		} catch (Exception e) {
			logger.error("query fiExceptionCardDetailMsg error：" + e);
			e.printStackTrace();
		}
		return JSonUtil.toJSonString(resultMap);
	}

	// 企业会员密码重置发送邮件
	private void sendMail(String emailAddress, String websiteName, String bodyContent) {
		logger.info("###TradeEmailNotifyHandler.setnMail==>emailAddress="+emailAddress);
		logger.info("###TradeEmailNotifyHandler.sendMail==>websiteName="+websiteName);
		logger.info("###TradeEmailNotifyHandler.sendMail==>bodyContent="+bodyContent);
		long id = Long.valueOf(SendMailConstants.ORDER_EMAIL_NOTIFY_TEMPID);
		NotifyTemplate notifyTemplate = notifyTemplateService.findTemplateById(id);//根据ID查询邮件模板数据
		if(notifyTemplate == null)
		{
			notifyTemplate = new NotifyTemplate();
			notifyTemplate.setId(id);
			notifyTemplate.setSubject("订单邮件通知");
			notifyTemplate.setContent(getTemplate());//请根据id值在数据库INF.notify_template表中修改模板内容 TODO
			notifyTemplate.setMemo("订单邮件通知模板");
			notifyTemplate.setStatus("1");
			notifyTemplate.setBusType("emailnotify_1");//如果邮件发送成功过后，邮件服务器将会根据此值来缓存这个模板，如果模板数据有变更，一定要在数据表中修改这个值。//TODO
			notifyTemplate.setPartnerId("0");//商户号，默认为0
			notifyTemplate.setTempType("email");//这个值只能填"email"因为if-notify-server程序中写死的。
			notifyTemplate.setParams("websiteName,bodyContent");//模板内容中的“$$data.替换变量$$”发生改变时，请修改此处内容替换变量。// 参数和列名相同 格式为：$$data.列名$$
			notifyTemplateService.saveNotifyTemplate(notifyTemplate);
			logger.info("###TradeEmailNotifyHandler.sendMail==>保存模板记录成功！");
		}
		logger.info("###TradeEmailNotifyHandler.sendMail==>id="+notifyTemplate.getId());
		logger.info("###TradeEmailNotifyHandler.busType==>busType="+notifyTemplate.getBusType());
		logger.info("###TradeEmailNotifyHandler.sendMail==>partnerId="+notifyTemplate.getPartnerId());
		logger.info("###TradeEmailNotifyHandler.sendMail==>tempType="+notifyTemplate.getTempType());
		logger.info("###TradeEmailNotifyHandler.sendMail==>params="+notifyTemplate.getParams());
		List<String> recAddress = new ArrayList<String>();
		recAddress.add(emailAddress);
		Map<String, String> data = new HashMap<String, String>();
		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setFromAddress(SendMailConstants.PASSWORDRESET_ENTERPRISE_EMAIL_SENDER);// 默认邮件发送者 FIXME 这个值要改吗？
		request.setRecAddress(recAddress);// 收件人列表
		request.setBizCode(notifyTemplate.getBusType());
		request.setSubject(notifyTemplate.getSubject());// 邮件主题
		request.setMerchantCode(notifyTemplate.getPartnerId());
		request.setType(RequestType.EMAIL);
		request.setTemplateId(notifyTemplate.getId());// INF.notify_template表中的模板ID，一定要跟这个值一样。 XXX
		data.put("websiteName", websiteName);//网站
		data.put("bodyContent", bodyContent);// 订单内容
		request.setData(data);
		jmsSender.send(request);
		// return checkCode;
	}
	
	//邮件模板，可以将模板样式调好后，直接更新到模板数据表中
	private String getTemplate()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE HTML>");
		sb.append("<html lang='en-US'>");
		sb.append("<head>");
		sb.append("<meta charset='UTF-8'>");
		sb.append("<title>订单通知</title>");
		sb.append("<style type='text/css'>");
		sb.append("*{margin:0; padding:0; font-size:12px; line-height:2;}");
		sb.append("a { text-decoration:none;}");
		sb.append("a:hover{ text-decoration:underline; color:#b10000}");
		sb.append("#content { padding:20px 20px;}");
		sb.append("#content p { font-size:14px; color:#666666;}");
		sb.append("#content p a { color:#000000;}");
		sb.append("#content p a:hover { color:#b10000;}");
		sb.append("#content strong { font-size:14px; }");
		sb.append("#footer{ margin:0 30px; padding:10px 30px 0; border-top:1px dotted #8e8e8e; color:#999999;}");
		sb.append("#footer a { color:#999999;}");
		sb.append("#footer a.obvious { color:#333333 !important;}");
		sb.append("td{border:solid #add9c0; border-width:1px 1px 1px 1px; padding-left:10px;}");
		sb.append("table{border:solid #add9c0; border-width:1px 1px 1px 1px;}");
		sb.append("td label{font-size:14px;font-weight:bold;}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<div style='margin:0 auto;font-size:12px;'>");
		sb.append("<div style='width:850px; margin:0 auto;'>");
		sb.append("<div id='content' style='padding:20px 10px;'>");
		sb.append("<strong style='font-size: x-large;'>感谢您使用ipayLisks收单服务，您收到了一批订单通知，来自您的网站$$data.websiteName$$，订单信息如下：</strong>");
		sb.append("<br/>");
		sb.append("<table style='width:100%; height:100%; text-align:left;' >");
		sb.append("$$data.bodyContent$$");
		sb.append("</<div id='footer' style='margin:0 30px; padding:10px 30px 0; border-top:1px dotted #8e8e8e; color:#999999;'>>");
		sb.append("</div>");
		sb.append("<p>此为系统邮件，请勿回复</p>");
		sb.append("<p>请保管好您的邮箱，避免账户被他人盗用</p>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</div>");
		sb.append("</body>");
		sb.append("</html>");
		
		return sb.toString();
	}
	
	
	//生成订单通知内容
	private Map<String,String> genBodyContent(List<Map> tradeDetailMap)
	{
		Map<String,String> contextMap = new HashMap<String,String>();
		StringBuffer sb = new StringBuffer();
		String websiteName = "";
		String tradeOrderNoStr = "";
		String statusMsg = "";
		Long maxTradeOrderNo = 0L;
		Long tradeOrderNo = 0L;
		//sb.append("<tr><td><label>商户订单号</label></td><td><label>支付订单号</label></td><td ><label>交易状态</label></td><td><label>交易时间</label></td><td><label>交易金额</label></td><td><label>持卡人姓名</label></td></tr>");
		sb.append("<tr><td><label>商户订单号</label></td><td><label>交易流水号</label></td><td ><label>订单状态</label></td><td><label>交易时间</label></td><td><label>交易金额</label></td><td><label>持卡人姓名</label></td></tr>");
		if(tradeDetailMap != null)
		{
			for(Map detail : tradeDetailMap)
			{
				if(detail.get("extOrderInfo") != null)
					websiteName = detail.get("extOrderInfo").toString();
				tradeOrderNoStr = detail.get("tradeOrderNo").toString();
				if(!StringUtil.isEmpty(tradeOrderNoStr))
					tradeOrderNo = Long.valueOf(tradeOrderNoStr);
				if(maxTradeOrderNo < tradeOrderNo)
					maxTradeOrderNo = tradeOrderNo;//记录最大的网关订单号
				if(detail.get("statusMsg") != null)
					statusMsg = detail.get("statusMsg").toString();
				if(!StringUtil.isEmpty(statusMsg) && statusMsg.indexOf("失败")>=0)
					sb.append("<tr style='color:#F00;font-weight:bold;'>");//失败订单用红色字体显示
				else
					sb.append("<tr>");
				sb.append("<td>"+detail.get("orderId")+"</td>");//商户订单号
				sb.append("<td>"+tradeOrderNoStr+"</td>");//支付订单号
				sb.append("<td>"+statusMsg+"</td>");//交易状态
				sb.append("<td>"+detail.get("createDate")+"</td>");//交易时间
				sb.append("<td>"+detail.get("amountMsg")+"</td>");//交易金额
				sb.append("<td>"+detail.get("cardholderMame")+"</td>");//持卡人姓名
				sb.append("</tr>");
			}
		}
		contextMap.put("websiteName", websiteName);
		contextMap.put("bodyContent", sb.toString());
		contextMap.put("maxTradeOrderNo", String.valueOf(maxTradeOrderNo));
		  
		return contextMap;
	}


	/**
	 * @param jmsSender the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setNotifyTemplateService(
			NotifyTemplateService notifyTemplateService) {
		this.notifyTemplateService = notifyTemplateService;
	}

	public void setOrderCenterInvokeService(
			HessianInvokeService orderCenterInvokeService) {
		this.orderCenterInvokeService = orderCenterInvokeService;
	}
	
}
