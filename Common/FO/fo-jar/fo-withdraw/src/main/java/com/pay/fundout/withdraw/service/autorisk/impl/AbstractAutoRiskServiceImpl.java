package com.pay.fundout.withdraw.service.autorisk.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fundout.withdraw.common.util.WithDrawAutoRiskConstants;
import com.pay.fundout.withdraw.dto.autorisk.AutoRiskDto;
import com.pay.fundout.withdraw.dto.autorisk.RmRuleDefDTO;
import com.pay.fundout.withdraw.service.autorisk.AutoRiskInterfaceService;
import com.pay.fundout.withdraw.service.autorisk.AutoRiskService;
import com.pay.fundout.withdraw.service.autorisk.CheckLogService;
import com.pay.fundout.withdraw.service.autorisk.RiskManagerService;
import com.pay.util.DateUtil;

/**
 * 出款自动过风控规则抽象基类
 * @author meng.li
 *
 */
public abstract class AbstractAutoRiskServiceImpl implements AutoRiskService {

	transient Log log = LogFactory.getLog(getClass());
	
	// 交易是否通过出款自动过分控规则，true表示通过，false表示未通过
	private static boolean PASS_STATUS;
	
	// 记录一笔交易触犯的规则列表
	private List<AutoRiskDto> riskList;
	
	FundoutOrderService fundoutOrderService;
	
	CheckLogService checkLogService;
	
	RiskManagerService riskManagerService;
	
	AutoRiskInterfaceService autoRiskInterfaceService;

	public void setAutoRiskInterfaceService(AutoRiskInterfaceService autoRiskInterfaceService){
		this.autoRiskInterfaceService = autoRiskInterfaceService;
	}
	
	public void setFundoutOrderService(FundoutOrderService fundoutOrderService){
		this.fundoutOrderService = fundoutOrderService;
	}
	
	public void setCheckLogService(CheckLogService checkLogService){
		this.checkLogService = checkLogService;
	}
	
	public void setRiskManagerService(RiskManagerService riskManagerService) {
		this.riskManagerService = riskManagerService;
	}
	
	/**
	 * 企业商户规则1
	 * @param order 出款订单实体
	 * @return 规则是否出发，true表示没触发，false表示触发
	 */
	abstract boolean m1(FundoutOrderDTO order);
	
	/**
	 * 企业商户规则2
	 * @param order 出款订单实体
	 * @return 规则是否出发，true表示没触发，false表示触发
	 */
	abstract boolean m2(FundoutOrderDTO order);
	
	/**
	 * 企业商户规则3
	 * @param order 出款订单实体
	 * @return 规则是否出发，true表示没触发，false表示触发
	 */
	abstract boolean m3(FundoutOrderDTO order);
	
	/**
	 * 企业商户规则4
	 * @param order 出款订单实体
	 * @return 规则是否出发，true表示没触发，false表示触发
	 */
	abstract boolean m4(FundoutOrderDTO order);
	
	@Override
	public boolean autorisk(String orderId){
		log.info("-----start of fundout autorisk-----");
		riskList = new ArrayList<AutoRiskDto>();
		PASS_STATUS = true;
		FundoutOrderDTO order = (FundoutOrderDTO) fundoutOrderService.getOrder(Long.valueOf(orderId));
		judge(order);
		if(PASS_STATUS){
			log.info("-----fundout autorisk passed-----");
			log.info("-----end of fundout autorisk-----");
			return true;
		}
		// 创建交易触犯的规则日志并且返回自动风控不过
		checkLogService.createCheckLogsRdTx(riskList);
		log.info("-----fundout autorisk not passed-----");
		log.info("-----end of fundout autorisk-----");
		return false;
	}
	
