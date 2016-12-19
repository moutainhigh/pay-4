package com.pay.fo.controller.fundout.batchrule;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.fundout.withdraw.dto.ruleconfig.BatchRuleChannelQueryDTO;
import com.pay.fundout.withdraw.dto.ruleconfig.RuleConfigQueryDTO;
import com.pay.fundout.withdraw.dto.timeconfig.BatchTimeConfigDTO;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleChannelRes;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.model.ruleoperator.BatchRuleOperator;
import com.pay.fundout.withdraw.model.timeconfig.BatchTimeConfig;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleChannelResService;
import com.pay.fundout.withdraw.service.ruleconfig.BatchRuleConfigService;
import com.pay.fundout.withdraw.service.ruleoperator.BatchRuleOperatorService;
import com.pay.fundout.withdraw.service.timeconfig.BatchTimeConfigService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.security.util.SessionUserHolderUtil;

public class BatchRuleConfigController extends AbstractBaseController {
	private BatchRuleConfigService batchRuleConfigService;

	private BatchRuleOperatorService batchRuleOperatorService;

	private BatchTimeConfigService batchTimeConfigService;

	private BatchRuleChannelResService batchRuleChannelResService;

	private FundoutChannelService fundoutChannelService;

	public void setFundoutChannelService(FundoutChannelService fundoutChannelService) {
		this.fundoutChannelService = fundoutChannelService;
	}

	public void setBatchRuleChannelResService(BatchRuleChannelResService batchRuleChannelResService) {
		this.batchRuleChannelResService = batchRuleChannelResService;
	}

	public void setBatchRuleConfigService(BatchRuleConfigService batchRuleConfigService) {
		this.batchRuleConfigService = batchRuleConfigService;
	}

	public void setBatchRuleOperatorService(BatchRuleOperatorService batchRuleOperatorService) {
		this.batchRuleOperatorService = batchRuleOperatorService;
	}

	public void setBatchTimeConfigService(BatchTimeConfigService batchTimeConfigService) {
		this.batchTimeConfigService = batchTimeConfigService;
	}

