package Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import Level.World1;
import handlers.AABB;

public class PlayerCollision {

	boolean debug = false;

	public Vector2 pos;
	public Vector2 size;

	int player_tile_x;
	int player_tile_y;

	public static final float safe_distance = 1.5f;

	Player player;
	OrthographicCamera cam;
	ShapeRenderer shape;
	World1 world;

	AABB aabb_player;
	AABB aabb_target;

	public PlayerCollision() {
		pos = new Vector2(0, 0);
		size = new Vector2(0, 0);
	}

	public void create() {

	}

	public void init(Player player, OrthographicCamera cam, ShapeRenderer shape, World1 world) {
		this.player = player;
		this.cam = cam;
		this.shape = shape;
		this.world = world;

		// size.set(player.texture_size / 2, player.texture_size / 2);
	}

	public void set_size(float x, float y) {
		size.set(x, y);
	}

	public void update() {
		// set collision pos
		pos.x = player.x + (player.texture_size / 2) - (size.y / 2);
		pos.y = player.y;

		player_tile_x = (int) Math.floor(pos.x / world.tile_size);
		player_tile_y = (int) Math.floor(pos.y / world.tile_size);

		// check arround blocks
		int i_start = player_tile_x - 1;
		int j_start = player_tile_y - 1;
		int i_end = player_tile_x + 2;
		int j_end = player_tile_y + 2;

		if (i_start < 0) {
			i_start = 0;
		}
		if (j_start < 0) {
			j_start = 0;
		}
		if (i_end > world.collumn) {
			i_end = world.collumn;
		}
		if (j_end > world.row) {
			j_end = world.row;
		}

		aabb_player = new AABB(pos.cpy(), size.cpy());

		for (int i = i_start; i < i_end; i++) {
			for (int j = j_start; j < j_end; j++) {
				aabb_target = new AABB(new Vector2(i * world.tile_size, j * world.tile_size),
						new Vector2(world.tile_size, world.tile_size));
				if (world.collision_array[i][j] == true) {
					if (AABB.collides(aabb_player, aabb_target)) {

						player.is_walking = false;
						// Collision detected
						System.out.println("Collides");
						// find side
						Vector2 temp = new Vector2(
								(((i * world.tile_size) + world.tile_size / 2f) - (pos.x + (size.x / 2f))),
								(((j * world.tile_size) + world.tile_size / 2f) - (pos.y + (size.y / 2f))));

						float angle = temp.angle();
						int side = 0;
						if (angle <= 45) {
							side = 1;
						} else if (angle <= 135) {
							side = 0;
						} else if (angle <= 225) {
							side = 3;
						} else if (angle <= 315) {
							side = 2;
						} else if (angle <= 360) {
							side = 1;
						}
						
						System.out.println("Angle: " + angle + " side: " + side);

						switch (side) {
						case 0:
							player.y -= (pos.y + size.y) - (j * world.tile_size) + safe_distance;
							break;
						case 1: player.x -= (pos.x + size.x) - (i * world.tile_size) + safe_distance;
							break;
						case 2:
							player.y += ((j * world.tile_size) + world.tile_size) - pos.y + safe_distance;
							break;
						case 3: player.x += ((i * world.tile_size) + world.tile_size) - pos.x + safe_distance;
							break;
						default:
							break;
						}
					}

				}
			}
		}

		// aabb_target = new AABB(new Vector2(x, y), new Vector2(x, y));
	}

	public void render() {
		if (debug) {
			shape.begin(ShapeType.Line);
			shape.setColor(Color.BLUE);
			shape.rect(pos.x, pos.y, size.x, size.y);

			// draw arround blocks
			int i_start = player_tile_x - 1;
			int j_start = player_tile_y - 1;
			int i_end = player_tile_x + 2;
			int j_end = player_tile_y + 2;

			if (i_start < 0) {
				i_start = 0;
			}
			if (j_start < 0) {
				j_start = 0;
			}
			if (i_end > world.collumn) {
				i_end = world.collumn;
			}
			if (j_end > world.row) {
				j_end = world.row;
			}

			for (int i = i_start; i < i_end; i++) {
				for (int j = j_start; j < j_end; j++) {
					shape.setColor(Color.BLUE);
					shape.rect(i * world.tile_size, j * world.tile_size, world.tile_size, world.tile_size);
				}
			}
			shape.end();
		}

	}

	public void dispose() {

	}

}
