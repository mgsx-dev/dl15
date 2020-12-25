package net.mgsx.dl15.model.enemies;

import net.mgsx.dl15.assets.Assets;
import net.mgsx.dl15.model.Bullet;
import net.mgsx.dl15.model.Enemy;
import net.mgsx.dl15.model.World;

public class Potiron extends Enemy {
	public Potiron() {
		sprite.setRegion(Assets.i.atlas.findRegion("potiron"));
	}
	
	@Override
	public void init() {
		width = 9;
		height = 5;
		
		spriteBase = 10;
		spriteOffsetX = -5.5f;
		spriteOffsetY = -4;
		
		shotTimeout = 0;
	}
	
	@Override
	public void update(World world, float delta) {
		
		shotTimeout -= delta;
		if(shotTimeout < 0){
			shotTimeout = .3f;
			float spd = 20;
			world.emitBullet(Bullet.ENEMY_BIT, position.x, position.y, 0, -spd);
			world.emitBullet(Bullet.ENEMY_BIT, position.x + 3, position.y, 0, -spd);
			world.emitBullet(Bullet.ENEMY_BIT, position.x - 3, position.y, 0, -spd);
		}
		
		super.update(world, delta);
	}
}
