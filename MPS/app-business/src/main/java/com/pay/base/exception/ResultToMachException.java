/**
 * 
 */
package com.pay.base.exception;

import com.pay.inf.exception.AppException;

/**
 * 记录过的时的异常
 * @author DDR
 * Date 2011-9-23
 */
public class ResultToMachException extends AppException {

	private int countMax = 5000;
	/**
	 * @param args
	 * @author DDR
	 */
	public ResultToMachException(){
		 super("记录数量超过最大限制!");
	}


    public ResultToMachException(String message) {
        super(message);
    }


	/**
	 * @return the countMax
	 */
	public int getCountMax() {
		return countMax;
	}


	/**
	 * @param countMax the countMax to set
	 */
	public void setCountMax(int countMax) {
		this.countMax = countMax;
	}
    
    
}
