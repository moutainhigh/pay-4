package com.pay.poss.userrelation.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.userrelation.dao.IUserRelationDao;
import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.dto.UserRelationDto;
import com.pay.poss.userrelation.service.IUserRelationService;
import com.pay.util.StringUtil;

public class UserRelationServiceImpl implements IUserRelationService {

	private IUserRelationDao userRelationDaoImpl;
	
	
	
	public void setUserRelationDaoImpl(IUserRelationDao userRelationDaoImpl) {
		this.userRelationDaoImpl = userRelationDaoImpl;
	}

	@Override
	public void createUserRelation(UserRelationDto userRelationDto) {
		
		int lv = 1; //左值
		int rv = 2; //右值
		UserRelationDto urDto = new UserRelationDto();
		if(!StringUtil.isEmpty(userRelationDto.getPloginId())) {
			urDto = userRelationDaoImpl.findUserRDtoByLoginId(userRelationDto.getPloginId());
			if(null != urDto){
				lv = urDto.getRv();
				rv = lv + 1;
			}
		}
		else {
			int maxRv = userRelationDaoImpl.getMaxRv();
			lv = maxRv + 1;
			rv = lv + 1;
		}

		userRelationDaoImpl.insertUpdateLeftValue(lv);
		userRelationDaoImpl.insertUpdateRigthValue(lv);
		userRelationDto.setLayer(userRelationDaoImpl.countLayer(lv, rv));
		userRelationDto.setLv(lv);
		userRelationDto.setRv(rv);
		userRelationDto.setCreateDate(new Timestamp(new Date().getTime()));
		userRelationDaoImpl.create(userRelationDto);
	}

	@Override
	public void updateUserRelation(UserRelationDto userRelationDto) {
		//修改前对象
		UserRelationDto oldURDto = (UserRelationDto) userRelationDaoImpl.findById(userRelationDto.getId());
		
		//分三种情况处理：
		//1.原来和现在都为空，不需要做处理; 
		boolean empty = StringUtil.isEmpty(oldURDto.getPloginId());
		boolean empty2 = StringUtil.isEmpty(userRelationDto.getPloginId());
		if(empty && empty2)
			return;
		
		//2.原来的领导和现在领导相同，不需要做处理; 
		if((!empty) && (!empty2) && oldURDto.getPloginId().equals(userRelationDto.getPloginId()))
			return;
		UserRelationDto urDto = null;
		if(!empty2){
			//新上级对象
			urDto = userRelationDaoImpl.findUserRDtoByLoginId(userRelationDto.getPloginId());
		}
		//首先将当前用户树的左右值搬迁到0的前面
		int curUserLv = userRelationDto.getLv();
		int curUserRv = userRelationDto.getRv();
		
		//MessageFormat.format("update is_users set l_v=l_v-{1,number,#}, r_v=r_v-{1,number,#} where l_v>={0,number,#} and r_v<={1,number,#} order by l_v asc", lv, rv);
		userRelationDaoImpl.changeNodeUpdate(curUserLv,curUserRv);
		
		//MessageFormat.format("update is_users set l_v=l_v-{0,number,#} where l_v>{1,number,#} order by l_v asc", curUserRv - curUserLv + 1, curUserRv);
		userRelationDaoImpl.changeNodeUpdateLV(curUserRv - curUserLv + 1, curUserRv);
		
		//MessageFormat.format("update is_users set r_v=r_v-{0,number,#} where r_v>{1,number,#} order by r_v asc", curUserRv - curUserLv + 1, curUserRv);
		userRelationDaoImpl.changeNodeUpdateRV(curUserRv - curUserLv + 1, curUserRv);
		
			
		//3.现在的领导为空; 将当前树搬迁到当前最大的右值后面
		int newLv = 1;
		if(empty2) {
			int maxRv = userRelationDaoImpl.getMaxRv();
			newLv = maxRv + 1;
		}
		//4.现在的领导不为空；增加现在领导右值及后面的值，将当前树搬迁到现在领导的节点下
		else {
			int offset1 = curUserRv - curUserLv + 1;
			if(urDto.getRv() > curUserRv){
				newLv = urDto.getRv()-offset1;
			}else{
				newLv = urDto.getRv();
			}
			
			//MessageFormat.format("update is_users set l_v=l_v+{0,number,#} where l_v>{1,number,#} order by l_v desc", offset1, newLv);
			userRelationDaoImpl.changeNodeUpdateLVForNP(offset1, newLv);
			
			//MessageFormat.format("update is_users set r_v=r_v+{0,number,#} where r_v>={1,number,#} order by r_v desc", offset1, newLv);
			userRelationDaoImpl.changeNodeUpdateRVForNP(offset1, newLv);
		}
		
		int offset = newLv - curUserLv + curUserRv;
		
		//MessageFormat.format("update is_users set l_v=l_v+{0,number,#} where l_v<=0 order by l_v desc", offset);
		userRelationDaoImpl.changeNodeUpdateLV(offset);
		
		//MessageFormat.format("update is_users set r_v=r_v+{0,number,#} where r_v<=0 order by r_v desc", offset);
		userRelationDaoImpl.changeNodeUpdateRV(offset);
		
		//更新除左右值外的其他信息
		UserRelationDto dto = userRelationDaoImpl.findUserRDtoByLoginId(userRelationDto.getLoginId());
		userRelationDto.setId(dto.getId());
		userRelationDto.setLayer(userRelationDaoImpl.countLayer(dto.getLv(), dto.getRv())-1);
		userRelationDaoImpl.update(userRelationDto);
		
	}

