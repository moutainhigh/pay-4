/**
 * 
 */
package com.pay.rm.exceptioncard.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.rm.exceptioncard.dao.ExceptionCardDao;
import com.pay.rm.exceptioncard.dto.AweekCount;
import com.pay.rm.exceptioncard.dto.ExceptionCardDTO;
import com.pay.rm.exceptioncard.service.ExceptionCardService;
import com.pay.util.DateUtil;

/**
 * @author Jiangbo.peng
 *
 */
public class ExceptionCardServiceImpl implements ExceptionCardService {
	
	private static Log logger = LogFactory.getLog(ExceptionCardServiceImpl.class) ;

	//注入
	private ExceptionCardDao exceptionCardDao ;

	/**
	 * @param exceptionCardDao the exceptionCardDao to set
	 */
	public void setExceptionCardDao(ExceptionCardDao exceptionCardDao) {
		this.exceptionCardDao = exceptionCardDao;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long createExceptionCardTrans(ExceptionCardDTO exceptionCardDTO) {
		return (Long) this.exceptionCardDao.create(exceptionCardDTO);
	}

	@Override
	public ExceptionCardDTO queryExceptionCardDTO(Map<String, Object> hMap) {
		return (ExceptionCardDTO) this.exceptionCardDao.findObjectByCriteria(hMap) ;
	}

	@Override
	public void saveOrUpdate(ExceptionCardDTO exceptionCardDTO) {
		logger.info("risk.T_EXCEPTION_CARD svae or update执行开始..........................");
		try {
			Map<String, Object> hMap = new HashMap<String, Object>() ;
			//当天当前时间段记录是否存在
			//构造时间参数
			Long memberCode = exceptionCardDTO.getMemberCode() ;
			hMap.put("timeZoneBetween", "true") ;
			hMap.put("timeBetween", "true") ;
			hMap.put("memberCode", memberCode) ;
			this.getDayStart2EndStr(hMap);
			ExceptionCardDTO queryExceptionCard = this.queryExceptionCardDTO(hMap) ;
			//取出异常卡标志
			boolean flag = exceptionCardDTO.isExceptionCardFlag() ;
			if(null == queryExceptionCard){	//新增
				logger.info("is新增......");
				//时间区间
			    String hours = (String) hMap.get("hours") ;
				String timeZone = hours + ":00" + "-" + hours + ":59" ;
				//统计前七日分时比例
				//if(query)
				String dayStart = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), -7)) ;
				String dayEnd = DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", DateUtil.skipDateTime(DateUtil.getTheDate(new Date()), 0)) ;
				Map<String, Object> aweekCountMap = new HashMap<String, Object>() ;
				aweekCountMap.put("dayStart", dayStart) ;
				aweekCountMap.put("dayEnd", dayEnd) ;
				aweekCountMap.put("timeZone", timeZone) ;
				aweekCountMap.put("memberCode", memberCode) ;
				AweekCount queryAweekCount = this.queryAweekCount(aweekCountMap) ;
				Long aweekExceptionCardCount = null == queryAweekCount.getAweekExceptionCardCount() ? 0L :queryAweekCount.getAweekExceptionCardCount() ;
//				Long aweekFailOrderCount = null == queryAweekCount.getAweekFailOrderCount() ? 0L : queryAweekCount.getAweekFailOrderCount() ;
				Long aweekFailOrderCount = queryAweekCount.getAweekFailOrderCount() ;
				String aweekAgoTimeZonePercent = "" ;
				if(null == aweekFailOrderCount || 0 == aweekFailOrderCount){
					aweekAgoTimeZonePercent = "0.00%" ;
				}else{
					//计算前七日比例
					BigDecimal divideAweekResult = new BigDecimal(aweekExceptionCardCount).divide(new BigDecimal(aweekFailOrderCount), 4, BigDecimal.ROUND_HALF_EVEN) ;
					//计算前七日比例
					//BigDecimal divideAweekResult = new BigDecimal(aweekExceptionCardCount).divide(new BigDecimal(aweekFailOrderCount), 4, BigDecimal.ROUND_HALF_EVEN) ;
					DecimalFormat df = new DecimalFormat("0.00%") ;
					aweekAgoTimeZonePercent = df.format(divideAweekResult) ;
				}
				
				//丰富异常卡对象
				//设置前七日分时比例
				exceptionCardDTO.setAweekAgoTimeZonePercent(aweekAgoTimeZonePercent);
				//设置状态，首次记录为正常
				//exceptionCardDTO.setStatus("N");
				exceptionCardDTO.setTime(new Timestamp(System.currentTimeMillis()));
				exceptionCardDTO.setTimeZone(timeZone);
				if(flag){//异常卡
					exceptionCardDTO.setExceptionCardCount(1L); //新增从1开始
					exceptionCardDTO.setExceptionCardPercent("100.00%"); //新增为100%
					exceptionCardDTO.setStatus("E");//预警
				}else{//非异常卡
					exceptionCardDTO.setExceptionCardCount(0L); //新增从0开始
					exceptionCardDTO.setExceptionCardPercent("0.00%"); //新增为100%
					exceptionCardDTO.setStatus("N");//正常
				}
				exceptionCardDTO.setFailOrderCount(1L); //新增从1开始
				
				this.createExceptionCardTrans(exceptionCardDTO) ;
			}else{	//修改
				logger.info("is更新...");
				//异常卡笔数
				Long exceptionCardCount = queryExceptionCard.getExceptionCardCount() ;
				//失败订单笔数
				Long failOrderCount = queryExceptionCard.getFailOrderCount() ;
				exceptionCardDTO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
				//exception
				//异常卡则更新异常卡笔数,否则不更新异常卡笔数
				if(flag){
					exceptionCardCount = exceptionCardCount + 1 ;
					exceptionCardDTO.setExceptionCardCount(exceptionCardCount);
				}
				failOrderCount = failOrderCount + 1 ;
				exceptionCardDTO.setFailOrderCount(failOrderCount);
				//计算异常比例
				BigDecimal divideResult = new BigDecimal(exceptionCardCount).divide(new BigDecimal(failOrderCount), 4, BigDecimal.ROUND_HALF_EVEN) ;
				DecimalFormat df = new DecimalFormat("0.00%") ;
				String exceptionCardPercent = df.format(divideResult) ;
				//七天之前的异常卡比例
				String _aweekAgoTimeZonePercent = queryExceptionCard.getAweekAgoTimeZonePercent() ;
				String _exceptionCardPercent = null == exceptionCardPercent ? "0.00" : exceptionCardPercent.substring(0, exceptionCardPercent.length() -1) ;
				_aweekAgoTimeZonePercent = null == _aweekAgoTimeZonePercent ? "0.00" : _aweekAgoTimeZonePercent.substring(0, _aweekAgoTimeZonePercent.length()-1) ;
				double doubleValue = new BigDecimal(_exceptionCardPercent).subtract(new BigDecimal(_aweekAgoTimeZonePercent)).doubleValue() ;
				if(doubleValue > 10 || exceptionCardCount > 20){
					//E预警状态
					exceptionCardDTO.setStatus("E");
				}else{
					//正常
					if(!"N".equals(exceptionCardDTO.getStatus())){
						exceptionCardDTO.setStatus("N");
					}
				}
				exceptionCardDTO.setExceptionCardPercent(exceptionCardPercent);
				exceptionCardDTO.setId(queryExceptionCard.getId());
				this.updateExceptionCardTrans(exceptionCardDTO) ;
			}
			logger.info("risk.T_EXCEPTION_CARD svae or update执行结束..........................");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateExceptionCardTrans(ExceptionCardDTO exceptionCardDTO) {
		return this.exceptionCardDao.update(exceptionCardDTO) ;
	}
	
