
package org.cas.iie.xp.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>getXacmlResult complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType name="getXacmlResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="arg0" type="{http://service.xp.iie.cas.org/}userInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getXacmlResult", propOrder = {
    "arg0"
})
public class GetXacmlResult {

    protected UserInfo arg0;

    /**
     * ��ȡarg0���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link UserInfo }
     *     
     */
    public UserInfo getArg0() {
        return arg0;
    }

    /**
     * ����arg0���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link UserInfo }
     *     
     */
    public void setArg0(UserInfo value) {
        this.arg0 = value;
    }

}
