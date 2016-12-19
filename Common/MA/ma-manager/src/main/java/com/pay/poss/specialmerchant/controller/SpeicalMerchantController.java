package com.pay.poss.specialmerchant.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.comm.LiquidateModeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.poss.merchantmanager.model.Relation;
import com.pay.poss.specialmerchant.dto.SpCardInfoDto;
import com.pay.poss.specialmerchant.dto.SpEnumInfoDto;
import com.pay.poss.specialmerchant.dto.SpecialMerchantDto;
import com.pay.poss.specialmerchant.service.ISpEnumInfoService;
import com.pay.poss.specialmerchant.service.SpCardInfoService;
import com.pay.poss.specialmerchant.service.SpecialMerchantService;
import com.pay.poss.specialmerchant.utils.FileProcessUtil;
import com.pay.util.SpringControllerUtils;

public class SpeicalMerchantController extends MultiActionController {
	private Log logger = LogFactory.getLog(SpeicalMerchantController.class);
	
	/**
	 * 查询索引页面
	 */
	private String smInitView;
	/**
	 * 查询显示页面
	 */
	private String smListView;
	/**
	 * 编辑特约商户页面
	 */
	private String smEditView;
	/**
	 * 添加特约商户页面
	 */
	private String smAddView;
	private String smEditResultView;
	/**
	 * 初始查询页面跳转view
	 */
	private String redirectView;
	private String redirectAddView;
	private String redirectEditView;
	private String smDetailView;
	/**
	 * 城市服务接口
	 */
	private CityService cityService;
	/**
	 * 省份服务接口
	 */
	private ProvinceService provinceService;
	/**
	 * 特约商户服务接口
	 */
	private SpecialMerchantService specialMerchantService;
	/**
	 * 卡类型，经营范围服务接口
	 */
	private ISpEnumInfoService spEnumInfoService;
	/**
	 * 商户支持卡类型
	 */
	private SpCardInfoService spCardInfoService;
	public SpecialMerchantService getSpecialMerchantService() {
		return specialMerchantService;
	}
	public void setSpecialMerchantService(
			SpecialMerchantService specialMerchantService) {
		this.specialMerchantService = specialMerchantService;
	}
	public String getSmInitView() {
		return smInitView;
	}
	public void setSmInitView(String smInitView) {
		this.smInitView = smInitView;
	}
	public String getSmListView() {
		return smListView;
	}
	public void setSmListView(String smListView) {
		this.smListView = smListView;
	}
	public String getSmEditView() {
		return smEditView;
	}
	public void setSmEditView(String smEditView) {
		this.smEditView = smEditView;
	}
	public String getSmAddView() {
		return smAddView;
	}
	public void setSmAddView(String smAddView) {
		this.smAddView = smAddView;
	}
	public String getSmEditResultView() {
		return smEditResultView;
	}
	public void setSmEditResultView(String smEditResultView) {
		this.smEditResultView = smEditResultView;
	}
	
	/**
	 * 特约商户查询初始index
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initSpecialMerchant(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		String query_spMerchantName = (String)request.getSession().getAttribute("query_spMerchantName");
		String query_spCity = (String)request.getSession().getAttribute("query_spCity");
		String query_spRangId = (String)request.getSession().getAttribute("query_spRangId");
		List<CityDTO> cityList = initCityData();
		List<SpEnumInfoDto> spEnumInfoList = initRangIdData();
		dataMap.put("cityList", cityList);
		dataMap.put("rangIdList", spEnumInfoList);
		if(query_spMerchantName == null){
			dataMap.put("query_spMerchantName", "");
		}
		else{
			dataMap.put("query_spMerchantName", query_spMerchantName);
		}
		if(query_spCity == null){
			dataMap.put("query_spCity", "");
		}else{
			dataMap.put("query_spCity", query_spCity);
		}
		if(query_spRangId == null){
			dataMap.put("query_spRangId", "");
		}else{
			dataMap.put("query_spRangId", query_spRangId);
		}
		return new ModelAndView(smInitView).addAllObjects(dataMap);
	}
	
	/**
	 * 特约商户查询初始List
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView listSpecialMerchant(HttpServletRequest request,HttpServletResponse response) throws Exception{
		SpecialMerchantDto dto = new SpecialMerchantDto();
		String spMerchantName = request.getParameter("spMerchantName");
		String provinceCode = request.getParameter("provinceCode");
		String range_id = request.getParameter("rangeId");
		/*String cardTypeCode = request.getParameter("card_type_code");*/
		if(StringUtils.isNotEmpty(spMerchantName)){
			dto.setSp_merchant_name(spMerchantName);
		}
		if(StringUtils.isNotEmpty(provinceCode)){
			dto.setProvince_code(provinceCode);
		}
		if(StringUtils.isNotEmpty(range_id)){
			dto.setRange_id(Long.valueOf(range_id));
		}
		/*if(StringUtils.isNotEmpty(cardTypeCode)){
			dto.setCard_type_code(cardTypeCode);
		}*/
		request.getSession().setAttribute("query_spMerchantName", spMerchantName);
		request.getSession().setAttribute("query_spCity", provinceCode);
		request.getSession().setAttribute("query_spRangId", range_id);
		
