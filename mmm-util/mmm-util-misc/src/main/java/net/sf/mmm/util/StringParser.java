/* $Id$ */
package net.sf.mmm.util;

/**
 * This class represents a string together with a
 * {@link #getCurrentIndex() index position} in that string.<br>
 * This implementation is NOT thread-safe.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class StringParser implements CharSequence {

  /** the original string */
  private String str;

  /** the string to parse as char array */
  private char[] chars;

  /** @see #getCurrentIndex() */
  private int pos;

  /**
   * The constructor
   * 
   * @param string
   *        is the string to parse.
   */
  public StringParser(String string) {

    super();
    this.str = string;
    this.chars = string.toCharArray();
    this.pos = 0;
  }

  /**
   * @see java.lang.CharSequence#charAt(int)
   */
  public char charAt(int index) {

    return this.chars[index];
  }

  /**
   * @see java.lang.CharSequence#length()
   */
  public int length() {

    return this.chars.length;
  }

  /**
   * @see java.lang.CharSequence#subSequence(int, int)
   */
  public CharSequence subSequence(int start, int end) {

    return new CharSubSequence(this, start, end);
  }

  /**
   * @see String#substring(int, int)
   * 
   * @param start
   *        the start index, inclusive.
   * @param end
   *        the end index, exclusive.
   * @return the specified substring.
   */
  public String substring(int start, int end) {

    return new String(this.chars, start, end - start);
  }

  /**
   * This method gets the current {@link String#charAt(int) position} in the
   * string to parse. It will initially be <code>0</code>.
   * 
   * @return the current index position.
   */
  public int getCurrentIndex() {

    return this.pos;
  }

  /**
   * This method sets the {@link #getCurrentIndex() current index}.
   * 
   * @param index
   *        is the next index position to set. The value has to be greater or
   *        equal to <code>0</code> and less or equal to {@link #length()}.
   */
  public void setCurrentIndex(int index) {

    if ((index < 0) || (index > this.chars.length)) {
      throw new IndexOutOfBoundsException("" + index);
    }
    this.pos = index;
  }

  /**
   * This method determines if there is at least one character available.
   * 
   * @return <code>true</code> if there is at least one character available,
   *         <code>false</code> if the end of the string has been reached.
   */
  public boolean hasNext() {

    return (this.pos < this.chars.length);
  }

  /**
   * This method reads the {@link #getCurrentIndex() current}
   * {@link #charAt(int) character} without incementing the
   * {@link #getCurrentIndex() index}. You need to {@link #hasNext() check} if
   * a character is available before calling this method.
   * 
   * @return the current character.
   */
  public char peek() {

    return this.chars[this.pos];
  }

  /**
   * This method reads the {@link #getCurrentIndex() current}
   * {@link #charAt(int) character} and increments the
   * {@link #getCurrentIndex() index}. You need to {@link #hasNext() check} if
   * a character is available before calling this method.
   * 
   * @return the current character.
   */
  public char next() {

    return this.chars[this.pos++];
  }

  /**
   * This method reads the {@link #getCurrentIndex() current}
   * {@link #charAt(int) character} and increments the
   * {@link #getCurrentIndex() index}. If there is no character
   * {@link #hasNext() available} this method will do nothing and returns
   * <code>0</code> (the NULL character and NOT <code>'0'</code>).
   * 
   * @return the current character or <code>0</code> if none is
   *         {@link #hasNext() available}.
   */
  public char forceNext() {

    if (this.pos < this.chars.length) {
      return this.chars[this.pos++];
    } else {
      return 0;
    }
  }

  /**
   * This method decrements the {@link #getCurrentIndex() index} by one. If the
   * {@link #getCurrentIndex() index} is <code>0</code> this method will have
   * no effect.<br>
   * E.g. use this method if you read a character too much.
   */
  public void stepBack() {

    if (this.pos > 0) {
      this.pos--;
    }
  }

  /**
   * This method skips all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.
   * 
   * @param stop
   *        is the character to read until.
   * @return <code>true</code> if the first occurence of the given
   *         <code>stop</code> character has been passed, <code>false</code>
   *         if there is no such character.
   */
  public boolean skipUntil(char stop) {

    while (this.pos < this.chars.length) {
      if (this.chars[this.pos++] == stop) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character after the (first) <code>stop</code>
   * character or to the end of the string if NO such character exists.
   * 
   * @param stop
   *        is the character to read until.
   * @param acceptEof
   *        if <code>true</code> EOF will be treated as <code>stop</code>,
   *        too.
   * @return the string with all read characters excluding the <code>stop</code>
   *         character or <code>null</code> if there was no <code>stop</code>
   *         character and <code>acceptEof</code> is <code>false</code>.
   */
  public String readUntil(char stop, boolean acceptEof) {

    int startIndex = this.pos;
    while (this.pos < this.chars.length) {
      if (this.chars[this.pos++] == stop) {
        return new String(this.chars, startIndex, this.pos - startIndex - 1);
      }
    }
    if (acceptEof) {
      int len = this.pos - startIndex;
      if (len > 0) {
        return new String(this.chars, startIndex, len);
      } else {
        return "";
      }
    } else {
      return null;
    }
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.
   * In advance to {@link #skipUntil(char)}, this method will read over the
   * <code>stop</code> character if it is properly escaped.
   * 
   * @param stop
   *        is the character to read until.
   * @param escape
   *        is the character used to escape the stop character (e.g. '\').
   * @return <code>true</code> if the first occurence of the given
   *         <code>stop</code> character has been passed, <code>false</code>
   *         if there is no such character.
   */
  public boolean skipUntil(char stop, char escape) {

    boolean escapeActive = false;
    while (this.pos < this.chars.length) {
      char c = this.chars[this.pos++];
      if (c == escape) {
        escapeActive = !escapeActive;
      } else {
        if ((c == stop) && (!escapeActive)) {
          return true;
        }
        escapeActive = false;
      }
    }
    return false;
  }

  /**
   * This method reads all {@link #next() next characters} until the given
   * <code>stop</code> character or the end of the string to parse is reached.
   * In advance to {@link #readUntil(char, boolean)}, this method will read
   * over the <code>stop</code> character if it is properly escaped. <br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character after the (first) <code>stop</code>
   * character or to the end of the string if NO such character exists.
   * 
   * @param stop
   *        is the character to read until.
   * @param escape
   *        is the character used to escape the stop character (e.g. '\').
   * @param acceptEof
   *        if <code>true</code> EOF will be treated as <code>stop</code>,
   *        too.
   * @return the string with all read characters excluding the <code>stop</code>
   *         character or <code>null</code> if there was no <code>stop</code>
   *         character.
   */
  public String readUntil(char stop, char escape, boolean acceptEof) {

    StringBuffer result = new StringBuffer();
    boolean escapeActive = false;
    int index = this.pos;
    while (this.pos < this.chars.length) {
      char c = this.chars[this.pos++];
      if (c == escape) {
        if (escapeActive) {
          result.append(escape);
        } else {
          int len = this.pos - index - 1;
          if (len > 0) {
            result.append(this.chars, index, len);
          }
        }
        index = this.pos;
        escapeActive = !escapeActive;
      } else {
        if ((c == stop) && (!escapeActive)) {
          int len = this.pos - index - 1;
          if (len > 0) {
            result.append(this.chars, index, len);
          }
          return result.toString();
        }
        escapeActive = false;
      }
    }
    return null;
  }

  /**
   * This method reads all {@link #next() next characters} that are identical to
   * the character given by <code>c</code>.<br>
   * E.g. use <code>{@link #skipWhile(char) readWhile}(' ')</code> to skip
   * all blanks from the {@link #getCurrentIndex() current index}. After the
   * call of this method, the {@link #getCurrentIndex() current index} will
   * point to the next character that is different to <code>c</code> or to the
   * end of the string if NO such character exists.
   * 
   * @param c
   *        is the character to read over.
   * @return the number of characters that have been skipped.
   */
  public int skipWhile(char c) {

    int currentPos = this.pos;
    while (this.pos < this.chars.length) {
      if (this.chars[this.pos] != c) {
        break;
      }
      this.pos++;
    }
    return this.pos - currentPos;
  }

  /**
   * This method reads all {@link #next() next characters} that are
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character that was NOT
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>
   * or to the end of the string if NO such character exists.
   * 
   * @see #skipWhile(char)
   * 
   * @param filter
   *        is used to {@link CharFilter#accept(char) decide} which characters
   *        should be accepted.
   * @return the number of characters {@link CharFilter#accept(char) accepted}
   *         by the given <code>filter</code> that have been skipped.
   */
  public int skipWhile(CharFilter filter) {

    int currentPos = this.pos;
    while (this.pos < this.chars.length) {
      char c = this.chars[this.pos];
      if (!filter.accept(c)) {
        break;
      }
      this.pos++;
    }
    return this.pos - currentPos;
  }

  /**
   * This method reads all {@link #next() next characters} that are
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>.<br>
   * After the call of this method, the {@link #getCurrentIndex() current index}
   * will point to the next character that was NOT
   * {@link CharFilter#accept(char) accepted} by the given <code>filter</code>
   * or to the end of the string if NO such character exists.
   * 
   * @see #skipWhile(CharFilter)
   * 
   * @param filter
   *        is used to {@link CharFilter#accept(char) decide} which characters
   *        should be accepted.
   * @return a string with all characters
   *         {@link CharFilter#accept(char) accepted} by the given
   *         <code>filter</code>.
   */
  public String readWhile(CharFilter filter) {

    int currentPos = this.pos;
    int len = skipWhile(filter);
    if (len == 0) {
      return "";
    } else {
      return new String(this.chars, currentPos, len);
    }
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {

    return this.str;
  }

}
