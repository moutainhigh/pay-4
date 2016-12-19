package com.pay.poss.merchantmanager.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.lucene.service.LuceneService;
import com.pay.poss.base.exception.PossException;

public class BankCommonController extends MultiActionController {

	// 取得分行
	private LuceneService luceneService;
	private BankBrancheInfoService bankBrancheInfoService ;
	
	// 验证卡片
//	private CardBinInfoFactoryService cardBinInfoFactoryService;

	public void setLuceneService(final LuceneService luceneService) {
		this.luceneService = luceneService;
	}

//	public void setCardBinInfoFactoryService(
//			CardBinInfoFactoryService cardBinInfoFactoryService) {
//		this.cardBinInfoFactoryService = cardBinInfoFactoryService;
//	}

	/**
	 * 得到分行的select的options
	 * 
	 * @param request
	 * @param response
	 * @param searchBTO
	 * @return
	 * @throws PossException
	 * @throws IOException
	 */
	public ModelAndView getBranchBanksOptions(final HttpServletRequest request,
			final HttpServletResponse response, final SearchParamInfoDTO searchBTO)
			throws PossException, IOException {

		Long defaultBankNo = ServletRequestUtils.getLongParameter(request,
				"defaultBankNo", -1);
		searchBTO.setResultSize(1000);// 最大为1000条
		List<SearchResultInfoDTO> list = luceneService
				.searchUnionBankCodeInfo(searchBTO);
		response.setContentType("text/plain;charset=UTF-8");
		StringBuffer sb = new StringBuffer("");
		if (!list.isEmpty()) {
			if (defaultBankNo > -1) {
				for (SearchResultInfoDTO srDTO : list) {
					sb.append("<option"
							+ " value=\""
							+ srDTO.getBankNo()
							+ "\""
							+ (Long.valueOf(srDTO.getBankNo()).longValue() == Long
									.valueOf(defaultBankNo).longValue() ? " selected=\"selected\""
									: "") + " >" + srDTO.getBankName()
							+ "</option>");
				}
			} else {
				for (SearchResultInfoDTO srDTO : list) {
					sb.append("<option" + " " + " value=\"" + srDTO.getBankNo()
							+ "\">" + srDTO.getBankName() + "</option>");
				}

			}

		}
		response.getWriter().write(sb.toString());
		//ObjectUtils.instanceByClass("d") ;
		return null;
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("bankList", list);
		// map.put("defaultBankNo", defaultBankNo);
		// return new ModelAndView(branchBanksSelectView,map);
	}
	/**
	 * 得到分行的select的options
	 * 
	 * @param request
	 * @param response
	 * @param searchBTO
	 * @return
	 * @throws PossException
	 * @throws IOException
	 */
	public ModelAndView getBranchBanksOptions_2(final HttpServletRequest request,
			final HttpServletResponse response, final SearchParamInfoDTO searchBTO)
					throws PossException, IOException {
		
		Long defaultBankNo = ServletRequestUtils.getLongParameter(request,
				"defaultBankNo", -1);
		searchBTO.setResultSize(1000);// 最大为1000条
		/*List<SearchResultInfoDTO> list = luceneService
				.searchUnionBankCodeInfo(searchBTO);*/
		BankBrancheInfoDto bankBrancheInfo = new BankBrancheInfoDto() ;
		bankBrancheInfo.setBankName(searchBTO.getBankName());
		bankBrancheInfo.setCity(searchBTO.getCityName());
		bankBrancheInfo.setProvince(searchBTO.getProvinceName());
		
		List<BankBrancheInfoDto> list = this.bankBrancheInfoService.findByCondition(bankBrancheInfo) ;
		
		response.setContentType("text/plain;charset=UTF-8");
		StringBuffer sb = new StringBuffer("");
		if (CollectionUtils.isNotEmpty(list)) {
			if (defaultBankNo > -1) {
				for (BankBrancheInfoDto srDTO : list) {
					sb.append("<option"
							+ " value=\""
							+ srDTO.getBankNumber()
							+ "\""
							+ (Long.valueOf(srDTO.getBankNumber()).longValue() == Long
							.valueOf(defaultBankNo).longValue() ? " selected=\"selected\""
									: "") + " >" + srDTO.getBankKaihu()
									+ "</option>");
				}
			} else {
				for (BankBrancheInfoDto srDTO : list) {
					sb.append("<option" + " " + " value=\"" + srDTO.getBankNumber()
							+ "\">" + srDTO.getBankKaihu() + "</option>");
				}
				
			}
			
		}
		response.getWriter().write(sb.toString());
		return null;
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("bankList", list);
		// map.put("defaultBankNo", defaultBankNo);
		// return new ModelAndView(branchBanksSelectView,map);
	}

	/**
	 * 根据查询条件，过滤分行select的options
	 * @author Davis.guo at 2016-08-09
	 * 
	 * @param request
	 * @param response
	 * @param searchBTO
	 * @return
	 * @throws PossException
	 * @throws IOException
	 */
	public ModelAndView queryBranchBanksOptions(final HttpServletRequest request,
			final HttpServletResponse response, final SearchParamInfoDTO searchBTO)
					throws PossException, IOException {
		
		Long defaultBankNo = ServletRequestUtils.getLongParameter(request,
				"defaultBankNo", -1);
		searchBTO.setResultSize(1000);// 最大为1000条
		/*List<SearchResultInfoDTO> list = luceneService
				.searchUnionBankCodeInfo(searchBTO);*/
		BankBrancheInfoDto bankBrancheInfo = new BankBrancheInfoDto() ;
		bankBrancheInfo.setBankName(searchBTO.getBankName());
		bankBrancheInfo.setCity(searchBTO.getCityName());
		bankBrancheInfo.setProvince(searchBTO.getProvinceName());
		bankBrancheInfo.setBankKaihu(searchBTO.getBankKaihu());
		
		List<BankBrancheInfoDto> list = this.bankBrancheInfoService.findByCondition(bankBrancheInfo) ;
		
		response.setContentType("text/plain;charset=UTF-8");
		StringBuffer sb = new StringBuffer("");
		if (CollectionUtils.isNotEmpty(list)) {
			if (defaultBankNo > -1) {
				for (BankBrancheInfoDto srDTO : list) {
					sb.append("<option"
							+ " value=\""
							+ srDTO.getBankNumber()
							+ "\""
							+ (Long.valueOf(srDTO.getBankNumber()).longValue() == Long
							.valueOf(defaultBankNo).longValue() ? " selected=\"selected\""
									: "") + " >" + srDTO.getBankKaihu()
									+ "</option>");
				}
			} else {
				for (BankBrancheInfoDto srDTO : list) {
					sb.append("<option" + " " + " value=\"" + srDTO.getBankNumber()
							+ "\">" + srDTO.getBankKaihu() + "</option>");
				}
				
			}
			
		}
		response.getWriter().write(sb.toString());
		return null;
	}

	/**
	 * AJAX验证卡号是分正确
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws PossException
	 * @throws IOException
	 */
	public ModelAndView validCardNo(final HttpServletRequest request,
			final HttpServletResponse response) throws PossException, IOException {

		String bankId = ServletRequestUtils.getStringParameter(request,
				"bankId", "");
		String cardNo = ServletRequestUtils.getStringParameter(request,
				"cardNo", "");
		//boolean ok = cardBinInfoFactoryService.isValidCardBin(bankId, cardNo);
//		String result = ok ? "S" : "F";
//		response.getWriter().write(result);
		return null;
	}

	public void setBankBrancheInfoService(
			final BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}
	
}
