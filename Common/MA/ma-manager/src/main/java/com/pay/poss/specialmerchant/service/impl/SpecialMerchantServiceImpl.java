package com.pay.poss.specialmerchant.service.impl;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.specialmerchant.dao.SpCardInfoDao;
import com.pay.poss.specialmerchant.dao.SpecialMerchantDao;
import com.pay.poss.specialmerchant.dto.SpecialMerchantDto;
import com.pay.poss.specialmerchant.service.SpecialMerchantService;
import com.pay.poss.specialmerchant.utils.FileProcessUtil;

import common.Assert;

public class SpecialMerchantServiceImpl implements SpecialMerchantService {
	private Log logger = LogFactory.getLog(SpecialMerchantServiceImpl.class);
	private String sp_pic_path;
	private String sp_pic_type;
	private String sp_show_path;
	public String getSp_show_path() {
		return sp_show_path;
	}

	public void setSp_show_path(String sp_show_path) {
		this.sp_show_path = sp_show_path;
	}

	private SpecialMerchantDao specialMerchantDao;
	private SpCardInfoDao spCardInfoDao;
	public SpecialMerchantDao getSpecialMerchantDao() {
		return specialMerchantDao;
	}

	public void setSpecialMerchantDao(SpecialMerchantDao specialMerchantDao) {
		this.specialMerchantDao = specialMerchantDao;
	}

	@Override
	public List<SpecialMerchantDto> querySpecialMerchantByPage(
			Map<String, Object> param) {
		return this.specialMerchantDao.querySpecialMerchantByPage(param);
	}

	@Override
	public int querySpecialMerchantCount(Map<String, Object> param) {
		return this.specialMerchantDao.querySpecialMerchantByQuery(param);
	}

	@Override
	public boolean isMerchantExist(String merchantName) {
		SpecialMerchantDto dto = new SpecialMerchantDto();
		dto.setSp_merchant_name(merchantName);
		List<SpecialMerchantDto> spList = specialMerchantDao.querySpecialMerchant(dto);
		if(spList != null && spList.size() > 0){
			return true;
		}
		return false;
	}
	@Override
	public Integer updateSpecialMerchantRdTx(SpecialMerchantDto dto,Map<String,InputStream> map) throws Exception {
		SpecialMerchantDto smdto = this.specialMerchantDao.querySpecialMerchantById(dto.getSp_merchant_id());
		if(smdto == null){
			throw new Exception("更新特约商户信息有误。");
		}
		if(!smdto.getSp_merchant_name().equals(dto.getSp_merchant_name())){//特约商户名称变化
			//查询商户名称是否已经存在
			SpecialMerchantDto existQueryDto = new SpecialMerchantDto();
			existQueryDto.setSp_merchant_name(dto.getSp_merchant_name());
			List<SpecialMerchantDto> existQueryList = this.specialMerchantDao.querySpecialMerchant(existQueryDto);
			if(existQueryList != null && existQueryList.size() > 0){
				throw new Exception("更新特约商户信息有误。");
			}
		}
		String sourceLogoName = smdto.getSp_merchant_logo();
		String spMerchantLogoName = null;//路径+图片名前缀
		String spMerchantLogo = null;//图片实际路径
		if(StringUtils.isEmpty(sourceLogoName)){//图片路径为空，设置图片路径
			if(map.size() > 0){
				//need a path
				String uuid = UUID.randomUUID().toString();
				spMerchantLogoName = this.getSp_pic_path() + "/" + uuid;
				spMerchantLogo = spMerchantLogoName + this.getSp_pic_type();
//				dto.setSp_merchant_logo(spMerchantLogo.replaceAll(this.getSp_pic_path(), this.getSp_show_path()));
				dto.setSp_merchant_logo(this.getSp_show_path() + "/" + uuid + this.getSp_pic_type());//图片显示路径
			}
		}else{//使用原有路径
			spMerchantLogo = sourceLogoName;
			String spMerchantLogoShowName = sourceLogoName.substring(0, sourceLogoName.lastIndexOf("."));
			spMerchantLogoName = spMerchantLogoShowName.replaceAll(this.getSp_show_path(), this.getSp_pic_path());//specialmerchant --> to /data/upload/specialmerchant
		}
		
//		String spMerchantLogo = this.getSp_pic_path() + "/" + dto.getSp_merchant_name() + this.getSp_pic_type();
//		dto.setSp_merchant_logo(spMerchantLogo);
		
		//add special merchant
		Integer specialMerchantId = specialMerchantDao.updateSpecialMerchant(dto);
		// 创建目录
		FileProcessUtil.createFileFolder(this.getSp_pic_path());
		InputStream imageBigStream  = map.get("imageBig");
		try {
			if(imageBigStream != null){
				String fileNameBig = spMerchantLogoName + "_big" + this.getSp_pic_type();
				FileProcessUtil.deleteFile(fileNameBig);
				//FileProcessUtil.writeFile(imageBigStream, fileNameBig);
				com.pay.poss.specialmerchant.utils.ImageUtil.scaleImageForStream(imageBigStream, fileNameBig);
				InputStream imageSmallStream = map.get("imageSmall");
				
				String fileNameSmall = spMerchantLogoName + this.getSp_pic_type();
				if(imageSmallStream != null){
					FileProcessUtil.deleteFile(fileNameSmall);
					//FileProcessUtil.writeFile(imageSmallStream, fileNameSmall);
					com.pay.poss.specialmerchant.utils.ImageUtil.imageConvertForStream(imageSmallStream,fileNameSmall);
				}else{
					if(!FileProcessUtil.fileIsExist(fileNameSmall)){
						com.pay.poss.specialmerchant.utils.ImageUtil.imageConvert(fileNameBig, fileNameSmall);//上传缩略图
					}
				}
			}else{
				InputStream imageSmallStream = map.get("imageSmall");
				String fileNameSmall = spMerchantLogoName + this.getSp_pic_type();
				if(imageSmallStream != null){
					FileProcessUtil.deleteFile(fileNameSmall);
					//FileProcessUtil.writeFile(imageSmallStream, fileNameSmall);
					com.pay.poss.specialmerchant.utils.ImageUtil.imageConvertForStream(imageSmallStream,fileNameSmall);
				}
			}
		}catch(Exception e){
			logger.error("IO流操作异常 [" + e.getMessage() + "]");
			throw new Exception("图片上传失败：" + e.getMessage());
		}
		return specialMerchantId;
	}
	@Override
	public Long insertSpecialMerchantRdTx(SpecialMerchantDto dto,Map<String,InputStream> map) throws Exception {
		String spMerchantLogoName = this.getSp_pic_path() + "/" + UUID.randomUUID().toString();//真实路径
		String spMerchantLogo = spMerchantLogoName + this.getSp_pic_type();//全路径
		if(map.size() > 0){
			String spShowPath = spMerchantLogo.replaceAll(this.getSp_pic_path(), this.getSp_show_path());//显示路径
			dto.setSp_merchant_logo(spShowPath);
		}
		//add special merchant
		Long specialMerchantId = specialMerchantDao.createSpecialMerchant(dto);
		// 创建目录
		FileProcessUtil.createFileFolder(this.getSp_pic_path());
		InputStream imageBigStream  = map.get("imageBig");
		try {
			if(imageBigStream != null){
				String fileNameBig = spMerchantLogoName+"_big" + this.getSp_pic_type();
				//FileProcessUtil.writeFile(imageBigStream, fileNameBig);
				com.pay.poss.specialmerchant.utils.ImageUtil.scaleImageForStream(imageBigStream, fileNameBig);
				InputStream imageSmallStream = map.get("imageSmall");
				
				String fileNameSmall = spMerchantLogoName + this.getSp_pic_type();
				if(imageSmallStream != null){
					//FileProcessUtil.writeFile(imageSmallStream, fileNameSmall);
					com.pay.poss.specialmerchant.utils.ImageUtil.imageConvertForStream(imageSmallStream,fileNameSmall);
				}else{
					com.pay.poss.specialmerchant.utils.ImageUtil.imageConvert(fileNameBig, fileNameSmall);//上传缩略图
				}
			}else{
				InputStream imageSmallStream = map.get("imageSmall");
				String fileNameSmall = spMerchantLogoName + this.getSp_pic_type();
				if(imageSmallStream != null){
					//FileProcessUtil.writeFile(imageSmallStream, fileNameSmall);
					com.pay.poss.specialmerchant.utils.ImageUtil.imageConvertForStream(imageSmallStream,fileNameSmall);
				}
			}
		}catch(Exception e){
			logger.error("IO流操作异常 [" + e.getMessage() + "]");
			throw new Exception("图片上传失败：" + e.getMessage());
		}
		return specialMerchantId;
	}

	
	@Override
	public SpecialMerchantDto querySpecialMerchantById(Long sp_merchant_id) {
		return specialMerchantDao.querySpecialMerchantById(sp_merchant_id);
	}

