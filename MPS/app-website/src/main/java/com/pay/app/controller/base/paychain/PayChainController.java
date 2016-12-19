package com.pay.app.controller.base.paychain;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.common.enums.TokenEnum;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.controller.common.UploadHelper;
import com.pay.app.controller.common.UploadStatistics;
import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.base.common.enums.EffectiveTypeEnum;
import com.pay.base.common.enums.PayChainEnum;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.model.PayChain;
import com.pay.base.service.common.ImgResonse;
import com.pay.base.service.contextPic.ContextPicService;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.enterprise.EnterpriseContactService;
import com.pay.base.service.paychain.PayChainService;
import com.pay.util.CheckUtil;
import com.pay.util.DateUtil;

public class PayChainController extends MultiActionController {
	private static final Log logger = LogFactory
			.getLog(PayChainController.class);

	private PayChainService payChainService;
	private EnterpriseContactService enterpriseContactService;
	private EnterpriseBaseService enterpriseBaseService;
	private BankAcctServiceFacade provinceServiceFacade;
	private ContextPicService contextPicService;
	private String generateIndex;
	private String generateConfirm;
	private String generateSuccess;
	private String generateFaile;
	private String preview;
	private String generateRepeat;

	// 支付链收款首页
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String projectDesc = request.getParameter("projectDesc");
		String projectName = request.getParameter("projectName");
		paraMap.put("projectName", projectName);
		paraMap.put("projectDesc", projectDesc);
		paraMap.put("upImgList", getImgList());
		return new ModelAndView(generateIndex, paraMap);
	}

	private LinkedList<ImgResonse> getImgList() {
		LinkedList<ImgResonse> imgList = new LinkedList<ImgResonse>();
		UploadStatistics up = UploadHelper.getUploadStatistics();
		if (up != null)
			imgList = up.getImgList();
		return imgList;
	}

	// 生成支付链收款
	public ModelAndView generate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String projectDesc = request.getParameter("projectDesc");
		String projectName = request.getParameter("projectName");
		String compareToken = request.getParameter("compareToken");
		String effectiveType = request.getParameter("effectiveType");
		if (!SessionHelper.validateToken(TokenEnum.PAY_CHAIN, compareToken)) {
			return new ModelAndView(generateRepeat);
		}
		Map<String, Object> paraMap = new HashMap<String, Object>();
		ModelAndView mv = this.validateProject(paraMap, generateIndex,
				projectName, projectDesc, effectiveType);
		if (mv != null)
			return mv;
		Long payChainId = null;
		int effectiveDate = Integer.valueOf(effectiveType);
		paraMap.put("projectDesc", projectDesc);
		paraMap.put("projectName", projectName);
		try {
			Long memberCode = Long.valueOf(SessionHelper
					.getMemeberCodeBySession());
			PayChain pc = new PayChain();
			pc.setMemberCode(memberCode);
			String payChainNum = payChainService.generatePayChainNum();
			pc.setPayChainNumber(payChainNum);
			String payChainUrl = this.generateUrl(request, payChainNum);
			pc.setPayChainUrl(payChainUrl);
			pc.setReceiptDesc(projectDesc);
			pc.setPayChainName(projectName);
			pc.setStatus(PayChainEnum.EFFECT.getValue());
			pc.setEffectiveDate(effectiveDate);
			Date overDate = payChainService.getOverDate(EffectiveTypeEnum
					.getDatsByValue(effectiveDate));
			pc.setOverdueDate(overDate);
			payChainId = payChainService.createPayChain(pc);
			UploadStatistics up = UploadHelper.getUploadStatistics();
			if (up != null && up.getIdList() != null) {// 绑定已上传的图片
				if (contextPicService.batchUpdateOwner(up.getIdList(),
						payChainId)) {
					UploadHelper.clearUploadNum();
				}
			}
			paraMap.put("effectiveDesc",
					EffectiveTypeEnum.getMemoByValue(effectiveDate));
			paraMap.put("payChainUrl", getFullUrl(request, payChainUrl));
			paraMap.put("serverUrl", getServerUrl(request));
			paraMap.put("overDate", overDate);
		} catch (Exception e) {
			logger.error("PayChainController.generate throws error", e);
		}
		if (payChainId != null)
			return new ModelAndView(generateSuccess, paraMap);
		return new ModelAndView(generateFaile, paraMap);
	}

	// 确认支付链收款
	public ModelAndView confirm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String projectDesc = request.getParameter("projectDesc");
		String projectName = request.getParameter("projectName");

		ModelAndView mv = this.validateProject(paraMap, generateIndex,
				projectName, projectDesc, "1");
		if (mv != null)
			return mv;
		paraMap.put("effectiveList", EffectiveTypeEnum.SEARCH_TYPES);
		paraMap.put("projectDesc", projectDesc);
		paraMap.put("projectName", projectName);
		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession());
		paraMap.put("compareToken", SessionHelper.setToken(TokenEnum.PAY_CHAIN));
		paraMap.put("upImgList", getImgList());
		return new ModelAndView(generateConfirm, this.generateParaMap(
				memberCode, paraMap));
	}

	// 预览支付链收款
	public ModelAndView preview(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		String projectDesc = request.getParameter("projectDesc");
		String projectName = request.getParameter("projectName");
		String effectiveType = request.getParameter("effectiveType");
		ModelAndView mv = this.validateProject(paraMap, generateIndex,
				projectName, projectDesc, effectiveType);
		if (mv != null)
			return mv;

		Long memberCode = Long.valueOf(SessionHelper.getMemeberCodeBySession());
		paraMap.put("projectDesc", projectDesc);
		paraMap.put("projectName", projectName);
		paraMap.put("effectiveDesc", EffectiveTypeEnum.getMemoByValue(Integer
				.valueOf(effectiveType)));
		paraMap.put("chainNum", payChainService.getNextPayChainNum());
		paraMap.put("upImgList", getImgList());
		return new ModelAndView(preview, this.generateParaMap(memberCode,
				paraMap));
	}

	private ModelAndView validateProject(Map<String, Object> paraMap,
			String resultView, String projectName, String projectDesc,
			String effectiveType) {
		ResultDto rd1 = validateProjectName(projectName, effectiveType);

		ResultDto rd2 = validateProjectDesc(projectDesc, effectiveType);
		if (rd1.isResultStatus() && rd2.isResultStatus()) {
			return null;
		}
		if (!rd1.isResultStatus()) {
			paraMap.put("errMsg", rd1.getErrorMsg());
		} else if (!rd2.isResultStatus()) {
			paraMap.put("errMsg", rd2.getErrorMsg());
		}

		return new ModelAndView(resultView, paraMap);
	}

	private ResultDto validateProjectName(String projectName,
			String effectiveType) {
		ResultDto rd = new ResultDto();
		rd.setResultStatus(false);
		if (StringUtils.isBlank(projectName)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("paychainProjectNameEmpty"));
		} else if (!CheckUtil.checkStringLength(projectName, 40)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("paychainNameToLong"));
		} else if (StringUtils.isBlank(effectiveType)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("effectiveTypeEmpty"));
		} else if (!StringUtils.isNumeric(effectiveType)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("effectiveTypeEmpty"));
		} else if (projectName.contains("|") || projectName.contains("<")
				|| projectName.contains(">")) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("paychainNameHasIllage"));
		} else {
			rd.setResultStatus(true);
		}
		return rd;
	}

	private ResultDto validateProjectDesc(String projectDesc,
			String effectiveType) {
		ResultDto rd = new ResultDto();
		rd.setResultStatus(false);
		if (StringUtils.isBlank(projectDesc)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("paychainProjectDescEmpty"));
		} else if (!CheckUtil.checkStringLength(projectDesc, 200)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("paychainDescToLong"));
		} else if (StringUtils.isBlank(effectiveType)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("effectiveTypeEmpty"));
		} else if (!StringUtils.isNumeric(effectiveType)) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("effectiveTypeEmpty"));
		} else if (projectDesc.contains("|") || projectDesc.contains("<")
				|| projectDesc.contains(">")) {
			rd.setErrorMsg(MessageConvertFactory
					.getMessage("paychainDescHasIllage"));
		} else {
			rd.setResultStatus(true);
		}
		return rd;
	}

	private Map<String, Object> generateParaMap(Long memberCode,
			Map<String, Object> paraMap) {
		EnterpriseBase enterpriseBase = enterpriseBaseService
				.findByMemberCode(memberCode);
		EnterpriseContact enterpriseContact = enterpriseContactService
				.findByMemberCode(memberCode);
		StringBuffer address = new StringBuffer();
		String region = enterpriseBase.getRegion();
		String city = enterpriseBase.getCity();
		if (StringUtils.isNotBlank(region)) {
			region = provinceServiceFacade.getProvinceById(Integer
					.valueOf(region));
			address.append(region).append("&nbsp;&nbsp;");
		}
		if (StringUtils.isNotBlank(city)) {
			city = provinceServiceFacade.getCityById(Integer.valueOf(city));
			address.append(city).append("&nbsp;&nbsp;");
		}
		if (StringUtils.isNotEmpty(enterpriseContact.getAddress())) {
			address.append(enterpriseContact.getAddress());
		}
		if (StringUtils.isNotEmpty(enterpriseContact.getZip())) {
			address.append("&nbsp;(" + enterpriseContact.getZip() + ")");
		}
		paraMap.put("address", address.toString());
		paraMap.put("enterpriseContact", enterpriseContact);
		paraMap.put("enterpriseBase", enterpriseBase);
		return paraMap;

	}

	/**
	 * 关闭支付链,chainNum必须传
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author DDR
	 */
	public ModelAndView closePayChain(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("closePayChain.....");
		String result = "参数不正确";
		try {
			String payChainNumer = ServletRequestUtils.getStringParameter(
					request, "chainNum", "");
			Long memberCode = Long.valueOf(SessionHelper.getLoginSession()
					.getMemberCode());

			if (payChainNumer.trim().length() > 1) {

				PayChain pc = payChainService
						.getPayChainByChainNum(payChainNumer);
				if (pc == null || pc.getMemberCode().longValue() != memberCode) {
					result = "无权关闭此支付链接";
				} else if (pc.getStatus().intValue() == 2) {
					result = "此支付链已经关闭";
				} else {
					boolean resultBool = payChainService
							.closePayChainRdTx(payChainNumer);
					result = resultBool ? "S" : "支付链接关闭失败";
				}
			}
		} catch (Exception e) {
			result = "关闭支付链接出现异常";
			logger.error("关闭支付链接出现异常", e);
			e.printStackTrace();
		}
		logger.info(result);
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(result);
		return null;
	}

	/**
	 * 更新支付链有效期
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateEffDate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("updateEffDate.....");
		String result = "参数不正确";
		try {
			String payChainNumer = ServletRequestUtils.getStringParameter(
					request, "chainNum", "");
			int effType = ServletRequestUtils.getIntParameter(request,
					"effType", -1);
			Long memberCode = Long.valueOf(SessionHelper.getLoginSession()
					.getMemberCode());
			if (payChainNumer.trim().length() > 1) {
				PayChain pc = payChainService
						.getPayChainByChainNum(payChainNumer);
				if (pc == null || pc.getMemberCode().longValue() != memberCode) {
					result = "无权修改此支付链接";
				} else if (pc.getStatus().intValue() == 2) {
					result = "此支付链已经关闭";
				} else {

					int effDays = EffectiveTypeEnum.getDatsByValue(effType);
					Date overDate = DateUtil.skipDateTime(pc.getCreateDate(),
							effDays);
					if (overDate.getTime() < new Date().getTime()) {
						result = "修改有效期小于当前系统时间，请选择更大的有效期";
					} else {
						String endDate = DateUtil.formatDateTime(
								"yyyy-MM-dd HH:mm:ss", overDate);
						boolean resultBool = payChainService
								.updateEffectiveDate(payChainNumer, effType);
						result = resultBool ? ("S," + endDate) : result;
					}

				}
			}
		} catch (Exception e) {
			result = "更新支付链接有效时间出现异常";
			logger.error("更新支付链接有效时间出现异常", e);
			e.printStackTrace();
		}
		logger.info(result);
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(result);

		return null;
	}

	private String generateUrl(HttpServletRequest request, String payChainNum) {
		return AppConf.get(AppConf.payChainUrl) + "?n="
				+ payChainService.encryptChainNum(payChainNum);
	}

	private String getFullUrl(HttpServletRequest request, String payChainUrl) {// +":"+request.getServerPort()
		String path = request.getContextPath();
		int port = request.getServerPort();
		String portStr = "";
		if (port != 80  && port != 443) {
			portStr = ":" + port;
		}
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ portStr + path + "/";
		return (basePath + payChainUrl);
	}

	private String getServerUrl(HttpServletRequest request) {// +":"+request.getServerPort()

		String basePath = request.getScheme() + "://" + request.getServerName();
		return basePath;
	}

	public void setPayChainService(PayChainService payChainService) {
		this.payChainService = payChainService;
	}

	public void setGenerateIndex(String generateIndex) {
		this.generateIndex = generateIndex;
	}

	public void setGenerateConfirm(String generateConfirm) {
		this.generateConfirm = generateConfirm;
	}

	public void setGenerateSuccess(String generateSuccess) {
		this.generateSuccess = generateSuccess;
	}

	public void setGenerateFaile(String generateFaile) {
		this.generateFaile = generateFaile;
	}

	public void setPreview(String preview) {
		this.preview = preview;
	}

	public void setEnterpriseContactService(
			EnterpriseContactService enterpriseContactService) {
		this.enterpriseContactService = enterpriseContactService;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setProvinceServiceFacade(
			BankAcctServiceFacade provinceServiceFacade) {
		this.provinceServiceFacade = provinceServiceFacade;
	}

	public void setContextPicService(ContextPicService contextPicService) {
		this.contextPicService = contextPicService;
	}

	public void setGenerateRepeat(String generateRepeat) {
		this.generateRepeat = generateRepeat;
	}

}
