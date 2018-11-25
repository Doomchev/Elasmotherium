package parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Export extends Base {
  public Rules rules;
  
  public final HashMap<String, String> constants = new HashMap<>();
  public final HashMap<Category, Chunk> forms = new HashMap<>();
  public Chunk defaultForm;

  public Export(Rules rules) {
    this.rules = rules;
  }

  private static class ChunkSequence {
    Chunk firstChunk, currentChunk = null;
    
    void appendChunk(Chunk chunk) {
      if(currentChunk == null) {
        firstChunk = chunk;
      } else {
        if(currentChunk.appendChunk(chunk)) return;
        currentChunk.nextChunk = chunk;
      }
      currentChunk = chunk;
    }
  }
  
  private Chunk getChunkSequence(String line) {
    int pos = 0, stringStart = 0;
    ChunkSequence seq = new ChunkSequence();
    while(pos < line.length()) {
      char c = line.charAt(pos);
      if(c == '\\') {
        if(stringStart < pos) seq.appendChunk(new ChunkString(
            line.substring(stringStart, pos)));
        pos++;
        c = line.charAt(pos);
        switch(c) {
          case 'n':
            seq.appendChunk(new ChunkString("\n"));
            break;
          case 't':
            seq.appendChunk(new ChunkString("\t"));
            break;
          case '+':
            seq.appendChunk(new ChunkAddTab());
            break;
          case '-':
            seq.appendChunk(new ChunkRemoveTab());
            break;
          case 'x':
            seq.appendChunk(new ChunkText());
            break;
          case 'i':
            seq.appendChunk(new ChunkTabs());
            break;
          case 'c':
            pos++;
            if(line.charAt(pos) != '(') parsingError("( expected");
          case '(':
            pos++;
            stringStart = pos;
            while(pos < line.length() - 1) {
              if(line.charAt(pos) == ')') break;
              pos++;
            }
            
            String str = line.substring(stringStart, pos);
            if(c == 'c') {
              String[] parts = str.split(",");
              seq.appendChunk(new ChunkChildren(parts[0].equals("*") ? null 
                  : rules.categories.get(parts[0]), parts.length < 2 ? null
                  : getChunkSequence(constants.get(parts[1]))));
            } else {
              String txt = constants.get(str);
              if(txt != null) {
                seq.appendChunk(getChunkSequence(txt));
              } else {
                Category childSymbol = rules.categories.get(str);
                if(childSymbol == null) exportingCodeError("Category or constant \""
                    + str + "\" is not found");
                seq.appendChunk(new ChunkChild(childSymbol));
              }
            }
            break;
          default:
            c = line.charAt(pos);
            if(c < '0' || c > '9')
              exportingCodeError("Invalid escape sequence");
            seq.appendChunk(new ChunkChildAtIndex(Integer.parseInt("" + c)));
        }
        stringStart = pos + 1;
      }
      pos++;
    }
    if(stringStart < pos) seq.appendChunk(new ChunkString(
        line.substring(stringStart, pos)));
    return seq.firstChunk;
  }
  
  public Export load(String fileName) {
    try {
      BufferedReader reader = new BufferedReader(new FileReader(fileName));
      String line;
      lineNum = 0;
      while((line = reader.readLine()) != null) {
        lineNum++;
        if(line.trim().isEmpty() || line.startsWith("//")) continue;
        int colonPos = line.indexOf(':');
        int equalPos = line.indexOf('=');
        if(equalPos >= 0 && (equalPos < colonPos || colonPos < 0)) {
          constants.put(line.substring(0, equalPos), line.substring(equalPos + 1));
        } else {
          Chunk chunk = getChunkSequence(line.substring(colonPos + 1));
          String name = line.substring(0, colonPos).trim();
          if(name.equals("*")) {
            defaultForm = chunk;
          } else {
            forms.put(rules.getCategory(name), chunk);
          }
        }
      }
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", fileName + "Cannot read " + fileName + ".");
    }
    
    return this;
  }
  
  public String exportNode(Node node) {
    Chunk.export = this;
    Chunk chunk = forms.get(node.type);
    if(chunk == null) chunk = defaultForm;
    String str = "";
    while(chunk != null) {
      str += chunk.toString(node);
      chunk = chunk.nextChunk;
    }
    return str;
  }
}
