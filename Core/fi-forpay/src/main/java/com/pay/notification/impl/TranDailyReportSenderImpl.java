/**
 * 
 */
package com.pay.notification.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.forpay.client.SettleClientService;
import com.pay.jms.notification.request.NotifyRequest;
import com.pay.notification.ExceptionCardSender;
import com.pay.trandailyreport.ReconciliationDto;
import com.pay.trandailyreport.TranDailyReportVo;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * @author Jiangbo.peng
 * 
 * 交易日报表需要对账后更新的字段
 * bankPerFee：银行比例手续费
 * bankPerCurrencyCode：银行比例手续费币种
 * bankFixedFee：银行固定手续费
 * bankFixedCurrencyCode：银行固定手续费币种	
 * bankAmount：银行存款入账
 * bankCurrencyCode：银行存款入账币种
 * bankPayAmountCny：渠道支付金额CNY->
 *
 */
@SuppressWarnings({"unchecked"})
public class TranDailyReportSenderImpl implements ExceptionCardSender {

	private static final Log logger = LogFactory.getLog(TranDailyReportSenderImpl.class) ;
	private SettleClientService settleClientService;

	@Override
	public boolean process(NotifyRequest notifyRequest) {
		try {
			logger.info("交易日报表数据更新开始...........................");
			Integer updateCount = 0 ;
			Map<String, String> data = (Map<String, String>) notifyRequest.getData() ;
			String listStr = data.get("list") ;
			//存在已正确对账数据
			if(StringUtils.isNotEmpty(listStr)){
				logger.info("reportsReconcilianList:" + listStr);
				List<Map> reconciliationDtoList = (List<Map>) JSonUtil.toObject(listStr, new ArrayList<Map>().getClass()) ;
				if(CollectionUtils.isNotEmpty(reconciliationDtoList)){
					logger.info("接收到的对账单数据reconciliationDtoList总共【" + reconciliationDtoList.size() + "】条");
					for(Map map : reconciliationDtoList){
						ReconciliationDto reconciliationDto = MapUtil.map2Object(ReconciliationDto.class, map) ;
						//检查是否正确对账
						Integer reconciliationFlg = reconciliationDto.getReconciliationFlg() ;
						//正确对账
						
						if(1 == reconciliationFlg){
							Map<String, Object> paraMap = new HashMap<String, Object>() ;
							//对账参数
							paraMap.put("reconciliationFlg", reconciliationFlg) ;
							//数据有效性- >有效
							paraMap.put("flag", reconciliationFlg) ;
							//渠道编号
							String channelCode = reconciliationDto.getChannelCode() ;
							//渠道订单号
							String channelOrderNo = reconciliationDto.getChannelOrderNo() ;
							logger.info("开始更新交易日报表数据，渠道订单号：" + channelOrderNo + "渠道编号：" + channelCode);
							paraMap.put("channelOrderNo", channelOrderNo) ;	
							String bankCurrencyCode = reconciliationDto.getSettlementCurrencyCode() ;
							paraMap.put("bankCurrencyCode", bankCurrencyCode) ;
							//银行存款入账，取对账上的结算金额
							BigDecimal bankAmount = new BigDecimal(reconciliationDto.getSettAmount()).multiply(new BigDecimal(1000)) ;
							paraMap.put("bankAmount", bankAmount) ;
							//credorax对账
							if(ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(channelCode)){
								//更新交易日报表数据
								String perFee = reconciliationDto.getPerFee() ;
								String settlementCurrencyCode = reconciliationDto.getSettlementCurrencyCode() ;
								String fixedFee = reconciliationDto.getFixedFee() ;
								//String settlementAmount = reconciliationDto.getSettlementAmount() ;
								//对账单上金额都是以元为单位，数据库转成厘存储
								BigDecimal bankPerFee = new BigDecimal(perFee).multiply(new BigDecimal(1000)) ;
								String bankPerCurrencyCode = settlementCurrencyCode ;
								BigDecimal bankFixedFee = new BigDecimal(fixedFee).multiply(new BigDecimal(1000)) ;
								String bankFixedCurrencyCode = settlementCurrencyCode ;
								
								paraMap.put("bankPerFee", bankPerFee) ;
								paraMap.put("bankPerCurrencyCode", bankPerCurrencyCode) ;
								paraMap.put("bankFixedFee", bankFixedFee) ;
								paraMap.put("bankFixedCurrencyCode", bankFixedCurrencyCode) ;
								
							}
							//ctv
							if(ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(channelCode)){
								//更新交易日报表数据
								
							}
							//农行
							if(ChannelItemOrgCodeEnum.ABC.getCode().equals(channelCode) || ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(channelCode)){
								//渠道支付金额CNY， 若走农行，取对账单上的交易金额（人民币）那一列
								BigDecimal bankPayAmountCny = new BigDecimal(reconciliationDto.getSettlementAmount()).multiply(new BigDecimal(1000)) ; 
								paraMap.put("bankPayAmountCny", bankPayAmountCny) ;
								//对账单上的交易金额（人民币）
								BigDecimal rc_settlementAmount = new BigDecimal(reconciliationDto.getSettlementAmount()).multiply(new BigDecimal(1000)) ;
								//对账单上的结算净金额
								BigDecimal rc_settAmount = new BigDecimal(reconciliationDto.getSettAmount()).multiply(new BigDecimal(1000)) ;
								//走农行时，渠道支付金额修改之后，
								//汇差、汇差收益、总收入、毛利、毛利率受到影响需要重新计算
								//获取部分交易日报表原始插入数据
								Map queryMap = new HashMap<String, Object>() ;
								queryMap.put("channelOrderNo", channelOrderNo) ;
								queryMap.put("falg", reconciliationFlg) ;
								Map<String, Object> resultMap = this.settleClientService.queryTranDailyReport(queryMap) ;
								if(null != resultMap && "0000".equals(resultMap.get("responseCode"))){
									List<Map> listMap =(List<Map>) resultMap.get("result") ;
									List<TranDailyReportVo> lists =  MapUtil.map2List(TranDailyReportVo.class, listMap) ;
									if(CollectionUtils.isNotEmpty(lists)){
										TranDailyReportVo tranDailyReportVo = lists.get(0) ;
										//原始插入交易日报表数据的金额，都是以厘为单位的，此处无需转厘
										BigDecimal settlementAmountCny = tranDailyReportVo.getSettlementAmountCny() ;
										BigDecimal perFeeCny = tranDailyReportVo.getPerFeeCny() ;
										BigDecimal fixedFeeCny = tranDailyReportVo.getFixedFeeCny() ;
									    BigDecimal bankFixedFeeCny = tranDailyReportVo.getBankFixedFeeCny() ;
									    BigDecimal settlementAmount = tranDailyReportVo.getSettlementAmount() ;
									    //BigDecimal bankPerFeeCny = tranDailyReportVo.getBankPerFeeCny() ;
									    //对账后，农行的银行比例手续费要重新计算，不能直接从原始日报表中数据获取
									    //银行比例手续费CNY， 取对账单上的交易金额 - 对账上的清算金额
									    //BigDecimal bankPerFeeCny = rc_settlementAmount.subtract(settlementAmount) ;
									    BigDecimal bankPerFeeCny = rc_settlementAmount.subtract(rc_settAmount) ;
										//计算汇差=>渠道支付金额CNY-清算金额CNY（银行还没收手续费的值-我们还没收商户手续费的值）
										BigDecimal rateIncome = bankPayAmountCny.subtract(settlementAmountCny) ;
										//汇差收益率
										BigDecimal rateRate = rateIncome.divide(bankPayAmountCny, 7, BigDecimal.ROUND_HALF_UP) ;
										//总收入
										BigDecimal totalIncome = perFeeCny.add(fixedFeeCny).add(rateIncome) ;
										//毛利
										BigDecimal profit = totalIncome.subtract(bankFixedFeeCny).subtract(bankPerFeeCny) ;
										//毛利率
										BigDecimal profitRate = profit.divide(bankPayAmountCny, 7, BigDecimal.ROUND_HALF_UP) ;
										
										paraMap.put("rateIncome", rateIncome) ;
										paraMap.put("rateRate", rateRate) ;
										paraMap.put("totalIncome", totalIncome) ;
										paraMap.put("profit", profit) ;
										paraMap.put("profitRate", profitRate) ;
										paraMap.put("bankPerFeeCny", bankPerFeeCny) ;
										
									}
								}else{
									logger.info("异步更新农行对账单数据时，查询原始插入的交易日报表数据出错，无法更新该条数据，渠道订单号为：" + channelOrderNo);
								}
								
							}
							Map<String, Object> resultMap = this.settleClientService.updateTranDailyReport(paraMap) ;
							String responseCode = (String) resultMap.get("responseCode") ;
							if("0000".equals(responseCode)){
								updateCount ++ ;
								logger.info("本次总共成功更新交易日报表数据【"+ updateCount + "】条！");
							}
						}
					}
				}
			}else{
				logger.info("本次接收到的对账单数据0条！");
			}
			logger.info("交易日报表数据更新结束...........................");
			return true; 
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}

	/**
	 * @param settleClientService the settleClientService to set
	 */
	public void setSettleClientService(SettleClientService settleClientService) {
		this.settleClientService = settleClientService;
	}
	
}
