List<String> list = ["one", "two", "three"];
String text = "";
for(each string in list)
	text += if(!first then ", ") + string;
println(text);  