package base;

import static base.Debug.error;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import processor.Processor;

public abstract class Base extends Allocation {
  public static String workingPath, modulesPath;
  public static Processor currentProcessor;
  
  static {
    try {
      String wPath = new java.io.File(".").getCanonicalPath();
      String mPath = wPath + "/modules";
      workingPath = wPath;
      modulesPath = mPath;
    } catch (java.io.IOException ex) {
    }
  }
}