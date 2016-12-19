package com.pay.acc.service.account.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.balancelog.dto.FrozenAmountDto;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;
import com.pay.acc.balancelog.service.FrozenOperatorLogService;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.AccountSplitHandlerService;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.FreezeBalanceDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.jms.sender.JmsSender;
import com.pay.util.StringUtil;

public class AccountSplitHandlerServiceImpl implements AccountSplitHandlerService {
	private Log log = LogFactory.getLog(AccountSplitHandlerServiceImpl.class);	
	
	private JmsSender jmsSender;
	
	private FrozenOperatorLogService frozenOperatorLogService;
	private AcctService acctService;
	
	private AccountBalanceHandlerService accountBalanceHandlerService;
	
	
	 public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setFrozenOperatorLogService(
			FrozenOperatorLogService frozenOperatorLogService) {
		this.frozenOperatorLogService = frozenOperatorLogService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setAccountBalanceHandlerService(
			AccountBalanceHandlerService accountBalanceHandlerService) {
		this.accountBalanceHandlerService = accountBalanceHandlerService;
	}

	/* (non-Javadoc)
	 * @see com.pay.acc.service.account.impl.AccountSplitHandlerService#batchUpdateAcctBalanceRntx(java.util.List, java.util.List, java.lang.Integer)
	 */
	@Override
	public boolean batchUpdateAcctBalanceRntx(List<CalFeeReponseDto> updateBalanceRequestList,List<FreezeBalanceDto> freezeList,Integer payType) throws MaAcctBalanceException {
		 //根据分录信息批量更新余额
		//循环更新余额，记录交易明细的id号
		List<Long> dealSeqIds = new ArrayList<Long>();
		for(CalFeeReponseDto calFeeDto: updateBalanceRequestList){
			Long seqId=  accountBalanceHandlerService.updateAccBalance(calFeeDto, payType);
			dealSeqIds.add(seqId);
		}
		 //freezeList.size大于0情况下 对账户进行批量冻结
		 //校验冻结金额是否大于可用余额
		 //批量冻结
		 //记录冻结日志
			batchFrozenBlance(freezeList);
		 //...
		 
		//前面都成功之后发送MQ
			int index = 0;
			for (Long dealId : dealSeqIds) {
				accountBalanceHandlerService.sendMsgToPe(updateBalanceRequestList.get(index), dealId);
				index++;
			}
		 return true;
	 }
	 
	/**
	 * 分账接口
	 * @param updateBalanceRequestList
	 * @param unfreezeDto（需要解冻的单个账户）
	 * @param freezeList（需要冻结的多个分账账户）
	 * @param payType
	 * @return
	 * @throws AcctServiceUnkownException 
	 * @throws AcctServiceException 
	 */
	@Override
	public boolean batchSplitAcctBalanceRntx(List<CalFeeReponseDto> updateBalanceRequestList,FreezeBalanceDto unfreezeDto,List<FreezeBalanceDto> unfreezeList,Integer payType) throws MaAcctBalanceException{
			 //解冻账户金额(需做校验)
			log.info("分账-------------开始-----------------");
			log.info("-------分账---1---解冻主账户金额");
			if(unfreezeDto == null){
				log.error("-------分账------主账户参数为空！");
				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, "batchSplit--main account unfreezeDto is null!");
			}
			Long memberCode = unfreezeDto.getMemberCode();
			String acctCode = unfreezeDto.getAcctCode();
			if(StringUtil.isEmpty(acctCode)){
				log.error("-------分账------主账户参数帐户号为空为空！");
				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, "batchSplit--the acctCode of main account unfreezeDto is null!");
			
			}
			AcctDto accDto;
			try {
				accDto = acctService.queryAcctWithAcctCode(acctCode);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, e.getMessage(), e);
			} 
			if(accDto == null){
				log.error("-------分账------主帐户号不存在:acctCode:"+acctCode);
				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, "batchSplit--main acctCode is null!");
			}else {
				if(!accDto.getMemberCode().equals(memberCode)){
					log.error("-------分账------主账户会员号帐户号不匹配:");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, "batchSplit--main acctCode and memberCode is false!");
				}
			}
