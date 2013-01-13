package com.bzzup.fruityrain;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Enemy extends AnimatedSprite {
	
	private Body body;
	private float speed;
	private float health = 500f; 
	private boolean isUnderAttack = false;
	private boolean alive = true;
	private Enemy me; 

	public Enemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);

	}

	public Enemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.me = this;
		this.animate(200);

//		body = PhysicsFactory.createCircleBody(pWorld, this, BodyType.DynamicBody, Main.FIXTURE_DEF);
//		body.setUserData("enemy");
		GameScene scene = GameScene.getInstance();
		scene.registerTouchArea(this);
//		scene.getWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
		scene.attachChild(this);
		this.setStartSpeed(0.5f);
		
		GameScene.getInstance().addEnemyToTheWorld(this);
	}
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		if (!this.alive) {
			destroy();
		}
	}
	
	private void setStartSpeed(float mSpeed) {
		this.setSpeed(mSpeed);
		Vector2 direction = new Vector2(0, 10);
		direction.nor().mul(getSpeed());
//		body.applyLinearImpulse(direction, body.getPosition());
	}

	private float getSpeed() {
		return speed;
	}

	private void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void startMoving(Path traectory, int time) {
								
		this.registerEntityModifier(new PathModifier(time, traectory));
	}
	
	public Vector2 getMyCoordinates() {
		return new Vector2(this.getX(), this.getY());
	}
	
	public float getDistance(Player player) {
		return (float) Math.sqrt((Math.pow(Math.abs(player.getMyCoordinates().x - this.getMyCoordinates().x), 2) + Math.pow(Math.abs(player.getMyCoordinates().y - this.getMyCoordinates().y), 2)));
	}
	
	public void attack(boolean attack) {
		this.isUnderAttack = attack;
	}
	
	public boolean isUnderAttack() {
		return this.isUnderAttack;
	}
	
	public void hit(float damage) {
		if (this.alive) {this.health -= damage;}
		if (this.health < 0) {
			this.alive = false;
		}
	}
	
	public boolean isAlive() {
		return this.alive;
	}
	
	public void destroy() {
//		detachSelf();
		this.registerEntityModifier(new ScaleModifier(0.2f, 1f, 1.5f) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				me.setIgnoreUpdate(true);
				me.setVisible(false);
			}
		});
	}
}
