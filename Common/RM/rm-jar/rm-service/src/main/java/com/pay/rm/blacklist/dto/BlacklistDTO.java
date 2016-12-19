package com.pay.rm.blacklist.dto;

import java.util.Date;

public class BlacklistDTO {

	private Long hmdid; // 负面信息id
	private String hmdlx; // 负面信息类型(1个人负面信息/2机构负面信息)
	private String cydwbm; // 成员单位编码
	private String gmsfhm; // 公民身份号码/法人代表公民身份号码
	private String xm; // 姓名/法人代表姓名
	private String fsdq; // 发生地区
	private String lhtj; // 录黑途径
	private String hmdsj; // 负面信息事件
	private String hmdsjbz; // 负面信息事件备注
	private String sjhm; // 手机号码
	private String yhkh; // 银行卡号
	private String khh; // 开户行
	private String gddh; // 固定电话号码
	private String ip; // ip地址
	private String mac; // mac地址
	private String email; // 邮箱
	private String dz; // 地址
	private String jgmc; // 机构名称
	private String urldz; // url地址
	private String urltzdz; // url跳转地址
	private String yyzzbh;// 营业执照编号
	private String icp; // icp
	private String icpbar; // icp备案人
	private String zfr;// 支付人
	private String zzjgdm;// 组织机构代码
	private Date bjsj; // 标记时间
	private String bjsjStr;
	private String sjzt; // 数据状态(名单类型)
	private String czr; // 操作人
	private String ywlb; // 业务类别
	private String status; // 状态(0正常1审核拒绝2新建待审核3修改待审核4删除待审核5上传待审核)
	private Integer uploadcount;
	private String remark;// 备注
	private String[] choose;// 批量操作

	public String[] getChoose() {
		return choose;
	}

	public void setChoose(String[] choose) {
		this.choose = choose;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBjsjStr() {
		return bjsjStr;
	}

	public void setBjsjStr(String bjsjStr) {
		this.bjsjStr = bjsjStr;
	}

	public Long getHmdid() {
		return hmdid;
	}

	public void setHmdid(Long hmdid) {
		this.hmdid = hmdid;
	}

	public String getHmdlx() {
		return hmdlx;
	}

	public void setHmdlx(String hmdlx) {
		this.hmdlx = hmdlx;
	}

	public String getCydwbm() {
		return cydwbm;
	}

	public void setCydwbm(String cydwbm) {
		this.cydwbm = cydwbm;
	}

	public String getGmsfhm() {
		return gmsfhm;
	}

	public void setGmsfhm(String gmsfhm) {
		this.gmsfhm = gmsfhm;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getFsdq() {
		return fsdq;
	}

	public void setFsdq(String fsdq) {
		this.fsdq = fsdq;
	}

	public String getLhtj() {
		return lhtj;
	}

	public void setLhtj(String lhtj) {
		this.lhtj = lhtj;
	}

	public String getHmdsj() {
		return hmdsj;
	}

	public void setHmdsj(String hmdsj) {
		this.hmdsj = hmdsj;
	}

	public String getHmdsjbz() {
		return hmdsjbz;
	}

	public void setHmdsjbz(String hmdsjbz) {
		this.hmdsjbz = hmdsjbz;
	}

	public String getSjhm() {
		return sjhm;
	}

	public void setSjhm(String sjhm) {
		this.sjhm = sjhm;
	}

	public String getYhkh() {
		return yhkh;
	}

	public void setYhkh(String yhkh) {
		this.yhkh = yhkh;
	}

	public String getKhh() {
		return khh;
	}

	public void setKhh(String khh) {
		this.khh = khh;
	}

	public String getGddh() {
		return gddh;
	}

	public void setGddh(String gddh) {
		this.gddh = gddh;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

	public String getJgmc() {
		return jgmc;
	}

	public void setJgmc(String jgmc) {
		this.jgmc = jgmc;
	}

	public String getUrldz() {
		return urldz;
	}

	public void setUrldz(String urldz) {
		this.urldz = urldz;
	}

	public String getUrltzdz() {
		return urltzdz;
	}

	public void setUrltzdz(String urltzdz) {
		this.urltzdz = urltzdz;
	}

	public String getYyzzbh() {
		return yyzzbh;
	}

	public void setYyzzbh(String yyzzbh) {
		this.yyzzbh = yyzzbh;
	}

	public String getIcp() {
		return icp;
	}

	public void setIcp(String icp) {
		this.icp = icp;
	}

	public String getIcpbar() {
		return icpbar;
	}

	public void setIcpbar(String icpbar) {
		this.icpbar = icpbar;
	}

	public String getZfr() {
		return zfr;
	}

	public void setZfr(String zfr) {
		this.zfr = zfr;
	}

	public String getZzjgdm() {
		return zzjgdm;
	}

	public void setZzjgdm(String zzjgdm) {
		this.zzjgdm = zzjgdm;
	}

	public Date getBjsj() {
		return bjsj;
	}

	public void setBjsj(Date bjsj) {
		this.bjsj = bjsj;
	}

	public String getSjzt() {
		return sjzt;
	}

	public void setSjzt(String sjzt) {
		this.sjzt = sjzt;
	}

	public String getCzr() {
		return czr;
	}

	public void setCzr(String czr) {
		this.czr = czr;
	}

	public String getYwlb() {
		return ywlb;
	}

	public void setYwlb(String ywlb) {
		this.ywlb = ywlb;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getUploadcount() {
		return uploadcount;
	}

	public void setUploadcount(Integer uploadcount) {
		this.uploadcount = uploadcount;
	}

}