		Page<SpecialMerchantDto> page = PageUtils.getPage(request);
		int pageEnd = page.getPageNo()*page.getPageSize();
		int pageStart = 0;
		if((page.getPageNo()-1)==0){
			pageStart = 0;
		}else{
			pageStart = (page.getPageNo()-1)*page.getPageSize();
		}
		Map<String,Object> param = new HashMap<String,Object>();
		if(StringUtils.isNotBlank(dto.getSp_merchant_name())){
			param.put("sp_merchant_name", dto.getSp_merchant_name());
		}
		if(dto.getRange_id() != null){
			param.put("range_id", dto.getRange_id());
		}
		if(StringUtils.isNotBlank(dto.getCard_type_code())){
			param.put("card_type_code", dto.getCard_type_code());
		}
		if(StringUtils.isNotBlank(dto.getProvince_code())){
			param.put("province_code", dto.getProvince_code());
		}
		param.put("pageStartRow", pageStart);
		param.put("pageEndRow", pageEnd);
		List<SpecialMerchantDto> merchantList = specialMerchantService.querySpecialMerchantByPage(param);
		Integer merchantLogListCount = specialMerchantService.querySpecialMerchantCount(param);
		page.setResult(merchantList);
		page.setTotalCount(merchantLogListCount);
		return new ModelAndView(smListView).addObject("page", page);
	
	}
	
	/**
	 * 添加特约商户index
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addSpecialMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String info = request.getParameter("info");
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap = initData();
		List<SpEnumInfoDto> spEnumInfoList = initRangIdData();
		dataMap.put("rangIdList", spEnumInfoList);
		if(StringUtils.isNotEmpty(info)){
			dataMap.put("info", info);
		}
		return new ModelAndView(smAddView).addAllObjects(dataMap);
	}
	/**
	 * 编辑特约商户index
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView editSpecialMerchant(HttpServletRequest request, HttpServletResponse response)throws Exception{
		String info = request.getParameter("info");
		String spMerchantId = request.getParameter("sp_merchant_id");
		//Assert.notNull(spMerchantId, "special merchant id must be not null");
		if(spMerchantId == null){
			String url = response.encodeRedirectURL(this.getRedirectView());
			return new ModelAndView(url);
		}
		Long specialMerchantId = Long.valueOf(request.getParameter("sp_merchant_id"));
		SpecialMerchantDto smDto = this.specialMerchantService.querySpecialMerchantById(specialMerchantId);
		//Assert.notNull(smDto, "could not find special merchant for id");
		if(smDto == null){
			String url = response.encodeRedirectURL(this.getRedirectView());
			return new ModelAndView(url);
		}
		String spMerchantLogo = smDto.getSp_merchant_logo();
		if(StringUtils.isNotEmpty(spMerchantLogo)){
			if(!FileProcessUtil.fileIsExist(smDto.getSp_merchant_logo().replaceAll("/specialmerchant/", "/data/upload/specialmerchant/"))){
				smDto.setSp_merchant_logo("");
			}else{
	//			smDto.setSp_merchant_logo(spMerchantLogo.replaceAll("/data/upload/specialmerchant/", "/file/specialmerchant/"));
				smDto.setSp_merchant_logo("/file" + spMerchantLogo);
			}
		}
		
		CityDTO cityDto = cityService.findByCityCode(Integer.valueOf(smDto.getProvince_code()));
		Integer provinceCode = cityDto.getProvincecode();
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap = initData();
		dataMap.put("cityinfo", cityDto);
		dataMap.put("spmerchant", smDto);
		List<SpEnumInfoDto> spEnumInfoList = initRangIdData();
		dataMap.put("rangIdList", spEnumInfoList);
		if(StringUtils.isNotEmpty(info)){
			dataMap.put("info", info);
		}
		return new ModelAndView(smEditView).addAllObjects(dataMap);
	}
	/**
	 * 编辑特约商户处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView editSpecialMerchantForResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = response.encodeRedirectURL(this.getRedirectEditView());
		SpecialMerchantDto dto = new SpecialMerchantDto();
		String cityCode = request.getParameter("city");
		bind(request, dto);
		if(StringUtils.isNotEmpty(cityCode)){
			dto.setProvince_code(cityCode);
		}
		
		Map<String, String> outMap = check(dto, request);
		if (StringUtils.isNotEmpty(outMap.get("errorMsg"))) {
			logger.info("入参校验" + outMap.get("errorMsg"));
			outMap.put("result", "0");
			outMap.put("validate", "入参校验不通过!");
			return new ModelAndView(url).addObject("info", "入参校验不通过，请确认!");
		}
		InputStream inputStreamBig = null;
		InputStream inputStreamSmall = null;
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
		MultipartFile imgFile1  =  multipartRequest.getFile("imgBig"); 
		MultipartFile imgFile2  =  multipartRequest.getFile("imgSmall"); 
		if (imgFile1 != null && imgFile1.getSize() > 0) {
			if(!FileProcessUtil.isImageFile(imgFile1.getOriginalFilename())){
				return new ModelAndView(url).addObject("info", "只支持上传图片格式(jpg,gif,jpeg,bmp,png)，请确认！");
			}
			inputStreamBig = imgFile1.getInputStream();
		}
		if (imgFile2 != null && imgFile2.getSize() > 0) {
			if(!FileProcessUtil.isImageFile(imgFile2.getOriginalFilename())){
				return new ModelAndView(url).addObject("info", "只支持上传图片格式(jpg,gif,jpeg,bmp,png)，请确认！");
			}
			inputStreamSmall = imgFile2.getInputStream();
		}

		Map<String,InputStream> imageMap = new HashMap<String,InputStream>();
		if(inputStreamBig != null){
			imageMap.put("imageBig", inputStreamBig);
		}
		if(inputStreamSmall != null){
			imageMap.put("imageSmall", inputStreamSmall);
		}
		try{
			specialMerchantService.updateSpecialMerchantRdTx(dto,imageMap);
			return new ModelAndView(url).addObject("info", "修改商户信息成功").addObject("sp_merchant_id", dto.getSp_merchant_id());
		}catch(Exception e){
			//e.printStackTrace();
			this.logger.error(e);
			return new ModelAndView(url).addObject("info", "修改商户信息失败").addObject("sp_merchant_id", dto.getSp_merchant_id());
		}
		
	}
	/**
	 * 添加特约商户处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addSpecialMerchantForResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String url = response.encodeRedirectURL(this.getRedirectAddView());
		SpecialMerchantDto dto = new SpecialMerchantDto();
		String cityCode = request.getParameter("city");
		bind(request, dto);
		dto.setProvince_code(cityCode);
		Map<String, String> outMap = check(dto, request);
		if (StringUtils.isNotEmpty(outMap.get("errorMsg"))) {
			logger.info("入参校验" + outMap.get("errorMsg"));
			outMap.put("result", "0");
			outMap.put("validate", "入参校验不通过!");
			return new ModelAndView(url).addObject("info", "入参校验不通过，请确认!");
		}
		if(StringUtils.isNotBlank(dto.getSp_merchant_name())){
			if(this.specialMerchantService.isMerchantExist(dto.getSp_merchant_name())){
				return new ModelAndView(url).addObject("info", "商户名称已经存在，请确认！");
			}
		}else{
			return new ModelAndView(url).addObject("info", "商户名称不能为空，请确认！" );
		}
		
		//
		InputStream inputStreamBig = null;
		InputStream inputStreamSmall = null;
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
		MultipartFile imgFile1  =  multipartRequest.getFile("imgBig"); 
		MultipartFile imgFile2  =  multipartRequest.getFile("imgSmall"); 
		if (imgFile1 != null && imgFile1.getSize() > 0) {
			if(!FileProcessUtil.isImageFile(imgFile1.getOriginalFilename())){
				return new ModelAndView(url).addObject("info", "只支持上传图片格式(jpg,gif,jpeg,bmp,png)，请确认！");
			}
			inputStreamBig = imgFile1.getInputStream();
		}
		if (imgFile2 != null && imgFile2.getSize() > 0) {
			if(!FileProcessUtil.isImageFile(imgFile2.getOriginalFilename())){
				return new ModelAndView(url).addObject("info", "只支持上传图片格式(jpg,gif,jpeg,bmp,png)，请确认！");
			}
			inputStreamSmall = imgFile2.getInputStream();
		}

		Map<String,InputStream> imageMap = new HashMap<String,InputStream>();
		if(inputStreamBig != null){
			imageMap.put("imageBig", inputStreamBig);
		}
		if(inputStreamSmall != null){
			imageMap.put("imageSmall", inputStreamSmall);
		}
		try{
			specialMerchantService.insertSpecialMerchantRdTx(dto,imageMap);
			return new ModelAndView(url).addObject("info", "添加商户成功");
		}catch(Exception e){
			//e.printStackTrace();
			this.logger.error(e);
			return new ModelAndView(url).addObject("info", "添加商户失败");
		}
		
	}
	/**
	 * 删除特约商户处理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ModelAndView delSpecialMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sp_merchant_id = request.getParameter("sp_merchant_id");
		String url = response.encodeRedirectURL(this.getRedirectView());
		try{
			if (StringUtils.isNotEmpty(sp_merchant_id) && StringUtils.isNumeric(sp_merchant_id)) {
				SpecialMerchantDto smDto = new SpecialMerchantDto();
				smDto.setSp_merchant_id(Long.valueOf(sp_merchant_id));
				SpecialMerchantDto existsm = specialMerchantService.querySpecialMerchantById(smDto.getSp_merchant_id());
				if(existsm != null){
					specialMerchantService.deleteSpecialMerchant(existsm);
				}else{
					String error = "删除记录不存在!";
					return new ModelAndView(url).addObject("error",error);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			String error = "删除数据异常!";
			return new ModelAndView(url).addObject("error",error);
		}
		return new ModelAndView(url);
	}
	/**
	 * 特约商户详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView detailSpecialMerchant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sp_merchant_id = request.getParameter("sp_merchant_id");
		//Assert.hasLength(sp_merchant_id);
		try{
			if(sp_merchant_id == null){
				String url = response.encodeRedirectURL(this.getRedirectView());
				return new ModelAndView(url);
			}
			SpecialMerchantDto spDto = specialMerchantService.querySpecialMerchantById(Long.valueOf(sp_merchant_id));
			if(spDto == null){
				String url = response.encodeRedirectURL(this.getRedirectView());
				return new ModelAndView(url);
			}
			String spMerchantLogo = spDto.getSp_merchant_logo();
			
			if(StringUtils.isNotEmpty(spMerchantLogo)){
				if(!FileProcessUtil.fileIsExist(spDto.getSp_merchant_logo().replaceAll("/specialmerchant/", "/data/upload/specialmerchant/"))){
					spDto.setSp_merchant_logo("");
				}
				else{
					spDto.setSp_merchant_logo("/file" + spMerchantLogo);
					//spDto.setSp_merchant_logo(spMerchantLogo.replaceAll("/data/upload/specialmerchant/", "/file/specialmerchant/"));
				}
				String spMerchantLogoBig = spMerchantLogo.substring(0, spMerchantLogo.lastIndexOf(".")) + "_big" + spMerchantLogo.substring(spMerchantLogo.lastIndexOf("."));
				if(FileProcessUtil.fileIsExist(spMerchantLogoBig.replaceAll("/specialmerchant/", "/data/upload/specialmerchant/"))){
					spDto.setSp_merchant_logo_big("/file" + spMerchantLogoBig);
//					spDto.setSp_merchant_logo_big(spMerchantLogoBig.replaceAll("/data/upload/specialmerchant/", "/file/specialmerchant/"));
				}
			}
			
			//query cityname
			//query rangeId
			//query cardtype intro
			CityDTO cityinfo = this.cityService.findByCityCode(Integer.valueOf(spDto.getProvince_code()));
			SpEnumInfoDto spEnumInfo = new SpEnumInfoDto();
			spEnumInfo.setEnumType(2L);
			spEnumInfo.setEnumCode(String.valueOf(spDto.getRange_id()));
			List<SpEnumInfoDto> spEnumInfoList = this.spEnumInfoService.queryRangIdOrCardType(spEnumInfo);//查询经营范围名称
			
			SpCardInfoDto spCardInfoDto = new SpCardInfoDto();
			spCardInfoDto.setSpMerchantId(spDto.getSp_merchant_id());
			List<SpCardInfoDto> spCardInfoList = new ArrayList<SpCardInfoDto>();
			spCardInfoList = spCardInfoService.queryMerchantCardInfo(spCardInfoDto);
			
			Map<String, Object> dataMap = new Hashtable<String, Object>();
			dataMap.put("spCardInfoList", spCardInfoList);
			if(spEnumInfoList != null && spEnumInfoList.size() > 0){
				dataMap.put("spEnumInfo", spEnumInfoList.get(0));
			}else{
				dataMap.put("spEnumInfo", new SpEnumInfoDto());
			}
			dataMap.put("specialMerchant", spDto);
			dataMap.put("cityinfo",cityinfo);
			return new ModelAndView(smDetailView).addAllObjects(dataMap);
		}catch(Exception e){
			//e.printStackTrace();
			this.logger.error(e);
			String url = response.encodeRedirectURL(this.getRedirectView());
			return new ModelAndView(url);
		}
	}
	/**
	 * 校验特约商户名称是否存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView validateSpecialMerchantName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String,String> paraMap = new HashMap<String,String>();
		String spMerchantName = request.getParameter("sp_merchant_name");
		if(StringUtils.isEmpty(spMerchantName)){
			SpringControllerUtils.renderText(response, "false");
			return null;
		}
		String spMerchantId = request.getParameter("sp_merchant_id");
		if(StringUtils.isNotEmpty(spMerchantId)){
			SpecialMerchantDto dto = this.specialMerchantService.querySpecialMerchantById(Long.valueOf(spMerchantId));
			if(spMerchantName.equals(dto.getSp_merchant_name())){
				SpringControllerUtils.renderText(response, "true");
				return null;
			}
		}
		boolean isAllowResult = this.specialMerchantService.isMerchantExist(spMerchantName);
		if(isAllowResult){
			SpringControllerUtils.renderText(response, "false");
		}else{
			SpringControllerUtils.renderText(response, "true");
		}
		return null;
	}
	
	/**
	 * 校验特约商户请求参数
	 * @param smDto
	 * @param request
	 * @return
	 */
	public Map<String, String> check(SpecialMerchantDto smDto, HttpServletRequest request) {
		Map<String, String> outMap = new HashMap<String, String>();
		StringBuffer errorMsg = new StringBuffer();
		if(StringUtils.isBlank(smDto.getSp_merchant_name())){
			errorMsg = errorMsg.append("特约商户名称不能为空,");
		}else if(smDto.getSp_merchant_name().length() > 255){
			errorMsg = errorMsg.append("特约商户名称长度不能超过255位,");
		}
		if(StringUtils.isBlank(smDto.getProvince_code())){
			errorMsg = errorMsg.append("省份城市code不能为空,");
		}else if(smDto.getProvince_code().length() > 9){
			errorMsg = errorMsg.append("省份城市code长度不能超过9位,");
		}
		if(StringUtils.isBlank(smDto.getAddr())){
			errorMsg = errorMsg.append("特约商户地址不能为空,");
		}else if(smDto.getAddr().length() > 128){
			errorMsg = errorMsg.append("特约商户地址长度不能超过128位,");
		}
		if(StringUtils.isBlank(smDto.getTel())){
			errorMsg = errorMsg.append("特约商户联系电话地址不能为空,");
		}else if(smDto.getTel().length() > 40){
			errorMsg = errorMsg.append("特约商户联系电话长度不能超过40位,");
		}
		if(smDto.getRange_id() == null){
			errorMsg = errorMsg.append(" 经营范围ID不能为空.");
		}

		outMap.put("errorMsg", errorMsg.toString());
		if (StringUtils.isNotEmpty(errorMsg.toString())) {
			outMap.put("result", "0");
			outMap.put("errorCode", "100");
		}
		return outMap;
	}
