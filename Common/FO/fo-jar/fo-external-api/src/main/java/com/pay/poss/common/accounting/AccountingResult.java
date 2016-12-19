/**
 * 
 */
package com.pay.poss.common.accounting;

/**
 * 更新余额结果
 * @author zliner
 *
 */
public enum AccountingResult {
	ACCOUNTING_SUCC(1,"更新余额成功"),
	ACCOUNTING_FAIL(0,"更新余额失败");
	//业务类型
	private final Integer result;
	//业务描述
    private final String description;
    /**
     * 私有构造方法
     * @param result
     * @param description
     */
    private AccountingResult(Integer result, String description) {
        this.result = result;
        this.description = description;
    }

    /**
     * @return Returns the code.
     */
    public Integer getResult() {
        return result;
    }

    /**
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * 通过枚举<code>businessType</code>获得枚举
     * @param businessType  
     * @return WithdrawBusinessType
     */
    public static AccountingResult getByResult(Integer result) {
        for (AccountingResult type : values()) {
            if (type.getResult().equals(result)) {
                return type;
            }
        }
        return null;
    }
}
