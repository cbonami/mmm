/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets.richtext;

import net.sf.mmm.client.ui.api.common.RichTextFeature;

/**
 * This is the implementation of {@link FeatureBehavior} for {@link RichTextFeature#ITALIC}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
class FeatureBehaviorItalic extends AbstractToggleFeatureBehavior {

  /**
   * The constructor.
   * 
   * @param richTextToolbar is the {@link RichTextToolbar} owning this {@link FeatureBehavior}.
   */
  FeatureBehaviorItalic(RichTextToolbar richTextToolbar) {

    super(richTextToolbar);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public RichTextFeature getFeature() {

    return RichTextFeature.ITALIC;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValue() {

    return Boolean.valueOf(getFormatter().isItalic());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void toggle() {

    getFormatter().toggleItalic();
  }
}
