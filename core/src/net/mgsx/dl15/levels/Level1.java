package net.mgsx.dl15.levels;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import net.mgsx.dl15.model.Enemy;
import net.mgsx.dl15.model.GameSequence;
import net.mgsx.dl15.model.World;

public class Level1 extends GameSequence {

	@Override
	protected Action create(World world) {
		return Actions.sequence(
				Actions.repeat(4, Actions.sequence(
						Actions.run(()->wave1(world)),
						Actions.delay(1f))),
				Actions.repeat(10, Actions.sequence(
						Actions.run(()->wave2(world)),
						Actions.delay(.5f)))
				
				
				);
	}

	private void wave1(World world) {
		Enemy e = new Enemy();
		e.width = 1;
		e.height = 2;
		e.position.set(world.screenBounds.x + world.screenBounds.width/2 - e.width/2, 
				world.screenBounds.y + world.screenBounds.height - e.height/2);
		e.direction.set(0,-1);
		e.alive = true;
		e.energy = 5;
		world.enemies.add(e);
	}
	
	private void wave2(World world) {
		Enemy e = new Enemy();
		e.width = 1;
		e.height = 2;
		float xmin = world.screenBounds.x + e.width/2;
		float xmax = world.screenBounds.x + world.screenBounds.width - e.width/2;
		float px = MathUtils.random(xmin, xmax);
		
		e.position.set(px, 
				world.screenBounds.y + world.screenBounds.height - e.height/2);
		e.direction.set(0,-1);
		e.alive = true;
		e.energy = 5;
		world.enemies.add(e);
	}
	
}
