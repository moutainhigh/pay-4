package com.pay.fundout.withdraw.service.bankcorporateexpress;

import java.util.List;

import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressReqDTO;
import com.pay.fundout.withdraw.dto.bankcorporateexpress.BankCorporateExpressResDTO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;

public interface BankCorporateExpressService {
	/**
	 * 查询指定条件的所有交易数据
	 * @param reqDTO 查询条件
	 * @return 所有数据的集合
	 */
	List<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(BankCorporateExpressReqDTO reqDTO);
	
	Page<BankCorporateExpressResDTO> bankCorporateExpressFailQuery(Page<BankCorporateExpressResDTO> page, BankCorporateExpressReqDTO reqDTO);

	Page<BankCorporateExpressResDTO> bankCorporateExpressReAuditQuery(Page<BankCorporateExpressResDTO> page, BankCorporateExpressReqDTO reqDTO);

	/**
	 * 确认失败
	 * 
	 * @param workorders
	 * @return
	 * @throws PossException
	 */
	boolean confirmFailRdTx(String workorders) throws PossException;

	/**
	 * 复核通过
	 * 
	 * @param workorders
	 * @return
	 * @throws PossException
	 */
	boolean reAuditPass(String workorders) throws PossException;

	/**
	 * 复核拒绝
	 * 
	 * @param workorders
	 * @return
	 * @throws PossException
	 */
	boolean reAuditRejectRdTx(String workorders) throws PossException;
}
