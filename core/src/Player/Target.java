package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Target {
	Player player;
	Texture target;
	float x, y;
	boolean is_visible;

	public Target() {
		target = new Texture(Gdx.files.internal("play/target.png"));
		is_visible = false;
	}

	public void create() {

	}

	public void init(Player player) {
		this.player = player;
	}

	public void update() {

	}

	public void render(SpriteBatch sb) {
		/*
		if (player.is_walking) {
			sb.begin();
			sb.draw(target, (int) (x - target.getWidth() / 2), (int) (y - target.getHeight() / 2));
			sb.end();
		}
		*/

	}

	public void dispose() {

	}

	public void set_x(float x) {
		this.x = x;
	}

	public void set_y(float y) {
		this.y = y;
	}

	public void set_position(float x, float y) {
		
		this.x = x;
		//this.x = x + (player.texture_size / 2) - (target.getWidth() / 2);
		this.y = y;
	}

}
