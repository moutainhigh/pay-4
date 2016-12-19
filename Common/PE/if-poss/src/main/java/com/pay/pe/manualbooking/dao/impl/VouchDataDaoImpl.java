package com.pay.pe.manualbooking.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.manualbooking.dao.VouchDataDao;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;

/**
 * 手工记帐申请数据访问实现
 */
public class VouchDataDaoImpl extends BaseDAOImpl<VouchData> implements VouchDataDao {	
	private static final Log LOG = LogFactory.getLog(VouchDataDaoImpl.class);			
	@SuppressWarnings("unchecked")
//	public List<VouchData> findVouchDatasWithPage(Page page, VouchSearchCriteria vouchCriteria) {
	public List<VouchData> findVouchDatasWithPage(VouchSearchCriteria vouchCriteria) {
		LOG.info("Start");
		
		
		VouchData vouchData = new VouchData();
		
		
		
		
		if (StringUtils.isNotEmpty(vouchCriteria.getVouchSeq())) {
			LOG.info("Build vouch application code criteria");
			//like
			vouchData.setApplicationCode(vouchCriteria.getVouchSeq().trim());			
		}
		
		
		if (StringUtils.isNotEmpty(vouchCriteria.getVouchCode())) {
			LOG.info("Build vouch code criteria");			
			//like
			vouchData.setVouchCode(vouchCriteria.getVouchCode()) ;
		
		}
		
		
		if (vouchCriteria.getVouchStatus() != null) {
			LOG.info("Build vouch status criteria");
			//==
			vouchData.setStatus(vouchCriteria.getVouchStatus());
		}
		
		if (vouchCriteria.getDateFrom() != null) {
			LOG.info("Build vouch create date from criteria");			
			//>==
			vouchData.setCreateDate(vouchCriteria.getDateFrom());
			
		}
		
		if (vouchCriteria.getDateTo() != null) {
			LOG.info("Build vouch create date to criteria");
			Date dateTo = new Date(vouchCriteria.getDateTo().getTime());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dateTo);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			dateTo = calendar.getTime();
			
//			dateToCriteria = ToplinkCriteriaFactory.lessThanEqual("createDate", dateTo);
		
		}		
		LOG.info("Build vouchDataId order desc criteria");		
		//orderByDesc("vouchDataId");
		
		LOG.info("End");
		return super.findBySelective(vouchData);
//		return super.findModelsByCriteria(page, list);
	}

	public VouchData getVouchDataById(Long id) {
		return (VouchData) super.findById(id);
	}

	public VouchData saveVouchData(VouchData vouchData) {
		//super.create(vouchData); 
		super.getSqlMapClientTemplate().insert("vouchdata.create", vouchData);
		
		List vdlist=super.findByTemplate("createId", vouchData.getApplicationCode());
		String vdId="";
		if(vdlist!=null && vdlist.size()>0){
			for(Iterator it=vdlist.iterator();it.hasNext();){
				VouchData v=(VouchData)it.next();
				vdId=v.getVouchDataId().toString();
			}
		}
		
		
		List list=vouchData.getVouchDetails();
		if(list!=null && list.size()>0){
			for(Iterator it=list.iterator();it.hasNext();){
				VouchDetailData vdd=(VouchDetailData)it.next();
				vdd.setVouchDataId(Long.valueOf(vdId));
				super.getSqlMapClientTemplate().insert("vouchdetail.create", vdd);
				
			}
		}
		return vouchData;
		//return (VouchData) super.save(vouchData);
	}

	public VouchData updateVouchData(VouchData vouchData) {
		super.update(vouchData);
		return  vouchData ;
//		return (VouchData) super.update(vouchData);
	}

	@SuppressWarnings("unchecked")
	public Class getModelClass() {
		return VouchData.class;
	}

}
