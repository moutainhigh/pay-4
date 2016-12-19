package com.pay.rm.blacklist.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.rm.blacklist.dao.BlacklistDAO;
import com.pay.rm.blacklist.dao.BlacklistLogDAO;
import com.pay.rm.blacklist.dto.BlacklistDTO;
import com.pay.rm.blacklist.dto.BlacklistLogDTO;
import com.pay.rm.blacklist.service.BlacklistService;
import com.pay.rm.blacklist.utils.TemplateUtils;
import com.pay.rm.blacklist.utils.XmlUtils;
import com.pay.util.DateUtil;

public class BlacklistServiceImpl implements BlacklistService {

	protected transient Log log = LogFactory.getLog(getClass());

	private String blacklist2P;

	private String blacklist2E;

	private BlacklistDAO blacklistDAO;

	private NciicClient nciicClient;

	private BlacklistLogDAO blacklistLogDAO;

	public void setBlacklistLogDAO(BlacklistLogDAO blacklistLogDAO) {
		this.blacklistLogDAO = blacklistLogDAO;
	}

	public void setNciicClient(NciicClient nciicClient) {
		this.nciicClient = nciicClient;
	}

	public void setBlacklist2P(String blacklist2p) {
		blacklist2P = blacklist2p;
	}

	public void setBlacklistDAO(BlacklistDAO blacklistDAO) {
		this.blacklistDAO = blacklistDAO;
	}

	public void setBlacklist2E(String blacklist2e) {
		blacklist2E = blacklist2e;
	}

	@Override
	public Page<BlacklistDTO> queryBlacklist2P(Page<BlacklistDTO> page, BlacklistDTO dto) {
		return blacklistDAO.queryBlacklist2P(page, dto);
	}

	@Override
	public Page<BlacklistDTO> queryBlacklist2E(Page<BlacklistDTO> page, BlacklistDTO dto) {
		return blacklistDAO.queryBlacklist2E(page, dto);
	}

	@Override
	public Long addBlacklist2P(BlacklistDTO dto) {
		return blacklistDAO.addBlacklist2P(dto);
	}

	@Override
	public Long addBlacklist2E(BlacklistDTO dto) {
		return blacklistDAO.addBlacklist2E(dto);
	}

	@Override
	public boolean updateBlacklist2P(BlacklistDTO dto) {
		return blacklistDAO.updateBlacklist2P(dto);
	}

	@Override
	public boolean updateBlacklist2E(BlacklistDTO dto) {
		return blacklistDAO.updateBlacklist2E(dto);
	}

	@Override
	public boolean deleteBlacklist(BlacklistDTO dto) {
		return blacklistDAO.deleteBlacklist(dto);
	}

	@Override
	public boolean auditBlacklist(BlacklistDTO dto) {
		return blacklistDAO.auditBlacklist(dto);
	}

	@Override
	public BlacklistDTO queryById(BlacklistDTO dto) {
		return blacklistDAO.queryById(dto);
	}

