package com.pay.poss.memberrelation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.VouchRelationValidateMessage;
import com.pay.poss.memberrelation.service.RelationValidator;

/**
 * 
 * 手工记帐数据验证器
 */
public class RelationValidatorImpl implements RelationValidator {	
	
	private static final Log LOG = LogFactory.getLog(RelationValidatorImpl.class);
	/**
	 * 最多记录数
	 */
	private static final int MAX_RECORD_SIZE = 1000;
	
	/**
	 * 空记录数
	 */
	private static final int EMPTY_RECORD_SIZE = 0;
	
	/**
	 * email长度
	 */
	private static final int REMARK_MAX_SIZE = 60;
	
	private static final int RELATION_STORE_NUMBERS_SIZE = 60;
	
	private static final int RELATION_STORE_ADDRESS_SIZE = 255;

	private MemberQueryService memberQueryService ;
	

	public RelationValidatorImpl() { }
	

	
	/**
	 * @return VouchRelationValidateMessage
	 * 产生记录为空消息
	 */
	protected VouchRelationValidateMessage createRecordsEmptyMessage() {
		return VouchRelationValidateMessage.createRecordsEmptyMessage();
	}	
	/**
	 * @return VouchRelationValidateMessage
	 * 产生记录数溢出消息
	 */
	protected VouchRelationValidateMessage createRecordSizeExceedMessage() {
		return VouchRelationValidateMessage.createRecordSizeExceedMessage();
	}
	/**
	 * @param i
	 * @return VouchRelationValidateMessage
	 * email过长消息
	 */
	protected VouchRelationValidateMessage createEmailTooLongMessage(int i) {
		return VouchRelationValidateMessage.createEmailTooLongMessage(i);
	}
	
	/**
	 * 店面会员号不存在
	 * @param i
	 * @return
	 */
	protected VouchRelationValidateMessage createSunMemberCodeNotExistsMessage(int i) {
		return VouchRelationValidateMessage.createSunMemberCodeNotExistsMessage(i);
	}
	/**
	 * email 为空消息
	 * @param i
	 * @return
	 */
	protected VouchRelationValidateMessage createAmountEmailIsNullMessage(int i) {
		return VouchRelationValidateMessage.createAmountEmailIsNullMessage(i);
	}
	/**
	 * 总店会员号不可以为空
	 * @param i
	 * @return
	 */
	protected VouchRelationValidateMessage createMemberCodeIsEmptyMessage() {
		return VouchRelationValidateMessage.createMemberCodeIsEmptyMessage();
		
	}
	/**
	 * 总店会员号不是数字
	 * @param i
	 * @return
	 */
	protected VouchRelationValidateMessage createMemberCodeNotLongMessage() {
		return VouchRelationValidateMessage.createMemberCodeNotLongMessage();
		
	}
	/**
	 * 总店会员号在系统中不存在
	 * @param i
	 * @return
	 */
	protected VouchRelationValidateMessage createMemberCodeNotExistsMessage() {
		return VouchRelationValidateMessage.createMemberCodeNotExistsMessage();
		
	}
	/**
	 *门店编号空消息
	 * @param i
	 * @return
	 */
	protected VouchRelationValidateMessage createStoreNumbersIsNullMessage(int i) {
		return VouchRelationValidateMessage.createStoreNumbersIsNullMessage(i);
	}
	
	/**
	 * @param i
	 * @return VouchRelationValidateMessage
	 * 门店编号消息过长
	 */
	protected VouchRelationValidateMessage createStoreNumbersTooLongMessage(int i) {
		return VouchRelationValidateMessage.createStoreNumbersTooLongMessage(i);
	}
	
	/**
	 *门店地址为 空消息
	 * @param i
	 * @return
	 */
	protected VouchRelationValidateMessage createStoreAddressIsNullMessage(int i) {
		return VouchRelationValidateMessage.createStoreAddressIsNullMessage(i);
	}

	/**
	 * @param i
	 * @return VouchRelationValidateMessage
	 * 门店地址消息过长
	 */
	protected VouchRelationValidateMessage createStoreAddressTooLongMessage(int i) {
		return VouchRelationValidateMessage.createStoreAddressTooLongMessage(i);
	}
	/**
	 * 检查父会员员在系统中是否存在
	 * @param memberCode
	 * @param messages
	 * @return
	 */
	private boolean checkFatherMemberCode(String memberCode,List<VouchRelationValidateMessage> messages) {
		//判断会员号是否存在
		if(StringUtils.isEmpty(memberCode)){
			LOG.debug("memberCode is empty : " );
			messages.add(createMemberCodeIsEmptyMessage());
			return true;
		}
		Long fatherMemberCode=null;
		try{
			fatherMemberCode=new Long(memberCode);
		}catch (Exception e){
			//会员号格式不正确
			messages.add(createMemberCodeNotLongMessage());
			return true;
		}
		try {
			MemberBaseInfoBO bo=memberQueryService.queryMemberBaseInfoByMemberCode(fatherMemberCode);
			if(null==bo){
				messages.add(createMemberCodeNotExistsMessage());
				return true;
			}
		} catch (MaMemberQueryException e) {
			messages.add(createMemberCodeNotExistsMessage());
			return true;
		}		
		return false;		
	}
	
