/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.base;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.util.io.api.EncodingUtil;

/**
 * This is the test-case for {@link EncodingUtil} configured using spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class EncodingUtilSpringTest extends EncodingUtilTest {

  /**
   * {@inheritDoc}
   */
  @Override
  protected EncodingUtil getEncodingUtil() {

    return SpringContainerPool.getContainer("net/sf/mmm/util/io/beans-util-io.xml").getComponent(
        EncodingUtil.class);
  }

}
