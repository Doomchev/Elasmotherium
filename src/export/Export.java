package export;

import base.Base;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import parser.Rules;
import parser.structure.ClassEntity;
import parser.structure.Entity;
import parser.structure.ID;

public class Export extends Base {
  public Rules rules;
  
  public final HashMap<String, String> constants = new HashMap<>();
  public final HashMap<ID, Chunk> forms = new HashMap<>();

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
        boolean condition = true;
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
            if(line.charAt(pos) != '(') error("( expected");
          case '(':
            stringStart = pos + 1;
            pos = line.indexOf(')', stringStart);
            String str = line.substring(stringStart, pos);
            if(c == 'c') {
              String[] param = str.split(":");
              Chunk chunk = param[0].isEmpty() ? null
                  : getChunkSequence(constants.get(param[0]));
              seq.appendChunk(new ChunkChildren(chunk, param.length == 1 ? null
                  : ID.get(param[1])));
            } else {
              String txt = constants.get(str);
              if(txt != null) {
                seq.appendChunk(getChunkSequence(txt));
              } else {
                String[] param = str.split(":");
                seq.appendChunk(new ChunkChild(ID.get(param[0])
                    , param.length == 1 ? null : ID.get(param[1])));
              }
            }
            break;
          case '!':
            condition = false;
          case '?':
            stringStart = pos + 1;
            int bracket = line.indexOf('[', stringStart), depth = 0;
            pos = bracket + 1;
            while(true) {
              if(line.charAt(pos) == '[') {
                depth++;
              } else if(line.charAt(pos) == ']') {
                if(depth == 0) break;
                depth--;
              }
              pos++;
              if(pos >= line.length()) error("Brackets error.");
            }
            seq.appendChunk(new ChunkExists(ID.get(line.substring(stringStart
                , bracket)), getChunkSequence(line.substring(bracket + 1, pos))
                , condition));
            break;
          default:
            c = line.charAt(pos);
            if(c < '0' || c > '9')
              error("Invalid escape sequence");
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
    currentFileName = fileName;
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
          forms.put(ID.get(name), chunk);
        }
      }
    } catch (FileNotFoundException ex) {
      error("I/O error", fileName + " not found.");
    } catch (IOException ex) {
      error("I/O error", fileName + "Cannot read " + fileName + ".");
    }
    
    return this;
  }
  
  public String exportEntity(Entity entity) {
    ID id = entity.getFormId();
    Chunk chunk = forms.get(id);
    if(chunk == null) error(id + " is not defined");
    return exportEntity(entity, chunk);
  }
  
  public String exportEntity(Entity entity, ID postfix) {
    ID id = entity.getFormId();
    ID postfixId = postfix == null ? id : ID.get(id.string + "_" + postfix.string);
    Chunk chunk = forms.get(postfixId);
    if(chunk == null) chunk = forms.get(id);
    if(chunk == null) error(id + " is not defined");
    return exportEntity(entity, chunk);
  }

  private String exportEntity(Entity entity, Chunk chunk) {
    if(entity == null) return "";
    Chunk.export = this;
    String str = "";
    while(chunk != null) {
      str += chunk.toString(entity);
      chunk = chunk.nextChunk;
    }
    //System.out.println(str);
    return str;
  }

  public void log() {
    for(ClassEntity classEntity : ClassEntity.all.values()) {
      if(classEntity.isNative) continue;
      System.out.println("\n" + exportEntity(classEntity));
    }
  }
  
  public static void error(String message) {
    error("Exporting code error in " + currentFileName, message + " at line "
        + lineNum);
  }
}
