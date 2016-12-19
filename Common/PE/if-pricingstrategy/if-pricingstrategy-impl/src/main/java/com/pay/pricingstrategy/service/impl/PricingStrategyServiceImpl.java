package com.pay.pricingstrategy.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.pricingstrategy.dao.PricingStrategyDAO;
import com.pay.pricingstrategy.dto.PricingStrategyDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDTOUtil;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTO;
import com.pay.pricingstrategy.dto.PricingStrategyDetailDTOUtil;
import com.pay.pricingstrategy.helper.CACULATEMETHOD;
import com.pay.pricingstrategy.helper.EFFECTIVEON;
import com.pay.pricingstrategy.helper.PRICESTRATEGYTYPE;
import com.pay.pricingstrategy.model.PricingStrategy;
import com.pay.pricingstrategy.model.PricingStrategyDetail;
import com.pay.pricingstrategy.model.PricingStrategyDetailReport;
import com.pay.pricingstrategy.service.CalPriceFactory;
import com.pay.pricingstrategy.service.CalPriceInnerParam;
import com.pay.pricingstrategy.service.CalPricingStrategyParam;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.util.DateRange;
import com.pay.util.MfDate;
import com.pay.util.NumberUtil;
import com.pay.util.ObjectUtil;
import com.pay.util.StringUtil;

public class PricingStrategyServiceImpl implements PricingStrategyService {

	private static Log log = LogFactory
			.getLog(PricingStrategyServiceImpl.class);
	private CalPriceFactory calPriceFactory;
	private PricingStrategyDAO pricingStrategyDAO;

	/**
	 * 根据计算价格策略的参数得到费用信息。 费用精确到厘，对于厘后还有小数统一采用ObjectUtil.formatFee(fee)进行处理。 <li>
	 * 该方法会按照先会员，再会员服务等级，再全局的优先顺序，查找价格策略进行算费。</li> <li>如果没有找到对应的价格策略，则返回0。</li>
	 * 
	 * @param calParam
	 *            算费的价格策略参数
	 * @return 费用
	 */
	public Long calculatePrice(CalPricingStrategyParam calParam) {
		Assert.isTrue(
				calParam.getTransactionAmount() != null
						&& calParam.getTransactionAmount() >= 0,
				"[Transaction Amount] must greater than 0");
		Assert.notNull(calParam.getPaymentServiceCode(),
				"]Payment Service Code] must not be null");
		Assert.notNull(calParam.getMfDatetime(), "[MF Date] must not be null");

		Long fee = this.calculatePriceDetail(calParam.getTransactionAmount(),
				calParam.getPaymentServiceCode(),
				calParam.getServiceLevelCode(), calParam.getMemberCode(),
				calParam.getTerminaltype(), calParam.getMfDatetime(),
				calParam.getReservedCode()).getFee();
		return fee;
	}

	@Override
	public CalPriceFeeResponse calculatePriceToResponse(
			CalPricingStrategyParam calParam) {
		Assert.isTrue(
				calParam.getTransactionAmount() != null
						&& calParam.getTransactionAmount() >= 0,
				"[Transaction Amount] must greater than 0");
		Assert.notNull(calParam.getPaymentServiceCode(),
				"]Payment Service Code] must not be null");
		Assert.notNull(calParam.getMfDatetime(), "[MF Date] must not be null");
		return this.calculatePriceDetail(calParam.getTransactionAmount(),
				calParam.getPaymentServiceCode(),
				calParam.getServiceLevelCode(), calParam.getMemberCode(),
				calParam.getTerminaltype(), calParam.getMfDatetime(),
				calParam.getReservedCode());
	}

