package com.pay.app.controller.base.login;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.cert.model.CertManage;
import com.pay.app.base.api.annotation.PutAppLog;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.activation.NotActivation;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.controller.common.UserHelper;
import com.pay.app.dto.AnnouncementDTO;
import com.pay.app.filter.AppFilterCommon;
import com.pay.app.service.announcement.AnnouncementService;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.common.enums.TargetsEnums;
import com.pay.base.dao.platformmember.PlatformMemberDao;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.model.Advertisement;
import com.pay.base.model.Operator;
import com.pay.base.model.PlatformMember;
import com.pay.base.service.advertise.AdvertiseService;
import com.pay.base.service.matrixcard.login.MatrixCardLoginService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.base.service.platformmember.PlatformMemberService;
import com.pay.base.service.user.UserLoginService;
import com.pay.util.DateUtil;
import com.pay.util.RSAHelper;
import com.pay.util.SignatureHelper;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-7-19 下午06:41:29 登陆相关控制
 */
public class MemberController extends MultiActionController {

	/**
	 * 判断是否从商城登录过来的service
	 */
	private NotActivation notActivation;

	private MatrixCardLoginService matrixCardLoginService;

	private OperatorServiceFacade operatorServiceFacade;
	/** 用户登录服务 */
	private UserLoginService userLoginService;

	// private CertService certService;

	private UserHelper userHelper;

	private String malllogin;

	private String individualSecurityLogout;

	private String enterpriceSecurityLogout;

	private String enterpriceLogout;

	private String individualLogout;

	/**
	 * log
	 */
	private static final Log log = LogFactory.getLog(MemberController.class);

	// 万能验证码
	private static final String PER_CODE = "6a204bd89f3c8348afd5c77c717a097a";

	private static final String FLAGACTIVE = "0"; // 1表示会员已激活，0表示会员未激活.

	private static final String FLAGACTIVESUCESS = "1";

	private final static String REGTYPE_EMAIL = "2";
	/** 临时会员 */
	private final static String MEMBERSTATUS_LINSHI = "5";

	private final static String ORGIN_FROM_MALL = "relationlogin";

	private final static String ORGIN_FROM_PUBLIC = "publiclogin";

	private final static String ORGIN_FROM_IFRAME = "iframelogin";

	private final static String ORGIN_FROM_SECURITY = "security";

	// private final static String ORGIN_FROM_SECURITY_PUBLIC="securityPublic";

	private final static String GO_ACTIVE = "memberActive.htm";

	private AnnouncementService announcementService;

	private String autoJump;

	private String autoCertJump;

	private String companyIndex;

	private static final String GET_METHOD_PREFIX = "POST";

	private static final Integer AVAIL_ADVERTISE_YES = 1;

	private AdvertiseService advertiseService;

	private PlatformMemberService platformMemberService;

