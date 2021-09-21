Int size = 10;
Array<Int> array = Array<Int>(size);
for(Int i = 0 ..< size) array[i] = i;
for(Int i = 0 ..= size - 2) {
  Int i2 = randomInt(i + 1, size);
  Int a = array[i];
  array[i] = array[i2];
  array[i2] = a;
}
String text = "";
for(Int value: array) text = text + ", \(value)";
println(text);