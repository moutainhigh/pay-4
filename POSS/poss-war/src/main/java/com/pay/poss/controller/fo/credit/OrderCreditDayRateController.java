package com.pay.poss.controller.fo.credit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.poss.client.OrderCreditCoreService;
import com.pay.poss.dto.DayRateDTO;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * @author JiangboPeng
 */
public class OrderCreditDayRateController extends MultiActionController {

	private Log logger = LogFactory.getLog(OrderCreditDayRateController.class) ;
	
	private String index;
	private String effectDayRateList ;
	private String historyDayRateList ;
	
	private EnterpriseBaseService enterpriseBaseService ;
	private OrderCreditCoreService orderCreditCoreService ;
	
	/**
	 * 到达日利率页面	
	 * @param request
	 * @param response
	 * @return
	 */
	public  ModelAndView index(HttpServletRequest request , HttpServletResponse response){
		return new ModelAndView(this.index) ;
	}
	
	/**
	 * 日利率新增
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView dayRateCreate(HttpServletRequest request, HttpServletResponse response){
		try {
			//获取日利率，商户会员号
			String partnerId = StringUtil.null2String(request.getParameter("partnerId")) ;
			if(this.partnerDayRateIsExists(partnerId)){
				logger.info("商户授信日利率已存在 ！");
				return new ModelAndView(this.index).addObject("errorMsg", "该会员号日利率已配置") ;
			}
			if(!this.partnerIsExists(partnerId)){
				logger.info("该会员号不存在！===》" + partnerId);
				return new ModelAndView(this.index).addObject("errorMsg", "该会员号不存在！") ;
			}
			logger.info("新增授信日利率开始...");
			String dayRate = StringUtil.null2String(request.getParameter("dayRate")) ;
			//商户基本信息
			//if(StringUtils.isNotEmpty(partnerId) && 0 )
			String partnerName = this.getPartnerName(partnerId);
			String username = this.getOperatorName();
			logger.info("操作员：" + username);
			//构建日利率新增对象
			DayRateDTO dayRateDTO = constructorDayRateDTO(partnerId, dayRate,
					partnerName, username);
			Map<String, Object> paraMap = new HashMap<String, Object>() ;
			paraMap.put("dayRateDTO", dayRateDTO) ;
			this.orderCreditCoreService.dayRateCreate(paraMap) ;
			logger.info("新增授信日利率结束...");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		return new ModelAndView(this.index) ;
	}

	/**
	 * 构建日利率新增对象
	 * @param partnerId
	 * @param dayRate
	 * @param partnerName
	 * @param username
	 * @return
	 */
	private DayRateDTO constructorDayRateDTO(String partnerId, String dayRate,
			String partnerName, String username) {
		DayRateDTO dayRateDTO = new DayRateDTO() ;
		Date date = new Date() ;
		dayRateDTO.setCreateDate(date);
		//新增时的更新时间即为创建时间
		dayRateDTO.setUpdateDate(date);
		dayRateDTO.setOperator(username);
		dayRateDTO.setPartnerId(partnerId);
		dayRateDTO.setPartnerName(partnerName);
		dayRateDTO.setRate(dayRate);
		dayRateDTO.setStatus("1");//使用标志：0停用1启用
		return dayRateDTO;
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
	
	/**
	 * 日利率修改
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView dayRateUpdate(HttpServletRequest request, HttpServletResponse response){
		String id = StringUtil.null2String(request.getParameter("id")) ;
		String partnerId = StringUtil.null2String(request.getParameter("partnerId")) ;
		String dayRate = StringUtil.null2String(request.getParameter("dayRate")) ;
		//修改对象
		DayRateDTO dayRateDTO = new DayRateDTO() ;
		dayRateDTO.setId(Long.valueOf(id));
		//修改操作- 》 禁用该条日利率，并不更新禁用时间
		//dayRateDTO.setUpdateDate(new Date());
		String username = this.getOperatorName() ;
		dayRateDTO.setOperator(username);
		dayRateDTO.setStatus("0"); //停用
		//dayRateDTO.setRate(dayRate);
		//修改
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("dayRateDTO", dayRateDTO) ;
		logger.info("授信日利率修改开始，partnerId:" + partnerId + "日利率id:" + id);
		Map resultMap = this.orderCreditCoreService.dayRateUpdate(paraMap) ;
		if(null != resultMap && ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
			logger.info("日利率:" + id + "修改成功， 已禁用。开始新增记录");
			//新增对象
			String partnerName = this.getPartnerName(partnerId) ;
			DayRateDTO dayRateDTOForCreate = this.constructorDayRateDTO(partnerId, dayRate, partnerName, username) ;
			Map<String, Object> paraMapForCreate = new HashMap<String, Object>() ;
			paraMapForCreate.put("dayRateDTO", dayRateDTOForCreate) ;
			Map createResultMap = this.orderCreditCoreService.dayRateCreate(paraMapForCreate) ;
			String createResponseCode = (String) createResultMap.get("responseCode") ;
			if(null != createResponseCode && ResponseCodeEnum.SUCCESS.getCode().equals(createResponseCode)){
				logger.info("新增成功");
			}else{
				logger.info("新增失败");
			}
			
		}
		logger.info("授信日利率修改结束..");
		return new ModelAndView(this.index) ;
	}
	
	/**
	 * 日利率批量删除--》》》特别注意，该批量删除，并并非是删除， 而是将日利率启用标志置为禁用
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView dayRatedelete(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String ids = StringUtil.null2String(request.getParameter("ids")) ;
		String idArr[] = ids.split(",") ;
		//****************************此处为实际批量删除逻辑 sta*****************************
//		String idArr [] = null  ;
//		List<DayRateDTO> list = new ArrayList<DayRateDTO>() ;
//		if(StringUtils.isNotEmpty(ids)){
//			idArr = ids.split(",") ;
//			for(String s : idArr){
//				DayRateDTO dayRateDTO = new DayRateDTO() ;
//				dayRateDTO.setId(Long.valueOf(s));
//				list.add(dayRateDTO) ;
//			}
//		}
//		//List<String> list = Arrays.asList(idArr) ;
//		Map<String, Object> paraMap = new HashMap<String, Object>() ;
//		paraMap.put("list", list) ;
//		Map resultMap = this.orderCreditCoreService.dayRateDelete(paraMap) ;
		//****************************此处为实际批量删除逻辑  end*****************************
		DayRateDTO dayRateDTO = new DayRateDTO() ;
		dayRateDTO.setStatus("0"); ;
		dayRateDTO.setIds(idArr);
		dayRateDTO.setUpdateDate(new Date());
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		/*paraMap.put("status", "0") ;
		paraMap.put("ids", ids) ;
		paraMap.put("updateDate", new Date()) ;*/
		paraMap.put("dayRateDTO", dayRateDTO) ;
		Map resultMap = this.orderCreditCoreService.dayRateUpdate(paraMap) ;
		if(null != resultMap && ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
			logger.info("授信日利率删除成功，ids:" + ids);
			response.getWriter().print("删除成功！");
			response.getWriter().flush();
			response.getWriter().close();
		}else{
			logger.info("授信日利率删除失败..");
			response.getWriter().print("删除失败!");
			response.getWriter().flush();
			response.getWriter().close();
		}
 		return null ;
	}
	
