package net.mgsx.dl15.model;

import com.badlogic.gdx.math.Vector2;

import net.mgsx.dl15.assets.Assets;

public class Module extends Entity {

	public final Vector2 direction = new Vector2(0,1);
	public float radius = 1;
	public boolean locked = false;
	private float shootTimeout;
	public float shootFrequency = 3f;
	public final Vector2 shootDirection = new Vector2(0,1);

	public enum State{
		Idle, Forward, Backward
	}
	public State state = State.Idle;
	public boolean shooting;
	
	public Module() {
		super();
		sprite.setRegion(Assets.i.moduleSprite);
	}
	
	@Override
	public void update(World world, float delta) {
		if(shooting){
			shootTimeout -= delta;
			if(shootTimeout < 0){
				shootTimeout = 1f / shootFrequency;
				shootFrequency = 20;
				float speed = 40f;
				world.emitBullet(Bullet.MODULE_BIT | Bullet.LEVEL_1, position.x, position.y, speed * shootDirection.x, speed * shootDirection.y);
			}
		}else{
			shootTimeout = 0;
		}
		sprite.setBounds(position.x-radius, position.y-radius, radius*2, radius*2);
		sprite.setOriginCenter();
		sprite.setRotation(shootDirection.angleDeg()-90);
	}

}
