package com.bzzup.fruityrain.bullet;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;

public abstract class Bullet extends AnimatedSprite {
	private Body body;
	private Scene scene;
	private float damage;
	private boolean active;
	
	private PhysicsConnector physConnector;
	
	public Bullet(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, Scene mScene) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		body = PhysicsFactory.createCircleBody(GameScene.getInstance().getWorld(), this, BodyType.DynamicBody, ResourceManager.getInstance().FIXTURE_DEF_BULLET);
		body.setUserData(this);
		physConnector = new PhysicsConnector(this, body, true, true);
		GameScene.getInstance().getWorld().registerPhysicsConnector(physConnector);
		this.scene = mScene;
		this.scene.attachChild(this);
		body.setBullet(true);
		this.active = true;
	}
	
	protected void fire(Vector2 enemyPosition) {
		Vector2 direction = new Vector2((enemyPosition.x - getX()), (enemyPosition.y - getY()));
		enemyPosition.nor().mul(30f);
		body.applyLinearImpulse(direction, body.getPosition());
	}
	
	protected void setDamage(float dmg) {
		this.damage = dmg;
	}
	
	public float getDamage() {
		return this.damage;
	}
	
	public void inactivate() {
		this.active = false;
		this.setVisible(false);
		this.physConnector.setUpdatePosition(false);
		GameScene.getInstance().getWorld().unregisterPhysicsConnector(physConnector);
		this.setIgnoreUpdate(true);
	}
	
}
