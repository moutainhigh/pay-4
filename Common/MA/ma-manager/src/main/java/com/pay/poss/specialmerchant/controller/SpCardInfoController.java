package com.pay.poss.specialmerchant.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.specialmerchant.dto.SpCardInfoDto;
import com.pay.poss.specialmerchant.dto.SpEnumInfoDto;
import com.pay.poss.specialmerchant.dto.SpecialMerchantDto;
import com.pay.poss.specialmerchant.service.ISpEnumInfoService;
import com.pay.poss.specialmerchant.service.SpCardInfoService;
import com.pay.poss.specialmerchant.service.SpecialMerchantService;
import com.pay.util.JSonUtil;
public class SpCardInfoController extends MultiActionController {
	private Log logger = LogFactory.getLog(SpeicalMerchantController.class);
	private SpCardInfoService spCardInfoService;
	private ISpEnumInfoService spEnumInfoService;
	private SpecialMerchantService specialMerchantService;
	private String initSpCardView;
	private String listSpCardView;
	public ModelAndView initCardInfo(HttpServletRequest request, HttpServletResponse response){
		String spMerchantId = request.getParameter("spMerchantId");
		SpecialMerchantDto smDto = specialMerchantService.querySpecialMerchantById(Long.valueOf(spMerchantId));
		SpEnumInfoDto spEnumInfo = new SpEnumInfoDto();
		spEnumInfo.setEnumType(1L);
		List<SpEnumInfoDto> spEnumInfoList = spEnumInfoService.queryRangIdOrCardType(spEnumInfo);
		return new ModelAndView(initSpCardView).addObject("spmerchant", smDto).addObject("spEnumInfoList",spEnumInfoList);
	}
	
