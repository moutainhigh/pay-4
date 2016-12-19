package com.pay.poss.report.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.MemberFlowPackageCfgDTO;
import com.pay.poss.report.service.MemberFlowPackageCfgService;
import com.pay.poss.report.util.HTTPProtocolHandleUtil;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 分子公司维护
 * 
 * @Description
 * @file MemberFlowController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 */
public class MemberFlowPackageCfgController extends MultiActionController {

	private String viewList;

	private String toView;

	private String addView;

	private String toModify;

	private MemberFlowPackageCfgService memberFlowPackageCfgService;

	public void setToView(String toView) {
		this.toView = toView;
	}

	public void setViewList(String viewList) {
		this.viewList = viewList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setToModify(String toModify) {
		this.toModify = toModify;
	}

	public void setMemberFlowPackageCfgService(
			MemberFlowPackageCfgService memberFlowPackageCfgService) {
		this.memberFlowPackageCfgService = memberFlowPackageCfgService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(toView);
	}

	/**
	 * 查询包量配置列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String memberCode = StringUtil.null2String(request
				.getParameter("memberCode"));
		String name = StringUtil
				.null2String(request.getParameter("memberName"));
		String status = StringUtil.null2String(request.getParameter("status"));

		if (!StringUtil.isEmpty(memberCode)) {
			map.put("memberCode", memberCode.trim());
		}
		if (!StringUtil.isEmpty(name)) {
			map.put("memberName", name.trim());
		}
		if (!StringUtil.isEmpty(status)) {
			map.put("status", status.trim());
		}
		Page<MemberFlowPackageCfgDTO> page = PageUtils.getPage(request);
		page = memberFlowPackageCfgService.queryMemberFlowPackageCfg(page, map);
		return new ModelAndView(viewList).addObject("page", page).addObject(
				"status", "");
	}

	/**
	 * 初始化新增包量配置
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView addInit(HttpServletRequest req, HttpServletResponse res)
			throws ServletRequestBindingException, IOException {
		String sysDate = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil
				.skipDateTime(new Date(), 1));
		Map modelMap = new HashMap();
		modelMap.put("sysDate", sysDate);
		modelMap.put("newFlag", "1");
		return new ModelAndView(addView).addAllObjects(modelMap);
	}

	/**
	 * 新增包量配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		Map<String, Object> model = new HashMap<String, Object>();

		MemberFlowPackageCfgDTO memberFlowPackageCfgDTO = new MemberFlowPackageCfgDTO();
		memberFlowPackageCfgDTO = composeData(request, memberFlowPackageCfgDTO);
		memberFlowPackageCfgDTO.setCreator(SessionUserHolderUtil.getLoginId());
		memberFlowPackageCfgDTO.setCreateTime(new Date());
		memberFlowPackageCfgDTO.setStatus(0); // 状态 0-未启用、1-正常、2-已停用、9-作废
		memberFlowPackageCfgDTO.setRemark(buildRemark(SessionUserHolderUtil
				.getLoginId(), "新增", ""));
		memberFlowPackageCfgService
				.createMemberFlowPackageCfg(memberFlowPackageCfgDTO);
		return new ModelAndView(toView, model).addObject("message", "添加成功");
	}

	/**
	 * 初始化包量配置续费
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView renewInit(HttpServletRequest req,
			HttpServletResponse res) throws ServletRequestBindingException,
			IOException {
		MemberFlowPackageCfgDTO memberFlowPackageCfgDTO = memberFlowPackageCfgService
				.findById(Long.parseLong(req.getParameter("id")));
		// String sysDate = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil
		// .skipDateTime(new Date(), 1));
		// List<MemberFlowPackageCfgDTO> resultList =
		// memberFlowPackageCfgService
		// .findOrderbyBegindateAsc(memberFlowPackageCfgDTO
		// .getMemberCode());
		// if (resultList != null && resultList.size() > 0) {
		// MemberFlowPackageCfgDTO resultDto = resultList.get(0);
		// sysDate = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil
		// .skipDateTime(resultDto.getBeginDate(), 1));
		// }
		Map modelMap = new HashMap();
		// modelMap.put("sysDate", sysDate);
		modelMap.put("newFlag", "0");
		modelMap.put("dto", memberFlowPackageCfgDTO);
		return new ModelAndView(addView).addAllObjects(modelMap);
	}

	/**
	 * 初始化更新包量配置
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toUpdate(HttpServletRequest req,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		MemberFlowPackageCfgDTO memberFlowPackageCfgDTO = memberFlowPackageCfgService
				.findById(Long.parseLong(req.getParameter("id")));
		memberFlowPackageCfgDTO.setBeginDateStr(DateUtil.formatDateTime(
				"yyyy-MM-dd", memberFlowPackageCfgDTO.getBeginDate()));
		memberFlowPackageCfgDTO.setPrePayDateStr(DateUtil.formatDateTime(
				"yyyy-MM-dd", memberFlowPackageCfgDTO.getPrePayDate()));
		String sysDate = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil
				.skipDateTime(new Date(), 1));
		List<MemberFlowPackageCfgDTO> resultList = memberFlowPackageCfgService
				.findOrderbyBegindateAsc(memberFlowPackageCfgDTO
						.getMemberCode());
		String newFlag = "1";// 0-续费修改，1-新增修改
		if (resultList != null && resultList.size() > 1) {
			MemberFlowPackageCfgDTO resultDto = resultList.get(0);
			sysDate = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil
					.skipDateTime(resultDto.getBeginDate(), 0));
			newFlag = "0";
		}
		modelMap.put("memberToUpdate", memberFlowPackageCfgDTO);
		modelMap.put("sysDate", sysDate);
		modelMap.put("newFlag", newFlag);
		return new ModelAndView(toModify, modelMap);
	}

	/**
	 * 更新包量配置
	 * 
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	public ModelAndView update(HttpServletRequest req, HttpServletResponse res)
			throws ServletRequestBindingException, IOException {
		MemberFlowPackageCfgDTO memberFlowPackageCfgDTO = memberFlowPackageCfgService
				.findById(Long.parseLong(req.getParameter("id")));
		memberFlowPackageCfgDTO = composeData(req, memberFlowPackageCfgDTO);
		memberFlowPackageCfgDTO.setLastOperator(SessionUserHolderUtil
				.getLoginId());
		memberFlowPackageCfgDTO.setLastOperateTime(new Date());
		memberFlowPackageCfgDTO.setStatus(0); // 状态 0-未启用、1-正常、2-已停用、9-作废
		String remark = req.getParameter("remark");
		memberFlowPackageCfgDTO.setRemark(buildRemark(SessionUserHolderUtil
				.getLoginId(), "修改", remark));
		memberFlowPackageCfgService
				.updateMemberFlowPackageCfg(memberFlowPackageCfgDTO);

		return new ModelAndView(toView).addObject("message", "修改成功");
	}

	private MemberFlowPackageCfgDTO composeData(HttpServletRequest request,
			MemberFlowPackageCfgDTO memberFlowPackageCfgDTO) {
		// 取页面参数
		String memberCode = request.getParameter("memberCode");
		String memberName = StringUtil.null2String(request
				.getParameter("memberName"));
		String prePayAmount = request.getParameter("prePayAmount");
		String prePayFlow = request.getParameter("prePayFlow");
		String prePayDate = StringUtil.null2String(request
				.getParameter("prePayDate"));
		String beginDate = StringUtil.null2String(request
				.getParameter("beginDate"));
		String averageRate = request.getParameter("averageRate");
		String warnFlow = request.getParameter("warnFlow");
		String warnLinkman = StringUtil.null2String(request
				.getParameter("warnLinkman"));
		String warnLinkmanEmail = StringUtil.null2String(request
				.getParameter("warnLinkmanEmail"));
		String warnLinkmanMobile = StringUtil.null2String(request
				.getParameter("warnLinkmanMobile"));
		String shutDownFlow = request.getParameter("shutDownFlow");
		String shutDownLinkman = request.getParameter("shutDownLinkman");
		String shutDownLinkmanEmail = request
				.getParameter("shutDownLinkmanEmail");
		String shutDownLinkmanMobile = request
				.getParameter("shutDownLinkmanMobile");
		// 设置属性参数
		memberFlowPackageCfgDTO.setMemberCode(!"".equals(memberCode) ? Long
				.valueOf(memberCode) : null);
		memberFlowPackageCfgDTO.setMemberName(memberName);
		memberFlowPackageCfgDTO
				.setPrePayAmount(prePayAmount != null ? toLongAmount(prePayAmount)
						: null);
		memberFlowPackageCfgDTO
				.setPrePayFlow(prePayFlow != null ? toLongAmount(prePayFlow)
						: null);
		memberFlowPackageCfgDTO.setPrePayDate(!"".equals(prePayDate) ? DateUtil
				.parse("yyyy-MM-dd", prePayDate) : null);
		memberFlowPackageCfgDTO.setBeginDate(!"".equals(beginDate) ? DateUtil
				.parse("yyyy-MM-dd", beginDate) : null);
		memberFlowPackageCfgDTO.setAverageRate(!"".equals(averageRate) ? Long
				.valueOf(averageRate) : null);
		memberFlowPackageCfgDTO
				.setWarnFlow(warnFlow != null ? toLongAmount(warnFlow) : null);
		memberFlowPackageCfgDTO.setWarnLinkman(warnLinkman);
		memberFlowPackageCfgDTO.setWarnLinkmanEmail(warnLinkmanEmail);
		memberFlowPackageCfgDTO.setWarnLinkmanMobile(warnLinkmanMobile);
		memberFlowPackageCfgDTO
				.setShutDownFlow(!"".equals(shutDownFlow) ? toLongAmount(shutDownFlow)
						: null);
		memberFlowPackageCfgDTO.setShutDownLinkman(shutDownLinkman);
		memberFlowPackageCfgDTO.setShutDownLinkmanEmail(shutDownLinkmanEmail);
		memberFlowPackageCfgDTO.setShutDownLinkmanMobile(shutDownLinkmanMobile);

		return memberFlowPackageCfgDTO;
	}

	/**
	 * 作废包量配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView invalid(HttpServletRequest request,
			HttpServletResponse response) {
		HTTPProtocolHandleUtil.setAll(request, response);
		String id = request.getParameter("id");
		String remark = request.getParameter("remark");
		MemberFlowPackageCfgDTO memberFlowPackageCfgDTO = memberFlowPackageCfgService
				.findById(Long.parseLong(id));

		memberFlowPackageCfgDTO.setStatus(9);
		memberFlowPackageCfgDTO.setLastOperator(SessionUserHolderUtil
				.getLoginId());
		memberFlowPackageCfgDTO.setLastOperateTime(new Date());
		memberFlowPackageCfgDTO.setRemark(buildRemark(SessionUserHolderUtil
				.getLoginId(), "作废", remark));
		memberFlowPackageCfgService
				.updateMemberFlowPackageCfg(memberFlowPackageCfgDTO);

		String msg = "S";
		try {
			PrintWriter pw = response.getWriter();
			pw.write(msg);
			pw.flush();
		} catch (IOException e) {
			logger.error("MemberFlowPackageCfgController.invalid throws error",
					e);
		}
		return null;
	}

	/**
	 * 根据memberCode查找memberName
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryByMemberCode(HttpServletRequest request,
			HttpServletResponse response) {
		HTTPProtocolHandleUtil.setAll(request, response);
		String memberCode = request.getParameter("memberCode");
		String memberName = memberFlowPackageCfgService.findByMemberCode(Long
				.valueOf(memberCode));

		String isNewFlag = "1";// 0-续费，1-新增
		String sysDate = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil
				.skipDateTime(new Date(), 1));
		List<MemberFlowPackageCfgDTO> resultList = memberFlowPackageCfgService
				.findOrderbyBegindateAsc(Long.valueOf(memberCode));
		if (resultList != null && resultList.size() > 0) {
			MemberFlowPackageCfgDTO resultDto = resultList.get(0);
			sysDate = DateUtil.formatDateTime("yyyy-MM-dd", DateUtil
					.skipDateTime(resultDto.getBeginDate(), 1));
			isNewFlag = "0";
		}
		HashMap<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("isNewFlag", isNewFlag);
		resultMap.put("sysDate", sysDate);
		resultMap.put("memberName", memberName != null ? memberName : "");
		try {
			PrintWriter pw = response.getWriter();
			pw.write(JSonUtil.toJSonString(resultMap));
			pw.flush();
		} catch (IOException e) {
			logger
					.error(
							"MemberFlowPackageCfgController.queryByMemberCode throws error",
							e);
		}
		return null;
	}

	/**
	 * 根据beginDate查找列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryByBegindate(HttpServletRequest request,
			HttpServletResponse response) {
		HTTPProtocolHandleUtil.setAll(request, response);
		String newFlag = request.getParameter("newFlag");
		String beginDateStr = request.getParameter("beginDate");
		String memberCode = request.getParameter("memberCode");
		String msg = "";
		Map paramMap = new HashMap();
		Date beginDate = DateUtil.parse("yyyy-MM-dd", beginDateStr);
		paramMap.put("beginDate", beginDate);
		paramMap.put("memberCode", memberCode);
		if ("1".equals(newFlag)) {// 新增
			List<MemberFlowPackageCfgDTO> resultList = memberFlowPackageCfgService
					.findbyBegindate(paramMap);

			if (resultList != null && resultList.size() == 1) {
				msg = "F";
			}
		}

		try {
			PrintWriter pw = response.getWriter();
			pw.write(msg);
			pw.flush();
		} catch (IOException e) {
			logger
					.error(
							"MemberFlowPackageCfgController.queryByBegindate throws error",
							e);
		}
		return null;
	}

	public static String buildRemark(String userId, String codeStr,
			String remark) {
		if (StringUtil.isEmpty(remark)) {
			return "[" + codeStr + "]["
					+ DateUtil.formatDateTime("yyyy-MM-dd hh:mm:ss") + "]["
					+ userId + "];";
		}
		return "[" + codeStr + "][" + remark + "]["
				+ DateUtil.formatDateTime("yyyy-MM-dd hh:mm:ss") + "]["
				+ userId + "];";
	}

	/**
	 * 将字符串金额转成长整型
	 * 
	 * @param amountStr
	 * @return
	 */
	private static Long toLongAmount(String amountStr) {
		if (checkAmount(amountStr)) {
			return new BigDecimal(amountStr).multiply(new BigDecimal(1000))
					.longValue();
		}
		return 0L;
	}

	/**
	 * 验证金额是否合法
	 * 
	 * @param amountStr
	 * @return
	 */
	private static boolean checkAmount(String amountStr) {
		String AMOUNT_PATTERN = "^(0(\\.\\d{0,2})?|([1-9]+[0]*)+(\\.\\d{0,2})?)$";
		boolean ret = false;
		try {
			Pattern p = Pattern.compile(AMOUNT_PATTERN);
			Matcher m = p.matcher(amountStr);
			ret = m.matches();
		} catch (Exception e) {
			ret = false;
		}
		return ret;
	}

}
