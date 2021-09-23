package base;

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