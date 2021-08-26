class FileSystem {
  class _FoldersIterator extends Iterator; 
  static Iterator getIterator() -> _FoldersIterator();
}

class Folder {
  String name();
  
  create(String name);
  from(String name) -> Folder(name);
  
  class _FilesIterator extends Iterator {
    create(Folder folder);
  }
  class _FoldersIterator extends Iterator {
    create(Folder folder);
  }
  
  Iterator folders -> new _FoldersIterator(this);
  Iterator files -> new _FilesIterator(this);
}

String.from(Folder folder) -> folder.name;

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
