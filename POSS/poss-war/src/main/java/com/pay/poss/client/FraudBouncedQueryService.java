/**
 * 
 */
package com.pay.poss.client;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.poss.controller.fi.dto.BouncedFraudResultDTO;
import com.pay.poss.controller.fi.dto.BouncedOrderVO;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**
 * 拒付欺诈报表查询 File: FraudBouncedQueryService.java Description: Copyright 2016-2030
 * IPAYLINKS Corporation. All rights reserved. Date Author Changes 2016年6月12日
 * mmzhang Create
 *
 */
public class FraudBouncedQueryService {
	protected transient Log logger = LogFactory.getLog(getClass());
	private HessianInvokeService invokeService;
	private BouncedQueryService bouncedQueryService;
	private ChargeBackService chargeBackService;
	private EnterpriseBaseService enterpriseBaseService;
	

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}


	public static String getLastMonthFirstDay(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		String year = new SimpleDateFormat("yyyy").format(date);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		Date firstDate = cal.getTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(firstDate);
	}
	public static String getFirstDay(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		String year = new SimpleDateFormat("yyyy").format(date);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		Date firstDate = cal.getTime();
		
		return new SimpleDateFormat("yyyy-MM-dd").format(firstDate);
	}
	/***
	 * 拒付欺诈报表展现
	 * 
	 * @param beginCreateDate
	 * @param endCreateDate
	 * @param flag
	 * @param type
	 * @param partnerId
	 * @param merchantNo
	 * @return
	 * @throws ParseException
	 *             2016年6月13日 mmzhang add
	 */
	public Map<String, List<BouncedFraudResultDTO>> computeBouncedPossForView(
			String beginCreateDate, String endCreateDate, String flag, String type,
			String partnerId, String merchantNo,String orgcodeQuery) throws ParseException {
		Map<String, List<BouncedFraudResultDTO>> bouncedFraudMap = new LinkedHashMap<String, List<BouncedFraudResultDTO>>();

		Date datef = new SimpleDateFormat("yyyy-MM-dd").parse(beginCreateDate);
		String prebeginCreateDate = getLastMonthFirstDay(datef);
		String beginCreateDate2 = DateUtil.getFirstDay(datef);
		String tradeDates = DateUtil.formatDateTime(DateUtil.PATTERN, datef);
		String comDate = tradeDates.substring(0, 6);
		// 如果计算渠道的数据需要上个月的交易数据,已修改目前商户和渠道都一样，要计算master的数据
		beginCreateDate2 = prebeginCreateDate;
		Map paraMap = new HashMap();
		paraMap.put("status", "1");
		paraMap.put("beginCreateDate", beginCreateDate2);
		paraMap.put("endCreateDate", endCreateDate);
		paraMap.put("partnerId", partnerId);
		paraMap.put("merchantNo", merchantNo);
		paraMap.put("flag", flag);
		//交易数据
		List<Map> BouncedFrauds = this.bouncedFraudQuery(paraMap);
		Map<String, BouncedFraudResultDTO> bouncedFraudMapsIPAY = new HashMap<String, BouncedFraudResultDTO>();
		Map<String, BouncedOrderVO> bouncedOrderMapsIPAY = new HashMap<String, BouncedOrderVO>();

		// 商户拒付的数量和金额
		BouncedOrderVO bouncedOrderVO = new BouncedOrderVO();
		bouncedOrderVO.setCpdBeginTime(beginCreateDate2);
		bouncedOrderVO.setCpdEndTime(endCreateDate);
		bouncedOrderVO.setBouncedFlag("0");
		
		bouncedOrderVO.setChargeBackAmount("0");
		Map resultMap = chargeBackService.queryBouncedOrder(bouncedOrderVO, null);
		
		// 渠道拒付的数量和金额
		BouncedOrderVO bouncedOrderVO2 = new BouncedOrderVO();
		bouncedOrderVO2.setCpdBeginTime(beginCreateDate2);
		bouncedOrderVO2.setCpdEndTime(endCreateDate);
		bouncedOrderVO2.setCpType(0);
		
		bouncedOrderVO.setChargeBackAmount("0");
		Map resultMap2 = chargeBackService.queryBouncedOrder(bouncedOrderVO2, null);
		//拒付数据
		List<Map> bouncedOrderVOs = (List<Map>) resultMap.get("result");
		List<Map> bouncedOrderVOs2 = (List<Map>) resultMap2.get("result");
		Set<String> dateset=new HashSet<String>();
		Map<String,String> partnermap=new HashMap<String,String>();
		Set<String>  merchantset=new HashSet<String>();
		Map<String,BigDecimal> ratemap=new HashMap<String,BigDecimal>();
		
		//查询当前有效交易汇率
		Map<String, Object> paraMap3 = new HashMap();
		paraMap3.put("status","1");
		Map<String,Object> rateMaps = bouncedQueryService.transactionBaseRateQuery(paraMap3);
		List<Map> returnList=null;
		returnList = (List<Map>) rateMaps.get("list");
		for (Map map : returnList) {
			 //币种代码
			String currency=getMapValueByString(map,"currency");
			 //* 兑换的币种
			String targetCurrency=getMapValueByString(map,"targetCurrency");
			 //交换汇率
			String exchangeRate=getMapValueByString(map,"exchangeRate");
			String key=currency.concat(targetCurrency);
			ratemap.put(key,new BigDecimal(exchangeRate));
		}
		
			if ("0".equals(type)) {

				/*buildFlagMapsForPartner2(BouncedFrauds, bouncedFraudMapsIPAY,
						bouncedOrderMapsIPAY, bouncedOrderVOs,dateset,partnermap,ratemap);*/
				buildFlagMapsForPartner(BouncedFrauds, bouncedFraudMapsIPAY,
						bouncedOrderMapsIPAY, bouncedOrderVOs,dateset,partnermap,ratemap);
				setBouncedFraudAmountRateForViewPartner(bouncedFraudMapsIPAY, bouncedOrderMapsIPAY,
						bouncedFraudMap,endCreateDate,beginCreateDate);
				for (String date : dateset) {
					List<BouncedFraudResultDTO> bouncedFraudResults=bouncedFraudMap.get(date);
					if(null==bouncedFraudResults)
					{
						continue;
					}
					for (Entry<String, String> entry: partnermap.entrySet()) {
						String spartnerId1=entry.getKey();
						String partnerName=entry.getValue();
						String cardOrg1="VISA";
						String cardOrg2="MASTER";
						String key = date.concat("|").concat(cardOrg1).concat("|").concat(spartnerId1);
						if (!bouncedFraudMapsIPAY.containsKey(key)) {
							BouncedFraudResultDTO fraud = new BouncedFraudResultDTO();
							fraud.setPartnerId(spartnerId1);
							fraud.setPartnerName(partnerName);
							fraud.setCardOrg(cardOrg1);
							fraud.setAmount(BigDecimal.ZERO);
							fraud.setBouncedCount(BigDecimal.ZERO);
							fraud.setBouncedRate(BigDecimal.ZERO);
							fraud.setFraudAmount(BigDecimal.ZERO);
							fraud.setFraudRate(BigDecimal.ZERO);
							fraud.setBouncedMulct(BigDecimal.ZERO);
							fraud.setTotalAmount(BigDecimal.ZERO);
							fraud.setTotalCount(BigDecimal.ZERO);
							bouncedFraudResults.add(fraud);
						}
						String key2 = date.concat("|").concat(cardOrg2).concat("|").concat(spartnerId1);
						if (!bouncedFraudMapsIPAY.containsKey(key2)) {
							BouncedFraudResultDTO fraud = new BouncedFraudResultDTO();
							
							//如果拒付为0，有一种情况是五月没有数据，分母没有数据这部分被漏掉了，这里把六月的订单数和金额取出来
							   String mkey = gegMasterDate(date).concat("|").concat("MASTER").concat("|").concat(spartnerId1);

								BouncedFraudResultDTO fraudvo2 = bouncedFraudMapsIPAY.get(mkey);
								if (null == fraudvo2) {
									fraud.setTotalCount(BigDecimal.ZERO);
									fraud.setTotalAmount(BigDecimal.ZERO);
								} else {
									fraud.setTotalCount(fraudvo2.getThistotalCount());
									fraud.setTotalAmount(fraudvo2.getThistotalAmount().divide(new BigDecimal("1000")));
								}
							
							fraud.setPartnerId(spartnerId1);
							fraud.setPartnerName(partnerName);
							fraud.setCardOrg("MASTER");
							fraud.setAmount(BigDecimal.ZERO);
							fraud.setBouncedCount(BigDecimal.ZERO);
							fraud.setBouncedRate(BigDecimal.ZERO);
							fraud.setFraudAmount(BigDecimal.ZERO);
							fraud.setFraudRate(BigDecimal.ZERO);
							fraud.setBouncedMulct(BigDecimal.ZERO);
							bouncedFraudResults.add(fraud);
						}
						
						/*String key = date.concat("|").concat(spartnerId1);
						if(!bouncedFraudMapsIPAY.containsKey(key))
						{
							BouncedFraudResultDTO fraud=new BouncedFraudResultDTO();
							fraud.setPartnerId(spartnerId1);
							fraud.setPartnerName(partnerName);
							fraud.setAmount(BigDecimal.ZERO);
							fraud.setBouncedCount(BigDecimal.ZERO);
							fraud.setBouncedRate(BigDecimal.ZERO);
							fraud.setFraudAmount(BigDecimal.ZERO);
							fraud.setFraudRate(BigDecimal.ZERO);
							fraud.setBouncedMulct(BigDecimal.ZERO);
							fraud.setTotalAmount(BigDecimal.ZERO);
							fraud.setTotalCount(BigDecimal.ZERO);
							bouncedFraudResults.add(fraud);
						}*/
						
					}
					Collections.sort(bouncedFraudResults, new Comparator<BouncedFraudResultDTO>(){  
						  

						@Override
						public int compare(BouncedFraudResultDTO o1, BouncedFraudResultDTO o2) {
			                return o1.getPartnerId().concat(o1.getCardOrg()).compareTo(o2.getPartnerId().concat(o2.getCardOrg()));
						}  
			        });   
				}
			} else {
				buildFlagMapsForMerchant(BouncedFrauds, bouncedFraudMapsIPAY,
						bouncedOrderMapsIPAY, bouncedOrderVOs2,orgcodeQuery,dateset,merchantset,ratemap);
				setBouncedFraudAmountRateForView(bouncedFraudMapsIPAY, bouncedOrderMapsIPAY,
						bouncedFraudMap,orgcodeQuery,endCreateDate,beginCreateDate);
				for (String date : dateset) {
					List<BouncedFraudResultDTO> bouncedFraudResults=bouncedFraudMap.get(date);
					if(null==bouncedFraudResults)
					{
						continue;
					}
					for (String merchants : merchantset) {
						
						
						
					if ("true".equals(orgcodeQuery)) {
						String cardOrg3="VISA";
						String cardOrg4="MASTER";
						String orgName="";
						if ("10079001".equals(merchants)
								|| "10080001".equals(merchants)) {
							orgName="中银";
						}
						if ("10078001".equals(merchants)) {
							orgName="农业银行";
						}
						if ("10075001".equals(merchants)) {
							orgName="CREDORAX";
						}
						if ("10076001".equals(merchants)) {
							orgName="卡司";
						}
						if("".equals(orgName))
						{
							System.out.print("fraudvoOld.getOrgName()"+orgName);
						}
						String key = date.concat("|").concat(cardOrg3).concat("|").concat(merchants);
						if (!bouncedFraudMapsIPAY.containsKey(key)) {
							BouncedFraudResultDTO fraud = new BouncedFraudResultDTO();
							fraud.setCardOrg(cardOrg3);
							fraud.setOrgCode(merchants);
							fraud.setAmount(BigDecimal.ZERO);
							fraud.setBouncedCount(BigDecimal.ZERO);
							fraud.setBouncedRate(BigDecimal.ZERO);
							fraud.setFraudAmount(BigDecimal.ZERO);
							fraud.setFraudRate(BigDecimal.ZERO);
							fraud.setBouncedMulct(BigDecimal.ZERO);
							fraud.setTotalAmount(BigDecimal.ZERO);
							fraud.setTotalCount(BigDecimal.ZERO);
							fraud.setOrgName(orgName);
							bouncedFraudResults.add(fraud);
						}
						String key2 = date.concat("|").concat(cardOrg4).concat("|").concat(merchants);
						if (!bouncedFraudMapsIPAY.containsKey(key2)) {
							BouncedFraudResultDTO fraud = new BouncedFraudResultDTO();
							fraud.setOrgCode(merchants);
							fraud.setCardOrg(cardOrg4);
							fraud.setAmount(BigDecimal.ZERO);
							fraud.setBouncedCount(BigDecimal.ZERO);
							fraud.setBouncedRate(BigDecimal.ZERO);
							fraud.setFraudAmount(BigDecimal.ZERO);
							fraud.setFraudRate(BigDecimal.ZERO);
							fraud.setBouncedMulct(BigDecimal.ZERO);
							fraud.setTotalAmount(BigDecimal.ZERO);
							fraud.setTotalCount(BigDecimal.ZERO);
							fraud.setOrgName(orgName);
							bouncedFraudResults.add(fraud);
						}
					} else {
						String cardOrg1="VISA";
						String cardOrg2="MASTER";
						String key = date.concat("|").concat(cardOrg1).concat("|").concat(merchants);
						if (!bouncedFraudMapsIPAY.containsKey(key)) {
							BouncedFraudResultDTO fraud = new BouncedFraudResultDTO();
							fraud.setMerchantNo(merchants);
							fraud.setCardOrg(cardOrg1);
							fraud.setAmount(BigDecimal.ZERO);
							fraud.setBouncedCount(BigDecimal.ZERO);
							fraud.setBouncedRate(BigDecimal.ZERO);
							fraud.setFraudAmount(BigDecimal.ZERO);
							fraud.setFraudRate(BigDecimal.ZERO);
							fraud.setBouncedMulct(BigDecimal.ZERO);
							fraud.setTotalAmount(BigDecimal.ZERO);
							fraud.setTotalCount(BigDecimal.ZERO);
							bouncedFraudResults.add(fraud);
						}
						String key2 = date.concat("|").concat(cardOrg2).concat("|").concat(merchants);
						if (!bouncedFraudMapsIPAY.containsKey(key2)) {
							BouncedFraudResultDTO fraud = new BouncedFraudResultDTO();
							
							//如果拒付为0，有一种情况是五月没有数据，分母没有数据这部分被漏掉了，这里把六月的订单数和金额取出来
							   String mkey = gegMasterDate(date).concat("|").concat("MASTER").concat("|").concat(merchants);

								BouncedFraudResultDTO fraudvo2 = bouncedFraudMapsIPAY.get(mkey);
								if (null == fraudvo2) {
									fraud.setTotalCount(BigDecimal.ZERO);
									fraud.setTotalAmount(BigDecimal.ZERO);
								} else {
									fraud.setTotalCount(fraudvo2.getThistotalCount());
									fraud.setTotalAmount(fraudvo2.getThistotalAmount().divide(new BigDecimal("1000")));
								}
							
							fraud.setMerchantNo(merchants);
							fraud.setCardOrg("MASTER");
							fraud.setAmount(BigDecimal.ZERO);
							fraud.setBouncedCount(BigDecimal.ZERO);
							fraud.setBouncedRate(BigDecimal.ZERO);
							fraud.setFraudAmount(BigDecimal.ZERO);
							fraud.setFraudRate(BigDecimal.ZERO);
							fraud.setBouncedMulct(BigDecimal.ZERO);
							bouncedFraudResults.add(fraud);
						}
					}
						
					}
					if ("true".equals(orgcodeQuery)) {
						Collections.sort(bouncedFraudResults, new Comparator<BouncedFraudResultDTO>(){  
							@Override
							public int compare(BouncedFraudResultDTO o1, BouncedFraudResultDTO o2) {
				                return o1.getOrgCode().concat(o1.getCardOrg()).compareTo(o2.getOrgCode().concat(o2.getCardOrg()));
							}  
				        });
					}else{
						Collections.sort(bouncedFraudResults, new Comparator<BouncedFraudResultDTO>(){  
							@Override
							public int compare(BouncedFraudResultDTO o1, BouncedFraudResultDTO o2) {
								 int i = o1.getMerchantNo().concat(o1.getCardOrg()).compareTo(o2.getMerchantNo().concat(o2.getCardOrg()));
				                return i;
							}  
				        });
					}
					 
				}
			}
			
			List<Map.Entry<String, List<BouncedFraudResultDTO>>> mapsort 
			=new ArrayList<Map.Entry<String, List<BouncedFraudResultDTO>>>(bouncedFraudMap.entrySet());
			Collections.sort(mapsort, new Comparator<Map.Entry<String, List<BouncedFraudResultDTO>>>(){

				@Override
				public int compare(Map.Entry<String, List<BouncedFraudResultDTO>> o1,
						Entry<String, List<BouncedFraudResultDTO>> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
				
	        });
		Map<String, List<BouncedFraudResultDTO>> bouncedFraudMap2 = new LinkedHashMap<String, List<BouncedFraudResultDTO>>();
		for (Entry<String, List<BouncedFraudResultDTO>> entry : mapsort) {
			bouncedFraudMap2.put(entry.getKey(), entry.getValue());
		}
	  
		return bouncedFraudMap2;
	}

	
	private void setBouncedFraudAmountRateForViewPartner(
			Map<String, BouncedFraudResultDTO> bouncedFraudMapsIPAY,
			Map<String, BouncedOrderVO> bouncedOrderMapsIPAY,
			Map<String, List<BouncedFraudResultDTO>> bouncedFraudMap,
			String endCreateDate,String beginCreateDate2) {

		for (Entry<String, BouncedFraudResultDTO> entry : bouncedFraudMapsIPAY.entrySet()) {
			String[] keys=entry.getKey().split("\\|");
			String date=keys[0];
			String enddate=endCreateDate.substring(0,4).concat(endCreateDate.substring(5,7));
			String begindate=beginCreateDate2.substring(0,4).concat(beginCreateDate2.substring(5,7));
			if(date.compareTo(enddate)>0)
			{
				continue;
			}
			if(date.compareTo(begindate)<0)
			{
				continue;
			}
			
			BouncedOrderVO order = bouncedOrderMapsIPAY.get(entry.getKey());
			BouncedFraudResultDTO fraudvoOld = entry.getValue();
			BouncedFraudResultDTO fraudvo = new BouncedFraudResultDTO();
			fraudvo.setOrgCode(fraudvoOld.getOrgCode());
			fraudvo.setOrgName(fraudvoOld.getOrgName());
			fraudvo.setCardOrg(fraudvoOld.getCardOrg());
			fraudvo.setPartnerId(fraudvoOld.getPartnerId());
			fraudvo.setPartnerName(fraudvoOld.getPartnerName());
			fraudvo.setMerchantNo(fraudvoOld.getMerchantNo());
			fraudvo.setCreateDate(date);
			fraudvo.setBouncedMulct(fraudvoOld.getBouncedMulct());
			
			if(null!=fraudvoOld.getThistotalCount())
			{
			fraudvo.setThistotalCount(fraudvoOld.getThistotalCount());
			}else{
				fraudvo.setThistotalCount(BigDecimal.ZERO);
			}
			if(null!=fraudvoOld.getThistotalAmount())
			{
				fraudvo.setThistotalAmount(fraudvoOld.getThistotalAmount().divide(new BigDecimal("1000")));
			}else{
				fraudvo.setThistotalAmount(BigDecimal.ZERO);
			}
			fraudvo.setTotalCount(fraudvoOld.getTotalCount());
			fraudvo.setTotalAmount(fraudvoOld.getTotalAmount().divide(new BigDecimal("1000")));
			if(null !=order)
			{	
				BigDecimal bouncedCount=order.getBouncedCount();
				if(null==bouncedCount)
				{
					bouncedCount=BigDecimal.ZERO;
				}	
				BigDecimal fraudAmount=order.getFraudAmount();
				if(null==fraudAmount)
				{
					fraudAmount=BigDecimal.ZERO;
				}	
			BigDecimal bouncedRate = bouncedCount.divide(fraudvoOld.getTotalCount(), 4,
					BigDecimal.ROUND_HALF_UP);
			BigDecimal fraudRate = fraudAmount.divide(fraudvoOld.getTotalAmount(), 4,
					BigDecimal.ROUND_HALF_UP);
			fraudvo.setBouncedCount(bouncedCount);
			fraudvo.setFraudAmount(fraudAmount.divide(new BigDecimal("1000")));
			fraudvo.setBouncedRate(bouncedRate);
			fraudvo.setFraudRate(fraudRate);
			}else{
				fraudvo.setBouncedCount(BigDecimal.ZERO);
				fraudvo.setFraudAmount(BigDecimal.ZERO);
				fraudvo.setBouncedRate(BigDecimal.ZERO);
				fraudvo.setFraudRate(BigDecimal.ZERO);
				fraudvo.setBouncedMulct(BigDecimal.ZERO);
			}
			if ("MASTER".equals(fraudvoOld.getCardOrg())) {
				String mkey = "";
					mkey = fraudvoOld.getCreateDate().concat("|").concat("MASTER").concat("|")
							.concat(fraudvoOld.getPartnerId());

				BouncedFraudResultDTO fraudvo2 = bouncedFraudMapsIPAY.get(mkey);
				if (null == fraudvo2) {
					fraudvo.setTotalCount(BigDecimal.ZERO);
					fraudvo.setTotalAmount(BigDecimal.ZERO);
					fraudvo.setThistotalCount(fraudvoOld.getThistotalCount());
					if(null!=fraudvoOld.getThistotalAmount())
					{
						fraudvo.setThistotalAmount(fraudvoOld.getThistotalAmount().divide(new BigDecimal("1000")));
					}else{
						fraudvo.setThistotalAmount(BigDecimal.ZERO);
					}
				} else {
					fraudvo.setThistotalCount(fraudvoOld.getThistotalCount());
					if(null!=fraudvoOld.getThistotalAmount())
					{
						fraudvo.setThistotalAmount(fraudvoOld.getThistotalAmount().divide(new BigDecimal("1000")));
					}else{
						fraudvo.setThistotalAmount(BigDecimal.ZERO);
					}
					
					fraudvo.setTotalCount(fraudvo2.getTotalCount());
					if(null!=fraudvoOld.getTotalAmount())
					{
					fraudvo.setTotalAmount(fraudvo2.getTotalAmount().divide(new BigDecimal("1000")));
					}else{
						fraudvo.setTotalAmount(BigDecimal.ZERO);
					}
				}
			}
			if(bouncedFraudMap.containsKey(date))
			{
				bouncedFraudMap.get(date).add(fraudvo);
			}else{
				List<BouncedFraudResultDTO> bouncedFraudResults = new ArrayList<BouncedFraudResultDTO>();
				bouncedFraudResults.add(fraudvo);
				bouncedFraudMap.put(date, bouncedFraudResults);
			}
			
			
		}
	}
	
	private void setBouncedFraudAmountRateForView(
			Map<String, BouncedFraudResultDTO> bouncedFraudMapsIPAY,
			Map<String, BouncedOrderVO> bouncedOrderMapsIPAY,
			Map<String, List<BouncedFraudResultDTO>> bouncedFraudMap,
			String orgcodeQuery,String endCreateDate,String beginCreateDate2) {
		for (Entry<String, BouncedFraudResultDTO> entry : bouncedFraudMapsIPAY.entrySet()) {
			String[] keys=entry.getKey().split("\\|");
			String date=keys[0];
			String enddate=endCreateDate.substring(0,4).concat(endCreateDate.substring(5,7));
			String begindate=beginCreateDate2.substring(0,4).concat(beginCreateDate2.substring(5,7));
			if(date.compareTo(enddate)>0)
			{
				continue;
			}
			if(date.compareTo(begindate)<0)
			{
				continue;
			}
			
			BouncedOrderVO order = bouncedOrderMapsIPAY.get(entry.getKey());
			BouncedFraudResultDTO fraudvoOld = entry.getValue();
			BouncedFraudResultDTO fraudvo = new BouncedFraudResultDTO();
			fraudvo.setOrgCode(fraudvoOld.getOrgCode());
			String orgName=fraudvoOld.getOrgName();
			if("".equals(fraudvoOld.getOrgName()))
			{
				if ("10079001".equals(fraudvoOld.getOrgCode())
						|| "10080001".equals(fraudvoOld.getOrgCode())) {
					orgName="中银";
				}
				if ("10002001".equals(fraudvoOld.getOrgCode())) {
					orgName="农业银行";
				}
				if ("10075001".equals(fraudvoOld.getOrgCode())) {
					orgName="CREDORAX";
				}
				if ("10076001".equals(fraudvoOld.getOrgCode())) {
					orgName="卡司";
				}
			}
			fraudvo.setOrgName(orgName);
			
			fraudvo.setCardOrg(fraudvoOld.getCardOrg());
			fraudvo.setPartnerId(fraudvoOld.getPartnerId());
			fraudvo.setPartnerName(fraudvoOld.getPartnerName());
			fraudvo.setMerchantNo(fraudvoOld.getMerchantNo());
			fraudvo.setCreateDate(date);
			fraudvo.setBouncedMulct(fraudvoOld.getBouncedMulct());
			
			if(null!=fraudvoOld.getThistotalCount())
			{
			fraudvo.setThistotalCount(fraudvoOld.getThistotalCount());
			}else{
				fraudvo.setThistotalCount(BigDecimal.ZERO);
			}
			if(null!=fraudvoOld.getThistotalAmount())
			{
				fraudvo.setThistotalAmount(fraudvoOld.getThistotalAmount().divide(new BigDecimal("1000")));
			}else{
				fraudvo.setThistotalAmount(BigDecimal.ZERO);
			}
			fraudvo.setTotalCount(fraudvoOld.getTotalCount());
			fraudvo.setTotalAmount(fraudvoOld.getTotalAmount().divide(new BigDecimal("1000")));
			if(null !=order)
			{	
				BigDecimal bouncedCount=order.getBouncedCount();
				if(null==bouncedCount)
				{
					bouncedCount=BigDecimal.ZERO;
				}	
				BigDecimal fraudAmount=order.getFraudAmount();
				if(null==fraudAmount)
				{
					fraudAmount=BigDecimal.ZERO;
				}	
			BigDecimal bouncedRate = bouncedCount.divide(fraudvoOld.getTotalCount(), 4,
					BigDecimal.ROUND_HALF_UP);
			BigDecimal fraudRate = fraudAmount.divide(fraudvoOld.getTotalAmount(), 4,
					BigDecimal.ROUND_HALF_UP);
			fraudvo.setBouncedCount(bouncedCount);
			fraudvo.setFraudAmount(fraudAmount.divide(new BigDecimal("1000")));
			fraudvo.setBouncedRate(bouncedRate);
			fraudvo.setFraudRate(fraudRate);
			}else{
				fraudvo.setBouncedCount(BigDecimal.ZERO);
				fraudvo.setFraudAmount(BigDecimal.ZERO);
				fraudvo.setBouncedRate(BigDecimal.ZERO);
				fraudvo.setFraudRate(BigDecimal.ZERO);
				fraudvo.setBouncedMulct(BigDecimal.ZERO);
			}
			if ("MASTER".equals(fraudvoOld.getCardOrg())) {
				String mkey = "";
				if ("true".equals(orgcodeQuery)) {
					mkey = fraudvoOld.getCreateDate().concat("|").concat("MASTER").concat("|").concat(fraudvoOld.getOrgCode());
				} else {
					mkey = fraudvoOld.getCreateDate().concat("|").concat("MASTER").concat("|")
							.concat(fraudvoOld.getMerchantNo());
				}

				BouncedFraudResultDTO fraudvo2 = bouncedFraudMapsIPAY.get(mkey);
				if (null == fraudvo2) {
					fraudvo.setTotalCount(BigDecimal.ZERO);
					fraudvo.setTotalAmount(BigDecimal.ZERO);
					fraudvo.setThistotalCount(fraudvoOld.getThistotalCount());
					if(null!=fraudvoOld.getThistotalAmount())
					{
						fraudvo.setThistotalAmount(fraudvoOld.getThistotalAmount().divide(new BigDecimal("1000")));
					}else{
						fraudvo.setThistotalAmount(BigDecimal.ZERO);
					}
				} else {
					fraudvo.setThistotalCount(fraudvoOld.getThistotalCount());
					if(null!=fraudvoOld.getThistotalAmount())
					{
						fraudvo.setThistotalAmount(fraudvoOld.getThistotalAmount().divide(new BigDecimal("1000")));
					}else{
						fraudvo.setThistotalAmount(BigDecimal.ZERO);
					}
					
					fraudvo.setTotalCount(fraudvo2.getTotalCount());
					if(null!=fraudvoOld.getTotalAmount())
					{
					fraudvo.setTotalAmount(fraudvo2.getTotalAmount().divide(new BigDecimal("1000")));
					}else{
						fraudvo.setTotalAmount(BigDecimal.ZERO);
					}
				}
			}
			if(bouncedFraudMap.containsKey(date))
			{
				bouncedFraudMap.get(date).add(fraudvo);
			}else{
				List<BouncedFraudResultDTO> bouncedFraudResults = new ArrayList<BouncedFraudResultDTO>();
				bouncedFraudResults.add(fraudvo);
				bouncedFraudMap.put(date, bouncedFraudResults);
			}
			
			
		}
	}

	private void buildFlagMapsForMerchant( List<Map> BouncedFrauds,
			Map<String, BouncedFraudResultDTO> bouncedFraudsIPAY,
			Map<String, BouncedOrderVO> bouncedOrdersIPAY, List<Map> bouncedOrderVOs,String orgcodeQuery,
			Set<String> dateset,Set<String> merchantset,Map<String, BigDecimal> ratemap) {
		if(null!=bouncedOrderVOs && bouncedOrderVOs.size()>0)
		{	
		for (Map vo : bouncedOrderVOs) {
			BouncedOrderVO bouncedOrderVO1 = new BouncedOrderVO();
			if(null==vo.get("tradeDate"))
			{
				continue;
			}
			if(null==vo.get("cpdDate"))
			{
				continue;
			}
			if (null == vo.get("cardOrg")) {
				continue;
			}else if("null".equals(vo.get("cardOrg")) || (!"VISA".equals(vo.get("cardOrg")) && !"MASTER".equals(vo.get("cardOrg")))) {
				continue;
			}
			String tradeDates = DateUtil.getDateForLong(vo.get("tradeDate").toString(),DateUtil.PATTERN);
			String tradeDateRate = DateUtil.getDateForLong(vo.get("tradeDate").toString(),DateUtil.SIMPLE_DATE_FROMAT);
			String cpdDate=vo.get("cpdDate").toString();
			String cpdDates = cpdDate.substring(0,4).concat(cpdDate.substring(5,7)).concat(cpdDate.substring(8,10));
			String memberCode = getMapValueByString(vo, "memberCode");
			String merchantNos = getMapValueByString(vo, "merchantNo");
			String orgCode = getMapValueByString(vo, "orgCode");
			String cardOrg = getMapValueByString(vo, "cardOrg");
			String channelOrderId = getMapValueByString(vo, "channelOrderId");
			String tranCurrencyCode = getMapValueByString(vo, "tranCurrencyCode");
			String chargeBackAmount = getMapValueByAmount(vo, "chargeBackAmount");
			String visableCode = getMapValueByString(vo, "visableCode");
			// 使用拒付日期
			String cpdDateSub = cpdDates.substring(0, 6);


					BigDecimal rate=BigDecimal.ONE;
					String rateKey=tranCurrencyCode.concat("CNY");
					if(!"CNY".equals(tranCurrencyCode) && ratemap.containsKey(rateKey))
					{
						rate=ratemap.get(rateKey);
					}
					BigDecimal amount=BigDecimal.ZERO;
					if(null != rate)
					{
						 amount = new BigDecimal(chargeBackAmount).multiply(rate).setScale(0,
									BigDecimal.ROUND_HALF_UP);;
					}
					
					bouncedOrderVO1.setChannelOrderId(Long.valueOf(channelOrderId));
					bouncedOrderVO1.setMemberCode(memberCode);
					bouncedOrderVO1.setMerchantNo(merchantNos);
					
					bouncedOrderVO1.setCardOrg(cardOrg);
					bouncedOrderVO1.setTranCurrencyCode(tranCurrencyCode);
					bouncedOrderVO1.setChargeBackAmount(amount.toString());
					String key="";
					
					if("true".equals(orgcodeQuery))
					{
						if("10079001".equals(orgCode) ||"10080001".equals(orgCode))
						{
							orgCode="10080001";
						}
						if("10078001".equals(orgCode))
						{
							orgCode="10002001";
						}
						key = cpdDateSub.concat("|").concat(cardOrg).concat("|").concat(orgCode);
					}else{
						key = cpdDateSub.concat("|").concat(cardOrg).concat("|").concat(merchantNos);
					}
					bouncedOrderVO1.setOrgCode(orgCode);
					
					if (bouncedOrdersIPAY.containsKey(key)) {
						bouncedOrdersIPAY.get(key).setBouncedCount(
								bouncedOrdersIPAY.get(key).getBouncedCount().add(BigDecimal.ONE));
						
						// 欺诈金额计算
						if ("3".equals(visableCode) || "5".equals(visableCode)) {
							BigDecimal fraudamt=bouncedOrdersIPAY.get(key).getFraudAmount();
							if(null==fraudamt)
							{
								fraudamt=BigDecimal.ZERO;
							}
							bouncedOrdersIPAY.get(key).setFraudAmount(
									fraudamt.add(amount));
						}
					} else {
						bouncedOrderVO1.setBouncedCount(BigDecimal.ONE);
						// 欺诈金额计算
						if ("3".equals(visableCode) || "5".equals(visableCode)) {
							bouncedOrderVO1.setFraudAmount(amount);
						}
						bouncedOrdersIPAY.put(key, bouncedOrderVO1);
					}

		}
		}
		if(null!=BouncedFrauds && BouncedFrauds.size()>0)
		{
			for (Map map : BouncedFrauds) {

				if (null == map.get("createDate")) {
					continue;
				}
				if (null == map.get("merchantNo")) {
					continue;
				}
				if (null == map.get("cardOrg")) {
					continue;
				}else if("null".equals(map.get("cardOrg")) || (!"VISA".equals(map.get("cardOrg")) && !"MASTER".equals(map.get("cardOrg")))) {
					continue;
				}
				String createDate = getMapValueByString(map, "createDate");
				String yyyy1 = createDate.substring(0, 4);
				String mm1 = createDate.substring(4, 6);
				String dd1 = createDate.substring(6, 8);
				String createDateRate = yyyy1.concat("-").concat(mm1).concat("-").concat(dd1);

				String amount0 = getMapValueByAmount(map, "amount");
				String currencyCode = getMapValueByString(map, "currencyCode");

				String spartnerId = getMapValueByString(map, "partnerId");
				String merchantNos1 = getMapValueByString(map, "merchantNo");
				String orgCode1 = getMapValueByString(map, "orgCode");
				String cardOrg1 = getMapValueByString(map, "cardOrg");
				String createDateSub = createDate.substring(0, 6);
				String orgName="";

				BigDecimal rate=BigDecimal.ONE;
				String rateKey=currencyCode.concat("CNY");
				if(!"CNY".equals(currencyCode) && ratemap.containsKey(rateKey))
				{
					rate=ratemap.get(rateKey);
				}
				
				BigDecimal amount=BigDecimal.ZERO;
				if(null != rate)
				{
				amount = new BigDecimal(amount0).multiply(rate).setScale(0,
						BigDecimal.ROUND_HALF_UP);
				}

				BouncedFraudResultDTO bouncedFraudResultDTO = new BouncedFraudResultDTO();
				bouncedFraudResultDTO.setBouncedMulct(BigDecimal.ZERO);
				bouncedFraudResultDTO.setPartnerId(spartnerId);
				bouncedFraudResultDTO.setMerchantNo(merchantNos1);

				bouncedFraudResultDTO.setCardOrg(cardOrg1);
				
				if ("MASTER".equals(cardOrg1)) {
					createDateSub = gegMasterDate(createDateSub);
				}
				String key = "";
				
				if ("true".equals(orgcodeQuery)) {
					if ("10079001".equals(orgCode1)
							|| "10080001".equals(orgCode1)) {
						orgCode1 = "10080001";
						orgName="中银";
					}
					if ("10078001".equals(orgCode1)) {
						orgCode1 = "10002001";
						orgName="农业银行";
					}
					if ("10075001".equals(orgCode1)) {
						orgName="CREDORAX";
					}
					if ("10076001".equals(orgCode1)) {
						orgName="卡司";
					}
					bouncedFraudResultDTO.setOrgName(orgName);
					key = createDateSub.concat("|").concat(cardOrg1).concat("|").concat(orgCode1);
					dateset.add(createDateSub);
					merchantset.add(orgCode1);
				} else {
					dateset.add(createDateSub);
					merchantset.add(merchantNos1);
					key = createDateSub.concat("|").concat(cardOrg1)
							.concat("|").concat(merchantNos1);
				}
				
				String createDateSub1=createDateSub;
				if ("MASTER".equals(cardOrg1)) {
					createDateSub1=gegMasterDate(createDateSub);
				}
				
				bouncedFraudResultDTO.setCreateDate(createDateSub1);
				bouncedFraudResultDTO.setOrgCode(orgCode1);

				if (bouncedFraudsIPAY.containsKey(key)) {
					bouncedFraudsIPAY.get(key).setTotalCount(
							bouncedFraudsIPAY.get(key).getTotalCount().add(BigDecimal.ONE));
					bouncedFraudsIPAY.get(key).setThistotalCount(
							bouncedFraudsIPAY.get(key).getThistotalCount().add(BigDecimal.ONE));

					BigDecimal totalamt = bouncedFraudsIPAY.get(key).getTotalAmount();
					if (null == totalamt) {
						totalamt = BigDecimal.ZERO;
					}
					BigDecimal totalamt1 = bouncedFraudsIPAY.get(key).getThistotalAmount();
					if (null == totalamt1) {
						totalamt1 = BigDecimal.ZERO;
					}
					bouncedFraudsIPAY.get(key).setTotalAmount(totalamt.add(amount));
					bouncedFraudsIPAY.get(key).setThistotalAmount(totalamt1.add(amount));
				} else {
					bouncedFraudResultDTO.setTotalCount(BigDecimal.ONE);
					bouncedFraudResultDTO.setTotalAmount(amount);
					bouncedFraudResultDTO.setThistotalCount(BigDecimal.ONE);
					bouncedFraudResultDTO.setThistotalAmount(amount);
					bouncedFraudsIPAY.put(key, bouncedFraudResultDTO);
				}
			}
		}
	}
	private void buildFlagMapsForPartner( List<Map> BouncedFrauds,
			Map<String, BouncedFraudResultDTO> bouncedFraudsIPAY,
			Map<String, BouncedOrderVO> bouncedOrdersIPAY, List<Map> bouncedOrderVOs,
			Set<String> dateset,Map<String,String> partnermap,Map<String, BigDecimal> ratemap) {
		if(null!=bouncedOrderVOs && bouncedOrderVOs.size()>0)
		{	
			//拒付数据处理
			for (Map vo : bouncedOrderVOs) {
				BouncedOrderVO bouncedOrderVO1 = new BouncedOrderVO();
				if(null==vo.get("tradeDate"))
				{
					continue;
				}
				if(null==vo.get("memberCode"))
				{
					continue;
				}
				if(null==vo.get("cpdDate"))
				{
					continue;
				}
				if (null == vo.get("cardOrg")) {
					continue;
				}else if("null".equals(vo.get("cardOrg")) || (!"VISA".equals(vo.get("cardOrg")) && !"MASTER".equals(vo.get("cardOrg")))) {
					continue;
				}
				String tradeDates = DateUtil.getDateForLong(vo.get("tradeDate").toString(),DateUtil.PATTERN);
				String tradeDateRate = DateUtil.getDateForLong(vo.get("tradeDate").toString(),DateUtil.SIMPLE_DATE_FROMAT);
				String cpdDate=vo.get("cpdDate").toString();
				String cpdDates = cpdDate.substring(0,4).concat(cpdDate.substring(5,7)).concat(cpdDate.substring(8,10));
				String memberCode = getMapValueByString(vo, "memberCode");
				String orgCode = getMapValueByString(vo, "orgCode");
				String cardOrg = getMapValueByString(vo, "cardOrg");
				String channelOrderId = getMapValueByString(vo, "channelOrderId");
				String tranCurrencyCode = getMapValueByString(vo, "tranCurrencyCode");
				String chargeBackAmount = getMapValueByAmount(vo, "chargeBackAmount");
				String visableCode = getMapValueByString(vo, "visableCode");
				// 使用拒付日期
				String cpdDateSub = cpdDates.substring(0, 6);
				
				
				BigDecimal rate=BigDecimal.ONE;
				String rateKey=tranCurrencyCode.concat("CNY");
				if(!"CNY".equals(tranCurrencyCode) && ratemap.containsKey(rateKey))
				{
					rate=ratemap.get(rateKey);
				}
				BigDecimal amount=BigDecimal.ZERO;
				if(null != rate)
				{
					amount = new BigDecimal(chargeBackAmount).multiply(rate).setScale(0,
							BigDecimal.ROUND_HALF_UP);
				}
				
				bouncedOrderVO1.setChannelOrderId(Long.valueOf(channelOrderId));
				bouncedOrderVO1.setMemberCode(memberCode);
				
				bouncedOrderVO1.setCardOrg(cardOrg);
				bouncedOrderVO1.setTranCurrencyCode(tranCurrencyCode);
				bouncedOrderVO1.setChargeBackAmount(amount.toString());
				String key="";
				
				key = cpdDateSub.concat("|").concat(cardOrg).concat("|").concat(memberCode);
				bouncedOrderVO1.setOrgCode(orgCode);
				
				if (bouncedOrdersIPAY.containsKey(key)) {
					bouncedOrdersIPAY.get(key).setBouncedCount(
							bouncedOrdersIPAY.get(key).getBouncedCount().add(BigDecimal.ONE));
					
					// 欺诈金额计算
					if ("3".equals(visableCode) || "5".equals(visableCode)) {
						BigDecimal fraudamt=bouncedOrdersIPAY.get(key).getFraudAmount();
						if(null==fraudamt)
						{
							fraudamt=BigDecimal.ZERO;
						}
						bouncedOrdersIPAY.get(key).setFraudAmount(
								fraudamt.add(amount));
					}
				} else {
					bouncedOrderVO1.setBouncedCount(BigDecimal.ONE);
					// 欺诈金额计算
					if ("3".equals(visableCode) || "5".equals(visableCode)) {
						bouncedOrderVO1.setFraudAmount(amount);
					}
					bouncedOrdersIPAY.put(key, bouncedOrderVO1);
				}
				
			}
		}
		if(null!=BouncedFrauds && BouncedFrauds.size()>0)
		{
			for (Map map : BouncedFrauds) {
				
				if (null == map.get("createDate")) {
					continue;
				}
				if (null == map.get("partnerId")) {
					continue;
				}
				if (null == map.get("cardOrg")) {
					continue;
				}else if("null".equals(map.get("cardOrg")) || (!"VISA".equals(map.get("cardOrg")) && !"MASTER".equals(map.get("cardOrg")))) {
					continue;
				}
				String createDate = getMapValueByString(map, "createDate");
				String yyyy1 = createDate.substring(0, 4);
				String mm1 = createDate.substring(4, 6);
				String dd1 = createDate.substring(6, 8);
				String createDateRate = yyyy1.concat("-").concat(mm1).concat("-").concat(dd1);
				
				String amount0 = getMapValueByAmount(map, "amount");
				String currencyCode = getMapValueByString(map, "currencyCode");
				
				String spartnerId = getMapValueByString(map, "partnerId");
				String partnerName = getMapValueByString(map, "partnerName");
				//String merchantNos1 = getMapValueByString(map, "merchantNo");
				String orgCode1 = getMapValueByString(map, "orgCode");
				String cardOrg1 = getMapValueByString(map, "cardOrg");
				String createDateSub = createDate.substring(0, 6);
				
				BigDecimal rate=BigDecimal.ONE;
				String rateKey=currencyCode.concat("CNY");
				
				if(!"CNY".equals(currencyCode) && ratemap.containsKey(rateKey))
				{
					rate=ratemap.get(rateKey);
				}
				
				BigDecimal amount=BigDecimal.ZERO;
				if(null != rate)
				{
					amount = new BigDecimal(amount0).multiply(rate).setScale(0,
							BigDecimal.ROUND_HALF_UP);
				}
				
				BouncedFraudResultDTO bouncedFraudResultDTO = new BouncedFraudResultDTO();
				bouncedFraudResultDTO.setBouncedMulct(BigDecimal.ZERO);
				bouncedFraudResultDTO.setPartnerId(spartnerId);
				bouncedFraudResultDTO.setPartnerName(partnerName);
				//bouncedFraudResultDTO.setMerchantNo(merchantNos1);
				
				bouncedFraudResultDTO.setCardOrg(cardOrg1);
				
				if ("MASTER".equals(cardOrg1)) {
					createDateSub = gegMasterDate(createDateSub);
				}
				String key = "";
					dateset.add(createDateSub);
					partnermap.put(spartnerId,partnerName);
					key = createDateSub.concat("|").concat(cardOrg1)
							.concat("|").concat(spartnerId);
				
				String createDateSub1=createDateSub;
				if ("MASTER".equals(cardOrg1)) {
					createDateSub1=gegMasterDate(createDateSub);
				}
				
				bouncedFraudResultDTO.setCreateDate(createDateSub1);
				bouncedFraudResultDTO.setOrgCode(orgCode1);
				
				if (bouncedFraudsIPAY.containsKey(key)) {
					bouncedFraudsIPAY.get(key).setTotalCount(
							bouncedFraudsIPAY.get(key).getTotalCount().add(BigDecimal.ONE));
					bouncedFraudsIPAY.get(key).setThistotalCount(
							bouncedFraudsIPAY.get(key).getThistotalCount().add(BigDecimal.ONE));
					
					BigDecimal totalamt = bouncedFraudsIPAY.get(key).getTotalAmount();
					if (null == totalamt) {
						totalamt = BigDecimal.ZERO;
					}
					BigDecimal totalamt1 = bouncedFraudsIPAY.get(key).getThistotalAmount();
					if (null == totalamt1) {
						totalamt1 = BigDecimal.ZERO;
					}
					bouncedFraudsIPAY.get(key).setTotalAmount(totalamt.add(amount));
					bouncedFraudsIPAY.get(key).setThistotalAmount(totalamt1.add(amount));
				} else {
					bouncedFraudResultDTO.setTotalCount(BigDecimal.ONE);
					bouncedFraudResultDTO.setTotalAmount(amount);
					bouncedFraudResultDTO.setThistotalCount(BigDecimal.ONE);
					bouncedFraudResultDTO.setThistotalAmount(amount);
					bouncedFraudsIPAY.put(key, bouncedFraudResultDTO);
				}
			}
		}
	}

	private String gegMasterDate(String createDateSub) {
		String MASTERDate = "";
		String yyyy = createDateSub.substring(0, 4);
		String mm = createDateSub.substring(4, 6);
		if (mm.equals("12")) {
			MASTERDate = (Integer.valueOf(yyyy) + 1) + "01";
		} else {
			if (Integer.valueOf(mm) < 9) {
				MASTERDate = yyyy + "0" + (Integer.valueOf(mm) + 1);
			} else {
				MASTERDate = yyyy + (Integer.valueOf(mm) + 1);
			}
		}
		return MASTERDate;
	}

	


	/**
	 * 拒付单项数据登记查询
	 * 
	 * @param paraMap
	 * @return
	 */
	public List<Map> bouncedFraudQuery(Map<String, String> paraMap) {

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(SerCode.BOUNCED_FRAUD_CHANNEL_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.POSS.getCode(), SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(), param.getMsgCompress(),
				param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		List<Map> listMap = (List<Map>) resultMap.get("bouncedResults");

		return listMap;
	}
	/**
	 * 取对应日期交易汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @param screateDate
	 * @param isIgnoreMarkup
	 * @return 2016年6月13日 mmzhang add
	 */
	private BigDecimal getTrantionRate(String sourceCurrency, String targetCurrency,
			String screateDate, String isIgnoreMarkup) {
		BigDecimal rate = BigDecimal.ONE;

		if (!sourceCurrency.equals(targetCurrency)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", sourceCurrency);
			param.put("targetCurrency", targetCurrency);
			// status为1是当天的汇率，置为有效，取某一天的这字段不传，传交易日期
			param.put("ltaCurrencyCode", "USD");
			param.put("currentDate", screateDate);
			param.put("isIgnoreMarkup", isIgnoreMarkup);// add by Mack to
														// control ignore markup

			Map<String, Object> map = bouncedQueryService.transactionRateBounced(param);
			Map transactionRate = (Map) map.get("transactionRate");
			if (null == transactionRate) {
				logger.info("未找到对应交易汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: " + sourceCurrency
						+ " ,targetCurrencyCode: " + targetCurrency);
				rate = null;

			} else {
				rate = new BigDecimal(transactionRate.get("exchangeRate").toString());
				logger.info("找到对应交易汇率为rate=" + rate + ".....sourceCurrencyCode: "
						+ sourceCurrency + " ,targetCurrencyCode: " + targetCurrency);
			}
		}
		return rate;
	}
	private BigDecimal getTrantionRateStatus(String sourceCurrency, String targetCurrency,
			String screateDate, String isIgnoreMarkup) {
		BigDecimal rate = BigDecimal.ONE;
		
		if (!sourceCurrency.equals(targetCurrency)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", sourceCurrency);
			param.put("targetCurrency", targetCurrency);
			// status为1是当天的汇率，置为有效，取某一天的这字段不传，传交易日期
			param.put("ltaCurrencyCode", "USD");
			param.put("status", 1);
			param.put("isIgnoreMarkup", isIgnoreMarkup);// add by Mack to
			// control ignore markup
			
			Map<String, Object> map = bouncedQueryService.transactionRateBounced(param);
			Map transactionRate = (Map) map.get("transactionRate");
			if (null == transactionRate) {
				logger.info("未找到对应交易汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: " + sourceCurrency
						+ " ,targetCurrencyCode: " + targetCurrency);
				rate = null;
				
			} else {
				rate = new BigDecimal(transactionRate.get("exchangeRate").toString());
				logger.info("找到对应交易汇率为rate=" + rate + ".....sourceCurrencyCode: "
						+ sourceCurrency + " ,targetCurrencyCode: " + targetCurrency);
			}
		}
		return rate;
	}

	private String getMapValueByAmount(Map vo, String name) {
		String doingRefundAmount = "0";
		if (vo.get(name) != null && !"".equals(vo.get(name))) {
			doingRefundAmount = vo.get(name).toString();
		}
		return doingRefundAmount;
	}

	private String getMapValueByString(Map vo, String name) {
		String doingRefundAmount = "";
		if (vo.get(name) != null && !"".equals(vo.get(name))) {
			doingRefundAmount = vo.get(name).toString();
		}
		return doingRefundAmount;
	}

	public void setBouncedQueryService(BouncedQueryService bouncedQueryService) {
		this.bouncedQueryService = bouncedQueryService;
	}

	public void setChargeBackService(ChargeBackService chargeBackService) {
		this.chargeBackService = chargeBackService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}



}