	/**
	 * @计算费用
	 * @param transactionAmount
	 *            Long
	 * @param paymentServiceCode
	 *            Integer
	 * @param serviceLevelCode
	 *            Integer
	 * @param memberCode
	 *            Long
	 * @param terminaltype
	 *            Integer 终端类型
	 * @param mfDatetime
	 *            MfDateTime
	 * @param reservedCode
	 *            String 扩展代码.
	 * @return Long
	 */
	private CalPriceFeeResponse calculatePriceDetail(
			final Long transactionAmount, final Integer paymentServiceCode,
			final Integer serviceLevelCode, final Long memberCode,
			final Integer terminaltype, final Date mfDatetime,
			final String reservedCode) {

		CalPriceInnerParam param = new CalPriceInnerParam();
		param.setTransactionAmount(transactionAmount);
		param.setMfDateTime(mfDatetime);
		param.setTerminltype(terminaltype);
		param.setReservedCode(reservedCode);
		// 增加得到累计金额的参数
		param.setMemberCode(memberCode);
		param.setPaymentServiceCode(paymentServiceCode);

		CalPriceFeeResponse reponse;

		// PricingStrategyDTO pricingStrategyDto = this.getPricingStrategyDtO(
		// paymentServiceCode, serviceLevelCode, memberCode, mfDatetime);
		PricingStrategyDTO pricingStrategyDto = this.getPricingStrategyDtO(
				paymentServiceCode, serviceLevelCode, memberCode, mfDatetime);
		if (pricingStrategyDto != null) {
			param.setPricingStrategyDto(pricingStrategyDto);
			reponse = calPriceFactory.getCalPrice(
					pricingStrategyDto.getCaculateMethod()).calPriceDetail(
					param);
			if (null != reponse) {

				// 价格策略CODE
				reponse.setPriceStrategyCode(pricingStrategyDto
						.getPriceStrategyCode());
				return reponse;
			}
		}
		// 没有价格策略
		throw new RuntimeException("没有价格策略");
	}

	public Long getPricingStrategyCode(CalPricingStrategyParam calParam) {
		Integer paymentServiceCode = calParam.getPaymentServiceCode();
		Integer serviceLevelCode = calParam.getServiceLevelCode();
		Long memberCode = calParam.getMemberCode();
		Date mfDatetime = calParam.getMfDatetime();
		PricingStrategyDTO dto = this.getPricingStrategyDtO(paymentServiceCode,
				serviceLevelCode, memberCode, mfDatetime);
		if (null != dto) {
			return dto.getPriceStrategyCode();
		}

		return null;
	}

	/**
	 * @得到具体的价格策略
	 * @param paymentServiceCode
	 *            Integer
	 * @param serviceLevelCode
	 *            Integer
	 * @param memberCode
	 *            Long
	 * @param mfdate
	 *            MfDate
	 * @return PricingStrategyDTO
	 */
	private PricingStrategyDTO getPricingStrategyDtO(
			final Integer paymentServiceCode, final Integer serviceLevelCode,
			final Long memberCode, final Date date) {
		List<PricingStrategyDTO> pricingStrategyDtoList = null;
		// 从memberCode中找价格策略
		if (null != memberCode) {
			log.info("Finding pricing strategy from memberCode");
			pricingStrategyDtoList = this.getPricingStrategyByMember(
					paymentServiceCode, memberCode, date);
			if (pricingStrategyDtoList != null
					&& pricingStrategyDtoList.size() == 1) {
				PricingStrategyDTO result = pricingStrategyDtoList.get(0);
				log.info("Found pricing strategy from memberCode");
				return result;
			}
		}
		// 从ServiceLevel中找价格策略
		if (null != serviceLevelCode) {
			log.info("Finding pricing strategy from serviceLevelCode");
			pricingStrategyDtoList = this.getPricingStrategyByServiceLevel(
					paymentServiceCode, serviceLevelCode, date);
			if (pricingStrategyDtoList != null
					&& pricingStrategyDtoList.size() == 1) {
				PricingStrategyDTO result = pricingStrategyDtoList.get(0);
				log.info("Found pricing strategy from serviceLevelCode");
				return result;
			}
		}
		// 从全局中找价格策略
		// if (memberCode == null && serviceLevelCode == null) {
		log.info("Finding pricing strategy from Global");
		System.out.println("---------------paymentServiceCode="
				+ paymentServiceCode + ";date=" + date);
		pricingStrategyDtoList = this.getPricingStrategyGlobal(
				paymentServiceCode, date);
		if (pricingStrategyDtoList != null
				&& pricingStrategyDtoList.size() == 1) {
			PricingStrategyDTO result = pricingStrategyDtoList.get(0);
			log.info("Found pricing strategy from Global");
			return result;
		}
		// }
		if (null == pricingStrategyDtoList
				|| pricingStrategyDtoList.size() == 0)
			throw new RuntimeException(
					"PricingStrategy is not exists paymentServiceCode="
							+ paymentServiceCode);
		else
			throw new RuntimeException(
					"PricingStrategy size  > 1 paymentServiceCode="
							+ paymentServiceCode);
		// throw new RuntimeException("PricingStrategy is not  exists or > 1");
	}

	public List<PricingStrategyDTO> getPricingStrategyByMember(
			final Integer paymentServiceCode, final Long memberCode,
			final Date date) {
		Assert.notNull(paymentServiceCode,
				"paymentServiceCode must be not null");
		Assert.notNull(memberCode, "memberCode must be not null");

		List<PricingStrategy> resultList = this.pricingStrategyDAO
				.getPricingStrategyByMember(paymentServiceCode, memberCode,
						date);

		return PricingStrategyDTOUtil
				.convertToPricingStrategyDTOList(resultList);
	}

