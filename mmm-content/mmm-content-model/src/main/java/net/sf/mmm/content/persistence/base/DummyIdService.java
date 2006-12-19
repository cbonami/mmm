/* $Id$ */
package net.sf.mmm.content.persistence.base;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.content.value.impl.IdImpl;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DummyIdService extends AbstractIdService {

  /** @see #createClassId() */
  private int classIdCounter;

  /** @see #createFieldId() */
  private long fieldIdCounter;

  /** @see #createResourceId(ContentClass) */
  private long resourceIdCounter;
  
   /**
    * The constructor
    */
  public DummyIdService() {

    super();
    this.classIdCounter = IdImpl.MINIMUM_CUSTOM_CLASS_ID;
    this.fieldIdCounter = IdImpl.MINIMUM_CUSTOM_FIELD_ID;
    this.resourceIdCounter = IdImpl.MINIMUM_CUSTOM_RESOURCE_ID;
  }

  /**
   * @see net.sf.mmm.content.persistence.base.AbstractIdService#createClassId()
   */
  @Override
  protected Id createClassId() {

    return new IdImpl(this.classIdCounter++);
  }

  /**
   * @see net.sf.mmm.content.persistence.base.AbstractIdService#createFieldId()
   */
  @Override
  protected Id createFieldId() {

    return new IdImpl(this.fieldIdCounter++, IdImpl.CLASS_ID_FIELD);
  }

  /**
   * @see net.sf.mmm.content.persistence.base.AbstractIdService#createResourceId(net.sf.mmm.content.model.api.ContentClass)
   */
  @Override
  protected Id createResourceId(ContentClass type) {

    return new IdImpl(this.resourceIdCounter++, ((IdImpl) type.getId()).getClassId());
  }

}
