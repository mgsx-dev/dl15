package net.mgsx.dl15.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class World {
	public final Array<Ship> ships = new Array<Ship>();
	public final Array<Bullet> bullets = new Array<Bullet>();
	private ShapeRenderer shapes;
	private Viewport viewport;
	private static final Vector2 tmp = new Vector2();
	private static final Circle c = new Circle();
	public Rectangle screenBounds = new Rectangle();
	public final Array<GameSequence> sequences = new Array<GameSequence>();
	public final Array<Enemy> enemies = new Array<Enemy>();
	
	public World() {
		shapes = new ShapeRenderer();
		viewport = new FitViewport(60, 30);
	}
	
	public void update(float delta){
		boolean sequenceOver = true;
		for(int i=0 ; i<sequences.size ; i++){
			if(!sequences.get(i).update(delta)){
				sequenceOver = false;
				break;
			}
		}
		if(sequenceOver){
			for(GameSequence s : sequences) s.reset(this);
		}
		screenBounds.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
		screenBounds.setCenter(viewport.getCamera().position.x, viewport.getCamera().position.y);
		for(Ship ship : ships){
			ship.update(this, delta);
		}
		for(int i=enemies.size-1 ; i>=0 ; i--){
			Enemy e = enemies.get(i);
			e.update(this, delta);
			
			for(Ship ship : ships){
				if(ship.module != null){
					c.set(ship.module.position, ship.module.radius);
					if(e.hit(c)){
						e.damage(ship.module, delta);
					}
				}
			}
			
			
			if(!e.alive){
				enemies.swap(enemies.size-1, i);
				enemies.pop();
			}
		}
		for(int i=bullets.size-1 ; i>=0 ; i--){
			Bullet bullet = bullets.get(i);
			bullet.update(this, delta);
			c.set(bullet.position, bullet.radius);
			
			if((bullet.bulletBits & Bullet.PLAYER_MASK) != 0){
				for(Enemy enemy : enemies){
					if(enemy.hit(c)){
						enemy.damage(bullet, delta);
						bullet.alive = false;
					}
				}
			}
			
			if(!screenBounds.contains(c)){
				bullet.alive = false;
			}
			if(!bullet.alive){
				bullets.swap(bullets.size-1, i);
				bullets.pop();
				Bullet.pool.free(bullet);;
			}
		}
	}


	public void draw() {
		viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapes.setProjectionMatrix(viewport.getCamera().combined);
		shapes.begin(ShapeType.Line);
		for(Ship ship : ships){
			drawShip(ship);
			if(ship.module != null) drawModule(ship.module);
		}
		for(Enemy enemy : enemies){
			drawEnemy(enemy);
		}
		for(Bullet bullet : bullets){
			drawBullet(bullet);
		}
		shapes.end();
	}


	private void drawEnemy(Enemy enemy) {
		shapes.setColor(enemy.flashing ? Color.WHITE : Color.PURPLE);
		shapes.rect(enemy.position.x - enemy.width/2, enemy.position.y - enemy.height/2, enemy.width, enemy.height);

	}

	private void drawBullet(Bullet bullet) {
		shapes.setColor(Color.YELLOW);
		shapes.circle(bullet.position.x, bullet.position.y, bullet.radius, 16);
	}

	private void drawModule(Module module) {
		shapes.setColor(Color.ORANGE);
		shapes.circle(module.position.x, module.position.y, module.radius, 16);
		tmp.set(module.position).mulAdd(module.shootDirection, module.radius);
		shapes.circle(tmp.x, tmp.y, module.radius / 4f, 16);
	}


	private void drawShip(Ship ship) {
		shapes.setColor(Color.WHITE);
		shapes.rect(ship.position.x+ship.bounds.x, ship.position.y+ship.bounds.y, ship.bounds.width, ship.bounds.height);
	}

	public void emitBullet(int bulletBits, float x, float y, float dx, float dy) {
		Bullet b = Bullet.pool.obtain();
		b.position.set(x, y);
		b.velocity.set(dx, dy);
		b.bulletBits = bulletBits;
		b.alive = true;
		b.radius = .5f;
		bullets.add(b);
	}

	public void addSequence(GameSequence s) {
		sequences.add(s);
		s.create(this);
	}
}
