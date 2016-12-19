/**
 * SingleBankDataLoader.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.hnapay.fi.dto.batchrepair.api.BankOrderDTO;

/**
 * 单笔银行数据查询接口， 因为是单笔接口，所以需要提供一批数据，共单笔接口多次调用 latest modified time : 2011-8-23
 * 下午2:51:09
 * 
 * @author bigknife
 */
public abstract class SingleBankOrderLoader extends AbstractBaseBankOrderLoader {
	private int maxThreadSize = 100;

	/**
	 * 最大线程数，默认为100
	 * 
	 * @param maxThreadSize
	 *            the maxThreadSize to set
	 */
	public void setMaxThreadSize(int maxThreadSize) {
		this.maxThreadSize = maxThreadSize;
	}

	@Override
	protected final List<Map<String, String>> getLoadParametersList() {
		return loadManyParameterMaps();
	}

	/**
	 * 批量查询的请求参数
	 * 
	 * @return
	 */
	protected abstract List<Map<String, String>> loadManyParameterMaps();

	@Override
	protected List<BankOrderDTO> multiThreadLoad(
			List<Map<String, String>> parametersList) {
		if (parametersList == null || parametersList.size() == 0) {
			return null;
		}

		int threadSize = parametersList.size() > maxThreadSize ? maxThreadSize
				: parametersList.size();
		ExecutorService execService = Executors.newFixedThreadPool(threadSize);
		List<Future<List<BankOrderDTO>>> futureList = new ArrayList<Future<List<BankOrderDTO>>>();

		for (final Map<String, String> param : parametersList) {
			Future<List<BankOrderDTO>> future = execService
					.submit(new Callable<List<BankOrderDTO>>() {

						@Override
						public List<BankOrderDTO> call() throws Exception {
							return singleThreadLoad(param);
						}
					});

			futureList.add(future);
		}
		List<BankOrderDTO> result = new ArrayList<BankOrderDTO>();
		for (Future<List<BankOrderDTO>> fulture : futureList) {
			try {
				List<BankOrderDTO> tmp = null;
				tmp = fulture.get();
				if (tmp != null) {
					result.addAll(tmp);
				}

				// TODO: 如果出异常，是不是忽略？正常请求的返回
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		execService.shutdownNow();
		return result;

	}
}
