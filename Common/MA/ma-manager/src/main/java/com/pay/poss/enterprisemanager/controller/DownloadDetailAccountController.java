package com.pay.poss.enterprisemanager.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;

public class DownloadDetailAccountController extends MultiActionController {

	private IEnterpriseService enterpriseService;

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		EnterpriseSearchDto enterpriseSearchDto = new EnterpriseSearchDto();
		Page<EnterpriseSearchListDto> page = PageUtils.getPage(request);
		String accountCode = request.getParameter("accountCode");
		String dealType1 = request.getParameter("dealType");
		String startDate = request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String dealId=request.getParameter("dealId");
		enterpriseSearchDto.setAccountCode(accountCode);
		enterpriseSearchDto.setDealType(dealType1);
		enterpriseSearchDto.setStartDate(startDate);
		enterpriseSearchDto.setEndDate(endDate);
		enterpriseSearchDto.setDealId(dealId);
		List<EnterpriseSearchListDto> detailOfAccountList = enterpriseService
				.queryDetailOfAccountAll(enterpriseSearchDto);
		if (detailOfAccountList != null) {
			for (EnterpriseSearchListDto enterpriseSearchListDto : detailOfAccountList) {
				Map<String, String> paraMap = new HashMap<String, String>(3);
				paraMap.put("dealId", enterpriseSearchListDto.getDealId());
				paraMap.put("acctCode",
						enterpriseSearchListDto.getAccountCode());
				if (enterpriseSearchListDto.getDealType().equals(
						String.valueOf(PayForEnum.FEE_AMOUNT.getCode()))) {
					paraMap.put("status", "1");
				} else {
					paraMap.put("status", "0");
				}
				String dealCode = enterpriseSearchListDto.getDealCode() == null ? ""
						: String.valueOf(enterpriseSearchListDto.getDealCode());
				paraMap.put("dealCode", dealCode);
				//优化，查询余额和数据一起查询。
/*				String accDetailBalance = enterpriseService 
						.queryBalance(paraMap);
				enterpriseSearchListDto.setBalance(accDetailBalance);
*/	
				String dealType = enterpriseSearchListDto.getDealType();

				PayForEnum payForEnumType = PayForEnum.get(Integer
						.parseInt(dealType));

				if (null != payForEnumType) {
					enterpriseSearchListDto.setDealType(payForEnumType
							.getMessage());
				}

			}
		}
		page.setResult(detailOfAccountList);
		try {
			String[] headers = new String[] { "交易日期", "交易流水号", "交易类型", "收入",
					"支出", "余额" };
			String[] fields = new String[] { "createDate", "dealId",
					"dealType", "strRevenue", "strPay", "balance" };
			Workbook grid = SimpleExcelGenerator.generateGrid("账户余额明细",
					headers, fields, page.getResult());
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
