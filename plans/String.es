class String {
	Int count(Char char) {
		Int quantity = 0;
		for(Char stringChar: this) if(stringChar == char) quantity++;
		return quantity;
	}
}