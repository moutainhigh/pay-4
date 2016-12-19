package com.pay.fo.controller.fundout.autorisk;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.withdraw.dto.autorisk.RmRuleDefDTO;
import com.pay.fundout.withdraw.service.autorisk.RiskManagerService;
import com.pay.poss.base.controller.AbstractBaseController;

/**
 * 风控规则配置
 * 
 * @author Administrator
 * 
 */
public class RiskManagerController extends AbstractBaseController {

	private RiskManagerService riskManagerService;

	public void setRiskManagerService(RiskManagerService riskManagerService) {
		this.riskManagerService = riskManagerService;
	}

	/**
	 * 默认页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView riskmanagerView = new ModelAndView(URL("riskmanager"));
		List<RmRuleDefDTO> list = riskManagerService.riskManagerQuery();
		for (RmRuleDefDTO rmRuleDefDTO : list) {
			riskmanagerView.addObject(rmRuleDefDTO.getRuleCode(), rmRuleDefDTO.getRuleValue());
		}
		return riskmanagerView;
	}

	/**
	 * 保存规则
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveRisk(HttpServletRequest request, HttpServletResponse response) throws Exception {

		StringBuffer m001 = new StringBuffer();
		StringBuffer m002 = new StringBuffer();
		String m003 = request.getParameter("m003_1");
		String m004 = request.getParameter("m004_1");
		StringBuffer p001 = new StringBuffer();
		String p002 = request.getParameter("p002_1");

		for (int i = 1; i <= 3; i++) {
			m001.append(StringUtils.isBlank(request.getParameter("m001_" + i)) ? " " : request.getParameter("m001_" + i));
			m001.append(",");
		}

		for (int i = 1; i <= 18; i++) {
			m002.append(StringUtils.isBlank(request.getParameter("m002_" + i)) ? " " : request.getParameter("m002_" + i));
			m002.append(",");
		}

		for (int i = 1; i <= 2; i++) {
			p001.append(StringUtils.isBlank(request.getParameter("p001_" + i)) ? " " : request.getParameter("p001_" + i));
			p001.append(",");
		}

		List<RmRuleDefDTO> list = new ArrayList<RmRuleDefDTO>();
		RmRuleDefDTO rm001 = new RmRuleDefDTO();
		rm001.setRuleCode("m001");
		rm001.setRuleValue(m001.toString());
		list.add(rm001);

		RmRuleDefDTO rm002 = new RmRuleDefDTO();
		rm002.setRuleCode("m002");
		rm002.setRuleValue(m002.toString());
		list.add(rm002);

		RmRuleDefDTO rm003 = new RmRuleDefDTO();
		rm003.setRuleCode("m003");
		rm003.setRuleValue(m003);
		list.add(rm003);

		RmRuleDefDTO rm004 = new RmRuleDefDTO();
		rm004.setRuleCode("m004");
		rm004.setRuleValue(m004);
		list.add(rm004);

		RmRuleDefDTO rp001 = new RmRuleDefDTO();
		rp001.setRuleCode("p001");
		rp001.setRuleValue(p001.toString());
		list.add(rp001);

		RmRuleDefDTO rp002 = new RmRuleDefDTO();
		rp002.setRuleCode("p002");
		rp002.setRuleValue(p002);
		list.add(rp002);

		String info = "配置成功";
		
		try{
			riskManagerService.batchCreateriskmanagerRdTx(list);
		}catch (Exception e) {
			log.error("配置失败", e);
			info = "配置失败";
		}
		ModelAndView riskmanagerView = new ModelAndView(URL("riskmanager"));
		List<RmRuleDefDTO> result = riskManagerService.riskManagerQuery();
		for (RmRuleDefDTO rmRuleDefDTO : result) {
			riskmanagerView.addObject(rmRuleDefDTO.getRuleCode(), rmRuleDefDTO.getRuleValue());
		}
		riskmanagerView.addObject("info", info);
		return riskmanagerView;
	}
	
}