	public List<PricingStrategyDTO> getPricingStrategyByServiceLevel(
			final Integer paymentServiceCode, final Integer serviceLevelCode,
			final Date mfdate) {
		Assert.notNull(paymentServiceCode,
				"paymentServiceCode must be not null");
		Assert.notNull(serviceLevelCode, "serviceLevelCode must be not null");
		// Assert.notNull(mfdate, "date must be not null"); 0110

		List<PricingStrategy> resultList = this.pricingStrategyDAO
				.getPricingStrategyByServiceLevel(paymentServiceCode,
						serviceLevelCode, mfdate);

		return PricingStrategyDTOUtil
				.convertToPricingStrategyDTOList(resultList);
	}

	@SuppressWarnings("unchecked")
	public List<PricingStrategyDTO> getPricingStrategyGlobal(
			final Integer paymentServiceCode, final Date mfdate) {
		Assert.notNull(paymentServiceCode,
				"paymentServiceCode must be not null");
		// Assert.notNull(mfdate, "date must be not null"); 0110

		List resultList = this.pricingStrategyDAO.getAllGlobalPricingStrategy(
				paymentServiceCode, mfdate);

		return PricingStrategyDTOUtil
				.convertToPricingStrategyDTOList(resultList);
	}

	public CalPriceFactory getCalPriceFactory() {
		return calPriceFactory;
	}

	public void setCalPriceFactory(CalPriceFactory calPriceFactory) {
		this.calPriceFactory = calPriceFactory;
	}

	public PricingStrategyDAO getPricingStrategyDAO() {
		return pricingStrategyDAO;
	}

	public void setPricingStrategyDAO(PricingStrategyDAO pricingStrategyDAO) {
		this.pricingStrategyDAO = pricingStrategyDAO;
	}

	@Override
	public List<PricingStrategyDTO> getAllPricingStrategyByPSC(
			Integer paymentSeriviceCode) {
		List<PricingStrategyDTO> resultList = PricingStrategyDTOUtil
				.convertToPricingStrategyDTOList(this.pricingStrategyDAO
						.getAllPricingStrategyByPSC(paymentSeriviceCode));
		return resultList;
	}
	@Override
	public List<PricingStrategyDetailReport> getAllPricingStrategyDetailByPSC(
			Integer paymentSeriviceCode) {
		List<PricingStrategyDetailReport> resultList = this.pricingStrategyDAO
						.getAllPricingStrategyDetailByPSC(paymentSeriviceCode);
		return resultList;
	}

	public PricingStrategyDTO getPricingStrategy(final Long pricingStrategyCode) {
		Assert.notNull(pricingStrategyCode,
				"pricingStrategyCode must be not null");

		PricingStrategyDTO dto = PricingStrategyDTOUtil
				.convertToPricingStrategyDTO(this.pricingStrategyDAO
						.getPricingStrategy(pricingStrategyCode));

		// if (dto != null && dto.getServiceLevel() != null) {
		// ServiceLevel level = this.serviceLevelDao.getServiceLevelByCode(dto
		// .getServiceLevel());
		// if (level != null) {
		// dto.setServiceLevelDesc(level.getServicelevelname());
		// }
		// }

		return dto;
	}

	public PricingStrategyDTO changePricingStrategy(
			final PricingStrategyDTO pricingStrategyDTO) {
		this.validatePricingStradegy(pricingStrategyDTO);

		PricingStrategyDTO result = PricingStrategyDTOUtil
				.convertToPricingStrategyDTO(this.pricingStrategyDAO
						.changePricingStrategy(PricingStrategyDTOUtil
								.convertToPricingStrategy(pricingStrategyDTO)));

		// 更改
		return result;
	}

	// 删除是不是要级联删除对应的 detail
	public void removePricingStrategy(final Long pricingStrategyCode) {
		// PricingStrategyDTO dto =
		// this.getPricingStrategy(pricingStrategyCode);
		// if (dto != null && dto.getStatus().intValue() != 0) {
		// if (dto != null && dto.getStatus().intValue() != 0) {
		// this.setErrorCode(pricingStrategyCode.toString());
		// throw new RemoveException();
		// }
		this.pricingStrategyDAO.removePricingStrategy(pricingStrategyCode);
		// 增加删除detail
		List<PricingStrategyDetail> detailList = this.pricingStrategyDAO
				.getAllPricingStrategyDetailByPricingStrategyCode(pricingStrategyCode);
		for (PricingStrategyDetail detail : detailList) {
			this.removePricingStrategyDetail(detail
					.getPriceStrategyDetailCode());
		}
	}

