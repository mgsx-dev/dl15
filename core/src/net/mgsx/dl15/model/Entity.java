package net.mgsx.dl15.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

abstract public class Entity {
	public boolean alive;
	public final Vector2 position = new Vector2();
	
	public final Sprite sprite;
	
	public Entity() {
		sprite = new Sprite();
		sprite.setColor(Color.BLACK);
	}
	
	abstract public void update(World world, float delta);
}
