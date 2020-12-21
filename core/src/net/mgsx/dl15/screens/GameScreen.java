package net.mgsx.dl15.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

import net.mgsx.dl15.levels.Level1;
import net.mgsx.dl15.model.Module;
import net.mgsx.dl15.model.Ship;
import net.mgsx.dl15.model.World;

public class GameScreen extends ScreenAdapter {
	private World world;
	
	public GameScreen() {
		world = new World();
		world.ships.add(new Ship());
		world.ships.first().module = new Module();
		
		world.addSequence(new Level1());
	}
	
	@Override
	public void render(float delta) {
		world.update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// TODO
		world.draw();
	}
}
