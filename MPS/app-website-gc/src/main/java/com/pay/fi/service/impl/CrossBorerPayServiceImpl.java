package com.pay.fi.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pay.acct.crossBorderPay.model.KfFeeConfig;
import com.pay.acct.crossBorderPay.service.KfFeeConfigService;
import com.pay.app.client.ChannelClientService;
import com.pay.app.client.SettleCoreClientService;
import com.pay.app.crossBorderPay.verify.TemplateVerifyEnum;
import com.pay.app.validator.CrossBorerPayValidator;
import com.pay.execlUtil.PoiTemplateUtil;
import com.pay.fi.dto.KfPayResource;
import com.pay.fi.dto.KfPayTrade;
import com.pay.fi.dto.KfPayTradeDetail;
import com.pay.fi.service.CrossBorerPayService;
import com.pay.fileserver.tokenlib.MyOSS;
import com.pay.fileserver.tokenlib.MyOSSException;

public class CrossBorerPayServiceImpl implements CrossBorerPayService{
	@Autowired
	@Qualifier(value="fi-crossBorerPayValidator")
	private CrossBorerPayValidator crossBorerPayValidator;
	@Autowired
	@Qualifier(value="kfFeeConfigService")
	private KfFeeConfigService kfFeeConfigServiceImpl;
	
	@Autowired
	@Qualifier(value="channelClientService")
	private ChannelClientService channelClientService;
	
	@Autowired
	@Qualifier(value = "settleCoreClientService")
	private SettleCoreClientService settleCoreClientService;

	@Autowired
	@Qualifier(value = "ossUtils")
	private MyOSS myoss;
	
	private KfFeeConfig getSettle(Long partnerId){
		Map<String, String> paraMap=new HashMap<String, String>();
		paraMap.put("partnerId", String.valueOf(partnerId));
		KfFeeConfig kfFeeConfig=kfFeeConfigServiceImpl.findKfFeeConfig(paraMap);
		if (kfFeeConfig==null) {
			paraMap.put("partnerId", "0");
			kfFeeConfig=kfFeeConfigServiceImpl.findKfFeeConfig(paraMap);
		}
		return kfFeeConfig;
	}
	private Map<String, Object> getParities(Long partnerId,String currencyCode,String targetCurrencyCode){
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("orderNo",System.currentTimeMillis()+new Random().nextInt(1000)+"");
		paraMap.put("partnerId",String.valueOf(partnerId));
		paraMap.put("targetCurrencyCode",targetCurrencyCode);
		paraMap.put("currencyCode",currencyCode);
		paraMap.put("remark","");
		Map<String, Object> result=channelClientService.queryHnaRate(paraMap);
		return result;
	}
	@Override
	public int fileVerify(ArrayList<ArrayList<String>> verify,String type) {
		int repCode=-1;
		if(type.equals(TemplateVerifyEnum.CARGO_TRADE_TEMPLATE.getTemplateType())){
			if(TemplateVerifyEnum.CARGO_TRADE_TEMPLATE.getVerifyField().equals(verify.get(0).toString())){
				repCode=0;
			}
		}else if(type.equals(TemplateVerifyEnum.TICKET_TRAVEL_TEMPLATE.getTemplateType())){
			if(TemplateVerifyEnum.TICKET_TRAVEL_TEMPLATE.getVerifyField().equals(verify.get(0).toString())){
				repCode=0;
			}
		}else if(type.equals(TemplateVerifyEnum.GROGSHOP_CEASE_TEMPLATE.getTemplateType())){
			if(TemplateVerifyEnum.GROGSHOP_CEASE_TEMPLATE.getVerifyField().equals(verify.get(0).toString())){
				repCode=0;
			}
		}else if(type.equals(TemplateVerifyEnum.STUDY_ABROAD_TEMPLATE.getTemplateType())){
			if(TemplateVerifyEnum.STUDY_ABROAD_TEMPLATE.getVerifyField().equals(verify.get(0).toString())){
				repCode=0;
			}
		}
		return repCode;
	}
	