	@Override
	public boolean batchAuditBlacklist(BlacklistDTO dto) throws Exception {
		boolean bl = false;
		String[] choose = dto.getChoose();
		List<BlacklistDTO> list = new ArrayList<BlacklistDTO>();
		BlacklistDTO blacklistDTO = null;
		for (String hmdid : choose) {
			blacklistDTO = new BlacklistDTO();
			blacklistDTO.setHmdid(Long.valueOf(hmdid));
			blacklistDTO.setStatus(dto.getStatus());
			blacklistDTO.setRemark(dto.getRemark());
			list.add(blacklistDTO);
		}
		try {
			blacklistDAO.batchAuditBlacklist(list);
			bl = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e.getMessage());
		}
		return bl;
	}

	@Override
	public String uploadBlacklist2P(BlacklistDTO dto) throws Exception {
		String xml = buildBlacklist(blacklist2P, dto, "1");
		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS>");
		bf.append(xml);
		bf.append("</ROWS>");
		String result = nciicClient.executeClient(bf.toString());
		if (result.startsWith("共")) {
			int count = dto.getUploadcount() == null ? 0 : dto.getUploadcount();
			dto.setUploadcount(count + 1);
			blacklistDAO.updateBlacklist2P(dto);
			return result;
		} else {
			Map<String, String> map = XmlUtils.xml2Map(xml);
			return map.get("ErrorMsg") == null ? result : map.get("ErrorMsg");
		}
	}

	public String batchUploadBlacklist2P(BlacklistDTO dto) throws Exception {
		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS>");
		List<BlacklistDTO> blacklist = blacklistDAO.queryListByIds(dto);
		List<BlacklistDTO> updateBlacklist = new ArrayList<BlacklistDTO>();
		for (int i = 0; i < blacklist.size(); i++) {
			BlacklistDTO blacklistDTO = blacklist.get(i);
			String xml = buildBlacklist(blacklist2P, blacklistDTO, String.valueOf(i + 1));
			bf.append(xml);

			int count = blacklistDTO.getUploadcount() == null ? 0 : blacklistDTO.getUploadcount();
			blacklistDTO.setUploadcount(count + 1);
			updateBlacklist.add(blacklistDTO);
		}
		bf.append("</ROWS>");
		String result = nciicClient.executeClient(bf.toString());
		if (result.startsWith("共")) {
			blacklistDAO.batchUpdateBlacklist2P(updateBlacklist);
			return result;
		} else {
			Map<String, String> map = XmlUtils.xml2Map(result);
			return map.get("ErrorMsg") == null ? result : map.get("ErrorMsg");
		}
	}

	public String uploadBlacklist2E(BlacklistDTO dto) throws Exception {
		String xml = buildBlacklist(blacklist2E, dto, "1");
		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS>");
		bf.append(xml);
		bf.append("</ROWS>");
		String result = nciicClient.executeClient(bf.toString());
		if (result.startsWith("共")) {
			int count = dto.getUploadcount() == null ? 0 : dto.getUploadcount();
			dto.setUploadcount(count + 1);
			blacklistDAO.updateBlacklist2P(dto);
			return result;
		} else {
			Map<String, String> map = XmlUtils.xml2Map(result);
			return map.get("ErrorMsg") == null ? result : map.get("ErrorMsg");
		}
	}

	public String batchUploadBlacklist2E(BlacklistDTO dto) throws Exception {

		StringBuffer bf = new StringBuffer();
		bf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROWS>");
		List<BlacklistDTO> blacklist = blacklistDAO.queryListByIds(dto);
		List<BlacklistDTO> updateBlacklist = new ArrayList<BlacklistDTO>();
		for (int i = 0; i < blacklist.size(); i++) {
			BlacklistDTO blacklistDTO = blacklist.get(i);
			String xml = buildBlacklist(blacklist2E, blacklistDTO, String.valueOf(i + 1));
			bf.append(xml);

			int count = blacklistDTO.getUploadcount() == null ? 0 : blacklistDTO.getUploadcount();
			blacklistDTO.setUploadcount(count + 1);
			updateBlacklist.add(blacklistDTO);
		}
		bf.append("</ROWS>");
		String result = nciicClient.executeClient(bf.toString());
		if (result.startsWith("共")) {
			blacklistDAO.batchUpdateBlacklist2E(updateBlacklist);
			return result;
		} else {
			Map<String, String> map = XmlUtils.xml2Map(result);
			return map.get("ErrorMsg") == null ? result : map.get("ErrorMsg");
		}
	}

	private String buildBlacklist(String xmlTemplate, BlacklistDTO dto, String count) {

		Map<String, String> msgXml = new HashMap<String, String>();
		msgXml.put("count", count);
		msgXml.put("hmdid", String.valueOf(dto.getHmdid()));
		msgXml.put("hmdlx", dto.getHmdlx());
		msgXml.put("cydwbm", dto.getCydwbm());
		msgXml.put("gmsfhm", dto.getGmsfhm());
		msgXml.put("xm", dto.getXm());
		msgXml.put("fsdq", dto.getFsdq());
		msgXml.put("lhtj", dto.getLhtj());
		msgXml.put("hmdsj", dto.getHmdsj());
		msgXml.put("hmdsjbz", dto.getHmdsjbz());
		msgXml.put("sjhm", dto.getSjhm());
		msgXml.put("yhkh", dto.getYhkh());
		msgXml.put("khh", dto.getKhh());
		msgXml.put("gddh", dto.getGddh());
		msgXml.put("ip", dto.getIp());
		msgXml.put("mac", dto.getMac());
		msgXml.put("email", dto.getEmail());
		msgXml.put("dz", dto.getDz());
		msgXml.put("jgmc", dto.getJgmc());
		msgXml.put("urldz", dto.getUrldz());
		msgXml.put("urltzdz", dto.getUrltzdz());
		msgXml.put("yyzzbh", dto.getYyzzbh());
		msgXml.put("icp", dto.getIcp());
		msgXml.put("icpbar", dto.getIcpbar());
		msgXml.put("zfr", dto.getZfr());
		msgXml.put("zzjgdm", dto.getZzjgdm());
		msgXml.put("bjsj", DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", dto.getBjsj()));
		msgXml.put("sjzt", dto.getSjzt());
		msgXml.put("czr", dto.getCzr());
		msgXml.put("ywlb", dto.getYwlb());

		msgXml.put("jgmc", dto.getJgmc());
		msgXml.put("yyzzbh", dto.getYyzzbh());
		msgXml.put("zzjgdm", dto.getZzjgdm());

		return TemplateUtils.getTemplate(xmlTemplate, msgXml);
	}

	@Override
	public Long addBlacklistLog(BlacklistLogDTO dto) {
		return blacklistLogDAO.addBlacklistLog(dto);
	}

	@Override
	public List<BlacklistLogDTO> queryBlacklistLogList(Long hmdid) {
		return blacklistLogDAO.queryBlacklistLogList(hmdid);
	}

	@Override
	public List<Long> insertBatchBlacklist(List<BlacklistLogDTO> list) {
		return blacklistLogDAO.insertBatchBlacklist(list);
	}

}
