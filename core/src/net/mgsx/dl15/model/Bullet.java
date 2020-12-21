package net.mgsx.dl15.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Bullet extends Entity {
	public static final Pool<Bullet> pool = new Pool<Bullet>(){
		@Override
		protected Bullet newObject() {
			return new Bullet();
		}
	};
	public static final int SHIP_BIT = 1;
	public static final int MODULE_BIT = 2;
	
	public static final int PLAYER_MASK = 3;
	
	public static final int ENEMY_BIT = 4;
	public int bulletBits;
	public final Vector2 velocity = new Vector2();
	public float radius;
	

	@Override
	public void update(World world, float delta) {
		position.mulAdd(velocity, delta);
	}
}
