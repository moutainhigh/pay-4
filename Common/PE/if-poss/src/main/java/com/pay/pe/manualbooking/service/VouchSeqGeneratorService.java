package com.pay.pe.manualbooking.service;

/**
 * 
 * 手工记帐申请号产生器
 */
public interface VouchSeqGeneratorService {
	/**
	 * @return String
	 * 产生手工记账申请号
	 */
	String generatorSeq();
}
