//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2017.11.02 alle 05:53:04 PM CET 
//


package it.digitalgarage.marketplace.commons.email.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.rete2i.asset.commons.email.jaxb package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.rete2i.asset.commons.email.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Mails }
     * 
     */
    public Mails createMails() {
        return new Mails();
    }

    /**
     * Create an instance of {@link Mail }
     * 
     */
    public Mail createMail() {
        return new Mail();
    }

}
