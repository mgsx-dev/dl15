package net.mgsx.dl15;

import com.badlogic.gdx.Game;

import net.mgsx.dl15.screens.GameScreen;

public class DL15Game extends Game {
	
	
	@Override
	public void create () {
		setScreen(new GameScreen());
	}
	
}
