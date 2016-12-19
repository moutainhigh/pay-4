package com.pay.pe.manualbooking.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import com.pay.inf.dao.Page;
import com.pay.pe.manualbooking.dao.VouchDataDao;
import com.pay.pe.manualbooking.dao.VouchDetailDataDao;
import com.pay.pe.manualbooking.dto.VouchDataDetailSearchDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.dto.VouchDataDtoUtil;
import com.pay.pe.manualbooking.exception.VouchDataInconsistException;
import com.pay.pe.manualbooking.exception.VouchDataPostingException;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.service.AccService;
import com.pay.pe.manualbooking.service.VouchAccountingService;
import com.pay.pe.manualbooking.service.VouchSeqGeneratorService;
import com.pay.pe.manualbooking.service.VouchService;
import com.pay.pe.manualbooking.service.VouchUserService;
import com.pay.pe.manualbooking.util.VouchDataStatus;
import com.pay.pe.manualbooking.util.VouchDataTemplate;
import com.pay.pe.manualbooking.util.VouchDetailSearchCriteria;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;
import com.pay.pe.manualbooking.util.VouchStateMachine;
import com.pay.pe.manualbooking.util.VouchStatusChangeEvent;
import com.pay.poss.security.model.SessionUserHolder;

/**
 * 
 * 手工记账服务实现
 */
public class VouchServiceImpl implements VouchService {
	private static final Log LOG = LogFactory.getLog(VouchServiceImpl.class);
	/**
	 * 手工记帐申请数据访问对象
	 */
	private VouchDataDao vouchDataDao;

	/**
	 * 手工记账申请明细访问对象
	 */
	private VouchDetailDataDao vouchDetailDataDao;

	/**
	 * 手工记账申请号产生器
	 */
	private VouchSeqGeneratorService vouchSeqGenerator;

	/**
	 * 手工记帐用户服务
	 */
	private VouchUserService vouchUserService;

	/**
	 * 手工记帐申请的记帐服务
	 */
	private VouchAccountingService vouchAccountingService;

	public VouchDataDao getVouchDataDao() {
		return vouchDataDao;
	}

	public void setVouchDataDao(VouchDataDao vouchDataDao) {
		this.vouchDataDao = vouchDataDao;
	}

	public VouchSeqGeneratorService getVouchSeqGenerator() {
		return vouchSeqGenerator;
	}

	public void setVouchSeqGenerator(VouchSeqGeneratorService vouchSeqGenerator) {
		this.vouchSeqGenerator = vouchSeqGenerator;
	}

	public VouchUserService getVouchUserService() {
		return vouchUserService;
	}

	public void setVouchUserService(VouchUserService vouchUserService) {
		this.vouchUserService = vouchUserService;
	}

	public VouchDetailDataDao getVouchDetailDataDao() {
		return vouchDetailDataDao;
	}

	public void setVouchDetailDataDao(VouchDetailDataDao vouchDetailDataDao) {
		this.vouchDetailDataDao = vouchDetailDataDao;
	}

	public VouchAccountingService getVouchAccountingService() {
		return vouchAccountingService;
	}

	public void setVouchAccountingService(
			VouchAccountingService vouchAccountingService) {
		this.vouchAccountingService = vouchAccountingService;
	}

	public VouchServiceImpl() {

	}

	private AccService accService;

	public VouchDataDto approveVouchData(VouchDataDto vouchDataDto)
			throws VouchDataInconsistException, VouchDataPostingException {
		LOG.info("Start");

		VouchData vouchData = vouchDataDao.getVouchDataById(vouchDataDto
				.getVouchDataId());

		String auditor = vouchUserService.getCurrentUser();

		vouchData.setAuditor(auditor);
		vouchData.setAuditDate(new Date());
		vouchData.setRemark(vouchDataDto.getRemark());

		VouchDataStatus currentStatus = null;
		if (vouchData.getStatus() != null) {
			currentStatus = VouchDataStatus.valueOf(vouchData.getStatus());
		}
		VouchDataStatus nextStatus = VouchStateMachine.getNextStatus(
				currentStatus, VouchStatusChangeEvent.APPROVE);

		vouchData.setStatus(nextStatus.getValue());

		LOG.info("Before posting");
		// String vouchCode = vouchAccountingService.posting(vouchDataDto);

		String vouchCode = accService.posting(vouchDataDto);

		LOG.info("After posting");

		vouchData.setAccountingDate(new Date());
		vouchData.setVouchCode(vouchCode);

		try {
			vouchData = vouchDataDao.updateVouchData(vouchData);
		} catch (Throwable e) {
			LOG.debug("fails to update vouch data!", e);
			throw new VouchDataInconsistException();
		}

		LOG.info("End");
		return VouchDataDtoUtil.convert2VouchDataDto(vouchData);
	}

	public VouchDataDto cancelVouchData(VouchDataDto vouchDataDto)
			throws VouchDataInconsistException {
		LOG.info("Start");

		VouchData vouchData = vouchDataDao.getVouchDataById(vouchDataDto
				.getVouchDataId());

		VouchDataStatus currentStatus = null;
		if (vouchData.getStatus() != null) {
			currentStatus = VouchDataStatus.valueOf(vouchData.getStatus());
		}
		VouchDataStatus nextStatus = VouchStateMachine.getNextStatus(
				currentStatus, VouchStatusChangeEvent.CANCEL);

		vouchData.setStatus(nextStatus.getValue());

		try {
			vouchData = vouchDataDao.updateVouchData(vouchData);
		} catch (Throwable e) {
			LOG.debug("fails to update vouch data!", e);
			throw new VouchDataInconsistException();
		}

		LOG.info("End");
		return VouchDataDtoUtil.convert2VouchDataDto(vouchData);
	}

