package items;

public class Ducktape extends Item {
	public void create() {
		loadTexture("items/ducktape128x128.png");
		name = "Duck tape";
		width = texture.getWidth();
		height = texture.getHeight();
		is_taken = false;
		x = 300;
		y = 150;
	}
}
