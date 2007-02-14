/* $Id$ */
package net.sf.mmm.configuration.base.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.sf.mmm.configuration.base.AbstractConfiguration;

/**
 * This class iterates over all child nodes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SiblingIterator implements Iterator<AbstractConfiguration> {

  /** the next item */
  private AbstractConfiguration headSibling;

  /** the next item */
  private AbstractConfiguration next;

  /**
   * The constructor.
   */
  public SiblingIterator() {

    this(null);
  }

  /**
   * The constructor.
   * 
   * @param head
   *        is the head configuration of the sibling-list.
   */
  public SiblingIterator(AbstractConfiguration head) {

    super();
    this.next = head;
    this.headSibling = head;
  }

  /**
   * @param nextSibling
   *        the next to set
   */
  public void setNext(AbstractConfiguration nextSibling) {

    this.next = nextSibling;
    this.headSibling = nextSibling;
  }

  /**
   * This method steps to the next element.
   * 
   * @return <code>true</code> if a next sibling was found, <code>false</code>
   *         otherwise.
   */
  protected boolean stepNext() {

    AbstractConfiguration configuration;
    if (this.next != null) {
      configuration = this.next.getNextSibling();
      if (configuration == this.headSibling) {
        this.next = null;
      } else {
        this.next = configuration;
        return true;
      }
    }
    return false;
  }

  /**
   * @see java.util.Iterator#hasNext()
   */
  public boolean hasNext() {

    return (this.next != null);
  }

  /**
   * @see java.util.Iterator#next()
   */
  public AbstractConfiguration next() {

    if (this.next != null) {
      AbstractConfiguration result = this.next;
      stepNext();
      return result;
    } else {
      throw new NoSuchElementException();
    }
  }

  /**
   * @see java.util.Iterator#remove()
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
