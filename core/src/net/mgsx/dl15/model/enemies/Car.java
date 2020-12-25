package net.mgsx.dl15.model.enemies;

import net.mgsx.dl15.assets.Assets;
import net.mgsx.dl15.model.Bullet;
import net.mgsx.dl15.model.Enemy;
import net.mgsx.dl15.model.World;

public class Car extends Enemy {
	public Car() {
		sprite.setRegion(Assets.i.atlas.findRegion("car"));
	}
	@Override
	public void init() {
		width = 2.5f;
		height = 5;
		
		spriteBase = 6;
		spriteOffsetX = -1.7f;
		spriteOffsetY = -3;
		
		shotTimeout = 0;
	}
	@Override
	public void update(World world, float delta) {
		
		shotTimeout -= delta;
		if(shotTimeout < 0){
			shotTimeout = .5f;
			float spd = 20;
			world.emitBullet(Bullet.ENEMY_BIT, position.x, position.y, spd, 0);
			world.emitBullet(Bullet.ENEMY_BIT, position.x, position.y, -spd, 0);
			world.emitBullet(Bullet.ENEMY_BIT, position.x, position.y, 0, spd);
			world.emitBullet(Bullet.ENEMY_BIT, position.x, position.y, 0, -spd);
		}
		
		super.update(world, delta);
	}
}
