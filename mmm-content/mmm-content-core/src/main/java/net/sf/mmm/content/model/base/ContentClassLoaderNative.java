/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Set;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.base.AbstractContentObject;
import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.base.FieldAnnotation;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.api.ContentAccessor;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.api.ContentModelException;
import net.sf.mmm.content.model.api.ContentModelService;
import net.sf.mmm.content.model.api.FieldModifiers;
import net.sf.mmm.content.value.base.SmartId;
import net.sf.mmm.util.filter.Filter;
import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.impl.PublicMethodPojoDescriptorBuilder;
import net.sf.mmm.util.reflect.ClassResolver;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.filter.AnnotationFilter;
import net.sf.mmm.util.resource.ClasspathResource;
import net.sf.mmm.util.resource.DataResource;
import net.sf.mmm.util.xml.StaxUtil;

/**
 * This is an extension of {@link ContentClassLoaderStAX} that also allows to
 * load {@link ContentClass}es from the native Java implementations of
 * entities.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentClassLoaderNative extends AbstractContentClassLoader {

  /** The XML tag name defining an entity by classname. */
  public static final String XML_TAG_ENTITY = "entity";

  /**
   * The XML attribute name for the {@link Class#getName() classname}of the
   * entity.
   */
  public static final String XML_ATR_ENTITY_CLASS = "class";

  /** The XML tag name defining the entities by package. */
  public static final String XML_TAG_ENTITIES = "entities";

  /** The XML attribute name for the package containing the entities. */
  public static final String XML_ATR_ENTITIES_PACKAGE = "package";

  /**
   * @see #loadClassRecursive(Class, Context)
   */
  private PojoDescriptorBuilder methodDescriptorBuilder;

  /** @see #getRootEntity() */
  private Class<? extends ContentObject> rootEntity;

  /** A filter that only accepts types that are annotated as {@link Entity}. */
  private final Filter<Class> entityFilter;

  /** The XML input factory used to create the parser. */
  private final XMLInputFactory factory;

  /** @see #setConfigurationResource(DataResource) */
  private DataResource configurationResource;

  /** @see #setClassResolver(ClassResolver) */
  private ClassResolver classResolver;

  /**
   * The constructor.
   * 
   * @param contentModelService
   */
  public ContentClassLoaderNative(AbstractMutableContentModelService contentModelService) {

    super(contentModelService);
    this.methodDescriptorBuilder = new PublicMethodPojoDescriptorBuilder();
    this.rootEntity = AbstractContentObject.class;
    this.entityFilter = new AnnotationFilter(ClassAnnotation.class);
    this.factory = XMLInputFactory.newInstance();
    this.classResolver = ClassResolver.CLASS_FOR_NAME_RESOLVER;
    this.configurationResource = new ClasspathResource(ContentModelService.XML_MODEL_LOCATION);
  }

  /**
   * This method sets (overrides) the resource pointing to the configuration
   * data.
   * 
   * @param configurationResource the configurationResource to set
   */
  public void setConfigurationResource(DataResource configurationResource) {

    this.configurationResource = configurationResource;
  }

  /**
   * @param classResolver the classResolver to set
   */
  public void setClassResolver(ClassResolver classResolver) {

    this.classResolver = classResolver;
  }

  /**
   * @return the rootEntity
   */
  public Class<? extends ContentObject> getRootEntity() {

    return this.rootEntity;
  }

  /**
   * @param rootEntity the rootEntity to set
   */
  public void setRootEntity(Class<? extends ContentObject> rootEntity) {

    this.rootEntity = rootEntity;
  }

  /**
   * This method loads the content-model configuration and parses it.
   * 
   * @param context is where to collect the configured classes.
   */
  public void parseConfiguration(Context context) {

    try {
      InputStream in = this.configurationResource.openStream();
      try {
        XMLStreamReader xmlReader = this.factory.createXMLStreamReader(in);
        int eventType = xmlReader.nextTag();
        assert (eventType == XMLStreamConstants.START_ELEMENT);
        assert (ContentModelService.XML_TAG_ROOT.equals(xmlReader.getLocalName()));
        eventType = xmlReader.nextTag();
        while (eventType == XMLStreamConstants.START_ELEMENT) {
          parseConfiguration(xmlReader, context);
          eventType = xmlReader.nextTag();
        }
        xmlReader.close();
      } finally {
        in.close();
      }
    } catch (Exception e) {
      // TODO: exception handling!!!
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * This method parses a child-element of the root-element of the content-model
   * configuration.
   * 
   * @param xmlReader is the reading pointing to the start of the child-element.
   * @param context is where to add the configured classes to.
   * @throws XMLStreamException if an error occurred while parsing the XML.
   */
  protected void parseConfiguration(XMLStreamReader xmlReader, Context context)
      throws XMLStreamException {

    try {
      String tagName = xmlReader.getLocalName();
      if (XML_TAG_ENTITY.equals(tagName)) {
        String className = xmlReader.getAttributeValue(null, XML_ATR_ENTITY_CLASS);
        Class entityClass = this.classResolver.resolveClass(className);
        loadClassRecursive(entityClass, context);
      } else if (XML_TAG_ENTITIES.equals(tagName)) {
        String packageName = xmlReader.getAttributeValue(null, XML_ATR_ENTITIES_PACKAGE);
        Set<String> classNames = ReflectionUtil.findClassNames(packageName, true);
        Set<Class> entityClasses = ReflectionUtil.loadClasses(classNames, this.entityFilter);
        for (Class entityClass : entityClasses) {
          loadClassRecursive(entityClass, context);
        }
      }
      StaxUtil.skipOpenElement(xmlReader);
    } catch (Exception e) {
      // TODO: exception handling!!!
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass loadClasses() throws IOException, ContentModelException {

    Context context = new Context();
    parseConfiguration(context);
    SmartId rootClassId = getContentModelService().getIdManager().getRootClassId();
    AbstractContentClass rootClass = context.getContentClass(rootClassId);
    if (rootClass == null) {
      // TODO: NLS + Details/Explain
      throw new IllegalArgumentException("Root-class NOT found!");
    }

    loadFieldsRecursive(rootClass);

    return rootClass;
  }

  /**
   * This method "loads" the {@link ContentClass} of the entity represented by
   * the given <code>javaClass</code>. It will recursively create the
   * super-classes accordingly.
   * 
   * @param javaClass is the entity to load.
   * @param context is where the {@link ContentClass}es are added that have
   *        already been created.
   * @return the {@link ContentClass} for the given <code>javaClass</code>.
   */
  public AbstractContentClass loadClassRecursive(Class<?> javaClass, Context context) {

    ClassAnnotation classAnnotation = javaClass.getAnnotation(ClassAnnotation.class);
    if (classAnnotation == null) {
      // TODO: NLS
      throw new ContentModelException("Illegal entity class: missing "
          + ClassAnnotation.class.getName());
    }
    // id
    SmartId classId = getContentModelService().getIdManager().getClassId(classAnnotation.id());
    // name
    String name = classAnnotation.name();
    if ((name == null) || (name.length() == 0)) {
      name = javaClass.getSimpleName();
    }
    boolean createSuperClass = true;
    AbstractContentClass contentClass = context.getContentClass(name);
    if (contentClass == null) {
      // the class has NOT yet been loaded - create it
      int modifiers = javaClass.getModifiers();
      boolean isSystem = false;
      boolean isFinal = Modifier.isFinal(modifiers);
      boolean isAbstract = Modifier.isAbstract(modifiers);
      boolean isExtendable;
      if (isSystem) {
        isExtendable = classAnnotation.isExtendable();
      } else {
        isExtendable = !isFinal;
      }
      ClassModifiers classModifiers = ClassModifiersImpl.getInstance(isSystem, isFinal, isAbstract,
          isExtendable);
      boolean deleted = javaClass.isAnnotationPresent(Deprecated.class);
      contentClass = getContentReflectionFactory().createNewClass(classId);
      contentClass.setName(name);
      contentClass.setModifiers(classModifiers);
      contentClass.setDeletedFlag(deleted);
      contentClass.setJavaClass((Class<? extends ContentObject>) javaClass);
      context.put(contentClass);
    } else {
      // the class has already been loaded: check!
      if (!contentClass.getId().equals(classId)) {
        throw new DuplicateClassException(classId);
      }
      Class<?> type = contentClass.getJavaClass();
      if ((type != null) && (javaClass != type)) {
        throw new DuplicateClassException(classId);
      }
      if (contentClass.getJavaClass() == null) {
        contentClass.setJavaClass((Class<? extends ContentObject>) javaClass);
      }
      if (contentClass.getSuperClass() != null) {
        // this can only happen if the class was created outside (in a subclass)
        createSuperClass = false;
      }
    }

    if (createSuperClass) {
      // root-class ?
      if (!this.rootEntity.equals(javaClass)) {
        // find super-class that is entity
        Class<?> superClass = javaClass.getSuperclass();
        while (!superClass.isAnnotationPresent(ClassAnnotation.class)) {
          superClass = superClass.getSuperclass();
          if (superClass == null) {
            // error in class hierarchy
            throw new ContentModelException("Entity \"{0}\" does NOT inherit from \"{1}\"!",
                javaClass.getName(), this.rootEntity.getName());
          }
        }
        AbstractContentClass superContentClass = loadClassRecursive(superClass, context);
        contentClass.setSuperClass(superContentClass);
        superContentClass.addSubClass(contentClass);
      }
    }
    return contentClass;
  }

  protected void loadFieldsRecursive(AbstractContentClass contentClass) {

    Class<? extends ContentObject> type = contentClass.getJavaClass();
    AbstractContentClass superClass = contentClass.getSuperClass();
    PojoDescriptor<? extends ContentObject> methodPojoDescriptor;
    if (type == null) {
      throw new IllegalStateException("TODO");
    }
    methodPojoDescriptor = this.methodDescriptorBuilder.getDescriptor(type);
    for (PojoPropertyDescriptor methodPropertyDescriptor : methodPojoDescriptor
        .getPropertyDescriptors()) {
      PojoPropertyAccessor accessor = methodPropertyDescriptor
          .getAccessor(PojoPropertyAccessMode.READ);
      try {
        boolean declareField = true;
        FieldAnnotation fieldAnnotation = null;
        if (accessor == null) {
          // no getter: ignore
          declareField = false;
        } else {
          Method getter = (Method) accessor.getAccessibleObject();
          fieldAnnotation = getter.getAnnotation(FieldAnnotation.class);
          if (fieldAnnotation == null) {
            // getter NOT properly annotated: ignore
            declareField = false;
          } else if (superClass != null) {
            Class<?> declaringClass = accessor.getDeclaringClass();
            Class<? extends ContentObject> superType = superClass.getJavaClass();
            if (!superType.isAssignableFrom(declaringClass)) {
              // getter inherited from super entity: ignore
              declareField = false;
            }
          }
        }
        if (declareField) {
          SmartId fieldId = getContentModelService().getIdManager()
              .getFieldId(fieldAnnotation.id());
          String name = fieldAnnotation.name();
          if ((name == null) || (name.length() == 0)) {
            name = accessor.getName();
          }
          int modifiers = accessor.getModifiers();
          boolean isSystem = contentClass.getModifiers().isSystem();
          boolean isFinal = Modifier.isFinal(modifiers);
          boolean isStatic = Modifier.isStatic(modifiers) || fieldAnnotation.isStatic();
          boolean isTransient = fieldAnnotation.isTransient();
          boolean isReadOnly = true;
          PojoPropertyAccessor writeAccessor = methodPropertyDescriptor
              .getAccessor(PojoPropertyAccessMode.WRITE);
          if ((writeAccessor != null) && (Modifier.isPublic(writeAccessor.getModifiers()))) {
            isReadOnly = fieldAnnotation.isReadOnly();
          }
          FieldModifiers fieldModifiers = FieldModifiersImpl.getInstance(isSystem, isFinal,
              isReadOnly, isStatic, isTransient);
          boolean isDeleted = accessor.getAccessibleObject().isAnnotationPresent(Deprecated.class);
          Type fieldType = accessor.getPropertyType();
          Class fieldClass = accessor.getPropertyClass();
          if (fieldClass.isPrimitive()) {
            fieldType = ReflectionUtil.getNonPrimitiveType(fieldClass);
          }
          AbstractContentField contentField = getContentReflectionFactory().createNewField(fieldId);
          contentField.setName(name);
          contentField.setDeclaringClass(contentClass);
          contentField.setDeletedFlag(isDeleted);
          contentField.setFieldTypeAndClass(fieldType);
          contentField.setModifiers(fieldModifiers);
          contentField.setAccessor(getFieldAccessor(methodPropertyDescriptor));
          contentClass.addField(contentField);
        }
      } catch (Exception e) {
        throw new ContentModelException(e, "Error loading field '" + accessor.getName()
            + "' of class " + contentClass);
      }
    }
  }

  /**
   * This method gets (creates) the {@link ContentField#getAccessor() accessor}
   * for the field given by <code>methodPropertyDescriptor</code>.
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the field accessor.
   */
  protected ContentAccessor getFieldAccessor(PojoPropertyDescriptor methodPropertyDescriptor) {

    Method getter = (Method) methodPropertyDescriptor.getAccessor(PojoPropertyAccessMode.READ)
        .getAccessibleObject();
    Method setter = null;
    PojoPropertyAccessor writeAccessor = methodPropertyDescriptor
        .getAccessor(PojoPropertyAccessMode.WRITE);
    if (writeAccessor != null) {
      setter = (Method) writeAccessor.getAccessibleObject();
    }
    return new MethodContentAccessor(getter, setter);
  }

}
