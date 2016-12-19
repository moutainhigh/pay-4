package com.pay.app.controller.base.subscribe;

import java.beans.PropertyDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.dto.SubscribeNotifyDto;
import com.pay.app.dto.SubscribeTypeDto;
import com.pay.app.service.subscribe.SubscribeNotifyService;
import com.pay.app.service.subscribe.SubscribeTypeService;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.service.member.MemberService;

public class SubscribeNotifyController extends MultiActionController {

	private SubscribeTypeService subscribeTypeService;
	private SubscribeNotifyService subscribeNotifyService;
	private MemberService memberService;

	// 引导使用界面
	String subscribeTypeView;

	/**
	 * 进入安全中心口令卡申请首页
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return showSubscribeView(request, response);
	}

	/**
	 * 显示绑定页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView showSubscribeView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			Long memberCode = Long.parseLong(loginSession.getMemberCode());
			MemberInfoDto infoDto = memberService.queryMemberInfoByMemberCodeNsTx(memberCode);
			boolean isEmail = false;
			boolean isMobile = false;
			if (null != infoDto) {
				String loginName = infoDto.getLoginName();
				if (loginName.indexOf("@") > 0) {
					isEmail = true;
				}
				else {
					isMobile = true;
				}
				if (!isEmail && StringUtils.isNotEmpty(infoDto.getEmail())) {
					isEmail = true;
				}
				if (!isMobile && StringUtils.isNotEmpty(infoDto.getMobile())) {
					isMobile = true;
				}
			}

			SubscribeTypeDto dto = new SubscribeTypeDto();
			dto.setBeginNum(0);
			dto.setEndNum(100);
			List<SubscribeTypeDto> dtoList = subscribeTypeService.querySubscribeTypeDtoList(dto, memberCode);
			return new ModelAndView(subscribeTypeView).addObject("subscribeList", dtoList).addObject("isEmail", isEmail).addObject("isMobile",isMobile);
		}
		catch (Exception mcException) {
			return new ModelAndView(subscribeTypeView);
		}
	}

	@Override
	protected void bind(HttpServletRequest request, Object command) throws Exception {
		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(command.getClass());
		for (PropertyDescriptor pd : pds) {
			Class<?> clas = pd.getPropertyType();
			boolean isSimpleProperty = BeanUtils.isSimpleProperty(clas);
			boolean isInterface = clas.isInterface();
			boolean hasConstruct = clas.getConstructors().length == 0 ? false : true;
			if (!isSimpleProperty && !isInterface && hasConstruct) {
				pd.getWriteMethod().invoke(command, clas.newInstance());
			}
		}
		super.bind(request, command);
	}

	public void saveSubscribeView(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String flag = null;
		try {
			List<SubscribeNotifyDto> dtoList = new ArrayList<SubscribeNotifyDto>();
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			response.setContentType("text/plain;charset=UTF-8");
			Long memberCode = Long.parseLong(loginSession.getMemberCode());
			String[] notifyIds = request.getParameterValues("notifyId");
			String[] noticeModes = request.getParameterValues("noticeMode");
			String[] context = request.getParameterValues("context");
			for (int i = 0; i < notifyIds.length; i++) {
				SubscribeNotifyDto dto = new SubscribeNotifyDto();
				dto.setNoticeMode(Long.parseLong(noticeModes[i]));
				dto.setType(Long.parseLong(notifyIds[i]));
				dto.setMemberCode(memberCode);
				dto.setContext(context[i]);
				dto.setStatus(1L);
				dtoList.add(dto);
			}
			subscribeNotifyService.saveSubscribeNotifyRdTx(dtoList);
			// response.
			flag = "保存成功";
			// return new ModelAndView("redirect:/app/subscribe.htm?method=showSubscribeView").addObject("msg","保存成功");
		}
		catch (Exception e) {
			flag = "保存失败";
			// return new ModelAndView("redirect:/app/subscribe.htm?method=showSubscribeView");
		}
		PrintWriter out = null;
		out = response.getWriter();
		out.print(flag);
		out.flush();
		out.close();

	}

	public void setSubscribeTypeView(String subscribeTypeView) {
		this.subscribeTypeView = subscribeTypeView;
	}

	public void setSubscribeTypeService(SubscribeTypeService subscribeTypeService) {
		this.subscribeTypeService = subscribeTypeService;
	}

	public void setSubscribeNotifyService(SubscribeNotifyService subscribeNotifyService) {
		this.subscribeNotifyService = subscribeNotifyService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
