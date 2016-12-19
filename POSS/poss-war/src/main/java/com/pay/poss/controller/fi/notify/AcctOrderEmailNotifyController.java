package com.pay.poss.controller.fi.notify;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.poss.client.AcctOrderEmailNotifyService;
import com.pay.poss.client.GatewayOrderQueryService;
import com.pay.poss.dto.OrderEmailNotifyCriteria;
import com.pay.poss.dto.OrderEmailNotifyDTO;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 商户下单邮件通知控制类
 * @author davis.guo at 2016-08-31
 */
public class AcctOrderEmailNotifyController extends MultiActionController {

	private Log logger = LogFactory.getLog(AcctOrderEmailNotifyController.class) ;
	
	private String index ;
	private String emailNotifyList ;
	private IEnterpriseService enterpriseService ;
	private EnterpriseBaseService enterpriseBaseService ;
	private GatewayOrderQueryService gatewayOrderQueryService;
	
	private AcctOrderEmailNotifyService acctOrderEmailNotifyService;
	
	private ConcurrentMap<String, EnterpriseSearchListDto> enterpriseMap = new ConcurrentHashMap<String, EnterpriseSearchListDto>();
	private String merberCodes = ",";//记录已经加载的会员号
	
	/**
	 * 转支商户下单邮件通知主页面	
	 * @param request
	 * @param response
	 * @return
	 */
	public  ModelAndView index(HttpServletRequest request , HttpServletResponse response){
		
		return new ModelAndView(this.index) ;
	}
	
	/**
	 * 新增操作	
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView orderEmailNotifyCreate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			//获取商户会员号
			String memberCode = StringUtil.null2String(request.getParameter("_memberCode")) ;
			if (!StringUtil.isEmpty(memberCode)) {
				memberCode = memberCode.trim();
			}
			if (memberIsExists(memberCode))
			{
				return new ModelAndView(this.index).addObject("errorMsg", "该会员号已经存在！") ;
			}
			if(!this.partnerIsExists(memberCode)){
				logger.info("该会员号不存在！===》" + memberCode);
				return new ModelAndView(this.index).addObject("errorMsg", "该会员号不存在，请在会员管理中添加！") ;
			}
			
			logger.info("新增邮件通知开始...");
			
			//构建新增对象
			OrderEmailNotifyDTO orderEmailNotify = constructorDTO(request);
			orderEmailNotify.setCreateDate(new Date());
			
			Long maxTradeOrderNo = getMaxTradeOrderNo(orderEmailNotify.getMemberCode()+"");//获取当前最大的网关订单号
			orderEmailNotify.setMaxTradeOrderNo(maxTradeOrderNo);
			
			Map<String, Object> paraMap = new HashMap<String, Object>() ;
			paraMap.put("orderEmailNotify", orderEmailNotify) ;
			this.acctOrderEmailNotifyService.orderEmailNotifyCreate(paraMap) ;
			logger.info("新增邮件通知结束...");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView(this.index) ;
	}
	
	/***
	 * 获取当前最大的网关订单号
	 * 
	 * @param partnerId
	 * @return
	 */
	private Long getMaxTradeOrderNo(String partnerId)
	{
		Long maxTradeOrderNo = 0L;
		Map paraMap = new HashMap();
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paraMap.put("startDate", startDate);//当前时间
		Map resultMap = gatewayOrderQueryService.getMaxTradeOrderNo(paraMap);
		if(resultMap!=null && resultMap.get("maxTradeOrderNo")!=null)
		{
			maxTradeOrderNo = Long.valueOf(resultMap.get("maxTradeOrderNo").toString());
			logger.info("###maxTradeOrderNo="+maxTradeOrderNo);
		}
		/*paraMap.put("partnerId", partnerId);//根据会员号查询
		paraMap.put("statusList", "4,3");//支付成功的
		Map resultMap = gatewayOrderQueryService.queryTradeOrder(paraMap);
		//TODO 后期再完善获取商户的最大网关订单号，目前先中记录中获取。
		List<Map> tradeOrders = (List<Map>) resultMap.get("result");
		if(tradeOrders!=null){
			String tNo = "";
			Long tradeOrderNo = 0L;
			for (Map map : tradeOrders) {
				tNo = map.get("tradeOrderNo").toString();
				if(!StringUtil.isEmpty(tNo))tradeOrderNo = Long.valueOf(tNo);
				if(maxTradeOrderNo < tradeOrderNo){
					maxTradeOrderNo = tradeOrderNo ;
				}
			}
		}*/
		
		return maxTradeOrderNo;
	}
	
