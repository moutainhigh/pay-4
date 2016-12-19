/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.report;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.ChannelOrderReport;
import com.pay.txncore.model.TranDailyReportVo;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.ReportService;
import com.pay.txncore.service.TranDailyReportService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 交易日报表
 * 
 * 
 */
public class TranDailyReportCreateHanler implements EventHandler {

	public final Log logger = LogFactory.getLog(TranDailyReportCreateHanler.class);
	private int maxSize = 100;
	private ChannelOrderService channelOrderService;
	private ReportService reportService;
	private TranDailyReportService tranDailyReportService;

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> resultMap = new HashMap<String, Object>();
		System.out.println("TranDailyReportCreateHanler handle............") ;
		Map<String, String> paraMap = null;
		int status = 1;
		paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());
		String beginDate = tranDailyReportService.queryTranDailyReportMaxDate();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String endDate = df.format(new Date());
		if (StringUtil.isEmpty(beginDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.DATE,  c.get(Calendar.DATE) - 2);
			beginDate = df.format(c.getTime());
		}
		// 查询所有交易成功的渠道订单 statue 1成功
		List<ChannelOrderReport> channelOrders = channelOrderService
				.queryUnStatuschannelOrder(beginDate, endDate, maxSize, status);
   
			
		logger.info("查询到交易在" + beginDate + "和" + endDate + "交易成功的渠道订单为"
				+ channelOrders.size() + "条！");

		if (null != channelOrders && !channelOrders.isEmpty()
				&& channelOrders.size() > 0) {
			List<TranDailyReportVo> list = null;

			try {
				list = reportService.tranDailyReport(channelOrders);
			} catch (Exception e) {
				String result = "生成交易日报表失败!";
				logger.info("FinancialReportHandler-method: handle [" + result
						+ "]");
				e.printStackTrace();
			}
			/*List<TranDailyReportVo> list = new ArrayList<TranDailyReportVo>() ;
			TranDailyReportVo constructDailyReportVo = this.constructDailyReportVo() ;
			list.add(constructDailyReportVo) ;*/
			if (null != list && !list.isEmpty() && list.size() > 0) {
				tranDailyReportService.saveTranDailyReportRdTx(list);
			}
		}

		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}
	
	//测试用
	private TranDailyReportVo constructDailyReportVo(){
		TranDailyReportVo tranDailyReportVo = new TranDailyReportVo() ;
		tranDailyReportVo.setAssureAmount(new BigDecimal(1));
		tranDailyReportVo.setAssureAmountCny(new BigDecimal(23)) ;
		tranDailyReportVo.setAssuresettlementFlg(32);
		tranDailyReportVo.setAuthorisation("authorisation");
		tranDailyReportVo.setBankCurrencyCode("CNY");
		tranDailyReportVo.setBankFixedCurrencyCode("CNY");
		tranDailyReportVo.setBankFixedFee(new BigDecimal(88));
		tranDailyReportVo.setBankFixedFeeCny(new BigDecimal(99));
		tranDailyReportVo.setBankPayAmount(new BigDecimal(99));
		tranDailyReportVo.setBankPayAmountCny(new BigDecimal(99));
		tranDailyReportVo.setBankPayCurrencyCode("CNY");
		tranDailyReportVo.setBankPerCurrencyCode("CNY");
		tranDailyReportVo.setBankPerFee(new BigDecimal(33));
		tranDailyReportVo.setBankPerFeeCny(new BigDecimal(33));
		tranDailyReportVo.setBaseAmount(new BigDecimal(33));
		tranDailyReportVo.setBaseAmountCny(new BigDecimal(22));
		tranDailyReportVo.setChannelOrderNo(999L);
		tranDailyReportVo.setCreateDate(new Date());
		tranDailyReportVo.setCurrencyCode("USD");
		tranDailyReportVo.setFeeRate(new BigDecimal(3.33));
		tranDailyReportVo.setFixedFee(new BigDecimal(1));
		tranDailyReportVo.setFlag("flag");
		tranDailyReportVo.setMerchantNo("32222222222222");
		tranDailyReportVo.setOrderId("888");
		tranDailyReportVo.setOrgCode("orgcode");
		tranDailyReportVo.setOrgName("orgName");
		tranDailyReportVo.setPartnerId(2342L);
		tranDailyReportVo.setPartnerSettlementOrderNo(3232L);
		tranDailyReportVo.setPayAmount(new BigDecimal(9));
		tranDailyReportVo.setPayCnyRate(new BigDecimal(1.2));
		tranDailyReportVo.setPayCurrencyCode("CNY");
		tranDailyReportVo.setPaymentOrderNo(2332L);
		tranDailyReportVo.setPayRate("3.33");
		tranDailyReportVo.setPayType("DCC");
		tranDailyReportVo.setPerFee(new BigDecimal(3));
		tranDailyReportVo.setProfit(new BigDecimal(3));
		tranDailyReportVo.setProfitRate(new BigDecimal(1.33));
		tranDailyReportVo.setRateIncome(new BigDecimal(9999));
		tranDailyReportVo.setRateRate(new BigDecimal("1.3"));
		tranDailyReportVo.setReconciliationFlg(1);
		tranDailyReportVo.setRemark("remark");
		tranDailyReportVo.setSettlementAmount(new BigDecimal(3));
		tranDailyReportVo.setSettlementAmountCny(new BigDecimal(3));
		tranDailyReportVo.setSettlementCnyRate(new BigDecimal("3.333"));
		tranDailyReportVo.setSettlementCurrencyCode("CNY");
		tranDailyReportVo.setSettlementDate(new Date());
		tranDailyReportVo.setSettlementFlg(1);
		tranDailyReportVo.setSettlementRate(new BigDecimal(1));
		tranDailyReportVo.setStatus(1);
		tranDailyReportVo.setTotalIncome(new BigDecimal(3333));
		tranDailyReportVo.setTradeOrderNo(1222L);
		tranDailyReportVo.setTranAmount(new BigDecimal(111));
		tranDailyReportVo.setTranCount(3L);
		tranDailyReportVo.setTranCurrencyCode("CNY");
//		tranDailyReportVo.setTranDailyReportNo(tranDailyReportNo);
		tranDailyReportVo.setTranFee(new BigDecimal(6.8));
		tranDailyReportVo.setTranType("tranType");
		
		return tranDailyReportVo ;
		
	}
	
	
	/**
	 * @return测试用， 构建数据库必填字段
	 */
	private TranDailyReportVo constructDailyReportVo2(){
		TranDailyReportVo tranDailyReportVo = new TranDailyReportVo() ;
		tranDailyReportVo.setChannelOrderNo(999L);
		tranDailyReportVo.setTradeOrderNo(120L);
		tranDailyReportVo.setPaymentOrderNo(112L);
		tranDailyReportVo.setOrderId("234223");
		tranDailyReportVo.setPartnerSettlementOrderNo(111L);
		tranDailyReportVo.setOrgCode("orgCode");
		tranDailyReportVo.setOrgName("orgName");
		tranDailyReportVo.setCreateDate(new Date());
		tranDailyReportVo.setPartnerId(10000003671L) ;
		tranDailyReportVo.setCurrencyCode("CNY");
		tranDailyReportVo.setTranCurrencyCode("CNY");
		tranDailyReportVo.setPayCurrencyCode("CNY");
		tranDailyReportVo.setSettlementCurrencyCode("CNY");
		tranDailyReportVo.setStatus(1);
		tranDailyReportVo.setBankPerCurrencyCode("CNY");
		tranDailyReportVo.setBankFixedCurrencyCode("CNY");
		tranDailyReportVo.setBankCurrencyCode("CNy");
		tranDailyReportVo.setSettlementFlg(1);
		
		return tranDailyReportVo ;
	}
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setTranDailyReportService(
			TranDailyReportService tranDailyReportService) {
		this.tranDailyReportService = tranDailyReportService;
	}
}