//			BigDecimal amount =  unfreezeDto.getAmount();
//			Long accAmount = accDto.getFrozeAmount();
//			if(amount.compareTo(new BigDecimal(accAmount)) > 0){
//				log.info("分账---主帐户更新冻结金额大于帐户当前冻结金额:"+amount+">"+accAmount);
//				return false;
//			}
			UnFrozenAmountDto unFrozen = new UnFrozenAmountDto();
			unFrozen.setOrderSeqNo(unfreezeDto.getTransOrderSeq());
			unFrozen.setMemberCode(unfreezeDto.getMemberCode());
			unFrozen.setAcctCode(unfreezeDto.getAcctCode());
			unFrozen.setUnFrozenAmount(unfreezeDto.getAmount());
			try{
				boolean unfrozen = acctService.unFrozenAmount(unFrozen);
				if(unfrozen == false){
					log.info("-------分账------分账，解冻主账户发生错误!");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, "解冻主账户发生错误");
				}
			}catch(Exception e){
				log.error(e);
				throw new MaAcctBalanceException(e.getMessage());
			}
			
			
			List<Long> dealSeqIds = new ArrayList<Long>();
			if(updateBalanceRequestList != null && updateBalanceRequestList.size() > 0){
//				Long totalPayerAmount = 0l;
//				for(CalFeeReponseDto calFeeRes:updateBalanceRequestList){
//					totalPayerAmount += calFeeRes.getCalFeeRequestDto().getAmount();
////					if(acctCode.equals(calFeeRes.getCalFeeRequestDto().getPayer())){
////						log.info("分账---分账，付款方不是主帐户");
////						return false;
////					}
//				}
//				if(unfreezeDto.getAmount().longValue() < totalPayerAmount){
//					log.info("-------分账------分账，主帐户分账金额与子帐户收款总额不符");
//					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_PARAMETER, "主帐户分账金额与子帐户收款总额不符");
//							  
//				}
				//记录解冻日志
				//根据分录信息批量更新余额
				log.info("-------分账---2---批量更新子帐户余额");
				for(CalFeeReponseDto calFeeDto: updateBalanceRequestList){
					Long seqId=  accountBalanceHandlerService.updateAccBalance(calFeeDto, payType);
					dealSeqIds.add(seqId);
				}
			}
			
			 //freezeList.size大于0情况下 对账户进行批量冻结
			 //校验冻结金额是否大于可用余额
