String indent = "";

printFolder(askFolder("Select folder to print its contents:"));

printFolder(Folder folder) {
  println(indent + folder);
  with(indent += "  ") {
    for(innerFolder: folder.folders) printFolder(innerFolder);
    for(file: folder.files) println(indent + file);
  }
}