	private boolean checkAmountEmail(String amountEmail,List<VouchRelationValidateMessage> messages,int index,RelationDataDto dataDto){
		//email为空
		if(StringUtils.isEmpty(amountEmail)){
			messages.add(createAmountEmailIsNullMessage(index));
			return true;
		}
		//email过长消息
		if (StringUtils.isNotEmpty(amountEmail) && amountEmail.length() > REMARK_MAX_SIZE) {
			messages.add(createEmailTooLongMessage(index));
			return true;
		}
		try {
			MemberInfoDto dto=this.memberQueryService.doQueryMemberInfoNsTx(amountEmail, null, null, null);
			if(null==dto){
				messages.add(createSunMemberCodeNotExistsMessage(index));
				return true;
			}
			//将会员号设置到RelationDataDto对象
			dataDto.setSunMemberCode(dto.getMemberCode());
		} catch (MaMemberQueryException e) {
			messages.add(createSunMemberCodeNotExistsMessage(index));
			return true;
		}
		return false;
	}
	
	private boolean checkRelationStoreNumbers(String storeNumbers,List<VouchRelationValidateMessage> messages,int index,RelationDataDto dataDto){
		if(StringUtils.isEmpty(storeNumbers)){
			messages.add(createStoreNumbersIsNullMessage(index));
			return true;
		}
		//消息过长
		if (StringUtils.isNotEmpty(storeNumbers) && storeNumbers.length() > RELATION_STORE_NUMBERS_SIZE) {
			messages.add(createStoreNumbersTooLongMessage(index));
			return true;
		}
		return false;
	}
	
	private boolean checkRelationStoreAdress(String storeAdress,List<VouchRelationValidateMessage> messages,int index,RelationDataDto dataDto){
		if(StringUtils.isEmpty(storeAdress)){
			messages.add(createStoreAddressIsNullMessage(index));
			return true;
		}
		//消息过长
		if (StringUtils.isNotEmpty(storeAdress) && storeAdress.length() > RELATION_STORE_ADDRESS_SIZE) {
			messages.add(createStoreAddressTooLongMessage(index));
			return true;
		}
		return false;
	}
	
	public List<VouchRelationValidateMessage> validate(List<RelationDataDto> relationDataDto ) {
		LOG.info("Start");
		List<VouchRelationValidateMessage> messages = new ArrayList<VouchRelationValidateMessage>();
		//记录为空
		if (relationDataDto.size() == EMPTY_RECORD_SIZE) {
			LOG.debug("Records is empty!");
			messages.add(createRecordsEmptyMessage());
			return messages;
		} 
		//记录大于最大条数
		if (relationDataDto.size() > MAX_RECORD_SIZE) {
			LOG.debug("Record num exceed max size! size : " + relationDataDto.size());
			messages.add(createRecordSizeExceedMessage());
			return messages;
		}
		//检查父会员员在系统中是否存在
		String memberCode=relationDataDto.get(0).getFatherMemberCode();
		if(checkFatherMemberCode(memberCode, messages)){
			return messages;
		}	
		
		int detailIndex = 0;
		for (RelationDataDto dto : relationDataDto) {	
			detailIndex++;
			boolean flag=false;
			String amountEmail =dto.getAmountEmail();
			flag=this.checkAmountEmail(amountEmail, messages, detailIndex, dto);
			if(flag){
				//代表对应的这行数据不更新
				continue;
			}
			String storeNumbers=dto.getRelationStoreNumbers();
			flag=this.checkRelationStoreNumbers(storeNumbers, messages, detailIndex, dto);
			if(flag){
				continue;
			}
			String storeAddress=dto.getRelationStoreAddress();
			flag=this.checkRelationStoreAdress(storeAddress, messages, detailIndex, dto);
			if(flag){
				continue;
			}
			dto.setInsertFlag(true);

		}
		
		if (messages.size() != 0) {
			return messages;
		}
		

		LOG.info("End");
		return messages;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
	
	
}
