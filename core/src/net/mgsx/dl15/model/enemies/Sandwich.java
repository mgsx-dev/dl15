package net.mgsx.dl15.model.enemies;

import net.mgsx.dl15.assets.Assets;
import net.mgsx.dl15.model.Bullet;
import net.mgsx.dl15.model.Enemy;
import net.mgsx.dl15.model.World;

public class Sandwich extends Enemy {
	public Sandwich() {
		sprite.setRegion(Assets.i.atlas.findRegion("sandwich"));
	}
	@Override
	public void init() {
		width = 2f;
		height = 8;
		
		spriteBase = 10;
		spriteOffsetX = -2.7f;
		spriteOffsetY = -5;
		
		shotTimeout = 0;
	}
	@Override
	public void update(World world, float delta) {
		
		shotTimeout -= delta;
		if(shotTimeout < 0){
			shotTimeout = .3f;
			float spd = 5;
			world.emitBullet(Bullet.ENEMY_BIT, position.x + 3, position.y, spd, -spd);
			world.emitBullet(Bullet.ENEMY_BIT, position.x - 3, position.y, -spd, -spd);
		}
		
		super.update(world, delta);
	}
}
