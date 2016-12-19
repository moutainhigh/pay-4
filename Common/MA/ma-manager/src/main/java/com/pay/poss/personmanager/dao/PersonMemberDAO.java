package com.pay.poss.personmanager.dao;

import java.util.List;
import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.operatelogmanager.model.PossOperate;
import com.pay.poss.personmanager.dto.PersonMemberInfoDto;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctAssociateDto;
import com.pay.poss.personmanager.dto.PersonalAcctBalanceDto;
import com.pay.poss.personmanager.dto.PersonalAcctInfoSearchDto;
import com.pay.poss.personmanager.dto.PersonalAcctTradeTotalDto;
import com.pay.poss.personmanager.dto.PersonalLoginHistoryDto;
import com.pay.poss.personmanager.model.Member;

public interface PersonMemberDAO extends BaseDAO<Member>{

	/**查询个人会员账户list
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<PersonalAcctInfoSearchDto> queryPersonalAcctInfoList(Map paramMap);
	
	/**查询个人会员账户count
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	int countPersonalAcctInfo(Map paramMap);
	
	
	/**查询个人会员List
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<PersonMemberInfoDto> queryPersonInfoList(Map paramMap);
	
	/**查询个人会员count
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	int countPersonInfo(Map paramMap);	
	
	/** 统计个人会员
	 * @param memberSearchDto
	 * @return
	 */
	int countPersonMember(PersonMemberSearchDto memberSearchDto);
	
	/**根据会员号得到会员信息
	 * @param memberCode
	 * @return
	 */
	PersonMemberSearchDto selectPersonMember(Long memberCode);

	/**查询个人会员登录历史list
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<PersonalAcctInfoSearchDto> queryPersonalLoginHistoryList(Map paramMap);
	
	/**查询个人会员登录count
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	int countPersonalLoginHistory(Map paramMap);
	


	/**查询个人会员账户余额List
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalAcctBalanceDto> queryPersonalAcctBalanceList(Map paramMap) ;


	/**查询个人会员账户余额count
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int countPersonalAcctBalanceList(Map paramMap) ;
	
	/**查询个人会员账户交易总额
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PersonalAcctTradeTotalDto> queryPeraonalAcctTradeTotal(Map paramMap) ;
	
	/**查询疑似IP关联账户list
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	List<PersonalAcctAssociateDto> queryPersonalAcctAssociatelist(Map paramMap);
	
	/**查询疑似IP关联账户count
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	int countqueryPersonalAcctAssociate(Map paramMap);
	
	/**更新会员账号可操作属性
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	void updatePersonalMemberAcctOperate(Map paramMap);
	
	/**更新会员可操作属性
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	void updatePersonalMemberOperate(Map paramMap);
	

	/**查询个人会员详细信息
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PersonMemberInfoDto selectPersonlMemberInfoDetail(Map paramMap);
	
	/**查询最后一次登录信息
	 * @param paramMap
	 * @return
	 */
	public PersonalLoginHistoryDto selectMemberLoginIp(Map paramMap);
	
	/**查询会员银行账户
	 * @param paramMap
	 * @return
	 */
	public PersonMemberInfoDto selectMemberBankAcct(Map paramMap);
	
	/**查询个人会员账户信息
	 * @param paramMap
	 * @return
	 */
	public  List<PersonalAcctInfoSearchDto>   queryPersonalMemberAcctMessage(Map paramMap);
	
	/**count个人会员账户信息
	 * @param paramMap
	 * @return
	 */
	public int countPersonalMemberAcctMessage(Map paramMap);
	
	/**个人会员冻结解冻记录日志
	 * @param possOperate
	 * @return
	 */
	public void insertPossOperater(PossOperate possOperate);
	
	/**个人会员修改冻结解冻记录日志
	 * @param possOperate
	 * @return
	 */
	public void updatePossOperater(Map<String,Object> paraMap);
	
	/**查询个人身份证状态
	 * @param paramMemberCode
	 * @return PersonMemberInfoDto
	 */
	public PersonMemberInfoDto selectPersonlMemberIsPaperFile (Long paramMemberCode);
	
	/**查询账户状态是否冻结,止入,止出等
	 * @param paramMap
	 * @return PersonMemberInfoDto
	 */
	public AcctAttribDto selectAcctAttrib (Map<String,Object> paramMap);
	
	/**查询冻结账户记录以便找出同时期的批量记录
	 * @param paramMap
	 * @return List<PossOperate> 
	 */
	public List<PossOperate> selectAcctFrozenOperateByAcctCode (Map<String,Object> paramMap);
	
	/**查询该会员是否绑定口令卡
	 * @param paramMap
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public Integer countBindMatrixCard(Map paramMap);
	
	/**由交易流水号和acctCode查询余额(页面显示)
	 * @param paramMap
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public String queryBalance(Map paramMap) ;
	
	/**更新t_member_operate
	 * @param paramMap
	 * @return String
	 */
	public void updateMemberOperateByCondition(Map<String,Object> paramMap);
	
	/**由memberCode查询会员t_member表信息
	 * @param paramMap
	 * @return PersonMemberInfoDto
	 */
	public PersonMemberInfoDto selectMemberByMemberCode(Map paramMap);
}
