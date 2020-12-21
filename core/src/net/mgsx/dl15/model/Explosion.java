package net.mgsx.dl15.model;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Pool;

import net.mgsx.dl15.assets.Assets;

public class Explosion extends Entity
{
	public static final Pool<Explosion> pool = new Pool<Explosion>(){
		@Override
		protected Explosion newObject() {
			return new Explosion();
		}
	};
	
	public float time;
	public float radius;
	
	@Override
	public void update(World world, float delta) {
		time += delta;
		int frame = MathUtils.floor(time * 20);
		if(frame >= Assets.i.explosions.length){
			alive = false;
		}else{
			sprite.setRegion(Assets.i.explosions[frame]);
			sprite.setBounds(position.x-radius, position.y-radius, radius*2, radius*2);
			sprite.setOriginCenter();
		}
	}

}