	public void validatePricingStradegy(
			final PricingStrategyDTO pricingStrategyDTO) {
		log.info("validatePricingStradegy Start");

		Assert.notNull(pricingStrategyDTO);
		Assert.notNull(pricingStrategyDTO.getPriceStrategyName());

		PricingStrategyDTO oldDto = new PricingStrategyDTO();
		if (!StringUtil.isNull(pricingStrategyDTO.getPriceStrategyCode())) {
			oldDto = this.getPricingStrategy(pricingStrategyDTO
					.getPriceStrategyCode());
		}
		// 全局价格策略是否存在

		// 老的on为空--新增 || on新老不等 修改 || 日期不等 修改
		if (!StringUtil.isNull(pricingStrategyDTO.getEffectiveOn())
				&& pricingStrategyDTO.getEffectiveOn() == EFFECTIVEON.GLOBLE
						.getValue()
				&& (StringUtil.isNull(oldDto.getEffectiveOn())
						|| !(pricingStrategyDTO.getEffectiveOn() == oldDto
								.getEffectiveOn())
						|| !ObjectUtil.equalsContainNull(
								pricingStrategyDTO.getValidDate(),
								oldDto.getValidDate()) || !ObjectUtil
							.equalsContainNull(
									pricingStrategyDTO.getInvalidDate(),
									oldDto.getInvalidDate()))) {
			//
			if (this.isPricingStrategyGlobalExist(pricingStrategyDTO
					.getPaymentServiceCode(), new DateRange(new MfDate(
					pricingStrategyDTO.getValidDate()), new MfDate(
					pricingStrategyDTO.getInvalidDate())), pricingStrategyDTO
					.getPriceStrategyCode())) {
				log.info("validatePricingStradegy Fail globle of pricingstategy is exists");
				throw new RuntimeException("globle of pricingstategy is exists");
			}
		}

		// 指定服务等级是否存在
		if (!StringUtil.isNull(pricingStrategyDTO.getEffectiveOn())
				&& pricingStrategyDTO.getEffectiveOn() == EFFECTIVEON.SPECIFIDESERVERLEVEL
						.getValue()) {
			if (StringUtil.isNull(pricingStrategyDTO.getServiceLevelCode())) {
				log.info("validatePricingStradegy Fail specifideserverlevel  must be input");
				throw new RuntimeException(
						"specifideserverlevel  must be input");
			}
			// old作用范围为空 --新增 || 指定代码策略为空 || 新老范围不相等--修改 || getValidDate 不相等 ||
			// getInvalidDate不相等
			if (StringUtil.isNull(oldDto.getEffectiveOn())
					|| !(pricingStrategyDTO.getEffectiveOn() == oldDto
							.getEffectiveOn())
					|| !ObjectUtil.equalsContainNull(
							pricingStrategyDTO.getValidDate(),
							oldDto.getValidDate())
					|| !ObjectUtil.equalsContainNull(
							pricingStrategyDTO.getInvalidDate(),
							oldDto.getInvalidDate())) {

				//
				if (this.isPricingStrategyForServiceLevelExist(
						pricingStrategyDTO.getPaymentServiceCode(),
						pricingStrategyDTO.getServiceLevelCode(),
						new DateRange(new MfDate(pricingStrategyDTO
								.getValidDate()), new MfDate(pricingStrategyDTO
								.getInvalidDate())), pricingStrategyDTO
								.getPriceStrategyCode())) {

					log.info("validatePricingStradegy Fail specifideserverlevel of pricingstategy is exists");
					throw new RuntimeException(
							"specifideserverlevel of pricingstategy is exists");
				}
			}
		}

		// 指定会员
		if (!StringUtil.isNull(pricingStrategyDTO.getEffectiveOn())
				&& pricingStrategyDTO.getEffectiveOn() == EFFECTIVEON.SPECIFIEDMEMBER
						.getValue()) {
			if (StringUtil.isNull(pricingStrategyDTO.getMemberCode())) {
				log.info("validatePricingStradegy Fail specifiedmember  must be input");
				throw new RuntimeException("specifiedmember  must be input");
			}
			// old作用范围为空 || 指定代码策略为空 || 新老范围不相等 || getValidDate 不相等 ||
			// getInvalidDate不相等
			if (StringUtil.isNull(oldDto.getEffectiveOn())
					|| oldDto == null
					|| !(pricingStrategyDTO.getEffectiveOn() == oldDto
							.getEffectiveOn())
					|| !ObjectUtil.equalsContainNull(
							pricingStrategyDTO.getValidDate(),
							oldDto.getValidDate())
					|| !ObjectUtil.equalsContainNull(
							pricingStrategyDTO.getInvalidDate(),
							oldDto.getInvalidDate())) {

				// 指定会员策略是否存在
				if (this.isPricingStrategyForMemberExist(pricingStrategyDTO
						.getPaymentServiceCode(), pricingStrategyDTO
						.getMemberCode(), new DateRange(new MfDate(
						pricingStrategyDTO.getValidDate()), new MfDate(
						pricingStrategyDTO.getInvalidDate())),
						pricingStrategyDTO.getPriceStrategyCode())) {
					log.info("validatePricingStradegy Fail specifiedmember of pricingstategy is exists");
					throw new RuntimeException(
							"specifiedmember of pricingstategy is exists");
				}
			}
		}

		log.info("validatePricingStradegy Success");

	}

