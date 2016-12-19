package com.pay.app.controller.base.home;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.AppConf;
import com.pay.app.filter.AppFilterCommon;
import com.pay.app.service.announcement.AnnouncementService;
import com.pay.base.common.enums.TargetsEnums;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.model.Acct;
import com.pay.base.model.Advertisement;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.advertise.AdvertiseService;
import com.pay.base.service.member.MemberService;
import com.pay.util.FormatNumber;
import com.pay.util.WebUtil;
import com.pay.wechat.model.OAuth2Resp;
import com.pay.wechat.model.SysUserMapper;
import com.pay.wechat.service.merchant.MerchantService;
import com.pay.wechat.util.Oauth2AccessToken;
import com.pay.wechat.util.WeiXinUtil;


public class HomeController extends MultiActionController{
	
	private String  toIndex;
	
	private String companyIndex;
	
	private AnnouncementService announcementService;

    private MemberService baseMemberService;
    
    private AdvertiseService advertiseService;
    
    private static final Integer AVAIL_ADVERTISE_YES = 1;
    /**
	 * 查询会员账户余额
	 */

    private  AcctService acctService;
    
    private MerchantService merchantService ;

	@SuppressWarnings("unchecked")
	public ModelAndView indexApp(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginSession loginSession=SessionHelper.getLoginSession();
		int topnum=8;
		List list = announcementService.queryTopAnnouncement(topnum);
		BigDecimal balance=new BigDecimal(0);
		int waitPay =0;
		String memberCode=null;
		String salutatory="";
		ModelAndView mv = new ModelAndView(toIndex);
		if(loginSession!=null && loginSession.getMemberCode()!=null){

			memberCode=loginSession.getMemberCode();
			Map<String,Object> loginFailNum=loginSession.getLoginFailNum();
			loginFailNum.put(StringUtils.trim(WebUtil.getIp(request)),0);
			
			loginSession.setLoginFailNum(loginFailNum);
			request.getSession().setAttribute("userSession", loginSession);
			Acct acct =  acctService.getByMemberCode(Long.valueOf(loginSession.getMemberCode()),  AcctTypeEnum.BASIC_CNY.getCode());
			balance=(acct == null ? new BigDecimal(0) :FormatNumber.decimalsRound(acct.getBalance().divide(new BigDecimal(1000)), 2));
			//countUnRead = (Integer) messageReceiveService.countUnRead(memberCode);// 未读消息数
		 //	waitPay = queryHistoryServiceFacade.queryWaitPayOrderByMembercode(Long.valueOf(memberCode));
			MemberCriteria mc=baseMemberService.queryMemberCriteriaByMemberCodeNsTx(Long.valueOf(memberCode));
			//MemberCriteria mc=memberAppInfoService.queryMemberInfoByMemberCode(Long.valueOf(memberCode));
			salutatory=mc.getSalutatory();
		}
		
//		String Agent = request.getHeader("User-Agent");
//		StringTokenizer st = new StringTokenizer(Agent,";");
//		st.nextToken();
//		String userbrowser = st.nextToken();
//		boolean isSecurity=false;
//		
//		if(userbrowser.contains("MSIE")){
//			isSecurity=true;
//			String pkey=RSAHelper.public_key_String;
//			mv.addObject("pkey",pkey);
//		}
		List<Advertisement> advertiseList = null;
		advertiseList = advertiseService.queryAdvertiseListByTargetsAvail(TargetsEnums.INDEX_ADV.getCode(), AVAIL_ADVERTISE_YES);
		
		List<Advertisement> advertiseMerchantList = null;
		advertiseMerchantList = advertiseService.queryAdvertiseListByTargetsAvail(TargetsEnums.MERCHANT_ADV.getCode(), AVAIL_ADVERTISE_YES);
		
		mv.addObject("ggls",list);
		mv.addObject("balance",balance);
		mv.addObject("wait_pay",waitPay);
		mv.addObject("salutatory",salutatory);
		mv.addObject("imgList",advertiseList);
		mv.addObject("merchantList",advertiseMerchantList);
//		mv.addObject("isSecurity",isSecurity);
		
		AppFilterCommon.setCallBackJumpUri(request, AppConf.defaultCallBack,1);//个人首页登录默认进入账户桌面
		return mv;
	}