	/**
	 * 企业和个人都适用的规则1，每个规则都有自己的判断逻辑，按类的单一职责话原则该分离出来，此处先写在一起了，
	 * 可以用代理的方式将这些拼装规则和保存进列表的代码提取出来的，此处也暂不处理
	 * @param order 出款订单实体
	 * @return 规则是否出发，true表示没触发，false表示触发
	 */
	boolean p1(FundoutOrderDTO order){
		RmRuleDefDTO rule = riskManagerService.findRuleByRuleCode(WithDrawAutoRiskConstants.P1);
		boolean passed = true;
		if(rule != null && StringUtils.isNotBlank(rule.getRuleValue())){
			log.info("-----p1's value is " + rule.getRuleValue() + "-----");
			String[] p1ValArray = rule.getRuleValue().split("\\,");
			if(p1ValArray != null && StringUtils.isNotBlank(p1ValArray[0]) && StringUtils.isNotBlank(p1ValArray[1])){
				int dayOff = Integer.parseInt(p1ValArray[0]);
				int limitNum = Integer.parseInt(p1ValArray[1]);
				Date endDate = new Date();
				Date beginDate = DateUtil.getDate(-dayOff);
				int failNum = autoRiskInterfaceService.queryLoginFailNum(order.getPayerMemberCode(), beginDate, endDate);
				if(failNum >= limitNum){
					log.info("-----p1 rule touched, memberCode is " + order.getPayerMemberCode() + ", faliNum is " + failNum + ", limitNum is " + limitNum + "-----");
					passed = false;
				}
			}
		}
		if(!passed){
			AutoRiskDto dto = fillAutoAutoRiskDto(order, WithDrawAutoRiskConstants.P1);
			this.getRiskList().add(dto);
		}
		return passed;
	}
	
	/**
	 * 企业和个人都适用的规则2
	 * @param order 出款订单实体
	 * @return 规则是否出发，true表示没触发，false表示触发
	 */
	boolean p2(FundoutOrderDTO order){
		RmRuleDefDTO rule = riskManagerService.findRuleByRuleCode(WithDrawAutoRiskConstants.P2);
		boolean passed = true;
		if(rule != null && StringUtils.isNotBlank(rule.getRuleValue())){
			log.info("-----p2's value is " + rule.getRuleValue() + "-----");
			String[] p2ValArray = rule.getRuleValue().split("\\,");
			if(p2ValArray != null && StringUtils.isNotBlank(p2ValArray[0])){
				int dayOff = Integer.parseInt(p2ValArray[0]);
				Date endDate = new Date();
				Date beginDate = DateUtil.getDate(-dayOff);
				boolean status = autoRiskInterfaceService.isIpConnected(order.getPayerMemberCode(), beginDate, endDate);
				if(status){
					log.info("-----p2 rule touched, memberCode is " + order.getPayerMemberCode() + "-----");
					passed = false;
				}
			}
		}
		if(!passed){
			AutoRiskDto dto = fillAutoAutoRiskDto(order, WithDrawAutoRiskConstants.P2);
			this.getRiskList().add(dto);
			return false;
		}
		return passed;
	}
	
	/**
	 * 执行出款自动过分控相关规则
	 * @param order 出款订单实体
	 */
	void judge(FundoutOrderDTO order){
		if(!m1(order)){
			PASS_STATUS = false;
		}
		if(!m2(order)){
			PASS_STATUS = false;
		}
		if(!m3(order)){
			PASS_STATUS = false;
		}
		if(!m4(order)){
			PASS_STATUS = false;
		}
		if(!p1(order)){
			PASS_STATUS = false;
		}
		if(!p2(order)){
			PASS_STATUS = false;
		}
	}
	
	/**
	 * 根据订单信息和负责编码填充出款分控dto实体
	 * @param order 出款订单实体
	 * @param ruleCode 风控编号
	 * @return
	 */
	AutoRiskDto fillAutoAutoRiskDto(FundoutOrderDTO order, String ruleCode){
		AutoRiskDto dto = new AutoRiskDto();
		dto.setCreateDate(new Date());
		dto.setMemberCode(order.getPayerMemberCode());
		dto.setMemberType(order.getPayerMemberType());
		dto.setMemberName(order.getPayerName());
		dto.setOrderNo(order.getOrderId());
		dto.setRuleCode(ruleCode);
		dto.setStatus(WithDrawAutoRiskConstants.STATUS_NOT_CHECKED);
		return dto;
	}

	public List<AutoRiskDto> getRiskList() {
		return riskList;
	}

	public void setRiskList(List<AutoRiskDto> riskList) {
		this.riskList = riskList;
	}
	
}
