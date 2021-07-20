class Color extends I32 {
	U8<0> red;
	U8<8> green;
	U8<16> blue;
	U8<24> alpha;
	this(this.red, this.green = red, this.blue = red, this.alpha = 255);
	this(String code) {
		Int size = code.size;
		if(size < 6) {
			red = code[0].fromHex() * 17;
			green = code[1].fromHex() * 17;
			blue = code[2].fromHex() * 17;
			alpha = size >= 4 ? code[3].fromHex() * 17 : 255;
		} else {
			red = code[0..2].fromHex();
			green = code[2..4].fromHex();
			blue = code[4..6].fromHex();
			alpha = size >= 8 ? code[6..8].fromHex() : 255;
		}
	}
}