package net.mgsx.dl15.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Assets i;
	public Texture bgNatural;
	public TextureAtlas atlas;
	public TextureRegion [] enBullets;
	public TextureRegion [] shipBullets;
	public TextureRegion [] enFighters;
	public TextureRegion [] explosions;
	public AtlasRegion moduleSprite;
	
	public Assets() {
		bgNatural = new Texture(Gdx.files.internal("sprites/bg-natural.png"));
		bgNatural.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		
		atlas = new TextureAtlas(Gdx.files.internal("spritesheet.atlas"));
		
		enBullets = new TextureRegion[4];
		enBullets[0] = atlas.findRegion("en-bullet-1");
		enBullets[1] = atlas.findRegion("en-bullet-2");
		enBullets[2] = atlas.findRegion("en-bullet-3");
		enBullets[3] = atlas.findRegion("en-bullet-4");
		
		shipBullets = new TextureRegion[3];
		shipBullets[0] = atlas.findRegion("shot-blue-sm");
		shipBullets[1] = atlas.findRegion("shot-blue-md");
		shipBullets[2] = atlas.findRegion("shot-blue-lg");

		enFighters = new TextureRegion[3];
		enFighters[0] = atlas.findRegion("en-red-a-1");
		enFighters[1] = atlas.findRegion("en-red-a-2");
		enFighters[2] = atlas.findRegion("en-red-a-3");
		
		moduleSprite = atlas.findRegion("module");
		
		int nbExp = 9;
		explosions = new TextureRegion[nbExp];
		for(int i=0 ; i<nbExp ; i++){
			explosions[i] = atlas.findRegion("exp" + (i+1));
		}
		
	}
}
