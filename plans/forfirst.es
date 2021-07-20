List<String> list = ["one", "two", "three"];
String text = "";
for(String string: list) {
	if(!first) text += ", ";
	text += string; 
}
print(text);