package net.mgsx.dl15;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import net.mgsx.dl15.assets.Assets;
import net.mgsx.dl15.screens.GameScreen;
import net.mgsx.dl15.screens.SceneScreen;

public class DL15Game extends Game {
	
	
	@Override
	public void create () {
		
		Assets.i = new Assets();
		
		setScreen(new SceneScreen());
		// setScreen(new GameScreen());
	}

	public static void newGame() {
		((DL15Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
	}
	
}
