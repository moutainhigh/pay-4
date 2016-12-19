package com.pay.risk.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户网站配置信息
 */
public class PartnerWebsiteConfig implements Serializable {
	
	private static final long serialVersionUID = 2083767964392626661L;

	/**
     * [主键]
     */
    private Long id;

    /**
     * 商户ID
     */
    private String partnerId;

    /**
     * 资金机构 [外键]
     */
    private String url;

    /**
     * 资金机构渠道 [外键]
     */
    private String ip;

    /**
     * 0：冻结<br>
	 * 1：正常<br>
	 * 2：待审核<br>
	 * 3：审核未通过<br>
	 * 4：已删除
     */
    private String status;

    private String operator;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createDate;
    
    private String sendCredorax;
    
    //状态集	update date 2016年6月16日15:00:08 delin
  	private String statusIn ;
    
    public String getSendCredorax() {
		return sendCredorax;
	}

	public void setSendCredorax(String sendCredorax) {
		this.sendCredorax = sendCredorax;
	}

    public String getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(String statusIn) {
		this.statusIn = statusIn;
	}

	/**
     * <b>获取</b> [主键]
     */
    public Long getId() {
        return id;
    }

    /**
     * <b>设置</b> [主键]
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <b>获取</b> 商户ID
     */
    public String getPartnerId() {
        return partnerId;
    }

    /**
     * <b>设置</b> 商户ID
     */
    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    /**
     * <b>获取</b> 资金机构 [外键]
     */
    public String getUrl() {
        return url;
    }

    /**
     * <b>设置</b> 资金机构 [外键]
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <b>获取</b> 资金机构渠道 [外键]
     */
    public String getIp() {
        return ip;
    }

    /**
     * <b>设置</b> 资金机构渠道 [外键]
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * <b>获取</b> 0：冻结<br>
	 * 1：正常<br>
	 * 2：待审核<br>
	 * 3：审核未通过<br>
	 * 4：已删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * <b>设置</b> 0：冻结<br>
	 * 1：正常<br>
	 * 2：待审核<br>
	 * 3：审核未通过<br>
	 * 4：已删除
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * <b>获取</b> 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <b>设置</b> 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <b>获取</b> 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <b>设置</b> 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}