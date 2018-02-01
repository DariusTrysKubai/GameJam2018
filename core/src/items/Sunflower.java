package items;

public class Sunflower extends Item {
	public void create() {
		loadTexture("items/sunflower128x128.png");
		name = "sunflower";
		width = texture.getWidth();
		height = texture.getHeight();
		is_taken = false;
		x = 300;
		y = 150;
	}
}
