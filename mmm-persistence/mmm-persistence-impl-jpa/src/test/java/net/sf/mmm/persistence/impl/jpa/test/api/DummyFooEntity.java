/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa.test.api;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DummyFooEntity extends DummyFooEntityView {

  public abstract void setBar(DummyBarEntity bar);

  public abstract void setNumber(int number);

  public abstract DummyBarEntity getBar();

  public abstract int getNumber();

}
