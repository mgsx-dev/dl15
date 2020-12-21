package net.mgsx.dl15.model;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;

abstract public class GameSequence {

	protected final Actor actor = new Actor();
	
	public void reset(World world){
		actor.clearActions();
		actor.addAction(create(world));
	}

	abstract protected Action create(World world);

	/**
	 * return true if finished
	 * @param world
	 * @param delta
	 * @return
	 */
	public boolean update(float delta){
		actor.act(delta);
		return !actor.hasActions();
	}

}
