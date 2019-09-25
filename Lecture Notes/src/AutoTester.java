import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/** Runs tests from classes in the default package automatically! No more opening the tester profile
 * and changing classes all the time. The target classes must have a class within them containing
 * the word "Examples" in its name. i.e.: "ExamplesGame" in class file "Game.java", but not
 * "ExampleGame" in class file "ExamplesGame.java" or "Game" in class file "ExamplesGame.java". */
public class AutoTester {
  /** Searches for classes in the default package that contain an Examples class in them and runs
   * tests on each. If there are multiple classes in the default package containing Examples
   * classes, tests will be run on each. If none are found, prints an error message. */
  public static void main(String[] args) {
    int tests = 0;
    try {
      for (Object obj : Files.list(Paths.get("src")).toArray()) {
        if (obj.toString().endsWith(".java")
            && !obj.toString().endsWith(AutoTester.class.getSimpleName() + ".java")) {
          String className = getExamplesClass(Files.readAllLines((Path) obj));
          if (!className.isEmpty()) {
            tests++;
            tester.Main.main(new String[] { className });
          }
        }
      }
      if (tests == 0) {
        System.err.println("No Examples classes could be found in the default package.");
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Looks through a {@link List} of {@link String}s to find one that contains the declaration of
   * an Examples class. If there are multiple classes containing the word "Examples", the one
   * closest to the top of the file will be chosen.
   *
   * @param lines the content of a class
   * @return the name of an Examples class or an empty {@link String} if none are found */
  public static String getExamplesClass(List<String> lines) {
    for (String line : lines) {
      if (line.contains("class Examples")) {
        int beginIndex = line.indexOf("class") + 6;
        return line.substring(beginIndex, line.indexOf(" ", beginIndex));
      }
    }
    return "";
  }
}