/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.impl.dummy.MyPojo;

import static org.junit.Assert.*;

/**
 * This is the test-case for {@link PublicMethodPojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PublicMethodPojoDescriptorBuilderTest extends AbstractMyPojoDescriptorBuilderTest {

  @Test
  public void testPojoDescriptor() throws Exception {

    PojoDescriptorBuilder factory = new PublicMethodPojoDescriptorBuilder();
    PojoDescriptor<MyPojo> pojoDescriptor = factory.getDescriptor(MyPojo.class);
    assertEquals(MyPojo.class, pojoDescriptor.getPojoType());
    MyPojo pojoInstance = new MyPojo();
    // test property "class"
    assertEquals(MyPojo.class, pojoDescriptor.getProperty(pojoInstance, "class"));
    checkPojo(pojoDescriptor, pojoInstance);
    // test property "port"
    checkProperty(pojoDescriptor, "port", Integer.class, int.class);
    // test property "flag"
    checkProperty(pojoDescriptor, "flag", Boolean.class, boolean.class);

    List<String> myList = new ArrayList<String>();
    pojoDescriptor.setProperty(pojoInstance, "items", myList);
    assertSame(myList, pojoDescriptor.getProperty(pojoInstance, "items"));
    String item1 = "item1";
    pojoDescriptor.addPropertyItem(pojoInstance, "item", item1);
    assertEquals(1, myList.size());
    assertEquals(item1, myList.get(0));
    String item2 = "item2";
    pojoDescriptor.addPropertyItem(pojoInstance, "item", item2);
    assertEquals(2, myList.size());
    assertEquals(item2, myList.get(1));
  }

}
