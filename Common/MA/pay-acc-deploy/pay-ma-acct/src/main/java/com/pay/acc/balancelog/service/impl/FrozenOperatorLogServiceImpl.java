/**
 * 
 */
package com.pay.acc.balancelog.service.impl;

import java.math.BigDecimal;

import com.pay.acc.balancelog.dao.FrozenOperatorLogDao;
import com.pay.acc.balancelog.dto.FrozenAmountDto;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;
import com.pay.acc.balancelog.model.FrozenOperatorLog;
import com.pay.acc.balancelog.service.FrozenOperatorLogService;

/**
 * @author ddr
 *
 */
public class FrozenOperatorLogServiceImpl implements FrozenOperatorLogService {
	
	private FrozenOperatorLogDao frozenOperatorLogDao;
	
	public FrozenOperatorLogDao getFrozenOperatorLogDao() {
		return frozenOperatorLogDao;
	}

	public void setFrozenOperatorLogDao(FrozenOperatorLogDao frozenOperatorLogDao) {
		this.frozenOperatorLogDao = frozenOperatorLogDao;
	}

//	@Override
//	public List<Long> batchAddFrozenLog(List<FrozenAmountDto> frozenAmountDtos) {
//		List<FrozenOperatorLog> frOpDtos = new ArrayList<FrozenOperatorLog>();
//		for(FrozenAmountDto fDto: frozenAmountDtos){
//			FrozenOperatorLog frOp = new FrozenOperatorLog();
//			frOp.setFrozenType(FrozenOperatorLog.FROZEN_TYPE_FROZEN);//冻结
//			frOp.setMemberCode(fDto.getMemberCode());
//			frOp.setAmount(fDto.getFrozenAmount());
//			frOp.setAcctCode(fDto.getAcctCode());
//			frOp.setOrderSeqNo(fDto.getOrderSeqNo());
//			Acct acct = acctDAO.queryAcctWithAcctCode(frOp.getAcctCode());
//			frOp.setBalance(BigDecimal.valueOf(acct.getBalance()));
//			frOpDtos.add(frOp);
//		}
//		try {
//			return frozenOperatorLogDao.batchAddFrozenLog(frOpDtos);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	@Override
//	public List<Long> batchUnFrozenLog(
//			List<UnFrozenAmountDto> unFrozenAmountDtos) {
//		List<FrozenOperatorLog> frOpDtos = new ArrayList<FrozenOperatorLog>();
//		for(UnFrozenAmountDto fDto: unFrozenAmountDtos){
//			FrozenOperatorLog frOp = new FrozenOperatorLog();
//			frOp.setFrozenType(FrozenOperatorLog.FROZEN_TYPE_UNFROZEN);//解冻
//			frOp.setMemberCode(fDto.getMemberCode());
//			frOp.setAmount(fDto.getUnFrozenAmount());
//			frOp.setAcctCode(fDto.getAcctCode());
//			frOp.setOrderSeqNo(fDto.getOrderSeqNo());
//			frOp.setBalance(BigDecimal.valueOf(acct.getBalance()));
//			frOpDtos.add(frOp);
//		}
//		try {
//			return frozenOperatorLogDao.batchAddFrozenLog(frOpDtos);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	@Override
	public Long addFrozenLog(FrozenAmountDto fDto,BigDecimal oldBlance) {
		FrozenOperatorLog frOp = new FrozenOperatorLog();
		frOp.setFrozenType(FrozenOperatorLog.FROZEN_TYPE_FROZEN);//解冻
		frOp.setMemberCode(fDto.getMemberCode());
		frOp.setAmount(fDto.getFrozenAmount());
		frOp.setAcctCode(fDto.getAcctCode());
		frOp.setOrderSeqNo(fDto.getOrderSeqNo());
		frOp.setBalance(oldBlance);
		return (Long) frozenOperatorLogDao.create(frOp);
	}

	@Override
	public Long addUnFrozenLog(UnFrozenAmountDto fDto,BigDecimal oldBlance) {
		FrozenOperatorLog frOp = new FrozenOperatorLog();
		frOp.setFrozenType(FrozenOperatorLog.FROZEN_TYPE_UNFROZEN);//解冻
		frOp.setMemberCode(fDto.getMemberCode());
		frOp.setAmount(fDto.getUnFrozenAmount());
		frOp.setAcctCode(fDto.getAcctCode());
		frOp.setOrderSeqNo(fDto.getOrderSeqNo());
		frOp.setBalance(oldBlance);
		if(fDto.getOrderSeqNo()!=null && fDto.getOrderSeqNo().startsWith("300") ){
			frOp.setValue1("poss");
		}
		return (Long) frozenOperatorLogDao.create(frOp);
	}

}