	/**
	 * 页面跳转
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(this.URL("toAddPage"));
	}

	/**
	 * 创建规则
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView create(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Long batchRuleId = this.batchRuleConfigService.getSeqId();
			BatchTimeConfig timeConfig = createBatchTimeConfig(request, response);
			BatchRuleConfig ruleConfig = createBatchRuleConfig(request, response, batchRuleId);
			if (timeConfig.getSequenceId() != null) {
				ruleConfig.setBatchTimeConfId(timeConfig.getSequenceId());
			}
			List operatorList = createBatchRuleOperator(request, response, batchRuleId);
			this.batchRuleConfigService.createRuleConfigRdTx(ruleConfig, timeConfig, operatorList);
			return new ModelAndView(this.URL("toSuccess"), "hrefStr", "继续添加批次规则时间").addObject("toUrl",
					"batchRuleConfigController.do").addObject("result", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(this.URL("toSuccess"), "hrefStr", "点击返回").addObject("toUrl",
					"batchRuleConfigController.do").addObject("result", "添加失败");
		}
	}

	/**
	 * 检查规则名称是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView checkRuleConfigDesc(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String desc = request.getParameter("batchRuleDesc");
		String ruleConfigId = request.getParameter("ruleConfigId");
		Map params = new HashMap();
		params.put("ruleConfigDesc", desc.trim());
		Page<RuleConfigQueryDTO> page = PageUtils.getPage(request);
		page = this.batchRuleConfigService.getRuleConfigList(page, params);
		if (ruleConfigId != null) {
			if (page.getResult().size() == 1
					&& page.getResult().get(0).getSequenceId().equals(Long.valueOf(ruleConfigId))
					|| page.getResult().size() == 0) {
				response.getWriter().print("yes");
			} else {
				response.getWriter().print("no");
			}
		} else {
			if (page.getResult().size() == 0) {
				response.getWriter().print("yes");
			} else {
				response.getWriter().print("no");
			}
		}
		response.getWriter().close();
		return null;
	}

	/**
	 * 查询批次规则列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List ruleConfigList = this.batchRuleConfigService.getBatchRuleConfigList();
		return new ModelAndView(URL("toRuleConfigInit")).addObject("ruleConfigList", ruleConfigList);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView queryRuleConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ruleConfigDesc = request.getParameter("ruleConfigDesc");
		Map params = new HashMap();
		if (ruleConfigDesc != null && ruleConfigDesc.trim().length() > 0 && !ruleConfigDesc.equals("-1")) {
			params.put("ruleConfigDesc", ruleConfigDesc.trim());
		}
		Page<RuleConfigQueryDTO> page = PageUtils.getPage(request);
		page = this.batchRuleConfigService.getRuleConfigList(page, params);
		for (int i = 0; i < page.getResult().size(); i++) {
			StringBuffer buff = new StringBuffer();
			List operatorList = this.batchRuleOperatorService.getOperatorsByBatchId(page.getResult().get(i)
					.getSequenceId());
			for (int j = 0; j < operatorList.size(); j++) {
				BatchRuleOperator operator = (BatchRuleOperator) operatorList.get(j);
				buff.append(operator.getIdentity());
				buff.append("\n");
			}
			page.getResult().get(i).setOperator(buff.toString());
		}

		return new ModelAndView(URL("toRuleConfigList")).addObject("page", page);
	}

	/**
	 * 查询批次规则详情
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long sequenceId = Long.valueOf(request.getParameter("sequenceId"));
		RuleConfigQueryDTO ruleConfig = this.batchRuleConfigService.getRuleConfigById(sequenceId);
		BatchTimeConfigDTO timeConfig = this.batchTimeConfigService.getTimeConfigById(ruleConfig.getBatchTimeConfId());
		List operatorList = this.batchRuleOperatorService.getOperatorsByBatchId(sequenceId);
		StringBuffer buff = new StringBuffer();
		for (int i = 0; i < operatorList.size(); i++) {
			BatchRuleOperator operator = (BatchRuleOperator) operatorList.get(i);
			buff.append(operator.getIdentity());
			if (i != operatorList.size() - 1) {
				buff.append(";");
			}
		}
		Map model = new HashMap();
		model.put("ruleConfig", ruleConfig);
		model.put("timeConfig", timeConfig);
		model.put("operators", buff.toString());
		if (request.getParameter("update") != null) {
			return new ModelAndView(URL("toUpdateRuleConfig"), model);
		}

		return new ModelAndView(URL("toRuleConfigDetail"), model);
	}

	/**
	 * 更新批次规则
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView updateRuleConfig(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			Long batchRuleId = Long.valueOf(request.getParameter("sequenceId"));
			Long timeConfigId = Long.valueOf(request.getParameter("timeConfigId"));
			BatchTimeConfig timeConfig = createBatchTimeConfig(request, response);
			BatchRuleConfig ruleConfig = createBatchRuleConfig(request, response, batchRuleId);
			ruleConfig.setBatchTimeConfId(timeConfigId);
			List operatorList = createBatchRuleOperator(request, response, batchRuleId);
			this.batchRuleConfigService.updateRuleConfigRdTx(ruleConfig, timeConfig, operatorList);
			return new ModelAndView(this.URL("toSuccess"), "hrefStr", "返回查询页面").addObject("toUrl",
					"batchRuleConfigController.do?method=query").addObject("result", "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(this.URL("toSuccess"), "hrefStr", "返回查询页面").addObject("toUrl",
					"batchRuleConfigController.do?method=query").addObject("result", "修改失败");
		}
	}

	/**
	 * 创建批次时间配置
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private BatchTimeConfig createBatchTimeConfig(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Integer timeType = Integer.valueOf(request.getParameter("timeType"));
		String weekDayList = request.getParameter("weekDayList");

		BatchTimeConfig batchTimeConfig = new BatchTimeConfig();
		Map params = new HashMap();
		params.put("timeType", timeType);
		params.put("weekDayList", weekDayList);
		if (timeType.equals(1)) {
			params.put("specialPoint", request.getParameter("specialPoint"));
		} else if (timeType.equals(0)) {
			params.put("startTimePoint", request.getParameter("startTimePoint"));
			params.put("endTimePoint", request.getParameter("endTimePoint"));
		}
		Long timeId = this.batchTimeConfigService.getSequenceIdByWeekDayListAndType(params);
		if (timeId == null) {
			batchTimeConfig.setTimeType(timeType);
			batchTimeConfig.setWeekDayList(weekDayList);
			batchTimeConfig.setCreationDate(new Date());
			batchTimeConfig.setUpdateDate(new Date());
			batchTimeConfig.setStatus(1);
		} else {
			BatchTimeConfigDTO dto=batchTimeConfigService.getTimeConfigById(timeId);
			BeanUtils.copyProperties(batchTimeConfig, dto);
		}
		if (timeType.equals(1)) {
			batchTimeConfig.setSpecialPoint(request.getParameter("specialPoint"));
		} else if (timeType.equals(0)) {
			batchTimeConfig.setStartTimePoint(request.getParameter("startTimePoint"));
			batchTimeConfig.setEndTimePoint(request.getParameter("endTimePoint"));
			batchTimeConfig.setTimeSpan(Integer.valueOf(request.getParameter("timeSpan")));
		}
		return batchTimeConfig;
	}

	/**
	 * 创建批次规则
	 * 
	 * @param request
	 * @param response
	 * @param batchRuleId
	 * @throws Exception
	 */
	private BatchRuleConfig createBatchRuleConfig(HttpServletRequest request, HttpServletResponse response,
			Long batchRuleId) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BatchRuleConfig batchRuleConfig = new BatchRuleConfig();
		batchRuleConfig.setSequenceId(batchRuleId);
		batchRuleConfig.setBatchRuleDesc(request.getParameter("batchRuleDesc"));
		batchRuleConfig.setCreationDate(new Date());
		batchRuleConfig.setEffectDate(dateFormat.parse(request.getParameter("effectDate")));
		batchRuleConfig.setLostEffectDate(dateFormat.parse(request.getParameter("lostEffectDate")));
		String maxOrderCounts = request.getParameter("maxOrderCounts").trim();
		if (maxOrderCounts != null && maxOrderCounts.length() > 0) {
			batchRuleConfig.setMaxOrderCounts(Long.valueOf(maxOrderCounts));
		}
		if (batchRuleConfig.getEffectDate().after(batchRuleConfig.getLostEffectDate())) {
			batchRuleConfig.setStatus(0);
		} else {
			batchRuleConfig.setStatus(1);
		}
		batchRuleConfig.setBusiType(0); // 暂设为0,等待商榷
		batchRuleConfig.setBatchLevel(Integer.valueOf(1));

