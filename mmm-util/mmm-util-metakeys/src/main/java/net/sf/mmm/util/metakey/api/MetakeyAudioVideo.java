/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.metakey.api;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface MetakeyAudioVideo extends MetakeyMultimedia {

  /**
   * The genre of the audio/video content. E.g. "Blues" or "Thriller".
   */
  String GENRE = "genre";

  /**
   * The name of the producer(s) of the audio/video content.
   */
  String PRODUCER = "producer";

  /**
   * The duration of the audio/video content.
   */
  String DURATION = "duration";

}
