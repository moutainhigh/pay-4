package com.pay.acc.service.cidverify.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.identityverify.IdentityVerifyService;
import com.pay.acc.member.dao.IdcVerifyBaseDAO;
import com.pay.acc.member.dao.IdcVerifyGovDAO;
import com.pay.acc.member.dto.IdcVerifyBaseDto;
import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.dto.IdcVerifyGovDto;
import com.pay.acc.member.dto.IndividualInfoDto;
import com.pay.acc.member.model.IdcVerifyBase;
import com.pay.acc.member.model.IdcVerifyGov;
import com.pay.acc.member.service.IndividualInfoService;
import com.pay.acc.service.cidverify.INciicClient;
import com.pay.acc.service.cidverify.IdCardVerifyService;
import com.pay.acc.service.cidverify.IdcVerifyBankService;
import com.pay.acc.service.cidverify.IdcVerifyGovService;
import com.pay.acc.service.cidverify.cid2gov.XmlModelManager;
import com.pay.acc.service.cidverify.dto.BaseCid2GovParameterModel;
import com.pay.acc.service.cidverify.dto.Cid2GovDTO;
import com.pay.acc.service.cidverify.dto.CidResult;
import com.pay.acc.service.cidverify.dto.IdcBankVerifyDto;
import com.pay.acc.service.cidverify.impl.validation.ValidatorService;
import com.pay.acc.service.cidverify.util.CidVerifyEnum;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.acc.translog.dto.TransLogDto;
import com.pay.acc.translog.model.TransLog;
import com.pay.acc.translog.service.TransLogService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.exception.AppException;
import com.pay.util.BeanConvertUtil;
import com.pay.util.DESUtil;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-9-12 下午02:36:45
 */
public class IdCardVerifyServiceImpl implements IdCardVerifyService {

	private final Log log = LogFactory.getLog(IdCardVerifyServiceImpl.class);

	private final Long ID_VERIFY_MEMBER_CODE = 10000000000L; // 做为对外接口专用memberCode
	// 公安网处理
	private INciicClient nciicClient;
	// 公安网模板
	private XmlModelManager xmlModelManager;
	// 保存公安网认证数据
	private IdcVerifyGovService idcVerifyGovService;
	// 保存银行鉴权认证数据
	private IdcVerifyBankService idcVerifyBankService;
	// 个人信息服务
	private IndividualInfoService individualInfoService;
	private IdcVerifyBaseDAO idcVerifyBaseDAO;
	private IdcVerifyGovDAO idcVerifyGovDAO;
	private TransLogService transLogService;

	private final String STR_HM = "<result_gmsfhm>一致</result_gmsfhm>";
	private final String STR_XM = "<result_xm>一致</result_xm>";
	private final String STR_HM_NO = "<result_gmsfhm>不一致</result_gmsfhm>";
	private final String STR_XM_NO = "<result_xm>不一致</result_xm>";
	private final String STR_ISNULL = "库中无此号";

	private final int STATE_PASS = 1;// 成功
	private final int STATE_ERROR = 2;// 失败
	private final int STATE_VFYING = 3;// 认证中
	private final static int verifyFlag_gov = 1;// 公安网认证
	private final static int paperType = 1; // 实名认证的证件类型 1代表身份证
	private final static int isPaperFile = 0; // 实名认证 默认用户等级 0为普通用户，未认证
	private final int num = 1;
	private final static int gov_level = 2;// 实名认证等级 公安网 认证成功 为 2
	private BaseDAO mainDao;
	
	private ValidatorService validatorService;
	
	private IdentityVerifyService identityVerifyService;
	
	public void setValidatorService(ValidatorService validatorService) {
		this.validatorService = validatorService;
	}

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	public void setIdentityVerifyService(IdentityVerifyService identityVerifyService) {
		this.identityVerifyService = identityVerifyService;
	}

	public int editTransLogForLinkId(String orderSerial, Long baseId)
			throws NumberFormatException, SQLException {
		if (!"".equals(orderSerial) && !"".equals(baseId)) {
			List<TransLog> transLogList = transLogService
					.queryTransLogBySerialNo(orderSerial);
			if (transLogList != null) {
				return transLogService.editTransLogForLinkId(transLogList,
						baseId);
			}

		}
		return 0;
	}