		return batchRuleConfig;
	}

	/**
	 * 创建操作人员
	 * 
	 * @param request
	 * @param response
	 * @param batchRuleId
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private List createBatchRuleOperator(HttpServletRequest request, HttpServletResponse response, Long batchRuleId)
			throws Exception {
		String[] identitys = request.getParameter("identity").split("[;|；]");
		Date date = new Date();
		List list = new ArrayList();
		for (int i = 0; i < identitys.length; i++) {
			BatchRuleOperator batchRuleOperator = new BatchRuleOperator();
			batchRuleOperator.setBatchRuleId(batchRuleId);
			batchRuleOperator.setIdentity(identitys[i]);
			batchRuleOperator.setCreationDate(date);
			batchRuleOperator.setStatus(1);
			list.add(batchRuleOperator);
		}
		return list;
	}

	/**
	 * 转到添加批次规则业务的页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView toAddBusi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List ruleConfigList = this.batchRuleConfigService.getBatchRuleConfigList();
		return new ModelAndView(URL("toAddBusiPage")).addObject("ruleConfigList", ruleConfigList);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView getChannelList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long ruleConfigId = Long.valueOf(request.getParameter("ruleConfigId"));
		List channelList = batchRuleChannelResService.getResChannelList(ruleConfigId);
		Map<String, String> bank = fundoutChannelService.getBankMap(fundoutChannelService
				.getFoBankList(new FundoutBank()));
//		Map<String, String> business = fundoutChannelService.getBusinessMap(fundoutChannelService
//				.getFoBusinessList(new FundoutBusiness()));
		Map<String, String> mode = fundoutChannelService.getModeMap(fundoutChannelService
				.getFoModeList(new FundoutMode()));
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < channelList.size(); i++) {
			BatchRuleChannelQueryDTO channel = (BatchRuleChannelQueryDTO) channelList.get(i);
			buffer.append("<table cellpadding=\"5\">");
			if ((i + 1) % 3 == 1) {
				buffer.append("<tr>");
			}
			buffer.append("<td><input type=\"checkbox\" name=\"channelId\" value=\"");
			buffer.append(channel.getChannelKy());
			buffer.append("\"/>");
			buffer.append(mode.get(channel.getModeId().toString()));
			buffer.append("-");
//			buffer.append(business.get(channel.getBusinessId().toString()));
//			buffer.append("-");
			buffer.append(bank.get(channel.getBankId().toString()));
			buffer.append("</td>");
			if ((i + 1) % 3 == 0 || i == channelList.size() - 1) {
				buffer.append("</tr>");
			}
			buffer.append("</table>");
		}
		response.setCharacterEncoding("utf-8");
		if (buffer.toString().length() > 0) {
			response.getWriter().print(buffer.toString());
		} else {
			response.getWriter().print("no");
		}
		response.getWriter().close();
		return null;
	}

	/**
	 * 创建批次规则业务和批次规则的关联
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView createBusi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 创建批次规则和渠道的关联
		try {
			String ruleConfigId = request.getParameter("ruleConfigId");
			String[] ids = request.getParameterValues("channelId");
			String operator = SessionUserHolderUtil.getLoginId();
			if (ids != null) {
				for (int i = 0; i < ids.length; i++) {
					BatchRuleChannelRes res = new BatchRuleChannelRes();
					res.setRuleKy(ruleConfigId);
					res.setChannelKy(ids[i]);
					res.setCreatedBy(operator);
					res.setState(1);
					res.setCreatedDate(new Date());
					batchRuleChannelResService.insert(res);
				}
			}
			return new ModelAndView(this.URL("toSuccess"), "hrefStr", "继续添加批次规则业务").addObject("toUrl",
					"batchRuleConfigController.do?method=toAddBusi").addObject("result", "添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(this.URL("toSuccess"), "hrefStr", "继续添加批次规则业务").addObject("toUrl",
					"batchRuleConfigController.do?method=toAddBusi").addObject("result", "添加失败");
		}
	}

	/**
	 * 查询批次规则业务
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryBusi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List ruleConfigList = this.batchRuleConfigService.getBatchRuleConfigList();
		return new ModelAndView(URL("toBatchRuleBusiInit")).addObject("ruleConfigList", ruleConfigList);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView queryBusiList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String ruleKy = request.getParameter("ruleConfigId");
		Map params = new HashMap();
		if (ruleKy != null && !ruleKy.equals("-1")) {
			params.put("ruleKy", ruleKy);
		}
		Page<BatchRuleChannelQueryDTO> page = PageUtils.getPage(request);
		page = batchRuleChannelResService.getBatchRuleChannelResList(page, params);
		Map<String, String> bank = fundoutChannelService.getBankMap(fundoutChannelService
				.getFoBankList(new FundoutBank()));
		Map<String, String> business = fundoutChannelService.getBusinessMap(fundoutChannelService
				.getFoBusinessList(new FundoutBusiness()));
		Map<String, String> mode = fundoutChannelService.getModeMap(fundoutChannelService
				.getFoModeList(new FundoutMode()));
		Map<String, String> ruleConfig = batchRuleConfigService.getRuleConfigMap(batchRuleConfigService
				.getBatchRuleConfigList());
		for (int i = 0; i < page.getResult().size(); i++) {
			page.getResult().get(i).setChannelDesc(
					mode.get(page.getResult().get(i).getModeId().toString()) + "-"
							+ business.get(page.getResult().get(i).getBusinessId().toString()) + "-"
							+ bank.get(page.getResult().get(i).getBankId().toString()));
			String str = ruleConfig.get(page.getResult().get(i).getRuleKy().toString());
			if (str != null) {
				page.getResult().get(i).setRuleConfigDesc(str);
			}
		}
		return new ModelAndView(URL("toBatchRuleBusiList")).addObject("page", page);
	}

	/**
	 * 删除业务规则（将其状态设为不可用）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView deleteBusi(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String seqId = request.getParameter("id");
			BatchRuleChannelRes res = new BatchRuleChannelRes();
			res.setSeqKy(Long.valueOf(seqId));
			res.setState(0);
			batchRuleChannelResService.updateBatchRuleChannelResById(res);
			response.getWriter().print("ok");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("error");
		}
		response.getWriter().close();
		return null;
	}
}
