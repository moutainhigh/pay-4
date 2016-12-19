package com.pay.acc.member.dao;

import java.util.List;
import java.util.Map;

import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.model.IdcVerifyBase;
import com.pay.inf.dao.BaseDAO;

public interface IdcVerifyBaseDAO extends BaseDAO {

	/**
	 * 重写方法 查询该会员曾经验证的失败记录
	 * 
	 * @param map
	 * @return true/false
	 */
	public boolean findIdcVerifyDtoList(String sqlId,
			Map<String, Object> paraMap);

	/**
	 * 重写方法 查询该会员的验证成功记录
	 * 
	 * @param map
	 * @return IdcVerifyDto
	 */
	public IdcVerifyDto findIdcVerifyDto(String sqlId,
			Map<String, Object> paraMap);

	/**
	 * 根据memberCode查询实名认证信息
	 * 
	 * @param memberCode
	 * @return IdcVerifyBase
	 */
	public IdcVerifyBase QueryMemberVerifyByMemberCode(Long memberCode);

	/**
	 * 通过id去查 数据
	 * 
	 * @author Sunny Ying
	 * @param id
	 * @throw null
	 * @return IdcVerifyBase
	 */
	public IdcVerifyBase getIdcVerifyBaseById(Long id);

	/**
	 * 更新 数据
	 * 
	 * @author Sunny Ying
	 * @param idcVerifyBase
	 * @throw null
	 * @return int
	 */
	public int editIdcVerifyBase(IdcVerifyBase idcVerifyBase);

	/**
	 * 查询会员的实名认证所有信息
	 * 
	 * @author Sunny Ying
	 * @param memberCode
	 * @throw null
	 * @return List<IdcVerifyBase>
	 */
	public List<IdcVerifyBase> getVerifyList(Long memberCode);

	/**
	 * 查询用户是否实名认证过
	 * 
	 * @param idCardNo
	 * @param name
	 * @return IdcVerifyBase
	 * @author ddr 2013-3-15
	 */
	public IdcVerifyBase querylastIdcVerify(String idCardNO, String name);
}