	public Long addTransLog(String serialNo, Integer payType, Integer status,
			String recvAcct, String payAcct, String relatxSerialNo,
			Long linkId, String gov_amount) {
		if (!"".equals(serialNo) && payType != null && !"".equals(recvAcct)
				&& !"".equals(payAcct)) {
			// String gov_amount=MessageConvertFactory.getMessage("gov_amount");
			// //公安网认证订单费用
			TransLogDto transLog = new TransLogDto();
			Date time = new Date();
			transLog.setAcctType(CidVerifyEnum.acctType);
			transLog.setAmount(Long.parseLong(gov_amount));
			transLog.setConfirmDate(time);
			transLog.setCreateDate(time);
			transLog.setPayAcct(payAcct);
			transLog.setPayDate(time);
			transLog.setPayType(payType);
			transLog.setRecvAcct(recvAcct);
			if (relatxSerialNo != null && !"".equals(relatxSerialNo)) {
				transLog.setRelatxSerialNo(Long.parseLong(relatxSerialNo));
			}
			transLog.setSerialNo(Long.parseLong(serialNo));
			transLog.setStatus(status);
			transLog.setUpdateDate(time);
			transLog.setLinkId(linkId);
			try {
				return transLogService.createTransLog(transLog);
			} catch (Exception e) {
				log.error("create log error:", e);
			}

		}
		return null;
	}

	public int updateMemberVerifyStatus(Long idcId, Integer status) {
		int fail_update = 0;
		IdcVerifyBase idcVerifyBase = new IdcVerifyBase();
		idcVerifyBase.setId(idcId);
		idcVerifyBase.setStatus(status);
		IdcVerifyGov idcVerifyGov = idcVerifyGovDAO.getIdcVerifyGov(idcId);
		if (idcVerifyGov != null) {
			IdcVerifyGov verifyGov = new IdcVerifyGov();
			verifyGov.setIdcVerifyBaseId(idcId);
			verifyGov.setPhoto(STR_ISNULL);
			idcVerifyGovDAO.editIdcVerifyGov(verifyGov);
		}
		if (idcVerifyBaseDAO.getIdcVerifyBaseById(idcId) != null) {
			return idcVerifyBaseDAO.editIdcVerifyBase(idcVerifyBase);
		}
		return fail_update;
	}

	public int getMemberVerifyState(Long memberCode) {
		int status = 0;// 不存在记录
		if (memberCode != null && !"".equals(memberCode)) {
			List<IdcVerifyBase> verifyList = new ArrayList<IdcVerifyBase>();
			verifyList = idcVerifyBaseDAO.getVerifyList(memberCode);
			if (verifyList != null && !verifyList.isEmpty()) {
				for (IdcVerifyBase verify : verifyList) {
					if (verify.getStatus() == STATE_VFYING) {
						status = STATE_VFYING;
						return status;
					}
					if (verify.getStatus() == STATE_ERROR) {
						status = STATE_ERROR;
					}
					if (verify.getStatus() == STATE_PASS) {
						status = STATE_PASS;
						return status;
					}
				}
			}
		}
		return status;
	}

	public int editIdcVerify(Long idcId, int status, String xmlResult) {
		IdcVerifyBase idcVerifyBase = new IdcVerifyBase();
		IdcVerifyGov verifyGov = new IdcVerifyGov();
		verifyGov.setIdcVerifyBaseId(idcId);
		verifyGov.setPhoto(xmlResult);
		idcVerifyBase.setId(idcId);
		idcVerifyBase.setStatus(status);
		// if(status == STATE_PASS)
		// {
		idcVerifyBase.setIsPaperFile(gov_level);
		// }
		return this.editIdcVerifyBase(idcVerifyBase, verifyGov);
	}