	protected SessionUserHolder getSessionUserHolder() {

		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (authentication != null) {
			Object sessionObj = authentication.getPrincipal();

			SessionUserHolder sessionUserHolder = null;

			if (sessionObj instanceof SessionUserHolder) {

				sessionUserHolder = (SessionUserHolder) sessionObj;

			}
			return sessionUserHolder;
		}
		return null;
	}

	public VouchDataDto createVouchData(VouchDataDto vouchDto) {
		LOG.info("Start");

		VouchData vouchData = VouchDataDtoUtil.convert2VouchData(vouchDto);

		String applicationCode = vouchSeqGenerator.generatorSeq();

		String creator = vouchUserService.getCurrentUser();

		vouchData.setApplicationCode(applicationCode);
		vouchData.setCreator(creator);
		vouchData.setCreateDate(new Date());

		// vouchData.setAuditor(null);
		// vouchData.setRemark(null);
		// vouchData.setVouchCode(null);
		vouchData.setVersion(Long.valueOf("1"));

		VouchDataStatus currentStatus = null;
		if (vouchData.getStatus() != null) {
			currentStatus = VouchDataStatus.valueOf(vouchData.getStatus());
		}
		VouchDataStatus nextStatus = VouchStateMachine.getNextStatus(
				currentStatus, VouchStatusChangeEvent.CREATE);

		vouchData.setStatus(nextStatus.getValue());

		vouchData = vouchDataDao.saveVouchData(vouchData);

		LOG.info("End");
		return VouchDataDtoUtil.convert2VouchDataDto(vouchData);
	}

	public List<VouchDataDto> findVouch(VouchSearchCriteria vouchCriteria) {
		// , Page page) {
		LOG.info("Start");
		// List<VouchData> vouchDatas =
		// vouchDataDao.findVouchDatasWithPage(page, vouchCriteria);
		List<VouchData> vouchDatas = vouchDataDao
				.findVouchDatasWithPage(vouchCriteria);
		LOG.info("End");
		return VouchDataDtoUtil.convert2VouchDataDtos(vouchDatas);
	}

	// public List<VouchDataDetailSearchDto> findVouchDetail(
	// VouchDetailSearchCriteria vouchDetailCriteria, Page page) {
	public List<VouchDataDetailSearchDto> findVouchDetail(
			VouchDetailSearchCriteria vouchDetailCriteria) {
		LOG.info("Start");
		List<VouchDataDetailSearchDto> vouchDataDetailSearchDtos = new ArrayList<VouchDataDetailSearchDto>();

		try {
			vouchDataDetailSearchDtos = vouchDetailDataDao
					.findVouchDetailDatasWithPage(vouchDetailCriteria);
		} catch (SQLException e) {
			LOG.debug("fails to query the vouch detail!", e);
		}
		LOG.info("End");
		return vouchDataDetailSearchDtos;
	}

	public VouchDataDto getVouchDataById(Long vouchId) {
		LOG.info("Start");
		VouchData vouchData = vouchDataDao.getVouchDataById(vouchId);

		VouchDetailData vouchDetailData = new VouchDetailData();
		vouchDetailData.setVouchDataId(vouchId);
		vouchData.setVouchDetails(vouchDetailDataDao
				.findBySelective(vouchDetailData));

		LOG.info("End");
		return VouchDataDtoUtil.convert2VouchDataDto(vouchData);
	}

	public VouchDataTemplate getVouchDataTemplate() {
		return VouchDataTemplate.getInstance();
	}

	public VouchDataDto rejectVouchData(VouchDataDto vouchDataDto)
			throws VouchDataInconsistException {
		LOG.info("Start");
		VouchData vouchData = vouchDataDao.getVouchDataById(vouchDataDto
				.getVouchDataId());

		String auditor = vouchUserService.getCurrentUser();

		vouchData.setAuditor(auditor);
		vouchData.setAuditDate(new Date());
		vouchData.setRemark(vouchDataDto.getRemark());

		VouchDataStatus currentStatus = null;
		if (vouchData.getStatus() != null) {
			currentStatus = VouchDataStatus.valueOf(vouchData.getStatus());
		}
		VouchDataStatus nextStatus = VouchStateMachine.getNextStatus(
				currentStatus, VouchStatusChangeEvent.REJECT);

		vouchData.setStatus(nextStatus.getValue());

		try {
			vouchData = vouchDataDao.updateVouchData(vouchData);
		} catch (Throwable e) {
			LOG.debug("fails to update vouch data!", e);
			throw new VouchDataInconsistException();
		}

		LOG.info("End");
		return VouchDataDtoUtil.convert2VouchDataDto(vouchData);
	}

	public Page<VouchDetailData> search(Page<VouchDetailData> page,
			Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return vouchDetailDataDao.findByQuery("PE-MIS.getVouchDetailInfo",
				page, map);

	}

	public void createVouchDetail(VouchDetailData vdd) {
		vouchDetailDataDao.createVouchDetail(vdd);
	}

	public VouchData insertVouchData(VouchData vd) {
		String applicationCode = vouchSeqGenerator.generatorSeq();
		vd.setApplicationCode(applicationCode);
		vd.setVouchCode(applicationCode);
		VouchData voud = vouchDetailDataDao.insertVouchData(vd);
		return voud;
	}

	public AccService getAccService() {
		return accService;
	}

	public void setAccService(AccService accService) {
		this.accService = accService;
	}

}
