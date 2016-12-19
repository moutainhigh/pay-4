package com.pay.fundout.withdraw.service.autorisk.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fundout.withdraw.common.util.WithDrawAutoRiskConstants;
import com.pay.fundout.withdraw.dto.autorisk.AutoRiskDto;
import com.pay.fundout.withdraw.dto.autorisk.RmRuleDefDTO;
import com.pay.util.DateUtil;

/**
 * 商户出款自动过风控规则服务
 * @author meng.li
 *
 */
public class MerchantAutoRiskServiceImpl extends AbstractAutoRiskServiceImpl {
	
	private static final int STATUS_GE = 1; // 表示大于等于
	private static final int STATUS_LE = 2; // 表示小于等于

	@Override
	boolean m1(FundoutOrderDTO order) {
		// 首先判断是否首次批量付款
		if (order.getOrderType() == OrderType.BATCHPAY2BANK.getValue()) {
			Long parentOrderid = order.getParentOrderId();
			int times = autoRiskInterfaceService.queryBatchFundOutTimes(order.getPayerMemberCode(), parentOrderid);
			// 为0表示第一次批量付款
			if (times == 0) {
				log.info("-----first batch fundout, m1 rule touched -----");
				AutoRiskDto dto = fillAutoAutoRiskDto(order, WithDrawAutoRiskConstants.M1);
				this.getRiskList().add(dto);
				return false;
			}
		}
		// 非首次批量付款到银行情况 不过m001规则
		if (order.getOrderType() == OrderType.BATCHPAY2BANK.getValue()) {
			return true;
		}
		// 其他该怎么走还是怎么走
		RmRuleDefDTO rule = riskManagerService.findRuleByRuleCode(WithDrawAutoRiskConstants.M1);
		int times = autoRiskInterfaceService.queryFundOutTimes(order.getPayerMemberCode());
		boolean passed = true;
		if(rule != null && StringUtils.isNotBlank(rule.getRuleValue())){
			log.info("-----m1's value is " + rule.getRuleValue() + "-----");
			String[] m1ValArray = rule.getRuleValue().split("\\,");
			if(m1ValArray != null){
				for(String str : m1ValArray){
					if(StringUtils.isNotBlank(str)){
						int limitTime = Integer.parseInt(str);
						if(times == limitTime){
							passed = false;
							log.info("-----m1 rule touched, memberCode is" + order.getPayerMemberCode() + ", histroy fundount number is " + times + "-----");
							break;
						}
					}
				}
			}
		}
		if(!passed){
			AutoRiskDto dto = fillAutoAutoRiskDto(order, WithDrawAutoRiskConstants.M1);
			this.getRiskList().add(dto);
		}
		return passed;
	}
	
