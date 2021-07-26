section boolean {
  rename Bool.yes, true;
  rename Bool.no, false;
}

section types {
  rename I8, byte;
  rename I16, short;
  rename I32, int;
  rename I64, long;
  rename F32, float;
  rename F64, double;
  rename Bool, boolean;
  rename Char, char;
}

section length {
  alias String.size, length;
  alias Array.size, length;
}