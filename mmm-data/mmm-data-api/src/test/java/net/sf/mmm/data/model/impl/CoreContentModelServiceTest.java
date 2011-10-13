/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.model.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import net.sf.mmm.data.api.DataObject;
import net.sf.mmm.data.api.reflection.DataClass;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.api.reflection.ContentReflectionService;
import net.sf.mmm.data.reflection.impl.CoreContentModelService;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This is the test-case for {@link CoreContentModelService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@SuppressWarnings("all")
public class CoreContentModelServiceTest {

  public void checkClass(ContentReflectionService modelService, DataClass contentClass) {

    assertNotNull(contentClass);
    assertSame(contentClass, modelService.getContentClass(contentClass.getTitle()));
    assertSame(contentClass, modelService.getContentClass(contentClass.getId()));
  }

  @Test
  @Ignore
  public void testModelService() throws Exception {

    CoreContentModelService modelService = new CoreContentModelService();
    modelService.initialize();

    DataClass rootClass = modelService.getRootContentClass();
    checkClass(modelService, rootClass);
    assertEquals(DataObject.CLASS_NAME, rootClass.getTitle());
    assertNotNull(rootClass.getJavaClass());
    assertTrue(DataObject.class.isAssignableFrom(rootClass.getJavaClass()));

    DataClass classClass = modelService.getContentClass(rootClass);
    checkClass(modelService, classClass);
    assertEquals(DataClass.CLASS_NAME, classClass.getTitle());
    assertNotNull(classClass.getJavaClass());
    assertTrue(DataClass.class.isAssignableFrom(classClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(classClass));
    assertTrue(classClass.isSubClassOf(rootClass));

    DataField idField = rootClass.getField(DataObject.FIELD_NAME_ID);
    assertNotNull(idField);
    assertEquals(DataObject.FIELD_NAME_ID, idField.getTitle());
    assertSame(rootClass, idField.getDeclaringClass());

    DataClass fieldClass = modelService.getContentClass(idField);
    checkClass(modelService, fieldClass);
    assertEquals(DataField.CLASS_NAME, fieldClass.getTitle());
    assertNotNull(fieldClass.getJavaClass());
    assertTrue(DataField.class.isAssignableFrom(fieldClass.getJavaClass()));
    assertTrue(rootClass.isSuperClassOf(fieldClass));
    assertTrue(fieldClass.isSubClassOf(rootClass));
  }

}
