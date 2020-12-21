package net.mgsx.dl15.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ship extends Entity {
	public Module module;

	public final Rectangle bounds = new Rectangle(-2, -1, 4, 2);

	public final Vector2 velocity = new Vector2();

	public boolean shooting;

	private float shootTimeout;

	private float shootFrequency = 5;
	
	
	@Override
	public void update(World world, float delta) {
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			velocity.x = -1;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			velocity.x = 1;
		}
		else {
			velocity.x = 0;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			velocity.y = 1;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			velocity.y = -1;
		}
		else {
			velocity.y = 0;
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.X)){
			if(module != null) module.locked = !module.locked;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)){
			if(module != null) module.shooting = true;
			shooting = true;
		}else{
			if(module != null) module.shooting = false;
			shooting = false;
		}
		position.mulAdd(velocity, delta * 10f);
		float xMax = world.screenBounds.x +  world.screenBounds.width - (bounds.x + bounds.width);
		if(position.x > xMax){
			position.x = xMax;
		}
		float xMin = world.screenBounds.x - bounds.x;
		if(position.x < xMin){
			position.x = xMin;
		}
		float yMax = world.screenBounds.y +  world.screenBounds.height - (bounds.y + bounds.height);
		if(position.y > yMax){
			position.y = yMax;
		}
		float yMin = world.screenBounds.y - bounds.y;
		if(position.y < yMin){
			position.y = yMin;
		}
		
		// update module
		if(module != null){
			if(module.locked){
				module.direction.scl(-1).lerp(velocity, delta * 6).scl(-1).nor();
			}
			module.position.set(position).mulAdd(module.direction, 5);
			module.shootDirection.scl(-1).lerp(velocity, delta * 10).scl(-1).nor();
			
			module.update(world, delta);
		}
		if(shooting){
			shootTimeout -= delta;
			if(shootTimeout < 0){
				shootTimeout = 1f / shootFrequency;
				shootFrequency = 10;
				world.emitBullet(Bullet.SHIP_BIT, position.x, position.y, 60f, 0f);
			}
		}else{
			shootTimeout = 0;
		}
	}
	
}
