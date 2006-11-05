/* $Id$ */
package net.sf.mmm.ui.toolkit.impl.swt.widget.editor;

import java.util.Date;
import java.util.Locale;

import org.eclipse.swt.SWT;
import net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor;
import net.sf.mmm.ui.toolkit.impl.swt.UIFactorySwt;
import net.sf.mmm.ui.toolkit.impl.swt.UISwtNode;
import net.sf.mmm.ui.toolkit.impl.swt.sync.AbstractSyncControlAccess;
import net.sf.mmm.ui.toolkit.impl.swt.sync.SyncCompositeAccess;
import net.sf.mmm.ui.toolkit.impl.swt.widget.AbstractUIWidget;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor} interface
 * using SWT as the UI toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UIDateEditorImpl extends AbstractUIWidget implements UIDateEditor {

  /** */
  private final SyncCompositeAccess syncAccess;

  /**
   * The constructor.
   * 
   * @param uiFactory
   * @param parentObject
   */
  public UIDateEditorImpl(UIFactorySwt uiFactory, UISwtNode parentObject) {

    super(uiFactory, parentObject);
    this.syncAccess = new SyncCompositeAccess(uiFactory, SWT.DEFAULT);
  }

  /**
   * @see net.sf.mmm.ui.toolkit.impl.swt.AbstractUIComponent#getSyncAccess()
   */
  @Override
  public AbstractSyncControlAccess getSyncAccess() {

    return this.syncAccess;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor#setDate(java.util.Date)
   */
  public void setDate(Date newDate) {

  // TODO Auto-generated method stub

  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.widget.editor.UIDateEditor#getDate()
   */
  public Date getDate() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.UIObject#getType()
   */
  public String getType() {

    return TYPE;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIReadLocale#getLocale()
   */
  public Locale getLocale() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * @see net.sf.mmm.ui.toolkit.api.state.UIWriteLocale#setLocale(java.util.Locale)
   */
  public void setLocale(Locale newLocale) {

  // TODO Auto-generated method stub

  }

}
