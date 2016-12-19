package com.pay.rm.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.rm.blacklist.dto.BlacklistDTO;
import com.pay.rm.blacklist.dto.BlacklistLogDTO;
import com.pay.rm.blacklist.service.BlacklistService;
import com.pay.util.DateUtil;
import com.pay.util.SpringControllerUtils;

/**
 * 公安网黑名单管理
 */
public class BlacklistController extends AbstractBaseController {

	protected transient Log log = LogFactory.getLog(getClass());

	private BlacklistService blacklistService;

	public void setBlacklistService(BlacklistService blacklistService) {
		this.blacklistService = blacklistService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView blacklist2PInit = new ModelAndView(URL("blacklist2PInit"));
		return blacklist2PInit;
	}

	public ModelAndView blacklist2PList(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) {
		ModelAndView blacklist2PList = new ModelAndView(URL("blacklist2PList"));
		Page<BlacklistDTO> page = PageUtils.getPage(request);
		dto.setHmdlx("1");
		page = blacklistService.queryBlacklist2P(page, dto);
		blacklist2PList.addObject("page", page);
		return blacklist2PList;
	}

	public ModelAndView addBlacklist2PInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView blacklist2PAdd = new ModelAndView(URL("blacklist2PAdd"));
		return blacklist2PAdd;
	}

	public ModelAndView addBlacklist2P(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		SessionUserHolder sessionUserHolder = (SessionUserHolder) authentication
				.getPrincipal();
		String userName = sessionUserHolder.getUsername();
		ModelAndView blacklist2PAdd = new ModelAndView(URL("blacklist2PAdd"));
		dto.setHmdlx("1"); // 负面信息类型(1个人负面信息/2机构负面信息)
		dto.setStatus("2");// 0正常1审核拒绝2新建待审核3修改待审核4删除待审核5上传待审核
		dto.setCzr(userName);
		if (blacklistService.addBlacklist2P(dto) == 0L) {
			blacklist2PAdd.addObject("flag", "0");
		} else {
			addBlacklistLog(dto.getHmdid(), "新增");
			blacklist2PAdd.addObject("flag", "1");
		}
		return blacklist2PAdd;
	}

	public ModelAndView updateBlacklist2PInit(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		ModelAndView blacklist2PEdit = new ModelAndView(URL("blacklist2PEdit"));
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		return blacklist2PEdit.addObject("dto", resultDto);
	}

	public ModelAndView updateBlacklist2P(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		ModelAndView blacklist2PInit = new ModelAndView(URL("blacklist2PInit"));
		dto.setStatus("3");
		dto.setBjsj(DateUtil.parse("yyyy-MM-dd HH:mm:ss", dto.getBjsjStr()));
		blacklistService.updateBlacklist2P(dto);
		addBlacklistLog(dto.getHmdid(), "修改");
		return blacklist2PInit;
	}

	public ModelAndView blacklist2PDetail(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		ModelAndView blacklist2PDetail = new ModelAndView(
				URL("blacklist2PDetail"));
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		List<BlacklistLogDTO> logDtoList = blacklistService
				.queryBlacklistLogList(dto.getHmdid());
		return blacklist2PDetail.addObject("dto", resultDto).addObject(
				"logDtoList", logDtoList);
	}

	public ModelAndView deleteBlacklist(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		String bl = "";
		try {
			if (blacklistService.deleteBlacklist(dto)) {
				bl = "success";
				addBlacklistLog(dto.getHmdid(), "删除");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringControllerUtils.renderText(response, bl);
		return null;
	}

	public ModelAndView auditInit(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		return new ModelAndView(URL("blacklistAudit")).addObject("dto",
				resultDto);
	}

	public ModelAndView audit(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		String bl = "";
		try {
			if (blacklistService.auditBlacklist(dto)) {
				bl = "success";
				addBlacklistLog(dto.getHmdid(), "审核");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		SpringControllerUtils.renderText(response, bl);
		return null;
	}

	public ModelAndView batchAudit(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		String bl = "";
		try {
			if (blacklistService.batchAuditBlacklist(dto)) {
				bl = "success";
				addBlacklistLog(dto.getChoose(), "批量审核");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		SpringControllerUtils.renderText(response, bl);
		return null;
	}

	public ModelAndView uploadBlacklist2P(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		String msg = "";
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		try {
			msg = blacklistService.uploadBlacklist2P(resultDto);
			addBlacklistLog(dto.getHmdid(), "上传");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		SpringControllerUtils.renderText(response, msg);
		return null;
	}

	public ModelAndView batchUploadBlacklist2P(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		String msg = "";
		try {
			msg = blacklistService.batchUploadBlacklist2P(dto);
			addBlacklistLog(dto.getChoose(), "批量上传");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		SpringControllerUtils.renderText(response, msg);
		return null;
	}

	/**
	 * blacklist2E
	 */
	public ModelAndView blacklist2EInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView blacklist2EInit = new ModelAndView(URL("blacklist2EInit"));
		return blacklist2EInit;
	}

	public ModelAndView blacklist2EList(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) {
		ModelAndView blacklist2EList = new ModelAndView(URL("blacklist2EList"));
		Page<BlacklistDTO> page = PageUtils.getPage(request);
		dto.setHmdlx("2");
		page = blacklistService.queryBlacklist2E(page, dto);
		blacklist2EList.addObject("page", page);
		return blacklist2EList;
	}

	public ModelAndView addBlacklist2EInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView blacklist2EAdd = new ModelAndView(URL("blacklist2EAdd"));
		return blacklist2EAdd;
	}

	public ModelAndView addBlacklist2E(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		SessionUserHolder sessionUserHolder = (SessionUserHolder) authentication
				.getPrincipal();
		String userName = sessionUserHolder.getUsername();
		ModelAndView blacklist2EAdd = new ModelAndView(URL("blacklist2EAdd"));
		dto.setHmdlx("2"); // 负面信息类型(1个人负面信息/2机构负面信息)
		dto.setStatus("2");// 0正常1审核拒绝2新建待审核3修改待审核4删除待审核5上传待审核
		dto.setCzr(userName);
		if (blacklistService.addBlacklist2E(dto) == 0L) {
			blacklist2EAdd.addObject("flag", "0");
		} else {
			addBlacklistLog(dto.getHmdid(), "新增");
			blacklist2EAdd.addObject("flag", "1");
		}
		return blacklist2EAdd;
	}

	public ModelAndView updateBlacklist2EInit(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		ModelAndView blacklist2EEdit = new ModelAndView(URL("blacklist2EEdit"));
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		return blacklist2EEdit.addObject("dto", resultDto);
	}

	public ModelAndView updateBlacklist2E(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		ModelAndView blacklist2EInit = new ModelAndView(URL("blacklist2EInit"));
		dto.setStatus("3");
		dto.setBjsj(DateUtil.parse("yyyy-MM-dd HH:mm:ss", dto.getBjsjStr()));
		blacklistService.updateBlacklist2E(dto);
		addBlacklistLog(dto.getHmdid(), "修改");
		return blacklist2EInit;
	}

	public ModelAndView blacklist2EDetail(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		ModelAndView blacklist2EDetail = new ModelAndView(
				URL("blacklist2EDetail"));
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		List<BlacklistLogDTO> logDtoList = blacklistService
				.queryBlacklistLogList(dto.getHmdid());
		return blacklist2EDetail.addObject("dto", resultDto).addObject(
				"logDtoList", logDtoList);
	}

	public ModelAndView auditInit2E(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		return new ModelAndView(URL("blacklistAudit2E")).addObject("dto",
				resultDto);
	}

	public ModelAndView uploadBlacklist2E(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		String msg = "";
		BlacklistDTO resultDto = blacklistService.queryById(dto);
		try {
			msg = blacklistService.uploadBlacklist2E(resultDto);
			addBlacklistLog(dto.getHmdid(), "上传");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		SpringControllerUtils.renderText(response, msg);
		return null;
	}

	public ModelAndView batchUploadBlacklist2E(HttpServletRequest request,
			HttpServletResponse response, BlacklistDTO dto) throws Exception {
		String msg = "";
		try {
			msg = blacklistService.batchUploadBlacklist2E(dto);
			addBlacklistLog(dto.getChoose(), "批量上传");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		SpringControllerUtils.renderText(response, msg);
		return null;
	}

	private void addBlacklistLog(Long hmdid, String logContent) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		SessionUserHolder sessionUserHolder = (SessionUserHolder) authentication
				.getPrincipal();
		String userName = sessionUserHolder.getUsername();
		BlacklistLogDTO dto = new BlacklistLogDTO();
		dto.setHmdid(hmdid);
		dto.setLogOperator(userName);
		dto.setLogContent(logContent);
		blacklistService.addBlacklistLog(dto);

	}

	private void addBlacklistLog(String[] hmdids, String logContent) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		SessionUserHolder sessionUserHolder = (SessionUserHolder) authentication
				.getPrincipal();
		String userName = sessionUserHolder.getUsername();

		BlacklistLogDTO dto = null;
		List<BlacklistLogDTO> list = new ArrayList<BlacklistLogDTO>();
		for (int i = 0; i < hmdids.length; i++) {
			String hmdid = hmdids[i];
			dto = new BlacklistLogDTO();
			dto.setHmdid(Long.valueOf(hmdid));
			dto.setLogOperator(userName);
			dto.setLogContent(logContent);
			list.add(dto);
		}
		blacklistService.insertBatchBlacklist(list);
	}

}