	/**
	 * 判断价格策略是否指定serviceLevel.
	 * 
	 * @param paymentServiceCode
	 *            Integer
	 * @param serviceLevelCode
	 *            Integer
	 * @param dateRange
	 *            DateRange
	 * @param pricingStrategyCode
	 *            Integer
	 * @return Boolean Integer
	 */
	private Boolean isPricingStrategyForServiceLevelExist(
			final Integer paymentServiceCode, final Integer serviceLevelCode,
			final DateRange dateRange, final Long pricingStrategyCode) {
		Assert.notNull(paymentServiceCode,
				"paymentServiceCode must be not null");
		Assert.notNull(serviceLevelCode, "serviceLevelCode must be not null");
		Assert.notNull(dateRange, "date must be not null");
		List<PricingStrategyDTO> resultList = this
				.getPricingStrategyByServiceLevel(paymentServiceCode,
						serviceLevelCode, null);
		// ------------filter
		resultList = filterByDateRange(resultList, dateRange);

		if (resultList != null) {
			for (PricingStrategyDTO dto : resultList) {
				if (!ObjectUtil.equalsContainNull(dto.getPriceStrategyCode(),
						pricingStrategyCode)) {
					return true;
				}
			}
		}
		return false;
	}

	private Boolean isPricingStrategyGlobalExist(
			final Integer paymentServiceCode, final DateRange dateRange,
			final Long pricingStrategyCode) {
		Assert.notNull(paymentServiceCode,
				"paymentServiceCode must be not null");
		Assert.notNull(dateRange, "date must be not null");
		List<PricingStrategyDTO> resultList = this.getPricingStrategyGlobal(
				paymentServiceCode, null);
		// ------------filter
		resultList = filterByDateRange(resultList, dateRange);

		if (resultList != null) {
			for (PricingStrategyDTO dto : resultList) {
				if (!ObjectUtil.equalsContainNull(dto.getPriceStrategyCode(),
						pricingStrategyCode)) {
					return true;
				}
			}
		}
		return false;
	}

	private List<PricingStrategyDTO> filterByDateRange(
			List<PricingStrategyDTO> pricingStrategyList, DateRange dateRange) {
		// 对时间进行处理
		if (!StringUtil.isNull(dateRange)) {
			List<PricingStrategyDTO> resultList = new LinkedList<PricingStrategyDTO>();
			for (PricingStrategyDTO prcingstrategy : pricingStrategyList) {
				if (StringUtil.isNull(prcingstrategy.getValidDate())) {
					continue;
				}
				DateRange currRange = new DateRange(new MfDate(
						prcingstrategy.getValidDate()), new MfDate(
						prcingstrategy.getInvalidDate()));
				if (currRange.coOverlapsSpecDateNoEnd(dateRange)) {
					resultList.add(prcingstrategy);
				}
			}
			return resultList;
		}
		return pricingStrategyList;
	}

	private Boolean isPricingStrategyForMemberExist(
			final Integer paymentServiceCode, final Long memberCode,
			final DateRange dateRange, final Long pricingStrategyCode) {
		Assert.notNull(paymentServiceCode,
				"paymentServiceCode must be not null");
		Assert.notNull(memberCode, "memberCode must be not null");
		Assert.notNull(dateRange, "date must be not null");

		List<PricingStrategyDTO> resultList = this.getPricingStrategyByMember(
				paymentServiceCode, memberCode, null);
		// ------------filter
		resultList = filterByDateRange(resultList, dateRange);
		if (resultList != null) {
			for (PricingStrategyDTO dto : resultList) {
				if (!ObjectUtil.equalsContainNull(dto.getPriceStrategyCode(),
						pricingStrategyCode)) {
					return true;
				}
			}
		}
		return false;
	}

