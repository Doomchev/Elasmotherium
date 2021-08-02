section boolean {
  rename Bool.yes, true;
  rename Bool.no, false;
}

section types {
  rename I8, byte;
  rename I16, short;
  rename U16, char;
  rename I32, int;
  rename I64, long;
  rename F32, float;
  rename F64, double;
  rename Bool, boolean;
  rename Char, char;
}

section length {
  rename String.size, length;
  rename Array.size, length;
}