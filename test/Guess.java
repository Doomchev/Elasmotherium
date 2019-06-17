
import export.Export;
import base.Module;
import base.Processor;
import parser.Rules;
import parser.structure.ClassEntity;

public class Guess {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.epc");
    Module module = Module.read("examples/guess.es", rules);
    Processor.process(module);
    
    Export export = new Export(rules).load("java.eec");
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      System.out.println("\n" + export.exportEntity(classEntity));
    }
  }
}
