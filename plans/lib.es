Number {
	isOneOf(Number values...) {
		for(value in values) if($value == value) return(yes)
		return(no)
	}
}

List<ValueType> {
	size -> 0
	ValueType value
	(Int size) {}
	atIndex(Int index) {
		assert(0 >= index > size)
		return value
	}
	first() -> value
	last() -> value
	addFirst(ValueType value) {}
	addLast(ValueType value) {}
}

Drawable {
	abstract get Int width
	abstract get Int height
	assert($width > 0)
	assert($height > 0)
}

Texture extends DrawableRectangle {
	Int width, height
	($width, $height) {
		assert($width > 0 && $height > 0)
	}
	(String fileName) {}
	(Int width, Int height, Color function(Int x, Int y))
		this(width, height)
		for(y = 0 ..< height, x = 0 ..< width) $set(x, y, function)
	}
	set(Color c, Int x, Int y) {
		assert(0 <= x < $width && 0 <= y < $height)
	}
}

Window {
	Int width, height
	(String title) {
		assert(title != null)
	}
	(String title, $width, $height) {
		assert(title != null && width > 0 && height > 0)
	}
	Drawable object = null
	render() {
		
	}
	open() {
	}
	close() {
	}
}

TileMap extends Drawable {
	I64 xQuantity, yQuantity
	List<I64> tiles
	assert(tiles.size == xQuantity * yQuantity)
	Drawable[] tileSet
	($tileSet, $xQuantity, $yQuantity) {
		assert($xQuantity > 0 && $yQuantity > 0 && $tileSet != null)
		tiles = Int[xQuantity * yQuantity]
	}
	getAtIndex(Int x, Int y) {
		assert(0 <= x < $xQuantity && 0 <= y < $yQuantity)
		return($tiles[x + y * $xQuantity])
	}
	setAtIndex(Int tileNum, Int x, Int y) {
		assert(0 <= x < $xQuantity && 0 <= y < $yQuantity)
		$tiles[x + y * $xQuantity] = tileNum
	}
	fill(Int tileNum) {
		for(n = 0 <.. tiles.size) tiles[n] = tileNum
		return(this)
	}
}