	/**
	 * 修改记录数据
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView orderEmailNotifyUpdate(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException{
		ModelAndView view = new ModelAndView(this.index) ;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = StringUtil.null2String(request.getParameter("id")) ;
		//查询记录是否存在
		/*OrderEmailNotifyCriteria oenCriteria = new OrderEmailNotifyCriteria();
		if (!StringUtil.isEmpty(id)) {
			id = id.trim();
			oenCriteria.setId(Long.valueOf(id));
		}
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("orderEmailNotifyCriteria", oenCriteria);
		paraMap.put("type", "one") ;
		
		Map resultMap = this.acctOrderEmailNotifyService.orderEmailNotifyQuery(paraMap);
		Object obj = resultMap.get("result") ;
		if(obj != null)resultMap = (Map)obj;
		if(resultMap==null || (resultMap.get("id")!=null && Long.valueOf(resultMap.get("id").toString())<=0))//if(null != resultMap && ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){}
		{
			return view.addObject("errorMsg", "id:"+id+"不存在！") ;
		}*/
		
		logger.info("修改邮件通知开始...");
		//构建修改内容对象
		OrderEmailNotifyDTO orderEmailNotify = constructorDTO(request);
		Map paraMap = new HashMap<String, Object>() ;
		paraMap.put("orderEmailNotify", orderEmailNotify) ;
		Map resultMap = this.acctOrderEmailNotifyService.orderEmailNotifyUpdate(paraMap) ;
		
		if(null != resultMap && ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
			logger.info("修改成功");
		}
		else
		{
			logger.info("修改失败");
			view.addObject("errorMsg", "id:"+id+"修改失败！") ;
		}
		logger.info("修改邮件通知结束...");
		