	@Override
	public void deleteUserRelation(long id) {
		UserRelationDto urDto = (UserRelationDto) userRelationDaoImpl.findById(id);
		if(null != urDto){
			int lv = urDto.getLv();
			int rv = urDto.getRv();
			userRelationDaoImpl.deleteNode(lv, rv);
			int offset = rv-lv+1;
			userRelationDaoImpl.deleteNodeUpdateLV(offset, rv);
			userRelationDaoImpl.deleteNodeUpdateRv(offset, rv);
		}
	}

	@Override
	public List<NodesDto> findAllSubLoginId(String loginId) {
		UserRelationDto urDto = userRelationDaoImpl.findUserRDtoByLoginId(loginId);
		if(null != urDto)
			return userRelationDaoImpl.findAllSubLoginId(urDto.getLv(),urDto.getRv());
		return null;
	}

	@Override
	public UserRelationDto findById(long id) {
		return (UserRelationDto) userRelationDaoImpl.findById(id);
	}
	
	@Override
	public List<UserRelationDto>  queryUserRelationByCondition(UserRelationDto userRelationDto,Page  page)
	{
		int totalCount=userRelationDaoImpl.countMemberRelationByCondition(userRelationDto);
	
		UserRelationDto dto=setPage(userRelationDto, page, totalCount);
		if(totalCount==0)
			return null;		
		return userRelationDaoImpl.queryMemberRelationByCondition(dto);
	}

	private UserRelationDto  setPage(UserRelationDto dto,Page  page,Integer totalCount){
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
        if (null == page || totalCount == 0) {
			  return null;
        }
        page.setTotalCount(totalCount);
        pageEndRow = page.getPageNo() * page.getPageSize();
        if ((page.getPageNo() - 1) == 0) {
        	pageStartRow = 0;
        }else {
        	pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
        }
        dto.setPageStartRow(pageStartRow);
        dto.setPageEndRow(pageEndRow);
		return dto;
	}

	@Override
	public UserRelationDto findUserRelatoinByLoginId(String loginId) {
		
		return userRelationDaoImpl.findUserRDtoByLoginId(loginId);
	}

	@Override
	public boolean isExistUser(String loginId) {
		
		return userRelationDaoImpl.isExistUser(loginId) > 0;
	}

	@Override
	public List<UserRelationDto> findByLayer(long id) {
		return userRelationDaoImpl.findByLayer(id);
	}

	@Override
	public List<NodesDto> findAll() {
		return userRelationDaoImpl.findAll();
	}

}
