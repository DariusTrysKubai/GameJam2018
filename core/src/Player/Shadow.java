package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Shadow {

	Player player;
	Texture shadow;
	int x, y;

	public Shadow() {
		shadow = new Texture(Gdx.files.internal("play/shadow.png"));
	}

	public void create() {

	}

	public void init(Player player) {
		this.player = player;
	}

	public void update() {
		x = (int) (player.x + (player.texture_size / 2) - (shadow.getWidth() / 2));
		y = (int) (player.y - (player.texture_size * 0.025f));
	}

	public void render(SpriteBatch sb) {
		sb.begin();
		sb.draw(shadow, x, y);
		sb.end();
	}

	public void dispose() {

	}

}
