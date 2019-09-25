import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class AutoTester {
  public static Set<String> RAN = new HashSet<>();
  public static int TESTS;

  public static void main(String[] args) {
    try {
      Files.list(Paths.get("src")).filter(path -> path.toString().endsWith(".java"))
          .forEach(path -> runTests(path));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    if (TESTS == 0) {
      System.err.println("No Examples classes could be found in the default package.");
    }
  }

  public static void runTests(Path path) {
    try {
      Files.readAllLines(path).forEach(line -> {
        if (line.contains("Example")) {
          line = line.substring(line.indexOf("Example"));
          int endIndex;
          if (line.contains(" ")) {
            endIndex = line.indexOf(' ');
          }
          else if (line.contains("{")) {
            endIndex = line.indexOf('{');
          }
          else {
            endIndex = line.length();
          }
          runTests(line.substring(0, endIndex));
        }
      });
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void runTests(String... argv) {
    if (!RAN.contains(argv[0])) {
      try {
        Class.forName(argv[0]);
        TESTS++;
        tester.Main.main(argv);
        RAN.add(argv[0]);
      }
      catch (ClassNotFoundException e) {
      }
    }
  }
}