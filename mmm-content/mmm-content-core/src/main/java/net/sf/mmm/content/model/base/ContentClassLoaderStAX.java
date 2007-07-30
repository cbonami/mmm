/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.io.IOException;
import java.lang.reflect.Type;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.model.api.Modifiers;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.content.value.base.SmartIdManager;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.resource.DataResource;
import net.sf.mmm.util.value.ValueUtil;
import net.sf.mmm.util.xml.StaxUtil;
import net.sf.mmm.util.xml.XIncludeStreamReader;
import net.sf.mmm.value.api.ValueException;

/**
 * This class allows to de-serialize the content-model from XML using StAX.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassLoaderStAX implements ContentClassLoader {

  /** @see #getContentModelService() */
  private AbstractContentModelServiceBase contentModelService;

  /**
   * The constructor.
   * 
   * @param contentModelService is the
   *        {@link #getContentModelService() content-model service}.
   */
  public ContentClassLoaderStAX(AbstractContentModelServiceBase contentModelService) {

    super();
    this.contentModelService = contentModelService;
  }

  /**
   * This method gets the content-model service.
   * 
   * @return the content-model service.
   */
  protected AbstractContentModelServiceBase getContentModelService() {

    return this.contentModelService;
  }

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code>
   * as given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute is NOT defined or its value can NOT
   *         be converted to <code>type</code>.
   */
  protected <V> V parseAttribute(XMLStreamReader xmlReader, String localAttributeName, Class<V> type)
      throws ValueException {

    String value = xmlReader.getAttributeValue(null, localAttributeName);
    return ValueUtil.convertValue(localAttributeName, value, type);
  }

  /**
   * This method parses the attribute with the given
   * <code>localAttributeName</code> from the given <code>xmlReader</code>
   * as given by <code>type</code>.
   * 
   * @param <V> is the generic for the <code>type</code>.
   * @param xmlReader is where to read the XML from.
   * @param localAttributeName is the local name of the requested attribute.
   * @param type is the type the requested attribute should be converted to.
   * @param defaultValue is the default value returned if the requested
   *        attribute is NOT defined. It may be <code>null</code>.
   * @return the requested attribute as the given <code>type</code>.
   * @throws ValueException if the attribute value can NOT be converted to
   *         <code>type</code>.
   */
  protected <V> V parseAttribute(XMLStreamReader xmlReader, String localAttributeName,
      Class<V> type, V defaultValue) throws ValueException {

    String value = xmlReader.getAttributeValue(null, localAttributeName);
    return ValueUtil.convertValue(localAttributeName, value, type, defaultValue);
  }

  /**
   * This method parses the type given by <code>typeString</code>.
   * 
   * @param typeString is the type given as string (e.g. <code>String</code>
   *        or <code>List&lt;ContentObject&gt;</code>).
   * @return the parsed type.
   */
  protected Type parseFieldType(String typeString) {

    try {
      return ReflectionUtil.toType(typeString, this.contentModelService.getClassResolver());
    } catch (Exception e) {
      throw new ContentModelException("Illegal Type '" + typeString + "'!", e);
    }
  }

  /**
   * This method loads the basic fields of a {@link ContentClass} or
   * {@link ContentField}.
   * 
   * @param xmlReader is where to read the XML from.
   * @param object is the class or field.
   * @throws ValueException if a value was illegal.
   * @throws XMLStreamException if the XML was illegal.
   */
  protected void loadBasicFields(XMLStreamReader xmlReader, AbstractContentReflectionObject object)
      throws ValueException, XMLStreamException {

    // parse ID
    int id = parseAttribute(xmlReader, ContentObject.FIELD_NAME_ID, Integer.class).intValue();
    SmartId uid;
    SmartIdManager idManager = this.contentModelService.getIdManager();
    if (object.isClass()) {
      uid = idManager.getClassId(id);
    } else {
      uid = idManager.getFieldId(id);
    }
    object.setId(uid);
    // parse name
    String name = parseAttribute(xmlReader, ContentObject.FIELD_NAME_NAME, String.class);
    object.setName(name);
    // parse deleted flag
    boolean deleted = parseAttribute(xmlReader, ContentObject.FIELD_NAME_DELETED, Boolean.class,
        Boolean.FALSE).booleanValue();
    object.setDeleted(deleted);
  }

  /**
   * This method reads a class-hierarchy from the given <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized classes and fields is NOT set by this method so it may be
   * <code>null</code> if NOT initialized.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public AbstractContentClass loadClass(XMLStreamReader xmlReader) throws ValueException,
      XMLStreamException {

    assert (xmlReader.isStartElement());
    if (!ContentClass.CLASS_NAME.equals(xmlReader.getLocalName())) {
      // TODO:
      throw new IllegalArgumentException("wrong tag for class: " + xmlReader.getLocalName());
    }
    AbstractContentClass contentClass = this.contentModelService.newClass();
    // contentClass.setSuperClass(superClass);
    // superClass.addSubClass(contentClass);
    loadBasicFields(xmlReader, contentClass);
    // parse modifier
    boolean isFinal = parseAttribute(xmlReader, Modifiers.XML_ATR_ROOT_FINAL, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isAbstract = parseAttribute(xmlReader, ClassModifiers.XML_ATR_ROOT_ABSTRACT,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isExtendable = parseAttribute(xmlReader, ClassModifiers.XML_ATR_ROOT_EXTENDABLE,
        Boolean.class, Boolean.valueOf(!isFinal)).booleanValue();
    boolean isSystem = parseAttribute(xmlReader, Modifiers.XML_ATR_ROOT_SYSTEM, Boolean.class,
        Boolean.FALSE).booleanValue();
    ClassModifiers modifiers = ClassModifiersImpl.getInstance(isSystem, isFinal, isAbstract,
        isExtendable);
    contentClass.setModifiers(modifiers);
    // parse XML-child elements
    int xmlEvent = xmlReader.nextTag();
    while (XMLStreamConstants.START_ELEMENT == xmlEvent) {
      String tagName = xmlReader.getLocalName();
      if (ContentClass.CLASS_NAME.equals(tagName)) {
        // class
        AbstractContentClass childClass = loadClass(xmlReader);
        childClass.setSuperClass(contentClass);
        contentClass.addSubClass(childClass);
      } else if (ContentField.CLASS_NAME.equals(tagName)) {
        // field
        AbstractContentField contentField = loadField(xmlReader, contentClass);
        contentClass.addField(contentField);
      }
      xmlEvent = xmlReader.nextTag();
    }
    return contentClass;
  }

  /**
   * This method reads a {@link ContentField} from the given
   * <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized field is NOT set by this method so it may be
   * <code>null</code> if NOT initialized.
   * 
   * @param xmlReader is where to read the XML from.
   * @param declaringClass is the class declaring this field.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public AbstractContentField loadField(XMLStreamReader xmlReader,
      AbstractContentClass declaringClass) throws ValueException, XMLStreamException {

    AbstractContentField contentField = this.contentModelService.newField();
    loadBasicFields(xmlReader, contentField);
    contentField.setDeclaringClass(declaringClass);
    // parse modifier
    boolean isFinal = parseAttribute(xmlReader, Modifiers.XML_ATR_ROOT_FINAL, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isReadOnly = parseAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_READ_ONLY,
        Boolean.class, Boolean.FALSE).booleanValue();
    boolean isStatic = parseAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_STATIC, Boolean.class,
        Boolean.FALSE).booleanValue();
    boolean isSystem = declaringClass.getModifiers().isSystem();
    boolean isTransient = parseAttribute(xmlReader, FieldModifiers.XML_ATR_ROOT_TRANSIENT,
        Boolean.class, Boolean.FALSE).booleanValue();
    FieldModifiers modifiers = FieldModifiersImpl.getInstance(isSystem, isFinal, isReadOnly,
        isStatic, isTransient);
    contentField.setModifiers(modifiers);
    // parse type
    String typeString = parseAttribute(xmlReader, ContentField.XML_ATR_ROOT_TYPE, String.class);
    Type type = parseFieldType(typeString);
    contentField.setFieldTypeAndClass(type);

    xmlReader.nextTag();
    if (xmlReader.isStartElement()) {
      // TODO: parse until end...
    }
    return contentField;
  }

  /**
   * This method reads a class-hierarchy from the given <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized classes and fields is NOT set by this method so it may be
   * <code>null</code> if NOT initialized.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public AbstractContentClass readModel(XMLStreamReader xmlReader) throws ValueException,
      XMLStreamException {

    assert (xmlReader.isStartElement());
    if (!ContentClass.XML_TAG_CONTENT_MODEL.equals(xmlReader.getLocalName())) {
      // TODO:
      throw new IllegalArgumentException("wrong tag for content-model: " + xmlReader.getLocalName());
    }
    AbstractContentClass rootClass = null;
    while (XMLStreamConstants.START_ELEMENT == xmlReader.nextTag()) {
      if (ContentClass.CLASS_NAME.equals(xmlReader.getLocalName())) {
        if (rootClass != null) {
          // TODO:
          throw new IllegalArgumentException("Multiple root-classes in contentmodel!");
        }
        rootClass = loadClass(xmlReader);
      } else {
        StaxUtil.skipOpenElement(xmlReader);
      }
    }
    if (rootClass == null) {
      // TODO:
      throw new IllegalArgumentException("Missing root-class!");
    }
    return rootClass;
  }

  /**
   * {@inheritDoc}
   */
  public void loadClasses(DataResource resource) throws IOException, ContentModelException {

    XMLInputFactory factory = XMLInputFactory.newInstance();
    XMLStreamReader xmlReader = null;
    try {
      xmlReader = new XIncludeStreamReader(factory, resource);
      readModel(xmlReader);
    } catch (XMLStreamException e) {
      throw new ContentModelException("Failed to parse content-model from " + resource.getPath(), e);
    } finally {
      try {
        if (xmlReader != null) {
          xmlReader.close();
        }
      } catch (XMLStreamException e) {
        throw new ContentModelException("Failed to parse content-model from " + resource.getPath(),
            e);
      }
    }
  }

}
