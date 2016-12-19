package com.pay.gateway.service.impl;

import com.googlecode.aviator.AviatorEvaluator;
import com.pay.gateway.dto.ValidateResponse;
import com.pay.gateway.model.Column;
import com.pay.gateway.model.Condition;
import com.pay.gateway.model.GeneralData;
import com.pay.gateway.model.Handler;
import com.pay.gateway.model.Key;
import com.pay.gateway.model.PayGate;
import com.pay.gateway.model.ValidateObj;
import com.pay.gateway.service.BuilderPayGateService;
import com.pay.gateway.validate.rule.newrule.InterfaceRule;
import com.pay.gateway.validate.rule.newrule.RuleHandler;
import com.pay.util.MapUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/20.
 */
public class BuilderPayGateServiceImpl implements BuilderPayGateService{
    private PayGate payGate;
    private String xmlLocation;
    private RuleHandler handlerHolder;

    public RuleHandler getHandlerHolder() {
        return handlerHolder;
    }

    public void setHandlerHolder(RuleHandler handlerHolder) {
        this.handlerHolder = handlerHolder;
    }

    public PayGate getPayGate() {
        return payGate;
    }

    public void setPayGate(PayGate payGate) {
        this.payGate = payGate;
    }

    public String getXmlLocation() {

        return xmlLocation;
    }

    public void setXmlLocation(String xmlLocation) {
        try{
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            Document document = reader.read(BuilderPayGateServiceImpl.class.getClassLoader().getResourceAsStream(xmlLocation));
            //获取根节点元素对象
            Element root = document.getRootElement();
            payGate = buildPayGate(root);
        }catch(Exception e){
            e.printStackTrace();
        }

        this.xmlLocation = xmlLocation;
    }

    private static PayGate buildPayGate(Element root) {
        PayGate payGate =  new  PayGate();
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            if(e.getName().equals("generaldata")){
                payGate.setGeneralData(buildGeneralData(e));
            }else{
                if(null == payGate.getConditions())
                    payGate.setConditions(new ArrayList<Condition>());
                payGate.getConditions().add(buildCondition(e));
            }
        }
        return payGate;
    }

    private static Condition buildCondition(Element e) {
        Condition condition = new Condition();
        condition = builderAttribute(e,condition);
        Iterator<Element> iterator = e.elementIterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if(element.getName().equals("key")){
                if(null == condition.getValidationColumns())
                    condition.setValidationColumns(new ArrayList<Key>());
                Key key = buildValidateObj(element, new Key());
                key.setMapProperties(condition.getValidateMapColumn());
                condition.getValidationColumns().add(key);
            }
        }
        return condition;
    }

    private static <T extends ValidateObj> T buildValidateObj(Element element, T t) {
        ValidateObj obj = (ValidateObj) t;
        t = builderAttribute(element, t);
        Iterator<Element> iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element ele = iterator.next();
            if("handlers".equals(ele.getName())){
                List<Handler> handlers = new ArrayList<Handler>();
                buildHandler(element,handlers);
                t.setHandlers(handlers);
            }
        }
        return t;
    }



    private static GeneralData buildGeneralData(Element e) {
        GeneralData generalData = new GeneralData();
        Iterator<Element> iterator = e.elementIterator();
        while (iterator.hasNext()) {
            Element element = iterator.next();
            if(element.getName().equals("column")){
                if(null == generalData.getColumns())
                    generalData.setColumns(new ArrayList<Column>());
                generalData.getColumns().add(buildValidateObj(element, new Column()));
            }
        }
        return generalData;
    }


    private static void buildHandler(Element element, List<Handler> handlers) {
        Iterator<Element> iterator = element.elementIterator();
        while (iterator.hasNext()) {
            Element ele = iterator.next();
            if("handler".equals(ele.getName())){
                Handler handler = new Handler();
                handler = builderAttribute(ele, handler);
                handlers.add(handler);
            }
        }
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

    @Override
    public ValidateResponse validation(Object obj) {
        List<ValidateObj>  validateObjs = getValidateObj(obj);
        ValidateResponse validateResponse = executeValidate(obj,validateObjs);
        return validateResponse;
    }

    private ValidateResponse executeValidate(Object obj, List<ValidateObj> validateObjs) {
        ValidateResponse response = new ValidateResponse();
        for (ValidateObj validateObj:validateObjs
             ) {
            List<InterfaceRule> rules =  new ArrayList<InterfaceRule>();
            if(null != validateObj.getDateType()){
                rules.add(handlerHolder.getHandlers().get("dateRule"));
            }
            if(null != validateObj.getNotnull()){
                rules.add(handlerHolder.getHandlers().get("notNullRule"));
            }
            List<Handler> lls = validateObj.getHandlers();
            if(lls != null)
                for(Handler ll:lls){
                    rules.add(handlerHolder.getHandlers().get(ll.getName()));
                }
            for (InterfaceRule rule:rules
                 ) {
                response  = rule.validate(obj,validateObj);
                if(!response.isPass()){
                    return response;
                }
            }
        }
        return response;
    }



    private List<ValidateObj> getValidateObj(Object obj) {
        List<ValidateObj> validateObjs =  new ArrayList<ValidateObj>();
        if(payGate != null && payGate.getGeneralData()!= null){
            validateObjs.addAll(payGate.getGeneralData().getColumns());
        }

        if(payGate != null && payGate.getConditions() != null){
            BeanWrapper bean = new BeanWrapperImpl(obj);
            for(int i = 0; i < payGate.getConditions().size(); i++){
                Condition condiction = payGate.getConditions().get(i);
                if(condiction!= null &&
                        null != bean.getPropertyDescriptor(condiction.getValidateMapColumn())){
                    Map<String, Object> env = MapUtil.bean2map(obj);
                    Boolean needOrNot =(Boolean)AviatorEvaluator.execute(condiction.getExpression(),env);
                    if(needOrNot){
                        validateObjs.addAll(condiction.getValidationColumns());
                    }
                }
            }
        }
        return validateObjs;
    }
}
