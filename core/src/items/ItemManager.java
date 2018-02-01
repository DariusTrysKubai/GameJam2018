package items;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import Player.ActiveItemFrame;
import Player.Player;

public class ItemManager {

	boolean debug = false;

	ActiveItemFrame frame;

	// ACTIVE ITEM
	Item active_item;

	// all items:
	Item cutters;
	Glue sunflower;

	Item[] items_array;

	public static final int item_count = 6;
	public static final float collision_size = 60;

	Player player;
	SpriteBatch sb;
	OrthographicCamera cam;
	OrthographicCamera hudcam;
	ShapeRenderer shape;

	int active_item_x, active_item_y, active_item_frame_x, active_item_frame_y;

	public ItemManager() {
		
	}

	public void init(SpriteBatch sb, OrthographicCamera cam, OrthographicCamera hudcam, ShapeRenderer shape,
			Player player) {
		this.sb = sb;
		this.cam = cam;
		this.shape = shape;
		this.player = player;
		this.hudcam = hudcam;
	}

	public void create() {

		frame = new ActiveItemFrame();
		frame.init(sb, hudcam);
		frame.create();

		items_array = new Item[item_count];

		items_array[0] = new Hammer();
		items_array[1] = new Glue();
		items_array[2] = new Radio();
		items_array[3] = new Ducktape();
		items_array[4] = new walkietalkie();
		items_array[5] = new Sunflower();

		for (int i = 0; i < item_count; i++) {
			items_array[i].init(sb, cam, hudcam, shape, player, debug);
			items_array[i].create();
			items_array[i].set_active_position(this);
			items_array[i].set_index(i);
		}
		items_array[0].setPosition(2945, 1900+300);
		items_array[1].setPosition(2573, 1940-10);
		items_array[2].setPosition(2200, 1800);
		items_array[3].setPosition(850, 1000+800+150);
		items_array[4].setPosition(900, 900+280);
		items_array[5].setPosition(1500, 1600+600);
	}

	public void update() {

		for (Item item : items_array) {
			item.update(this);
		}

	}

	public void render() {

		shape.setProjectionMatrix(cam.combined);
		cam.update();

		// Render all items
		for (Item item : items_array) {
			item.render(sb);
		}

		frame.render(sb);

		if (active_item != null) {
			active_item.render(sb);
		}
		
		if(debug) {
			shape.begin(ShapeType.Line);
			shape.end();
		}

		// Active item render
		// sb.setProjectionMatrix(hudcam.combined);
		// hudcam.update();

		/*
		 * for(int i = 0 ; i < 15 ; i++) { cutters_array[i].render(sb); }
		 */
	}

	public void dispose() {

	}

	public void set_active_item(Item item) {
		active_item = item;
		active_item.pickItem();
	}

	public ItemManager get_manager() {
		return this;
	}

	public boolean check_click(float target_x, float target_y) {

		for (Item item : items_array) {
			if (item.check_if_clicked(target_x, target_y)) {
				// System.out.println(item.name + " index: " + item.index);
				return true;
			}
		}
		return false;
	}

	public Item get_click_item(float target_x, float target_y) {
		for (Item item : items_array) {
			if (item.check_if_clicked(target_x, target_y)) {
				// System.out.println(item.name + " index: " + item.index);
				return item;
			}
		}
		return active_item;
	}

	public void find_new_active(float target_x, float target_y) {
		for (Item item : items_array) {
			if (item.check_if_clicked(target_x, target_y)) {
				set_active_item(item);
			}
		}
	}

	public Item get_active_item() {
		return active_item;
	}

	public void swap(Item active_item, Item picking_item) {
		active_item.x = picking_item.x;
		active_item.y = picking_item.y;
		active_item.reset();

		picking_item.x = picking_item.active_item_x;
		picking_item.y = picking_item.active_item_y;
		picking_item.pickItem();
	}

}
