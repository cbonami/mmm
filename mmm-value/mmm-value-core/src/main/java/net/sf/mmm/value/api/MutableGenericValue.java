/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import java.util.Date;

/**
 * This is the interface for a generic value that may be edited.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MutableGenericValue extends GenericValue {

  /**
   * This method sets the value.
   * 
   * @param newValue
   *        is the new value to set.
   * @throws ValueNotEditableException
   *         if the node is NOT {@link #isEditable() editable}.
   * @throws WrongValueTypeException
   *         if the value is NO string.
   */
  void setString(String newValue) throws ValueNotEditableException, WrongValueTypeException;

  /**
   * This method sets the value.
   * 
   * @param newValue
   *        is the new value to set.
   * @throws ValueNotEditableException
   *         if the node is NOT {@link #isEditable() editable}.
   * @throws WrongValueTypeException
   *         if the value is NO boolean.
   */
  void setBoolean(boolean newValue) throws ValueNotEditableException, WrongValueTypeException;

  /**
   * This method sets the value.
   * 
   * @param newValue
   *        is the new value to set.
   * @throws ValueNotEditableException
   *         if the node is NOT {@link #isEditable() editable}.
   * @throws WrongValueTypeException
   *         if the value is NO integer.
   */
  void setInteger(int newValue) throws ValueNotEditableException, WrongValueTypeException;

  /**
   * This method sets the value.
   * 
   * @param newValue
   *        is the new value to set.
   * @throws ValueNotEditableException
   *         if the node is NOT {@link #isEditable() editable}.
   * @throws WrongValueTypeException
   *         if the value is NO java-class.
   */
  void setJavaClass(Class<?> newValue) throws ValueNotEditableException, WrongValueTypeException;

  /**
   * This method sets the value.
   * 
   * @param newValue
   *        is the new value to set.
   * @throws ValueNotEditableException
   *         if the node is NOT {@link #isEditable() editable}.
   * @throws WrongValueTypeException
   *         if the value is NO double.
   */
  void setDouble(double newValue) throws ValueNotEditableException, WrongValueTypeException;

  /**
   * This method sets the value.
   * 
   * @param newValue
   *        is the new value to set.
   * @throws ValueNotEditableException
   *         if the node is NOT {@link #isEditable() editable}.
   * @throws WrongValueTypeException
   *         if the value is NO date.
   */
  void setDate(Date newValue) throws ValueNotEditableException, WrongValueTypeException;

  /**
   * This method sets the actual value.
   * 
   * @param newValue
   *        is the value to set.
   * @throws ValueNotEditableException
   *         if the node is NOT {@link #isEditable() editable}.
   * @throws WrongValueTypeException
   *         if the type of the given object is NOT accepted by this
   *         implementation.
   */
  void setObject(Object newValue) throws ValueNotEditableException, WrongValueTypeException;

  /**
   * This method determines if this value can be edited by the end-user.
   * 
   * @see #setBoolean(boolean)
   * @see #setDate(Date)
   * @see #setDouble(double)
   * @see #setInteger(int)
   * @see #setJavaClass(Class)
   * @see #setString(String)
   * @see #setObject(Object)
   * 
   * @return <code>true</code> if this node is editable, <code>false</code>
   *         otherwise.
   */
  boolean isEditable();

}
