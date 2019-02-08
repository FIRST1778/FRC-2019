package frc.lib.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@Tag("slow")
public class DebugLogTest {

  @Test
  @DisplayName("Before logging a marker, if no file exists, one should be created")
  public void logMarkerShouldCreateFileifNoneExists(@TempDir Path folder) throws IOException {
    File logFile = new File(folder.toString() + "/testLog.csv");
    DebugLog.setFile(logFile);

    assertThat(logFile.exists()).isEqualTo(false);

    DebugLog.logNote("Test");
    assertThat(logFile.exists()).isEqualTo(true);
  }

  @Test
  @DisplayName("The log file should be in a CSV format with the appropriate header")
  public void logFileShouldHaveProperCsvHeader(@TempDir Path folder) throws IOException {
    File logFile = new File(folder.toString() + "/testLog.csv");
    DebugLog.setFile(logFile);
    DebugLog.logNote("Test");

    assertThat(new String(Files.readAllBytes(logFile.toPath()), "UTF-8"))
        .contains("date,compileDate,match,info,exception");
  }
}
