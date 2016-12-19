/**
 * 
 */
package com.pay.wechat.controller.merchant;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fi.dto.PartnerWebsiteConfig;
import com.pay.fi.hession.CrosspayWebsiteConfigService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;
import com.pay.util.MD5Util;
import com.pay.util.MapUtil;
import com.pay.util.SpringControllerUtils;
import com.pay.wechat.model.MerchantCooperation;
import com.pay.wechat.model.QuestionBind;
import com.pay.wechat.model.SysUserMapper;
import com.pay.wechat.model.template.Data;
import com.pay.wechat.model.template.First;
import com.pay.wechat.model.template.Keyword;
import com.pay.wechat.model.template.Remark;
import com.pay.wechat.model.template.TemplateMessageMain;
import com.pay.wechat.service.merchant.MerchantService;
import com.pay.wechat.service.questionbind.QuestionBindService;
import com.pay.wechat.service.sysusermapper.SysUserMapperService;
import com.pay.wechat.thread.TokenThread;
import com.pay.wechat.util.LoadPropertiesUtil;
import com.pay.wechat.util.WeiXinUtil;

import freemarker.template.Template;

/**
 * 商户控制器
 * @author PengJiangbo
 *
 */
public class MerchantController extends MultiActionController {

	private static final Log logger = LogFactory.getLog(MerchantController.class) ; 
	//注入 
	private MerchantService merchantService ;
	
	private QuestionBindService questionBindService ;
	
	private CrosspayWebsiteConfigService crosspayWebsiteConfigService;
	
