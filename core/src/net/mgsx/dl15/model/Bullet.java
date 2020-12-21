package net.mgsx.dl15.model;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

import net.mgsx.dl15.assets.Assets;

public class Bullet extends Entity {
	public static final Pool<Bullet> pool = new Pool<Bullet>(){
		@Override
		protected Bullet newObject() {
			return new Bullet();
		}
	};
	public static final int SHIP_BIT = 1;
	public static final int MODULE_BIT = 2;
	
	public static final int LEVEL_1 = 4;
	public static final int LEVEL_2 = 8;
	public static final int LEVEL_3 = 16;
	
	
	public static final int PLAYER_MASK = 3;
	
	public static final int ENEMY_BIT = 256;
	public int bulletBits;
	public final Vector2 velocity = new Vector2();
	public float radius;
	public float angle;
	
	private float time;
	
	@Override
	public void update(World world, float delta) {
		time += delta;
		position.mulAdd(velocity, delta);
		
		if((bulletBits & ENEMY_BIT) != 0){
			int i = MathUtils.floor(time * 5) % Assets.i.enBullets.length;
			sprite.setRegion(Assets.i.enBullets[i]);
			sprite.setBounds(position.x-radius, position.y-radius, radius * 2, radius * 2);
		}
		else if((bulletBits & LEVEL_1) != 0){
			TextureRegion r = Assets.i.shipBullets[0];
			sprite.setRegion(r);
			sprite.setBounds(position.x-radius, position.y-radius, radius * 2, radius * 2 * (float)r.getRegionHeight() / (float)r.getRegionWidth());
			
			sprite.setOrigin(radius, radius);
			sprite.setRotation(angle-90);
		}
		else if((bulletBits & LEVEL_2) != 0){
			TextureRegion r = Assets.i.shipBullets[1];
			sprite.setRegion(r);
			sprite.setBounds(position.x-radius, position.y-radius, radius * 2, radius * 2 * (float)r.getRegionHeight() / (float)r.getRegionWidth());
			
			sprite.setOrigin(radius, radius);
			sprite.setRotation(angle-90);
		}
	}
}
