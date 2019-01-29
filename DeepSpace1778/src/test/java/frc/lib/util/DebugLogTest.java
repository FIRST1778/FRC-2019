package frc.lib.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.support.io.TempDirectory;
import org.junit.jupiter.api.support.io.TempDirectory.TempDir;

public class DebugLogTest {

  @Test
  @ExtendWith(TempDirectory.class)
  public void logMarkerShouldCreateFileifNoneExists(@TempDir Path folder) throws IOException {
    File logFile = new File(folder.toString() + "/testLog.csv");
    DebugLog.setFile(logFile);

    assertThat("Log file should not exist yet", logFile.exists(), is(false));

    DebugLog.logNote("Test");
    assertThat("Log file should exist now", logFile.exists(), is(true));
  }

  @Test
  @ExtendWith(TempDirectory.class)
  public void logFileShouldHaveProperCsvHeader(@TempDir Path folder) throws IOException {
    File logFile = new File(folder.toString() + "/testLog.csv");
    DebugLog.setFile(logFile);
    DebugLog.logNote("Test");

    assertThat(
        "Log file should contain proper header",
        new String(Files.readAllBytes(logFile.toPath()), "UTF-8"),
        containsString("date,compileDate,match,info,exception"));
  }
}