	@Override
	public boolean deleteSpecialMerchant(SpecialMerchantDto smDto) {
		SpecialMerchantDto spdto = specialMerchantDao.querySpecialMerchantById(smDto.getSp_merchant_id());
		//删除记录
		boolean delIf = specialMerchantDao.deleteById(smDto.getSp_merchant_id());
		spCardInfoDao.deleteByMerchantId(smDto.getSp_merchant_id());
		if(delIf){
//			String logoPath = spdto.getSp_merchant_logo();
			String logoShowPath = spdto.getSp_merchant_logo();
			if(StringUtils.isNotEmpty(logoShowPath)){//删除图片
				String logoPath = logoShowPath.replaceAll(this.getSp_show_path(), this.getSp_pic_path());
				//图片
				if(StringUtils.isNotEmpty(logoPath)){
					FileProcessUtil.deleteFile(logoPath);
					int idx = logoPath.lastIndexOf(".");
					if(idx >= 0){
						String logoPathBig = logoPath.substring(0, idx) + "_big" + logoPath.substring(idx);
						FileProcessUtil.deleteFile(logoPathBig);
					}
				}
			}
		}
		return delIf;
	}

	@Override
	public List<String> querySpecialMerchantCity() {
		List<String> cityCode = specialMerchantDao.querySpeicialMerchantCity();
		return cityCode;
	}
	public String getSp_pic_path() {
		return sp_pic_path;
	}

	public void setSp_pic_path(String sp_pic_path) {
		this.sp_pic_path = sp_pic_path;
	}

	public String getSp_pic_type() {
		return sp_pic_type;
	}

	public void setSp_pic_type(String sp_pic_type) {
		this.sp_pic_type = sp_pic_type;
	}

	public SpCardInfoDao getSpCardInfoDao() {
		return spCardInfoDao;
	}

	public void setSpCardInfoDao(SpCardInfoDao spCardInfoDao) {
		this.spCardInfoDao = spCardInfoDao;
	}

}
