package net.mgsx.dl15.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

abstract public class StageScreen extends ScreenAdapter
{
	protected Stage stage;
	protected Viewport viewport;
	
	public StageScreen() 
	{
		this(new FitViewport(640, 480));
	}
	public StageScreen(Viewport viewport) 
	{
		this.viewport = viewport;
		stage = new Stage(viewport);
	}
	
	@Override
	public void show() {
		super.show();
		Gdx.input.setInputProcessor(stage);
	}
	
	@Override
	public void render(float delta) {
		
//		Gdx.gl.glClearColor(0, 0, 0, 0);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		stage.act();
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}
	
	@Override
	public void dispose() {
		stage.dispose();
	}
}