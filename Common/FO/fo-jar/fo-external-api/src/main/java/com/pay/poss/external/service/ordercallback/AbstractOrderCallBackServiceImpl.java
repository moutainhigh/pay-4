package com.pay.poss.external.service.ordercallback;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.common.accounting.AccountingResult;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.innerback.BackFundingInnerService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterFailProcHandler;

/**
 * 订单后处理回调服务
 * 
 * @author :rick_lv
 * @version :2010-10-25 V1.0
 */
public abstract class AbstractOrderCallBackServiceImpl implements OrderCallBackService {
	protected Log log = LogFactory.getLog(getClass());
	protected BaseDAO daoService;
	protected BackFundingInnerService backFundingInnerService;
	// 失败处理器，目前该处理器默认实现为写入预警表
	private OrderAfterFailProcHandler failHandler;
	private Set<String> temp = new HashSet<String>(3);

	@Override
	public void processOrderRdTx(HandlerParam param, AccountingService accountingService) throws PossException {
		// 订单更新
		if (changeOrderStatus(param) == false) {
			log.error("订单处理失败 [" + param.getBaseOrderDto() + "]");
			failHandler.execute(buildAlertInfo(param));// 预警处理
			return;
		}
		// 记账
		if(null == accountingService){
			//TODO
			return;
		}
		if (doAccounting(param, accountingService)) {
			param.getBaseOrderDto().setInnerStatus(Constants.ORDER_STATUS_SUCC);
			notify(param);
		} else {
			param.getBaseOrderDto().setInnerStatus(Constants.ORDER_STATUS_FAIL);
			throw new PossException("余额更新失败 [" + param.getBaseOrderDto() + "]", ExceptionCodeEnum.ACCTOUNTING_PROCESS_EXCEPTION);
		}
	}
	
	public void processCancelOrderRdTx(HandlerParam param) throws PossException {
		BackFundmentOrder backFundOrder = buildBackOrder(param);
		doCancelAccounting(backFundOrder);
	}

	public void processCancelOrderRnTx(HandlerParam param) throws PossException {
		// 因为不相信能保证都处理异常，所以最好捕获
		try {
			BackFundmentOrder backFundOrder = buildBackOrder(param);
			doCancelAccounting(backFundOrder);
		} catch (Exception e) {
			log.error("退款操作失败 [" + param.getBaseOrderDto() + "]", e);
			// 预警处理
			failHandler.execute(buildAlertInfo(param));
		}
	}

	/**
	 * 转换原订单对象成预警信息
	 * 
	 * @param param
	 *            原订单对象
	 * @return
	 */
	protected abstract OrderFailProcAlertModel buildAlertInfo(HandlerParam param);

	/**
	 * 转换原订单对象成退款订单
	 * 
	 * @param param
	 *            原订单对象
	 * @return
	 */
	protected abstract BackFundmentOrder buildBackOrder(HandlerParam param);

	/**
	 * 更新订单状态
	 * 
	 * @param param
	 *            待更新订单对象
	 * @return 如果更新成功返回true
	 */
	protected abstract boolean changeOrderStatus(HandlerParam param);

	/**
	 * 退款操作
	 * 
	 * @param backFundOrder
	 *            退款订单对象
	 * @return 退款是否成功
	 */
	protected abstract void doCancelAccounting(BackFundmentOrder backFundOrder) throws PossException;



	/**
	 * 调用记账服务
	 * 
	 * @param dto
	 *            记账对象
	 * @param accountingService
	 *            记账服务
	 * @return boolean true表示记账成功,false表示记账失败
	 */
	protected boolean doAccounting(HandlerParam param, AccountingService accountingService) {
		return checkAccountingResult(accountingService.doAccountingReturn(param.getAccountingDto()));
	}

	// 验证记账结果成功或失败
	private boolean checkAccountingResult(Integer accountingResult) {
		if (AccountingResult.ACCOUNTING_FAIL.getResult() == accountingResult) {
			return false;
		} else if (AccountingResult.ACCOUNTING_SUCC.getResult() == accountingResult) {
			return true;
		} else {
			return false;
		}
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	public void setFailHandler(OrderAfterFailProcHandler failHandler) {
		this.failHandler = failHandler;
	}

	public void setBackFundingInnerService(BackFundingInnerService backFundingInnerService) {
		this.backFundingInnerService = backFundingInnerService;
	}

}
