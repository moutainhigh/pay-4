package com.pay.poss.userrelation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.userrelation.dao.IUserRelationDao;
import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.dto.UserRelationDto;

public class UserRelationDaoImpl extends BaseDAOImpl implements
		IUserRelationDao {

	@Override
	public UserRelationDto findUserRDtoByLoginId(String loginId) {
		return (UserRelationDto) findObjectByTemplate("findUserRDtoByLoginId",
				loginId);
	}

	@Override
	public int countLayer(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		return ((Integer) countByCriteria("countLayer", map)).intValue();
	}

	@Override
	public int getMaxRv() {

		return ((Integer) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getMaxRv"))).intValue();
	}

	@Override
	public void insertUpdateLeftValue(int rv) {

		getSqlMapClientTemplate().update(
				getNamespace().concat("insert_updateLV"), rv);
	}

	@Override
	public void insertUpdateRigthValue(int rv) {
		getSqlMapClientTemplate().update(
				getNamespace().concat("insert_updateRV"), rv);
	}

	@Override
	public void changeNodeUpdate(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		getSqlMapClientTemplate().update(
				getNamespace().concat("update_updateLvAndRv"), map);
	}

	@Override
	public void changeNodeUpdateLV(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		getSqlMapClientTemplate().update(
				getNamespace().concat("update_updateLv"), map);
	}

	@Override
	public void changeNodeUpdateRV(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		getSqlMapClientTemplate().update(
				getNamespace().concat("update_updateRv"), map);
	}

	@Override
	public void changeNodeUpdateLVForNP(int offset, int newLv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", offset);
		map.put("newLv", newLv);
		getSqlMapClientTemplate().update(
				getNamespace().concat("changeNodeUpdateLVForNP"), map);
	}

	@Override
	public void changeNodeUpdateRVForNP(int offset, int newLv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("offset", offset);
		map.put("newLv", newLv);
		getSqlMapClientTemplate().update(
				getNamespace().concat("changeNodeUpdateRVForNP"), map);
	}

	@Override
	public void changeNodeUpdateLV(int offset) {

		getSqlMapClientTemplate().update(
				getNamespace().concat("changeNodeUpdateLV"), offset);
	}

	@Override
	public void changeNodeUpdateRV(int offset) {
		getSqlMapClientTemplate().update(
				getNamespace().concat("changeNodeUpdateRV"), offset);
	}

	@Override
	public void deleteNode(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		getSqlMapClientTemplate().delete(getNamespace().concat("delete"), map);
	}

	@Override
	public void deleteNodeUpdateLV(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		getSqlMapClientTemplate().delete(
				getNamespace().concat("delete_updateLV"), map);
	}

	@Override
	public void deleteNodeUpdateRv(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		getSqlMapClientTemplate().delete(
				getNamespace().concat("delete_updateRV"), map);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<NodesDto> findAllSubLoginId(int lv, int rv) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("lv", lv);
		map.put("rv", rv);
		return super.findByTemplate("findAllUserRDtoByLoginId", map);
	}

	@Override
	public int countMemberRelationByCondition(UserRelationDto userRelationDto) {
		Integer obj = (Integer) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("countUserRelationByCondition"),
				userRelationDto);
		if (null == obj) {
			return 0;
		}
		return obj;
	}

	@Override
	public List<UserRelationDto> queryMemberRelationByCondition(
			UserRelationDto dto) {

		return getSqlMapClientTemplate().queryForList(
				getNamespace().concat("queryUserRelationByCondition"), dto);
	}

	@Override
	public int isExistUser(String loginId) {

		return ((Integer) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("isExistUser"))).intValue();

	}

	@Override
	public List<UserRelationDto> findByLayer(long id) {
		return getSqlMapClientTemplate().queryForList(
				getNamespace().concat("queryUserRelationByLayer"), id);
	}

	public  List<NodesDto> findAll(){
		return getSqlMapClientTemplate().queryForList(
				getNamespace().concat("findAll"));
	};
	
	
	
	
}
