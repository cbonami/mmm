/* $Id: StaxXmlWriter.java 191 2006-07-24 21:00:49Z hohwille $ */
package net.sf.mmm.xml.impl;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.base.AbstractXmlWriter;

/**
 * This is an implementation of the {@link net.sf.mmm.xml.api.XmlWriterIF}
 * interface that delegates the write events to an
 * {@link javax.xml.stream.XMLStreamWriter} of the StAX API (JSR 173).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StaxXmlWriter extends AbstractXmlWriter {

    /** the StAX writer */
    private final XMLStreamWriter staxWriter;

    /**
     * The constructor.
     * 
     * @param streamWriter
     *        is the writer to adapt.
     */
    public StaxXmlWriter(XMLStreamWriter streamWriter) {

        super();
        this.staxWriter = streamWriter;
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeStartElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     * {@inheritDoc}
     */
    public void writeStartElement(String localName, String namespacePrefix, String namespaceUri)
            throws XmlException {

        try {
            if ((namespacePrefix == null) && (namespaceUri == null)) {
                this.staxWriter.writeStartElement(localName);
            } else {
                if (namespaceUri == null) {
                    namespaceUri = this.staxWriter.getNamespaceContext().getNamespaceURI(namespacePrefix);                    
                    if (namespaceUri == null) {
                        throw new XmlNamespacePrefixUndefinedException(namespacePrefix);
                    }
                } else {
                    if (namespacePrefix == null) {
                        namespacePrefix = this.staxWriter.getPrefix(namespaceUri);                        
                    }
                    if (namespacePrefix == null) {
                        // Exception or build own prefix?
                        throw new XmlException("No such Uri" + namespaceUri);                        
                    }
                }
                this.staxWriter.writeStartElement(namespacePrefix, localName, namespaceUri);                
            }
        } catch (XMLStreamException e) {
            // TODO
            throw new XmlException("", e);
        }        
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeAttribute(java.lang.String,
     *      java.lang.String, java.lang.String)
     * {@inheritDoc}
     */
    public void writeAttribute(String localName, String value, String namespacePrefix)
            throws XmlException {

        try {
            if (namespacePrefix == null) {
                this.staxWriter.writeAttribute(localName, value);
            } else {
                String namespaceUri = this.staxWriter.getNamespaceContext().getNamespaceURI(namespacePrefix);                    
                if (namespaceUri == null) {
                    throw new XmlNamespacePrefixUndefinedException(namespacePrefix);
                }                
                this.staxWriter.writeAttribute(namespaceUri, localName, value);            
            }
        } catch (XMLStreamException e) {
            // TODO
            throw new XmlException("", e);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeNamespaceDeclaration(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public void writeNamespaceDeclaration(String namespacePrefix, String namespaceUri)
            throws XmlException {

        try {
            this.staxWriter.writeNamespace(namespacePrefix, namespaceUri);
        } catch (XMLStreamException e) {
            // TODO
            throw new XmlException("", e);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeCharacters(java.lang.String)
     * {@inheritDoc}
     */
    public void writeCharacters(String text) throws XmlException {

        try {
            this.staxWriter.writeCharacters(text);
        } catch (XMLStreamException e) {
            throw new XmlIOException(e);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeCData(java.lang.String)
     * {@inheritDoc}
     */
    public void writeCData(String text) throws XmlException {

        try {
            this.staxWriter.writeCData(text);
        } catch (XMLStreamException e) {
            throw new XmlIOException(e);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeComment(java.lang.String)
     * {@inheritDoc}
     */
    public void writeComment(String comment) throws XmlException {

        try {
            this.staxWriter.writeComment(comment);
        } catch (XMLStreamException e) {
            throw new XmlIOException(e);
        }
    }

    /**
     * @see net.sf.mmm.xml.api.XmlWriterIF#writeEndElement(java.lang.String,
     *      java.lang.String)
     * {@inheritDoc}
     */
    public void writeEndElement(String localName, String namespacePrefix) throws XmlException {

        try {
            this.staxWriter.writeEndElement();
        } catch (XMLStreamException e) {
            // TODO
            throw new XmlException("", e);
        }
    }

}