	@Override
	public Map<String, Object> fileAnalysis(ArrayList<ArrayList<String>> verify,InputStream successtemplate,KfPayTrade kfPayTrade) throws IOException, UnsupportedOperationException, MyOSSException {
		Map<String, Object> result=new HashMap<String, Object>();
		if(kfPayTrade.getType().equals(TemplateVerifyEnum.CARGO_TRADE_TEMPLATE.getTemplateType())){
			Map<String, Object> verifyMap=verifyDetail(verify,kfPayTrade, 13,14,4,11);
			analysisResult(verify, successtemplate, result, verifyMap);
		}else if(kfPayTrade.getType().equals(TemplateVerifyEnum.TICKET_TRAVEL_TEMPLATE.getTemplateType())){
			Map<String, Object> verifyMap=verifyDetail(verify,kfPayTrade, 14,15,5,12);
			analysisResult(verify, successtemplate, result, verifyMap);
		}else if(kfPayTrade.getType().equals(TemplateVerifyEnum.GROGSHOP_CEASE_TEMPLATE.getTemplateType())){
			Map<String, Object> verifyMap=verifyDetail(verify,kfPayTrade, 13,14,4,11);
			analysisResult(verify, successtemplate, result, verifyMap);
		}else if(kfPayTrade.getType().equals(TemplateVerifyEnum.STUDY_ABROAD_TEMPLATE.getTemplateType())){
			Map<String, Object> verifyMap=verifyDetail(verify,kfPayTrade, 12,13,3,10);
			analysisResult(verify, successtemplate, result, verifyMap);
		}
		return result;
	}
	/**
	 * 文件返回结果集
	 * @param verify
	 * @param successtemplate
	 * @param result
	 * @param verifyMap
	 * @throws IOException
	 */
	private void analysisResult(ArrayList<ArrayList<String>> verify, InputStream successtemplate,
			Map<String, Object> result, Map<String, Object> verifyMap) throws IOException {
		result.put("analysisResult", verifyMap.get("analysisResult"));
		result.put("batchNo", verifyMap.get("batchNo"));
		PoiTemplateUtil poiTemplateUtil=new PoiTemplateUtil();
		for (int i = 0; i < verify.size(); i++) {
			verify.get(i).add("待出款");
			verify.get(i).add(" ");
		}
		result.put("successFile", poiTemplateUtil.createExeclResultInputStream(successtemplate,verifyMap.get("batchNo").toString(), verify));
		result.put("payAmount",verifyMap.get("payAmount"));
		result.put("saveTradeOrderDetailList",verifyMap.get("saveTradeOrderDetailList"));
	}
	/**
	 * 验证文件，获取币种，金额
	 * @param verify
	 * @param partnerId
	 * @param indexs
	 * @return
	 * @throws MyOSSException 
	 * @throws IOException 
	 * @throws UnsupportedOperationException 
	 */
	private Map<String, Object> verifyDetail(ArrayList<ArrayList<String>> verify,KfPayTrade kfPayTrade,int...indexs) throws UnsupportedOperationException, IOException, MyOSSException {
		List<Integer> list=new ArrayList<Integer>(); 
		Map<String, Object> result=new HashMap<String, Object>();
		Set<String> currencyCode=new HashSet<String>();
		Map<String, List<String>> payDetail=new HashMap<String,List<String>>();
		Set<String> orderId=new HashSet<String>();
		Set<Integer> verifyErrorIndex=new TreeSet<Integer>();
		int count=verify.size();
		for (int i=0;i<verify.size();i++) {
			ArrayList<String> item=verify.get(i);
			//验证金额和币种是否为空
			if(crossBorerPayValidator.verifyMoneyAndCurrencyIsNull(item,indexs)){
				verifyErrorIndex.add(i);
			//验证币种是否合法
			}else if(crossBorerPayValidator.verifyCurrencyLegal(item.get(indexs[0]),kfPayTrade.getRemitCurrency())){
				verifyErrorIndex.add(i);
			}else{
				List<String> set=new ArrayList<String>();
				set.add(0,item.get(0));//商户订单号
				set.add(1,item.get(indexs[0]));//汇款币种
				set.add(2,item.get(indexs[1]));//汇款金额
				set.add(3,item.get(indexs[2]));//付款人
				set.add(4,item.get(indexs[3]));//收款人
				if(crossBorerPayValidator.verifyRepeatTrade(payDetail,item.get(0))){
					orderId.add(item.get(0));
					payDetail.remove(item.get(0));
					verifyErrorIndex.add(i);
				}
				payDetail.put(item.get(0), set);
				currencyCode.add(item.get(indexs[0]));
			}
		}
		int i=0;
		//去除验证失败数据
		for(Integer key:verifyErrorIndex){
			verify.remove(key.intValue()-i);
			i++;
		}
		//去除重复数据
		for (String key : orderId) {
			for (int i1=0;i1<verify.size();i1++) {
				ArrayList<String> item=verify.get(i1);
				if(item.get(0).equals(key)){
					verify.remove(item);
				}
			}
			payDetail.remove(key);
		}
		//导入总笔数
		list.add(0, verify.size());
		//成功总笔数
		list.add(1, payDetail.size());
		//失败总笔数
		list.add(2, count-payDetail.size());
		result.put("payAmount", calculateMoney(currencyCode,payDetail,kfPayTrade));
		String batchNo=saveTradeOrder(list,currencyCode,kfPayTrade);
		result.put("batchNo",batchNo);
		kfPayTrade.setBatchNo(batchNo);
		result.put("saveTradeOrderDetailList", saveTradeOrderDetail(payDetail,kfPayTrade));
		result.put("analysisResult", list);
		return result;
	}
	/**
	 * 计算金额
	 * @param currencyCode
	 * @param payDetail
	 * @param kfPayTrade
	 * @return
	 */
	private long calculateMoney(Set<String> currencyCode,Map<String, List<String>> payDetail,KfPayTrade kfPayTrade){
		Map<String, Map<String, Object>> paritiesMap=new HashMap<String, Map<String,Object>>();
		for (String item : currencyCode) {
			Map<String, Object> parities=getParities(kfPayTrade.getPartnerId(),item,"CNY");
			paritiesMap.put(item,parities);
		}
		BigDecimal PayAmount=new BigDecimal("0");
		BigDecimal feeAmount=new BigDecimal("0");
		for (String key : payDetail.keySet()) {
			List<String> PayDetailList=payDetail.get(key);
			String rate=paritiesMap.get(PayDetailList.get(1)).get("sellPrice").toString();
			BigDecimal bigDecimal=new BigDecimal(rate);
			bigDecimal=bigDecimal.multiply(new BigDecimal(PayDetailList.get(2).toString()));
			BigDecimal orderAmount=new BigDecimal(bigDecimal.toString());
			BigDecimal[] feeResult=calculateFee(bigDecimal,kfPayTrade.getPartnerId());
			BigDecimal fee=feeResult[0];
			BigDecimal smallServiceFee=feeResult[1];
			BigDecimal remitExpense=fee.add(smallServiceFee);
			feeAmount=feeAmount.add(remitExpense).setScale(2, BigDecimal.ROUND_HALF_UP);
			PayAmount=PayAmount.add(orderAmount).setScale(2, BigDecimal.ROUND_HALF_UP);
			PayDetailList.add(5,bigDecimal.toString());//付款金额
			PayDetailList.add(6,remitExpense.toString());//汇款费用
			PayDetailList.add(7,rate);//汇率
			PayDetailList.add(8,smallServiceFee.toString());//小额服务费
			PayDetailList.add(9,fee.toString());//手续费
			PayDetailList.add(10,orderAmount.toString());//订单金额
			
			payDetail.put(key, PayDetailList);
		}
		long PayAmountLong=(feeAmount.add(PayAmount)).multiply(BigDecimal.valueOf(1000)).longValue();
		kfPayTrade.setPayAmount(PayAmountLong);
		kfPayTrade.setRemitAmount((feeAmount).multiply(BigDecimal.valueOf(1000)).longValue());
		return PayAmountLong;
	}
	/**
	 * 计算手续费
	 * @param PayAmount
	 * @param partnerId
	 * @return
	 */
	private BigDecimal[] calculateFee(BigDecimal PayAmount,long partnerId){
		BigDecimal[] result=new BigDecimal[2];
		BigDecimal feeAccount=new BigDecimal("0");
		BigDecimal smallAccount=new BigDecimal("0");
		BigDecimal baseFeeAccount=new BigDecimal(PayAmount.toString());
		KfFeeConfig kfFeeConfig=getSettle(partnerId);
		BigDecimal smallService=new BigDecimal("0");
		BigDecimal smallBaselin=BigDecimal.valueOf(kfFeeConfig.getSmallBaselin()).divide(BigDecimal.valueOf(1000));
		BigDecimal smallServiceFee=BigDecimal.valueOf(kfFeeConfig.getSmallServiceFee()).divide(BigDecimal.valueOf(1000));
		BigDecimal percentageFee=BigDecimal.valueOf(kfFeeConfig.getPercentageFee()).divide(BigDecimal.valueOf(10000));
		BigDecimal fixedFee=BigDecimal.valueOf(kfFeeConfig.getFixedFee()).divide(BigDecimal.valueOf(1000));
		BigDecimal minimumValue=BigDecimal.valueOf(kfFeeConfig.getMinimumValue()).divide(BigDecimal.valueOf(1000));
		BigDecimal capValue=BigDecimal.valueOf(kfFeeConfig.getCapValue()).divide(BigDecimal.valueOf(1000));
		if(baseFeeAccount.compareTo(smallBaselin)==-1){
			smallService=smallService.add(smallServiceFee);
		}
		baseFeeAccount=baseFeeAccount.multiply(percentageFee);
		baseFeeAccount=baseFeeAccount.add(fixedFee);
		if (baseFeeAccount.compareTo(minimumValue)==-1) {
			baseFeeAccount=minimumValue;
		}else if(baseFeeAccount.compareTo(capValue)!=-1){
			baseFeeAccount=capValue;
		}
		smallAccount=smallAccount.add(smallService);
		feeAccount=feeAccount.add(baseFeeAccount);
		result[0]=feeAccount;
		result[1]=smallAccount;
		return result;
	}
	
