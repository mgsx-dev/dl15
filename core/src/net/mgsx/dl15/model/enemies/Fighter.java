package net.mgsx.dl15.model.enemies;

import com.badlogic.gdx.math.Vector2;

import net.mgsx.dl15.model.Bullet;
import net.mgsx.dl15.model.Enemy;
import net.mgsx.dl15.model.World;

public class Fighter extends Enemy {

	
	@Override
	public void init() {
		width = 1;
		height = 3;
		
		spriteBase = 4;
		spriteOffsetX = -2f;
		spriteOffsetY = -1.5f;
		
		shotTimeout = 0;
	}
	
	@Override
	public void update(World world, float delta) {
		
		shotTimeout -= delta;
		if(shotTimeout < 0){
			shotTimeout = 1;
			Vector2 d = world.ships.first().position.cpy().sub(position).nor().scl(5f);
			world.emitBullet(Bullet.ENEMY_BIT, position.x, position.y, d.x, d.y);
		}
		
		super.update(world, delta);
	}
}
