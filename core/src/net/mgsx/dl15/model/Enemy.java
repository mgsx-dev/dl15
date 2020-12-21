package net.mgsx.dl15.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {

	public int width;
	public int height;
	public final Vector2 direction = new Vector2();
	public final Rectangle bounds = new Rectangle();
	private float flashTimeout;
	public boolean flashing;
	public float energy;
	private final static Rectangle r = new Rectangle();
	
	@Override
	public void update(World world, float delta) {
		
		if(energy <= 0) alive = false;
		
		position.x -= delta * 10;
		if(position.x + width/2 < world.screenBounds.x){
			alive = false;
		}
		bounds.set(position.x-width/2, position.y-height/2, width, height);
		
		flashTimeout -= delta;
		if(flashTimeout > 0){
			flashing = (flashTimeout * 30f) % 1f < .4f;
		}else{
			flashing = false;
		}
	}

	public boolean hit(Circle circle) {
		r.setSize(circle.radius * 2);
		r.setCenter(circle.x, circle.y);
		return bounds.overlaps(r);
	}

	public void damage(Entity e, float delta) {
		if(flashTimeout < 0) flashTimeout = .2f;
		energy -= e instanceof Module ? delta * 10f : 1;
	}

}
