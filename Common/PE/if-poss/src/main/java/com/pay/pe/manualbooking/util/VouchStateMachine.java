package com.pay.pe.manualbooking.util;

/**
 * 
 * 手工记账申请状态机
 */
public class VouchStateMachine {
	/**
	 * @param currentStatus
	 * @param event
	 * @return
	 * 根据手工记账申请的当前状态和触发的事件，决定手工记账申请的下一个状态
	 */
	public static VouchDataStatus getNextStatus(VouchDataStatus currentStatus, VouchStatusChangeEvent event) {
		if (event == null) {
			throw new IllegalArgumentException("事件不能为空");
		}
		
		if (currentStatus == null && VouchStatusChangeEvent.CREATE.equals(event)) {
			return VouchDataStatus.UNAUDITED;
		}
		
		if (VouchDataStatus.UNAUDITED.equals(currentStatus)) {
			if (VouchStatusChangeEvent.APPROVE.equals(event)) {
				return VouchDataStatus.APPROVED;
			} else if (VouchStatusChangeEvent.REJECT.equals(event)) {
				return VouchDataStatus.REJECTED;
			} else if (VouchStatusChangeEvent.CANCEL.equals(event)) {
				return VouchDataStatus.CANCELED;
			} else {
				throw new IllegalArgumentException("参数不正确，无法决定期望状态");
			}
		} 
		
		if (VouchDataStatus.REJECTED.equals(currentStatus)) {
			if (VouchStatusChangeEvent.CANCEL.equals(event)) {
				return VouchDataStatus.CANCELED;
			} else {
				throw new IllegalArgumentException("参数不正确，无法决定期望状态");
			}
		} 
			
		throw new IllegalArgumentException("参数不正确，无法决定期望状态");
	}
}
