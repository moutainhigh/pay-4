package com.pay.txncore.handler;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.model.Acct;
import com.pay.acc.acct.model.VouchAcct;
import com.pay.acc.acct.service.AcctService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 冻结金额修复
 *  File: FrozenAmountRepairHandler.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年7月18日   mmzhang     Create
 *
 */
public class FrozenAmountRepairHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private AcctService acctService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		logger.info("冻结金额补帐开始");
		
		Map paraMap = null;
		Map resultMap = new HashMap();

		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

		try {
			List<VouchAcct> guaranteeVouchAccts=acctService.queryGuaranteeRepairAmount();
			//要处理的保证金账户
			List<Acct> guaranteeAccts=acctService.queryAcctCodeForAcctType("2");
			//根据会员号和货币代码取账户冻结金额  900基本户冻结 901保证金额冻结
			List<Acct> updateguaranteeFrozenAccts=acctService.queryFrozenAmountByacctCode(guaranteeAccts, "901");
			List<Acct> updateguaranteeUnFrozenAccts=acctService.queryUnFrozenAmountByacctCode(guaranteeAccts, "903");
			
			List<Acct> guaranteeList= new ArrayList<Acct>();
			Map<String,Acct> guaranteeMap= new HashMap<String,Acct>();
			logger.info("保证金户冻结和解冻金额重新计算开始");
			if(null!=updateguaranteeFrozenAccts && updateguaranteeFrozenAccts.size()>0)
			{
			doforFrozenMap(updateguaranteeFrozenAccts,
					updateguaranteeUnFrozenAccts,guaranteeVouchAccts, guaranteeMap,guaranteeList,"2");
			}
			logger.info("保证金户冻结和解冻金额重新计算结束");
			//要处理的基本户账户
			List<VouchAcct> basicVouchAccts=acctService.queryBasicRepairAmount();
			List<Acct> basicAccts=acctService.queryAcctCodeForAcctType("1");
			//根据会员号和货币代码取账户解冻金额  902基本户解冻 903保证金额解
			List<Acct> upbasicFrozenAccts=acctService.queryFrozenAmountByacctCode(basicAccts, "900");
			List<Acct> upbasicUnFrozenAccts=acctService.queryUnFrozenAmountByacctCode(basicAccts, "902");
			List<Acct> basicList= new ArrayList<Acct>();
			Map<String,Acct> basicMap= new HashMap<String,Acct>();
			
			logger.info("基本户冻结和解冻金额重新计算开始");
			if(null!=upbasicFrozenAccts && upbasicFrozenAccts.size()>0)
			{
			doforFrozenMap(upbasicFrozenAccts,
					upbasicUnFrozenAccts,basicVouchAccts, basicMap,basicList,"1");
			}
			logger.info("基本户冻结和解冻金额重新计算结束");
			
			logger.info("批量更新基本户和保证金户开始！");
			acctService.updateFrozenAmountBatch(guaranteeList);
			acctService.updateFrozenAmountBatch(basicList);
			logger.info("批量更新基本户和保证金户结束！");
		} catch (Exception e) {
			logger.error("CountRefundFeeHandler error:", e);
		}
        	
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		
		logger.info("冻结金额补帐结束");
		
		return JSonUtil.toJSonString(resultMap);
	}

	/**
	 * 冻结金额计算过程
	 * @param updateguaranteeFrozenAccts
	 * @param updateguaranteeUnFrozenAccts
	 * @param guaranteeMap
	 * @param list
	 * 2016年7月18日   mmzhang     add
	 */
	private void doforFrozenMap(List<Acct> updateguaranteeFrozenAccts,
			List<Acct> updateguaranteeUnFrozenAccts,List<VouchAcct> vouchAccts,
			Map<String, Acct> guaranteeMap,List<Acct> list,String type) {
		for (Acct acct : updateguaranteeUnFrozenAccts) {
			String key=acct.getAcctCode();
			Acct acct0= new Acct();
			acct0.setAcctCode(key);
			acct0.setFrozeAmount(acct.getFrozeAmount());
			logger.info("账户："+key+";冻结金额："+acct.getFrozeAmount()+";type="+type);
			guaranteeMap.put(key, acct0);
		}
		for(VouchAcct vouchAcct:vouchAccts)
		{
			//添加调账金额计算冻结金额
			String key=vouchAcct.getAccountCode();
			if (guaranteeMap.containsKey(key)) {
				BigDecimal amount=BigDecimal.ZERO;
				Acct frozeAmountVo =guaranteeMap.get(key);
				if(vouchAcct.getCrdr()==1)
				{
					amount= new BigDecimal(vouchAcct.getAmount()).multiply(new BigDecimal("1000"));
					Long frozeAmount= frozeAmountVo.getFrozeAmount();
					frozeAmountVo.setFrozeAmount(new BigDecimal(frozeAmount).add(amount).longValue());
				}else if(vouchAcct.getCrdr()==1)
				{
					amount= new BigDecimal(vouchAcct.getAmount()).multiply(new BigDecimal("1000"));
					Long frozeAmount= frozeAmountVo.getFrozeAmount();
					Long amountsub=new BigDecimal(frozeAmount).subtract(amount).longValue();
					if(null==amountsub || amountsub<0L){
						amountsub=0L;
					}
					frozeAmountVo.setFrozeAmount(amountsub);
				}
			}
		}
		for (Acct acct : updateguaranteeFrozenAccts) {
			String key=acct.getAcctCode();
			if (guaranteeMap.containsKey(key)) {
				Acct frozeAmountVo =guaranteeMap.get(key);
				if(null==frozeAmountVo.getFrozeAmount()){
					frozeAmountVo.setFrozeAmount(0L);
				}
				
				//冻结金额
				Long frozeAmount2 =acct.getFrozeAmount();
				if(null==frozeAmount2){
					frozeAmount2=0L;
				}
				logger.info("账户："+key+"冻结金额："+frozeAmount2+"解冻金额："+frozeAmountVo.getFrozeAmount()+";type="+type);
				Long amount=frozeAmount2-frozeAmountVo.getFrozeAmount();
				logger.info("账户："+key+"计算后的冻结金额："+amount+";type="+type);
				if(null==amount || amount<0L){
					amount=0L;
				}
				frozeAmountVo.setFrozeAmount(amount);
				list.add(frozeAmountVo);
			}else{
				logger.info("无解冻情况，账户："+key+"冻结金额："+acct.getFrozeAmount()+"解冻金额：0"+";type="+type);
				list.add(acct);
			}
		}
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}


}
