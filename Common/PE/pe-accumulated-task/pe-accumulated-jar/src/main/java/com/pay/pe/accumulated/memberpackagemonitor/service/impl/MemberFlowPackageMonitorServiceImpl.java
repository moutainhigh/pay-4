package com.pay.pe.accumulated.memberpackagemonitor.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.jms.notification.request.RequestType;
import com.pay.pe.accumulated.jms.service.PeJmsSender;
import com.pay.pe.accumulated.jms.service.impl.NotifyTargetRequest;
import com.pay.pe.accumulated.memberpackagemonitor.dao.MemberFlowDao;
import com.pay.pe.accumulated.memberpackagemonitor.dao.MemberFlowPackageMonitorDao;
import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowDto;
import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowPackageCfgDto;
import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowPackageMonitorDto;
import com.pay.pe.accumulated.memberpackagemonitor.service.MemberFlowPackageCfgService;
import com.pay.pe.accumulated.memberpackagemonitor.service.MemberFlowPackageMonitorService;
import com.pay.util.TimeUtil;

public class MemberFlowPackageMonitorServiceImpl implements
		MemberFlowPackageMonitorService {
	private Log log = LogFactory
			.getLog(MemberFlowPackageMonitorServiceImpl.class);

	private MemberFlowDao memberFlowDao;

	public void setMemberFlowDao(MemberFlowDao memberFlowDao) {
		this.memberFlowDao = memberFlowDao;
	}

	private MemberFlowPackageMonitorDao memberFlowPackageMonitorDao;

	public void setMemberFlowPackageMonitorDao(
			MemberFlowPackageMonitorDao memberFlowPackageMonitorDao) {
		this.memberFlowPackageMonitorDao = memberFlowPackageMonitorDao;
	}

	private MemberFlowPackageCfgService memberFlowPackageCfgService;

	public void setMemberFlowPackageCfgService(
			MemberFlowPackageCfgService memberFlowPackageCfgService) {
		this.memberFlowPackageCfgService = memberFlowPackageCfgService;
	}

//	private CloseOpenMechentChannelService closeOpenMechentChannelService;
//
//	public void setCloseOpenMechentChannelService(
//			CloseOpenMechentChannelService closeOpenMechentChannelService) {
//		this.closeOpenMechentChannelService = closeOpenMechentChannelService;
//	}

	private PeJmsSender peJmsSender;

	public void setPeJmsSender(PeJmsSender peJmsSender) {
		this.peJmsSender = peJmsSender;
	}

	private String fromEmail;
	private Long warnEmailTmpId;
	private Long warnMobilTmpId;
	private Long shutDownEmailTmpId;
	private Long shutDownMobilTmpId;

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public void setWarnEmailTmpId(Long warnEmailTmpId) {
		this.warnEmailTmpId = warnEmailTmpId;
	}

	public void setWarnMobilTmpId(Long warnMobilTmpId) {
		this.warnMobilTmpId = warnMobilTmpId;
	}

	public void setShutDownEmailTmpId(Long shutDownEmailTmpId) {
		this.shutDownEmailTmpId = shutDownEmailTmpId;
	}

	public void setShutDownMobilTmpId(Long shutDownMobilTmpId) {
		this.shutDownMobilTmpId = shutDownMobilTmpId;
	}

	/**
	 * 新增会员流量监控记录
	 * 
	 * @param memberFlowPackageMonitorDto
	 */
	@Override
	public void addMemFlowPckMonitorRnTx(
			MemberFlowPackageMonitorDto memberFlowPackageMonitorDto) {
		memberFlowPackageMonitorDao.create(memberFlowPackageMonitorDto);
	}

	/**
	 * 查询会员流量监控记录
	 * 
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<MemberFlowPackageMonitorDto> queryMemFlowPckMonitorList(
			Map<String, Object> paramMap) {
		return memberFlowPackageMonitorDao.findByTemplate("findBySelective",
				paramMap);
	}

	/**
	 * 删除监控记录
	 * 
	 * @param memberFlowPackageMonitorDto
	 * @return
	 */
	@Override
	public boolean deleteMemFlowPckMonitor(
			MemberFlowPackageMonitorDto memberFlowPackageMonitorDto) {
		return memberFlowPackageMonitorDao.delete(memberFlowPackageMonitorDto
				.getMemberCode());
	}

	/**
	 * 处理会员流量监控
	 * 
	 * @param memberFlowPackageMonitorDto
	 */
	@Override
	public void handleMemFlowPckMonitorRnTx(
			MemberFlowPackageMonitorDto memberFlowPackageMonitorDto) {
		String beginDate = TimeUtil.formatDateTime("yyyy-MM-dd",
				memberFlowPackageMonitorDto.getBeginDate());
		if (TimeUtil.getDate().equals(beginDate)) {
			log.info("系统时间等于规则生效起始日期，该条记录不做处理...");
			return;
		}
		String reportDate = TimeUtil.getDate(-1);
		log.info("##########开始处理会员“"
				+ memberFlowPackageMonitorDto.getMemberCode() + "”,日期：“"
				+ reportDate + "”流量监控记录##########");
		// 根据会员号查询T-1天会员流量记录
		MemberFlowDto memberFlowDto = this.getMemFlow(
				memberFlowPackageMonitorDto.getMemberCode().toString(),
				reportDate);
		// 如果未找到交易记录直接返回
		if (null == memberFlowDto) {
			log.info("未找到会员“" + reportDate + "”流量消费记录!");
			return;
		}
		// 计算剩余流量-------------------
		// T - 1天消费流量(账户入款、银行卡入款、预付卡入款、充值卡入款、手机POS入款)
		BigDecimal consumptionFlow = new BigDecimal(
				memberFlowDto.getFiAccInAmount())
				.add(new BigDecimal(memberFlowDto.getFiBankAmount()))
				.add(new BigDecimal(memberFlowDto.getFiPrepaidCardAmount()))
				.add(new BigDecimal(memberFlowDto.getFiConsumeCardAmount()))
				.add(new BigDecimal(memberFlowDto.getFiMobilePosAmount()));
		log.info("T - 1天消费流量:" + consumptionFlow.toString());
		log.info("剩余流量:"
				+ memberFlowPackageMonitorDto.getResidualFlow().toString());
		Long residualFlow = new BigDecimal(
				memberFlowPackageMonitorDto.getResidualFlow()).subtract(
				consumptionFlow).longValue();
		log.info("计算后剩余流量:" + residualFlow.toString());
		memberFlowPackageMonitorDto.setResidualFlow(residualFlow);
		// 预警流量
		Long warnFlow = memberFlowPackageMonitorDto.getWarnFlow();
		log.info("预警流量:" + warnFlow.toString());
		// 关停流量
		Long shutDownFlow = memberFlowPackageMonitorDto.getShutDownFlow();
		log.info("关停流量:" + shutDownFlow.toString());

		// 查找是否有续费规则
		MemberFlowPackageCfgDto nextMemFlowPckRule = hasNewRule(memberFlowPackageMonitorDto);
		// 计算是预警OR关停
		if (shutDownFlow.compareTo(residualFlow) >= 0) { // 关停处理
			MemberFlowPackageCfgDto curMemFlowPckRule = this
					.getCurMemFlowCfg(memberFlowPackageMonitorDto
							.getMemberCode().toString());
			if (curMemFlowPckRule == null) {
				log.info("未找到商户“" + memberFlowPackageMonitorDto.getMemberCode()
						+ "”包量配置规则!");
			}
			if (nextMemFlowPckRule == null) { // 没有新规则关闭渠道
				shutDownMemFlowPckMonitor(memberFlowPackageMonitorDto,
						curMemFlowPckRule);
			} else { // 将当前套餐设为停用，新套餐设为启用。删除监控记录
				enableNewRule(memberFlowPackageMonitorDto, curMemFlowPckRule,
						nextMemFlowPckRule);
			}
		} else if (warnFlow.compareTo(residualFlow) >= 0) { // 预警
			// 如果没有后续规则且设置为需要重发时重发信息
			if (nextMemFlowPckRule == null) {
				warnMemFlowPckMonitor(memberFlowPackageMonitorDto);
			}
			// 更新监控记录
			memberFlowPackageMonitorDao.update(memberFlowPackageMonitorDto);
		} else {
			// 更新监控记录
			memberFlowPackageMonitorDao.update(memberFlowPackageMonitorDto);
		}
	}

	/**
	 * 查询会员流量
	 * 
	 * @param memberCode
	 * @param reportDate
	 * @return
	 */
	private MemberFlowDto getMemFlow(String memberCode, String reportDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode); // 商户号
		paramMap.put("reportDate", reportDate);
		MemberFlowDto memberFlowDto = (MemberFlowDto) memberFlowDao
				.findObjectByTemplate("selectMemberFlow", paramMap);
		return memberFlowDto;
	}

	/**
	 * 查找当前套餐 查询条件：会员号、状态“正常”、包量起始日期最大
	 * 
	 * @param memberCode
	 * @return
	 */
	private MemberFlowPackageCfgDto getCurMemFlowCfg(String memberCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		paramMap.put("status", new Integer(1));
		List<MemberFlowPackageCfgDto> cfgList = memberFlowPackageCfgService
				.queryMemFlowPckCfgList(paramMap,
						"selectMemberFlowCfgListOrderByBeginDate");
		if (cfgList == null || cfgList.size() == 0) {
			return null;
		}
		return cfgList.get(0);
	}

	/**
	 * 预警
	 * 
	 * @param memberFlowPackageMonitorDto
	 * @return
	 */
	private boolean warnMemFlowPckMonitor(
			MemberFlowPackageMonitorDto memberFlowPackageMonitorDto) {
		String subject = "会员包量流量预警通知";
		// 发送预警邮件通知
		log.info("##################发送预警邮件通知##################");
		NotifyTargetRequest emailRequest = this.buildEmailRequest(subject,
				warnEmailTmpId, memberFlowPackageMonitorDto.getMemberCode()
						.toString(), memberFlowPackageMonitorDto
						.getWarnLinkmanEmail(), memberFlowPackageMonitorDto
						.getWarnLinkman());
		peJmsSender.notifyRequest(emailRequest);
		// 发送预警短信通知
		log.info("##################发送预警短信通知##################");
		NotifyTargetRequest smsRequest = this.buildSMSRequest(
				memberFlowPackageMonitorDto.getMemberCode().toString(),
				memberFlowPackageMonitorDto.getWarnLinkmanMobile(), subject,
				warnMobilTmpId);
		peJmsSender.notifyRequest(smsRequest);

		// 设置预警通知状态为“已预警”
		memberFlowPackageMonitorDto.setWarnStatus(new Integer(1));
		return true;
	}

	/**
	 * 关停
	 * 
	 * @param memberFlowPackageMonitorDto
	 * @return
	 */
	private boolean shutDownMemFlowPckMonitor(
			MemberFlowPackageMonitorDto memberFlowPackageMonitorDto,
			MemberFlowPackageCfgDto curMemFlowPckRule) {
		String subject = "会员包量流量关停通知";
		// 发送关停邮件通知
		log.info("##################发送关停邮件通知##################");
		NotifyTargetRequest emailRequest = this.buildEmailRequest(subject,
				shutDownEmailTmpId, memberFlowPackageMonitorDto.getMemberCode()
						.toString(), memberFlowPackageMonitorDto
						.getShutDownLinkmanEmail(), memberFlowPackageMonitorDto
						.getShutDownLinkman());
		peJmsSender.notifyRequest(emailRequest);
		// 发送关停短信通知
		log.info("##################发送关停短信通知##################");
		NotifyTargetRequest smsRequest = this.buildSMSRequest(
				memberFlowPackageMonitorDto.getMemberCode().toString(),
				memberFlowPackageMonitorDto.getShutDownLinkmanMobile(),
				subject, shutDownMobilTmpId);
		peJmsSender.notifyRequest(smsRequest);

		// 发送关停通知（调FI）
		log.info("################调FI关停开始################");
		//MechentChannelStatus mechentChannelStatus = closeOpenMechentChannelService.closeMechentChannelRnTx(memberFlowPackageMonitorDto.getMemberCode().toString());
		log.info("################调FI关停结束################");
//		if (mechentChannelStatus.SUCCESS.getValue() == 1) {
//			log.info("调用返回：" + mechentChannelStatus.SUCCESS.getDescription());
//			// 将当前套餐设为停用
//			MemberFlowPackageCfgDto curMemFlowPckRuleTmp = new MemberFlowPackageCfgDto();
//			curMemFlowPckRuleTmp.setSequenceId(curMemFlowPckRule
//					.getSequenceId());
//			curMemFlowPckRuleTmp.setStatus(new Integer(2));
//			curMemFlowPckRuleTmp.setLastOperateTime(new Date());
//			curMemFlowPckRuleTmp.setLastOperator("system");
//			log.info("设置当前套餐状态为停用!");
//			memberFlowPackageCfgService
//					.updateMemFlowPckRnTx(curMemFlowPckRuleTmp);
//			// 删除包量监控记录
//			deleteMemFlowPckMonitor(memberFlowPackageMonitorDto);
//		} else if (mechentChannelStatus.EXISTSHIS.getValue() == 2) {
//			log.info("调用返回：" + mechentChannelStatus.EXISTSHIS.getDescription());
//			return false;
//		} else if (mechentChannelStatus.EXISTSCHENNEL.getValue() == 3) {
//			log.info("调用返回："
//					+ mechentChannelStatus.EXISTSCHENNEL.getDescription());
//			return false;
//		}
		return true;
	}

	/**
	 * 启用新规则
	 * 
	 * @param memberFlowPackageMonitorDto
	 * @param curMemFlowPckRule
	 * @param nextMemFlowPckRule
	 * @return
	 */
	private boolean enableNewRule(
			MemberFlowPackageMonitorDto memberFlowPackageMonitorDto,
			MemberFlowPackageCfgDto curMemFlowPckRule,
			MemberFlowPackageCfgDto nextMemFlowPckRule) {
		// 删除监控记录
		log.info("删除当前套餐监控记录!");
		deleteMemFlowPckMonitor(memberFlowPackageMonitorDto);
		// 将当前套餐设为停用
		MemberFlowPackageCfgDto curMemFlowPckRuleTmp = new MemberFlowPackageCfgDto();
		curMemFlowPckRuleTmp.setSequenceId(curMemFlowPckRule.getSequenceId());
		curMemFlowPckRuleTmp.setStatus(new Integer(2));
		curMemFlowPckRuleTmp.setLastOperateTime(new Date());
		curMemFlowPckRuleTmp.setLastOperator("system");
		log.info("设置当前套餐状态为停用!");
		memberFlowPackageCfgService.updateMemFlowPckRnTx(curMemFlowPckRuleTmp);
		// 启用新规则
		nextMemFlowPckRule.setBeginDate(TimeUtil.parseDate(TimeUtil.getDate()));
		nextMemFlowPckRule.setStatus(1);
		nextMemFlowPckRule.setLastOperateTime(new Date());
		nextMemFlowPckRule.setLastOperator("system");
		log.info("启用新规则!");
		memberFlowPackageCfgService.updateMemFlowPckRnTx(nextMemFlowPckRule);
		// 添加对应的监控记录
		MemberFlowPackageMonitorDto newMemFlowPckMonitor = buildMemFlowPckMonitor(nextMemFlowPckRule);
		log.info("添加新的监控记录!");
		addMemFlowPckMonitorRnTx(newMemFlowPckMonitor);
		return true;
	}

	/**
	 * 构建邮件通知体
	 * 
	 * @param subject
	 * @param tmpId
	 * @param memberCode
	 * @param email
	 * @param linkMan
	 * @return
	 */
	private NotifyTargetRequest buildEmailRequest(String subject, Long tmpId,
			String memberCode, String email, String linkMan) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("warnDate", TimeUtil.getTime());
		data.put("memberCode", memberCode);
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setSubject(subject);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(fromEmail);
		request.getRecAddress().add(email);
		request.setTemplateId(tmpId);
		request.setRequestTime(new Date());
		return request;
	}

	/**
	 * 给付款方发送短信
	 * 
	 * @param dto
	 */
	private NotifyTargetRequest buildSMSRequest(String memberCode,
			String mobilePhone, String subject, Long tmpId) {

		Map<String, String> data = new HashMap<String, String>();
		data.put("warnDate", TimeUtil.getTime());
		data.put("memberCode", memberCode);
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.getMobiles().add(mobilePhone);
		request.setNotifyType(RequestType.SMS.value());
		request.setSubject(subject);
		request.setData(data);
		request.setTemplateId(tmpId);
		request.setRequestTime(new Date());
		return request;
	}

	/**
	 * 检查是否有新规则
	 * 
	 * @param memberFlowPackageMonitorDto
	 * @return
	 */
	private MemberFlowPackageCfgDto hasNewRule(
			MemberFlowPackageMonitorDto memberFlowPackageMonitorDto) {
		// 根据会员号查询未启用的规则,根据预付日期排序
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberFlowPackageMonitorDto.getMemberCode());
		paramMap.put("status", new Integer(0));
		List<MemberFlowPackageCfgDto> cfgList = this.memberFlowPackageCfgService
				.queryMemFlowPckCfgList(paramMap, "hasNextPckRule");
		if (cfgList != null && cfgList.size() > 0) {
			return cfgList.get(0);
		}
		return null;
	}

	/**
	 * 组装包量监控记录
	 * 
	 * @param memberFlowPackageMonitorDto
	 * @return
	 */
	private MemberFlowPackageMonitorDto buildMemFlowPckMonitor(
			MemberFlowPackageCfgDto memberFlowPackageCfgDto) {
		MemberFlowPackageMonitorDto memberFlowPackageMonitorDto = new MemberFlowPackageMonitorDto();
		memberFlowPackageMonitorDto.setMemberCode(memberFlowPackageCfgDto
				.getMemberCode());
		memberFlowPackageMonitorDto.setMemberName(memberFlowPackageCfgDto
				.getMemberName());
		memberFlowPackageMonitorDto.setPrePayAmount(memberFlowPackageCfgDto
				.getPrePayAmount());
		memberFlowPackageMonitorDto.setPrePayFlow(memberFlowPackageCfgDto
				.getPrePayFlow());
		memberFlowPackageMonitorDto.setPrePayDate(memberFlowPackageCfgDto
				.getPrePayDate());
		memberFlowPackageMonitorDto.setBeginDate(memberFlowPackageCfgDto
				.getBeginDate());
		memberFlowPackageMonitorDto.setAverageRate(memberFlowPackageCfgDto
				.getAverageRate());
		memberFlowPackageMonitorDto.setWarnFlow(memberFlowPackageCfgDto
				.getWarnFlow());
		memberFlowPackageMonitorDto.setWarnLinkman(memberFlowPackageCfgDto
				.getWarnLinkman());
		memberFlowPackageMonitorDto.setWarnLinkmanEmail(memberFlowPackageCfgDto
				.getWarnLinkmanEmail());
		memberFlowPackageMonitorDto
				.setWarnLinkmanMobile(memberFlowPackageCfgDto
						.getWarnLinkmanMobile());
		memberFlowPackageMonitorDto.setShutDownFlow(memberFlowPackageCfgDto
				.getShutDownFlow());
		memberFlowPackageMonitorDto.setShutDownLinkman(memberFlowPackageCfgDto
				.getShutDownLinkman());
		memberFlowPackageMonitorDto
				.setShutDownLinkmanEmail(memberFlowPackageCfgDto
						.getShutDownLinkmanEmail());
		memberFlowPackageMonitorDto
				.setShutDownLinkmanMobile(memberFlowPackageCfgDto
						.getShutDownLinkmanMobile());
		memberFlowPackageMonitorDto.setResidualFlow(memberFlowPackageCfgDto
				.getPrePayFlow());
		memberFlowPackageMonitorDto.setWarnStatus(new Integer(0));
		memberFlowPackageMonitorDto.setGatewayStatus(new Integer(1));
		memberFlowPackageMonitorDto.setIsResendWarn(new Integer(1));
		return memberFlowPackageMonitorDto;
	}
}
