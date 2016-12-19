package com.pay.base.service.matrixcard;

import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

/**
 * @author jim_chen
 * @version 
 * 2010-9-16 
 */
public interface IMatrixCardService {
	
	/**
	 * 保存 口令卡表
	 * @param dto
	 * @return
	 */
	public MatrixCardDto save(final MatrixCardDto dto);
	
	
	/**统计口令卡表的纪录
	 * @return
	 */
	int countMatrixCard();
	
	/**根据ID查询口令卡表
	 * @param id
	 * @return
	 */
	public MatrixCardDto selectMatrixCardById(Long id);
	
	/**更新口令卡表
	 * @param dto
	 * @return
	 */
	public void updateMatrixCard(MatrixCardDto dto);
	
	/**
	 * 生成口令卡数据及口令卡序列号
	 * @return
	 */
	public MatrixCardDto create()throws MatrixCardException;
	
	
	/**得到解密的MatrixCardDto对象
	 * @param id
	 * @return
	 */
	MatrixCardDto findById(Long id);
	
	/**根据序列号查询
	 * @param serialNo
	 * @return
	 */
	MatrixCardDto findBySerialNo(String serialNo);
	
	
	/**根据绑定操作员查询
	 * @param bindUid
	 * @return
	 */
	MatrixCardDto findByBindUid(Long bindUid);
	
	
	
	/**根据会员查询
	 * @param memberCode
	 * @return
	 */
	MatrixCardDto findByBindMemberCode(Long memberCode);
	
	/**
	 * 根据操作员ID判断是否绑定
	 * @param u_id
	 * @return
	 */
//	boolean isBind(Long u_id); 
	
	
	/**根据memberCode判断是否绑定口令卡
	 * @param memberCode
	 * @return
	 */
	boolean isBindByMemberCode(Long memberCode);
	
	/**
	 * 根据坐标取口令卡值
	 * @param serialNo 口令卡序列号
	 * @param coordinates 坐标
	 * @return
	 */
	public String[] getValue(String serialNo, String[] coordinates);
	
	
	/**
	 * @param memberCode
	 * @return
	 */
	MatrixCardDto selectmatrixcardByTransInfoMemberCode(Long memberCode);
	

}
