package base;

import static base.Base.workingPath;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import parser.Rules;
import ast.ClassEntity;

public class Main {
  public static void main(String[] args) throws IOException {
    JFileChooser chooser = new JFileChooser(workingPath + "examples");
    chooser.setFileFilter(new FileFilter() {
      @Override
      public boolean accept(File file) {
        return file.isDirectory() || file.getName().endsWith(".es");
      }
      @Override
      public String getDescription() {
        return "Extensible Engine source";
      }
    });
    int ret = chooser.showDialog(null, "Select source file for compilation");
    if(ret == JFileChooser.APPROVE_OPTION) {
      Rules rules = new Rules().load("standard.epc");
      String fileName = chooser.getSelectedFile().getPath();
      String path = chooser.getSelectedFile().getParent() + "/";
      String pack = "package examples." +  chooser.getSelectedFile()
          .getParentFile().getName() + ";\n\n";
      Module module = Module.read(fileName, rules);
    }
  }
}
