/**
 *  <p>File: RefundAbstractController.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.fo.controller.refund;

import java.util.List;
import java.util.Map;

import com.pay.poss.service.fi.input.FiBankFacadeService;
import com.pay.poss.service.inf.input.BankInfoFacadeService;

public abstract class RefundAbstractController extends BaseController {

	private FiBankFacadeService bankInfoService;// 网关银行接口
	private BankInfoFacadeService bankFacadeService;// inf银行接口
	private static List<Map<String, String>> bankList;
	private static List<Map<String, String>> bankListInf;

	public void setBankInfoService(FiBankFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	public void setBankFacadeService(BankInfoFacadeService bankFacadeService) {
		this.bankFacadeService = bankFacadeService;
	}

	/**
	 * return bank list
	 * 
	 * @return
	 */
	protected List<Map<String, String>> getBankList() {

		if (null == bankList || bankList.isEmpty()) {
			bankList = bankInfoService.getFiBankList();
		}
		return bankList;
	}

	/**
	 * return bank list
	 * 
	 * @return
	 */
	protected List<Map<String, String>> getBankListInf() {

		if (null == bankListInf || bankListInf.isEmpty()) {
			bankListInf = bankFacadeService.getAllBankList();
		}
		return bankListInf;
	}
}
