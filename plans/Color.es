class Color extends I32 {
	U8<0> red;
	U8<8> green;
	U8<16> blue;
	U8<24> alpha;
	
	this(this.red = 0, this.green = red, this.blue = red, this.alpha = 255);
	
	this(String code) {
		Int size = code.size;
		if(size < 6) {
			red = fromHex(code[0]) * 17;
			green = fromHex(code[1]) * 17;
			blue = fromHex(code[2]) * 17;
			alpha = size >= 4 ? fromHex(code[3]) * 17 : 255;
		} else {
			red = fromHex(code[0..2]);
			green = fromHex(code[2..4]);
			blue = fromHex(code[4..6]);
			alpha = size >= 8 ? fromHex(code[6..8]) : 255;
		}
	}
	
	U8 fromHex(Char char) -> "0123456789ABCDEF".indexOf(char);

	U8 fromHex(String string) {
		assert(string.length <= 2);
		U8 value = 0;
		for(Int n = 0 ..< size) value = (value << 4) + fromHex(string[n]);
		return value;
	}
}