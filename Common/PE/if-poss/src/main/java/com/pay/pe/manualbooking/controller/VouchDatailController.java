package com.pay.pe.manualbooking.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.service.VouchLogService;
import com.pay.pe.manualbooking.service.VouchService;
import com.pay.util.SpringControllerUtils;

public class VouchDatailController extends MultiActionController{

	
	private static final Log LOG = LogFactory.getLog(VouchDataUploadController.class);
	
	private VouchService vouchService;
	
	private VouchLogService vouchLogService;
	
	private String mainUploadPage;
	
	private String mainUploadList;
	
	private String misInfoBoth;
	
	public String getMisInfoBoth() {
		return misInfoBoth;
	}

	public void setMisInfoBoth(String misInfoBoth) {
		this.misInfoBoth = misInfoBoth;
	}
	
	public String getVouchDataillogin() {
		return vouchDataillogin;
	}

	public void setVouchDataillogin(String vouchDataillogin) {
		this.vouchDataillogin = vouchDataillogin;
	}
	private String vouchDataillogin;
	
	public String getMainUploadPage() {
		return mainUploadPage;
	}

	public void setMainUploadPage(String mainUploadPage) {
		this.mainUploadPage = mainUploadPage;
	}
	
	public String getMainUploadList() {
		return mainUploadList;
	}

	public void setMainUploadList(String mainUploadList) {
		this.mainUploadList = mainUploadList;
	}

	public VouchService getVouchService() {
		return vouchService;
	}

	public void setVouchService(VouchService vouchService) {
		this.vouchService = vouchService;
	}
	
	public VouchLogService getVouchLogService() {
		return vouchLogService;
	}

	public void setVouchLogService(VouchLogService vouchLogService) {
		this.vouchLogService = vouchLogService;
	}

	public VouchDatailController() {}
	
	public VouchDatailController(VouchService vouchService) {
		this.vouchService = vouchService;
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 * 访问上传主页面
	 */
	public ModelAndView init(final HttpServletRequest request, final HttpServletResponse response) {
		return new ModelAndView(mainUploadPage).addObject("appContext", request.getContextPath());
	}
	
	
	
	public ModelAndView addInfo(final HttpServletRequest request, final HttpServletResponse response) {
		return new ModelAndView(vouchDataillogin).addObject("appContext", request.getContextPath());
	}
	/**
	 * @param request
	 * @param response
	 * @return
	 * 添加申请记账
	 */
	public ModelAndView addInfoMessage(final HttpServletRequest request, final HttpServletResponse response) {
		
		String vouchCode=request.getParameter("vouchCode")==null?"":request.getParameter("vouchCode");
		String accountName=request.getParameter("accountName")==null?"":request.getParameter("accountName");
		String crdr=request.getParameter("crdr");
		String amount=request.getParameter("amount")==null?"0":request.getParameter("amount");
		String remark=request.getParameter("remark")==null?"":request.getParameter("remark");
		VouchDetailData vdd=new VouchDetailData();
		vdd.setAccountCode(vouchCode);
		vdd.setAccountName(accountName);
		vdd.setCrdr(Integer.parseInt(crdr));
		vdd.setAmount(Double.valueOf(amount));
		vdd.setRemark(remark);
		vdd.setVouchDataId(Long.valueOf("1"));
		vouchService.createVouchDetail(vdd);
		
		System.out.println("====misInfoBoth==="+misInfoBoth);
		//return new ModelAndView(mainUploadPage).addObject("appContext", request.getContextPath()).addObject("vouchDetailData", vdd);
		return new ModelAndView(misInfoBoth).addObject("appContext", request.getContextPath()).addObject("vouchDetailData", vdd);
	}
	
	
	
	
	
	public ModelAndView queryInfo(final HttpServletRequest request, final HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("accountCode", request.getParameter("accountCode"));
		map.put("accountName", request.getParameter("accountName"));
		Page<VouchDetailData> page = PageUtils.getPage(request);
		try {
			page =vouchService.search(page, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String,Object> model=new HashMap<String,Object>() ;
		model.put("page", page);
		return new ModelAndView(mainUploadList,model).addObject("appContext", request.getContextPath());
	}
	
	/**
	 * @param request
	 * @param response
	 * @return
	 * 申请记账添加 对vouch_data,vouch_Detail表操作
	 */
	public ModelAndView InsertBothInfo(final HttpServletRequest request, final HttpServletResponse response) {
		
		//String vouchCode=request.getParameter("vouchCode")==null?"":request.getParameter("vouchCode");
		String creator=request.getParameter("creator")==null?"":request.getParameter("creator");
		String remark=request.getParameter("remark")==null?"":request.getParameter("remark");
		String accountCode[]=(String[])request.getParameterValues("accountCode");
		String crdr[]=(String[])request.getParameterValues("crdr");
		String amount[]=(String[])request.getParameterValues("amount");
		String _remark[]=(String[])request.getParameterValues("_remark");
		VouchData voud=null;
		VouchData vd=new VouchData();
		vd.setStatus(Integer.parseInt("0"));
		vd.setCreateDate(new Date());
		vd.setAuditDate(new Date());
		vd.setAccountingDate(new Date()); 
		vd.setCreator(creator);
		vd.setRemark(remark);
		vd.setAuditor("sys");
		vd.setVersion(Long.valueOf("1"));
		try{
			voud=vouchService.insertVouchData(vd);
			for(int   i=0;i <accountCode.length;i++) {
				VouchDetailData vdd=new VouchDetailData();
				vdd.setAccountCode(accountCode[i]);
				vdd.setAccountName("woyo");
				vdd.setCrdr(Integer.parseInt(crdr[i]));
				vdd.setAmount(Double.valueOf(amount[i]));
				vdd.setRemark(_remark[i]);
				vdd.setVouchDataId(voud.getVouchDataId());
				vouchService.createVouchDetail(vdd);
			}
		}catch(Exception e){
			e.printStackTrace();
			SpringControllerUtils.renderText(response, "false");
		}
		SpringControllerUtils.renderText(response, "true");
		return null;
	}
}
