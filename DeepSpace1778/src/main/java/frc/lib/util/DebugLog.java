package frc.lib.util;

import edu.wpi.first.wpilibj.DriverStation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

/**
 * Logs data as a CSV file on the roboRIO, saved as /home/lvuser/crashLog.csv. This file can be
 * later retrieved through SFTP/SSH and filtered or viewed. The log file contains the following data
 * points for each marker: the current time/date, the time/date the current JAR file was compiled,
 * and the current match type and number.
 *
 * @author FRC 1778 Chill Out
 */
public class DebugLog {

  private static SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
  private static StringWriter sw = new StringWriter();
  private static File logFile = new File("/home/lvuser/crashLog.csv");
  private static Date compileDate = getClassBuildTime();

  public static void setFile(File file) {
    logFile = file;
  }

  public static void logRobotStartup() {
    logMarker("Robot started");
  }

  public static void logRobotConstruction() {
    logMarker("Robot constructed");
  }

  public static void logRobotInit() {
    logMarker("robotInit() called");
  }

  public static void logDisabledInit() {
    logMarker("disabledInit() called");
  }

  public static void logAutoInit() {
    logMarker("autonomousInit() called");
  }

  public static void logTeleopInit() {
    logMarker("teleopInit() called");
  }

  public static void logTestInit() {
    logMarker("testInit() called");
  }

  public static void logThrowableCrash(Throwable throwable) {
    logMarker("Exception", throwable);
  }

  public static void logNote(String note) {
    logMarker("\"" + note + "\"");
  }

  private static void logMarker(String mark) {
    logMarker(mark, null);
  }

  private static void logMarker(String mark, Throwable nullableException) {
    try (PrintWriter writer =
        new PrintWriter(new FileWriter(logFile, Charset.defaultCharset(), true))) {
      if (!logFile.exists()) {
        writer.print("date,compileDate,match,info,exception");
        writer.println();
      }

      writer.print(dateFormatter.format(new Date()) + ",");
      writer.print(dateFormatter.format(compileDate) + ",");
      writer.print(
          DriverStation.getInstance().getMatchType()
              + ": #"
              + DriverStation.getInstance().getMatchNumber()
              + ",");
      writer.print(mark);

      if (nullableException != null) {
        sw.getBuffer().setLength(0);
        nullableException.printStackTrace(new PrintWriter(sw));
        String exception = sw.toString();
        exception = exception.substring(0, exception.length() - 1);
        writer.print(",\"" + exception + "\"");
      }

      writer.println();
    } catch (IOException e) {
      DriverStation.reportWarning("DebugLog failed to log marker", e.getStackTrace());
    }
  }

  private static Date getClassBuildTime() {
    Date d = null;
    Class<?> currentClass = new Object() {}.getClass().getEnclosingClass();
    URL resource = currentClass.getResource(currentClass.getSimpleName() + ".class");
    if (resource != null) {
      if (resource.getProtocol().equals("file")) {
        try {
          d = new Date(new File(resource.toURI()).lastModified());
        } catch (URISyntaxException ignored) {
          System.out.println("Last modified date failed to be parsed as URI");
        }
      } else if (resource.getProtocol().equals("jar")) {
        String path = resource.getPath();
        d = new Date(new File(path.substring(5, path.indexOf("!"))).lastModified());
      } else if (resource.getProtocol().equals("zip")) {
        String path = resource.getPath();
        File jarFileOnDisk = new File(path.substring(0, path.indexOf("!")));
        try (JarFile jf = new JarFile(jarFileOnDisk)) {
          ZipEntry ze = jf.getEntry(path.substring(path.indexOf("!") + 2));
          long zeTimeLong = ze.getTime();
          Date zeTimeDate = new Date(zeTimeLong);
          d = zeTimeDate;
        } catch (IOException | RuntimeException ignored) {
          System.out.println("Failed to get JAR as Zip");
        }
      }
    }
    return d;
  }
}
