/* $Id$ */
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

import junit.framework.TestCase;

/**
 * This is the test-case for {@link FieldPojoDescriptorBuilder}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FieldPojoDescriptorBuilderTest extends AbstractPojoDescriptorBuilderTest {

  /**
   * The constructor.
   */
  public FieldPojoDescriptorBuilderTest() {

    super();
  }

  @Test
  public void testPojoDescriptor() throws Exception {

    PojoDescriptorBuilder factory = new FieldPojoDescriptorBuilder();
    PojoDescriptor<MyPojo> pojoDescriptor = factory.getDescriptor(MyPojo.class);
    assertEquals(MyPojo.class, pojoDescriptor.getPojoType());
    MyPojo pojoInstance = new MyPojo();
    // test property "name"
    checkProperty(pojoDescriptor, "name", String.class, String.class);
    String name = "Emma";
    pojoDescriptor.setProperty(pojoInstance, "name", name);
    String retrievedName = (String) pojoDescriptor.getProperty(pojoInstance, "name");
    assertEquals(name, retrievedName);
    // test property "port"
    checkProperty(pojoDescriptor, "port", Integer.class, Integer.class);
    Integer port = new Integer(4242);
    assertNull(pojoDescriptor.getProperty(pojoInstance, "port"));
    pojoDescriptor.setProperty(pojoInstance, "port", port);
    Integer retrievedPort = (Integer) pojoDescriptor.getProperty(pojoInstance, "port");
    assertEquals(port, retrievedPort);
    // test property "flag"
    checkProperty(pojoDescriptor, "flag", Boolean.class, Boolean.class);
    Boolean flag = Boolean.TRUE;
    assertNull(pojoDescriptor.getProperty(pojoInstance, "flag"));
    pojoDescriptor.setProperty(pojoInstance, "flag", Boolean.valueOf(flag));
    assertEquals(flag, pojoDescriptor.getProperty(pojoInstance, "flag"));
    // test property "items"/"item"
    checkProperty(pojoDescriptor, "items", List.class, List.class);
    List<String> myList = new ArrayList<String>();
    pojoDescriptor.setProperty(pojoInstance, "items", myList);
    assertSame(myList, pojoDescriptor.getProperty(pojoInstance, "items"));
    /*
     * String item1 = "item1"; pojoDescriptor.addPropertyItem(pojoInstance,
     * "item", item1); assertEquals(1, myList.size()); assertEquals(item1,
     * myList.get(0)); String item2 = "item2";
     * pojoDescriptor.addPropertyItem(pojoInstance, "item", item2);
     * assertEquals(2, myList.size()); assertEquals(item1, myList.get(1));
     */
  }

}