//			if(unfreezeList != null && unfreezeList.size() > 0){
//				for(FreezeBalanceDto freezeBalance:unfreezeList){
//					AcctDto acct  = acctService.queryAcctByAcctCode(freezeBalance.getAcctCode());
//					if(acct == null){
//						log.info("子帐户不存在:AcctCode="+freezeBalance.getAcctCode());
//						return false;
//					}
//					if( freezeBalance.getAmount().compareTo(new BigDecimal(acct.getBalance())) > 0){
//						log.info("子帐户冻结金额大于可用余额");
//						return false;
//					}
//				}
//			}else{
//				log.info("子帐户冻结列表为空");
//			}
			 //批量冻结
			 //记录冻结日志
			log.info("-------分账---3---记录冻结日志");
			batchFrozenBlance(unfreezeList);
			 //...
			//前面都成功之后发送MQ
			int index = 0;
			for (Long dealId : dealSeqIds) {
				accountBalanceHandlerService.sendMsgToPe(updateBalanceRequestList.get(index), dealId);
				index++;
			}
			log.info("分账-------------结束-----------------");
			return true;

	 }
	 
	 
	 /* (non-Javadoc)
	 * @see com.pay.acc.service.account.impl.AccountSplitHandlerService#batchUnFreezeBalanceRntx(java.util.List)
	 */
	@Override
	public boolean batchUnFreezeBalanceRntx(List<FreezeBalanceDto> unfreezeList) throws MaAcctBalanceException{
	
		log.info("批量解冻开始......batchUnFreezeBalanceRntx......");
		if(unfreezeList!=null && ! unfreezeList.isEmpty()){
			log.info("unfreezeList验证大小...条数为..."+unfreezeList.size());
			List<UnFrozenAmountDto> unFaDtos = new ArrayList<UnFrozenAmountDto>();
			for (FreezeBalanceDto unDto : unfreezeList) {
				UnFrozenAmountDto unFaDto = new UnFrozenAmountDto();
				unFaDto.setAcctCode(unDto.getAcctCode());
				unFaDto.setMemberCode(unDto.getMemberCode());
				unFaDto.setOrderSeqNo(unDto.getTransOrderSeq());
				unFaDto.setUnFrozenAmount(unDto.getAmount());
				unFaDtos.add(unFaDto);
			}
			log.info("封装解冻dto正常......");
			try{
				boolean batchUpdate = acctService.batchUnFrozenAmount(unFaDtos);
				if(batchUpdate){
					//批量更新解冻日志
						return true;
				}
				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_BALANCE_UNFROZEN_ERROR,"批量解冻操作异常");
			}catch (Exception e) {
				throw new MaAcctBalanceException(e);
			}
		}
		//记录解冻日志
		 return true;
	 }
	
	
	@Override
	public boolean batchRefundBalanceRntx(List<FreezeBalanceDto> unfreezeList,List<CalFeeReponseDto> updateBalanceRequestList,Integer payType) throws MaAcctBalanceException{
		log.info("批量分账退款-------------start-----------------");
		//unfreezeList.size有值 批量解冻(需做校验)
		if(null != unfreezeList && unfreezeList.size() > 0){
			log.info("批量分账退款------1-------批量解冻-----------------");
			batchUnFreezeBalanceRntx(unfreezeList);
		}
		//记录解冻日志
		//根据分录信息批量更新余额
		List<Long> dealSeqIds = new ArrayList<Long>();
		if(updateBalanceRequestList != null && updateBalanceRequestList.size() > 0){
			log.info("批量分账退款------2------批量更新子帐户余额-----------------");
			for(CalFeeReponseDto calFeeDto: updateBalanceRequestList){
				Long seqId=  accountBalanceHandlerService.updateAccBalance(calFeeDto, payType);
				dealSeqIds.add(seqId);
			}
		}
		//前面都成功之后发送MQ
		int index = 0;
		for (Long dealId : dealSeqIds) {
			log.info("批量分账退款---send message to pe");
			accountBalanceHandlerService.sendMsgToPe(updateBalanceRequestList.get(index), dealId);
			index++;
		}
		log.info("批量分账退款-------------end-----------------");
		return true;
	}

	
	/**
	 *  批量冻结（无事务）
	 * @param unfreezeList
	 * @return true/false
	 * @throws MaAcctBalanceException
	 * @author ddr
	 */
	private boolean  batchFrozenBlance(List<FreezeBalanceDto> unfreezeList) throws MaAcctBalanceException{
		log.info("批量冻结开始......batchUnFreezeBalanceRntx....");
		if(unfreezeList !=null && ! unfreezeList.isEmpty()){
			log.info("unfreezeList验证大小...条数为."+unfreezeList.size());
			List<FrozenAmountDto> faDtos = new ArrayList<FrozenAmountDto>();
			for (FreezeBalanceDto dto : unfreezeList) {
				FrozenAmountDto faDto = new FrozenAmountDto();
				faDto.setAcctCode(dto.getAcctCode());
				faDto.setMemberCode(dto.getMemberCode());
				faDto.setFrozenAmount(dto.getAmount());
				faDto.setOrderSeqNo(dto.getTransOrderSeq());
				faDtos.add(faDto);
			}
			log.info("封装冻结dto正常.....");
			//批量冻结账户
			try{
				boolean batchUpdate =  acctService.batchAddFrozenAmount(faDtos);
				if(batchUpdate){
					//批量更新日志 
						return true;
				}
			}catch (Exception e) {
				log.error(e);
				throw new MaAcctBalanceException(e);
			}
			
			throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_BALANCE_BALANCE_ERROR,"批量冻结操作异常");
		}
		return false;
		
	}
	
	
	
}