/**
 * 查询当前特约商户城市列表
 * @return
 */
private List<CityDTO> initCityData(){
	List<String> cityCodeList = specialMerchantService.querySpecialMerchantCity();
	List<CityDTO> cityDtoList = new ArrayList<CityDTO>();
	if(cityCodeList != null){
		for(int i=0; i< cityCodeList.size(); i++){
			CityDTO city = new CityDTO();
			city = cityService.findByCityCode(Integer.valueOf(cityCodeList.get(i)));
			cityDtoList.add(city);
		}
	}
	return cityDtoList;
}
private List<SpEnumInfoDto> initRangIdData(){
	List<SpEnumInfoDto> spEnumInfoList = new ArrayList<SpEnumInfoDto>();
	SpEnumInfoDto spEnumInfo = new SpEnumInfoDto();
	spEnumInfo.setEnumType(2L);
	spEnumInfoList = spEnumInfoService.queryRangIdOrCardType(spEnumInfo);
	return spEnumInfoList;
}
/**
 * 查询省份及对应城市
 * @return
 */
private Map<String,Object> initData(){
    	
    	List<ProvinceDTO> provinceList = provinceService.findAll();
		List<Relation> relationList = new ArrayList<Relation>();

		for (int i = 0; i < provinceList.size(); i++) {
			ProvinceDTO province = (ProvinceDTO) provinceList.get(i);
			List<CityDTO> cityList = cityService.findByProvinceId(province.getProvincecode());

			for (int j = 0; j < cityList.size(); j++) {
				CityDTO city = (CityDTO) cityList.get(j);
				Relation relation = new Relation();
				relation.setFatherCode(province.getProvincecode().toString());
				relation.setCode(city.getCitycode().toString());
				relation.setName(city.getCityname());
				relationList.add(relation);
			}
		}
				
		LiquidateModeEnum[] liquidateModeEnum = LiquidateModeEnum.values();
//		NationEnum[] nationEnum = NationEnum.values();
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		
		dataMap.put("provinceList", provinceList);
		dataMap.put("relationList", relationList);
		
    	return dataMap;
    }

	
	public CityService getCityService() {
		return cityService;
	}
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	public ProvinceService getProvinceService() {
		return provinceService;
	}
	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}
	public String getRedirectView() {
		return redirectView;
	}
	public void setRedirectView(String redirectView) {
		this.redirectView = redirectView;
	}
	public ISpEnumInfoService getSpEnumInfoService() {
		return spEnumInfoService;
	}
	public void setSpEnumInfoService(ISpEnumInfoService spEnumInfoService) {
		this.spEnumInfoService = spEnumInfoService;
	}
	public String getSmDetailView() {
		return smDetailView;
	}
	public void setSmDetailView(String smDetailView) {
		this.smDetailView = smDetailView;
	}
	public SpCardInfoService getSpCardInfoService() {
		return spCardInfoService;
	}
	public void setSpCardInfoService(SpCardInfoService spCardInfoService) {
		this.spCardInfoService = spCardInfoService;
	}
	public String getRedirectAddView() {
		return redirectAddView;
	}
	public void setRedirectAddView(String redirectAddView) {
		this.redirectAddView = redirectAddView;
	}
	public String getRedirectEditView() {
		return redirectEditView;
	}
	public void setRedirectEditView(String redirectEditView) {
		this.redirectEditView = redirectEditView;
	}
}
