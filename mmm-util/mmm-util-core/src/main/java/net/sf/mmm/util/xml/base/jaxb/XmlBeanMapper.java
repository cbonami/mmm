/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base.jaxb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.MarshalException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.api.DataResourceFactory;
import net.sf.mmm.util.resource.api.ResourceNotAvailableException;
import net.sf.mmm.util.resource.impl.BrowsableResourceFactoryImpl;
import net.sf.mmm.util.xml.base.XmlInvalidException;

/**
 * This class is a little helper for the simple but common use of JAXB where you
 * simply want to {@link #loadXml(InputStream, Object) read} or
 * {@link #saveXml(Object, OutputStream) write} the XML for a single JAXB
 * annotated java bean.
 * 
 * @param <T> is the generic type of the JAXB bean.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class XmlBeanMapper<T> extends AbstractLoggable {

  /** @see #getJaxbContext() */
  private final JAXBContext jaxbContext;

  /** @see #loadXml(InputStream, Object) */
  private final Class<T> xmlBeanClass;

  /** @see #getDataResourceFactory() */
  private DataResourceFactory dataResourceFactory;

  /**
   * The constructor.
   * 
   * @param xmlBeanClass is the JAXB-annotated-{@link Class} that should be
   *        mapped.
   */
  public XmlBeanMapper(Class<T> xmlBeanClass) {

    super();
    this.xmlBeanClass = xmlBeanClass;
    try {
      this.jaxbContext = JAXBContext.newInstance(xmlBeanClass);
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * @return the dataResourceFactory
   */
  protected DataResourceFactory getDataResourceFactory() {

    return this.dataResourceFactory;
  }

  /**
   * @param dataResourceFactory is the dataResourceFactory to set
   */
  @Inject
  public void setDataResourceFactory(DataResourceFactory dataResourceFactory) {

    getInitializationState().requireNotInitilized();
    this.dataResourceFactory = dataResourceFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.dataResourceFactory == null) {
      BrowsableResourceFactoryImpl resourceFactoryImpl = new BrowsableResourceFactoryImpl();
      resourceFactoryImpl.initialize();
      this.dataResourceFactory = resourceFactoryImpl;
    }
  }

  /**
   * This method gets a {@link Marshaller} instance. This method potentially
   * allows reusing the {@link Marshaller} (if it is thread-safe).
   * 
   * @return the {@link Marshaller}.
   */
  protected Marshaller getOrCreateMarshaller() {

    try {
      return this.jaxbContext.createMarshaller();
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * This method gets a {@link Marshaller} instance. This method potentially
   * allows reusing the {@link Marshaller} (if it is thread-safe).
   * 
   * @return the {@link Unmarshaller}.
   */
  protected Unmarshaller getOrCreateUnmarshaller() {

    try {
      return this.jaxbContext.createUnmarshaller();
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

  /**
   * @return the jaxbContext
   */
  protected JAXBContext getJaxbContext() {

    return this.jaxbContext;
  }

  /**
   * This method loads the JAXB-bean as XML from the given
   * <code>inputStream</code>.
   * 
   * @param inputStream is the {@link InputStream} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   * @param source describes the source of the invalid XML. Typically this will
   *        be the filename where the XML was read from. It is used in in the
   *        exception message. This will help to find the problem easier.
   */
  public T loadXml(InputStream inputStream, Object source) {

    try {
      Object unmarshalledObject = getOrCreateUnmarshaller().unmarshal(inputStream);
      return this.xmlBeanClass.cast(unmarshalledObject);
    } catch (JAXBException e) {
      throw new XmlInvalidException(e, source);
    }
  }

  /**
   * This method loads the JAXB-bean as XML from the given <code>location</code>
   * .
   * 
   * @param locationUrl is the location URL for the {@link DataResource
   *        resource} pointing to the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(String locationUrl) {

    DataResource resource = this.dataResourceFactory.createDataResource(locationUrl);
    return loadXml(resource);
  }

  /**
   * This method loads the JAXB-bean as XML from the given
   * <code>inputStream</code>.
   * 
   * @param file is the {@link File} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(File file) {

    try {
      FileInputStream inputStream = new FileInputStream(file);
      try {
        return loadXml(inputStream, file);
      } finally {
        try {
          inputStream.close();
        } catch (Exception e) {
          // ignore...
        }
      }
    } catch (FileNotFoundException e) {
      throw new ResourceNotAvailableException(e, file.getPath());
    }
  }

  /**
   * This method loads the JAXB-bean as XML from the given <code>resource</code>
   * .
   * 
   * @param resource is the {@link DataResource} with the XML to parse.
   * @return the parsed XML converted to the according JAXB-bean.
   */
  public T loadXml(DataResource resource) {

    InputStream inputStream = resource.openStream();
    try {
      return loadXml(inputStream, resource);
    } finally {
      try {
        inputStream.close();
      } catch (Exception e) {
        // ignore...
      }
    }
  }

  /**
   * This method saves the given <code>jaxbBean</code> as XML to the given
   * <code>outputStream</code>.
   * 
   * @param jaxbBean is the JAXB-bean to save as XML.
   * @param outputStream is the {@link OutputStream} where to write the XML to.
   */
  public void saveXml(T jaxbBean, OutputStream outputStream) {

    try {
      getOrCreateMarshaller().marshal(jaxbBean, outputStream);
    } catch (MarshalException e) {
      throw new XmlInvalidException(e, jaxbBean);
    } catch (JAXBException e) {
      throw new NlsIllegalStateException(e);
    }
  }

}
