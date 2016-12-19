package com.pay.fo.controller.bankcorporateexpress;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.service.WorkOrderStatus;
import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressReqDTO;
import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressResDTO;
import com.pay.fundout.withdraw.service.bankcorporateexpress.BankCorporateExpressService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.SpringControllerUtils;

/**
 * 银企直联
 * 
 * @author Administrator
 * 
 */
public class BankCorporateExpressController extends AbstractBaseController {

	private BankInfoFacadeService bankFacadeService;
	private BankCorporateExpressService bankCorporateExpressService;

	public void setBankFacadeService(BankInfoFacadeService bankFacadeService) {
		this.bankFacadeService = bankFacadeService;
	}

	public void setBankCorporateExpressService(BankCorporateExpressService bankCorporateExpressService) {
		this.bankCorporateExpressService = bankCorporateExpressService;
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		return new ModelAndView(URL("indexView"), DateUtil.getInitTime()).addObject("withdrawBankList", bankFacadeService.getWithdrawBankList());
	}

	public ModelAndView bankCorporateExpressFailQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		Page<BankCorporateExpressResDTO> page = PageUtils.getPage(request);
		BankCorporateExpressReqDTO bankCorporateExpressReqDTO = new BankCorporateExpressReqDTO();
		bind(request, bankCorporateExpressReqDTO, "bankCorporateExpressReqDTO", null);
		String export = request.getParameter("export");
		
		//查询有多少条数据
		int pageSize=0;
		//指定接收查询的交易数据
		List<BankCorporateExpressResDTO> result=null;
		//指定跳转页面
		String view="listView";
		//计算查询的交易总金额
		Long countAmount=0L;
		//判断是否下载交易记录文件
		if (StringUtils.isNotBlank(export)) {
			try {
				String fileName = "银企直连交易时间" + this.dateToStr(bankCorporateExpressReqDTO.getStartDate())+"至"+this.dateToStr(bankCorporateExpressReqDTO.getEndDate());
				result = bankCorporateExpressService.bankCorporateExpressFailQuery(bankCorporateExpressReqDTO);
				//计算指定时间段的银企直连总交易金额
				for(BankCorporateExpressResDTO bank:result){
					countAmount+=bank.getAmount();
				}
				//查询出多少条数据
				pageSize=result.size();
				view = "bankCorporateExpressExcel";
				response.setHeader("Expires", "0");
				response.setHeader("Pragma", "public");
				response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
				response.setHeader("Cache-Control", "public");
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
				response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes("GBk"), "ISO8859_1"));
			} catch (UnsupportedEncodingException e) {
				log.info("下载交易记录文件错误"+e);
				e.printStackTrace();
			}
		} else {
			page = bankCorporateExpressService.bankCorporateExpressFailQuery(page, bankCorporateExpressReqDTO);
			//计算当前页页的银企直连总交易金额
			for(BankCorporateExpressResDTO bank:page.getResult()){
				countAmount+=bank.getAmount();
			}
			//查询出多少条数据
			pageSize=page.getResult().size();
		}
		
		return new ModelAndView(urlMap.get(view)).addObject("page", page).addObject("withdrawBankList", bankFacadeService.getWithdrawBankList())
				.addObject("bceStatus", request.getParameter("status")).addObject("countAmount", countAmount).addObject("pageSize", pageSize).addObject("result",result);
	}

	// 确认失败
	public ModelAndView confirmFail(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String workorders = request.getParameter("sequenceList");
		String bl = "";
		try {
			if (bankCorporateExpressService.confirmFailRdTx(workorders)) {
				bl = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringControllerUtils.renderText(response, bl);
		return null;
	}

	// 同意
	public ModelAndView reAuditPass(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String workorders = request.getParameter("sequenceList");
		String bl = "";
		try {
			if (bankCorporateExpressService.reAuditPass(workorders)) {
				bl = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringControllerUtils.renderText(response, bl);
		return null;
	}

	// 拒绝
	public ModelAndView reAuditReject(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		String workorders = request.getParameter("sequenceList");
		String bl = "";
		try {
			if (bankCorporateExpressService.reAuditRejectRdTx(workorders)) {
				bl = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringControllerUtils.renderText(response, bl);
		return null;
	}

	public ModelAndView reAudit(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		return new ModelAndView(URL("reAuditInit"), DateUtil.getInitTime()).addObject("withdrawBankList", bankFacadeService.getWithdrawBankList());
	}
	/**
	 * 时间格式转换
	 * Date-->String
	 * @param time
	 * @return
	 */
	public static String dateToStr(Date time) {
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String date="";
		date=format.format(time);
		return date;
	}

	public ModelAndView bankCorporateExpressReAuditQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException {

		Page<BankCorporateExpressResDTO> page = PageUtils.getPage(request);
		BankCorporateExpressReqDTO bankCorporateExpressReqDTO = new BankCorporateExpressReqDTO();
		bind(request, bankCorporateExpressReqDTO, "bankCorporateExpressReqDTO", null);
		bankCorporateExpressReqDTO.setStatus(WorkOrderStatus.BANKCORPORATEEXPRESS_CONFIRMFAIL.getValue());
		page = bankCorporateExpressService.bankCorporateExpressReAuditQuery(page, bankCorporateExpressReqDTO);
		return new ModelAndView(urlMap.get("reAuditList")).addObject("page", page).addObject("withdrawBankList", bankFacadeService.getWithdrawBankList());
	}
}
