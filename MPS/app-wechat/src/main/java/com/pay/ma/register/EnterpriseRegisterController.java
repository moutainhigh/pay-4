package com.pay.ma.register;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberInfoDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.member.service.RegisterService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.LuceneService;
import com.pay.util.StringUtil;

public class EnterpriseRegisterController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private String indexView;
	private String resultView;
	private String enterpriseRegisterNav;
	private String cooperation;
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
		System.out.println(registerCmd);
		ModelAndView view = new ModelAndView(resultView);
		MemberInfoDto memberInfoDto = new MemberInfoDto();
		BeanUtils.copyProperties(registerCmd, memberInfoDto);
		memberInfoDto.setEmail(registerCmd.getLoginName());
		memberInfoDto.setType(MemberTypeEnum.MERCHANT.getCode());
		memberInfoDto
				.setEnterpriseType(MerchantTypeEnum.CROSSPAY_ENTERPRISEMERCHANT
						.getCode() + "");
		memberInfoDto.setMerchantCodePrefix(merchantCodePrefix);
		//----------------------------------
		memberInfoDto.setPayPassWord(registerCmd.getPayPassWord());
		//----------------------------------
		String openBanks = registerCmd.getBranchBankId();
		String[] openBankArr = openBanks.split(",");

		try {
			memberInfoDto.setBranchBankId(openBankArr[1]);
			memberInfoDto.setBigBankName(openBankArr[0]);
			registerService.registerRdTx(memberInfoDto);
		} catch (Exception e) {
			logger.error("register error:", e);
			view.addObject("errorMsg", "errorMsg");
		}
		//----------------------------------------处理五证上传Sta---------------------
		SimpleDateFormat formatter = null ;
		/*String memberCode = "" ;
		LoginSession loginSession = SessionHelper.getLoginSession();
		logger.info("loginSession=============>>>>>" + loginSession);
		if(null != loginSession){
			memberCode = loginSession.getMemberCode() ;
		}else{	//非法请求路径
			memberCode = "nosessionPath" ;
		}*/
		
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){ 
                	formatter = new SimpleDateFormat("yyyyMMddHHmmsss") ;
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        //重命名上传后的文件名  
                        //String fileName = "demoUpload" + file.getOriginalFilename();  
                        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename() ;
                        //定义上传路径  
                        //String path = "E:/" + fileName;
                        String realPath = request.getSession().getServletContext().getRealPath(File.separator + "upload") ;
                        String path = realPath + File.separator + formatter.format(new Date()) + File.separator + fileName ;
                        //父级目录不存在，创建父级目录
                        if(!new File(path).getParentFile().exists()){
                        	new File(path).mkdirs() ;
                        }
                        File localFile = new File(path);  
                        file.transferTo(localFile);  
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                logger.info("==========>>>>>>>>>>>>>>>>>Every certificates upload takes " + (finaltime - pre) + "seconds");
            }  
              
        } 
		//----------------------------------------处理五证上传End---------------------
		
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

		List<SearchResultInfoDTO> results = new ArrayList<SearchResultInfoDTO>();
		StringBuffer sbf = new StringBuffer();

		String bankCode = request.getParameter("bankOrgCode");
		try {

			SearchParamInfoDTO params = new SearchParamInfoDTO();
			params.setResultSize(300);
			params.setBankName(StringUtil.null2String(request.getParameter("b")));
			params.setCityName(StringUtil.null2String(request.getParameter("c")));
			params.setProvinceName(StringUtil.null2String(request
					.getParameter("p")));
			params.setKeyWord(StringUtil.null2String(request.getParameter("k")));

			params.setType(1);
			results = luceneService.searchBankUnionCodes(params);

			for (SearchResultInfoDTO searchResultInfoDTO : results) {
				sbf.append("<option value='"
						+ searchResultInfoDTO.getBankName() + ","
						+ searchResultInfoDTO.getBankNo() + "'>");
				sbf.append(searchResultInfoDTO.getBankName());
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
	
	//到达商户注册导航页面
	public ModelAndView registerNav(HttpServletRequest request, HttpServletResponse response){

		return new ModelAndView(enterpriseRegisterNav) ;
	}
	//到达了解合作页面
	public ModelAndView cooperation(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView(cooperation);
	}
	
	//-------------------------setter  
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

	public String getEnterpriseRegisterNav() {
		return enterpriseRegisterNav;
	}

	public void setEnterpriseRegisterNav(String enterpriseRegisterNav) {
		this.enterpriseRegisterNav = enterpriseRegisterNav;
	}

	public String getCooperation() {
		return cooperation;
	}

	public void setCooperation(String cooperation) {
		this.cooperation = cooperation;
	}
	
	
}