	@Override
	boolean m2(FundoutOrderDTO order) {
		RmRuleDefDTO rule = riskManagerService.findRuleByRuleCode(WithDrawAutoRiskConstants.M2);
		boolean passed = true;
		if(rule != null && StringUtils.isNotBlank(rule.getRuleValue())){
			log.info("-----m2's value is " + rule.getRuleValue() + "-----");
			String[] m2ValArray = rule.getRuleValue().split("\\,");
			if(m2ValArray != null){
				Date endDate;
				Date beginDate;
				// 四条一条一条走，一条触发即认为此规则触发，其他不再执行
				if(StringUtils.isNotBlank(m2ValArray[0]) && StringUtils.isNotBlank(m2ValArray[1])){
					// 第一条判断
					int dayOff = Integer.parseInt(m2ValArray[0]);
					BigDecimal limitAmount = new BigDecimal(m2ValArray[1]);
					endDate = new Date();
					beginDate = DateUtil.getDate(-dayOff);
					BigDecimal totalFundInAmount = autoRiskInterfaceService.queryFundInAmount(order.getPayerMemberCode(), beginDate, endDate);
					if(totalFundInAmount != null && totalFundInAmount.compareTo(limitAmount) != -1){
						passed = false;
						log.info("-----m3's first rule touched, memberCode is " + order.getPayerMemberCode() + ", totalFundInAmount is " + totalFundInAmount + ", limitAmount is " + limitAmount + "-----");
					}
				}
				if(passed && StringUtils.isNotBlank(m2ValArray[2]) && StringUtils.isNotBlank(m2ValArray[3]) && StringUtils.isNotBlank(m2ValArray[4]) && StringUtils.isNotBlank(m2ValArray[5])){
					// 第二条判断
					int dayOff1 = Integer.parseInt(m2ValArray[2]);
					int dayOff2 = Integer.parseInt(m2ValArray[3]);
					int dayOff3 = Integer.parseInt(m2ValArray[4]);
					BigDecimal limitPercent = new BigDecimal(m2ValArray[5]);
					if(jundge(dayOff1, dayOff2, dayOff3, limitPercent, STATUS_GE, order.getPayerMemberCode())){
						passed = false;
						log.info("-----m3's second rule touched, memberCode is " + order.getPayerMemberCode() + "-----");
					}
				}
				if(passed && StringUtils.isNotBlank(m2ValArray[6]) && StringUtils.isNotBlank(m2ValArray[7]) && StringUtils.isNotBlank(m2ValArray[8]) && StringUtils.isNotBlank(m2ValArray[9])){
					// 第三条判断
					int dayOff1 = Integer.parseInt(m2ValArray[6]);
					int dayOff2 = Integer.parseInt(m2ValArray[7]);
					int dayOff3 = Integer.parseInt(m2ValArray[8]);
					BigDecimal limitPercent = new BigDecimal(m2ValArray[9]);
					if(jundge(dayOff1, dayOff2, dayOff3, limitPercent, STATUS_GE, order.getPayerMemberCode())){
						passed = false;
						log.info("-----m3's third rule touched, memberCode is " + order.getPayerMemberCode() + "-----");
					}
				}
				if(passed && StringUtils.isNotBlank(m2ValArray[10]) && StringUtils.isNotBlank(m2ValArray[11]) && StringUtils.isNotBlank(m2ValArray[12]) && StringUtils.isNotBlank(m2ValArray[13])){
					// 第四条判断
					int dayOff1 = Integer.parseInt(m2ValArray[10]);
					int dayOff2 = Integer.parseInt(m2ValArray[11]);
					int dayOff3 = Integer.parseInt(m2ValArray[12]);
					BigDecimal limitPercent = new BigDecimal(m2ValArray[13]);
					if(jundge(dayOff1, dayOff2, dayOff3, limitPercent, STATUS_LE, order.getPayerMemberCode())){
						passed = false;
						log.info("-----m3's fourth rule touched, memberCode is " + order.getPayerMemberCode() + "-----");
					}
				}
				if(passed && StringUtils.isNotBlank(m2ValArray[14]) && StringUtils.isNotBlank(m2ValArray[15]) && StringUtils.isNotBlank(m2ValArray[16]) && StringUtils.isNotBlank(m2ValArray[17])){
					// 第五条判断
					int dayOff1 = Integer.parseInt(m2ValArray[14]);
					int dayOff2 = Integer.parseInt(m2ValArray[15]);
					int dayOff3 = Integer.parseInt(m2ValArray[16]);
					BigDecimal limitPercent = new BigDecimal(m2ValArray[17]);
					if(jundge(dayOff1, dayOff2, dayOff3, limitPercent, STATUS_LE, order.getPayerMemberCode())){
						passed = false;
						log.info("-----m3's fifth rule touched, memberCode is " + order.getPayerMemberCode() + "-----");
					}
				}
			}
		}		
		if(!passed){
			AutoRiskDto dto = fillAutoAutoRiskDto(order, WithDrawAutoRiskConstants.M2);
			this.getRiskList().add(dto);
			return false;
		}
		return passed;
	}
	
