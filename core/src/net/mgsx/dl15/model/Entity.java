package net.mgsx.dl15.model;

import com.badlogic.gdx.math.Vector2;

abstract public class Entity {
	public boolean alive;
	public final Vector2 position = new Vector2();
	
	abstract public void update(World world, float delta);
}