	public PricingStrategyDTO addPricingStrategy(
			final PricingStrategyDTO pricingStrategyDTO) {
		// 验证
		this.validatePricingStradegy(pricingStrategyDTO);
		PricingStrategyDTO result = PricingStrategyDTOUtil
				.convertToPricingStrategyDTO(this.pricingStrategyDAO
						.addPricingStrategy(PricingStrategyDTOUtil
								.convertToPricingStrategy(pricingStrategyDTO)));
		return result;
	}

	@Override
	public PricingStrategyDetailDTO addPricingStrategyDetail(
			PricingStrategyDetailDTO pricingDetailDTO) {

		this.validateDetail(pricingDetailDTO);

		PricingStrategyDetailDTO result = PricingStrategyDetailDTOUtil
				.convertToPriceStrategyDetailDTO(this.pricingStrategyDAO.addPricingStrategyDetail(PricingStrategyDetailDTOUtil
						.convertToPriceStrategyDetail(pricingDetailDTO)));
		return result;
	}

	@Override
	public PricingStrategyDetailDTO changePricingStrategyDetail(
			PricingStrategyDetailDTO pricingDetailDTO) {
		this.validateDetail(pricingDetailDTO);
		PricingStrategyDetailDTO result = PricingStrategyDetailDTOUtil
				.convertToPriceStrategyDetailDTO(this.pricingStrategyDAO
						.changePricingStrategyDetail(PricingStrategyDetailDTOUtil
								.convertToPriceStrategyDetail(pricingDetailDTO)));
		return result;
	}

	@Override
	public PricingStrategyDetailDTO getPricingStrategyDetail(Long memberCode,
			String paymentServiceCode, Date transactiondate) {
		assert (transactiondate != null);
		// Integer paymentServiceCode = Integer.parseInt(sPaymentServiceCode);
		//
		// Integer pricingStrategyCode = null;
		//
		// List<PricingStrategyDTO> mbrPricingStrategies =
		// getPricingStrategyByMember(
		// paymentServiceCode, memberCode, new MfDateTime(transactiondate));
		//
		// PricingStrategyDetailDTO detailDto = null;
		// if (null != mbrPricingStrategies && mbrPricingStrategies.size() > 0)
		// {
		// // 取会员订制的费用
		// pricingStrategyCode = mbrPricingStrategies.get(0)
		// .getPricestrategycode();
		// if(pricingStrategyCode!=null){
		// detailDto =
		// getDetailByStrategyCode(pricingStrategyCode,reservedCode);
		// if(detailDto!=null){//如果没有找到价格策略明细则继续取特定服务下的价格策略
		// return detailDto;
		// }
		// }
		// }
		//
		// //取特定服务等级的价格策略
		// MemberDTO member = memberService.getMemberByMemberCode(String
		// .valueOf(memberCode));
		// //List<PricingStrategyDTO> slPricingStrategies =
		// getPricingStrategyByServiceLevel(
		// // paymentServiceCode, member.getServiceLevel()
		// // .getServicelevelcode(), new MfDateTime(transactiondate));
		// //modify by Martin. 2009.11.16
		// String mbrServiceLevelCode =
		// this.memberLevelHisService.getMemberServiceLevelCode(member.getMembercode()
		// + "", transactiondate);
		// List<PricingStrategyDTO> slPricingStrategies =
		// getPricingStrategyByServiceLevel(
		// paymentServiceCode,
		// Integer.valueOf(mbrServiceLevelCode), new
		// MfDateTime(transactiondate));
		//
		// if (null != slPricingStrategies && slPricingStrategies.size() > 0) {
		// pricingStrategyCode = slPricingStrategies.get(0)
		// .getPricestrategycode();
		// if(pricingStrategyCode!=null){
		// detailDto =
		// getDetailByStrategyCode(pricingStrategyCode,reservedCode);
		// if(detailDto!=null){//如果没有找到价格策略明细则继续取全局的价格策略
		// return detailDto;
		// }
		// }
		// }
		//
		// //取全局价格策略
		// List<PricingStrategyDTO> glPricingStrategies =
		// getPricingStrategyGlobal(
		// paymentServiceCode, new MfDateTime(transactiondate));
		// if (null != glPricingStrategies
		// && glPricingStrategies.size() > 0) {
		// pricingStrategyCode = glPricingStrategies.get(0)
		// .getPricestrategycode();
		// if(pricingStrategyCode!=null){
		// detailDto =
		// getDetailByStrategyCode(pricingStrategyCode,reservedCode);
		// }
		// }
		//
		// if (null == pricingStrategyCode) {
		// throw new IllegalArgumentException(
		// "massout pricingStrategyCode not exists");
		// }
		// if(null==detailDto){
		// throw new
		// IllegalArgumentException("massout pricingStrategyDetail not exists");
		// }else{
		// return detailDto;
		// }
		return null;
	}

