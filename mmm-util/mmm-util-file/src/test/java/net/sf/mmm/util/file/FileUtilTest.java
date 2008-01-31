/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.file;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.junit.Test;

/**
 * This is the test-case for {@link FileUtil}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class FileUtilTest {

  protected FileUtil getFileUtil() {

    return FileUtil.getInstance();
  }

  @Test
  public void testExtension() {

    FileUtil util = getFileUtil();
    assertEquals("java", util.getExtension("test.java"));
    assertEquals("", util.getExtension(".java"));
    assertEquals("gz", util.getExtension("archive.tar.gz"));
  }

  @Test
  public void testNormalizePath() {

    FileUtil util = getFileUtil();
    assertEquals(File.separator, util.normalizePath("////.//"));
    assertEquals(File.separator, util.normalizePath("/foo/../bar/.."));
    assertEquals(File.separator + "foo", util.normalizePath("/foo/bar/../bar/.."));
    assertEquals(File.separator + "foo", util.normalizePath("/foo\\bar/..\\bar/.."));
    assertEquals(File.separator + "foo", util.normalizePath("/foo\\//.\\./bar/..\\bar/.."));
    assertEquals("foo" + File.separator + "bar", util.normalizePath("foo\\//.\\./bar/."));
    assertEquals(System.getProperty("user.home"), util.normalizePath("~"));
    assertEquals(System.getProperty("user.home") + File.separator, util.normalizePath("~/"));
    assertEquals(System.getProperty("user.home"), util.normalizePath("~/foo/./.."));
    assertEquals(util.normalizePath(System.getProperty("user.home") + "/../someuser"), util
        .normalizePath("~someuser"));
  }

  @Test
  public void testBasename() {

    FileUtil util = getFileUtil();
    assertEquals("", util.getBasename(""));
    assertEquals("a", util.getBasename("a"));
    assertEquals("/", util.getBasename("/"));
    assertEquals("/", util.getBasename("///"));
    assertEquals("\\", util.getBasename("\\"));
    assertEquals("\\", util.getBasename("\\\\"));
    assertEquals("\\", util.getBasename("\\/\\"));
    assertEquals("/", util.getBasename("/\\/"));
    assertEquals(".", util.getBasename("/."));
    assertEquals("..", util.getBasename("/.."));
    assertEquals("foo", util.getBasename("foo"));
    assertEquals("bar", util.getBasename("foo/bar"));
    assertEquals("foo.bar", util.getBasename("foo.bar"));
    assertEquals("foo.bar", util.getBasename("./foo.bar"));
    assertEquals("foo", util.getBasename("/foo"));
    assertEquals("foo", util.getBasename("/foo/"));
    assertEquals("bar", util.getBasename("/foo/bar"));
    assertEquals("bar", util.getBasename("/foo/bar//"));
    assertEquals("", util.getBasename("c:\\"));
    assertEquals("", util.getBasename("http://"));
    assertEquals("foo", util.getBasename("c:\\foo"));
    assertEquals("foo", util.getBasename("http://foo"));
    assertEquals("bar", util.getBasename("http://foo.org/bar"));
  }

  @Test
  public void testDirname() {

    FileUtil util = getFileUtil();
    assertEquals("/", util.getDirname("/"));
    assertEquals("/", util.getDirname("/foo"));
    assertEquals("/", util.getDirname("/foo/"));
    assertEquals("\\", util.getDirname("\\foo\\"));
    assertEquals(".", util.getDirname("foo"));
    assertEquals(".", util.getDirname("foo/"));
    assertEquals(".", util.getDirname("foo\\"));
    assertEquals("/foo", util.getDirname("/foo/bar"));
    assertEquals("/foo", util.getDirname("/foo/bar/"));
    assertEquals("foo", util.getDirname("foo/bar"));
    assertEquals("foo", util.getDirname("foo/bar/"));
    assertEquals("foo", util.getDirname("foo\\bar\\"));
    assertEquals("./foo", util.getDirname("./foo/bar"));
    assertEquals("foo/bar", util.getDirname("foo/bar/test"));
    assertEquals("foo\\bar/test", util.getDirname("foo\\bar/test/xxx"));
    assertEquals("foo\\bar/test", util.getDirname("foo\\bar/test/xxx\\"));
    assertEquals("C:\\", util.getDirname("C:\\foo"));
    assertEquals("C:", util.getDirname("C:"));
    assertEquals("http://", util.getDirname("http://"));
    assertEquals("http://", util.getDirname("http://foo"));
  }

  protected void checkTestdata(File originalFile, File copyFile) throws IOException {

    assertEquals(originalFile.length(), copyFile.length());
    Properties copyProperties = new Properties();
    FileInputStream in = new FileInputStream(copyFile);
    copyProperties.load(in);
    in.close();
    assertEquals("This is only a test", copyProperties.get("Message1"));
    assertEquals("The second test", copyProperties.get("Message2"));
  }

  @Test
  public void testCopyFile() throws IOException {

    FileUtil util = getFileUtil();
    File originalFile = new File("src/test/resources/net/sf/mmm/util/file/testdata.properties");
    File copyFile = File.createTempFile("testdata", ".properties");
    util.copyFile(originalFile, copyFile);
    checkTestdata(originalFile, copyFile);
    copyFile.delete();
  }

  @Test
  public void testCopyDir() throws IOException {

    FileUtil util = getFileUtil();
    File tempDir = util.getTemporaryDirectory();
    String uidName = "mmm-" + UUID.randomUUID();
    File subdir = new File(tempDir, uidName);
    boolean success = subdir.mkdir();
    assertTrue(success);
    assertTrue(subdir.isDirectory());
    File originalFile = new File("src/test/resources/net/sf/mmm/util/file/testdata.properties");
    File copyFile = new File(subdir, originalFile.getName());
    util.copyFile(originalFile, copyFile);
    checkTestdata(originalFile, copyFile);
    File testFile = new File(subdir, "test.properties");
    success = testFile.createNewFile();
    assertTrue(success);
    File subsubdir = new File(subdir, "folder");
    success = subsubdir.mkdir();
    assertTrue(success);
    File fooFile = new File(subsubdir, "foo.properties");
    success = fooFile.createNewFile();
    assertTrue(success);
    File[] matchingFiles = util.getMatchingFiles(subdir, "*/*.properties", FileType.FILE);
    assertEquals(1, matchingFiles.length);
    assertEquals(matchingFiles[0], fooFile);
    matchingFiles = util.getMatchingFiles(subdir, "**/*.properties", FileType.FILE);
    assertEquals(3, matchingFiles.length);
    int magic = 0;
    for (File file : matchingFiles) {
      if (file.equals(fooFile)) {
        magic += 1;
      } else if (file.equals(testFile)) {
        magic += 2;
      } else if (file.equals(copyFile)) {
        magic += 4;
      }
    }
    assertEquals(7, magic);
    File copyDir = new File(tempDir, uidName + "-copy");
    util.copyRecursive(subdir, copyDir, false);
    matchingFiles = util.getMatchingFiles(copyDir, "*/*.properties", FileType.FILE);
    assertEquals(1, matchingFiles.length);
    assertEquals(matchingFiles[0].getName(), fooFile.getName());
    matchingFiles = util.getMatchingFiles(copyDir, "**/*.properties", FileType.FILE);
    assertEquals(3, matchingFiles.length);
    magic = 0;
    for (File file : matchingFiles) {
      String filename = file.getName();
      if (filename.equals(fooFile.getName())) {
        magic += 1;
      } else if (filename.equals(testFile.getName())) {
        magic += 2;
      } else if (filename.equals(copyFile.getName())) {
        magic += 4;
      }
    }
    assertEquals(7, magic);
    util.deleteRecursive(subdir);
    util.deleteRecursive(copyDir);
    assertFalse(subdir.exists());
  }

}
