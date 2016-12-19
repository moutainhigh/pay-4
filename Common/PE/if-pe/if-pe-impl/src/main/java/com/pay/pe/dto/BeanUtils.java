
package com.pay.pe.dto;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

import com.pay.inf.dto.Dto;
import com.pay.inf.dto.DtoUtil;
import com.pay.inf.dto.DtoUtilFactoryImpl;
import com.pay.inf.model.Model;


public final class BeanUtils extends org.springframework.beans.BeanUtils {
	/**
	 * 日志.
	 */
	private static Log logger = LogFactory.getLog(BeanUtils.class);
	
    /**
     * Default constructor.
     */
    public BeanUtils() {
        super();
    }

    /**
     * Copy the property values of the given source bean into the target bean.
     * <p>Note: The source and target classes do not have to match or even be derived
     * from each other, as long as the properties match. Any bean properties that the
     * source bean exposes but the target bean does not will silently be ignored.
     * <p>This is just a convenience method. For more complex transfer needs,
     * consider using a full BeanWrapper.
     * @param source the source bean
     * @param target the target bean
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    public static void copyProperties(Object source, Object target, boolean model2dto, DtoUtil util) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class actualEditable = target.getClass();

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

        for (int i = 0; i < targetPds.length; i++) {
            PropertyDescriptor targetPd = targetPds[i];
            if (targetPd.getWriteMethod() != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null && sourcePd.getReadMethod() != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                        readMethod.setAccessible(true);
                    }
                    Object value;
                    try {
                        value = readMethod.invoke(source, new Object[0]);
                    } catch (Exception e1) {
                        //没有get方法，不拷贝此字段内容
                        continue;
                    }
                    Method writeMethod = targetPd.getWriteMethod();
                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                        writeMethod.setAccessible(true);
                    }
                    //Copy from model to dto
                    if (model2dto) {
                    	copyModel2Dto(writeMethod, target, value);
                    } else {
                    	copyDto2Model(writeMethod, target, value);
                    }
                }
            }
        }
    }

    public static void copyProperties4Model(Object source, Object target){
    	Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);

		for (int i = 0; i < targetPds.length; i++) {
			PropertyDescriptor targetPd = targetPds[i];
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
//						
//						getPrimaryKey
						if(!"getPrimaryKey".equals(readMethod.getName())){
							if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object value = readMethod.invoke(source, new Object[0]);
							Method writeMethod = targetPd.getWriteMethod();
							if(!"setPrimaryKey".equals(writeMethod.getName())){
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, new Object[] {value});
							}
						}
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
    }
    
    /**
     * 从Model拷贝到Dto
     * @param writeMethod Method
     * @param target Object
     * @param value Object
     */
    private static void copyModel2Dto(final Method writeMethod, final Object target, final Object value) {
        //集合 实体在表中的关系为一对多.
        if (value instanceof Collection) {
            if (null==value || ((Collection)value).size()==0) {
                try {
                    writeMethod.invoke(target, new Object[] {null});
                } catch (Exception e) {
                	logger.error(e);
                }
            } else {
                Object temp = ((Collection)value).iterator().next();
                if (temp instanceof Model) {
                    try {
                        DtoUtil tempUtil = DtoUtilFactoryImpl.getInstance().getDtoUtil(temp.getClass());
                        if (value instanceof Set) {
                        	writeMethod.invoke(target, new Object[] {tempUtil.convert2Dtos((Set)value)});
                        } else {
                        	writeMethod.invoke(target, new Object[] {tempUtil.convert2Dtos((List)value)});
                        }
                    } catch (Exception e) {
                    	logger.error(e);
                    }
                }
            }
        } else if (value instanceof Model) {
            //实体在表中的关系为一对一.
            try {
                DtoUtil tempUtil = DtoUtilFactoryImpl.getInstance().getDtoUtil(value.getClass());
                writeMethod.invoke(target, new Object[] {tempUtil.convert2Dto((Model)value)});
            } catch (Exception e) {
            	logger.error(e);
            }
        } else {
	        try {
	            writeMethod.invoke(target, new Object[] {value});
	        } catch (Exception e) {
	            //DTO中存在不是字符串参数的SET方法，略过此字段的拷贝;
	        }
        }
    }
    
    /**
     * 从Dto拷贝到Model
     * @param writeMethod 写方法
     * @param target Object
     * @param value Object
     */
    private static void copyDto2Model(final Method writeMethod, final Object target, final Object value) {
		Class clazz = writeMethod.getParameterTypes()[0];
		if (Model.class.isAssignableFrom(clazz)) {
			//实体在表中的关系为一对一.
			try {
				DtoUtil tempUtil = DtoUtilFactoryImpl.getInstance().getDtoUtil(value.getClass());
				writeMethod.invoke(target, new Object[] { tempUtil.convert2Model((Dto) value) });
			} catch (Exception e) {
				logger.error(e);
			}
		} else if (Collection.class.isAssignableFrom(clazz)) {
			//集合 实体在表中的关系为一对多.
			if (null == value || ((Collection) value).size() == 0) {
				try {
					writeMethod.invoke(target, new Object[] { null });
				} catch (Exception e) {
					logger.error(e);
				}
			} else {
				Object temp = ((Collection) value).iterator().next();
				if (temp instanceof Dto) {
					try {
						DtoUtil tempUtil = DtoUtilFactoryImpl.getInstance()
								.getDtoUtil(temp.getClass());
						if (value instanceof Set) {
							writeMethod.invoke(target, new Object[] { tempUtil
									.convert2Models(((Set) value)) });
						} else {
							writeMethod.invoke(target, new Object[] { tempUtil
									.convert2Models(((List) value)) });
						}
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		} else {
			try {
				writeMethod.invoke(target, new Object[] { value });
			} catch (Exception e) {
				System.out.println("target:" + target.getClass() + "; value: " + value.getClass() + "; writeMethod:" + writeMethod.getName());
				logger.error(e);
			}
		}
	}
}
