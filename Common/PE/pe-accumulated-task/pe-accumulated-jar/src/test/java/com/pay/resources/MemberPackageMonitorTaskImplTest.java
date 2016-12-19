package com.pay.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowPackageCfgDto;
import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowPackageMonitorDto;
import com.pay.pe.accumulated.memberpackagemonitor.service.MemberFlowPackageCfgService;
import com.pay.pe.accumulated.memberpackagemonitor.service.MemberFlowPackageMonitorService;
import com.pay.util.TimeUtil;


@ContextConfiguration(locations={"classpath*:context/**/*.xml"})
public class MemberPackageMonitorTaskImplTest extends AbstractTestNGSpringContextTests{
	
	@Resource(name="memberFlowPackageMonitorService")
	private MemberFlowPackageMonitorService memberFlowPackageMonitorService;
	
	@Resource(name="memberFlowPackageCfgService")
	private MemberFlowPackageCfgService memberFlowPackageCfgService;
	
	@Test
	public void testMemFlowPckMonitorHandle(){
		
		// 查询会员包量配置,查询条件起始日期为T，状态为0
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", TimeUtil.getDate());
		paramMap.put("status", 0);
		List<MemberFlowPackageCfgDto> memFlowPckCfgList = memberFlowPackageCfgService.queryMemFlowPckCfgList(paramMap);
		if(memFlowPckCfgList != null
				&& memFlowPckCfgList.size() > 0){
			// 启用会员配置
			for(MemberFlowPackageCfgDto memberFlowPackageCfgDto : memFlowPckCfgList){
				// 新增会员包量监控记录
				MemberFlowPackageMonitorDto memberFlowPackageMonitorDto = this.buildMemFlowPckMonitor(memberFlowPackageCfgDto);
				memberFlowPackageMonitorService.addMemFlowPckMonitorRnTx(memberFlowPackageMonitorDto);
				// 更新会员配置信息
				memberFlowPackageCfgDto.setStatus(1);
				memberFlowPackageCfgService.updateMemFlowPckRnTx(memberFlowPackageCfgDto);
			}
		}
		// 查询包量监控记录,查询条件
		paramMap.clear();
		List<MemberFlowPackageMonitorDto> memFlowPckMonitorList = memberFlowPackageMonitorService.queryMemFlowPckMonitorList(paramMap);
		if(memFlowPckMonitorList != null
				&& memFlowPckMonitorList.size() > 0){
			for(MemberFlowPackageMonitorDto memberFlowPackageMonitorDto : memFlowPckMonitorList){
				memberFlowPackageMonitorService.handleMemFlowPckMonitorRnTx(memberFlowPackageMonitorDto);
			}
		}
	}
	
	/**
	 * 组装包量监控记录
	 * @param memberFlowPackageMonitorDto
	 * @return
	 */
	private MemberFlowPackageMonitorDto buildMemFlowPckMonitor(MemberFlowPackageCfgDto memberFlowPackageCfgDto){
		MemberFlowPackageMonitorDto memberFlowPackageMonitorDto = new MemberFlowPackageMonitorDto();
		memberFlowPackageMonitorDto.setMemberCode(memberFlowPackageCfgDto.getMemberCode());
		memberFlowPackageMonitorDto.setMemberName(memberFlowPackageCfgDto.getMemberName());
		memberFlowPackageMonitorDto.setPrePayAmount(memberFlowPackageCfgDto.getPrePayAmount());
		memberFlowPackageMonitorDto.setPrePayFlow(memberFlowPackageCfgDto.getPrePayFlow());
		memberFlowPackageMonitorDto.setPrePayDate(memberFlowPackageCfgDto.getPrePayDate());
		memberFlowPackageMonitorDto.setBeginDate(memberFlowPackageCfgDto.getBeginDate());
		memberFlowPackageMonitorDto.setAverageRate(memberFlowPackageCfgDto.getAverageRate());
		memberFlowPackageMonitorDto.setWarnFlow(memberFlowPackageCfgDto.getWarnFlow());
		memberFlowPackageMonitorDto.setWarnLinkman(memberFlowPackageCfgDto.getWarnLinkman());
		memberFlowPackageMonitorDto.setWarnLinkmanEmail(memberFlowPackageCfgDto.getWarnLinkmanEmail());
		memberFlowPackageMonitorDto.setWarnLinkmanMobile(memberFlowPackageCfgDto.getWarnLinkmanMobile());
		memberFlowPackageMonitorDto.setShutDownFlow(memberFlowPackageCfgDto.getShutDownFlow());
		memberFlowPackageMonitorDto.setShutDownLinkman(memberFlowPackageCfgDto.getShutDownLinkman());
		memberFlowPackageMonitorDto.setShutDownLinkmanEmail(memberFlowPackageCfgDto.getShutDownLinkmanEmail());
		memberFlowPackageMonitorDto.setShutDownLinkmanMobile(memberFlowPackageCfgDto.getShutDownLinkmanMobile());
		memberFlowPackageMonitorDto.setResidualFlow(memberFlowPackageCfgDto.getPrePayFlow());
		memberFlowPackageMonitorDto.setWarnStatus(new Integer(0));
		memberFlowPackageMonitorDto.setGatewayStatus(new Integer(1));
		memberFlowPackageMonitorDto.setIsResendWarn(new Integer(1));
		return memberFlowPackageMonitorDto;
	}
}
