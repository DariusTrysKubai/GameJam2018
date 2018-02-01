package Level;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;

import Player.PlayerCollision;
import handlers.TextureMapObjectRenderer;

public class World1 {

	boolean debug = false;

	TiledMap map;

	/*
	 * TiledMapTileLayer layer_stars; //0 TiledMapTileLayer layer_floor; //1
	 * TiledMapTileLayer layer_pipe; //2 TiledMapTileLayer layer_wall; //3
	 * TiledMapTileLayer layer_items_b; //4 TiledMapTileLayer layer_items_s; //5
	 */

	TiledMapTileLayer layer_grass; // 0
	TiledMapTileLayer layer_objects; // 1
	TiledMapTileLayer layer_collision; // 2
	MapLayer layer_items; // 3
	MapLayer layer_test; // 4

	MapObject object;

	TiledMapTileMapObject objects;

	public int collumn, row;
	public float tile_size;
	int player_tile_x, player_tile_y;

	public boolean[][] collision_array;

	Cell cell;

	private TextureMapObjectRenderer renderer;
	OrthographicCamera cam;
	ShapeRenderer shape;
	PlayerCollision collision;
	SpriteBatch sb;

	public World1() {
		// map = new TmxMapLoader().load("maps/map_v2.tmx");
		map = new TmxMapLoader().load("maps/mapv3.tmx");
		renderer = new TextureMapObjectRenderer(map);
		/*
		 * layer_stars = (TiledMapTileLayer) map.getLayers().get(0); layer_floor =
		 * (TiledMapTileLayer) map.getLayers().get(1); layer_pipe = (TiledMapTileLayer)
		 * map.getLayers().get(2); layer_wall = (TiledMapTileLayer)
		 * map.getLayers().get(3); layer_items_b = (TiledMapTileLayer)
		 * map.getLayers().get(4); layer_items_s = (TiledMapTileLayer)
		 * map.getLayers().get(5);
		 */

		layer_grass = (TiledMapTileLayer) map.getLayers().get(0);
		layer_objects = (TiledMapTileLayer) map.getLayers().get(1);
		layer_collision = (TiledMapTileLayer) map.getLayers().get(2);
		layer_items = map.getLayers().get(3);
		layer_test = map.getLayers().get(4);

		// object = (TextureMapObject)map.getLayers().get(3).getObjects().get("cutter");
		// TextureMapObject textureObject = object;
		// System.out.println(textureObject.getX());
		// layer_items = map.getLayers().get(3);
		// layer_objects2 = map.getLayers().get(4);

		for (MapObject o : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
			System.out.println("Name: "+o.getName());
			 Rectangle rect = ((RectangleMapObject) o).getRectangle();
			 System.out.println("Item x: " + rect.x + " y: " + rect.y);
		}

		// layer_ground.get

		// layer_items = map.getLayers().get(3).getObjects().get("cutter");
		// System.out.println("Test: " + layer_items.);
		// objects = layer_objects2.getObjects();
		// objects.
		// objects.get("cutter").;

		// System.out.println("Layer name: "+layer_objects2.getName());

		collumn = layer_grass.getWidth();
		row = layer_grass.getHeight();

		tile_size = layer_grass.getTileWidth();

		collision_array = new boolean[collumn][row];

		System.out.println("Starting generating collision array");
		for (int i = 0; i < collumn; i++) {
			for (int j = 0; j < row; j++) {
				cell = layer_collision.getCell(i, j);
				if (cell != null) {
					collision_array[i][j] = true;
				} else {
					collision_array[i][j] = false;
				}
				/*
				 * cell = layer_pipe.getCell(i, j); if (cell != null) { collision_array[i][j] =
				 * true; }
				 */
			}
		}

		System.out.println("Collision array done!");

		/*
		 * cell = layer_objects.getCell(0, 0); if(cell == null) {
		 * System.out.println("Cell is empty"); }else {
		 * System.out.println("Cell is not empty"); }
		 */

	}

	public void init(OrthographicCamera cam, ShapeRenderer shape, PlayerCollision collision, SpriteBatch sb) {
		this.cam = cam;
		this.shape = shape;
		this.collision = collision;
		this.sb = sb;
		renderer.setView(cam);
		cam.update();
	}

	public void create() {

	}

	public void update() {
		/*
		 * player_tile_x = (int) Math.floor(collision.pos.x / tile_size); player_tile_y
		 * = (int) Math.floor(collision.pos.y / tile_size);
		 * 
		 * // check arround blocks int i_start = player_tile_x - 2; int j_start =
		 * player_tile_y - 2; int i_end = player_tile_x + 2; int j_end = player_tile_y +
		 * 2;
		 * 
		 * if (i_start < 0) { i_start = 0; } if (j_start < 0) { j_start = 0; } if (i_end
		 * > collumn) { i_end = collumn; } if (j_end > row) { j_end = row; }
		 * 
		 * for (int i = i_start; i < i_end; i++) { for (int j = j_start; j < j_end; j++)
		 * { AABB aabb_target = new AABB(new Vector2(i * tile_size, j * tile_size), new
		 * Vector2(tile_size, tile_size)); } }
		 */
	}

	public void render(SpriteBatch sb) {
		renderer.setView(cam);
		cam.update();
		renderer.render();
		//
		if (debug) {
			shape.begin(ShapeType.Line);
			shape.setColor(Color.GOLD);
			for (int i = 0; i < collumn; i++) {
				for (int j = 0; j < row; j++) {
					if (collision_array[i][j] == true) {
						shape.rect(i * tile_size, j * tile_size, tile_size, tile_size);
					}
				}
			}
			shape.setColor(Color.RED);
			shape.rect(player_tile_x * tile_size, player_tile_y * tile_size, tile_size, tile_size);
		}

		shape.end();

	}

	public void dispose() {
		map.dispose();
	}

}