	/**
	 * 保存订单
	 * @param payDetail
	 * @param list
	 * @param currencyCode
	 * @param kfPayTrade
	 * @return
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 * @throws MyOSSException
	 */
	private String saveTradeOrder(List<Integer> list,Set<String> currencyCode,KfPayTrade kfPayTrade) throws UnsupportedOperationException, IOException, MyOSSException{
		kfPayTrade.setAllCount(list.get(0));
		kfPayTrade.setSuccessCount(list.get(1));
		kfPayTrade.setFailCount(list.get(2));
		kfPayTrade.setPayCount(list.get(1));
		kfPayTrade.setStatus(-1);
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("entity", kfPayTrade);
		Map<String, Object> rpcResult=settleCoreClientService.kfPayTrandSave(paraMap);
		String batchNo=rpcResult.get("batchNo").toString();
		return batchNo;
	}
	/**
	 * 保存订单明细
	 * @param payDetail
	 * @param kfPayTrade
	 */
	private List<KfPayTradeDetail> saveTradeOrderDetail(Map<String,List<String>> payDetail,KfPayTrade kfPayTrade){
		Map<String, Object> paraMap=new HashMap<String, Object>();
		List<KfPayTradeDetail> KfPayTradeDetailList=new ArrayList<KfPayTradeDetail>();
		for (String item : payDetail.keySet()) {
			KfPayTradeDetail kfPayTradeDetail=new KfPayTradeDetail();
			kfPayTradeDetail.setBatchNo(kfPayTrade.getBatchNo());
			kfPayTradeDetail.setPartnerId(kfPayTrade.getPartnerId());
			kfPayTradeDetail.setOperator(kfPayTrade.getOperator());
			kfPayTradeDetail.setType(kfPayTrade.getType());
			kfPayTradeDetail.setOrderId(payDetail.get(item).get(0));
			BigDecimal remitAmount=new BigDecimal(payDetail.get(item).get(2));
			remitAmount=remitAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			kfPayTradeDetail.setRemitAmount(remitAmount.multiply(BigDecimal.valueOf(1000)).longValue());
			kfPayTradeDetail.setRemitCurrencyCode(payDetail.get(item).get(1));
			kfPayTradeDetail.setDrawee(payDetail.get(item).get(3));
			kfPayTradeDetail.setCheques(payDetail.get(item).get(4));
			kfPayTradeDetail.setOutStatus("-1");
			kfPayTradeDetail.setRate(payDetail.get(item).get(7));
			BigDecimal remitExpense=new BigDecimal(payDetail.get(item).get(6));
			remitExpense=remitExpense.setScale(2, BigDecimal.ROUND_HALF_UP);
			kfPayTradeDetail.setRemitExpense(remitExpense.multiply(BigDecimal.valueOf(1000)).longValue());
			BigDecimal payAmount=new BigDecimal(payDetail.get(item).get(5));
			payAmount=payAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
			kfPayTradeDetail.setPayAmount(payAmount.multiply(BigDecimal.valueOf(1000)).longValue());
			BigDecimal fee=new BigDecimal(payDetail.get(item).get(9)).setScale(2, BigDecimal.ROUND_HALF_UP);
			kfPayTradeDetail.setFee(fee.multiply(BigDecimal.valueOf(1000)).longValue());
			BigDecimal smallServiceFee=new BigDecimal(payDetail.get(item).get(8)).setScale(2, BigDecimal.ROUND_HALF_UP);
			kfPayTradeDetail.setSmallServiceFee(smallServiceFee.multiply(BigDecimal.valueOf(1000)).longValue());
			BigDecimal orderAmount=new BigDecimal(payDetail.get(item).get(10)).setScale(2, BigDecimal.ROUND_HALF_UP);
			kfPayTradeDetail.setOrderAmount(orderAmount.multiply(BigDecimal.valueOf(1000)).longValue());
			KfPayTradeDetailList.add(kfPayTradeDetail);
			
		}
		paraMap.put("entity", KfPayTradeDetailList);
		settleCoreClientService.kfPayTrandDetailSave(paraMap);
		return KfPayTradeDetailList;
	}
	/**
	 * 保存付款文件
	 * @param batchNo
	 * @param filePath
	 * @param fullPath
	 * @param typeName
	 */
	public void savePayFileByKfResource(String batchNo,String filePath,String fullPath,String typeName,String fileName){
		KfPayResource kfPayResource=new KfPayResource();
		kfPayResource.setBatchNo(batchNo);
		kfPayResource.setFileName(fileName);
		kfPayResource.setFileType(2);
		kfPayResource.setUrl(fullPath+filePath);
		kfPayResource.setFilePath(filePath);
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("entity", kfPayResource);
		settleCoreClientService.kfPayResourceSave(paraMap);
	}
	/**
	 * 保存明细文件
	 * @param batchNo
	 * @param filePath
	 * @param fullPath
	 * @param typeName
	 */
	public void savePaySuccessFileByKfResource(String batchNo,String filePath,String fullPath,String typeName){
		KfPayResource kfPayResource=new KfPayResource();
		kfPayResource.setBatchNo(batchNo);
		kfPayResource.setFileName(batchNo+typeName+"付款明细文件.xls");
		kfPayResource.setFileType(3);
		kfPayResource.setUrl(fullPath+filePath);
		kfPayResource.setFilePath(filePath);
		Map<String, Object> paraMap=new HashMap<String, Object>();
		paraMap.put("entity", kfPayResource);
		settleCoreClientService.kfPayResourceSave(paraMap);
	}
}
