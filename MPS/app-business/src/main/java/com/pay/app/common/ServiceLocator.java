package com.pay.app.common;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

public class ServiceLocator {

	//private static final String DEFAULT_CONFIG_LOCATION = "classpath*:/context/*.xml";

    private static final ApplicationContext CONTEXT = ContextLoader.getCurrentWebApplicationContext();

    /**
     * 取得一个服务实例，用于只有一个服务实例的场景
     * 
     * @param clazz 服务接口类型
     * @return 返回一个服务实例，如果找不到服务实例或一个服务接口存在多个服务实例，则抛出RuntimeException异常
     */
    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> clazz) {
    	
        Map<?, ?> beans = CONTEXT.getBeansOfType(clazz);
        if (beans == null || beans.isEmpty()) {
            throw new RuntimeException("No bean typed " + clazz.getName() + " is defined");
        }

        if (beans.size() > 1) {
            throw new RuntimeException("Too many beans typed " + clazz.getName()
                    + ", use getService(Class, String) instead");
        }

        return (T) beans.values().iterator().next();
    }

    /**
     * 取得一个服务实例，用于存在多个服务实例的场景
     * 
     * @param clazz 服务接口类型
     * @param serviceName 服务名称
     * @return 返回一个服务实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> clazz, String serviceName) {
        return (T) CONTEXT.getBean(serviceName);
}
}
