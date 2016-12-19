package com.pay.txncore.crosspay.model;

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
     * 会员号 支持模糊查询
     */
    private String memberCode;

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
    
    
    /**
	 * 开始时间
	 */
	private String startTime;
	
	/**
	 * 结束时间
	 */
	private String endTime;
	
	//状态集
	private String statusIn ;
	//url查询匹配模式（默认模糊查询）：精确查询 ｜ 模糊查询
	private String urlQueryModel ;
	
	private String siteId;
	//网站类别
	private String category;
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getSendCredorax() {
		return sendCredorax;
	}

	public void setSendCredorax(String sendCredorax) {
		this.sendCredorax = sendCredorax;
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

	/**
	 * @return the statusIn
	 */
	public String getStatusIn() {
		return statusIn;
	}

	public void setStatusIn(String statusIn) {
		this.statusIn = statusIn;
	}

	/**
	 * @return the urlQueryModel
	 */
	public String getUrlQueryModel() {
		return urlQueryModel;
	}

	public void setUrlQueryModel(String urlQueryModel) {
		this.urlQueryModel = urlQueryModel;
	}
	
    
}