class Color extends I32 {
	U8<0> red;
	U8<8> green;
	U8<16> blue;
	U8<24> alpha;
	
	create(this.red = 0, this.green = red, this.blue = red, this.alpha = 255);
	
	create(String code) {
		size = code.size;
		if(size < 6) {
			red = fromHex(code[0]) * 17;
			green = fromHex(code[1]) * 17;
			blue = fromHex(code[2]) * 17;
			alpha = if(size >= 4 then fromHex(code[3]) * 17 else 255);
		} else {
			red = fromHex(code[0 ..< 2]);
			green = fromHex(code[2 ..< 4]);
			blue = fromHex(code[4 ..< 6]);
			alpha = if(size >= 8 then fromHex(code[6 ..< 8]) else 255);
		}
	}
	
	U8 fromHex(Char char) -> "0123456789ABCDEF".indexOf(char);

	U8 fromHex(String string) {
		assert(string.length <= 2);
		value = 0;
		for(n = 0 ..< size) value = (value << 4) + fromHex(string[n]);
		return value;
	}
}