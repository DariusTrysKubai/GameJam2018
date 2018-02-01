package items;

public class Glue extends Item {
	public void create() {
		loadTexture("items/glue128x128.png");
		name = "Glue";
		width = texture.getWidth();
		height = texture.getHeight();
		is_taken = false;
		x = 300;
		y = 150;
	}
}
