/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.io.impl;

import java.util.NoSuchElementException;

import net.sf.mmm.util.io.base.ByteArrayBuffer;

/**
 * This class is similar to {@link java.nio.ByteBuffer} but a lot simpler.
 * However it allows to {@link #setCurrentIndex(int) set the current index} so
 * the internal {@link #getBytes() buffer}-array can be consumed externally and
 * proceeded very fast.<br>
 * <b>ATTENTION:</b><br>
 * This class is NOT intended to be exposed. It should only be used internally
 * by some class or component.<br>
 * 
 * @see java.nio.ByteBuffer#wrap(byte[], int, int)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ByteArrayBufferImpl implements ByteArrayBuffer {

  /** @see #getBytes() */
  private final byte[] buffer;

  /** @see #getCurrentIndex() */
  private int currentIndex;

  /** @see #getMaximumIndex() */
  private int maximumIndex;

  /**
   * The constructor.
   * 
   * @param capacity is the <code>length</code> of the internal
   *        {@link #getBytes() buffer}.
   */
  public ByteArrayBufferImpl(int capacity) {

    this(new byte[capacity], 0, -1);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   */
  public ByteArrayBufferImpl(byte[] buffer) {

    this(buffer, 0, buffer.length - 1);
  }

  /**
   * The constructor.
   * 
   * @param buffer is the internal {@link #getBytes() buffer}.
   * @param currentIndex is the {@link #getCurrentIndex() current index}.
   * @param maximumIndex is the {@link #getMaximumIndex() maximum index}.
   */
  public ByteArrayBufferImpl(byte[] buffer, int currentIndex, int maximumIndex) {

    super();
    this.buffer = buffer;
    this.currentIndex = currentIndex;
    this.maximumIndex = maximumIndex;
  }

  /**
   * This method gets the underlying byte-array of this buffer. You are only
   * permitted to read the content from {@link #getCurrentIndex() currentIndex}
   * to {@link #getMaximumIndex() maximumIndex}. Only the creator of this object
   * should modify this array.
   * 
   * @see #getCurrentIndex()
   * @see #getMaximumIndex()
   * 
   * @return the buffer
   */
  public byte[] getBytes() {

    return this.buffer;
  }

  /**
   * {@inheritDoc}
   */
  public int getCurrentIndex() {

    return this.currentIndex;
  }

  /**
   * This method sets the {@link #getCurrentIndex() currentIndex}. This can be
   * done if data from the {@link #getBytes() buffer} has been consumed
   * externally.<br>
   * <b>ATTENTION:</b><br>
   * Be very careful and only use this method if you know what you are doing!
   * 
   * @param currentIndex is the {@link #getCurrentIndex() currentIndex} to set.
   *        It has to be in the range from <code>0</code> to
   *        <code>{@link #getMaximumIndex() maximumIndex} + 1</code>. A value of
   *        <code>{@link #getMaximumIndex() maximumIndex} + 1</code> indicates that the
   *        buffer is consumed.
   */
  public void setCurrentIndex(int currentIndex) {

    assert (currentIndex >= 0);
    assert (currentIndex <= (this.maximumIndex + 1));
    this.currentIndex = currentIndex;
  }

  /**
   * {@inheritDoc}
   */
  public int getMaximumIndex() {

    return this.maximumIndex;
  }

  /**
   * This method sets the {@link #getMaximumIndex() maximumIndex}. This may be
   * useful if the buffer should be reused.<br>
   * <b>ATTENTION:</b><br>
   * Be very careful and only use this method if you know what you are doing!
   * 
   * @param maximumIndex is the {@link #getMaximumIndex() maximumIndex} to set.
   *        It has to be in the range from <code>0</code> (
   *        <code>{@link #getCurrentIndex() currentIndex} - 1</code>) to
   *        <code>{@link #getBytes()}.length</code>.
   */
  public void setMaximumIndex(int maximumIndex) {

    this.maximumIndex = maximumIndex;
  }

  /**
   * {@inheritDoc}
   */
  public byte next() throws NoSuchElementException {

    if (this.currentIndex > this.maximumIndex) {
      throw new NoSuchElementException();
    }
    return this.buffer[this.currentIndex++];
  }

  /**
   * {@inheritDoc}
   */
  public byte peek() throws NoSuchElementException {

    if (this.currentIndex > this.maximumIndex) {
      throw new NoSuchElementException();
    }
    return this.buffer[this.currentIndex];
  }

  /**
   * {@inheritDoc}
   */
  public boolean hasNext() {

    return (this.currentIndex <= this.maximumIndex);
  }

  /**
   * {@inheritDoc}
   */
  public long skip(long byteCount) {

    int bytesLeft = this.maximumIndex - this.currentIndex + 1;
    int skip;
    if (bytesLeft > byteCount) {
      skip = (int) byteCount;
    } else {
      skip = bytesLeft;
    }
    this.currentIndex = this.currentIndex + skip;
    return skip;
  }

  /**
   * {@inheritDoc}
   */
  public int getBytesAvailable() {

    return this.maximumIndex - this.currentIndex + 1;
  }

}
