/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.base.reflection;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataClassModifiers;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.DataFieldModifiers;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.api.reflection.DataReflectionService;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.access.DataFieldAccessor;
import net.sf.mmm.data.base.AbstractDataObject;
import net.sf.mmm.util.filter.api.Filter;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderImpl;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.AnnotationFilter;
import net.sf.mmm.util.resource.api.DataResource;
import net.sf.mmm.util.resource.base.ClasspathResource;
import net.sf.mmm.util.xml.api.StaxUtil;
import net.sf.mmm.util.xml.base.StaxUtilImpl;

/**
 * This is an extension of {@link DataClassLoaderStAX} that also allows to
 * load {@link DataClass}es from the native Java implementations of entities.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class DataClassLoaderNative extends AbstractDataClassLoader {

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
  private Class<? extends DataObject> rootEntity;

  /**
   * A filter that only accepts types that are annotated as
   * {@link DataClassAnnotation}.
   */
  private final Filter<Class<?>> entityFilter;

  /** The XML input factory used to create the parser. */
  private final XMLInputFactory factory;

  /** @see #setConfigurationResource(DataResource) */
  private DataResource configurationResource;

  /** @see #setClassResolver(ClassResolver) */
  private ClassResolver classResolver;

  private StaxUtil staxUtil;

  /**
   * The constructor.
   * 
   * @param contentModelService
   */
  public DataClassLoaderNative(AbstractMutableDataModelService contentModelService) {

    super(contentModelService);
    PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
    builder.initialize();
    this.methodDescriptorBuilder = builder;
    this.rootEntity = AbstractDataObject.class;
    this.entityFilter = new AnnotationFilter(DataClassAnnotation.class);
    this.factory = XMLInputFactory.newInstance();
    this.classResolver = ClassResolver.CLASS_FOR_NAME_RESOLVER;
    this.configurationResource = new ClasspathResource(DataReflectionService.XML_MODEL_LOCATION);
  }

  /**
   * @return the staxUtil
   */
  public StaxUtil getStaxUtil() {

    if (this.staxUtil == null) {
      this.staxUtil = StaxUtilImpl.getInstance();
    }
    return this.staxUtil;
  }

  /**
   * @param staxUtil the staxUtil to set
   */
  @Resource
  public void setStaxUtil(StaxUtil staxUtil) {

    this.staxUtil = staxUtil;
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
  public Class<? extends DataObject> getRootEntity() {

    return this.rootEntity;
  }

  /**
   * @param rootEntity the rootEntity to set
   */
  public void setRootEntity(Class<? extends DataObject> rootEntity) {

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
        assert (DataReflectionService.XML_TAG_ROOT.equals(xmlReader.getLocalName()));
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
      ReflectionUtil reflectionUtil = getReflectionUtil();
      String tagName = xmlReader.getLocalName();
      if (XML_TAG_ENTITY.equals(tagName)) {
        String className = xmlReader.getAttributeValue(null, XML_ATR_ENTITY_CLASS);
        Class<?> entityClass = this.classResolver.resolveClass(className);
        loadClassRecursive(entityClass, context);
      } else if (XML_TAG_ENTITIES.equals(tagName)) {
        String packageName = xmlReader.getAttributeValue(null, XML_ATR_ENTITIES_PACKAGE);
        Set<String> classNames = reflectionUtil.findClassNames(packageName, true);
        Set<Class<?>> entityClasses = reflectionUtil.loadClasses(classNames, this.entityFilter);
        for (Class<?> entityClass : entityClasses) {
          loadClassRecursive(entityClass, context);
        }
      }
      getStaxUtil().skipOpenElement(xmlReader);
    } catch (Exception e) {
      // TODO: exception handling!!!
      throw new IllegalArgumentException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractDataClass loadClasses() throws IOException, DataReflectionException {

    Context context = new Context();
    parseConfiguration(context);
    AbstractDataClass<? extends DataObject> rootClass = context
        .getDataClass(DataObject.CLASS_ID);
    if (rootClass == null) {
      // TODO: NLS + Details/Explain
      throw new IllegalArgumentException("Root-class NOT found!");
    }
    loadFieldsRecursive(rootClass);

    return rootClass;
  }

  /**
   * This method "loads" the {@link DataClass} of the entity represented by
   * the given <code>javaClass</code>. It will recursively create the
   * super-classes accordingly.
   * 
   * @param javaClass is the entity to load.
   * @param context is where the {@link DataClass}es are added that have
   *        already been created.
   * @return the {@link DataClass} for the given <code>javaClass</code>.
   */
  public AbstractDataClass loadClassRecursive(Class<?> javaClass, Context context) {

    DataClassAnnotation contentClassAnnotation = javaClass
        .getAnnotation(DataClassAnnotation.class);
    if (contentClassAnnotation == null) {
      // TODO: NLS
      throw new DataReflectionException("Illegal entity class: missing "
          + DataClassAnnotation.class.getName());
    }
    // id
    DataId classId = getContentModelService().getIdManager().getClassId(
        contentClassAnnotation.id());
    // name
    String name = contentClassAnnotation.title();
    if ((name == null) || (name.length() == 0)) {
      name = javaClass.getSimpleName();
    }
    boolean createSuperClass = true;
    AbstractDataClass contentClass = context.getDataClass(name);
    if (contentClass == null) {
      // the class has NOT yet been loaded - create it
      int modifiers = javaClass.getModifiers();
      boolean isSystem = false;
      boolean isFinal = Modifier.isFinal(modifiers);
      boolean isAbstract = Modifier.isAbstract(modifiers);
      boolean isExtendable;
      if (isSystem) {
        isExtendable = contentClassAnnotation.isExtendable();
      } else {
        isExtendable = !isFinal;
      }
      DataClassModifiers contentClassModifiers = DataClassModifiersBean.getInstance(isSystem,
          isFinal, isAbstract, isExtendable);
      boolean deleted = javaClass.isAnnotationPresent(Deprecated.class);
      Class<? extends DataObject> entityClass = (Class<? extends DataObject>) javaClass;
      contentClass = getContentReflectionFactory().createNewClass(classId, name, null,
          contentClassModifiers, entityClass, deleted);
      context.put(contentClass);
    } else {
      // the class has already been loaded: check!
      if (!contentClass.getContentId().equals(classId)) {
        throw new DuplicateClassException(classId);
      }
      Class<?> type = contentClass.getJavaClass();
      if ((type != null) && (javaClass != type)) {
        throw new DuplicateClassException(classId);
      }
      if (contentClass.getJavaClass() == null) {
        contentClass.setJavaClass(javaClass);
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
        while (!superClass.isAnnotationPresent(DataClassAnnotation.class)) {
          superClass = superClass.getSuperclass();
          if (superClass == null) {
            // error in class hierarchy
            throw new DataReflectionException("Entity \"{0}\" does NOT inherit from \"{1}\"!",
                javaClass.getName(), this.rootEntity.getName());
          }
        }
        AbstractDataClass superContentClass = loadClassRecursive(superClass, context);
        contentClass.setSuperClass(superContentClass);
        superContentClass.addSubClass(contentClass);
      }
    }
    return contentClass;
  }

  /**
   * This method "loads" the {@link DataField field}s of the given
   * <code>contentClass</code>. It acts recursive on all sub-classes and
   * therefore loads the fields for the complete class-tree starting from the
   * given <code>contentClass</code>.
   * 
   * @param contentClass is the class for which the fields should be loaded.
   */
  protected void loadFieldsRecursive(AbstractDataClass contentClass) {

    Class<? extends DataObject> type = contentClass.getJavaClass();
    AbstractDataClass superClass = contentClass.getSuperClass();
    PojoDescriptor<? extends DataObject> methodPojoDescriptor;
    if (type == null) {
      throw new IllegalStateException("TODO");
    }
    methodPojoDescriptor = this.methodDescriptorBuilder.getDescriptor(type);
    for (PojoPropertyDescriptor methodPropertyDescriptor : methodPojoDescriptor
        .getPropertyDescriptors()) {
      PojoPropertyAccessor accessor = methodPropertyDescriptor
          .getAccessor(PojoPropertyAccessorNonArgMode.GET);
      try {
        boolean declareField = true;
        DataFieldAnnotation contentFieldAnnotation = null;
        if (accessor == null) {
          // no getter: ignore
          declareField = false;
        } else {
          Method getter = (Method) accessor.getAccessibleObject();
          contentFieldAnnotation = getter.getAnnotation(DataFieldAnnotation.class);
          if (contentFieldAnnotation == null) {
            // getter NOT properly annotated: ignore
            declareField = false;
          } else if (superClass != null) {
            Class<?> declaringClass = accessor.getDeclaringClass();
            Class<? extends DataObject> superType = superClass.getJavaClass();
            if (!superType.isAssignableFrom(declaringClass) || (superType.equals(declaringClass))) {
              // getter inherited from super entity: ignore
              declareField = false;
            }
          }
        }
        if (declareField) {
          DataId fieldId = getContentModelService().getIdManager().getFieldId(
              contentFieldAnnotation.id());
          String name = contentFieldAnnotation.title();
          if ((name == null) || (name.length() == 0)) {
            name = accessor.getName();
          }
          int modifiers = accessor.getModifiers();
          boolean isSystem = contentClass.getModifiers().isSystem();
          boolean isFinal = Modifier.isFinal(modifiers);
          boolean isStatic = Modifier.isStatic(modifiers) || contentFieldAnnotation.isStatic();
          boolean isTransient = contentFieldAnnotation.isTransient();
          boolean isReadOnly = true;
          PojoPropertyAccessor writeAccessor = methodPropertyDescriptor
              .getAccessor(PojoPropertyAccessorOneArgMode.SET);
          if ((writeAccessor != null) && (Modifier.isPublic(writeAccessor.getModifiers()))) {
            isReadOnly = contentFieldAnnotation.isReadOnly();
          }
          DataFieldModifiers contentFieldModifiers = DataFieldModifiersBean.getInstance(
              isSystem, isFinal, isReadOnly, isStatic, isTransient);
          boolean isDeleted = accessor.getAccessibleObject().isAnnotationPresent(Deprecated.class);
          Type fieldType = accessor.getPropertyType().getType();
          Class<?> fieldClass = accessor.getPropertyClass();
          if (fieldClass.isPrimitive()) {
            fieldType = getReflectionUtil().getNonPrimitiveType(fieldClass);
          }
          AbstractDataField contentField = getContentReflectionFactory().createNewField(fieldId,
              name, contentClass, fieldType, contentFieldModifiers, isDeleted);
          contentField.setAccessor(getFieldAccessor(methodPropertyDescriptor));
          contentClass.addField(contentField);
        }
      } catch (Exception e) {
        throw new DataReflectionException(e, "Error loading field '" + accessor.getName()
            + "' of class " + contentClass);
      }
    }
    // recursive invocation
    for (AbstractDataClass subClass : contentClass.getSubClasses()) {
      loadFieldsRecursive(subClass);
    }
  }

  /**
   * This method gets (creates) the {@link DataField#getAccessor() accessor}
   * for the field given by <code>methodPropertyDescriptor</code>.
   * 
   * @param methodPropertyDescriptor is the descriptor holding the accessor
   *        methods of the field.
   * @return the field accessor.
   */
  protected DataFieldAccessor getFieldAccessor(PojoPropertyDescriptor methodPropertyDescriptor) {

    Method getter = (Method) methodPropertyDescriptor.getAccessor(
        PojoPropertyAccessorNonArgMode.GET).getAccessibleObject();
    Method setter = null;
    PojoPropertyAccessor writeAccessor = methodPropertyDescriptor
        .getAccessor(PojoPropertyAccessorOneArgMode.SET);
    if (writeAccessor != null) {
      setter = (Method) writeAccessor.getAccessibleObject();
    }
    return new DataFieldAccessorPojo(getter, setter);
  }

}
