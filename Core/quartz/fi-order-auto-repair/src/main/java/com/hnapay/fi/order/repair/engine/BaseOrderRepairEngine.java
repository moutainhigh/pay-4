/**
 * BaseOrderRepairEngine.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hnapay.fi.dto.batchrepair.api.BatchBankOrderDTO;
import com.hnapay.fi.dto.batchrepair.api.BatchRepairApplyResDTO;
import com.hnapay.fi.dto.batchrepair.api.BatchRepairCompareResDTO;
import com.hnapay.fi.exception.BusinessException;
import com.hnapay.fi.order.repair.engine.exception.ConnectException;
import com.hnapay.fi.service.batchrepair.api.BatchRepairLoadServiceApi;
import com.hnapay.fi.service.batchrepair.api.BatchRepairServiceApi;

/**
 * 
 * latest modified time : 2011-8-22 上午11:44:28
 * 
 * @author bigknife
 */
public class BaseOrderRepairEngine implements IOrderRepairEngine {
	private IBankOrderLoader bankDataLoader;
	private BatchRepairLoadServiceApi loadService;
	private BatchRepairServiceApi repairService;
	

	private Log log = LogFactory.getLog(getClass());

	/**
	 * @param loadService
	 *            the loadService to set
	 */
	public void setLoadService(BatchRepairLoadServiceApi loadService) {
		this.loadService = loadService;
	}

	/**
	 * @param repairService
	 *            the repairService to set
	 */
	public void setRepairService(BatchRepairServiceApi repairService) {
		this.repairService = repairService;
	}

	/**
	 * @param bankDataLoader
	 *            the bankDataLoader to set
	 */
	public void setBankDataLoader(IBankOrderLoader bankDataLoader) {
		this.bankDataLoader = bankDataLoader;
	}

	@Override
	public BatchBankOrderDTO loadBankOrders() throws ConnectException {
		return bankDataLoader.load();
	}

	@Override
	public String createBatch(BatchBankOrderDTO batch) {
		if (loadService != null) {
			if(batch != null){
				try {
					return loadService.createBatchRnTx(batch);
				} catch (BusinessException e) {
					log.error("无法创建补单批次。",e);
					return null;
				}
			}
			log.warn("batch is null, so no batch can't be created.");
			return null;
		} else {
			log.warn("no BatchRepairLoadServiceApi implementation set ");
			return null;
		}
	}

	@Override
	public List<BatchRepairCompareResDTO> compare(String batchId) {
		return repairService.compare(batchId);
	}

	@Override
	public List<BatchRepairApplyResDTO> applyToRepair(List<BatchRepairCompareResDTO> resultList) {
		try {
			return repairService
					.applyToRepairRnTx(IOrderRepairEngine.OPERAOTR, resultList);
		} catch (BusinessException e) {
			log.error("创建补单申请失败。",e);
			return null;
		}
		
	}

	@Override
	public void doRepair(
			List<BatchRepairApplyResDTO> batchRepairApplyReslist) {
		try {
			repairService
					.doRepair(IOrderRepairEngine.OPERAOTR, batchRepairApplyReslist);
		} catch (BusinessException e) {
			log.error("补单审核失败。",e);
		}
		
	}

}