	@Override
	boolean m3(FundoutOrderDTO order) {
		RmRuleDefDTO rule = riskManagerService.findRuleByRuleCode(WithDrawAutoRiskConstants.M3);
		boolean passed = true;
		if(rule != null && StringUtils.isNotBlank(rule.getRuleValue())){
			log.info("-----m3's value is " + rule.getRuleValue() + "-----");
			log.info("-----membercode is " + order.getPayerMemberCode() + "-----");
			String[] m3ValArray = rule.getRuleValue().split("\\,");
			if(m3ValArray != null && StringUtils.isNotBlank(m3ValArray[0])){
				int dayOff = Integer.parseInt(m3ValArray[0]);
				Date startDate = DateUtil.getDate(-dayOff);
				int regionFundInNums = autoRiskInterfaceService.queryFundInNums(order.getPayerMemberCode(), startDate, null);
				if(regionFundInNums > 0){
					log.info("-----m3 rule touched, memberCode is" + order.getPayerMemberCode() + "-----");
					passed = false;
				}
			}
		}		
		if(!passed){
			AutoRiskDto dto = fillAutoAutoRiskDto(order, WithDrawAutoRiskConstants.M3);
			this.getRiskList().add(dto);
			return false;
		}
		return passed;
	}
	
	@Override
	boolean m4(FundoutOrderDTO order) {
		RmRuleDefDTO rule = riskManagerService.findRuleByRuleCode(WithDrawAutoRiskConstants.M4);
		boolean passed = true;
		if(rule != null && StringUtils.isNotBlank(rule.getRuleValue())){
			log.info("-----m4's value is " + rule.getRuleValue() + "-----");
			String[] m4ValArray = rule.getRuleValue().split("\\,");
			if(m4ValArray != null && StringUtils.isNotBlank(m4ValArray[0])){
				BigDecimal limitAmount = new BigDecimal(m4ValArray[0]);
				Date endDate = new Date();
				Date beginDate = DateUtil.getDate(-1);
				BigDecimal maxAmount = autoRiskInterfaceService.queryFundInMaxAmount(order.getPayerMemberCode(), beginDate, endDate);
				if(maxAmount != null && maxAmount.compareTo(limitAmount) != -1){
					log.info("-----m4 ruletouched, memberCode is" + order.getPayerMemberCode() + ", max amount is" + maxAmount + ", limit amount is " + limitAmount + "-----");
					passed = false;
				}
			}
		}		
		if(!passed){
			AutoRiskDto dto = fillAutoAutoRiskDto(order, WithDrawAutoRiskConstants.M4);
			this.getRiskList().add(dto);
			return false;
		}
		return passed;
	}
	
	/**
	 * 判断商户规则2第2-5行规则方法
	 * @param field1 field1值
	 * @param field2 field2值
	 * @param field3 field3值
	 * @param field4 field4值
	 * @return 是否触发规则逻辑
	 */
	private boolean jundge(int field1, int field2, int field3, BigDecimal field4, int status, Long memberCode){
		Date endDate;
		Date beginDate;
		beginDate = DateUtil.getDate(-field1);
		endDate = new Date();
		BigDecimal firstFundInAmount = autoRiskInterfaceService.queryFundInAmount(memberCode, beginDate, endDate);
		if(field2 > field3){
			beginDate = DateUtil.getDate(-field2);
			endDate = DateUtil.getDate(-field3);
		}else{
			beginDate = DateUtil.getDate(-field3);
			endDate = DateUtil.getDate(-field2);
		}
		BigDecimal secondFundInAmount = autoRiskInterfaceService.queryFundInAmount(memberCode, beginDate, endDate);
		
		if(firstFundInAmount == null && secondFundInAmount == null){
			// 如果两个都是空，没有比较意义，认为不触发规则
			return false;
		}else{
			if(firstFundInAmount == null){
				firstFundInAmount = new BigDecimal(0);
			}
			if(secondFundInAmount == null){
				secondFundInAmount = new BigDecimal(0);
			}
		}
		
		if(status == STATUS_GE){
			return ((firstFundInAmount.compareTo(secondFundInAmount.multiply(field4).divide(new BigDecimal(100))) != -1));
		}else{
			return ((firstFundInAmount.compareTo(secondFundInAmount.multiply(field4).divide(new BigDecimal(100))) != 1));
		}
	}
	
}
