
import export.Export;
import base.Module;
import parser.Rules;

public class Dialogs {
  public static void main(String[] args) {
    Rules rules = new Rules().load("standard.xep");
    Export export = new Export(rules).load("javascript.xee");
    Module module = Module.read("examples/dialogs.xes", rules);
    module.rootNode.log("");
    System.out.println("\n" + export.exportNode(module.rootNode));
    U s = new U<>(new String[1]);
  }
}


class U<E> {
  E[] a;

  public U(E[] a) {
    this.a = a;
  }
}