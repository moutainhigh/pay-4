package com.pay.acc.service.querybalance.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.querybalance.dao.QueryBalanceDAO;
import com.pay.acc.querybalance.dto.MaSumDto;
import com.pay.acc.querybalance.model.QueryBalance;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.acc.service.querybalance.QueryBalanceService;
import com.pay.acc.service.querybalance.dto.QueryBalanceDto;
import com.pay.acc.service.querybalance.dto.QueryMaSumDto;
import com.pay.util.BeanConvertUtil;

/**
 * 
 * @author jerry_jin
 * @date 2010-10-2
 *
 */
public class QueryBalanceServiceImpl implements QueryBalanceService {
	
	private static final Log logger = LogFactory.getLog(QueryBalanceServiceImpl.class);
	
	final static int DIVIDER = 1000;	//单位计算到厘，除数为1000
	final static int SCALE = 2;			//两位小数
	
	private QueryBalanceDAO queryBalanceDAO;
	private AcctService acctService;
	@Override
	public List<QueryBalanceDto> queryBalanceList(Date startDate, Date endDate,
			Long memberCode, String dealtype,String fundTrace, int pageNo, int pageSize,String[][] orderColumns) {
		List<QueryBalanceDto> queryCorpBalanceDtoList=null;
		try {
			AcctDto acct=acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, 10);
			queryCorpBalanceDtoList = null;
			QueryBalanceDto balanceDto = null;
			if(acct!=null && acct.getAcctCode()!=null){
				queryCorpBalanceDtoList = new ArrayList<QueryBalanceDto>();
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("sDate", startDate);
				paramMap.put("eDate", endDate);
				paramMap.put("acctCode", acct.getAcctCode());
				paramMap.put("start", (pageNo-1)*pageSize+1);
				
				//拼接排序SQL
				StringBuffer orderStr = new StringBuffer("");
				for(String[] orderColumn: orderColumns){
					for(String order:orderColumn){
						orderStr.append(order).append(" ");
					}
					orderStr.append(",");
				}
				if(orderStr.indexOf(",")!=-1){
					orderStr = orderStr.deleteCharAt(orderStr.lastIndexOf(","));
				}
				
				paramMap.put("orderStr", orderStr);
				paramMap.put("fundTrace",(fundTrace==null||"0".equals(fundTrace))?null:fundTrace);
				paramMap.put("end", pageNo*pageSize);
				String[] dealtypes = (dealtype==null||"0".equals(dealtype))?null:dealtype.split(",");
				paramMap.put("dealTypes", dealtypes);
				List<QueryBalance> queryCorpBalanceList = queryBalanceDAO.queryBalanceList(paramMap);
				if(queryCorpBalanceList!=null){
					for(QueryBalance corpBalance:queryCorpBalanceList){
						balanceDto = new QueryBalanceDto();
						paramMap.clear();
						paramMap.put("dealid", corpBalance.getPayNo());
						paramMap.put("acctCode", acct.getAcctCode());
						paramMap.put("dealCode", corpBalance.getDealCode());
						if(corpBalance.getFundTrace().equals(String.valueOf(PayForEnum.FEE_AMOUNT.getCode()))){
							paramMap.put("status", 1);
						}else{
							paramMap.put("status", 0);
						}
						
						//如果付款方费用无，则查询收款方费用,add by jerry
						BigDecimal payerFee = corpBalance.getPayerFee() == null ? new BigDecimal(0) : corpBalance.getPayerFee();
						BigDecimal payeeFee = corpBalance.getPayeeFee()== null ? new BigDecimal(0) : corpBalance.getPayeeFee();
						
						BigDecimal fee = divideThousand(payerFee);
						
						if(fee.doubleValue() == 0){
							fee = divideThousand(payeeFee);
						}
						balanceDto.setFee(fee);
						
						/*
						//设置付款方费用
						if(StringUtils.isNotEmpty(corpBalance.getPayerAccount()) && corpBalance.getPayerAccount().equals(acct.getAcctCode())){
							balanceDto.setFee(FormatNumber.round((corpBalance.getPayerFee()==null?0:corpBalance.getPayerFee()).doubleValue()/DIVIDER));
						}
						
						//设置收款方费用
						if(StringUtils.isNotEmpty(corpBalance.getPayeeAccount()) && corpBalance.getPayeeAccount().equals(acct.getAcctCode())){
							balanceDto.setFee(FormatNumber.round((corpBalance.getPayeeFee()==null?0:corpBalance.getPayeeFee()).doubleValue()/DIVIDER));
						}
						*/
						
						BigDecimal balance = queryBalanceDAO.queryBalance(paramMap);
						balance = balance == null ? new BigDecimal(0) : balance;
						balanceDto.setBalance(divideThousand(balance));	//余额
						balanceDto.setBalanceDate(corpBalance.getBalanceDate());									//余额交易日期
						balanceDto.setPayNo(corpBalance.getPayNo());												//流水号
						balanceDto.setFundTrace(PayForEnum.get(Integer.parseInt(corpBalance.getFundTrace())).getMessage());//交易类型
						balanceDto.setFundTraceType(Integer.parseInt(corpBalance.getFundTrace()));
						BigDecimal revenue = corpBalance.getRevenue() == null ? new BigDecimal(0) : corpBalance.getRevenue();
						BigDecimal pay = corpBalance.getPay() == null ? new BigDecimal(0) : corpBalance.getPay();
						
						BigDecimal paydouble = divideThousand(pay);
						BigDecimal revenuedouble = divideThousand(revenue);
						
						if(paydouble.doubleValue() <0){
							balanceDto.setRevenue(paydouble.abs());	//支出
						}else if(paydouble.doubleValue() >0){
							balanceDto.setPay(paydouble.abs());		//收入
						}
						if(revenuedouble.doubleValue() <0){
							balanceDto.setPay(revenuedouble.abs());		//支出	
						}else if(revenuedouble.doubleValue() >0){
							balanceDto.setRevenue(revenuedouble.abs());	//收入	
						}
						
						
						
						
						queryCorpBalanceDtoList.add(balanceDto);
					}
				}
				
				
			}else{
				logger.error("企业账户余额查询出错！非法账户号 acctCode="+(acct==null?null:acct.getAcctCode()));
			}
		} catch (Exception e) {
			logger.error("企业账户余额查询出错！memberCode=" +memberCode+" fundTrace="+fundTrace, e);
		}
		
