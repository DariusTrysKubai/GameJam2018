package items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import Player.Player;

public class Item {

	protected boolean debug = false;
	protected String name;
	protected Texture texture;
	protected float x, y;
	protected float x_old, y_old;
	protected float width, height;
	protected int index;

	public Vector2 collision_pos;

	public static final float collision_size = 120;
	public static final int distance_limit = 160;

	float active_item_x;
	float active_item_y;

	protected float distance_to_player = 0;
	protected Vector2 distance_to_player_vector;
	protected float distance_x, distance_y;

	protected boolean is_taken;
	protected boolean is_destroyed;
	protected boolean is_pickable;

	protected SpriteBatch sb;
	protected OrthographicCamera cam;
	protected ShapeRenderer shape;
	protected OrthographicCamera hudcam;
	Player player;

	public Item() {
		collision_pos = new Vector2();
	}

	public void create() {
		distance_x = 0;
		distance_y = 0;
		x_old = 0;
		y_old = 0;
		is_taken = false;
		is_destroyed = false;
		is_pickable = false;
		System.out.println("Creating vector");

	}

	public void init(SpriteBatch sb, OrthographicCamera cam, OrthographicCamera hudcam, ShapeRenderer shape,
			Player player, boolean debug) {
		this.sb = sb;
		this.cam = cam;
		this.shape = shape;
		this.player = player;
		this.hudcam = hudcam;
		this.debug = debug;
	}

	public void set_active_position(ItemManager manager) {
		active_item_x = manager.frame.x - 10;
		active_item_y = manager.frame.y - 10;
	}

	public void update(ItemManager manager) {

		// calculating distance between the item and player. Object middle, player,
		// bottom middle.
		// distance to player
		distance_x = (x + (width / 2)) - (player.x + (player.texture_size / 2));
		distance_y = (y + (height / 2) - player.y);
		distance_to_player = (float) Math.sqrt(Math.pow(distance_x, 2) + Math.pow(distance_y, 2));

		// collision box
		collision_pos.x = (x + (width / 2)) - (collision_size / 2);
		collision_pos.y = (y + (height / 2)) - (collision_size / 2);

		/*
		 * if (distance_to_player < distance_limit) { manager.set_active_item(this); x =
		 * active_item_x; y = active_item_y; }
		 */
	}

	public void render(SpriteBatch sb) {

		sb.begin();
		if (!is_taken) {
			sb.setProjectionMatrix(cam.combined);
			sb.draw(texture, x, y);
		} else {
			sb.setProjectionMatrix(hudcam.combined);
			sb.draw(texture, x, y);
		}
		sb.end();

		if (debug && !is_taken) {
			shape.begin(ShapeType.Line);
			if (distance_to_player > distance_limit) {
				shape.setColor(Color.RED);
			} else {
				shape.setColor(Color.GREEN);
			}
			shape.line(player.x + player.texture_size / 2, player.y, x + width / 2, y + height / 2);

			// Texture size
			shape.rect(x, y, width, height);
			// Collision size
			shape.setColor(Color.BLUE);
			shape.rect(collision_pos.x, collision_pos.y, collision_size, collision_size);

			shape.end();
		}

	}

	public void dispose() {

	}

	public void loadTexture(String path) {
		texture = new Texture(Gdx.files.internal(path));
	}

	public void pickItem() {
		is_taken = true;
		x_old = x;
		y_old = y;
		x = active_item_x;
		y = active_item_y;
	}

	public boolean check_if_clicked(float target_x, float target_y) {
		if (!is_taken) {
			if ((collision_pos.x < target_x) && (target_x < (collision_pos.x + collision_size))) {
				if ((collision_pos.y < target_y) && (target_y < (collision_pos.y + collision_size))) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;

	}

	public void set_distance_vector() {

	}

	public void setPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void destroy() {
		is_destroyed = true;
	}

	public void reset() {
		is_taken = false;
	}

	public void set_index(int i) {
		this.index = i;
	}

	public int get_index() {
		return index;
	}

	public float get_distance() {
		return distance_to_player;
	}

	public boolean is_taken() {
		return is_taken;
	}
}