	private SysUserMapperService sysUserMapperService ;
	/**
	 * 添加商户了解合作信息
	 * @param request
	 * @param response
	 * @param merchantCooperation
	 * @return
	 */
	public ModelAndView understandCooperation(HttpServletRequest request, HttpServletResponse response, MerchantCooperation merchantCooperation){
		System.out.println(merchantCooperation);
		//String message = "" ;
		StringBuilder sb = new StringBuilder() ;
		try {
			this.merchantService.addMerchantCooperationInfo(merchantCooperation);
			//发送微信消息
			sb.append("姓名：").append(merchantCooperation.getMemberName()).append("\n") ;
			sb.append("公司名称：").append(merchantCooperation.getCompanyName()).append("\n");
			sb.append("手机号：").append(merchantCooperation.getTel()).append("\n") ;
			sb.append("经营范围：").append(merchantCooperation.getOperateScope()) ;
			Remark remark = new Remark() ;
			remark.setValue(sb.toString());
			remark.setColor("#173177");
			
			Keyword keyword1 = new Keyword() ;
			keyword1.setColor("#173177");
			keyword1.setValue("商户了解合作");
			
			Keyword keyword2 = new Keyword() ;
			keyword2.setColor("#173177");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd") ;
			keyword2.setValue(sdf.format(new Date()));
			
			First first = new First() ;
			first.setColor("#173177");
			first.setValue("主人，有新的商户来了！"); //"主人，有新的商户来了！\ue231\n"
			Data data = new Data() ;
			data.setFirst(first);
			data.setKeyword1(keyword1);
			data.setKeyword2(keyword2);
			data.setRemark(remark); 
			
			TemplateMessageMain message  = new TemplateMessageMain() ;
			message.setData(data);
			message.setTemplate_id(LoadPropertiesUtil.getTemplateId());
			message.setTopcolor("#FF0000");
			message.setTouser(LoadPropertiesUtil.getTouser());
			message.setUrl("http://www.baidu.com");
			
			String jsontStr = JSONObject.fromObject(message).toString() ;
			
			//System.out.println(jsontStr);
			int sendTemplateMessage = WeiXinUtil.sendTemplateMessage(jsontStr, TokenThread.accessToken.getToken()) ;
			logger.info("Send custom message result code:" + sendTemplateMessage);
			
			if(sendTemplateMessage == 0){
				return new ModelAndView("/wechat/back_to_merchant");
			}else{
				//需要错误页面return null ;String jsonStr = JSONObject.fromObject(cmt).toString() ;
				return new ModelAndView("/wechat/back_to_merchant");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//需要错误页面
			return null ;
		} 
	}
	
	/**
	 * 到达商户问题验证页面
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView toMerQueValidate(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String login_dpwd = request.getParameter("dpwd") ;
		if(null != login_dpwd && !"".equals(login_dpwd)){
			return new ModelAndView("/wechat/questionBind").addObject("login_dpwd", login_dpwd) ;
		}else{
			long result = 0 ;
			response.getWriter().print(result);
			return null ;
		}
		//return new ModelAndView("/wechat/questionBind") ;
	}
	
	/**
	 * 商户设置登录密码及商户安全问题绑定，完成账号绑定 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView doMerQbFormSub(HttpServletRequest request, HttpServletResponse response, QuestionBind questionBind){
		logger.info("request param questionBind......" + questionBind);
		String login_dpwd = request.getParameter("login_dpwd") ;
		if(null != login_dpwd && !"".equals(login_dpwd)){
			//-------------------------------------------------------------------
			SysUserMapper sysUserMapper = new SysUserMapper();
			LoginSession loginSession = SessionHelper.getLoginSession() ;
			sysUserMapper.setBindTime(new Date());
			sysUserMapper.setLogin_dpwd(MD5Util.md5Hex(login_dpwd));
			sysUserMapper.setLogin_name(loginSession.getLoginName());
			sysUserMapper.setMember_code(Long.valueOf(loginSession.getMemberCode()));
			sysUserMapper.setOpenID(loginSession.getOpenID());
			sysUserMapper.setStatu(1L);
			sysUserMapper.setScale_type(loginSession.getScaleType());
			//绑定
			long result = this.merchantService.addSysUserMapperInfo(sysUserMapper);
			if(result > 0){	//成功
				questionBind.setMemberCode(Long.valueOf(loginSession.getMemberCode()));
				questionBind.setOpenID(loginSession.getOpenID());
				this.questionBindService.addQuestionBindInfo(questionBind) ;
				return new ModelAndView("/wechat/bindOk") ;
				
			}else{	//失败
				return new ModelAndView("enterprise_login").addObject("bindFailed", "绑定失败！") ;
			}
		}else{
			return new ModelAndView("enterprise_login").addObject("bindFailed", "绑定失败！") ;
		}
		
	}
	
	/**
	 * 到过商户绑定完成之后，手势登录界面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toMerLoginBinded(HttpServletRequest request, HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession() ;
		String openID = "" ;
		if(null != loginSession){
			openID = loginSession.getOpenID() ;
		}
		return new ModelAndView("/wechat/enterprise_login_binded").addObject("openID", openID) ;
	}
	
	/**
	 * 到达商户忘记密码页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView merForgetDpwd(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/forgetDpwd") ;
	}
	
	/**
	 * 商户问题验证，密码重置
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView merDpwdReset(HttpServletRequest request, HttpServletResponse response, QuestionBind questionBind){
		logger.info("forget dpwd, do security question valid , parameter is [ ==questionBind== ]......." + questionBind);
		ModelAndView view = null; //new ModelAndView("/wechat/drawPwd") ;
		//view.addObject("resetDpwd","yes") ;
		LoginSession loginSession = SessionHelper.getLoginSession() ;
		//String memberCode = "" ;
		String openID = "" ;
		if(null != loginSession){
			openID = loginSession.getOpenID() ;
			//System.out.println("openID==============>>>." + openID);
		}
		QuestionBind questionBindQueryResult = this.questionBindService.findQuestionBindByMemberCode(openID) ;
		logger.info("--------------questionBindQueryResult......" + questionBindQueryResult);
		if(null != questionBindQueryResult){
			//问题验证成功
			if(questionBind.getQuestion1().equals(questionBindQueryResult.getQuestion1()) 
					&& questionBind.getQuestion2().equals(questionBindQueryResult.getQuestion2())
					&& questionBind.getQuestion3().equals(questionBindQueryResult.getQuestion3())
					&& questionBind.getQa1().equals(questionBindQueryResult.getQa1())
					&& questionBind.getQa2().equals(questionBindQueryResult.getQa2())
					&& questionBind.getQa3().equals(questionBindQueryResult.getQa3())){
				view = new ModelAndView("/wechat/drawPwd").addObject("resetDpwd","yes") ;
			}else{
				view = new ModelAndView("/wechat/forgetDpwd").addObject("inValid","问题校验失败！");
			}
		}
		return view ;
	}
	
	/**
	 * 禁止从非微信客户端进行商户绑定操作
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView noWeixinLogin(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/enterprise_login").addObject("msgStr","请在微信客户端登录！") ;
	}
	
	/**
	 * 商户重置登录密码
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView doResetDpwd(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String login_dpwd_reset = request.getParameter("dpwd") ;
		if(null != login_dpwd_reset && !"".equals("login_dpwd_reset")){
			int updateResult = this.sysUserMapperService.updateSysUserMapLoginDpwd(MD5Util.md5Hex(login_dpwd_reset)) ;
			logger.info("--------------updateResult->>>>>>>>" + updateResult);
			response.getWriter().print("resetOk");
		}else{
			response.getWriter().print("resetFail");
		}
		
		return null ;
	}
	/**
	 * 到过重置登录密码成功页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView resetDpwdOk(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/bindOk") ;
	}
//	/**
//	 * 商户设置登录密码
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException 
//	 */
//	public ModelAndView doMerDpwdSave(HttpServletRequest request, HttpServletResponse response) throws IOException{
//		String login_dpwd = request.getParameter("dpwd") ;
//		if(null != login_dpwd && !"".equals(login_dpwd)){
//			LoginSession loginSession = SessionHelper.getLoginSession() ;
//			//loginSession.set
//			
//			
//			//-------------------------------------------------------------------
////			SysUserMapper sysUserMapper = new SysUserMapper();
////			LoginSession loginSession = SessionHelper.getLoginSession() ;
////			sysUserMapper.setBindTime(new Date());
////			sysUserMapper.setLogin_dpwd(MD5Util.md5Hex(login_dpwd));
////			sysUserMapper.setLogin_name(loginSession.getLoginName());
////			sysUserMapper.setMember_code(Long.valueOf(loginSession.getMemberCode()));
////			sysUserMapper.setOpenID(loginSession.getOpenID());
////			sysUserMapper.setStatu(1L);
////			sysUserMapper.setScale_type(loginSession.getScaleType());
//			//绑定
//			//long result = this.merchantService.addSysUserMapperInfo(sysUserMapper);
//			//--------------------------------------------------------------------
//			
////			if(result > 0){	//成功
////				response.getWriter().print(0);
////			}else{	//失败
////				response.getWriter().print(1);
////			}
//		}
//		return null;
//	}
	
	/**
	 * 商户绑定成功后的手势登录
	 * @param request
	 * @param response
	 * @param openID
	 * @param dpwd
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView bindedLogin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/hmtl");
		String openIDReq = request.getParameter("openID") ;
		String dpwdReq = request.getParameter("dpwd") ;
		SysUserMapper sysUserMapper = merchantService.findSysUserMapperByOpenID(openIDReq) ;
		if(null != sysUserMapper){
			//匹配正确
			if(sysUserMapper.getLogin_dpwd().equals(MD5Util.md5Hex(dpwdReq))){
				LoginSession loginSession = null ;
				loginSession = SessionHelper.getLoginSession() ;
				if(null == loginSession){ //微信端的绑定端的session设置
					loginSession = new LoginSession() ;
					loginSession.setMemberCode(String.valueOf(sysUserMapper.getMember_code()));
					loginSession.setOpenID(sysUserMapper.getOpenID());
					logger.info("---------------->>>>>>>>>>>>>>>scale_type:" + sysUserMapper.getScale_type());
					loginSession.setScaleType(sysUserMapper.getScale_type());
					SessionHelper.setLoginSession(loginSession);
				}else{
					loginSession.setMemberCode(String.valueOf(sysUserMapper.getMember_code()));
					loginSession.setOpenID(sysUserMapper.getOpenID());
				}
				response.getWriter().print("success");
				//response.sendRedirect("/toMerLogo.htm");
				//return new ModelAndView("/wechat/merchant_logo") ;
			}else{
				response.getWriter().print("手势密码错误!");
			}
		}
		return null ;
	}
	
	/**
	 * 已绑定公众号用户登录，到达商户后台首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toMerLogo(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/merchant_logo") ;
	}
	
	/**
	 * 商户账户查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView doMerAccountQuery(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/enterprise_account_query") ;
	}
	
	/**
	 * 商户交易查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView doMerTradingQuery(HttpServletRequest request, HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView("/wechat/merchant_trading_query_conditions") ;
		String act = request.getParameter("act");
		//String export = request.getParameter("export");
		if (StringUtils.isBlank(act)) {
			modelAndView.addObject("startTime", getDate(-2));
			modelAndView.addObject("endTime", getDate(1));
			//return tradeSumaryView;
		}
		return modelAndView ;
	}
	
	//日期操作
	protected Date getDate(int offset) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, offset);

		return DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), offset);
	}
	/**
	 * 商户网站提交,到达首页
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView doMerSiteCom(HttpServletRequest request, HttpServletResponse response, PartnerWebsiteConfig websiteConfig){
		// 获取当前登录的商户编号
		/*String memberCode = SessionHelper.getLoginSession().getMemberCode();*/
		String memberCode = "" ;
		LoginSession loginSession = SessionHelper.getLoginSession() ;
		if(null != loginSession){
			memberCode = loginSession.getMemberCode() ;
		}

		logger.info("SiteSet====================================memberCode="
				+ memberCode);
		String siteId = "";
		String status = "";
		
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 15; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 15;// 此处为避免分页计算时出现被0除的情况
		}

