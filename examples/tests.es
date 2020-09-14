// if clause
if(0 == 0) print("0 == 0")
if(0 == 1) print("0 == 1") else print("0 != 1")
if(0 == 1) {
  print("0 == 1") 
} else if(0 == 2) {
  print("0 == 2")
} else print("0 != 2")


// for loop
list = ["zero", "one", "two"]
for(index = 0 ..< list.size) print("\(list[index]) = \(index)")
for(value in list) print(value)
for(value at index in list) print("\(value) = \(index)")
for(index indexin list) print(index)
for(index = 0; index < list.size; index++) print("\(list[index]) = \(index)")


// multiple for loop and break
for(a = 0 .. 9; b = 0 .. 9) {
  print("\(a) * \(b) = \(a * b)")
  if(a * b == 35) break
}