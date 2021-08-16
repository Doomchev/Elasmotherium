import Window;
import Texture;
import Color;

new Window("Chessboard", Texture(128, 128, (Int x, Int y) -> Color((x >> 4) + (y >> 4) & 1 ? 255 : 128)))
	.intScaleMax().open()