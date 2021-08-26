package parser;

public class SymbolMask {
  boolean[] symbols = new boolean[130];
  
  public SymbolMask() {}
  
  public SymbolMask(SymbolMask source) {
    System.arraycopy(source.symbols, 0, symbols, 0, 130);
  }
  
  public SymbolMask(char c) {
    symbols[c] = true;
  }
  
  public SymbolMask(int n) {
    symbols[n] = true;
  }
  
  public SymbolMask set(char c) {
    symbols[c] = true;
    return this;
  }
  
  public SymbolMask set(char from, char to) {
    for(int c = from; c <= to; c++) symbols[c] = true;
    return this;
  }
  
  public SymbolMask or(SymbolMask mask) {
    for(int c = 0; c < 130; c++) symbols[c] |= mask.symbols[c];
    return this;
  }
  
  public SymbolMask flip() {
    for(int c = 0; c < 130; c++) symbols[c] = !symbols[c];
    return this;
  }
}
