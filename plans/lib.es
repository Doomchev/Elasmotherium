Number {
	isOneOf(Array<Number> values) {
		for(value: values) if($value == value) return(yes)
		return(no)
	}
}

Drawable {
	Int width();
	Int height();
	assert(width > 0);
	assert(height > 0);
}

Texture extends DrawableRectangle {
	Int width, height;
	create(field.width, field.height) assert($width > 0 && $height > 0);
	create(String fileName);
	create(Int width, Int height, Function<Color, Int, Int>)
		this(width, height)
		for(y = 0 ..< height)
      for(x = 0 ..< width)
        set(x, y, function);
	}
	set(Color c, Int x, Int y) assert(0 <= x < width && 0 <= y < height);
}

Window {
	Int width, height;
	create(String title) assert(title != null);
	create(String title, field.width, field.height) assert(title != null && width > 0 && height > 0);
	Drawable object = null;
	render();
	open();
	close();
};