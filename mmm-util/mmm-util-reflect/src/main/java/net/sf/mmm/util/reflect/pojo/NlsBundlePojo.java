/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo;

import net.sf.mmm.util.nls.AbstractResourceBundle;

/**
 * This class holds the internationalized messages for the POJO support.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class NlsBundlePojo extends AbstractResourceBundle {

  /** @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_FOUND = "Property \"{0}\" not found in \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.descriptor.api.PojoPropertyNotFoundException */
  public static final String ERR_PROPERTY_NOT_ACCESSABLE = "Property \"{0}\" not "
      + "accessible for \"{2}\" in \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.PojoPathSegmentIsNullException */
  public static final String ERR_PATH_SEGMENT_IS_NULL = "The pojo-path \"{0}\" "
      + "for object \"{1}\" evaluates to null!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.PojoPathCreationException */
  public static final String ERR_PATH_CREATION = "Failed to create the "
      + "object at the pojo-path \"{0}\" for object \"{1}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_PATH_ILLEGAL = "Illegal pojo-path \"{0}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.IllegalPojoPathException */
  public static final String ERR_FUNCTION_UNDEFINED = "Undefined function \"{0}\"!";

  /** @see net.sf.mmm.util.reflect.pojo.path.api.PojoPathFunctionUnsupportedOperationException */
  public static final String ERR_FUNCTION_UNSUPPORTED_OPERATION = "The function "
      + "\"{1}\" does NOT support the operation \"{0}\"!";

}
