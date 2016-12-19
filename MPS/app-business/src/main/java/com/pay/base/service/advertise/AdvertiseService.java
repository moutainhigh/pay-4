package com.pay.base.service.advertise;

import java.util.List;

import com.pay.base.dto.AdvertisementDTO;
import com.pay.base.model.Advertisement;





public interface AdvertiseService{
	
	/**
	 * 查询所有相同位置的广告的数量
	 * @param targets 广告位置
	 * @return
	 */
	public Integer getCountByLocation(Integer targets);
	
	/**
	 * 查询广告相信信息根据
	 * @param advId 广告id
	 * @return
	 */
	public AdvertisementDTO queryAdvertiseById(Long advId);
	
	/**
	 * 新增广告
	 * @param advertise
	 * @return
	 */
	public long creatAdvertise(AdvertisementDTO advertiseDto);
	
	/**
	 * 更新广告
	 * @param advertise
	 * @return
	 */
	public boolean updateAdvertise(AdvertisementDTO advertiseDto);
	
	/**
	 * 查询广告列表根据广告位置（所有）
	 * @param targets
	 * @return
	 */
	public List<Advertisement> queryAdvertiseListByTargets(Integer targets);
	
	/**
	 * 查询广告列表根据广告位置
	 * @param targets 位置
	 * @param avail 是否有效 1有效，0为无效
	 * @return
	 */
	public List<Advertisement> queryAdvertiseListByTargetsAvail(Integer targets,Integer avail);
	
	/**
	 * 更新sort根据位置
	 * @param location
	 * @param sort
	 * @return
	 */
	public boolean updateSortById(Long id,Integer sort);
	
	/**
	 * 交换更新sort
	 * @param location
	 * @param id1
	 * @param sort1
	 * @param sort2
	 * @return
	 */
	public boolean doUpdateSortRnTx(Integer location,Long id1,Integer sort1,Integer sort2);
	
	/**
	 * 根据广告位置，和sort查询广告
	 * @param location
	 * @param sort
	 * @return
	 */
	public Advertisement queryAdvertisetByLocSort(Integer location,Integer sort);
	
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	public boolean delAdvertisetById(Long id);
	
	/**
	 * 删除后更新一下的sort
	 * @param location
	 * @param sort
	 * @param count
	 * @return
	 */
	public boolean doUpdateSortNextRnTx(Integer location,Integer sort,Integer count);
}
