package com.pay.app.controller.base.matrixcard;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.dao.model.CheckCode;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.filter.AppFilterCommon;
import com.pay.app.service.messagebox.MessageBoxService;
import com.pay.base.common.helper.matrixcard.DESSecurityUtil;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.common.util.matrixcard.MatrixCardUtil;
import com.pay.base.controller.matrixcard.util.MatrixCardClientUtil;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.service.matrixcard.IMatrixCardService;
import com.pay.base.service.matrixcard.request.IMatrixCardReqNotifyService;
import com.pay.base.service.matrixcard.request.IMatrixCardReqService;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;
import com.pay.util.DateUtil;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public class MatrixCardRequestController extends MultiActionController {

	private Log logger = LogFactory.getLog(MatrixCardRequestController.class);
	private MatrixCardTransMgrService matrixCardTransMgrService;
	private IMatrixCardReqService matrixCardReqService;
	private IMatrixCardReqNotifyService matrixCardReqNotifyService;
	private IMatrixCardService matrixCardService;
	private MessageBoxService messageBoxService;

	// 安全中心口令卡首页
	String matrixcardIndex;

	// 申请首页
	String applyIndex;

	// 口令卡应用外部访问域名/ip
	String appAddr;

	// 介绍页 - 登录后
	String introView;

	// 介绍页 - 登录前
	String introViewNotLogon;

	// 信息提示页
	String msgView;

	// 信息提示页
	String msgViewNotLogon;

	// 输email地址页 - 登录后
	String inputAddrView;

	// 输email地址页 - 登录前
	String inputAddrViewNotLogon;

	// 申请成功页 - 登录后
	String reqSuccView;

	// 申请成功页 - 登录前
	String reqSuccViewNotLogon;
	// 设置口令卡申请附件模板
	private static final int EMAIL_TEMPLATEID = 12;

	/**
	 * 进入安全中心口令卡申请首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(matrixcardIndex);
	}

	/**
	 * 进入申请页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView applyCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(applyIndex);
	}

	/**
	 * 申请发放
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// 多次申请需要验证附加码
			// MatrixCardClientUtil.getOperatorInfo方法要求完善
			OperatorInfo operatorInfo = MatrixCardClientUtil.getOperatorInfo(request);
			// TODO 次数申请个常量
			// boolean requestTimes = matrixCardTransMgrService.beShowValidateCode(operatorInfo.getSessionId(), MatrixCardCfg.REQ_IP_LIMIT, 3);
			// if (requestTimes) {
			// 判断验证码是否正确
			String randCode = request.getSession().getAttribute("rand") == null ? "" : request.getSession().getAttribute("rand").toString();
			String code = request.getParameter("randCode") == null ? "" : request.getParameter("randCode");
			if ("".equals(randCode) || "".equals(code) || !code.equalsIgnoreCase(randCode)) { // 校验验证码
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("msgStr", MessageConvertFactory.getMessage("randCode"));
				return new ModelAndView(applyIndex, paraMap);
			}
			request.getSession().removeAttribute("rand");
			// }
			// 检查输入email是否合法
			String emailAddr = request.getParameter("email");
			if (!StringUtil.isEmpty(emailAddr) && !IDContentUtil.validateEmail(emailAddr)) { // TODO
				Map<String, Object> paraMap = new HashMap<String, Object>();
				paraMap.put("msgStr", MessageConvertFactory.getMessage("emailMsg"));
				return new ModelAndView(applyIndex, paraMap);
			}

			MatrixCardTransInfoDto transInfoDto = createRequestData(request);
			Long memberCode = operatorInfo.getMemberCode();
			MatrixCardDto matrixCardDto = null;
			ResultDto resultDto = null;

			if (AppFilterCommon.isSecurityLogin(request)) {
				// 判断该会员是否已有绑定卡，没有则判断是否有申请口令卡
				if (!matrixCardService.isBindByMemberCode(memberCode)) {
					resultDto = matrixCardReqService.showRequestMatrixCard(memberCode);
				}
			}
			if (null == resultDto || StringUtils.isNotEmpty(resultDto.getErrorCode())) {
				resultDto = matrixCardReqService.processRequest(transInfoDto);
			}

			if (null != resultDto && null != resultDto.getErrorCode()) {
				// TODO 失败后的处理
				return new ModelAndView(applyIndex).addObject("msgStr", resultDto.getErrorMsg());
			}
			matrixCardDto = (MatrixCardDto) resultDto.getObject();

			String imgId = DESSecurityUtil.encrypt(matrixCardDto.getId() + "|" + matrixCardDto.getSerialNo());

			// TODO 发送email通知
			if (!StringUtil.isEmpty(emailAddr) && IDContentUtil.validateEmail(emailAddr)) {
				List<String> listAddress = new ArrayList<String>();
				listAddress.add(emailAddr);
				Date lastValidDate = MatrixCardUtil.getBindLastValidDate(matrixCardDto.getCreationDate());
				String url = MessageConvertFactory.getWebsiteContextPath() + "app/matrixCardBind.htm?method=checkCodeView&checkCode=";// TODO 绑定的地址
				CheckCode checkCode = new CheckCode();
				if (null == memberCode) {
					memberCode = 0L;
				}
				checkCode.setMemberCode(memberCode);
				checkCode.setEmail(emailAddr);
				checkCode.setOrigin(CheckCodeOriginEnum.MATRIX_REQUEST.getValue());
				matrixCardReqNotifyService.sendReqAttachmentEmail(checkCode, lastValidDate, listAddress, MessageConvertFactory.getMessage("matrixCardRequestMsg"), url, EMAIL_TEMPLATEID, imgId);
			}
			imgId = URLEncoder.encode(imgId, "utf8");
			// 有效期
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, 30);
			String dateTo = DateUtil.formatDateTime("yyyy/MM/dd", new Date(c.getTimeInMillis()));
			if (AppFilterCommon.isSecurityLogin(request)) {
				messageBoxService.sendMessageBox(MessageConvertFactory.getMessage("matrixCardRequestSuccess"), memberCode.toString(),MessageConvertFactory.getMessage("matrixCardRequestBind"));
				return new ModelAndView(getReqSuccView()).addObject("imgId", imgId).addObject("emailAddr", emailAddr).addObject("dateTo", dateTo);
			}
			else {
				return new ModelAndView(getReqSuccViewNotLogon()).addObject("imgId", imgId).addObject("emailAddr", emailAddr).addObject("dateTo", dateTo);
			}
		}
		catch (Exception mcException) {
			logger.error("matrixCardClientUtil throws exception", mcException);
			String msgView = AppFilterCommon.isSecurityLogin(request) ? getMsgView() : this.getMsgViewNotLogon();
			ModelAndView view = new ModelAndView(msgView).addObject("message", mcException.getMessage()).addObject("messageExt", "");
			String backUrl = "";
			if (AppFilterCommon.isSecurityLogin(request)) {
				backUrl = "/matrixcard/request.htm?method=inputAddr";
			}
			else {
				backUrl = "/common/matrixcard/request.htm?method=inputAddr";
			}
			view.addObject("backUrl", backUrl);
			return view;
		}
	}

	private MatrixCardTransInfoDto createRequestData(HttpServletRequest request) {
		MatrixCardTransInfoDto transInfoDto = new MatrixCardTransInfoDto();
		OperatorInfo operatorInfo = MatrixCardClientUtil.getOperatorInfo(request);
		transInfoDto.setCreationDate(new Date(System.currentTimeMillis()));
		transInfoDto.setIp(operatorInfo.getIp());
		transInfoDto.setSessionId(operatorInfo.getSessionId());
		transInfoDto.setMemberCode(operatorInfo.getMemberCode());
		transInfoDto.setU_id(operatorInfo.getU_id());
		return transInfoDto;
	}

	public MatrixCardTransMgrService getMatrixCardTransMgrService() {
		return matrixCardTransMgrService;
	}

	public void setMatrixCardTransMgrService(MatrixCardTransMgrService matrixCardTransMgrService) {
		this.matrixCardTransMgrService = matrixCardTransMgrService;
	}

	public IMatrixCardReqService getMatrixCardReqService() {
		return matrixCardReqService;
	}

	public void setMatrixCardReqService(IMatrixCardReqService matrixCardReqService) {
		this.matrixCardReqService = matrixCardReqService;
	}

	public String getMatrixcardIndex() {
		return matrixcardIndex;
	}

	public void setMatrixcardIndex(String matrixcardIndex) {
		this.matrixcardIndex = matrixcardIndex;
	}

	public String getApplyIndex() {
		return applyIndex;
	}

	public void setApplyIndex(String applyIndex) {
		this.applyIndex = applyIndex;
	}

	public String getAppAddr() {
		return appAddr;
	}

	public void setAppAddr(String appAddr) {
		this.appAddr = appAddr;
	}

	public String getIntroView() {
		return introView;
	}

	public void setIntroView(String introView) {
		this.introView = introView;
	}

	public String getIntroViewNotLogon() {
		return introViewNotLogon;
	}

	public void setIntroViewNotLogon(String introViewNotLogon) {
		this.introViewNotLogon = introViewNotLogon;
	}

	public String getMsgView() {
		return msgView;
	}

	public void setMsgView(String msgView) {
		this.msgView = msgView;
	}

	public String getMsgViewNotLogon() {
		return msgViewNotLogon;
	}

	public void setMsgViewNotLogon(String msgViewNotLogon) {
		this.msgViewNotLogon = msgViewNotLogon;
	}

	public String getInputAddrView() {
		return inputAddrView;
	}

	public void setInputAddrView(String inputAddrView) {
		this.inputAddrView = inputAddrView;
	}

	public String getInputAddrViewNotLogon() {
		return inputAddrViewNotLogon;
	}

	public void setInputAddrViewNotLogon(String inputAddrViewNotLogon) {
		this.inputAddrViewNotLogon = inputAddrViewNotLogon;
	}

	public String getReqSuccView() {
		return reqSuccView;
	}

	public void setReqSuccView(String reqSuccView) {
		this.reqSuccView = reqSuccView;
	}

	public String getReqSuccViewNotLogon() {
		return reqSuccViewNotLogon;
	}

	public void setReqSuccViewNotLogon(String reqSuccViewNotLogon) {
		this.reqSuccViewNotLogon = reqSuccViewNotLogon;
	}

	public void setMatrixCardReqNotifyService(IMatrixCardReqNotifyService matrixCardReqNotifyService) {
		this.matrixCardReqNotifyService = matrixCardReqNotifyService;
	}

	public void setMatrixCardService(IMatrixCardService matrixCardService) {
		this.matrixCardService = matrixCardService;
	}
	public void setMessageBoxService(MessageBoxService messageBoxService) {
    	this.messageBoxService = messageBoxService;
    }
}
