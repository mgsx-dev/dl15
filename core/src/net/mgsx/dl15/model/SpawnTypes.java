package net.mgsx.dl15.model;

import com.badlogic.gdx.math.MathUtils;

import net.mgsx.dl15.model.enemies.Car;
import net.mgsx.dl15.model.enemies.Fighter;
import net.mgsx.dl15.model.enemies.Potiron;
import net.mgsx.dl15.model.enemies.Sandwich;

public class SpawnTypes {
	public static class SpawnType {
		public String label, text;
		public void spawn(World world){
			
		}
	}
	
	public static final SpawnType ww2 = new SpawnType(){
		{
			label = "Hard drive crackling";
			text = "Brrrrrr!!\nTaTaTaTa!!";
		}
		public void spawn(World world) {
			Enemy e = new Fighter();
			e.init();
			float xmin = world.screenBounds.x + e.width/2;
			float xmax = world.screenBounds.x + world.screenBounds.width - e.width/2;
			float px = MathUtils.random(xmin, xmax);
			
			e.position.set(px, 
					world.screenBounds.y + world.screenBounds.height  + e.height);
			e.direction.set(0,-1);
			e.alive = true;
			e.energy = 5;
			world.enemies.add(e);
		}
	};
	
	public static final SpawnType potiron = new SpawnType(){
		{
			label = "Spooky howl";
			text = "Halloween Party Next Week!!";
		}
		public void spawn(World world) {
			Enemy e = new Potiron();
			e.init();
			float xmin = world.screenBounds.x + e.width/2;
			float xmax = world.screenBounds.x + world.screenBounds.width - e.width/2;
			float px = MathUtils.random(xmin, xmax);
			
			e.position.set(px, 
					world.screenBounds.y + world.screenBounds.height  + e.height);
			e.direction.set(0,-1);
			e.alive = true;
			e.energy = 5;
			world.enemies.add(e);
		}
	};

	public static final SpawnType sandwich = new SpawnType(){
		{
			label = "Stomach gurgling";
			text = "My sandwich\nis rebelling";
		}
		public void spawn(World world) {
			Enemy e = new Sandwich();
			e.init();
			float xmin = world.screenBounds.x + e.width/2;
			float xmax = world.screenBounds.x + world.screenBounds.width - e.width/2;
			float px = MathUtils.random(xmin, xmax);
			
			e.position.set(px, 
					world.screenBounds.y + world.screenBounds.height + e.height);
			e.direction.set(0,-1);
			e.alive = true;
			e.energy = 5;
			world.enemies.add(e);
		}
	};

	public static final SpawnType car = new SpawnType(){
		{
			label = "Honk";
			text = "Buy a new car for only $1000 !!";
		}
		public void spawn(World world) {
			Enemy e = new Car();
			e.init();
			float xmin = world.screenBounds.x + e.width/2;
			float xmax = world.screenBounds.x + world.screenBounds.width - e.width/2;
			float px = MathUtils.random(xmin, xmax);
			
			e.position.set(px, 
					world.screenBounds.y + world.screenBounds.height + e.height);
			e.direction.set(0,-1);
			e.alive = true;
			e.energy = 5;
			world.enemies.add(e);
		}
	};

	
	public static final SpawnType [] all = new SpawnType[]{ww2, car, sandwich, potiron};
}
