package com.pay.fo.controller.autorisk;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.fundout.withdraw.common.util.WithDrawAutoRiskConstants;
import com.pay.fundout.withdraw.dto.autorisk.AutoRiskDto;
import com.pay.fundout.withdraw.dto.autorisk.IpDto;
import com.pay.fundout.withdraw.dto.autorisk.MemBerDto;
import com.pay.fundout.withdraw.dto.autorisk.TransSumDto;
import com.pay.fundout.withdraw.dto.autorisk.TransWebsiteDto;
import com.pay.fundout.withdraw.service.autorisk.AutoRiskInterfaceService;
import com.pay.fundout.withdraw.service.autorisk.CheckLogService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.security.util.SessionUserHolderUtil;


/**
 * 出款风控队列控制器
 * @author meng.li
 *
 */
public class CheckLogController extends AbstractBaseController {
	
	protected transient Log log = LogFactory.getLog(getClass());
	
	private CheckLogService checkLogService;
	
	private AutoRiskInterfaceService autoRiskInterfaceService;
	
	private MemberQueryFacadeService memberQueryFacadeService;
	
	public void setAutoRiskInterfaceService(AutoRiskInterfaceService autoRiskInterfaceService){
		this.autoRiskInterfaceService = autoRiskInterfaceService;
	}
	
	public void setCheckLogService(CheckLogService checkLogService){
		this.checkLogService = checkLogService;
	}
	
	public void setMemberQueryFacadeService(MemberQueryFacadeService memberQueryFacadeService){
		this.memberQueryFacadeService = memberQueryFacadeService;
	}
	
	/**
	 * 初始化
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		String viewName = urlMap.get("index");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, AutoRiskDto dto){
		Page<AutoRiskDto> page = PageUtils.getPage(request);
		page = this.checkLogService.getCheckLogList(page, dto);
		String viewName = urlMap.get("searchResult");
		return new ModelAndView(viewName).addObject("page", page);
	}
	
	/**
	 * 分页查找IP列表
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView searchIp(HttpServletRequest request, HttpServletResponse response){
		Long memberCode = Long.valueOf(request.getParameter("code"));
		Page<IpDto> page = PageUtils.getPage(request);
		page = this.autoRiskInterfaceService.queryIpInfo(page, memberCode);
		String viewName = urlMap.get("searchIp");
		return new ModelAndView(viewName).addObject("page", page);
	}
	
	/**
	 * 查看具体一个已审核记录信息
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	public ModelAndView view(HttpServletRequest request, HttpServletResponse response, AutoRiskDto dto){
		Long recordNo = dto.getRecordNo();
		AutoRiskDto riskDto = this.checkLogService.findById(recordNo);
		String viewName = urlMap.get("view");
		return new ModelAndView(viewName).addObject("dto", riskDto);
	}
	
	/**
	 * 进入审核页面
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	public ModelAndView check(HttpServletRequest request, HttpServletResponse response, AutoRiskDto dto){
		String viewName = urlMap.get("check");
		
		AutoRiskDto riskDto = this.checkLogService.findById(dto.getRecordNo());
		
		// 获取会员详细信息
		MemBerDto member;
		if(MemberTypeEnum.INDIVIDUL.getCode() == riskDto.getMemberType()){
			member = this.autoRiskInterfaceService.queryIndividualInfo(riskDto.getMemberCode());
		}else{
			member = this.autoRiskInterfaceService.queryEnterpriseInfo(riskDto.getMemberCode());
		}
		
		// 获取IP关联列表数据
		Page<IpDto> page = PageUtils.getPage(request);
		page = this.autoRiskInterfaceService.queryIpInfo(page, riskDto.getMemberCode());
		
		// 获取最近12个月交易汇总列表
		List<TransSumDto> transSumList = this.autoRiskInterfaceService.queryTransSumInfo(riskDto.getMemberCode());
		
		// 获取交易网址(个人留空)
		List<TransWebsiteDto> transWebsiteList = this.autoRiskInterfaceService.queryTransWebsiteInfo(riskDto.getMemberCode());
		
		AutoRiskDto queryDto = new AutoRiskDto();
		queryDto.setMemberCode(riskDto.getMemberCode());
		queryDto.setRuleCode(riskDto.getRuleCode());
		queryDto.setStatus(WithDrawAutoRiskConstants.STATUS_NOT_CHECKED);
		
		List<AutoRiskDto> checkList = this.checkLogService.queryCheckLogList(queryDto);
		
		StringBuffer sb = new StringBuffer();
		for(AutoRiskDto auto : checkList){
			sb.append(",").append(auto.getRecordNo());
		}
		
		final String recordNos = sb.toString().substring(1);
		
		return new ModelAndView(viewName).addObject("riskDto", riskDto).addObject("dto", member).addObject("page", page).addObject("transSumList", transSumList)
		.addObject("transWebsiteList", transWebsiteList).addObject("checkList", checkList).addObject("recordNos", recordNos).addObject("memberType", riskDto.getMemberType());
	}
	
	/**
	 * ajax提交审批记录
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	public ModelAndView ajaxSubmit(HttpServletRequest request, HttpServletResponse response, AutoRiskDto dto) throws Exception{
		String recordNos = request.getParameter("recordNos");
		String checkRemark = request.getParameter("checkRemark");
		String operator = SessionUserHolderUtil.getLoginId();
		String res = "yes";
		try{
			this.checkLogService.CheckLogsRdTx(recordNos, operator, checkRemark);
		}catch (Exception e) {
			log.error("批量审批出错", e);
			res = "no";
		}
		response.getWriter().print(res);
		response.getWriter().close();
		return null;
	}
	
	/**
	 * 进入账户查询信息页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryIndex(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView(URL("queryIndex"));
	}
	
	/**
	 * 根据条件查询账户相关信息
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request, HttpServletResponse response, AutoRiskDto dto){
		String viewName = urlMap.get("detail");
		
		String error;
		
		Long memberCode = dto.getMemberCode();
		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(memberCode);
		
		if(memberInfo == null){
			error = "该会员号对应的会员信息不存在";
			return new ModelAndView(viewName).addObject("error", error);
		}
		
		// 获取会员详细信息
		MemBerDto member;
		if(MemberTypeEnum.INDIVIDUL.getCode() == memberInfo.getMemberType()){
			member = this.autoRiskInterfaceService.queryIndividualInfo(memberCode);
		}else{
			member = this.autoRiskInterfaceService.queryEnterpriseInfo(memberCode);
		}
		
		// 获取IP关联列表数据
		Page<IpDto> page = PageUtils.getPage(request);
		page = this.autoRiskInterfaceService.queryIpInfo(page, memberCode);
		
		// 获取最近12个月交易汇总列表
		List<TransSumDto> transSumList = this.autoRiskInterfaceService.queryTransSumInfo(memberCode);
		
		// 获取交易网址(个人留空)
		List<TransWebsiteDto> transWebsiteList = this.autoRiskInterfaceService.queryTransWebsiteInfo(memberCode);
		
		return new ModelAndView(viewName).addObject("dto", member).addObject("page", page)
		.addObject("transSumList", transSumList).addObject("transWebsiteList", transWebsiteList).addObject("memberType", memberInfo.getMemberType());
	}
	
}
