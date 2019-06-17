Color extends Int32 {
	UInt8 r, g, b, a
	($r, $g = $r, $b = $r, $a = 255)
}

Drawable {
	Int get width
	Int get height
	draw(Int x, Int y)
}

native Texture extends Drawable {
	(Int width, Int height, Color function(Int x, Int y))
}

native Window {
	String title
	Drawable object
	($title, $object)
	open()
	Window intScaleMax()
}

Window("Chessboard", Texture(128, 128, (x, y) -> Color((x >> 4) + (y >> 4) & 1 ? 255 : 128)))
	.intScaleMax().open()