	@Override
	public void removeMulPricingStrategyDetail(
			String[] pricingStrategyDetailCode) {
		Assert.notNull(pricingStrategyDetailCode,
				"pricingStrategyDetailCode Integer[] must be not null");
		for (int i = 0, len = pricingStrategyDetailCode.length; i < len; i++) {
			this.removePricingStrategyDetail(new Long(
					pricingStrategyDetailCode[i]));
		}
	}

	@Override
	public void removePricingStrategyDetail(Long pricingStrategyDetailCode) {
		this.pricingStrategyDAO
				.removePricingStrategyDetail(pricingStrategyDetailCode);
	}

	/**
	 * @对更改价格策略详细的校验
	 * @param pricingDetailDTO
	 */
	private void validateDetail(final PricingStrategyDetailDTO pricingDetailDTO) {

		Assert.notNull(pricingDetailDTO.getPriceStrategyCode());
		Long priceStrategyCode = pricingDetailDTO.getPriceStrategyCode();
		PricingStrategyDTO pricingStrategyDTO = this
				.getPricingStrategy(priceStrategyCode);
		Assert.notNull(pricingStrategyDTO);
		Integer pricestrategytype = pricingStrategyDTO.getPriceStrategyType();
		Assert.notNull(pricestrategytype);
		Integer caculatemethod = pricingStrategyDTO.getCaculateMethod();

		Long oldRangFrom = null;
		Long oldRangTo = null;
		Integer oldTerminalTypeCode = null;
		String oldReservedCode = null;
		if (pricingDetailDTO.getPriceStrategyDetailCode() != null) {
			PricingStrategyDetailDTO old = this
					.getPricingStrategyDetail(pricingDetailDTO
							.getPriceStrategyDetailCode());
			if (old != null) {
				oldRangFrom = old.getRangFrom();
				oldRangTo = old.getRangTo();
				oldTerminalTypeCode = old.getTerminalTypeCode();
				oldReservedCode = old.getReservedCode();
			}
		}

		// 交易额
		if (caculatemethod.intValue() == CACULATEMETHOD.SINGLETRANSACTION
				.getValue()
				|| caculatemethod.intValue() == CACULATEMETHOD.ACCUMULATED
						.getValue()) {
			if (StringUtil.isNull(oldRangFrom) // old from 为空
					|| StringUtil.isNull(oldRangTo) // old to 为空
					|| (oldRangFrom.longValue() != pricingDetailDTO
							.getRangFrom().longValue() && NumberUtil
							.isGreateThenEquals(oldRangFrom,
									pricingDetailDTO.getRangFrom()))// old from
																	// 不等 〉=
					|| (oldRangTo.longValue() != pricingDetailDTO.getRangTo()
							.longValue() && !NumberUtil.isGreateThenEquals(
							oldRangTo, pricingDetailDTO.getRangTo())) // old to
																		// 不等
																		// old <
																		// to
					|| StringUtil.isNull(oldTerminalTypeCode) //
					|| oldTerminalTypeCode.intValue() != pricingDetailDTO
							.getTerminalTypeCode() //
					|| !ObjectUtil
							.equalsContainNull(
									pricingDetailDTO.getReservedCode(),
									oldReservedCode)) {
				if (this.isPricingStrategyDetailByRange(
						pricingDetailDTO.getPriceStrategyCode(),
						pricingDetailDTO.getRangFrom(),
						pricingDetailDTO.getRangTo(),
						pricingDetailDTO.getTerminalTypeCode(),
						pricingDetailDTO.getPriceStrategyDetailCode(),
						pricingDetailDTO.getReservedCode())) {

					throw new RuntimeException("rangRang is Exists");
				}
			}
		}

		// 费率及下限
		if (pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE
				.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.CHARGERATIOANDLOWERLIMIT
						.getValue()) {

		}
		// 固定费用
		if ((pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE
				.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIO
						.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMIT
						.getValue() || pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
				.getValue())) {
			Assert.notNull(pricingDetailDTO.getFixedCharge());
		}
		// 费率-- 修改
		if (pricestrategytype.intValue() != PRICESTRATEGYTYPE.FIXEDCHARGE
				.getValue()) {
			Assert.notNull(pricingDetailDTO.getChargeRate());
		}
		// 上限
		if (pricestrategytype.intValue() == PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMIT
				.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
						.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMIT
						.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
						.getValue()) {
			Assert.notNull(pricingDetailDTO.getMaxCharge());

			if (pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMIT
					.getValue()
					|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
							.getValue()) {
				if (pricingDetailDTO.getFixedCharge() >= pricingDetailDTO
						.getMaxCharge()) {
					throw new RuntimeException(
							"fixcharge  must less than maxcharge");
				}
			}

		}
		// 下限
		if (pricestrategytype.intValue() == PRICESTRATEGYTYPE.CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
				.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.CHARGERATIOANDLOWERLIMIT
						.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDLOWERLIMIT
						.getValue()
				|| pricestrategytype.intValue() == PRICESTRATEGYTYPE.FIXEDCHARGE_CHARGERATIOANDUPPERlIMITANDLOWERLIMIT
						.getValue()) {
			Assert.notNull(pricingDetailDTO.getMinCharge());
		}
	}

