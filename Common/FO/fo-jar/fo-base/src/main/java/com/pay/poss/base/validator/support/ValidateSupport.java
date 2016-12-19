package com.pay.poss.base.validator.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ResourceUtils;

import com.pay.poss.base.tool.ObjectUtils;
import com.pay.poss.base.validator.BaseValidator;

public class ValidateSupport implements InitializingBean {
	private Log logger = LogFactory.getLog(getClass());

	private Map<String, List<BaseValidator>> repository = new HashMap();
	private Map<String, String> typeRepository = new HashMap();

	private static final String SYS_VALIDATOR_TYPE = "classpath:com/huateng/platform/validator/validator_type.xml";
	private String validatorTypePath = "";
	private String validatorPath;

	public String validate(String field, Object fieldValue) {
		if (repository.containsKey(field)) {
			List<BaseValidator> validators = repository.get(field);
			for (BaseValidator validator : validators) {
				String result = validator.validate(fieldValue);
				if ("true".equals(result) == false) {
					return result;
				}
			}
		}
		return "true";
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.isEmpty(validatorPath) == true) {
			throw new IllegalArgumentException("validatorPath 属性必须不为空.");
		}

		// 读取validate type配置
		initValidateTypeRepository();

		// 读取配置并初始化容器
		initValidateRepository();
	}

	private void initValidateTypeRepository() {
		// <validator name="StringLength"
		// class="com.huateng.platform.validator.impl.StringLengthValidator" />
		SAXReader saxReader = new SAXReader();
		try {
			Document doc = saxReader.read(ResourceUtils.getURL(SYS_VALIDATOR_TYPE).openStream());
			List listValidatorType = doc.selectNodes("//validators/validator");
			for (Iterator iter = listValidatorType.iterator(); iter.hasNext();) {
				Element eleValidatorType = (Element) iter.next();
				String name = eleValidatorType.attributeValue("name");
				String className = eleValidatorType.attributeValue("class");

				if (logger.isDebugEnabled()) {
					logger.debug("注册validator [" + name + "=" + className + "]");
				}
				typeRepository.put(name, className);
			}

			if (StringUtils.isNotEmpty(validatorTypePath)) {
				Document extDoc = saxReader.read(ResourceUtils.getURL(SYS_VALIDATOR_TYPE).openStream());
				List extListValidatorType = extDoc.selectNodes("//validators/validator");
				for (Iterator extIter = extListValidatorType.iterator(); extIter.hasNext();) {
					Element extEleValidatorType = (Element) extIter.next();
					String name = extEleValidatorType.attributeValue("name");
					String className = extEleValidatorType.attributeValue("class");

					if (logger.isDebugEnabled()) {
						logger.debug("注册validator [" + name + "=" + className + "]");
					}
					typeRepository.put(name, className);
				}
			}
		} catch (Exception e) {
			logger.error("读取文件出错 [" + validatorTypePath + "]", e);
			throw new IllegalStateException("初始化失败 validate type容器处于错误状态");
		}
	}

	private void initValidateRepository() {
		Document doc;
		try {
			SAXReader saxReader = new SAXReader();
			doc = saxReader.read(ResourceUtils.getURL(validatorPath).openStream());
		} catch (Exception e) {
			logger.error("读取文件出错 [" + validatorPath + "]", e);
			throw new IllegalStateException("初始化失败 validate容器处于错误状态");
		}

		List listField = doc.selectNodes("//validators/field");
		for (Iterator iter = listField.iterator(); iter.hasNext();) {
			Element eleField = (Element) iter.next();
			String fieldName = eleField.attributeValue("name").trim();

			List dependValidators = eleField.selectNodes("./field-validator");
			List<BaseValidator> depends = new ArrayList();
			for (Iterator iter1 = dependValidators.iterator(); iter1.hasNext();) {
				Element eleValidator = (Element) iter1.next();
				String typeName = eleValidator.attributeValue("type");
				String typeClass = typeRepository.get(typeName);

				if (StringUtils.isEmpty(typeClass) == true) {
					logger.warn("还未注册此类型的validator,系统将忽略此validator [" + typeName + "]");
					continue;
				}

				Object instance = ObjectUtils.instanceByClass(typeClass);

				if (instance != null) {
					BaseValidator validator = (BaseValidator) instance;

					// 设置param
					List params = eleValidator.selectNodes("./param");
					for (Iterator iter2 = params.iterator(); iter2.hasNext();) {
						Element eleParam = (Element) iter2.next();
						validator.addParam(eleParam.attribute("index").getText().trim(), eleParam.getTextTrim());
					}

					// 设置type
					validator.setType(typeName);

					// 设置msgTemplate
					Element eleMsg = (Element) eleValidator.selectSingleNode("./message");
					if (eleMsg != null) {
						validator.setMessageTemplate(eleMsg.getTextTrim());
					}

					// 让validator有机会初始化
					validator.checkAfterPropertySet();

					depends.add(validator);
				} else {
					logger.warn("无法实例化,系统将忽略此validator [" + typeClass + "]");
					continue;
				}
			}

			// 绑定
			boundFieldAndValidator(fieldName, depends);
		}
	}

	/**
	 * 绑定字段和校验器
	 * 
	 * @param fieldName
	 *            绑定字段，形式field1,field2
	 * @param depends
	 *            List<IValidatable>
	 */
	private void boundFieldAndValidator(String fieldName, List<BaseValidator> depends) {
		String[] arrFieldName = fieldName.split("[,]");
		for (int i = arrFieldName.length - 1; i >= 0; i--) {

			List<BaseValidator> clones = new ArrayList<BaseValidator>();

			// 设置field
			for (BaseValidator validatable : depends) {
				try {
					BaseValidator cloneObject = validatable.clone();
					cloneObject.setFieldName(arrFieldName[i]);
					// cloneObject.checkAfterPropertySet();
					clones.add(cloneObject);
				} catch (Exception e) {
					logger.error("克隆Validator出现错误 [" + arrFieldName[i] + "]", e);
					continue;
				}
			}

			// 将field与依赖的validator关联
			repository.put(arrFieldName[i], clones);
		}
	}

	public void setValidatorTypePath(String validatorTypePath) {
		this.validatorTypePath = validatorTypePath;
	}

	public void setValidatorPath(String validatorPath) {
		this.validatorPath = validatorPath;
	}
}
