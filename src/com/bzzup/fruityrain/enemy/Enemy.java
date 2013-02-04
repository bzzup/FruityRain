package com.bzzup.fruityrain.enemy;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.bzzup.fruityrain.GameLevels;
import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;
import com.bzzup.fruityrain.player.Player;
import com.bzzup.fruityrain.ship.Ship;

public class Enemy extends AnimatedSprite {
	protected ScaleModifier scaleModifier;
	protected PhysicsHandler physicsHandler;
	private ITiledTextureRegion texture;
	private Scene scene;
	private Body body;
	private long regard;

	private float currentCoordinatesX;
	private float currentCoordinatesY;

	private boolean isAlive = true;
	private long currentHealth;
	private long maxHealth;
	
	private Rectangle healthBar;

	public Enemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager vertexBufferObjectManager, Scene mScene) {
		super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);
//		body = PhysicsFactory.createCircleBody(GameScene.getInstance().getWorld(), this, BodyType.DynamicBody, ResourceManager.getInstance().FIXTURE_DEF_ENEMY);
//		body.setUserData(this);
//		GameScene.getInstance().getWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));

		this.scene = mScene;
		this.animate(200);

		this.currentCoordinatesX = pX;
		this.currentCoordinatesY = pY;
		this.setTexture(pTiledTextureRegion);

		this.scene.registerTouchArea(this);
		this.scene.attachChild(this);
		// GameScene.getInstance().addEnemyToTheWorld(this);
		startMoving(GameLevels.level1.getTraectory(), GameLevels.level1.getTime());
		setHealth(50);
		setRegard(100);
		
		healthBar = new Rectangle(getMyCoordinates().x-10, getMyCoordinates().y+10, 30, 5, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
		healthBar.setColor(Color.GREEN);
		this.scene.attachChild(healthBar);
	}

	protected void setTexture(ITiledTextureRegion texture) {
		this.texture = texture;
	}

	public boolean isAlive() {
		return isAlive;
	}

	protected void kill() {
		this.isAlive = false;
	}

	protected long getHealth() {
		return currentHealth;
	}

	protected void setHealth(long health) {
		this.currentHealth = health;
		this.maxHealth = health;
	}

	public void hitByDmg(float dmg) {
		this.currentHealth -= dmg;
	}
	
	public Vector2 getMyCoordinates() {
		return new Vector2(this.getX(), this.getY());
	}

	public float getDistance(Ship ship) {
		return (float) Math.sqrt((Math.pow(Math.abs(ship.getCoordinates().x - this.getX()), 2) + Math.pow(Math.abs(ship.getCoordinates().y - this.getY()), 2)));
	}

	public void startMoving(Path traectory, int time) {
		this.registerEntityModifier(new EnemyPathModifier(time, traectory) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				kill();
				super.onModifierFinished(pItem);
			}
		});
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		if (this.currentHealth < 0) {
			this.isAlive = false;
			Player.Money.addMoney(this.regard);
		}
		healthBar.setPosition(getMyCoordinates().x-10, getMyCoordinates().y+10);
		healthBar.setWidth(currentHealth/maxHealth * 20);
		super.onManagedUpdate(pSecondsElapsed);
	}

	protected long getRegard() {
		return regard;
	}

	protected void setRegard(long regard) {
		this.regard = regard;
	}

	
	
}
