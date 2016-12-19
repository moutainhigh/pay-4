package com.pay.pricingstrategy.service;

import java.util.Date;
import java.util.List;

import com.pay.pricingstrategy.dto.PricingStrategyDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.model.PricingStrategyDetailReport;
import com.pay.pricingstrategy.service.impl.CalPriceFeeResponse;


public interface PricingStrategyService {

	/**
	 * 根据计算价格策略的参数得到费用信息。
	 * 费用精确到厘，对于厘后还有小数统一采用ObjectUtil.formatFee(fee)进行处理。
	 * <li>该方法会按照先会员，再会员服务等级，再全局的优先顺序，查找价格策略进行算费。</li>
	 * <li>如果没有找到对应的价格策略，则返回0。</li>
	 * @param calParam 算费的价格策略参数
	 * @return 费用
	 */
	Long calculatePrice(CalPricingStrategyParam calParam);
	
	
	/**
	 * 得到计算的费用以及调用的价格策略CLODE
	 * @param calParam
	 * @return
	 */
	 CalPriceFeeResponse calculatePriceToResponse(CalPricingStrategyParam calParam);

	
	/**
	 * 取得支付服务代码对应的支付服务下的所有价格策略。
	 * @param paymentSeriviceCode 支付服务代码
	 * @return 价格策略列表
	 */
	List<PricingStrategyDTO> getAllPricingStrategyByPSC(
			Integer paymentSeriviceCode);
	
	/**
	 * 取渠道结算手续费率。
	 * @param paymentSeriviceCode 支付服务代码
	 * @return 价格策略列表
	 */
	List<PricingStrategyDetailReport> getAllPricingStrategyDetailByPSC(
			Integer paymentSeriviceCode);
	
	
	/**
	 * 取得价格策略代码对应得价格策略。
	 * @param pricingStrategyCode 价格策略代码
	 * @return 价格策略
	 */
	PricingStrategyDTO getPricingStrategy(Long pricingStrategyCode);
	
	/**
	 * 更改一个价格策略。
	 * <li>该方法会首先验证要修改得价格策略得数据合法性。</li>
	 * <li>如果通过验证，将修改这条价格策略。</li>
	 * <li>否则，失败。</li>
	 * @param pricingStrategyDTO 价格策略
	 * @return 更改后的价格策略
	 */
	PricingStrategyDTO changePricingStrategy(
			PricingStrategyDTO pricingStrategyDTO);
	
	/**
	 * 删除价格策略代码对应得价格策略。
	 * <li>该方法只能删除未生效得价格策略。</li>
	 * <li>否则，会抛出异常</li>
	 * @param pricingStrategyCode 价格策略代码
	 * @exception RemoveException 删除异常
	 */
	void removePricingStrategy(Long pricingStrategyCode);

	
	/**
	 * 增加保存一个价格策略。
	 * <li>该方法会首先验证要新增得价格策略得数据合法性。</li>
	 * <li>如果通过验证，将新增一条价格策略。</li>
	 * <li>否则，失败。</li>
	 * @param pricingStrategyDTO 价格策略
	 * @return 保存后的价格策略
	 */
	PricingStrategyDTO addPricingStrategy(PricingStrategyDTO pricingStrategyDTO);	

	/**
	 * 查找价格策略明细。
	 * 参数paymentServiceCode对应得支付服务中，属于参数memberCode对应得会员。
	 * <li>该方法会首先根据支付服务代码，特定时间，查找特定会员对应得价格策略明细。</li>
	 * <li>如果没有，则根据支付服务代码，特定时间，查找特定会员在该特定时间得服务等级对应得价格策略明细。</li>
	 * <li>如果没有，则根据支付服务代码，特定时间，查找全局得价格策略明细。</li>
	 * <li>如果没有，系统将抛出异常。</li>
	 * @param memberCode 会员代码
	 * @param paymentServiceCode 支付服务代码
	 * @param transactiondate 会计日期
	 * @return 价格策略明细
	 */
	public PricingStrategyDetailDTO getPricingStrategyDetail(
			Long memberCode,String paymentServiceCode,Date transactiondate);
	
	
	
	/**
	 * 增加保存一个价格策略明细
	 * <li>该方法会首先验证要新增得价格策略明细得数据合法性。</li>
	 * <li>如果通过验证，将新增一条价格策略明细。</li>
	 * <li>否则，失败。</li>
	 * @param pricingDetailDTO 价格策略明细
	 * @return 保存后的价格策略明细
	 */
	PricingStrategyDetailDTO addPricingStrategyDetail(
			PricingStrategyDetailDTO pricingDetailDTO);

	/**
	 * 更改一个价格策略明细。
	 * <li>该方法会首先验证要修改得价格策略明细得数据合法性。</li>
	 * <li>如果通过验证，将修改这条价格策略明细。</li>
	 * <li>否则，失败。</li>
	 * @param pricingDetailDTO 价格策略明细
	 * @return 更改后的价格策略明细
	 */
	PricingStrategyDetailDTO changePricingStrategyDetail(
			PricingStrategyDetailDTO pricingDetailDTO);

	/**
	 * 删除多个价格策略明细.
	 * @param pricingStrategyDetailCode 价格策略明细代码数组
	 */
	void removeMulPricingStrategyDetail(String[] pricingStrategyDetailCode);

	/**
	 * 删除价格策略明细代码对应得价格策略明细.
	 * @param pricingStrategyDetailCode 价格策略明细代码
	 */
	void removePricingStrategyDetail(Long pricingStrategyDetailCode);
	
	/**
	 * 取得价格策略明细代码对应得价格策略明细。
	 * @param pricestrategydetailcode 价格策略明细代码
	 * @return 价格策略明细
	 */
	PricingStrategyDetailDTO getPricingStrategyDetail(
			Long pricestrategydetailcode);
	
	
	public List<PricingStrategyDetailDTO> getPricingStrategyDetailByCode(
			final Long pricingStrategyCode);
	
	/**
	 * 删除多个价格策略.
	 * <li>该方法只能删除未生效得价格策略。</li>
	 * <li>否则，会抛出异常</li>
	 * @param pricingStrategyCode 架构策略代码数组
	 * @exception RemoveException 删除异常
	 */
	void removeMulPricingStrategy(String[] pricingStrategyCode);
	
	 Long getPricingStrategyCode(CalPricingStrategyParam calParam);
	
	
}
