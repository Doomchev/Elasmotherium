class File {
  String name();
  
  create(String name);
  from(String name) -> File(name);
  
  Iterator getIterator -> _LineIterator(this);
  
  class _LineIterator extends Iterator {
    create(File file);
  }
}

String.from(File file) -> file.name;