	/**
	 * 商户生效日利率是否存在
	 * @param memberCode
	 * @return
	 */
	private boolean partnerDayRateIsExists(String memberCode){
		boolean boo = false ;
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("partnerId", memberCode) ;
		paraMap.put("status", "1") ;
		Map resultMap = this.orderCreditCoreService.dayRateQuery(paraMap) ;
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		if(CollectionUtils.isNotEmpty(listMap)){
			boo = true ;
		}
		return boo ;
	}
	
	/**
	 * 日利率查询列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView dayRateQuery(HttpServletRequest request, HttpServletResponse response){
		ModelAndView view = new ModelAndView() ;
		String type = StringUtil.null2String(request.getParameter("type")) ;
		String partnerId = StringUtil.null2String(request.getParameter("partnerId")) ;
		String partnerName = StringUtil.null2String(request.getParameter("partnerName")) ;
		String startTime = StringUtil.null2String(request.getParameter("startTime")) ;
		String endTime = StringUtil.null2String(request.getParameter("endTime")) ;
		Page page = PageUtils.getPage(request) ;
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("partnerId", partnerId) ;
		paraMap.put("partnerName", partnerName) ;
		paraMap.put("startTime", startTime) ;
		paraMap.put("endTime", endTime) ;
		paraMap.put("page", page) ;
		if(StringUtils.isEmpty(type)){
			//生效
			paraMap.put("status", "1") ;
			view.setViewName(this.effectDayRateList);
		}else{
			//失效，历史日利率查询
			paraMap.put("status", "0") ;
			view.setViewName(historyDayRateList);
		}
		Map resultMap = this.orderCreditCoreService.dayRateQuery(paraMap) ;
		List<Map> listMap = (List<Map>) resultMap.get("result") ;
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		if(CollectionUtils.isNotEmpty(listMap)){
			for(Map m : listMap){
				//String updateDate = () list
				if(null != m.get("updateDate")){
					m.put("updateDate", sdf.format(m.get("updateDate"))) ;
				}
			}
		}*/
		
		Map pageMap = (Map) resultMap.get("page") ;
		page = MapUtil.map2Object(Page.class, pageMap) ;
		view.addObject("dayRates", listMap).addObject("page", page) ;
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

	/**
	 * @param orderCreditCoreService the orderCreditCoreService to set
	 */
	public void setOrderCreditCoreService(
			OrderCreditCoreService orderCreditCoreService) {
		this.orderCreditCoreService = orderCreditCoreService;
	}

	/**
	 * @param effectDayRateList the effectDayRateList to set
	 */
	public void setEffectDayRateList(String effectDayRateList) {
		this.effectDayRateList = effectDayRateList;
	}

	/**
	 * @param historyDayRateList the historyDayRateList to set
	 */
	public void setHistoryDayRateList(String historyDayRateList) {
		this.historyDayRateList = historyDayRateList;
	}
	
	
	
}
