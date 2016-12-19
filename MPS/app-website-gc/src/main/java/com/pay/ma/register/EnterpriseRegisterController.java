package com.pay.ma.register;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberInfoDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.member.service.RegisterService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.lucene.service.LuceneService;
import com.pay.util.StringUtil;

public class EnterpriseRegisterController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private String indexView;
	private String resultView;
	/**
	 * 银行服务类
	 */
	protected BankService bankService;
	private ProvinceService provinceService;
	private CityService cityService;
	private MemberService memberService;
	private RegisterService registerService;
	/**
	 * 出款行配置服务类
	 */
	protected ConfigBankService configBankService;
	/**
	 * 查询开户行服务类
	 */
	protected LuceneService luceneService;

	// 商户号注册前缀
	private String merchantCodePrefix;
	private BankBrancheInfoService bankBrancheInfoService ;

	/**
	 * 注册跳转页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(indexView);
		// 获取银行列表
		List<Bank> bankList = bankService.getWithdrawBanks();
		List<ProvinceDTO> provinceList = provinceService.findAll();

		view.addObject("bankList", bankList);
		view.addObject("provinceList", provinceList);
		return view;
	}

	/**
	 * 企业注册提交
	 * 
	 * @param request
	 * @param response
	 * @param registerCmd
	 * @return
	 * @throws Exception
	 */
	public ModelAndView register(HttpServletRequest request,
			HttpServletResponse response, EnterpriseRegisterCommand registerCmd)
			throws Exception {

		ModelAndView view = new ModelAndView(resultView);
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		BeanUtils.copyProperties(registerCmd, memberInfoDto);
		memberInfoDto.setEmail(registerCmd.getLoginName());
		memberInfoDto.setType(MemberTypeEnum.MERCHANT.getCode());
		memberInfoDto
				.setEnterpriseType(MerchantTypeEnum.CROSSPAY_ENTERPRISEMERCHANT
						.getCode() + "");
		memberInfoDto.setMerchantCodePrefix(merchantCodePrefix);
/*		String openBanks = registerCmd.getBranchBankId();
		String[] openBankArr = openBanks.split(",");*/

		try {
	/*		memberInfoDto.setBranchBankId(openBankArr[1]);
			memberInfoDto.setBigBankName(openBankArr[0]);*/  //之前取得是 bankId 和 bankName 后经改动不去 bankName 2016年4月27日20:13:31 delin.dong
			registerService.registerRdTx(memberInfoDto);
		} catch (Exception e) {
			logger.error("register error:", e);
			view.addObject("errorMsg", "errorMsg");
		}

		return view;
	}

	/**
	 * 检查会员是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkLoginNameIsExists(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String loginName = request.getParameter("loginName");

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);
		if (null != memberDto) {
			response.getWriter().print(0);
		} else {
			response.getWriter().print(1);
		}
		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryOpeningBankNameList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		//List<SearchResultInfoDTO> results = new ArrayList<SearchResultInfoDTO>();
		List<BankBrancheInfoDto> results = new ArrayList<BankBrancheInfoDto>();
		StringBuffer sbf = new StringBuffer();

		String bankCode = request.getParameter("bankOrgCode");
		try {

			//SearchParamInfoDTO params = new SearchParamInfoDTO();
			/*params.setResultSize(300);
			params.setBankName(StringUtil.null2String(request.getParameter("b")));
			params.setCityName(StringUtil.null2String(request.getParameter("c")));
			params.setProvinceName(StringUtil.null2String(request
					.getParameter("p")));
			params.setKeyWord(StringUtil.null2String(request.getParameter("k")));*/

			//params.setType(1);
			//results = luceneService.searchBankUnionCodes(params);
			BankBrancheInfoDto bankBrancheInfoDto = new BankBrancheInfoDto() ;
			bankBrancheInfoDto.setBankName(StringUtil.null2String(request.getParameter("b")));
			bankBrancheInfoDto.setProvince(StringUtil.null2String(request
					.getParameter("p")));
			bankBrancheInfoDto.setCity(StringUtil.null2String(request.getParameter("c")));
			bankBrancheInfoDto.setBankKaihu(StringUtil.null2String(request.getParameter("k")));
			bankBrancheInfoDto.setType(1);
			results = this.bankBrancheInfoService.findByCondition(bankBrancheInfoDto) ;

			for (BankBrancheInfoDto searchResultInfoDTO : results) {
				sbf.append("<option value='"
						//+ searchResultInfoDTO.getBankName() + ","
						+ searchResultInfoDTO.getBankNumber() + "'>");
				//sbf.append(searchResultInfoDTO.getBankName());
				sbf.append(searchResultInfoDTO.getBankKaihu());
				sbf.append("</option>");
			}
		} catch (Exception e) {
			logger.error("call luceneService.searchUnionBankCodeInfo faild", e);
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/hmtl");
		response.getWriter().print(sbf.toString());
		return null;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

	public void setMerchantCodePrefix(String merchantCodePrefix) {
		this.merchantCodePrefix = merchantCodePrefix;
	}

	public void setBankBrancheInfoService(
			BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}
	
}
