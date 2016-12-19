package com.pay.fo.order.service.common;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.RecentPayeeDTO;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.PayToAcctOrderDTO;
import com.pay.inf.dao.BaseDAO;

public class CommonlyUsedContactsService {

	protected transient Log log = LogFactory.getLog(getClass());

	protected BaseDAO iBaseDao;// 数据库操作

	public void savePay2AcctCommonlyUsedContacts(PayToAcctOrderDTO pay2AcctDTO) {
		try {

			RecentPayeeDTO dto = new RecentPayeeDTO();
			dto.setOrderId(pay2AcctDTO.getOrderId());
			dto.setPayeeName(pay2AcctDTO.getPayeeName());
			dto.setTradeType(pay2AcctDTO.getTradeType());
			dto.setPayeeLoginname(pay2AcctDTO.getPayeeLoginName());
			dto.setType(2);
			dto.setPayerMembercode(pay2AcctDTO.getPayerMemberCode());
			dto.setCreateDate(new Date());
			List<RecentPayeeDTO> list = queryCommonlyUsedContacts(dto);
			if (list.size() <= 0) {
				iBaseDao.create("FO_FUNDOUT_COMMONLY_USED_CONTACTS.create", dto);
				dto.setPayeeLoginname(null);
				List<RecentPayeeDTO> countList = queryCommonlyUsedContacts(dto);
				if (countList.size() > 10) {
					deleteCommonlyUsedContacts(dto);
				}
			}
		} catch (Throwable e) {
			log.error(e);
		}
	}

	public void savePay2BankCommonlyUsedContacts(FundoutOrderDTO pay2BankDTO) {
		try {

			RecentPayeeDTO dto = new RecentPayeeDTO();
			dto.setOrderId(pay2BankDTO.getOrderId());
			dto.setPayeeName(pay2BankDTO.getPayeeName());
			dto.setTradeType(pay2BankDTO.getTradeType());
			dto.setPayeeBankCode(pay2BankDTO.getPayeeBankCode());
			dto.setPayeeBankName(pay2BankDTO.getPayeeBankName());
			dto.setPayeeBankprovince(pay2BankDTO.getPayeeBankProvince());
			dto.setPayeeBankcity(pay2BankDTO.getPayeeBankCity());
			dto.setPayeeOpeningbankname(pay2BankDTO.getPayeeOpeningBankName());
			dto.setPayeeBankacctcode(pay2BankDTO.getPayeeBankAcctCode());
			dto.setType(1);
			dto.setPayerMembercode(pay2BankDTO.getPayerMemberCode());
			dto.setCreateDate(new Date());
			List<RecentPayeeDTO> list = queryCommonlyUsedContacts(dto);
			if (list.size() <= 0) {
				iBaseDao.create("FO_FUNDOUT_COMMONLY_USED_CONTACTS.create", dto);
				dto.setPayeeBankacctcode(null);
				List<RecentPayeeDTO> countList = queryCommonlyUsedContacts(dto);
				if (countList.size() > 10) {
					deleteCommonlyUsedContacts(dto);
				}
			}
		} catch (Throwable e) {
			log.error(e);
		}
	}

	public List<RecentPayeeDTO> queryCommonlyUsedContacts(RecentPayeeDTO dto) {
		return iBaseDao.findByQuery(
				"FO_FUNDOUT_COMMONLY_USED_CONTACTS.queryContacts", dto);
	}

	public boolean deleteCommonlyUsedContacts(Long contactsId) {
		return iBaseDao.delete(
				"FO_FUNDOUT_COMMONLY_USED_CONTACTS.deleteByPrimaryKey",
				contactsId);
	}

	public boolean deleteCommonlyUsedContacts(RecentPayeeDTO dto) {
		return iBaseDao.delete(
				"FO_FUNDOUT_COMMONLY_USED_CONTACTS.deleteByRownum", dto);
	}

	public void setiBaseDao(BaseDAO<Object> iBaseDao) {
		this.iBaseDao = iBaseDao;
	}
}
