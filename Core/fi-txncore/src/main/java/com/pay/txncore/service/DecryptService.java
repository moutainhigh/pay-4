package com.pay.txncore.service;

import com.googlecode.aviator.AviatorEvaluator;
import com.pay.txncore.dto.Condition;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * Created by cuber.huang on 2016/7/27.
 */
public class DecryptService {
    private Map<String,String> decryptMatchs;
    private Map<String,List<Condition>> allDecrypts = new HashMap<String, List<Condition>>();

    public void setDecryptMatchs(Map<String, String> decryptMatchs) {
        this.decryptMatchs = decryptMatchs;
        if(decryptMatchs != null && decryptMatchs.size() > 0){
            Set<String> orgCodes = decryptMatchs.keySet();
            for (String orgCode:
                    orgCodes ) {
                String xmlLocation = decryptMatchs.get(orgCode);
                if(allDecrypts.containsKey(xmlLocation)){
                    continue;
                }else{
                    List<Condition> conditions = getConditionByXmlLocation(xmlLocation);
                    allDecrypts.put(xmlLocation, conditions);
                }
            }
        }
    }

    public  List<Condition> getConditionByXmlLocation(String xmlLocation) {
        List<Condition> conditions = null;
        try{
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(DecryptService.class.getClassLoader().getResourceAsStream(xmlLocation));
            //获取根节点元素对象
            Element root = document.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            conditions =  new ArrayList<Condition>();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                Condition condition = new Condition();
                condition = builderAttribute(element,condition);
                conditions.add(condition);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return conditions;
    }

    public static <T> T  builderAttribute(Element element, T t){
        if(t != null && null != element){
            List<Attribute> list = element.attributes();
            BeanWrapper bean = new BeanWrapperImpl(t);
            for (Attribute attribute:list
                    ) {
                PropertyDescriptor pro = bean.getPropertyDescriptor(attribute.getName());
                if(pro!=null){
                    bean.setPropertyValue(pro.getName(),attribute.getValue());
                }
            }
        }
        return t;
    }

    public Condition getFixCondition(String orgCode, String returnCode ){
        Condition condition = new Condition();
        condition.setReturnCode("5556");//没有出现错误信息
        condition.setReturnMsg("unkown errors");
        condition.setReturnMsgCn("未知错误");
        Map<String,Object> env = new HashMap<String, Object>();
        env.put("Code",returnCode);
        if(decryptMatchs.containsKey(orgCode)){
            String xmlLocation = decryptMatchs.get(orgCode);
            List<Condition> conditions = allDecrypts.get(xmlLocation);
            if(null != conditions &&  conditions.size() > 0){
                for (Condition conditionTest : conditions) {
                    Boolean fitExpression = (Boolean)AviatorEvaluator.execute(conditionTest.getExpression(),env);
                    if(fitExpression)
                        return conditionTest;
                }
            }
            return null;
        }
        return null;
    }
}
