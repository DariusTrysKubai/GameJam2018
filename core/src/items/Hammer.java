package items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hammer extends Item {

	public void create() {
		loadTexture("items/hammer128x128.png");
		name = "Hammer";
		width = texture.getWidth();
		height = texture.getHeight();
		is_taken = false;
		x = 2990;
		y = 1500;
	}

}
