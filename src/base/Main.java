package base;

import export.Export;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import parser.Rules;
import parser.structure.Node;
import parser.structure.NodeProcessor;

public class Main {
  static final Rules rules = new Rules().load("standard.eep");
  static final Export export = new Export(rules).load("java.eee");
  static final NodeProcessor processor = new NodeProcessor(rules);
  
  public static void main(String[] args) throws IOException {
    processFolder("src/examples", "examples");
  }
  
  static void processFolder(String path, String pack) throws IOException {
    File[] files = new File(path).listFiles();
    for(File file : files) {
      if(file.isDirectory()) {
        processFolder(file.getCanonicalPath(), pack + "." + file.getName());
      } else if(file.getName().endsWith(".ees")) {
        
        Module module = Module.read(file.getPath(), rules);
        processor.processModule(module);
        module.rootNode.log("");
        
        for(Node node : module.rootNode.children) {
          FileWriter writer = new FileWriter(path + "/" + node.caption + ".java");
          writer.write("package " + pack + ";\n");
          writer.write("import java.util.LinkedList;\n");
          /*if(!node.caption.equals("Main")) writer.write("import " + pack
              + ".Main;\n");*/
          writer.write(export.exportNode(node));
          writer.close();
        }
      }
    }
    
  }
}
