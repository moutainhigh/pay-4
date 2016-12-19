
package com.pay.app.cidverify.nciic.cxf;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the https.api_nciic_org_cn.nciicservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: https.api_nciic_org_cn.nciicservices
     * 
     */
    public ObjectFactory() {
    }

   
    /**
     * Create an instance of {@link NciicGetCondition }
     * 
     */
    public NciicGetCondition createNciicGetCondition() {
        return new NciicGetCondition();
    }

    

    /**
     * Create an instance of {@link NciicGetConditionResponse }
     * 
     */
    public NciicGetConditionResponse createNciicGetConditionResponse() {
        return new NciicGetConditionResponse();
    }

    

    /**
     * Create an instance of {@link NciicCheck }
     * 
     */
    public NciicCheck createNciicCheck() {
        return new NciicCheck();
    }

    

    /**
     * Create an instance of {@link NciicCheckResponse }
     * 
     */
    public NciicCheckResponse createNciicCheckResponse() {
        return new NciicCheckResponse();
    }

}