		return queryCorpBalanceDtoList;
	}

	@Override
	public QueryMaSumDto queryHistoryBusinessSum(Date startDate, Date endDate,
			Long memberCode, String dealtype,String fundTrace) {
		
		MaSumDto maCorpSumDto = null;
		
		try{
			AcctDto acct=acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, 10);
			if(acct!=null && acct.getAcctCode()!=null){
					Map<String,Object> paramMap = new HashMap<String,Object>();
					paramMap.put("sDate", startDate);
					paramMap.put("eDate", endDate);
					paramMap.put("acctCode", acct.getAcctCode());
					paramMap.put("fundTrace",(fundTrace==null||"0".equals(fundTrace))?null:fundTrace);
					String[] dealTypes = (dealtype==null||"0".equals(dealtype))?null:dealtype.split(",");
					paramMap.put("dealTypes",dealTypes);
					 maCorpSumDto = queryBalanceDAO.queryHistoryBusinessSum(paramMap);
					
					//maCorpSumDto.setIncomeSumNoCount(maCorpSumDto.getIncomeSumNoCount());														//收入比数
					//maCorpSumDto.setExpensesSumNoCount(maCorpSumDto.getExpensesSumNoCount());													//支出比数
					BigDecimal incomeSumNo = maCorpSumDto.getIncomeSumNo() == null ? new BigDecimal(0) : maCorpSumDto.getIncomeSumNo();
					BigDecimal expensesSumNo = maCorpSumDto.getExpensesSumNo() == null ? new BigDecimal(0) : maCorpSumDto.getExpensesSumNo();
																	
					maCorpSumDto.setExpensesSumNo(divideThousand(expensesSumNo));	//总支出
					maCorpSumDto.setIncomeSumNo(divideThousand(incomeSumNo));		//总收入
					maCorpSumDto.setBalanceSumNo(maCorpSumDto.getIncomeSumNo().subtract(maCorpSumDto.getExpensesSumNo()));						//盈余
					maCorpSumDto.setCount(maCorpSumDto.getCount());																				//总比数
				}else{
					logger.error("企业账户余额统计出错！非法账户号 acctCode="+(acct==null?null:acct.getAcctCode()));
				}
			}catch(Exception ex){
				logger.error("企业账户余额统计出错！memberCode=" +memberCode+" fundTrace="+fundTrace, ex);
			}
			return  BeanConvertUtil.convert(QueryMaSumDto.class, maCorpSumDto);
		
		
	}
	
	@Override
	public List<QueryMaSumDto> queryPayMentSumList(String acctCode){
		QueryMaSumDto mDto=null;
		List<QueryMaSumDto> mSumList=null;
		if(StringUtils.isNotBlank(acctCode)){
			mSumList=new ArrayList<QueryMaSumDto>(5);
			/**当天*/
			mDto=new QueryMaSumDto();
			mDto.setIncomeSumNo(divideThousand(queryBalanceDAO.queryIncomeBalance(acctCode, "day")));
			mDto.setIncomeSumNoCount(queryBalanceDAO.queryIncomeCount(acctCode, "day"));
			mDto.setExpensesSumNo(divideThousand(queryBalanceDAO.queryExpensesBalance(acctCode, "day")));
			mDto.setExpensesSumNoCount(queryBalanceDAO.queryExpensesCount(acctCode, "day"));
			mSumList.add(mDto);
			/**本周*/
			mDto=new QueryMaSumDto();
			mDto.setIncomeSumNo(divideThousand(queryBalanceDAO.queryIncomeBalance(acctCode, "week")));
			mDto.setIncomeSumNoCount(queryBalanceDAO.queryIncomeCount(acctCode, "week"));
			mDto.setExpensesSumNo(divideThousand(queryBalanceDAO.queryExpensesBalance(acctCode, "week")));
			mDto.setExpensesSumNoCount(queryBalanceDAO.queryExpensesCount(acctCode, "week"));
			mSumList.add(mDto);
			/**上周*/
			mDto=new QueryMaSumDto();
			mDto.setIncomeSumNo(divideThousand(queryBalanceDAO.queryIncomeBalance(acctCode, "preweek")));
			mDto.setIncomeSumNoCount(queryBalanceDAO.queryIncomeCount(acctCode, "preweek"));
			mDto.setExpensesSumNo(divideThousand(queryBalanceDAO.queryExpensesBalance(acctCode, "preweek")));
			mDto.setExpensesSumNoCount(queryBalanceDAO.queryExpensesCount(acctCode, "preweek"));
			mSumList.add(mDto);
			/**本月**/
			mDto=new QueryMaSumDto();
			mDto.setIncomeSumNo(divideThousand(queryBalanceDAO.queryIncomeBalance(acctCode, "month")));
			mDto.setIncomeSumNoCount(queryBalanceDAO.queryIncomeCount(acctCode, "month"));
			mDto.setExpensesSumNo(divideThousand(queryBalanceDAO.queryExpensesBalance(acctCode, "month")));
			mDto.setExpensesSumNoCount(queryBalanceDAO.queryExpensesCount(acctCode, "month"));
			mSumList.add(mDto);
			/**上月**/
			mDto=new QueryMaSumDto();
			mDto.setIncomeSumNo(divideThousand(queryBalanceDAO.queryIncomeBalance(acctCode, "premonth")));
			mDto.setIncomeSumNoCount(queryBalanceDAO.queryIncomeCount(acctCode, "premonth"));
			mDto.setExpensesSumNo(divideThousand(queryBalanceDAO.queryExpensesBalance(acctCode, "premonth")));
			mDto.setExpensesSumNoCount(queryBalanceDAO.queryExpensesCount(acctCode, "premonth"));
			mSumList.add(mDto);
			
		}
		return mSumList;
	}
	

	public void setQueryBalanceDAO(QueryBalanceDAO queryBalanceDAO) {
		this.queryBalanceDAO = queryBalanceDAO;
	}
	
	
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	//除以1000 ，保留2 位小数
	private BigDecimal divideThousand(BigDecimal num){
		if(num == null){
			return new BigDecimal(0);
		}
		return num.divide(new BigDecimal(Double.toString(DIVIDER)),SCALE,BigDecimal.ROUND_HALF_UP);
	}
	
	
}