	/**
	 * 处理用户登陆 1.根据USERID获取MEMBERCODE (已激活|未激活|已冻结) 2.根据MEMBERCODE查询账户状态(只导入)
	 * 3.账户信息查询 4.根据账户状态进行是否需要激活处理 5.权限菜单查询&处理 6.获得未读消息 7.上次登录时间 8.系统登录日志
	 */
	@PutAppLog(logType = CutsConstants.USER_LOG_LOGIN)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {

		boolean isSecurity = false;

		PlatformMember platformMember = new PlatformMember();
		List<PlatformMember> list1 = null;
		String coded = request.getParameter("rand_Code");
		// 设置Session过期，并且复制原来的session-------start
		log.debug("member login sessionId is :#####################" + request.getSession().getId());
		HttpSession session = request.getSession();

		Enumeration<String> enums = session.getAttributeNames();
		Map<String, Object> sessionMap = new HashMap<String, Object>();
		String elName = null;
		while (enums.hasMoreElements()) {
			elName = enums.nextElement();
			log.debug("get session attribute name = [" + elName + "],value=[" + session.getAttribute(elName) + "]");
			if (elName.equals(AppConf.sessionCorpJumpMap) || elName.equals(AppConf.sessionJumpMap)
					|| elName.equals("hascode") || elName.equals("rand") || elName.equals("loginNumSession"))
				sessionMap.put(elName, session.getAttribute(elName));
		}
		// request.getSession().setMaxInactiveInterval(0);
		request.getSession().invalidate();
		HttpSession newSession = request.getSession();
		Set<String> sessionAttrKeys = sessionMap.keySet();
		for (String sessionAttrKey : sessionAttrKeys) {
			log.debug("copy session attribute name = [" + sessionAttrKey + "],value=[" + sessionMap.get(sessionAttrKey)
					+ "] to new session");
			newSession.setAttribute(sessionAttrKey, sessionMap.get(sessionAttrKey));
		}
		// 设置Session过期，并且复制原来的session-------end

		// String
		// randCode=request.getParameter("rand_code")==null?"":request.getParameter("");
		String loginName = request.getParameter("loginName") == null ? "" : request.getParameter("loginName").trim();
		String passWord = request.getParameter("passWord") == null ? "" : request.getParameter("passWord");
		String hasSecurity = request.getParameter("hasSecurity") == null ? "" : request.getParameter("hasSecurity");
		String orginFrom = request.getParameter("relationlogin") == null ? "" : request.getParameter("relationlogin");
		String hascode = request.getSession().getAttribute("hascode") == null ? ""
				: (String) request.getSession().getAttribute("hascode");// 判断是否有验证码
		String memberType = request.getParameter("memberType") == null ? "2" : request.getParameter("memberType");
		String operatorName = request.getParameter("operatorName") == null ? ""
				: request.getParameter("operatorName").trim();
		String returnUrl = request.getParameter("returnUrl");
		ModelAndView mv = new ModelAndView("index");
		// 如果是企业账户
		if ("2".equals(memberType)) {
			mv = new ModelAndView(companyIndex);
			mv.addObject("operatorName", operatorName);
		}

		// 确定登录信息错误时返回的页面 默认首页的登录页
		if (ORGIN_FROM_MALL.equals(orginFrom)) {
			// 验证错误之后返回到的页面
			mv = new ModelAndView(malllogin).addObject("payname", loginName).addObject("pkey",
					RSAHelper.public_key_String);
		} else {
			if (ORGIN_FROM_PUBLIC.equals(orginFrom)) {// 共用登录页
				if (String.valueOf(ScaleEnum.ENTERPRICE.getValue()).equals(memberType)) {
					mv = new ModelAndView(enterpriceSecurityLogout);
				} else {
					mv = new ModelAndView(individualSecurityLogout);
				}
			} else if (ORGIN_FROM_IFRAME.equals(orginFrom)) {

				if (String.valueOf(ScaleEnum.ENTERPRICE.getValue()).equals(memberType)) {
					if (StringUtils.isBlank(returnUrl)) {
						returnUrl = AppConf.defaultCallBack;
					}
					mv = new ModelAndView(enterpriceLogout);
				} else {
					if (StringUtils.isBlank(returnUrl)) {
						returnUrl = AppConf.defaultCorpCallBack;
					}
					mv = new ModelAndView(individualLogout);
				}
				mv.addObject("returnUrl", returnUrl);
			} else {
				int topnum = 8;
				List<AnnouncementDTO> list = announcementService.queryTopAnnouncement(topnum);
				if (String.valueOf(ScaleEnum.ENTERPRICE.getValue()).equals(memberType)) {
					// 企业首页广告幻灯片 改为和个人统一
					List<Advertisement> advertiseList = advertiseService
							.queryAdvertiseListByTargetsAvail(TargetsEnums.INDEX_ADV.getCode(), AVAIL_ADVERTISE_YES);
					mv.addObject("imgList", advertiseList);
				} else {
					List<Advertisement> advertiseList = advertiseService
							.queryAdvertiseListByTargetsAvail(TargetsEnums.INDEX_ADV.getCode(), AVAIL_ADVERTISE_YES);
					List<Advertisement> advertiseMerchantList = advertiseService
							.queryAdvertiseListByTargetsAvail(TargetsEnums.MERCHANT_ADV.getCode(), AVAIL_ADVERTISE_YES);
					mv.addObject("imgList", advertiseList);
					mv.addObject("merchantList", advertiseMerchantList);
				}
				mv.addObject("ggls", list);
			}
		}

		// 如果是安全控件输入的密码 要用私钥解密
		if (ORGIN_FROM_SECURITY.equals(hasSecurity)) {
			isSecurity = true;
			try {
				String pkey = RSAHelper.public_key_String;
				passWord = RSAHelper.getRSAString(passWord);
				mv.addObject("pkey", pkey);
			} catch (Exception e) {
				log.error("MemberController login Error!", e);
			}
			mv.addObject("isSecurity", isSecurity);
		}

		mv.addObject("memberType", memberType);
		log.debug("member login sessionId is :#####################" + request.getSession().getId());
		// 登录校验码验证
		if (request.getMethod().equals(GET_METHOD_PREFIX)) {
			String randCode = request.getSession().getAttribute("rand") == null ? ""
					: request.getSession().getAttribute("rand").toString();
			String code = request.getParameter("rand_Code") == null ? "" : request.getParameter("rand_Code");
			request.getSession().removeAttribute("rand");

			/*
			 * if ("".equals(randCode) || "".equals(code) ||
			 * !code.equalsIgnoreCase(randCode)) {
			 * request.getSession().removeAttribute("rand");
			 * mv.addObject("msgStr",MessageConvertFactory.getMessage("randCode"
			 * )); mv.addObject("hascode","1");
			 * request.getSession().setAttribute("hascode", "1"); return mv; }
			 */
			if (null != randCode && null != code && !"".equals(code) && !"".equals(randCode)) {
				if (code.length() != 4) {
					if (!code.equals(this.PER_CODE)) {
						return codeV(request, mv);
					}
				} else {
					if (!code.equalsIgnoreCase(randCode)) {
						return codeV(request, mv);
					}
				}
			} else {
				return codeV(request, mv);
			}

		}
		request.getSession().removeAttribute("rand");
		Map<String, Object> jMaps = AppFilterCommon.getCallBackUri(request, 1);
		ResultDto rDto = null;
		if ("2".equals(memberType)) {
			jMaps = AppFilterCommon.getCallBackUri(request, 2);
			rDto = userLoginService.enterpriceMemberLogin(loginName, operatorName, passWord);
		} else {
			rDto = userLoginService.individualMemberLogin(loginName, passWord);
		}

		MemberCriteria memberCriteria = null;
		memberCriteria = (MemberCriteria) rDto.getObject();
		if (rDto.getErrorCode() != null) {
			request.getSession().setAttribute("hascode", "1");
			mv.addObject("msgStr", rDto.getErrorMsg());
			return mv;
		}
		request.getSession().setAttribute("hascode", null);
		// CookieUtil ck=new CookieUtil();
		// ck.clearCookie(request, response);
		String jumpUrl = AppConf.defaultCallBack;

		Long memberCode = Long.valueOf(memberCriteria.getMemberCode());
		request.getSession().setAttribute("memberCode", memberCriteria.getMemberCode());
		request.getSession().setAttribute("loginName", loginName);
		request.getSession().setAttribute("verifyName", memberCriteria.getVerifyName());
		// 会员上次登录时间
		if (StringUtils.isNotBlank(memberCriteria.getLastLoginTime())) {
			request.getSession().setAttribute("epLastLoginTime", memberCriteria.getLastLoginTime());
		} else {
			request.getSession().setAttribute("epLastLoginTime", DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss"));
		}

		Map<String, String> pMaps = null;
		if (jMaps != null) {
			if ("2".equals(memberType)) {
				jumpUrl = jMaps.get(AppConf.actionCorpUrl) == null ? AppConf.defaultCorpCallBack
						: jMaps.get(AppConf.actionCorpUrl).toString();
				pMaps = (Map) jMaps.get(AppConf.jumpChildParam);
				request.getSession().removeAttribute(AppConf.sessionCorpJumpMap);
			} else {
				jumpUrl = jMaps.get(AppConf.actionUrl) == null ? AppConf.defaultCallBack
						: jMaps.get(AppConf.actionUrl).toString();
				pMaps = (Map) jMaps.get(AppConf.jumpChildParam);
				request.getSession().removeAttribute(AppConf.sessionJumpMap);
			}
		}

		if (memberCriteria.getStatus().equals(FLAGACTIVE) && this.isToActive(jumpUrl)) {
			if (REGTYPE_EMAIL.equals(memberCriteria.getRegType())) {
				return new ModelAndView("redirect:/reSendEmail.htm?method=toEmail");
			} else {
				return new ModelAndView("redirect:/reSendMessage.htm?method=toMobile");
			}
			// 检查是否为临时会员
		} else if (StringUtils.equals(MEMBERSTATUS_LINSHI, memberCriteria.getStatus())) {
			// 5状态为临时会员
			// 跳转至补全信息页面
			jumpUrl = "/toActiveMember.htm?loginName=" + loginName;
		} else if (FLAGACTIVESUCESS.equals(memberCriteria.getStatus())) {

			LoginSession loginSession = new LoginSession();
			loginSession.setVerifyName(memberCriteria.getVerifyName());
			loginSession.setSalutatory(memberCriteria.getSalutatory());
			loginSession.setUpdateDate(memberCriteria.getUpdateDate());
			loginSession.setActiveStatus(memberCriteria.getStatus());
			/*add by yangjian when membercode is bigger than 80000000000 it is
			 * platmember ,search the son_membercode when the  sums is bigger 
			 * than 0 the membercode will set the list(0) value
			 */
			if (memberCode > 80000000000L) {
				platformMember.setStatus(2);
				platformMember.setFather_member_code(memberCode);
				list1 = platformMemberService.getSonMemberByFatherCode(platformMember);
				if (list1.size() > 0) {
					request.getSession().setAttribute("memberCode", String.valueOf(list1.get(0).getSon_member_code()));
					request.getSession().setAttribute("fatermemberCode", memberCode);
					loginSession.setFathermemberCode(memberCode);
					loginSession.setMemberCode(String.valueOf(list1.get(0).getSon_member_code()));
					loginSession.setOperatorId(list1.get(0).getSon_operator_id());
				}else{
					loginSession.setFathermemberCode(memberCode);
					loginSession.setMemberCode(memberCriteria.getMemberCode());
				}
			} else {
				loginSession.setMemberCode(memberCriteria.getMemberCode());
			}

			loginSession.setLoginName(memberCriteria.getLoginName());
			loginSession.setServiceLevel(memberCriteria.getServiceLevel());
			loginSession.setMemberType(memberCriteria.getMemberType());
			Long operatorId = memberCriteria.getOperatorId();
			if (operatorId != null && operatorId > 0) {
				// 将操作员ID存入到session中
				// 根据操作员Name是否为admin判断是否为企业用户管理员身份，是则不将操作员ID存入到session中，否则将将操作员ID存入到session中.
				Operator operator = operatorServiceFacade.getOperatorById(operatorId);
				if (operator != null && !StringUtils.equals(operator.getName(), "admin")) {
					// 设置操作员ID
					loginSession.setOperatorId(operatorId);
				} else
					loginSession.setOperatorId(0L);

				loginSession.setOperatorExtId(operatorId);
				loginSession.setOperatorIdentity(operatorName);

			}

			MatrixCardDto mc = null;// matrixCardLoginService.findByBindMemberCode(Long.valueOf(memberCriteria.getMemberCode()));
			// 口令卡登录
			if (mc != null) {
				loginSession.setSerialNo(mc.getSerialNo());
				loginSession.setSecurityLvl(SecurityLvlEnum.UNMAXTRIX.getValue());
			} else {
				loginSession.setSecurityLvl(SecurityLvlEnum.NORMAL.getValue());
			}
			if ("2".equals(memberType)) {
				loginSession.setScaleType(ScaleEnum.ENTERPRICE.getValue());
				if (memberCriteria.getServiceLevel().equals(ServiceLevelEnum.TRADING_CENTER.getValue())) {

					loginSession = userHelper.handlerBspSession(memberCode, loginSession);
				}
				// loginSession.setOperatorId(memberCriteria.getOperatorId());
				if (jumpUrl.equals(AppConf.defaultCallBack))
					jumpUrl = AppConf.defaultCorpCallBack;
			} else {
				if (jumpUrl.equals(AppConf.defaultCorpCallBack))
					jumpUrl = AppConf.defaultCallBack;

			}
			SessionHelper.setLoginSession(loginSession);

			// 用户sessionId+ip 加签之后 放入session
			request.getSession().setAttribute("memberSignatureData",
					SignatureHelper.generateAppSignature(AppFilterCommon.getSignData()));
			log.debug("member login signa sessionId is :#####################" + request.getSession().getId());
		}
		if (ORGIN_FROM_MALL.equals(orginFrom)) {
			request.getSession().setAttribute("relationUser", memberCriteria.getMemberCode());
		}

		// ApplyCertResponse acResponse=certService.getMmeberCert(memberCode,
		// SessionHelper.getOperatorIdBySession());
		// if(SessionHelper.isCertUser() || acResponse!=null){
		// if(ORGIN_FROM_IFRAME.equals(orginFrom)){
		// return new
		// ModelAndView(autoCertJump).addObject(AppConf.actionUrl,returnUrl).addObject("acResponse",acResponse);
		// }else if(pMaps!=null){
		// return new
		// ModelAndView(autoCertJump).addObject(AppConf.actionUrl,jumpUrl).addObject("pMaps",pMaps).addObject("acResponse",acResponse);
		// }else{
		// return new
		// ModelAndView(autoCertJump).addObject(AppConf.actionUrl,jumpUrl).addObject("acResponse",acResponse);
		// }
		// }

		if (ORGIN_FROM_IFRAME.equals(orginFrom)) {
			return new ModelAndView(autoJump).addObject(AppConf.actionUrl, returnUrl);
		} else if (pMaps != null) {
			return new ModelAndView(autoJump).addObject(AppConf.actionUrl, jumpUrl).addObject("pMaps", pMaps);
		}
		if (memberCode > 80000000000L) {
			if (list1.size() > 0) {
				response.sendRedirect(request.getContextPath() + jumpUrl);

			} else if (list1.size() == 0) {
				response.sendRedirect(request.getContextPath() + "/corp/relation.do");
			}
		} else {
			response.sendRedirect(request.getContextPath() + jumpUrl);
		}
		return null;
	}

	/**
	 * @param request
	 * @param mv
	 * @return
	 */
	private ModelAndView codeV(HttpServletRequest request, ModelAndView mv) {
		request.getSession().removeAttribute("rand");
		mv.addObject("msgStr", MessageConvertFactory.getMessage("randCode"));
		mv.addObject("hascode", "1");
		request.getSession().setAttribute("hascode", "1");
		return mv;
	}

	private boolean isToActive(String jumpUri) {
		if (jumpUri.contains(GO_ACTIVE)) {
			return false;
		} else {
			return true;
		}

	}

	public void doCheckLocalCert(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String machineIdentifier = request.getParameter("machineId") == null ? "" : request.getParameter("machineId");
		String status = request.getParameter("status") == null ? String.valueOf(CertManage.StatusEnum.VALID.getValue())
				: request.getParameter("status");
		LoginSession loginSession = SessionHelper.getLoginSession();
		PrintWriter out = null;
		out = response.getWriter();
		boolean result = false;
		// if(loginSession!=null && StringUtils.isNotBlank(machineIdentifier)){
		// CertManageDto cmDto = null;
		// if(
		// String.valueOf(CertManage.StatusEnum.TEMP.getValue()).equals(status)
		// ){
		// cmDto=certQueryService.queryTempUsePalce(Long.valueOf(loginSession.getMemberCode()),
		// loginSession.getOperatorId(), machineIdentifier);
		// }else{
		// cmDto=certQueryService.queryUsePalce(Long.valueOf(loginSession.getMemberCode()),
		// loginSession.getOperatorId(), machineIdentifier);
		// }
		// if(cmDto!=null){
		// SessionHelper.setLocalInstallCert();
		// result=true;
		// }
		// }
		out.print(result);
		out.flush();
		out.close();
	}

	/************** set **********************/

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}

