package handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import com.gamejam2018.game.Game;

public class TextManager {

	Texture bar;
	Vector2 pos_bar, size_bar;

	Vector2 pos_text;

	FreeTypeFontGenerator generator;
	FreeTypeFontParameter parameter;
	BitmapFont font12;

	SpriteBatch sb;
	OrthographicCamera hudcam;

	String text;

	public boolean visible;
	float elapsed;
	float duration;

	public TextManager() {
		generator = new FreeTypeFontGenerator(Gdx.files.internal("font/8-bit pusab.ttf"));
		parameter = new FreeTypeFontParameter();

		parameter.size = 35;
		parameter.color = Color.WHITE;
		font12 = generator.generateFont(parameter); // font size 12 pixels

		bar = new Texture("play/bar.png");

		size_bar = new Vector2(bar.getWidth(), bar.getHeight());
		pos_bar = new Vector2((Game.ORIGINAL_WIDTH / 2) - (size_bar.x / 2),
				Game.ORIGINAL_HEIGHT - size_bar.y - (Game.ORIGINAL_HEIGHT * 0.03f));
		pos_text = new Vector2();

		text = "Hello this is captain Martin, our ship\nis damaged.";

		visible = false;
		elapsed = 0;
		duration = 0;
	}

	public void create() {

	}

	public void init(SpriteBatch sb, OrthographicCamera hudcam) {
		this.sb = sb;
		this.hudcam = hudcam;
	}

	public void update() {
		pos_text.x = pos_bar.x + 10;
		pos_text.y = pos_bar.y + size_bar.y - 10;
		
		if(visible) {
			duration -= Gdx.graphics.getDeltaTime();
			if(duration < 0) {
				visible = false;
			}
		}
		
	}

	public void render() {
		if (visible) {
			sb.begin();
			sb.setProjectionMatrix(hudcam.combined);
			hudcam.update();
			sb.draw(bar, pos_bar.x, pos_bar.y);
			font12.draw(sb, text, pos_text.x, pos_text.y);
			sb.end();
		}
	}

	public void dispose() {
		generator.dispose();
	}

	public void write(String text, float duration) {
		visible = true;
		this.text = text;
		this.duration = duration;
	}

}
