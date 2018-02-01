package Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.gamejam2018.game.Game;

public class Player {

	Shadow shadow;
	public Target target;

	boolean debug = false;
	
	public float x, y;
	public float distance_original_x, distance_original_y;
	int distance_counting = 0;
	float move_x, move_y;
	float target_x, target_y;
	float height, width;
	int dir = 0;
	int dir_still = 0;
	float speed = 220;
	public float distance_target_original = 0;
	float distance_target = 0;
	float last_distance = 2000;
	public boolean clicked_on_item;
	
	public Vector2 vector_original_distance;
	public int texture_size;

	SpriteBatch sb;
	ShapeRenderer shape;
	OrthographicCamera cam;

	Texture anim;
	int FRAME_COLS = 3, FRAME_ROWS = 4;

	Animation<TextureRegion> walkAnimation_down;
	Animation<TextureRegion> walkAnimation_up;
	Animation<TextureRegion> walkAnimation_right;
	Animation<TextureRegion> walkAnimation_left;

	Animation<TextureRegion> walkAnimation;
	Animation<TextureRegion> still_down;
	Animation<TextureRegion> still_up;
	Animation<TextureRegion> still_right;
	Animation<TextureRegion> still_left;

	float stateTime;

	boolean is_destoyed;
	boolean is_walking;

	boolean click_first_time;

	public Player() {

	}

	public void create() {

		target = new Target();
		target.init(this);
		target.create();

		shadow = new Shadow();
		shadow.create();
		shadow.init(this);

		vector_original_distance = new Vector2(0, 0);
		click_first_time = true;
		clicked_on_item = false;
		stateTime = 0;
		anim = new Texture("play/martynas_sheet.png");

		TextureRegion[][] tmp = TextureRegion.split(anim, anim.getWidth() / FRAME_COLS, anim.getHeight() / FRAME_ROWS);

		TextureRegion[] walkFrames_down = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkFrames_up = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkFrames_right = new TextureRegion[FRAME_COLS];
		TextureRegion[] walkFrames_left = new TextureRegion[FRAME_COLS];
		TextureRegion walkFrames_still = new TextureRegion();
		TextureRegion walkFrames_still_down = new TextureRegion();
		TextureRegion walkFrames_still_up = new TextureRegion();
		TextureRegion walkFrames_still_right = new TextureRegion();
		TextureRegion walkFrames_still_left = new TextureRegion();

		int index = 0;
		for (int i = 0; i < FRAME_COLS; i++) {
			walkFrames_up[index++] = tmp[0][i];

		}
		index = 0;
		for (int i = 0; i < FRAME_COLS; i++) {
			walkFrames_left[index++] = tmp[1][i];

		}
		index = 0;
		for (int i = 0; i < FRAME_COLS; i++) {
			walkFrames_down[index++] = tmp[2][i];

		}
		index = 0;
		for (int i = 0; i < FRAME_COLS; i++) {
			walkFrames_right[index++] = tmp[3][i];
		}

		walkFrames_still_down = tmp[2][1];
		walkFrames_still_up = tmp[0][1];
		walkFrames_still_right = tmp[3][1];
		walkFrames_still_left = tmp[1][1];

		walkAnimation_down = new Animation<TextureRegion>(0.125f, walkFrames_down);
		walkAnimation_up = new Animation<TextureRegion>(0.125f, walkFrames_up);
		walkAnimation_left = new Animation<TextureRegion>(0.125f, walkFrames_left);
		walkAnimation_right = new Animation<TextureRegion>(0.125f, walkFrames_right);
		
		walkAnimation_down.setPlayMode(PlayMode.LOOP_PINGPONG);
		walkAnimation_up.setPlayMode(PlayMode.LOOP_PINGPONG);
		walkAnimation_left.setPlayMode(PlayMode.LOOP_PINGPONG);
		walkAnimation_right.setPlayMode(PlayMode.LOOP_PINGPONG);

		walkAnimation = walkAnimation_down;
		still_down = new Animation<TextureRegion>(0.125f, walkFrames_still_down);
		still_up = new Animation<TextureRegion>(0.125f, walkFrames_still_up);
		still_left = new Animation<TextureRegion>(0.125f, walkFrames_still_left);
		still_right = new Animation<TextureRegion>(0.125f, walkFrames_still_right);

		distance_original_x = 0;
		distance_original_y = 0;
		height = 40;
		width = 40;
		target_x = 0;
		target_y = 0;
		texture_size = tmp[0][0].getRegionWidth();
		is_walking = false;

		x = ((Game.ORIGINAL_WIDTH / 2) - (texture_size / 2)) * 2;
		y = ((Game.ORIGINAL_HEIGHT / 2) - (texture_size / 2)) * 2;
	}