	@SuppressWarnings("unchecked")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//------------------------------------------获取网页授权code------------------------------
		String code = request.getParameter("code") ;
		//网页OAuth2.0认证
		OAuth2Resp oAuth2Resp = WeiXinUtil.getOAuth2Resp(code, null) ;
		String access_token = "" ;
		String openID = "" ;
		if(null != oAuth2Resp){
			access_token = oAuth2Resp.getAccess_token();
			openID = oAuth2Resp.getOpenid() ;
			Oauth2AccessToken.setAccess_token(access_token);
		}
		SysUserMapper sysUserMapper = this.merchantService.findSysUserMapperByOpenID(openID) ;
		if(null != sysUserMapper && sysUserMapper.getStatu() == 1L){			//已绑定微公众号, 1:绑定， 0：解除绑定 
			LoginSession loginSession = SessionHelper.getLoginSession() ;
			if(null == loginSession){
				loginSession = new LoginSession();
				loginSession.setMemberCode(String.valueOf(sysUserMapper.getMember_code()));
				loginSession.setOpenID(openID);
				loginSession.setScaleType(sysUserMapper.getScale_type());
			}
			SessionHelper.setLoginSession(loginSession);
			return new ModelAndView("/wechat/enterprise_login_binded").addObject("openID", openID);
		//-----------------------------------------------------------------------------------
		}else{
			LoginSession loginSession=SessionHelper.getLoginSession();
		    String salutatory="";
		    ModelAndView mv = new ModelAndView(companyIndex);
		    BigDecimal balance=new BigDecimal(0);
		    if(loginSession!=null){
		    	Map<String,Object> loginFailNum=loginSession.getLoginFailNum();
				loginFailNum.put(StringUtils.trim(WebUtil.getIp(request)),0);
				
				loginSession.setLoginFailNum(loginFailNum);
				request.getSession().setAttribute("userSession", loginSession);
				
				Acct acct =  acctService.getByMemberCode(Long.valueOf(loginSession.getMemberCode()),  AcctTypeEnum.BASIC_CNY.getCode());
				balance=(acct == null ? new BigDecimal(0) :FormatNumber.decimalsRound(acct.getBalance().divide(new BigDecimal(1000)), 2));
				
		        MemberCriteria mc=baseMemberService.queryMemberCriteriaByMemberCodeNsTx(Long.valueOf(loginSession.getMemberCode()));
		        salutatory=mc.getSalutatory();
		    }
		    
			List<Advertisement> advertiseList = null;
			//企业首页广告幻灯片 改为和个人统一
			advertiseList = advertiseService.queryAdvertiseListByTargetsAvail(TargetsEnums.INDEX_ADV.getCode(), AVAIL_ADVERTISE_YES);
			mv.addObject("imgList",advertiseList);
			mv.addObject("balance",balance);
			
		    int topnum=8;
			List list = announcementService.queryTopAnnouncement(topnum);
			AppFilterCommon.setCallBackJumpUri(request, AppConf.defaultCorpCallBack,2);//企业首页登录默认进入账户桌面
			//-----------------------------PengJiangbo添加-------------------------------------------------------
			mv.addObject("access_token", access_token) ;
			//------------------------------------------------------------------------------------
			return mv.addObject("ggls",list).addObject("salutatory",salutatory);
		}
		
	}
	
	
	/********set方法*********/
	public void setToIndex(String toIndex) {
		this.toIndex = toIndex;
	}
	
	public void setCompanyIndex(String companyIndex) {
		this.companyIndex = companyIndex;
	}

	public void setAnnouncementService(AnnouncementService announcementService) {
		this.announcementService = announcementService;
	}



	
	public void setBaseMemberService(MemberService baseMemberService) {
		this.baseMemberService = baseMemberService;
	}
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}
	public void setAdvertiseService(AdvertiseService advertiseService) {
		this.advertiseService = advertiseService;
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	
	
}
