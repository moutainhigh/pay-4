
package com.pay.pe.helper;


public enum DealStatus {
	/**
	 * 创建成功.
	 */
	created(0),
	
	/**
	 * *记账成功.
	 */
	posted(1),
	
	/**
	 * 失败.
	 */
	failed(2),
	
	/**
	 * 已取消.
	 */
	canceled(3),
	
	/**
	 * 已充正.
	 */
	reversed(4);
	
	private int value;

    public int getValue() {
        return value;
    }

    DealStatus(int value) {
        this.value = value;
    }
}
