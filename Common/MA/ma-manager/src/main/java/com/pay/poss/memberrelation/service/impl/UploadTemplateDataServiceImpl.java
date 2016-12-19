package com.pay.poss.memberrelation.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.RelationDataBuildException;
import com.pay.poss.memberrelation.exception.RelationDataInvalidException;
import com.pay.poss.memberrelation.exception.VouchRelationValidateMessage;
import com.pay.poss.memberrelation.service.RelationDataLoader;
import com.pay.poss.memberrelation.service.RelationValidator;
import com.pay.poss.memberrelation.service.UploadTemplateDataService;

/**   
* @Title: UploadTemplateDataServiceImpl.java 
* @Package com.pay.poss.memberrelation.service.impl 
* @Description: 处理上传的excel文件,生成对应的消息
* @author cf
* @date 2011-9-23 上午10:10:31 
* @version V1.0   
*/
public class UploadTemplateDataServiceImpl implements UploadTemplateDataService {
	private List<RelationValidator> vouchValidators;
	private static final Log LOG = LogFactory.getLog(UploadTemplateDataServiceImpl.class);

	public void setVouchValidators(List<RelationValidator> vouchValidators) {
		this.vouchValidators = vouchValidators;
	}
	
	@Override
	public List<VouchRelationValidateMessage> uploadDate(InputStream inStream) {
		List<VouchRelationValidateMessage> messages = new ArrayList<VouchRelationValidateMessage>();
		 List<RelationDataDto> vouchDataDto = null;
	        try {
	        	RelationDataLoader vouchDataLoader = getVouchDataLoader(inStream);
				vouchDataDto = vouchDataLoader.getData();
			} catch (RelationDataInvalidException e) {
				LOG.debug("Vouch data is not correct!", e);
				messages.addAll(e.getMessages());
				return messages;
			} catch (RelationDataBuildException e) {
				LOG.debug("Vouch data build fails!", e);
				messages.add(VouchRelationValidateMessage.createValidateMessage("文件内容有错！"));
				return messages;
			} catch (Exception e) {
				LOG.debug("Vouch data build fails!", e);
				messages.add(VouchRelationValidateMessage.createValidateMessage("文件内容有错！"));
				return messages;
			}
//			saveTempVouchData(request, vouchDataDto);
			for(RelationDataDto dto:vouchDataDto){
				System.out.println(dto.getFatherMemberCode()+"  "+dto.getAmountEmail());
			}
			LOG.info("End");
			messages.add(VouchRelationValidateMessage.createValidateMessage("保存成功！"));
			return messages;
	}
	
	protected RelationDataLoader getVouchDataLoader(InputStream inputStream) {
		RelationDataLoaderImpl vouchDataLoader = RelationDataLoaderImpl.getInstance(inputStream, vouchValidators);
		return vouchDataLoader;
	}

}