	@Override
	public List<ExceptionCardDTO> queryExceptionCardList(
			Map<String, Object> hMap) {
		return this.exceptionCardDao.findByCriteria(hMap) ;
	}
	
	/**
	 * 
	 * @param hMap
	 * @return
	 */
	@Override
	public AweekCount queryAweekCount(Map<String, Object> hMap) {
		return (AweekCount) this.exceptionCardDao.findObjectByCriteria("queryAweekCount", hMap) ;
	}
	
	/**
	 * 获取当前日的开始和结束时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void getDayStart2EndStr(Map<String, Object> map){
		try {
			//Map<String, Object> dayStrMap = new HashMap<String, Object>() ;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			//获取当天开始时间
			Calendar c1 = Calendar.getInstance() ;
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			String dayStart = df.format(c1.getTime()) ;
			//获取当天结束时间
			Calendar c2 = Calendar.getInstance() ;
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);
			String dayEnd = df.format(c2.getTime()) ;
			//获取当前时间小时区间
			Date now = new Date() ;
			String hours = now.getHours()+"" ;
			String hourStart = hours + ":00" ;
			String hourEnd = hours + ":59" ;
			map.put("hours", hours) ;
			map.put("dayStart", dayStart) ;
			map.put("dayEnd", dayEnd) ;
			map.put("hourStart", hourStart) ;
			map.put("hourEnd", hourEnd) ;
		} catch (Exception e) {
			logger.info("构造查询t_exception_card所需要的时间参数出错了!");
			e.printStackTrace();
		}
	    //return map ;
	}


}
