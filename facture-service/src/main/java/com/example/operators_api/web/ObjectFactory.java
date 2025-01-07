
package com.example.operators_api.web;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.operators_api.web package. 
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

    private final static QName _Recharge_QNAME = new QName("http://web.operators_api.example.com/", "recharge");
    private final static QName _RechargeResponse_QNAME = new QName("http://web.operators_api.example.com/", "rechargeResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.example.operators_api.web
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Recharge }
     * 
     */
    public Recharge createRecharge() {
        return new Recharge();
    }

    /**
     * Create an instance of {@link RechargeResponse }
     * 
     */
    public RechargeResponse createRechargeResponse() {
        return new RechargeResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Recharge }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Recharge }{@code >}
     */
    @XmlElementDecl(namespace = "http://web.operators_api.example.com/", name = "recharge")
    public JAXBElement<Recharge> createRecharge(Recharge value) {
        return new JAXBElement<Recharge>(_Recharge_QNAME, Recharge.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RechargeResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RechargeResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://web.operators_api.example.com/", name = "rechargeResponse")
    public JAXBElement<RechargeResponse> createRechargeResponse(RechargeResponse value) {
        return new JAXBElement<RechargeResponse>(_RechargeResponse_QNAME, RechargeResponse.class, null, value);
    }

}
