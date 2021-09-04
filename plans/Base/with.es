String indent = "";

printFolder(askFolder("Select folder to print its contents:"));

printFolder(Folder folder) {
  println(indent + folder);
  with(indent += "  ") {
    for(each innerFolder in folder.folders) printFolder(innerFolder);
    for(each file in folder.files) println(indent + file);
  }
}