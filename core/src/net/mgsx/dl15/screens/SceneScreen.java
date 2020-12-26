package net.mgsx.dl15.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import net.mgsx.dl15.DL15Game;
import net.mgsx.dl15.assets.Assets;
import net.mgsx.dl15.utils.StageScreen;

public class SceneScreen extends StageScreen
{
	private Table root;
	private SequenceAction seq;
	private Label label;
	private TextButton btNext;
	private boolean skipJustPressed;

	public SceneScreen() {
		
		root = new Table(Assets.i.skin);
		root.setFillParent(true);
		stage.addActor(root);
		
		label = root.add("").expand().bottom().padBottom(50).getActor();
		
		root.row();
		
		btNext = new TextButton("skip", Assets.i.skin);
		root.add(btNext);
		
		btNext.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				skipJustPressed = true;
			}
		});
		
		seq = new SequenceAction();
		
		pushScene(Assets.i.scenes[0], 5f, "...libGDX man typing...");
		pushScene(Assets.i.scenes[1], 6f, "Holy crap, this libGDX 15th jam is a real nightmare.");
		pushScene(Assets.i.scenes[2], 4f, "...libGDX man typing harder...");
		pushScene(Assets.i.scenes[3], 6f, "Deadline is so close and am so tired.");
		pushScene(Assets.i.scenes[4], 4f, "...libGDX man stop typing...");
		pushScene(Assets.i.scenes[5], 4f, "...libGDX man yawning...");
		pushScene(Assets.i.scenes[6], 4f, "zZz ... zZz ... zZz ... zZz");
		pushScene(Assets.i.scenes[7], 5f, "zZz ... zZz ... zZz ... zZz");
		
		seq.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				DL15Game.newGame();
			}
		}));
		
		stage.addAction(seq);
	}

	private void pushScene(TextureRegion bg, float time, String text) {
		seq.addAction(Actions.run(new Runnable() {
			@Override
			public void run() {
				root.setBackground(new TextureRegionDrawable(bg));
				label.setText(text);
			}
		}));
		seq.addAction(new DelayAction(time){
			@Override
			protected boolean delegate(float delta) {
				return skipJustPressed || super.delegate(delta);
			}
		});
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		super.render(delta);
		
		skipJustPressed = false;
	}
}
