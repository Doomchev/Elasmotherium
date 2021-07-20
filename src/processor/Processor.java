package processor;

import ast.Function;
import ast.ID;
import ast.Variable;
import static base.Base.currentFileName;
import static base.Base.error;
import static base.Base.lineNum;
import base.ElException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import parser.ParserBase;

public class Processor extends ParserBase {
  private BufferedReader reader;
  private final int DECLARATIONS = 0, OPERATORS = 1, BLOCKS = 2, FUNCTIONS = 3;
  private int mode = DECLARATIONS;
  
  public Processor load(String fileName) {
    currentFileName = fileName;
    try {
      reader = new BufferedReader(new FileReader(fileName));
      String line;
      lineNum = 0;
      Function function = null;
      while((line = reader.readLine()) != null) {
        lineNum++;
        line = line.trim();
        if(line.isEmpty() || line.startsWith("//")) continue;
        if(line.startsWith("#")) {
          switch(line.substring(1).trim().toLowerCase()) {
            case "operators":
              mode = OPERATORS;
              break;
            case "blocks":
              mode = BLOCKS;
              break;
            case "functions":
              mode = FUNCTIONS;
          }
          continue;
        }
        if(function == null) {
          String functionsText = betweenBrackets(line, '[', ']').trim();
          if(functionsText.isEmpty()) functionsText = startingId(line);
          String[] functions = trimmedSplit(functionsText, ',');
          String[] parameters = trimmedSplit(betweenBrackets(line, '(', ')')
              , ',');
          for(int i = 0; i < functions.length; i++) {
            function = new Function(ID.get(functions[i]));
            for(int j = 0; j < parameters.length; j++) {
              String[] parts = parameters[j].split(" ");
              Variable parameter = new Variable(ID.get(parts[1]));
              //parameter.;
              function.parameters.add(parameter);
            }
          }
        } else if(line.equals("}")) {
          function = null;
        } else {  
          
        }
        
      }
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", fileName + "Cannot read " + fileName + ".");
    /*} catch (ElException ex) {
      error("Error in processor code", currentFileName + " (" + lineNum + ")\n"
      + ex.message);*/
    }
    
    return this;
  }
}
