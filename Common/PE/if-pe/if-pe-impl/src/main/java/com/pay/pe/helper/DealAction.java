
package com.pay.pe.helper;


public enum DealAction {
	/**
	 * 之保存交易数据.
	 */
	save(0),
	
	/**
	 * 创建.
	 */
	create(1),
	
	/**
	 * 记账.
	 */
	posting(2),
	
	/**
	 * 失败.
	 */
	fail(3),
	
	/**
	 * 取消交易.
	 */
	cancel(4),
	
	/**
	 * 充正.
	 */
	reverse(5),
	
	/**
	 * 充正Deal.
	 */
	reverseDeal(6);
	
	private int value;

    public int getValue() {
        return value;
    }

    DealAction(int value) {
        this.value = value;
    }
}