	public void init(SpriteBatch batch, ShapeRenderer shape, OrthographicCamera cam) {
		this.sb = batch;
		this.shape = shape;
		this.cam = cam;
	}

	public void handleinput() {

	}

	public void update(float dt) {

		handleinput();

		shadow.update();

		// MOVING ----------------------------------------------------
		if (is_walking) {
			last_distance = distance_target;
			//System.out.println();
			// calculate left distance between target
			
			//System.out.println("Target x: " + target.x + " y: " + target.y);
			//distance_target = Vector2.dst(target.x, target.y, x - (texture_size / 2), y);
			float dist_x = target.x - x - (texture_size / 2);
			float dist_y = target.y - y;
			distance_target = (float) Math.sqrt(Math.pow(dist_x, 2) + Math.pow(dist_y, 2));
			
			distance_counting++;
			//System.out.println("Distance: " + distance_target);
		}

		// move player
		if (is_walking) {
			//System.out.println("move_x: " + (move_x * speed * dt));
			//System.out.println("move_y: " + (move_y * speed * dt));
			x += move_x * speed * dt;
			y += move_y * speed * dt;
		}

		// check destination reach
		//System.out.println("last_distance " + last_distance + " distance_target " + distance_target);
		
		if(is_walking && ( distance_target < 3 ) ) {
			x = target.x - (texture_size / 2);
			y = target.y;
			//System.out.println("END");
			is_walking = false;
		}
		
		if(clicked_on_item) {
			if(distance_target < 140) {
				is_walking = false;
			}
		}
		
		/*
		if (is_walking && ((last_distance < distance_target) && (distance_counting > 5))) {
			System.out.println("END");
			is_walking = false;
		}
		*/

		// ANIMATIONS ----------------------------------------------------
		if (is_walking) {
			if (dir == 0) {
				walkAnimation = walkAnimation_up;
			}
			if (dir == 1) {
				walkAnimation = walkAnimation_right;
			}
			if (dir == 2) {
				walkAnimation = walkAnimation_down;
			}
			if (dir == 3) {
				walkAnimation = walkAnimation_left;
			}
		} else {
			if (dir_still == 0) {
				walkAnimation = still_up;
			}
			if (dir_still == 1) {
				walkAnimation = still_right;
			}
			if (dir_still == 2) {
				walkAnimation = still_down;
			}
			if (dir_still == 3) {
				walkAnimation = still_left;
			}
		}

	}

	public void render() {

		sb.setProjectionMatrix(cam.combined);

		stateTime += Gdx.graphics.getDeltaTime();
		target.render(sb);
		shadow.render(sb);
		// Get current frame of animation for the current stateTime
		TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		sb.begin();
		sb.draw(currentFrame, x, y);
		sb.end();

		if (debug) {
			shape.setProjectionMatrix(cam.combined);
			shape.begin(ShapeType.Line);
			shape.setColor(Color.WHITE);
			shape.rect((int) (x), (int) (y), texture_size, texture_size);
			shape.end();
		}

	}

	public void destroy() {

	}

	public void dispose() {

	}

	public void move(float target_x, float target_y) {
		// Screen coordinates in the game
		// target_x = - (texture_size / 2);
		// target_y = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),
		// 0)).y;

		target.set_position(target_x, target_y);
		
		this.target.x = target_x;
		this.target_y = target_y;

		// calculate target distance
		Vector2 temp = new Vector2(target_x - x, target_y - y);
		distance_original_x = temp.x;
		distance_original_y = temp.y;
		
		//distance_original_x = target_x - x;
		//distance_original_y = target_y - y;

		// make a vector from it
		vector_original_distance.set(distance_original_x - (texture_size / 2), distance_original_y);

		// vienetinio vektorio x ir y
		Vector2 temp_vector = vector_original_distance;
		temp_vector.setLength(1);
		move_x = temp_vector.x;
		move_y = temp_vector.y;

		distance_target_original = (float) Math
				.sqrt(Math.pow(distance_original_x - (texture_size / 2), 2) + Math.pow(distance_original_y, 2));
		//System.out.println("Original dist: " + distance_target_original + " left dist: " + distance_target);
		distance_target = distance_target_original;
		float temp_angle = vector_original_distance.angle();

		if (temp_angle < 45) {
			dir = 1;
			dir_still = 1;
		} else if (temp_angle < 135) {
			dir = 0;
			dir_still = 0;
		} else if (temp_angle < 225) {
			dir = 3;
			dir_still = 3;
		} else if (temp_angle < 315) {
			dir = 2;
			dir_still = 2;
		} else if (temp_angle < 360) {
			dir = 1;
			dir_still = 1;
		}

		is_walking = true;
		distance_counting = 0;
	}
	
	public void click_item() {
		clicked_on_item = true;
	}
	
	public void click_item_reset() {
		clicked_on_item = false;
	}

}