package frc.lib.util;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DebugLogTest {

  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void logMarkerShouldCreateFileifNoneExists() throws IOException {
    File logFile = new File(folder.newFolder().toString() + "/testLog.csv");
    DebugLog.setFile(logFile);

    Assert.assertThat("Log file should not exist yet", logFile.exists(), is(false));

    DebugLog.logNote("Test");
    Assert.assertThat("Log file should exist now", logFile.exists(), is(true));
  }

  @Test
  public void logFileShouldHaveProperCsvHeader() throws IOException {
    File logFile = new File(folder.newFolder().toString() + "/testLog.csv");
    DebugLog.setFile(logFile);
    DebugLog.logNote("Test");

    Assert.assertThat(
        "Log file should contain proper header",
        new String(Files.readAllBytes(logFile.toPath()), "UTF-8"),
        containsString("date,compileDate,match,info,exception"));
  }
}
