package net.mgsx.dl15.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;

import net.mgsx.dl15.assets.Assets;
import net.mgsx.dl15.model.Module;
import net.mgsx.dl15.model.Ship;
import net.mgsx.dl15.model.SpawnTypes;
import net.mgsx.dl15.model.SpawnTypes.SpawnType;
import net.mgsx.dl15.model.World;
import net.mgsx.dl15.utils.StageScreen;

public class GameScreen extends StageScreen {
	private World world;
	
	private boolean paused;

	private Label labelScore;
	
	public GameScreen() {
		world = new World();
		world.ships.add(new Ship());
		world.ships.first().module = new Module();
		
		// world.addSequence(new Level1());
		
		Image img = new Image(Assets.i.scenes[8]);
		img.setScaling(Scaling.fit);
		
		Table t = new Table();
		t.setFillParent(true);
		t.add(img).width(320).padLeft(320);
		
		
		stage.addActor(t);
		
		spawn(null);
		spawn(null);
		spawn(null);
		
		Table scoreTable = new Table();
		scoreTable.setFillParent(true);
		
		scoreTable.add(labelScore = new Label("", Assets.i.skin)).expand().top().padRight(320);
		
		labelScore.setFontScale(.5f);
		labelScore.setAlignment(Align.center);
		
		formatScore(0f, 0f, 0f, 0f);
		
		stage.addActor(scoreTable);
	}
	
	private void formatScore(float score, float best, float rate, float bestRate) {
		labelScore.setText("Score " + MathUtils.round(score) + " Best " + MathUtils.round(best) + "\n" +
				"Rate " + MathUtils.round(rate) + " Best " + MathUtils.round(bestRate));
	}

	private void spawn(TextButton bt){
		if(bt != null) bt.remove();
		
		int r = MathUtils.random(SpawnTypes.all.length-1);
		final SpawnType spt = SpawnTypes.all[r];
		
		Skin skin = Assets.i.skin;
		
		bt = new TextButton(spt.label, skin);
		bt.pack();
		stage.addActor(bt);
		
		float x = MathUtils.random(320 - bt.getWidth());
		float y = MathUtils.random(480 - bt.getHeight());
		
		bt.setPosition(320 + x, y);
		bt.getColor().a = .5f;
		
		final TextButton newBT = bt;
		bt.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				spawn(newBT);
				spt.spawn(world);
			}
		});
		
	}
	
	@Override
	public void render(float delta) {
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)) paused = !paused;
		
		if(paused) delta = 0;
		
		world.update(delta);
		
		formatScore(world.score, world.best, world.rate, world.bestRate);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// TODO
		world.draw();
		
		viewport.apply();
		super.render(delta);
	}
}