		// 设置状态为正常
		// criteria.andStatusEqualTo("1");

		// 设置排序
		websiteConfig.setPartnerId(memberCode);
		//Page page = PageUtils.getPage(request);
		
		Page page = new Page();
		page.setPageNo(pager_offset);
		page.setPageSize(page_size);
		
		Map resultMap = crosspayWebsiteConfigService.merchantWebsiteQuery(websiteConfig, page);
		
		String responseCode = (String) resultMap.get("responseCode");
		String responseDesc = (String) resultMap.get("responseDesc");
		List<Map> returnMap = (List<Map>) resultMap.get("result");
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);

		List<PartnerWebsiteConfig> resultList = MapUtil.map2List(
				PartnerWebsiteConfig.class, returnMap);

		Map<String, Object> model = new HashMap<String, Object>();

		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(),
				page.getTotalRecord());// 分页处理
		model.put("pu", pu);
		model.put("result", resultList);


		//return new ModelAndView(index, model);
		return new ModelAndView("/wechat/merchant_site_com", model) ;
	}
	
	
	/**
	 * 添加网址
	 * @param request
	 * @param response
	 * @param websiteConfig
	 * @return
	 * @throws Exception
	 */
	public ModelAndView saveSite(HttpServletRequest request,
			HttpServletResponse response, PartnerWebsiteConfig websiteConfig)
			throws Exception {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		String memberCode = SessionHelper.getLoginSession(request)
				.getMemberCode();
		
		// 页面参数
		int pager_offset = 1; // 当前页码
		int page_size = 15; // 每页条数
		pager_offset = request.getParameter("pager_offset") == null ? 1
				: Integer.parseInt(request.getParameter("pager_offset")); // 获取页面传过来的页码

		if (request.getParameter("numCount") != null
				&& !"".equals(request.getParameter("numCount"))) {// 获取页面传入的每页显示记录条数
			page_size = Integer.parseInt(request.getParameter("numCount"));
			if (page_size == 0)
				page_size = 15;// 此处为避免分页计算时出现被0除的情况
		}
		
		JSONObject json = new JSONObject();
		if (StringUtils.isEmpty(websiteConfig.getUrl())) {
			json.put("result", "error");
			json.put("message", "网址不能为空");
		} else {
			Page page = PageUtils.getPage(request);
			websiteConfig.setPartnerId(memberCode);
			List<PartnerWebsiteConfig> resultList = crosspayWebsiteConfigService
					.merchantWebsiteQuery1(websiteConfig, page);
			if (null != resultList && !resultList.isEmpty()) {
				json.put("result", "error");
				json.put("message", "网址不合法或者已经存在");
			} else {
				websiteConfig.setPartnerId(memberCode);
				websiteConfig.setIp(StringUtils.trim(getIp(request)));
				boolean f = crosspayWebsiteConfigService
						.merchantWebsiteAdd(websiteConfig);
				if (f) {
					json.put("result", "success");
					json.put("message", "添加网站成功");
				} else {
					json.put("result", "error");
					json.put("message", "添加失败");
				}
			}
		}
		SpringControllerUtils.renderText(response, json.toString());
		return null;
	}
	
	public static String getIp(final HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多级反向代理
		if (null != ip && !"".equals(ip.trim())) {
			StringTokenizer st = new StringTokenizer(ip, ",");
			if (st.countTokens() > 1) {
				return st.nextToken();
			}
		}
		return ip;
	}
	//------------------------------------setter------------------------
	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public void setQuestionBindService(QuestionBindService questionBindService) {
		this.questionBindService = questionBindService;
	}

	public void setCrosspayWebsiteConfigService(
			CrosspayWebsiteConfigService crosspayWebsiteConfigService) {
		this.crosspayWebsiteConfigService = crosspayWebsiteConfigService;
	}

	public void setSysUserMapperService(SysUserMapperService sysUserMapperService) {
		this.sysUserMapperService = sysUserMapperService;
	}
	
	
}
