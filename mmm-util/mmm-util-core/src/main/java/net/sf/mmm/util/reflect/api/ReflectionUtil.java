/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.api;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import net.sf.mmm.util.filter.api.Filter;

/**
 * This is the interface for a collection of utility functions to deal with
 * {@link java.lang.reflect reflection}.
 * 
 * @see net.sf.mmm.util.reflect.base.ReflectionUtilImpl
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ReflectionUtil {

  /** an empty class array */
  Class<?>[] NO_PARAMETERS = new Class[0];

  /** an empty {@link Object}-array */
  Object[] NO_ARGUMENTS = new Object[0];

  /** an empty {@link Type}-array */
  Type[] NO_TYPES = new Type[0];

  /**
   * This method gets the {@link Object#getClass() classes} of the given
   * objects.
   * 
   * @param objects is an array containing the objects for that the classes are
   *        requested.
   * @return an array of the same length as the given array. At each position
   *         the returned array contains the {@link Object#getClass() class} of
   *         the object from the given array at the same position or
   *         <code>null</code>, if that object is <code>null</code>.
   */
  Class<?>[] getClasses(Object[] objects);

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code>.<br>
   * The {@link GenericType#getType() type}, {@link GenericType#getLowerBound()
   * lower bound} and {@link GenericType#getUpperBound() upper bound} of the
   * returned {@link GenericType} will all be identical to the given
   * <code>type</code>.<br>
   * <b>ATTENTION:</b><br>
   * If you know the {@link Type} where the given <code>type</code> was
   * {@link net.sf.mmm.util.reflect.base.AbstractGenericType#getDefiningType()
   * defined} you should use {@link #createGenericType(Type, GenericType)}
   * instead to get a more precise result. <br>
   * 
   * @param <T> is the templated type of the {@link Class} to convert.
   * 
   * @param type is the {@link Type} to represent.
   * @return the according {@link GenericType}.
   */
  <T> GenericType<T> createGenericType(Class<T> type);

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code>.<br>
   * If the given <code>type</code> is a {@link Class}, the methods behaves like
   * {@link #createGenericType(Class)}.<br>
   * <b>ATTENTION:</b><br>
   * If you know the {@link Type} where the given <code>type</code> was defined
   * (e.g. the {@link Class} where you retrieved the given <code>type</code>
   * from as parameter, return-type or field-type) you should use
   * {@link #createGenericType(Type, GenericType)} instead to get a more precise
   * result.
   * 
   * @param type is the {@link Type} to represent.
   * @return the according {@link GenericType}.
   */
  GenericType<?> createGenericType(Type type);

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code> in the context of the given <code>definingType</code>.<br>
   * Here is some typical example of how to use this:
   * 
   * <pre>
   * {@link ReflectionUtil} util = {@link net.sf.mmm.util.reflect.base.ReflectionUtilImpl#getInstance()};
   * Class&lt;?&gt; myClass = getSomeClass();
   * GenericType definingType = util.{@link #createGenericType(Type) createGenericType}(myClass);
   * {@link Method} myMethod = findSomeMethod(myClass);
   * Type returnType = myMethod.{@link Method#getGenericReturnType() getGenericReturnType()};
   * GenericType type = util.{@link #createGenericType(Type, GenericType) createGenericType}(returnType, definingType);
   * Class&lt;?&gt; returnClass = type.{@link GenericType#getUpperBound()};
   * </pre>
   * 
   * Now if you ask your self my all this instead of just using
   * <code>myMethod.{@link Method#getReturnType() getReturnType()}</code>? Read
   * the javadoc of {@link GenericType} to get the answer.<br>
   * <b>NOTE:</b><br>
   * Please look at <code>mmm-util-pojo</code> which allows to use this features
   * at a higher level and therefore much easier.
   * 
   * @see #createGenericType(Type, Class)
   * 
   * @param type is the {@link Type} to represent.
   * @param definingType is the {@link GenericType} where the given
   *        <code>type</code> is defined in. It is needed to resolve
   *        {@link java.lang.reflect.TypeVariable}s.
   * @return the according {@link GenericType}.
   */
  GenericType<?> createGenericType(Type type, GenericType<?> definingType);

  /**
   * This method creates the {@link GenericType} representing the given
   * <code>type</code> in the context of the given <code>definingType</code>.<br>
   * It is a convenience method for
   * <code>{@link #createGenericType(Type, GenericType) createGenericType}(type, 
   * {@link #createGenericType(Type) createGenericType}(definingType))</code>
   * 
   * @param type is the {@link Type} to represent.
   * @param definingType is the {@link Class} where the given <code>type</code>
   *        is defined in. It is needed to resolve
   *        {@link java.lang.reflect.TypeVariable}s.
   * @return the according {@link GenericType}.
   */
  GenericType<?> createGenericType(Type type, Class<?> definingType);

  /**
   * This method creates the {@link Class} reflecting an {@link Class#isArray()
   * array} of the given
   * <code>{@link Class#getComponentType() componentType}</code>.
   * 
   * @param componentType is the {@link Class#getComponentType() component type}
   *        .
   * @return the according {@link Class#isArray() array}-class.
   */
  Class<?> getArrayClass(Class<?> componentType);

  /**
   * This method is the analogy to {@link Class#forName(String)} for creating a
   * {@link Type} instance from {@link String}.
   * 
   * @see #toType(String, ClassResolver)
   * 
   * @param type is the string representation of the requested type.
   * @return the requested type.
   * @throws ClassNotFoundException if a class could NOT be found (e.g. in
   *         <code>java.util.Map&lt;java.long.String&gt;</code> - what should be
   *         <code>lang</code> instead of <code>long</code>).
   * @throws IllegalArgumentException if the given <code>type</code> could NOT
   *         be parsed (e.g. <code>java.util.Map&lt;&lt;String&gt;</code>).
   */
  Type toType(String type) throws ClassNotFoundException, IllegalArgumentException;

  /**
   * This method is the analogy to {@link Class#forName(String)} for creating a
   * {@link Type} instance from {@link String}.
   * 
   * @param type is the string representation of the requested type.
   * @param resolver is used to resolve classes.
   * @return the requested type.
   * @throws ClassNotFoundException if a class could NOT be found (e.g. in
   *         <code>java.util.Map&lt;java.long.String&gt;</code> - what should be
   *         <code>lang</code> instead of <code>long</code>).
   * @throws IllegalArgumentException if the given <code>type</code> could NOT
   *         be parsed (e.g. <code>java.util.Map&lt;&lt;String&gt;</code>).
   */
  Type toType(String type, ClassResolver resolver) throws ClassNotFoundException,
      IllegalArgumentException;

  /**
   * This method gets the string representation of a {@link Type}. Instead of
   * <code>{@link Type}.toString()</code> it returns {@link Class#getName()} if
   * the type is a {@link Class}.
   * 
   * @param type is the type to get as string.
   * @return the string representation of the given <code>type</code>.
   */
  String toString(Type type);

  /**
   * This method compares the given classes.
   * 
   * @param class1 is the first class.
   * @param class2 is the second class.
   * @return <ul>
   *         <li><code>0</code> if both classes are equal to each other.</li>
   *         <li><code>1</code> if <code>class1</code> inherits from
   *         <code>class2</code>.</li>
   *         <li><code>-1</code> if <code>class2</code> inherits from
   *         <code>class1</code>.</li>
   *         <li>{@link Integer#MIN_VALUE} otherwise.</li>
   *         </ul>
   */
  int compare(Class<?> class1, Class<?> class2);

  /**
   * This method gets the according non-{@link Class#isPrimitive() primitive}
   * type for the class given by <code>type</code>.<br>
   * E.g.
   * <code>{@link #getNonPrimitiveType(Class) getNonPrimitiveType}(int.class)</code>
   * will return <code>Integer.class</code>.
   * 
   * @see Class#isPrimitive()
   * 
   * @param type is the (potentially) {@link Class#isPrimitive() primitive}
   *        type.
   * @return the according object-type for the given <code>type</code>. This
   *         will be the given <code>type</code> itself if it is NOT
   *         {@link Class#isPrimitive() primitive}.
   */
  Class<?> getNonPrimitiveType(Class<?> type);

  /**
   * This method gets the {@link java.lang.reflect.Field#get(java.lang.Object)
   * value} of a {@link java.lang.reflect.Modifier#isStatic(int) static}
   * {@link java.lang.reflect.Field field} .
   * 
   * @param <T> the templated type the requested field is assigned to.
   * @param type is the class or interface containing the requested field.
   * @param fieldName is the {@link java.lang.reflect.Field#getName() name} of
   *        the requested field.
   * @param fieldType is the type the requested field is assigned to. Therefore
   *        the field declaration (!) must be assignable to this type.
   * @param exactTypeMatch - if <code>true</code>, the <code>fieldType</code>
   *        must match exactly the type of the static field, else if
   *        <code>false</code> the type of the field may be a sub-type of
   *        <code>fieldType</code> or one of the types may be
   *        {@link Class#isPrimitive() primitive} while the other is the
   *        {@link #getNonPrimitiveType(Class) according} object-type.
   * @param mustBeFinal - if <code>true</code>, an
   *        {@link IllegalArgumentException} is thrown if the specified static
   *        field exists but is NOT
   *        {@link java.lang.reflect.Modifier#isFinal(int) final},
   *        <code>false</code> otherwise.
   * @param inherit if <code>true</code> the field may be inherited from a
   *        {@link Class#getSuperclass() super-class} or
   *        {@link Class#getInterfaces() super-interface} of <code>type</code>,
   *        else if <code>false</code> the field is only accepted if it is
   *        declared in <code>type</code>.
   * @return the value of the field with the given type.
   * @throws NoSuchFieldException if the given <code>type</code> has no field
   *         with the given <code>fieldName</code>.
   * @throws IllegalAccessException if you do not have permission to read the
   *         field (e.g. field is private).
   * @throws IllegalArgumentException if the field is NOT static (or final) or
   *         has the wrong type.
   */
  <T> T getStaticField(Class<?> type, String fieldName, Class<T> fieldType, boolean exactTypeMatch,
      boolean mustBeFinal, boolean inherit) throws NoSuchFieldException, IllegalAccessException,
      IllegalArgumentException;

  /**
   * 
   * @param <T> the templated type the requested field is assigned to.
   * @param type is the class or interface containing the requested field.
   * @param fieldName is the {@link java.lang.reflect.Field#getName() name} of
   *        the requested field.
   * @param fieldType is the type the requested field is assigned to. Therefore
   *        the field declaration (!) must be assignable to this type.
   * @param exactTypeMatch - if <code>true</code>, the <code>fieldType</code>
   *        must match exactly the type of the static field, else if
   *        <code>false</code> the type of the field may be a sub-type of
   *        <code>fieldType</code> or one of the types may be
   *        {@link Class#isPrimitive() primitive} while the other is the
   *        {@link #getNonPrimitiveType(Class) according} object-type.
   * @param mustBeFinal - if <code>true</code>, an
   *        {@link IllegalArgumentException} is thrown if the specified static
   *        field exists but is NOT
   *        {@link java.lang.reflect.Modifier#isFinal(int) final},
   *        <code>false</code> otherwise.
   * @param inherit if <code>true</code> the field may be inherited from a
   *        {@link Class#getSuperclass() super-class} or
   *        {@link Class#getInterfaces() super-interface} of <code>type</code>,
   *        else if <code>false</code> the field is only accepted if it is
   *        declared in <code>type</code>.
   * @return the value of the field with the given type or <code>null</code> if
   *         the field does NOT exist or is NOT accessible.
   * @throws IllegalArgumentException if the field is NOT static (or final) or
   *         has the wrong type.
   */
  <T> T getStaticFieldOrNull(Class<?> type, String fieldName, Class<T> fieldType,
      boolean exactTypeMatch, boolean mustBeFinal, boolean inherit) throws IllegalArgumentException;

  /**
   * This method gets the parent method of the given <code>method</code>. The
   * parent method is the method overridden (is the sense of {@link Override})
   * by the given <code>method</code> or directly inherited from an
   * {@link Class#getInterfaces() interface}.
   * 
   * @param method is the method.
   * @return the parent method or <code>null</code> if no such method exists.
   * @throws SecurityException if access has been denied by the
   *         {@link SecurityManager}.
   */
  Method getParentMethod(Method method) throws SecurityException;

  /**
   * This method gets the method {@link Class#getMethod(String, Class[])
   * identified} by <code>methodName</code> and <code>parameterTypes</code> that
   * is NOT {@link Method#getDeclaringClass() declared} but inherited by the
   * given <code>declaringClass</code>.
   * 
   * @see #getParentMethod(Class, String, Class[])
   * 
   * @param inheritingClass is the class inheriting the requested method.
   * @param methodName is the {@link Method#getName() name} of the requested
   *        method.
   * @param parameterTypes is the {@link Method#getParameterTypes() signature}
   *        of the requested method.
   * @return the inherited method or <code>null</code> if no such method exists.
   * @throws SecurityException if access has been denied by the
   *         {@link SecurityManager}.
   */
  Method getParentMethod(Class<?> inheritingClass, String methodName, Class<?>[] parameterTypes)
      throws SecurityException;

  /**
   * This method finds all classes that are located in the package identified by
   * the given <code>packageName</code>.<br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath
   * multiple directories,JAR-, and WAR-files may need to be scanned.
   * 
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if <code>true</code> all sub-packages of the
   *        specified {@link Package} will be included in the search.
   * @return a {@link Set} will the fully qualified names of all requested
   *         classes.
   * @throws IOException if the operation failed with an I/O error.
   */
  Set<String> findClassNames(String packageName, boolean includeSubPackages) throws IOException;

  /**
   * This method finds all classes that are located in the package identified by
   * the given <code>packageName</code>.<br>
   * <b>ATTENTION:</b><br>
   * This is a relative expensive operation. Depending on your classpath
   * multiple directories,JAR-, and WAR-files may need to be scanned.
   * 
   * @param packageName is the name of the {@link Package} to scan.
   * @param includeSubPackages - if <code>true</code> all sub-packages of the
   *        specified {@link Package} will be included in the search.
   * @param classSet is where to add the classes.
   * @throws IOException if the operation failed with an I/O error.
   */
  void findClassNames(String packageName, boolean includeSubPackages, Set<String> classSet)
      throws IOException;

  /**
   * This method loads the classes given as {@link Collection} of
   * {@link Class#getName() fully qualified names} by
   * <code>qualifiedClassNames</code> and returns them as {@link Set}.
   * 
   * @param qualifiedClassNames is a collection containing the
   *        {@link Class#getName() qualified names} of the classes to load.
   * @return a {@link Set} with all loaded classes.
   * @throws ClassNotFoundException if one of the classes could NOT be loaded.
   */
  Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames) throws ClassNotFoundException;

  /**
   * This method loads the classes given as {@link Collection} of
   * {@link Class#getName() fully qualified names} by
   * <code>qualifiedClassNames</code>. It returns a {@link Set} containing only
   * those loaded classes that are {@link Filter#accept(Object) accepted} by the
   * given <code>filter</code>.
   * 
   * @param qualifiedClassNames is a collection containing the
   *        {@link Class#getName() qualified names} of the classes to load.
   * @param filter is used to filter the loaded classes.
   * @return a {@link Set} with all loaded classes that are
   *         {@link Filter#accept(Object) accepted} by the given
   *         <code>filter</code>.
   * @throws ClassNotFoundException if one of the classes could NOT be loaded.
   */
  Set<Class<?>> loadClasses(Collection<String> qualifiedClassNames, Filter<? super Class<?>> filter)
      throws ClassNotFoundException;

  /**
   * This method loads the classes given as {@link Collection} of names by
   * <code>classNames</code> using the given <code>classResolver</code>. It
   * returns a {@link Set} containing only those loaded classes that are
   * {@link Filter#accept(Object) accepted} by the given <code>filter</code>.
   * 
   * @param classNames is a collection containing the names of the classes to
   *        load. The class names should typically be the
   *        {@link Class#getName() qualified names} of the classes to load. But
   *        this may differ depending on the <code>classResolver</code>.
   * @param classResolver is used to load/resolve the classes by their names.
   * @param filter is used to filter the loaded classes.
   * @return a {@link Set} with all loaded classes that are
   *         {@link Filter#accept(Object) accepted} by the given
   *         <code>filter</code>.
   * @throws ClassNotFoundException if one of the classes could NOT be loaded.
   */
  Set<Class<?>> loadClasses(Collection<String> classNames, ClassResolver classResolver,
      Filter<? super Class<?>> filter) throws ClassNotFoundException;

}
