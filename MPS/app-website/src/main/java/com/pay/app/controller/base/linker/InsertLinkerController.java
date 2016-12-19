/**
 *  File: InsertLinkerController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-21   lihua     Create
 *
 */
package com.pay.app.controller.base.linker;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.app.base.api.annotation.HasFeature;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.annotation.PutAppLog;
import com.pay.app.base.api.common.constans.CutsConstants;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.service.linker.LinkerService;
import com.pay.util.StringUtil;

/**
 * 添加联系人
 */
@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
public class InsertLinkerController extends MultiActionController {
	// 联系人基础服务
	private LinkerService linkerService;
	// 账户信息服务
	private MemberQueryService memberQueryService;

	// 成功
	private String success;
	private int TRUE = 1;

	/**
	 * 添加联系人
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_LINKER_INSERT")
	@PutAppLog(logType = CutsConstants.USER_LOG_ADDLINKER)
	public ModelAndView insertlinker(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String loginmemerCode = "";
		if (null != loginSession) {
			loginmemerCode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
		}
		LinkerDTO linkerDTO = new LinkerDTO();
		String[] chr = null;
		String[] chra = null;
		boolean boo = false;
		String linkername = null == request.getParameter("linkernameArray") ? ""
				: request.getParameter("linkernameArray");
		String realname = null == request.getParameter("realname") ? ""
				: request.getParameter("realname");
		realname = new String(realname.getBytes("ISO-8859-1"), "UTF-8");
		if (!"".equals(linkername) && !"".equals(realname)) {
			chr = linkername.split(",");
			chra = realname.split(",");
		}
		if (null != chr && chr.length > 0 && null != chra && chra.length > 0) {
			for (int i = 0; i < chr.length; i++) {
				MemberInfoDto memberInfoDto = memberQueryService
						.doQueryMemberInfoNsTx(String.valueOf(chr[i]), null,
								null,  AcctTypeEnum.BASIC_CNY.getCode());
				if (!StringUtil.isNull(memberInfoDto)) {
					linkerDTO.setLinkerId(memberInfoDto.getMemberCode());
					linkerDTO.setLinkerName(chr[i] + "");
					linkerDTO.setJoinDate(new Timestamp(new Date().getTime()));
					linkerDTO.setMemberCode(loginmemerCode);
					linkerDTO.setStatus(TRUE);
					linkerDTO.setDescription(memberInfoDto.getMemberName());
					boo = linkerService.verifylinkerrepeat(linkerDTO);
				}
				if (boo)
					linkerService.create(linkerDTO);
			}
		}
		String url = "/app/linkerinfo.htm";
		if(loginSession != null && loginSession.getScaleType() == 2){
		    // 企业会员返回页面.
		    url = "/corp/linkerinfo.htm";
		}
		return new ModelAndView(success).addObject("jumpUrl",
		    url).addObject("msgStr",
				MessageConvertFactory.getMessage("addlinkersuccess"));
	}

	/**
	 * 单个添加联系人( 要修改)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_LINKER_INSERT")
	public void addlinker(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String memberCode = null == request.getParameter("memberCode") ? ""
				: request.getParameter("memberCode");
		response.setContentType("text/plain;charset=UTF-8");
		String result = "";
		PrintWriter out = null;
		out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		LoginSession loginSession = SessionHelper.getLoginSession();
		String usermemberCode = "";
		if (null != loginSession) {
			usermemberCode = loginSession.getMemberCode();
		}
		MemberInfoDto memberInfoDto = null;
		LinkerDTO linkerDTO = new LinkerDTO();
		if (!StringUtil.isNull(memberCode)) {
			try{
			memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(null, Long
					.valueOf(memberCode), null,  AcctTypeEnum.BASIC_CNY.getCode());
			LinkerDTO linker = new LinkerDTO();
			linker.setMemberCode(usermemberCode);
			linker.setLinkerName(memberInfoDto.getLoginName());
			linker.setLinkerId(Long.valueOf(memberCode));
			boolean boo = linkerService.verifylinkerrepeat(linker);
			
			if (!StringUtil.isNull(memberInfoDto) && boo) {
				linkerDTO.setLinkerId(memberInfoDto.getMemberCode());
				linkerDTO.setLinkerName(memberInfoDto.getLoginName());
				linkerDTO.setJoinDate(new Timestamp(new Date().getTime()));
				linkerDTO.setDescription(memberInfoDto.getMemberName());
				linkerDTO.setStatus(1);
				linkerDTO.setMemberCode(usermemberCode);
				linkerDTO.setRemark("");
				Object obj = linkerService.create(linkerDTO);
				if (null != obj) {
					result = "Y";
				}
				} else {
					result = "N";
				}
			}catch(Exception e){
				result = "N";
			}
		}
		out.print(result);
		out.flush();
		out.close();
	}

	/**
	 * 添加联系人的验证
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void verifylinker(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		LinkerDTO linkerDTO = new LinkerDTO();
		String loginmembercode = "";
		String linkerinfo = "";
		PrintWriter out = null;
		out = response.getWriter();
		if (null != loginSession) {
			loginmembercode = null == loginSession.getMemberCode() ? ""
					: loginSession.getMemberCode();
		}

		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String linkerName = null == request.getParameter("linkerName") ? ""
				: request.getParameter("linkerName");

		if (!StringUtil.isNull(linkerName)) {
			linkerDTO.setMemberCode(loginmembercode);
			MemberInfoDto memberInfoDto = memberQueryService
					.doQueryMemberInfoNsTx(StringUtils.trim(linkerName), null, null,  AcctTypeEnum.BASIC_CNY.getCode());
			if (!StringUtil.isNull(memberInfoDto)) {// 验证用户是否有效和是否已在此联系人
				linkerDTO.setLinkerId(memberInfoDto.getMemberCode());
				boolean boo = linkerService.verifylinkerrepeat(linkerDTO);
				if (!memberInfoDto.getMemberCode().toString().equals(
						loginmembercode)) {// 不能添加自己为联系人
					if (boo) {
						// 添加会员状态: 正常和冻结
						if (memberInfoDto.getStatus() == MemberStatusEnum.NORMAL.getCode() ||  memberInfoDto.getStatus() == MemberStatusEnum.FROZEEN.getCode()) {// 未激活
							linkerinfo = memberInfoDto.getLoginName()
									+ ","
									+ URLEncoder.encode(memberInfoDto
                                            .getMemberName()==null?"":memberInfoDto
											.getMemberName(), "utf-8");

						} else {
							linkerinfo = "N3";// 用户异常，请联系客服
						}
					} else {
						linkerinfo = "N1";// 联系人已存在
					}

				} else {
					linkerinfo = "N2";// 不能添加自己
				}
			} else {
				linkerinfo = "N4";
			}
		}
		out.print(linkerinfo);
		out.flush();
		out.close();

	}

	public void setLinkerService(LinkerService linkerService) {
		this.linkerService = linkerService;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

}
