package net.mgsx.dl15.levels;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import net.mgsx.dl15.model.Enemy;
import net.mgsx.dl15.model.GameSequence;
import net.mgsx.dl15.model.World;

public class Level1 extends GameSequence {

	@Override
	protected Action create(World world) {
		return Actions.sequence(
				Actions.run(()->wave1(world)),
				Actions.delay(1f)
				);
	}

	private void wave1(World world) {
		Enemy e = new Enemy();
		e.width = 2;
		e.height = 1;
		e.position.set(world.screenBounds.x + world.screenBounds.width - e.width/2, world.screenBounds.y + world.screenBounds.height/2 - e.height/2);
		e.direction.set(-1, 0);
		e.alive = true;
		e.energy = 5;
		world.enemies.add(e);
	}
	
}
