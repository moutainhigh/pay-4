/**
 * 
 */
package com.pay.fo.order.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.fo.order.dto.Order;
import com.pay.inf.exception.AppException;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public abstract class AbstractOrderCallbackService implements
		OrderCallbackService {

	protected NotifyFacadeService notifyFacadeService;
	protected AcctAttribService acctAttribService;

	protected final Log log = LogFactory.getLog(getClass());

	@Override
	public void processRdTx(Order order, AccountingService accountingService)
			throws AppException {
		try {
			if (null == accountingService) {
				log.info("当前请求处理类型为【" + order.getProcessType().getDesc()
						+ "】，将不进行更新余额操作。");
				return;
			}

			// 更新余额
			if (1 == accountingService
					.doAccountingReturn(buildAccountingDto(order))) {
				
				log.info("当前请求处理类型为【" + order.getProcessType().getDesc()
						+ "】，更新余额成功。");
			} else {
				throw new RuntimeException("更新余额失败");
			}

			// 更新订单状态
			if (!updateOrderStatus(order)) {
				throw new RuntimeException("更新订单状态失败");
			}

		} catch (Throwable e) {
			log.error("更新余额、更新订单状态失败或发生异常", e);
			throw new AppException("更新余额、更新订单状态失败或发生异常");
		}
	}

	/**
	 * 构建通用记账对象
	 * 
	 * @param order
	 * @return
	 */
	protected abstract AccountingDto buildAccountingDto(Order order);

	/**
	 * 格式化SMS日期
	 * 
	 * @param date
	 * @return
	 */
	protected String formatSMSDate(Date date) {
		DateFormat df = new SimpleDateFormat("MM月dd日HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 格式化Email日期
	 * 
	 * @param date
	 * @return
	 */
	protected String formatEmailDate(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 获取银行卡号后四位
	 * 
	 * @param bankAccount
	 * @return
	 */
	protected String getRequireBankAccount(String bankAccount) {
		if (bankAccount.length() <= 4) {
			return bankAccount;
		}
		return bankAccount.substring(bankAccount.length() - 4);
	}

	/**
	 * 验证是否是Email
	 * 
	 * @param userId
	 * @return
	 */
	protected boolean isEmail(String userId) {
		if (StringUtil.null2String(userId).indexOf("@") > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param notifyFacadeService
	 *            the notifyFacadeService to set
	 */
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public AcctAttribService getAcctAttribService() {
		return acctAttribService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

}
