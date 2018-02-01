package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import items.ItemManager;

public class ActiveItemFrame {

	public Texture texture;
	public static final float x = 30;
	public static final float y = 30;
	float width, height;

	SpriteBatch sb;
	OrthographicCamera hudcam;
	Player player;

	public ActiveItemFrame() {

	}

	public void create() {
		texture = new Texture(Gdx.files.internal("play/active_item_button.png"));
	}

	public void init(SpriteBatch sb, OrthographicCamera hudcam) {
		this.sb = sb;
		this.hudcam = hudcam;
	}

	public void update() {

	}

	public void render(SpriteBatch sb) {

		sb.setProjectionMatrix(hudcam.combined);
		hudcam.update();
		
		sb.begin();
		sb.draw(texture, x, y);
		sb.end();

	}

	public void dispose() {

	}

}