	public void setMatrixCardLoginService(MatrixCardLoginService matrixCardLoginService) {
		this.matrixCardLoginService = matrixCardLoginService;
	}

	public NotActivation getNotActivation() {
		return notActivation;
	}

	public void setNotActivation(NotActivation notActivation) {
		this.notActivation = notActivation;
	}

	public void setMalllogin(String malllogin) {
		this.malllogin = malllogin;
	}

	public void setAdvertiseService(AdvertiseService advertiseService) {
		this.advertiseService = advertiseService;
	}

	public void setEnterpriceLogout(String enterpriceLogout) {
		this.enterpriceLogout = enterpriceLogout;
	}

	public void setIndividualLogout(String individualLogout) {
		this.individualLogout = individualLogout;
	}

	public void setAutoJump(String autoJump) {
		this.autoJump = autoJump;
	}

	public void setCompanyIndex(String companyIndex) {
		this.companyIndex = companyIndex;
	}

	public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	public void setIndividualSecurityLogout(String individualSecurityLogout) {
		this.individualSecurityLogout = individualSecurityLogout;
	}

	public void setEnterpriceSecurityLogout(String enterpriceSecurityLogout) {
		this.enterpriceSecurityLogout = enterpriceSecurityLogout;
	}

	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	public void setUserHelper(UserHelper userHelper) {
		this.userHelper = userHelper;
	}

	public void setAutoCertJump(String autoCertJump) {
		this.autoCertJump = autoCertJump;
	}

	public void setPlatformMemberService(PlatformMemberService platformMemberService) {
		this.platformMemberService = platformMemberService;
	}

}