		return view ;
	}

	/**
	 * 修改开通状态
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateFlag(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException{
		ModelAndView view = new ModelAndView(this.index) ;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = StringUtil.null2String(request.getParameter("id")) ;
		String openFlag = StringUtil.null2String(request.getParameter("_openFlag")) ;
		if (!StringUtil.isEmpty(id)) {
			id = id.trim();
		}
		if (!StringUtil.isEmpty(openFlag)) {
			openFlag = openFlag.trim();
		}
		logger.info("修改邮件通知开通状态开始...");
		Map paraMap = new HashMap<String, Object>() ;
		paraMap.put("id", id) ;
		paraMap.put("openFlag", openFlag) ;
		//paraMap.put("updateDate", new Date()) ;
		paraMap.put("type", "flag") ;//只更新状态
		Map resultMap = this.acctOrderEmailNotifyService.orderEmailNotifyUpdate(paraMap) ;
		
		if(null != resultMap && ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
			logger.info("修改状态成功");
		}
		else
		{
			logger.info("修改状态失败");
			view.addObject("errorMsg", "id:"+id+"修改状态失败！") ;
		}
		logger.info("修改邮件通知开通状态结束...");
		
		return view ;
	}


	/**
	 * 构建对象
	 * @return
	 */
	private OrderEmailNotifyDTO constructorDTO(HttpServletRequest request) 
			throws ServletException, UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		String id = StringUtil.null2String(request.getParameter("id")) ;
		String memberCode = StringUtil.null2String(request.getParameter("_memberCode")) ;
		String merchantCode = StringUtil.null2String(request.getParameter("_merchantCode")) ;
		String merchantName = StringUtil.null2String(request.getParameter("_merchantName")) ;
		String loginName = StringUtil.null2String(request.getParameter("_loginName")) ;
		String emailCompany = StringUtil.null2String(request.getParameter("_emailCompany")) ;
		String emailNotify = StringUtil.null2String(request.getParameter("_emailNotify")) ;
		String openFlag = StringUtil.null2String(request.getParameter("_openFlag")) ;
		String operator = StringUtil.null2String(request.getParameter("_operator")) ;
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		OrderEmailNotifyDTO oenDto = new OrderEmailNotifyDTO();
		if (!StringUtil.isEmpty(id)) {
			id = id.trim();
			oenDto.setId(Long.valueOf(id));
		}
		if (!StringUtil.isEmpty(memberCode)) {
			memberCode = memberCode.trim();
			oenDto.setMemberCode(Long.valueOf(memberCode));
		}
		if (!StringUtil.isEmpty(merchantCode)) {
			merchantCode = merchantCode.trim();
			oenDto.setMerchantCode(Long.valueOf(merchantCode));
		}
		if (!StringUtil.isEmpty(merchantName)) {
			merchantName = merchantName.trim();
			oenDto.setMerchantName(merchantName);
		}
		if (!StringUtil.isEmpty(loginName)) {
			loginName = loginName.trim();
		}
		oenDto.setLoginName(loginName);//不是必填项，修改时可以为空
		if (!StringUtil.isEmpty(openFlag)) {
			openFlag = openFlag.trim();
			oenDto.setOpenFlag(Integer.valueOf(openFlag));
		}
		if (!StringUtil.isEmpty(emailCompany)) {
			emailCompany = emailCompany.trim();
		}
		oenDto.setEmailCompany(emailCompany);//不是必填项，修改时可以为空
		if (!StringUtil.isEmpty(emailNotify)) {
			emailNotify = emailNotify.trim();
			oenDto.setEmailNotify(emailNotify);
		}
		if (!StringUtil.isEmpty(operator)) {
			operator = operator.trim();
			oenDto.setOperator(operator);
		}
		oenDto.setOperator(getOperatorName());
		//oenDto.setCreateDate(new Date());
		oenDto.setUpdateDate(new Date());
		
		
		return oenDto;
	}

	/**
	 * 取操作员
	 * @return
	 */
	private String getOperatorName() {
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil.getSessionUserHolder() ;
		String username = sessionUserHolder.getUsername() ;
		return username;
	}

	/**
	 * 获取商户名称
	 * @param partnerId
	 * @return
	 */
	private String getPartnerName(String partnerId) {
		EnterpriseBaseDto enterpriseBaseDto = this.enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long.valueOf(partnerId)) ;
		String partnerName = "0" ;
		if(null != enterpriseBaseDto){
			partnerName = enterpriseBaseDto.getZhName() ;
		}
		
		return partnerName;
	}
	
	/**
	 * 判断商户是否存在但是排除0会员号
	 * @param partnerId
	 * @return
	 */
	private boolean partnerIsExists(String partnerId){
		boolean boo = false ;
		try {
			EnterpriseBaseDto enterpriseBaseDto = this.enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long.valueOf(partnerId)) ;
			if(null != enterpriseBaseDto || "0".equals(partnerId)){
				boo = true ;
			}
		} catch (NumberFormatException e) {
			logger.info("获取商户信息出错了。。");
			e.printStackTrace();
		}
		return boo ;
	}

	
	/***
	 * 检查会员号是否存在
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws UnsupportedEncodingException
	 */
	public void checkMemberCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		String memberCode = request.getParameter("memberCode");
		if (!StringUtil.isEmpty(memberCode)) {
			memberCode = memberCode.trim();
		}
		
		try {
			if (memberIsExists(memberCode))// 排除平台会员号 || "80000000000".equalsIgnoreCase(memberCode)
			{
				response.getWriter().print("ipRepeat");
				logger.info("会员号已经存在!");
			}
			else
			{
				response.getWriter().print("yes");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private boolean memberIsExists(String memberCode)
	{
		OrderEmailNotifyCriteria oenCriteria = new OrderEmailNotifyCriteria();
		if (!StringUtil.isEmpty(memberCode)) {
			memberCode = memberCode.trim();
			oenCriteria.setMemberCode(Long.valueOf(memberCode));
		}
		
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("orderEmailNotifyCriteria", oenCriteria);
		paraMap.put("type", "all") ;

		Map resultMap = this.acctOrderEmailNotifyService.orderEmailNotifyQuery(paraMap);
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		if (CollectionUtils.isNotEmpty(listMap))
		{
			return true;
		}
		return false;
	}


	/**
	 * 查询会员列表，在页面显示。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryEnterprise(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		/*try {
			WebBindUtils.bind(request, organization, "", true, "");
		} catch (ServletException e) {
			logger.error(e.getMessage(), e);
		}*/
		EnterpriseSearchDto enterpriseSearchDto = new EnterpriseSearchDto();
		
		int enterpriseListCount = enterpriseService.queryEnterpriseCount(enterpriseSearchDto);
		enterpriseSearchDto.setPageStartRow("0");//获取全部记录
		enterpriseSearchDto.setPageEndRow(String.valueOf(enterpriseListCount));//获取
		List<EnterpriseSearchListDto> enterpriseListAll = enterpriseService.queryEnterprise(enterpriseSearchDto);
		List<EnterpriseSearchListDto> enterpriseList = new ArrayList<EnterpriseSearchListDto>();
		if(CollectionUtils.isNotEmpty(enterpriseListAll))
		{
			enterpriseMap.clear();
			String memberCode = "";
			for(EnterpriseSearchListDto entry : enterpriseListAll)
			{
				memberCode = entry.getMemberCode();
				if("80000000000".equalsIgnoreCase(memberCode)//// 排除平台会员号
						|| (merberCodes.indexOf(","+memberCode+",")>=0))// 排除已有记录
				{
					continue;
				}
				enterpriseList.add(entry);
				enterpriseMap.put(entry.getMemberCode(), entry);
			}
		}
		
		String enterpriseJSonStr = JSonUtil.toJSonString(enterpriseList);
		response.getWriter().print(enterpriseJSonStr);
		return null;
	}

	/**
	 * 根据会员号，获取会员信息，异步加载数据。
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView getEnterprise(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//获取商户会员号
		String memberCode = StringUtil.null2String(request.getParameter("memberCode")) ;
		if (!StringUtil.isEmpty(memberCode)) {
			memberCode = memberCode.trim();
		}
		EnterpriseSearchListDto enterprise = null;
		if(enterpriseMap.size()>0 && !StringUtil.isEmpty(memberCode))
			enterprise = enterpriseMap.get(memberCode);
		if(enterprise != null)
		{
			String enterpriseJSonStr = JSonUtil.toJSonString(enterprise);
			response.getWriter().print(enterpriseJSonStr);
		}
		else
		{
			response.getWriter().print("");
		}
		return null;
	}
	
	/**
	 * 删除
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView orderEmailNotifydelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ModelAndView view = new ModelAndView(index);
		String ids = StringUtil.null2String(request.getParameter("ids")) ;
		if (!StringUtil.isEmpty(ids)) {
			ids = ids.trim();
		}
		String[] idArray = ids.split(",") ;
		if(idArray != null)
		{
			for(String id : idArray)
			{
				logger.info("删除邮件通知记录开始..."+id);
				
				Map paraMap = new HashMap<String, Object>() ;
				paraMap.put("id", id.trim()) ;
				Map resultMap = this.acctOrderEmailNotifyService.orderEmailNotifyDelete(paraMap) ;
						
				if(null != resultMap && ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
					logger.info("删除成功");
				}
				else
				{
					logger.info("删除失败");
					view.addObject("errorMsg", "id:"+id+"删除失败！") ;;
				}
				logger.info("删除邮件通知记录结束...");
			}
		}
		
 		return view ;
	}
	
	/**
	 * 商户下单邮件通知查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView orderEmailNotifyQuery(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		ModelAndView view = new ModelAndView(emailNotifyList) ;
		String merchantCode = StringUtil.null2String(request.getParameter("merchantCode")) ;
		String loginName = StringUtil.null2String(request.getParameter("loginName")) ;
		String merchantName = StringUtil.null2String(request.getParameter("merchantName")) ;
		String memberCode = StringUtil.null2String(request.getParameter("memberCode")) ;
		String openFlag = StringUtil.null2String(request.getParameter("openFlag")) ;
		
		String startDate = StringUtil.null2String(request.getParameter("startDate")) ;
		String endDate = StringUtil.null2String(request.getParameter("endDate")) ;
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		OrderEmailNotifyCriteria oenCriteria = new OrderEmailNotifyCriteria();
		if (!StringUtil.isEmpty(memberCode)) {
			memberCode = memberCode.trim();
			oenCriteria.setMemberCode(Long.valueOf(memberCode));
		}
		if (!StringUtil.isEmpty(merchantCode)) {
			merchantCode = merchantCode.trim();
			oenCriteria.setMerchantCode(Long.valueOf(merchantCode));
		}
		if (!StringUtil.isEmpty(merchantName)) {
			merchantName = merchantName.trim();
			oenCriteria.setMerchantName(merchantName);
		}
		if (!StringUtil.isEmpty(loginName)) {
			loginName = loginName.trim();
			oenCriteria.setLoginName(loginName);
		}
		if (!StringUtil.isEmpty(openFlag)) {
			openFlag = openFlag.trim();
			oenCriteria.setOpenFlag(Integer.valueOf(openFlag));
		}
		if (!StringUtil.isEmpty(startDate)) {
			startDate = startDate.trim();
			oenCriteria.setStartDate(startDate);
		}
		if (!StringUtil.isEmpty(endDate)) {
			endDate = endDate.trim();
			oenCriteria.setEndDate(endDate);
		}
		
		Page page = PageUtils.getPage(request) ;
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("orderEmailNotifyCriteria", oenCriteria);
		paraMap.put("type", "all") ;
		paraMap.put("page", page);
		
		merberCodes = ",";//重新赋值
		
		Map resultMap = this.acctOrderEmailNotifyService.orderEmailNotifyQuery(paraMap);
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		if(listMap != null)
		for(Map entry : listMap)
		{
			if(entry.get("memberCode")!=null)
			merberCodes += entry.get("memberCode")+",";//统计记录编号，在选择列表中进行排除
		}
		
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		view.addObject("orderEmailNotifys", listMap).addObject("page", page) ;
		return view ;
	}
	
	/**
	 * @param enterpriseBaseService the enterpriseBaseService to set
	 */
	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}


	public void setGatewayOrderQueryService(
			GatewayOrderQueryService gatewayOrderQueryService) {
		this.gatewayOrderQueryService = gatewayOrderQueryService;
	}

	public void setEmailNotifyList(String emailNotifyList) {
		this.emailNotifyList = emailNotifyList;
	}

	public void setAcctOrderEmailNotifyService(
			AcctOrderEmailNotifyService acctOrderEmailNotifyService) {
		this.acctOrderEmailNotifyService = acctOrderEmailNotifyService;
	}

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	
	
}
