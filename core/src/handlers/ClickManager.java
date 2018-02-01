package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector3;

import Player.Player;
import items.Item;
import items.ItemManager;

public class ClickManager {

	boolean debug = false;

	public static final int distance_limit = 160;

	boolean click;
	boolean clickType_move;
	boolean clickType_item;
	boolean clickType_object;
	boolean clickType_button;

	boolean click_first_time;

	Player player;
	ItemManager itemmanager;
	OrthographicCamera cam;
	OrthographicCamera hudcam;
	ShapeRenderer shape;
	
	Item temp;

	float target_x, target_y;

	public ClickManager() {
		click = false;
		clickType_move = false;
		clickType_item = false;
		clickType_object = false;
		clickType_button = false;
		click_first_time = false;

		target_x = 0;
		target_y = 0;
	}

	public void create() {

	}

	public void init(Player player, ItemManager itemmanager, OrthographicCamera cam, OrthographicCamera hudcam,
			ShapeRenderer shape) {
		this.player = player;
		this.itemmanager = itemmanager;
		this.cam = cam;
		this.hudcam = hudcam;
		this.shape = shape;
	}

	public void update() {

		if (Gdx.input.isTouched() && click_first_time) { // Screen coordinates in the

			System.out.println("click detected");
			target_x = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).x;
			target_y = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)).y;

			player.click_item_reset();
			
			// button click

			// item pick up
			
			if (itemmanager.check_click(target_x, target_y)) {
				
				temp = itemmanager.get_click_item(target_x, target_y);
				if(temp.get_distance() <= distance_limit) {
					if(itemmanager.get_active_item() != null) {
						System.out.println("Swap needed");
						System.out.println("Radau: " + temp.is_taken());
						System.out.println("Active: " + itemmanager.get_active_item().is_taken());
						
						itemmanager.swap(itemmanager.get_active_item(), temp);
						itemmanager.set_active_item(temp);
					}else {
						System.out.println("Empty inv. Picking");
						itemmanager.set_active_item(temp);
						temp.pickItem();
					}
					
				}else {
					player.click_item();
					player.move(target_x, target_y);
				}
				
				/*
				if (player.distance_target_original > distance_limit) {
					System.out.println("Item found but far away");
					player.move(target_x, target_y);
					player.click_item();
				}else {
					System.out.println("Item found, picking it up");
					// ITEM WAS NEAR AND PLAYER CLICKED ON IT
					//itemmanager.find_new_active(target_x, target_y);
				}
				*/

			} else {
				// player move
				player.move(target_x, target_y);
			}

			click_first_time = false;
		}

		if (!Gdx.input.isTouched() && !click_first_time) {
			click_first_time = true;
		}

	}

	public void render() {

		if (debug) {
			shape.begin(ShapeType.Point);
			shape.point(target_x, target_y, 0);
			shape.end();
		}
	}

}
