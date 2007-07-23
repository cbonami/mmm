/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.base;

import java.util.Iterator;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.util.collection.AbstractReadOnlyLookaheadIterator;

/**
 * This class is an enumeration for the fields of a class. It will enumerate the
 * fields defined by the class and the fields inherited from the sub-classes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentFieldIterator extends AbstractReadOnlyLookaheadIterator<ContentField> {

  /** the current class */
  private ContentClass currentClass;

  /** the current own field enumeration */
  private Iterator<? extends ContentField> currentIt;

  /**
   * The constructor.
   * 
   * @param contentClass is the class of that all {@link ContentField fields}
   *        are to be iterated.
   */
  public ContentFieldIterator(ContentClass contentClass) {

    super();
    this.currentClass = contentClass;
    this.currentIt = contentClass.getDeclaredFields().iterator();
    findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ContentField findNext() {

    while (this.currentIt.hasNext()) {
      ContentField field = this.currentIt.next();
      if (field.getSuperField() == null) {
        return field;
      }
    }
    if (this.currentClass != null) {
      this.currentClass = this.currentClass.getSuperClass();
      this.currentIt = this.currentClass.getDeclaredFields().iterator();
      return findNext();
    }
    return null;
  }

}