	public ModelAndView listCardInfo(HttpServletRequest request, HttpServletResponse response){
		String spMerchantId = request.getParameter("spMerchantId");
		SpCardInfoDto dto = new SpCardInfoDto();
		if(StringUtils.isNotEmpty(spMerchantId) ){
			dto.setSpMerchantId(Long.valueOf(spMerchantId));
		}else{
			dto.setSpMerchantId(0L);
		}
		List<SpCardInfoDto> spCardInfoList = new ArrayList<SpCardInfoDto>();
		spCardInfoList = this.spCardInfoService.queryMerchantCardInfo(dto);
		return new ModelAndView(listSpCardView).addObject("spCardInfoList", spCardInfoList);
	}
	public ModelAndView addCardInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		try{
			String spMerchantId = request.getParameter("spMerchantId");
			String cardTypeId = request.getParameter("cardTypeId");
			String discountInfo = request.getParameter("discountInfo");
			Long id =0L;
			SpCardInfoDto dto = new SpCardInfoDto();
			
			if(StringUtils.isNotEmpty(cardTypeId) && StringUtils.isNotEmpty(spMerchantId) ){
				//if spmerchantid is exist
				SpecialMerchantDto existSpDto = specialMerchantService.querySpecialMerchantById(Long.valueOf(spMerchantId));
				if(existSpDto == null){
					String info = "该商户不存在";
					response.getWriter().write(info);
					return null;
				}
				dto.setCardTypeId(Long.valueOf(cardTypeId));
				//if card type id exist
				SpCardInfoDto cardDto = new SpCardInfoDto();
				cardDto.setSpMerchantId(Long.valueOf(spMerchantId));
				cardDto.setCardTypeId(Long.valueOf(cardTypeId));
				List<SpCardInfoDto> cardList = this.spCardInfoService.queryspcardbyselective(cardDto);
				if(cardList != null && cardList.size() > 0){
					response.getWriter().write("该商户对应卡的折扣信息已存在。");
					return null;
				}
				if(StringUtils.isNotEmpty(discountInfo)){
					dto.setDiscountInfo(discountInfo);
				}
				dto.setSpMerchantId(Long.valueOf(spMerchantId));
				 id = this.spCardInfoService.insertSpCardInfo(dto);
			}
		
			
			String resultMsg = "S";
			if(id <1){
				resultMsg = "新增失败";
			}
			response.getWriter().write(resultMsg);
			return null;
		}catch(Exception e){
			//e.printStackTrace();
			this.logger.error(e);
			String resultMsg = "新增失败";
			response.getWriter().write(resultMsg);
			return null;
		}
	}

	public ModelAndView removeCardInfo(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		Long id = ServletRequestUtils.getLongParameter(request, "spMerchantCardId",-1L); 
		SpCardInfoDto dto = new SpCardInfoDto();
		dto.setSpMerchantCardId(id);
		boolean isRemove = this.spCardInfoService.deleteSpCardInfo(dto);
		String resultMsg = isRemove?"S":"删除失败";
		response.getWriter().write(resultMsg);
		return null;
	}
	
	public ModelAndView modifyCardInfo(HttpServletRequest request,
            HttpServletResponse response) throws IOException{
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String spMerchantCardId = request.getParameter("spMerchantCardId");
		String spMerchantId = request.getParameter("spMerchantId");
		String cardTypeId = request.getParameter("cardTypeId");
		String discountInfo = request.getParameter("discountInfo");
		SpCardInfoDto dto = new SpCardInfoDto();
		
		dto.setDiscountInfo(discountInfo);
		if(StringUtils.isNotEmpty(cardTypeId) ){
			dto.setCardTypeId(Long.valueOf(cardTypeId));
		}else{
			String resultMsg = "请选择卡类型";
			response.getWriter().write(resultMsg);
			return null;
		}
		if(StringUtils.isNotEmpty(spMerchantCardId)){
			dto.setSpMerchantCardId(Long.valueOf(spMerchantCardId));
		}else{
			String resultMsg = "更新失败,索引错误";
			response.getWriter().write(resultMsg);
			return null;
		}
		//check if card id exist
		
		SpCardInfoDto rawDto = this.spCardInfoService.querySpCardInfoById(Long.valueOf(spMerchantCardId));
		if(rawDto == null){
			String resultMsg = "卡折扣信息不存在";
			response.getWriter().write(resultMsg);
			return null;
		}
		if(!rawDto.getCardTypeId().equals(Long.valueOf(cardTypeId))){
			SpCardInfoDto cardDto = new SpCardInfoDto();
			//cardDto.setSpMerchantCardId(Long.valueOf(spMerchantCardId));
			cardDto.setSpMerchantId(rawDto.getSpMerchantId());
			cardDto.setCardTypeId(Long.valueOf(cardTypeId));
			List<SpCardInfoDto> cardList = this.spCardInfoService.queryspcardbyselective(cardDto);
			if(cardList != null && cardList.size() > 0){
				response.getWriter().write("修改失败，该商户对应卡折扣已经存在。");
				return null;
			}
		}
		boolean isOk =  this.spCardInfoService.updateSpCardInfo(dto);
		String resultMsg = isOk?"S":"更新失败";
		response.getWriter().write(resultMsg);
		return null;
	}
	
	public ModelAndView queryDetail(HttpServletRequest request,
            HttpServletResponse response){
		Long spMerchantCardId  = ServletRequestUtils.getLongParameter(request, "id",-1L);
		String htmlType  = ServletRequestUtils.getStringParameter(request, "htmlType",null);
		SpCardInfoDto dto = null;
		if(spMerchantCardId != -1){
			dto = spCardInfoService.querySpCardInfoById(spMerchantCardId);
			if(dto !=null){
				if(dto.getDiscountInfo() == null){
					dto.setDiscountInfo("");
				}
			}
		}
		
		if(htmlType.equals("json")){
			String js = JSonUtil.toJSonString(dto);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			try {
				response.getWriter().write(js);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	}
	
	
	public SpCardInfoService getSpCardInfoService() {
		return spCardInfoService;
	}
	public void setSpCardInfoService(SpCardInfoService spCardInfoService) {
		this.spCardInfoService = spCardInfoService;
	}
	public String getInitSpCardView() {
		return initSpCardView;
	}

	public void setInitSpCardView(String initSpCardView) {
		this.initSpCardView = initSpCardView;
	}

	public String getListSpCardView() {
		return listSpCardView;
	}

	public void setListSpCardView(String listSpCardView) {
		this.listSpCardView = listSpCardView;
	}

	public ISpEnumInfoService getSpEnumInfoService() {
		return spEnumInfoService;
	}

	public void setSpEnumInfoService(ISpEnumInfoService spEnumInfoService) {
		this.spEnumInfoService = spEnumInfoService;
	}

	public SpecialMerchantService getSpecialMerchantService() {
		return specialMerchantService;
	}

	public void setSpecialMerchantService(SpecialMerchantService specialMerchantService) {
		this.specialMerchantService = specialMerchantService;
	}
}
