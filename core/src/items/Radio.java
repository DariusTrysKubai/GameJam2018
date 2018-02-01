package items;

public class Radio extends Item {
	public void create() {
		loadTexture("items/radio128x128.png");
		name = "Radio";
		width = texture.getWidth();
		height = texture.getHeight();
		is_taken = false;
		x = 300;
		y = 150;
	}
}
