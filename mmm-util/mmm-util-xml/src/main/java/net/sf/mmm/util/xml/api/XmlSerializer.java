/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.api;

import net.sf.mmm.util.xml.XmlException;

/**
 * This is the interface for an serializer that can convert objects of a
 * specific type to XML.
 * 
 * @see net.sf.mmm.util.xml.api.XmlSerializable
 * 
 * @param <O>
 *        is the templated type of the objects to serialize.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface XmlSerializer<O> {

  /**
   * This method creates an XML representation of the given value. <br>
   * 
   * @see XmlSerializable#toXml(XmlWriter)
   * 
   * @param xmlWriter
   *        is where the XML is written to.
   * @param object
   *        is the object to serialize. May be <code>null</code>.
   * @throws XmlException
   *         if the serialization fails (I/O error, invalid XML, etc.).
   */
  void toXml(XmlWriter xmlWriter, O object) throws XmlException;

}
