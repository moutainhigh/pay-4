package com.pay.poss.membermanager.model;

import java.util.List;

import com.pay.inf.model.BaseObject;
import com.pay.poss.featuremenu.model.Menu;


/**
 * 产品表
 * @Description 
 * Date				Author			Changes
 * 2012-7-16			DDR				Create
 * ID	NUMBER(5)	N			产品代码
NAME	VARCHAR2(60)	N			产品名称
DESCRIPTION	VARCHAR2(180)	Y			产品描述
ALLOW_OBJECT	NUMBER(1)	N			产品适用对象 产品类别 0:ALL 1:个人产品;2:企业产品
IMMEDIACY_PASS	NUMBER(1)	N			产品状态:1.产品上线2.产品下线
TYPE	NUMBER(1)	N			产品类型（1、人民币产品  2、积分产品 3:其它产品）
IS_DEFAULT	NUMBER(1)	N			是否默认产品：0、不是默认 1、默认
ACTIVATION_MODE	NUMBER(1)	N			产品激活方式(1、生成密钥后激活  2、自动激活)
PRODUCT_CODE	VARCHAR2(64)	Y			产品代码
VALUE1	VARCHAR2(64)	Y			备用1
VALUE2	VARCHAR2(64)	Y			备用2
PRODUCT_TYPE	NUMBER(2)	Y	99		1-分账产品 2-付款产品 3-商旅卡产品 4-提现产品 5-手机支付产品 6-支付链产品   99-其他 
PRODUCT_ORDER	NUMBER(3)	Y	100		产品顺序
 */
public class Product extends BaseObject{

	private static final long serialVersionUID = 1L;
	private Long id;
    private String name;
    private String description;
    private Integer allowObject;
    private Integer immediacyPass;
    private Integer type = 1;//在很长一段时间 只有1
    private Integer isDefault;
    private Integer activationMode;
    private String productCode;
    private Integer productType;
    private Integer productOrder; 	
    
    private List<Menu> menuList;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getAllowObject() {
		return allowObject;
	}
	public void setAllowObject(Integer allowObject) {
		this.allowObject = allowObject;
	}
	public Integer getImmediacyPass() {
		return immediacyPass;
	}
	public void setImmediacyPass(Integer immediacyPass) {
		this.immediacyPass = immediacyPass;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public Integer getActivationMode() {
		return activationMode;
	}
	public void setActivationMode(Integer activationMode) {
		this.activationMode = activationMode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public List<Menu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public Integer getProductOrder() {
		return productOrder;
	}
	public void setProductOrder(Integer productOrder) {
		this.productOrder = productOrder;
	}

	
	
   
}