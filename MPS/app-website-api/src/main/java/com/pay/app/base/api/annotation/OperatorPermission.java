/*
 * woyo.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.base.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 操作员功能权限 <br>
 * 将该注解添加到需要控件操作员访问某方法前。<br>
 * 格式如:
 * <p> @OperatorPermission(operatPermission = "PermissionValue") <br>
 *	public ModelAndView method() </p>
 *  注： PermissionValue 为自定义的权限值，将该值添加到菜单下的功能中.URL值。<br>
 *  PermissionValue 将添加到ACC库的t_menu表中的URL字段值，该值必须唯一。 <br>
 *  在controller 的映射文件中的org.springframework.web.servlet.handler.SimpleUrlHandlerMapping bean下加入如下属性<br>
 *  <code><</code>property name="interceptors"><br>
 *           <code><</code>list>
 *               <code><</code>ref bean="operatPermInterceptor"/>
 *           <code><<code>/list><br>
 *  <code><</code>/property>
 * @author zhi.wang
 * @version $Id: OperatorPermission.java, v 0.1 2010-11-02 上午11:31:15 zhi.wang Exp $
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.ANNOTATION_TYPE, ElementType.METHOD,ElementType.TYPE})
public @interface OperatorPermission {
	public String operatPermission();
}
