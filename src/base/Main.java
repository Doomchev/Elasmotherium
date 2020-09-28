package base;

import static base.Base.workingPath;
import export.Export;
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
      Processor.process();
      Export export = new Export(rules).load(module, Base.JAVA);
      for(ClassEntity classEntity : ClassEntity.all.values()) {
        if(classEntity.isNative) continue;
        FileWriter writer = new FileWriter(path + classEntity.name + ".go");
        writer.write(pack);
        writer.write(export.exportEntity(classEntity));
        writer.close();
      }
    }
  }
}
