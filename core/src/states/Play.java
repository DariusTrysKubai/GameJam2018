package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Level.World1;
import Player.Player;
import Player.PlayerCollision;
import handlers.ClickManager;
import handlers.GameStateManager;
import handlers.TextManager;
import items.ItemManager;

public class Play extends GameState {

	ItemManager itemmanager;
	Player player;
	PlayerCollision collision;
	World1 world;
	ClickManager clickmanager;
	TextManager text;

	float elapsed;

	public Play(GameStateManager gsm) {
		super(gsm);

		clickmanager = new ClickManager();
		collision = new PlayerCollision();

		clickmanager.create();

		player = new Player();
		player.init(sb, shape, cam);
		player.create();

		world = new World1();
		world.init(cam, shape, collision, sb);
		world.create();

		itemmanager = new ItemManager();
		itemmanager.init(sb, cam, hudcam, shape, player);
		itemmanager.create();

		collision.init(player, cam, shape, world);
		collision.create();

		clickmanager.init(player, itemmanager, cam, hudcam, shape);

		collision.set_size(world.tile_size / 2, world.tile_size / 2);

		text = new TextManager();
		text.init(sb, hudcam);
		text.create();
		elapsed = 0;
	}

	public void update(float dt) {

		elapsed += dt;

		cam.position.x = player.x + (player.texture_size / 2);
		cam.position.y = player.y + (player.texture_size / 2);
		cam.update();

		clickmanager.update();
		world.update();
		player.update(dt);
		itemmanager.update();
		text.update();

		collision.update();
		// (TextureRegion) idle.getKeyFrame(stateTime, true);

		if (!text.visible) {
			if ((elapsed > 1) && (elapsed < 2)) {
				text.write("Hello this is captain Martin, our ship\nis damaged.", 3);
			}
		}
		
		if (!text.visible) {
			if ((elapsed > 5) && (elapsed < 6)) {
				text.write("Find All Purpose mender\nof Everything!", 3);
			}
		}

	}

	public void render() {
		Gdx.gl.glClearColor(1 / 255f, 75 / 255f, 62 / 255f, 1);
		Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

		world.render(sb);
		itemmanager.render();
		player.render();
		clickmanager.render();
		collision.render();
		text.render();
	}

	public void dispose() {
		world.render(sb);
		player.dispose();
		itemmanager.dispose();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

}
