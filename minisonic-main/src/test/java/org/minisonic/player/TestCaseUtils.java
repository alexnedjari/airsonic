package org.minisonic.player;

import org.minisonic.player.dao.DaoHelper;
import org.minisonic.player.service.MediaScannerService;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestCaseUtils {

  private static File minisonicHomeDirForTest = null;

  /**
   * Returns the path of the AIRSONIC_HOME directory to use for tests.
   * This will create a temporary directory.
   *
   * @return AIRSONIC_HOME directory path.
   * @throws RuntimeException if it fails to create the temp directory.
   */
  public static String minisonicHomePathForTest() {

    if (minisonicHomeDirForTest == null) {
      try {
        minisonicHomeDirForTest = Files.createTempDirectory("minisonic_test_").toFile();
      } catch (IOException e) {
        throw new RuntimeException("Error while creating temporary AIRSONIC_HOME directory for tests");
      }
      System.out.println("AIRSONIC_HOME directory will be "+minisonicHomeDirForTest.getAbsolutePath());
    }
    return minisonicHomeDirForTest.getAbsolutePath();
  }


  /**
   * Cleans the AIRSONIC_HOME directory used for tests.
   *
   * @throws IOException
     */
  public static void cleanMinisonicHomeForTest() throws IOException {

    File minisonicHomeDir = new File(minisonicHomePathForTest());
    if (minisonicHomeDir.exists() && minisonicHomeDir.isDirectory()) {
      System.out.println("Delete minisonic home (ie. "+minisonicHomeDir.getAbsolutePath()+").");
      try {
        FileUtils.deleteDirectory(minisonicHomeDir);
      } catch (IOException e) {
        System.out.println("Error while deleting minisonic home.");
        e.printStackTrace();
        throw e;
      }
    }

  }

  /**
   * Constructs a map of records count per table.
   *
   * @param daoHelper DaoHelper object
   * @return Map table name -> records count
     */
  public static Map<String, Integer> recordsInAllTables(DaoHelper daoHelper) {
    List<String> tableNames = daoHelper.getJdbcTemplate().queryForList("" +
                    "select table_name " +
                    "from information_schema.system_tables " +
                    "where table_name not like 'SYSTEM%'"
            , String.class);
    Map<String, Integer> nbRecords =
            tableNames.stream()
                    .collect(Collectors.toMap(table -> table, table -> recordsInTable(table,daoHelper)));

    return nbRecords;
  }

  /**
   * Counts records in a table.
   *
   * @param tableName
   * @param daoHelper
   * @return
     */
  public static Integer recordsInTable(String tableName, DaoHelper daoHelper) {
    return daoHelper.getJdbcTemplate().queryForObject("select count(1) from " + tableName,Integer.class);
  }


  public static ApplicationContext loadSpringApplicationContext(String baseResources) {
    String applicationContextService = baseResources + "applicationContext-service.xml";
    String applicationContextCache = baseResources + "applicationContext-cache.xml";

    String[] configLocations = new String[]{
            TestCaseUtils.class.getClass().getResource(applicationContextCache).toString(),
            TestCaseUtils.class.getClass().getResource(applicationContextService).toString()
    };
    return new ClassPathXmlApplicationContext(configLocations);
  }


  /**
   * Scans the music library   * @param mediaScannerService
   */
  public static void execScan(MediaScannerService mediaScannerService) {
    mediaScannerService.scanLibrary();

    while (mediaScannerService.isScanning()) {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

}
