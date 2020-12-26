package net.mgsx.dl15.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import net.mgsx.dl15.assets.Assets;
import net.mgsx.dl15.assets.Shaders;
import net.mgsx.dl15.utils.MeanWindow;

public class World {
	public static boolean drawDebug = false;
	
	
	public final Array<Ship> ships = new Array<Ship>();
	public final Array<Bullet> bullets = new Array<Bullet>();
	private ShapeRenderer shapes;
	private Viewport viewport;
	private static final Vector2 tmp = new Vector2();
	private static final Circle c = new Circle();
	public Rectangle screenBounds = new Rectangle();
	public final Array<GameSequence> sequences = new Array<GameSequence>();
	public final Array<Enemy> enemies = new Array<Enemy>();
	private SpriteBatch batch;
	private float time;
	private final Array<Explosion> explosions = new Array<Explosion>();

	private ShaderProgram shader;

	
	public float score = 0;
	public float best = 0;
	public float rate = 0;
	public float bestRate = 0;

	public MeanWindow window = new MeanWindow(10);
	
	public World() {
		shapes = new ShapeRenderer();
		viewport = new FitViewport(32, 64);
		batch = new SpriteBatch();
		shader = Shaders.createDefaultShader();
		batch.setShader(shader);
	}
	
	public void update(float delta){
		
		time += delta;
		
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
				if(ship.hit(e.bounds)){
					ship.damage(this);
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
			if((bullet.bulletBits & Bullet.ENEMY_BIT) != 0){
				for(Ship ship : ships){
					if(ship.hit(c)){
						ship.damage(this);
						bullet.alive = false;
					}
					if(ship.module != null){
						if(ship.module.position.dst2(bullet.position) < ship.module.radius*ship.module.radius + bullet.radius*bullet.radius){
							bullet.alive = false;
						}
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
		
		for(int i=explosions.size-1 ; i>=0 ; i--){
			Explosion e = explosions.get(i);
			e.update(this, delta);
			if(!e.alive){
				explosions.swap(i, explosions.size-1);
				explosions.pop();
			}
		}
		
		window.update(time);
		
		rate = window.value;
		if(rate > bestRate) bestRate = rate;
		
		score = window.total;
		if(score > best) best = score;
	}


	public void draw() {
		viewport.update(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight());
		viewport.setScreenX((int)(viewport.getScreenX() * 1.5f)); // XXX hack to center viewport (doesn't really work)
		viewport.apply();
		
		batch.setColor(Color.BLACK);
		batch.setProjectionMatrix(viewport.getCamera().combined);
		batch.begin();
		
		float t = -time * .03f;
		batch.draw(Assets.i.bgNatural, 
				-viewport.getWorldWidth()/2, -viewport.getWorldHeight()/2, viewport.getWorldWidth(), viewport.getWorldHeight(),
				0, t+1, 1, t);
		
		for(Enemy e : enemies){
			e.sprite.draw(batch);
		}
		for(Ship ship : ships){
			ship.sprite.draw(batch);
			if(ship.module != null) ship.module.sprite.draw(batch);
		}
		for(Bullet bullet : bullets){
			bullet.sprite.draw(batch);
		}
		for(Explosion e : explosions){
			e.sprite.draw(batch);
		}
		
		batch.end();
		
		drawDebug = false;
		
		if(drawDebug){
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
		b.angle = b.velocity.angleDeg();
		bullets.add(b);
	}

	public void emitExplosion(float x, float y, float radius) {
		Explosion b = Explosion.pool.obtain();
		b.position.set(x, y);
		b.radius = radius;
		b.time = 0;
		b.alive = true;
		explosions.add(b);
	}

	public void addSequence(GameSequence s) {
		sequences.add(s);
		s.create(this);
	}

	public void pushScore(Enemy enemy) {
		window.put(time, enemy.points);
	}

	public void resetScore() {
		window.clear();
	}
}
