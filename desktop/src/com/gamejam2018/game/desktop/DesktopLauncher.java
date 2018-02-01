package com.gamejam2018.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gamejam2018.game.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Game(), config);
		config.title = "GameJam2018 v0.1";
		config.width = Game.V_WIDTH;
		config.height = Game.V_HEIGHT;
		config.resizable = false;
		
		config.fullscreen = false;
		config.vSyncEnabled = false;
		config.foregroundFPS = 60;
		config.backgroundFPS = 60;
	}
}