	public Long addCidVerifyDefaultState(String memberCode, String name,
			String cardNo) {
		MemberVerifyDto mvd = new MemberVerifyDto();
		mvd.setMemberCode(new Long(memberCode));
		mvd.setName(name);
		mvd.setPaperNo(DESUtil.encrypt(cardNo));// 身份证号码加密 DESUtil.encrypt(no)
		mvd.setVerifyFlag(verifyFlag_gov);
		mvd.setPaperType(paperType);
		mvd.setIsPaperFile(gov_level);
		mvd.setPhoto("null");
		mvd.setStatus(STATE_VFYING);
		try {
			return this.saveCidVerify2Gov(mvd);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	public int editIdcVerifyBase(IdcVerifyBase idcVerifyBase,
			IdcVerifyGov verifyGov) {
		int result_status = STATE_ERROR;
		if (idcVerifyBase != null) {
			result_status = idcVerifyBaseDAO.getIdcVerifyBaseById(
					idcVerifyBase.getId()).getStatus();
		}
		if (idcVerifyBaseDAO.getIdcVerifyBaseById(idcVerifyBase.getId()) != null) {
			if (idcVerifyBaseDAO.editIdcVerifyBase(idcVerifyBase) > 0) {
				idcVerifyBase = idcVerifyBaseDAO
						.getIdcVerifyBaseById(idcVerifyBase.getId());
				result_status = idcVerifyBase.getStatus();
				/*
				 * if(result_status == STATE_PASS){ IndividualInfoDto
				 * individualInfoDto =
				 * individualInfoService.queryIndividualInfoByMemberCode
				 * (idcVerifyBase.getMemberCode());
				 * individualInfoDto.setName(idcVerifyBase.getName());
				 * individualInfoDto.setCerCode(idcVerifyBase.getPaperNo());
				 * individualInfoDto.setCerType(idcVerifyBase.getPaperType());
				 * individualInfoService
				 * .updateIndividualInfo(individualInfoDto); }
				 */
				IdcVerifyGov idcVerifyGov = idcVerifyGovDAO
						.getIdcVerifyGov(verifyGov.getIdcVerifyBaseId());
				if (idcVerifyGov != null) {
					idcVerifyGovDAO.editIdcVerifyGov(verifyGov);
				}
			}
		}
		return result_status;
	}

	/**
	 * 通过公安网认证进行实名认证
	 */
	@Override
	public CidResult cid2Gov(Cid2GovDTO dto) throws Exception {
		BaseCid2GovParameterModel bpm = new BaseCid2GovParameterModel();
		log.info("#公安网实名认证处理开始#");
		CidResult result = new CidResult();
		if (null != dto && !"".equals(dto.getNo()) && !"".equals(dto.getName())) {
			bpm.setNo(dto.getNo());
			bpm.setName(dto.getName());
			String condition = xmlModelManager.getCondition(bpm);
			log.info("#通过公安网认证信息模版组装完成#");
			 //result = nciicClient.executeCidVerify(condition);// 暂时写死
			boolean flag = identityVerifyService.validate(dto.getName(), dto.getNo());
			//log.info("通过公安网认证信息模版组装完成，实名认证结束");
			log.info("#认证结果数据输出#:[" + result + "]");
			//boolean flag = validator(dto.getName(), dto.getNo());
			result.setStateResult(flag?1:2);
			log.info("#认证结果数据输出#:[" + result + "]");
		} else {
			log.error("#通过公安网认证进行实名认证参数缺失#");
		}
		return result;
	}

	/**
	 * 解析GOV返回的结果
	 * 
	 * @param result
	 * @return
	 */
	private CidResult parseGovResult(String result) throws Exception {
		CidResult cr = new CidResult();
		if (null != result && !"".equals(result)) {
			log.info("#开始解析结果数据#");

			int numHm = result.indexOf(STR_HM);
			int numXm = result.indexOf(STR_XM);
			int numHmNo = result.indexOf(STR_HM_NO);
			int numXmNo = result.indexOf(STR_XM_NO);
			int numIsNull = result.indexOf(STR_ISNULL);

			if (numHm < 0 && numXm < 0 && numIsNull < 0 && numHmNo < 0
					&& numXmNo < 0) {// 异常或者无结果
				// 认证中
				cr.setNoCid(false);
				cr.setStateResult(STATE_VFYING);
				cr.setGovResult(result);
			} else if (numIsNull > 0) {// 库中无此号
				// 失败
				cr.setNoCid(true);
				cr.setStateResult(STATE_ERROR);
				cr.setGovResult(result);
			} else if (numHm > 0 && numXm > 0) {// 两项正确
				// 成功
				cr.setNoCid(false);
				cr.setStateResult(STATE_PASS);
				cr.setGovResult(result);
			} else if (numHmNo > 0 || numXmNo > 0) {// 有一项不正确的
				// 失败
				cr.setNoCid(false);
				cr.setStateResult(STATE_ERROR);
				cr.setGovResult(result);
			}

			log.info("#解析结果数据完成#");

		} else {
			log.error("#结果数据无内容,解析终止.#");
		}
		return cr;
	}

	/**
	 * 保存认证的基础信息
	 * 
	 * @param memberVerifyDto
	 * @return IdcVerifyBase
	 */
	private IdcVerifyBaseDto saveCidVerifyBase(MemberVerifyDto memberVerifyDto)
			throws Exception {
		IdcVerifyBaseDto idcVerifyBase = new IdcVerifyBaseDto();
		try {
			idcVerifyBase.setCreatedDate(new Date());
			idcVerifyBase.setMemberCode(memberVerifyDto.getMemberCode());
			idcVerifyBase.setName(memberVerifyDto.getName());
			idcVerifyBase.setPaperType(memberVerifyDto.getPaperType());
			idcVerifyBase.setVerifyFlag(memberVerifyDto.getVerifyFlag());
			idcVerifyBase.setPaperNo(memberVerifyDto.getPaperNo());
			idcVerifyBase.setIsPaperFile(memberVerifyDto.getIsPaperFile());
			idcVerifyBase.setLinkNo("");
			idcVerifyBase.setStatus(memberVerifyDto.getStatus());
			Long id = (Long) mainDao.create(BeanConvertUtil.convert(
					IdcVerifyBase.class, idcVerifyBase));
			idcVerifyBase.setId(id);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return idcVerifyBase;
	}

	/**
	 * 保存公安网认证的实名认证数据
	 * 
	 * @return IdcVerifyBase.id
	 */
	@Override
	public Long saveCidVerify2Gov(MemberVerifyDto memberVerifyDto)
			throws Exception {
		// 新增 idcVerifyBase 记录
		IdcVerifyBaseDto reBase = this.saveCidVerifyBase(memberVerifyDto);
		// 新增 idcVerifyGov 记录
		IdcVerifyGovDto idcVerifyGov = new IdcVerifyGovDto();
		idcVerifyGov.setCreatedDate(new Date());
		idcVerifyGov.setErrorCode("null");
		idcVerifyGov.setErrorMsg("null");
		idcVerifyGov.setIdcVerifyBaseId(reBase.getId());
		idcVerifyGov.setPhoto(memberVerifyDto.getPhoto());
		IdcVerifyGovDto reGov = idcVerifyGovService.saveGov(idcVerifyGov);
		// 如认证状态成功 更新个人会员基本信息（真实姓名、证件类型、证件号码）
		if (null != reBase.getStatus() && reBase.getStatus() == num) {
			IndividualInfoDto individualInfoDto = individualInfoService
					.queryIndividualInfoByMemberCode(memberVerifyDto
							.getMemberCode());
			individualInfoDto.setName(reBase.getName());
			individualInfoDto.setCerCode(reBase.getPaperNo());
			individualInfoDto.setCerType(reBase.getPaperType());
			individualInfoService
					.updateIndividualInfoByMemberCode(individualInfoDto);
		}
		// 返回结果
		return reBase.getId();
	}

	/**
	 * 保存银行的实名认证数据
	 * 
	 * @return IdcVerifyBase.id
	 */
	@Override
	public Long saveCidVerify2Bank(MemberVerifyDto memberVerifyDto)
			throws Exception {
		// 新增 idcVerifyBase 记录
		IdcVerifyBaseDto reBase = this.saveCidVerifyBase(memberVerifyDto);
		// 新增 IdcBankVerify 记录
		IdcBankVerifyDto idcBankVerify = new IdcBankVerifyDto();

		idcBankVerify.setCreatedDate(new Date());
		idcBankVerify.setIdcVerifyBaseId(reBase.getId());
		if ("".equals(memberVerifyDto.getErrorCode())
				|| null == memberVerifyDto.getErrorCode()
				|| "null".equals(memberVerifyDto.getErrorCode())) {
			idcBankVerify.setErrorCode("null");
		}
		if ("".equals(memberVerifyDto.getErrorMsg())
				|| null == memberVerifyDto.getErrorMsg()
				|| "null".equals(memberVerifyDto.getErrorMsg())) {
			idcBankVerify.setErrorMsg("null");
		}
		idcBankVerify.setName(reBase.getName());
		idcBankVerify.setBankAcctId(memberVerifyDto.getBankAcctId());
		idcBankVerify.setBankId(memberVerifyDto.getBankId());
		idcBankVerify.setBranchBankName(memberVerifyDto.getBranchBankName());
		idcBankVerify.setBranchBankId(memberVerifyDto.getBranchBankId());
		idcBankVerify.setProvince(memberVerifyDto.getProvince());
		idcBankVerify.setCity(memberVerifyDto.getCity());
		idcVerifyBankService.saveBank(idcBankVerify);
		// 如认证状态成功 更新个人会员基本信息（真实姓名、证件类型、证件号码）
		if (null != reBase.getStatus() && reBase.getStatus() == num) {
			IndividualInfoDto individualInfoDto = individualInfoService
					.queryIndividualInfoByMemberCode(memberVerifyDto
							.getMemberCode());
			individualInfoDto.setName(reBase.getName());
			individualInfoDto.setCerCode(reBase.getPaperNo());
			individualInfoDto.setCerType(reBase.getPaperType());
			individualInfoService
					.updateIndividualInfoByMemberCode(individualInfoDto);
		}
		return reBase.getId();
	}

	/*** set ***/

	public void setNciicClient(INciicClient nciicClient) {
		this.nciicClient = nciicClient;
	}

	public void setXmlModelManager(XmlModelManager xmlModelManager) {
		this.xmlModelManager = xmlModelManager;
	}

	public void setIdcVerifyGovService(IdcVerifyGovService idcVerifyGovService) {
		this.idcVerifyGovService = idcVerifyGovService;
	}

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setIdcVerifyBankService(
			IdcVerifyBankService idcVerifyBankService) {
		this.idcVerifyBankService = idcVerifyBankService;
	}

	public void setIdcVerifyBaseDAO(IdcVerifyBaseDAO idcVerifyBaseDAO) {
		this.idcVerifyBaseDAO = idcVerifyBaseDAO;
	}

	public void setIdcVerifyGovDAO(IdcVerifyGovDAO idcVerifyGovDAO) {
		this.idcVerifyGovDAO = idcVerifyGovDAO;
	}

	public void setTransLogService(TransLogService transLogService) {
		this.transLogService = transLogService;
	}

	@Override
	public boolean isCidVerified(String idCardNO, String name)
			throws AppException {
		IdcVerifyBase baseVerify = idcVerifyBaseDAO.querylastIdcVerify(
				idCardNO, name);

		if (baseVerify != null) {
			log.info("身份证号：" + idCardNO + ",姓名：" + name + "已经在库中验证通过。将直接返回true");
			return true;
		}
		return cid2GovForApi(idCardNO, name);
	}

	private boolean cid2GovForApi(String idCardNO, String name)
			throws AppException {

		// 如果不存在则调用公安网验证
		Cid2GovDTO dto = new Cid2GovDTO();
		dto.setNo(idCardNO);
		dto.setName(name);
		CidResult resultDto = null;
		try {
			resultDto = cid2Gov(dto);
		} catch (Exception e) {
			log.info("身份证号：" + idCardNO + ",姓名：" + name + "访问公安网异常验证返回false");
			e.printStackTrace();
			throw new AppException(e.getMessage());
		}

		if (resultDto.isNoCid()) {
			log.info("身份证号：" + idCardNO + ",姓名：" + name + "调用公安网验证失败，身份证和姓名不匹配");
			return false;
		}
		if (resultDto.getStateResult() == 1) {
			log.info("身份证号：" + idCardNO + ",姓名：" + name + "调用公安网验证成功");
			MemberVerifyDto mvdto = new MemberVerifyDto();
			mvdto.setMemberCode(ID_VERIFY_MEMBER_CODE);
			mvdto.setName(name);
			mvdto.setPaperNo(DESUtil.encrypt(idCardNO));
			mvdto.setVerifyFlag(1);
			mvdto.setPaperType(1);// 1为身份证
			mvdto.setIsPaperFile(2);// 公安网
			mvdto.setStatus(1);// 成功为1
			mvdto.setPhoto(resultDto.getGovResult());
			try {
				Long govId = saveCidVerify2GovForApi(mvdto);
				log.info("身份证号：" + idCardNO + ",姓名：" + name + "记录id是=" + govId);
			} catch (Exception e) {
				log.info("身份证号：" + idCardNO + ",姓名：" + name
						+ "验证通过信息一致，但保存记录时出现异常");
				e.printStackTrace();
			}
		}
		return resultDto.getStateResult() == 1;
	}

	private Long saveCidVerify2GovForApi(MemberVerifyDto memberVerifyDto)
			throws Exception {
		// 新增 idcVerifyBase 记录
		IdcVerifyBaseDto reBase = this.saveCidVerifyBase(memberVerifyDto);
		// 新增 idcVerifyGov 记录
		IdcVerifyGovDto idcVerifyGov = new IdcVerifyGovDto();
		idcVerifyGov.setCreatedDate(new Date());
		idcVerifyGov.setErrorCode("null");
		idcVerifyGov.setErrorMsg("null");
		idcVerifyGov.setIdcVerifyBaseId(reBase.getId());
		idcVerifyGov.setPhoto(memberVerifyDto.getPhoto());
		IdcVerifyGovDto reGov = idcVerifyGovService.saveGov(idcVerifyGov);
		// 返回结果
		return reBase.getId();
	}

	@Override
	public boolean cidVerifyByBank(String bankNo, String custName, String certNo)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IdcVerifyDto queryVerifyInfo(Long memberCode) {

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);

		IdcVerifyDto IdcVerifyDto = (IdcVerifyDto) idcVerifyBaseDAO
				.findObjectByTemplate("queryVerifyInfo", paraMap);
		return IdcVerifyDto;
	}

	@Override
	public boolean validator(String name, String cardNo) throws Exception {

//		return validatorService.validator(name, cardNo);
		return identityVerifyService.validate(name, cardNo);
	}

}
