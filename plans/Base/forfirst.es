List<String> list = ["one", "two", "three"];
String text = "";
for(string: list)
	text += if(!first then ", ") + string;
println(text);  