/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.api;

import java.io.Serializable;

/**
 * This is the interface for a unique ID that identifies an object in the
 * content-repository. If the identified object is a content-resource this ID
 * points to the exact revision in the history of that resource.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentId extends Serializable {

  /** The name of this value type. */
  String VALUE_NAME = "Id";

  /*
   * long ID_FOLDER_CONTENT = 1;
   * 
   * long ID_FOLDER_ACTIONS = 5;
   */

  /** a reserved id that is always illegal */
  long ILLEGAL_ID = -1;

  /**
   * This method gets the unique string representation of this ID.
   * 
   * @see Object#toString()
   * 
   * @return the ID as string.
   */
  String toString();

}
