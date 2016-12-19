package com.pay.poss.base.tool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObjectUtils {
	private static final Log logger = LogFactory.getLog(ObjectUtils.class);

	public static Object instanceByClass(String className) {
		try {
			Class cmdClass = Class.forName(className.trim());
			return cmdClass.newInstance();
		} catch (ClassNotFoundException cnfe) {
			logger.error("cann't find class[" + className + "]", cnfe);
		} catch (InstantiationException ie) {
			logger.error("cann't instance for the class. [" + className + "]", ie);
		} catch (IllegalAccessException iae) {
			logger.error("cann't access for the class. [" + className + "]", iae);
		} catch (Exception e) {
			logger.error("other exception for the class. [" + className + "]", e);
		}
		return null;
	}
}
