//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.11.02 alle 05:53:04 PM CET 
//


package it.digitalgarage.marketplace.commons.email.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per Mail complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="Mail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="subject" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="content" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="language" use="required" type="{http://it/eng/rete2i/asset/commons/email/jaxb}language" />
 *       &lt;attribute name="defaultTemplate" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Mail", propOrder = {
    "subject",
    "content"
})
public class Mail {

    @XmlElement(required = true)
    protected String subject;
    @XmlElement(required = true)
    protected String content;
    @XmlAttribute(name = "language", required = true)
    protected Language language;
    @XmlAttribute(name = "defaultTemplate")
    protected Boolean defaultTemplate;
    @XmlAttribute(name = "defaultTemplate")
    protected Boolean html;
    /**
     * Recupera il valore della proprietà subject.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Imposta il valore della proprietà subject.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

    /**
     * Recupera il valore della proprietà content.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Imposta il valore della proprietà content.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Recupera il valore della proprietà language.
     * 
     * @return
     *     possible object is
     *     {@link Language }
     *     
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * Imposta il valore della proprietà language.
     * 
     * @param value
     *     allowed object is
     *     {@link Language }
     *     
     */
    public void setLanguage(Language value) {
        this.language = value;
    }

    /**
     * Recupera il valore della proprietà defaultTemplate.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDefaultTemplate() {
        if (defaultTemplate == null) {
            return false;
        } else {
            return defaultTemplate;
        }
    }
    
    public boolean isHtml() {
        if (html == null) {
            return true;
        } else {
            return html;
        }
    }

    /**
     * Imposta il valore della proprietà defaultTemplate.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDefaultTemplate(Boolean value) {
        this.defaultTemplate = value;
    }

    
    public void setHtml(Boolean html) {
		this.html = html;
	}
}
