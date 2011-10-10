/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import java.lang.reflect.Type;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.api.ContentIdManager;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentClassLoader;
import net.sf.mmm.content.reflection.api.ContentClassModifiers;
import net.sf.mmm.content.reflection.api.ContentField;
import net.sf.mmm.content.reflection.api.ContentFieldModifiers;
import net.sf.mmm.content.reflection.api.ContentReflectionException;
import net.sf.mmm.content.reflection.api.ContentReflectionService;
import net.sf.mmm.content.reflection.api.ContentModifiers;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.value.api.ValueException;

/**
 * This is an implementation of the {@link ContentClassLoader} interface that
 * allows to load (de-serialize) {@link ContentClass}es from XML using StAX.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContentClassLoaderStAX extends ContentClassLoaderNative {

  /**
   * The constructor.
   * 
   * @param contentModelService is the {@link #getContentModelService()
   *        content-model service}.
   */
  public ContentClassLoaderStAX(AbstractMutableContentModelService contentModelService) {

    super(contentModelService);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void parseConfiguration(XMLStreamReader xmlReader, Context context)
      throws XMLStreamException {

    String tagName = xmlReader.getLocalName();
    if (ContentReflectionService.XML_TAG_CLASS.equals(tagName)) {
      loadClassRecursive(xmlReader, context);
    }
    super.parseConfiguration(xmlReader, context);
  }

  protected Class getContentJavaClass(String name) {

    return getContentModelService().getName2JavaClassMap().get(name);
  }

  /**
   * This method parses the type given by <code>typeString</code>.
   * 
   * @param typeSpecification is the type given as string (e.g.
   *        <code>String</code> or <code>List&lt;ContentObject&gt;</code>).
   * @param classResolver is the resolver used to resolve the classes.
   * @return the parsed type.
   */
  protected Type parseFieldType(String typeSpecification, ClassResolver classResolver) {

    try {
      return getReflectionUtil().toType(typeSpecification, classResolver);
    } catch (Exception e) {
      // TODO: NLS
      throw new ContentReflectionException(e, "Illegal Type '" + typeSpecification + "'!", e);
    }
  }

  /**
   * This method reads the ID from the given <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the ID of the class or field.
   */
  protected ContentId parseId(XMLStreamReader xmlReader) {

    boolean isClass = ContentClass.XML_TAG_CLASS.equals(xmlReader.getLocalName());
    // parse ID
    int id = getStaxUtil().parseAttribute(xmlReader, null, ContentObject.FIELD_NAME_ID,
        Integer.class).intValue();
    ContentId uid;
    ContentIdManager idManager = getContentModelService().getIdManager();
    if (isClass) {
      uid = idManager.getClassId(id);
    } else {
      uid = idManager.getFieldId(id);
    }
    return uid;
  }

  /**
   * This method reads the {@link ContentObject#getTitle() name} from the given
   * <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the name of the class or field.
   */
  protected String parseName(XMLStreamReader xmlReader) {

    return getStaxUtil().parseAttribute(xmlReader, null, ContentObject.FIELD_NAME_TITLE,
        String.class);
  }

  /**
   * This method reads the {@link ContentObject#isDeleted() deleted flag} from
   * the given <code>xmlReader</code>.
   * 
   * @param xmlReader is where to read the XML from.
   * @return the deleted-flag of the class or field.
   */
  protected boolean parseDeletedFlag(XMLStreamReader xmlReader) {

    return getStaxUtil().parseAttribute(xmlReader, null, ContentObject.FIELD_NAME_DELETED,
        Boolean.class, Boolean.FALSE).booleanValue();
  }

  /**
   * This method reads a class-hierarchy from the given <code>xmlReader</code>.<br>
   * <b>ATTENTION:</b><br>
   * <ul>
   * <li>The order of the elements in the XML is important to this method. Field
   * elements have to occur after Class elements on the same level in the XML
   * tree.</li>
   * <li>The {@link ContentObject#getContentClass() content-class} of the
   * de-serialized classes and fields is NOT set by this method so it may be
   * <code>null</code> if NOT initialized via the
   * {@link net.sf.mmm.content.reflection.api.ContentReflectionService model-service}.</li>
   * </ul>
   * 
   * @param xmlReader is where to read the XML from.
   * @param context is where to add the configured classes to.
   * @return the class-hierarchy de-serialized via the given
   *         <code>xmlReader</code>.
   * @throws ValueException if a value is missing or invalid.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  public AbstractContentClass loadClassRecursive(XMLStreamReader xmlReader, Context context)
      throws ValueException, XMLStreamException {

    assert (xmlReader.isStartElement());
    assert (ContentClass.XML_TAG_CLASS.equals(xmlReader.getLocalName()));
    // parse class attributes...
    ContentId id = parseId(xmlReader);
    String name = parseName(xmlReader);
    boolean deleted = parseDeletedFlag(xmlReader);
    // parse modifier
    boolean isSystem = getStaxUtil().parseAttribute(xmlReader, null,
        ContentModifiers.XML_ATR_MODIFIERS_SYSTEM, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isFinal = getStaxUtil().parseAttribute(xmlReader, null,
        ContentModifiers.XML_ATR_MODIFIERS_FINAL, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isAbstract = getStaxUtil().parseAttribute(xmlReader, null,
        ContentClassModifiers.XML_ATR_MODIFIERS_ABSTRACT, Boolean.class, Boolean.FALSE)
        .booleanValue();
    // default value for extendable is...
    boolean isExtendable = !(isFinal || (isSystem && isAbstract));
    // configured value is therefore...
    isExtendable = getStaxUtil().parseAttribute(xmlReader, null,
        ContentClassModifiers.XML_ATR_MODIFIERS_EXTENDABLE, Boolean.class,
        Boolean.valueOf(isExtendable)).booleanValue();
    ContentClassModifiers modifiers = ContentClassModifiersBean.getInstance(isSystem, isFinal,
        isAbstract, isExtendable);

    AbstractContentClass contentClass = context.getContentClass(id);
    if (contentClass == null) {
      contentClass = getContentReflectionFactory().createNewClass(id, name, null, modifiers, null,
          deleted);
    } else {
      if (!name.equals(contentClass.getTitle())) {
        // TODO:
        throw new DuplicateClassException(id);
      }
      // TODO ...
    }

    String javaClassName = xmlReader.getAttributeValue(null, "javaClass");
    if (javaClassName != null) {
      try {
        Class javaClass = getClassResolver().resolveClass(javaClassName);
        Class oldClass = contentClass.getJavaClass();
        if ((oldClass != null) && (!oldClass.equals(javaClass))) {
          // TODO:
          throw new ContentReflectionException("Java-class mismatch for content-class " + name);
        }
        contentClass.setJavaClass(javaClass);
      } catch (ClassNotFoundException e) {
        // TODO: NLS
        throw new ContentReflectionException(e, "Java class NOT found for content-class " + name);
      }
    }

    // parse XML-child elements
    int xmlEvent = xmlReader.nextTag();
    while (XMLStreamConstants.START_ELEMENT == xmlEvent) {
      String tagName = xmlReader.getLocalName();
      if (ContentClass.XML_TAG_CLASS.equals(tagName)) {
        AbstractContentClass subClass = loadClassRecursive(xmlReader, context);
        subClass.setSuperClass(contentClass);
        contentClass.addSubClass(subClass);
      } else if (ContentField.XML_TAG_FIELD.equals(tagName)) {
        // field
        AbstractContentField contentField = loadField(xmlReader, contentClass);
        contentClass.addField(contentField);
      } else {
        parseClassChildElement(xmlReader, contentClass);
      }
      xmlEvent = xmlReader.nextTag();
    }
    return contentClass;
  }

  /**
   * This method parses the XML at the point where an unknown child-element of a
   * Class element was hit. The method has to consume this element including all
   * its children and point to the end of the unknown element.
   * 
   * @param xmlReader is where to read the XML from.
   * @param superClass is the class that owns the unknown child-element.
   * @throws XMLStreamException if the <code>xmlReader</code> caused an error.
   */
  protected void parseClassChildElement(XMLStreamReader xmlReader, AbstractContentClass superClass)
      throws XMLStreamException {

    getStaxUtil().skipOpenElement(xmlReader);
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

    assert (xmlReader.isStartElement());
    assert (ContentField.XML_TAG_FIELD.equals(xmlReader.getLocalName()));
    ContentId id = parseId(xmlReader);
    String name = parseName(xmlReader);
    boolean deleted = parseDeletedFlag(xmlReader);
    // parse modifier
    boolean isFinal = getStaxUtil().parseAttribute(xmlReader, null,
        ContentModifiers.XML_ATR_MODIFIERS_FINAL, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isReadOnly = getStaxUtil().parseAttribute(xmlReader, null,
        ContentFieldModifiers.XML_ATR_ROOT_READ_ONLY, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isStatic = getStaxUtil().parseAttribute(xmlReader, null,
        ContentFieldModifiers.XML_ATR_ROOT_STATIC, Boolean.class, Boolean.FALSE).booleanValue();
    boolean isSystem = declaringClass.getContentModifiers().isSystem();
    boolean isTransient = getStaxUtil().parseAttribute(xmlReader, null,
        ContentFieldModifiers.XML_ATR_ROOT_TRANSIENT, Boolean.class, Boolean.FALSE).booleanValue();
    ContentFieldModifiers modifiers = ContentFieldModifiersBean.getInstance(isSystem, isFinal,
        isReadOnly, isStatic, isTransient);
    // parse type
    String typeSpecification = getStaxUtil().parseAttribute(xmlReader, null,
        ContentField.XML_ATR_FIELD_TYPE, String.class);
    Type fieldType = null;
    // fieldType = parseFieldType(typeSpecification, getClassResolver());
    AbstractContentField contentField = getContentReflectionFactory().createNewField(id, name,
        declaringClass, fieldType, modifiers, deleted);
    contentField.setFieldTypeSpecification(typeSpecification);
    // AbstractContentField contentField =
    // getContentModelService().createOrUpdateField(id, name,
    // declaringClass, modifiers, fieldType, typeSpecification, deleted);
    xmlReader.nextTag();
    if (xmlReader.isStartElement()) {
      getStaxUtil().skipOpenElement(xmlReader);
    }
    return contentField;
  }

  /**
   * 
   * TODO: javadoc
   * 
   * @param contentClass
   */
  protected void initializeModel(AbstractContentClass contentClass, ClassResolver resolver) {

    for (AbstractContentField contentField : contentClass.getDeclaredFields()) {
      String typeSpecification = contentField.getFieldTypeSpecification();
      Type type = parseFieldType(typeSpecification, resolver);
      contentField.setFieldTypeAndClass(type);
    }
    for (AbstractContentClass subClass : contentClass.getSubClasses()) {
      initializeModel(subClass, resolver);
    }
  }

}
