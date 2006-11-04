/* $Id$ */
package net.sf.mmm.configuration.base.path;

import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This is the interface for a condition that
 * {@link #accept(AbstractConfiguration) filters}
 * {@link net.sf.mmm.configuration.api.Configuration configurations}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ConditionIF {

  /**
   * This method determines if the given <code>configuration</code> is
   * accepted for this condition.
   * 
   * @param configuration
   *        is the configuration to check.
   * @return <code>true</code> if the given <code>configuration</code> is
   *         acceptable, <code>false</code> otherwise.
   */
  boolean accept(AbstractConfiguration configuration);

}
