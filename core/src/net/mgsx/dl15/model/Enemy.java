package net.mgsx.dl15.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import net.mgsx.dl15.assets.Assets;

public class Enemy extends Entity {

	public float width;
	public float height;
	public final Vector2 direction = new Vector2();
	public final Rectangle bounds = new Rectangle();
	private float flashTimeout;
	public boolean flashing;
	public float energy;
	private final static Rectangle r = new Rectangle();
	protected float shotTimeout = 3;
	public float spriteBase;
	public float spriteOffsetX;
	public float spriteOffsetY;
	public float points = 100;
	
	public Enemy() {
		sprite.setRegion(Assets.i.enFighters[0]);
	}
	
	public void init(){
		
	}
	
	@Override
	public void update(World world, float delta) {
		
		if(energy <= 0){
			alive = false;
			world.emitExplosion(position.x, position.y, 4f);
			world.pushScore(this);
		}
		
		position.y -= delta * 10;
		if(position.y + height/2 < world.screenBounds.y){
			alive = false;
		}
		bounds.set(position.x-width/2, position.y-height/2, width, height);
		
		flashTimeout -= delta;
		if(flashTimeout > 0){
			flashing = (flashTimeout * 30f) % 1f < .4f;
		}else{
			flashing = false;
		}
		
		float sph = spriteBase;
		float spw = sph * (float)sprite.getRegionWidth() / (float)sprite.getRegionHeight();
		sprite.setBounds(position.x + spriteOffsetX, position.y + spriteOffsetY, spw, sph);
		
		if(flashing){
			sprite.setColor(Color.WHITE);
		}else{
			sprite.setColor(Color.BLACK);
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
