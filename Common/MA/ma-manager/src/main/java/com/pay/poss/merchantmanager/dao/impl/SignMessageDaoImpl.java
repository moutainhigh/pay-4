package com.pay.poss.merchantmanager.dao.impl;



import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.merchantmanager.dao.ISignMessageDao;
import com.pay.poss.merchantmanager.dto.SignMessageDto;
import com.pay.poss.merchantmanager.model.SignMessage;
import com.pay.util.BeanConvertUtil;



@SuppressWarnings("unchecked")
public class SignMessageDaoImpl extends BaseDAOImpl<SignMessage> implements ISignMessageDao{
	
	public Long createSignMessage(SignMessageDto dto){
		Long id = (Long)this.getSqlMapClientTemplate().insert("possma_signmessag.createSignMessage",BeanConvertUtil.convert(SignMessage.class, dto));
		return id;
	}
	
	public List<SignMessageDto> querySignMessageList(SignMessageDto dto){
		return this.getSqlMapClientTemplate().queryForList("possma_signmessag.querySignMessageList",dto);
	}
	
	public Integer countSignMessage(SignMessageDto dto){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("possma_signmessag.countSignMessage",dto);
	}
	
	public Integer updateSignMessage(SignMessageDto dto){
		return (Integer)this.getSqlMapClientTemplate().update("possma_signmessag.updateSignMessage",dto);
	}
	public Integer deleteSignMessage(SignMessageDto dto){
		return (Integer)this.getSqlMapClientTemplate().delete("possma_signmessag.deleteSignMessage",dto);
	}
	public SignMessageDto querySignMessageById(SignMessageDto dto){
		return (SignMessageDto)this.getSqlMapClientTemplate().queryForObject("possma_signmessag.querySignMessageById",dto);
	}
	
	public List<SignMessageDto> querySignMessageByCondition(SignMessageDto dto){
		return this.getSqlMapClientTemplate().queryForList("possma_signmessag.querySignMessageByCondition",dto);
	}
	public Integer countSignMessageByCondition(SignMessageDto dto){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("possma_signmessag.countSignMessageByCondition",dto);
	}
	public List<SignMessageDto> validateDepName(SignMessageDto dto){
		return this.getSqlMapClientTemplate().queryForList("possma_signmessag.validateDepName",dto);
	} 
}