	/**
	 * 判断交易金额计算费用的价格策略明细是否存在,存在ture.
	 * 
	 * @param pricingStrategyCode
	 *            Integer
	 * @param rangeFrom
	 *            Long
	 * @param rangeTo
	 *            Long
	 * @param terminaltype
	 *            Integer
	 * @return Boolean
	 */
	private Boolean isPricingStrategyDetailByRange(
			final Long pricingStrategyCode, final Long rangeFrom,
			final Long rangeTo, final Integer terminaltype,
			final Long pricingStrategyDetailCode, final String reservedCode) {
		Assert.notNull(pricingStrategyCode);
		Assert.notNull(rangeFrom);
		Assert.notNull(rangeTo);
		List<PricingStrategyDetailDTO> queryList = PricingStrategyDetailDTOUtil
				.convertToPricingDetailList(this.pricingStrategyDAO
						.getAllPricingStrategyDetail(pricingStrategyCode, null,
								terminaltype, reservedCode));
		if (queryList != null) {
			for (PricingStrategyDetailDTO dto : queryList) {
				// isCrossedRange 标记是否有交叉区间
				boolean isCrossedRange = NumberUtil.isBetweenEquals(
						dto.getRangFrom(), rangeFrom, dto.getRangTo());
				if (!isCrossedRange) {
					isCrossedRange = NumberUtil.isBetweenEquals(
							dto.getRangFrom(), rangeTo, dto.getRangTo());
					if (!isCrossedRange) {
						isCrossedRange = NumberUtil.isGreateThenEquals(
								dto.getRangFrom(), rangeFrom)
								&& NumberUtil.isGreateThenEquals(rangeTo,
										dto.getRangTo());
					}
				}

				if (isCrossedRange
						&& !ObjectUtil.equalsContainNull(
								dto.getPriceStrategyDetailCode(),
								pricingStrategyDetailCode)
						&& (ObjectUtil.equalsContainNull(
								StringUtil.null2String(dto.getReservedCode()),
								StringUtil.null2String(reservedCode)))
						&& (ObjectUtil.equalsContainNull(
								dto.getTerminalTypeCode(), terminaltype))) {
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public PricingStrategyDetailDTO getPricingStrategyDetail(
			Long pricestrategydetailcode) {
		return PricingStrategyDetailDTOUtil
				.convertToPriceStrategyDetailDTO(this.pricingStrategyDAO
						.getPricingStrategyDetail(pricestrategydetailcode));
	}

	public List<PricingStrategyDetailDTO> getPricingStrategyDetailByCode(
			final Long pricingStrategyCode) {
		List<PricingStrategyDetail> detailList = this.pricingStrategyDAO
				.getAllPricingStrategyDetailByPricingStrategyCode(pricingStrategyCode);
		// .getAllPricingStrategyDetailByParam(pricingStrategyCode);
		List<PricingStrategyDetailDTO> resultList = PricingStrategyDetailDTOUtil
				.convertToPricingDetailList(detailList);
		return resultList;
	}

	public void removeMulPricingStrategy(final String[] pricingStrategyCode) {
		if (!StringUtil.isNull(pricingStrategyCode)) {
			for (int i = 0, len = pricingStrategyCode.length; i < len; i++) {
				this.removePricingStrategy(new Long(pricingStrategyCode[i]));
			}
		}
	}

}
