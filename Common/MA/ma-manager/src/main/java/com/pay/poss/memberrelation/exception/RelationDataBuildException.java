package com.pay.poss.memberrelation.exception;

/**
 * 关联名单构建异常
 */
public class RelationDataBuildException extends RelationDataException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5056945168197825573L;

	public RelationDataBuildException(String message, Throwable t) {
		super(message, t);
	}
	
	public RelationDataBuildException(String message) {
		super(message);
	}
	
	public RelationDataBuildException(Throwable t) {
		super(t);
	}
}
