class Folder {
  readonly String name;
  
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