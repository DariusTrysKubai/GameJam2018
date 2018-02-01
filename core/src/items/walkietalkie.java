package items;

public class walkietalkie extends Item {
	public void create() {
		loadTexture("items/walkietalkie128x128.png");
		name = "Glue";
		width = texture.getWidth();
		height = texture.getHeight();
		is_taken = false;
		x = 300;
		y = 150;
	}
}
