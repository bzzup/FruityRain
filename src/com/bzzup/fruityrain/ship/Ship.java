package com.bzzup.fruityrain.ship;

import org.andengine.audio.music.Music;
import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.bzzup.fruityrain.FireLine;
import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.MovementCoolDown;
import com.bzzup.fruityrain.ResourceManager;
import com.bzzup.fruityrain.bullet.FighterBullet;
import com.bzzup.fruityrain.enemy.Enemy;

public abstract class Ship extends AnimatedSprite {

	protected final int MAX_LVL = 5;
	
	protected ScaleModifier scaleModifier;
	protected PhysicsHandler physicsHandler;
	private ITiledTextureRegion texture;
	private Scene scene;
	private Body body;

	private float currentCoordinatesX;
	private float currentCoordinatesY;

	private float speed = 1f;
	private long fireSpeed = 200;
	private float damage = 100f;
	private float fireDistance = 200f;
	private long hitsCount = 0;
	private float linearDamping;
	private long expToLevel;
	
	private long exp;
	private int currentLevel = 0;

	private ShootingCoolDown shootingCoolDown;
	private MovementCoolDown breakCoolDown;
	private Music shot;
	
	private boolean drawDistance = false;

	// /REMOVE
	FireLine fireLine;

	public Ship(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager vertexBufferObjectManager, Scene mScene) {
		super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);
		body = PhysicsFactory.createCircleBody(GameScene.getInstance().getWorld(), this, BodyType.DynamicBody, ResourceManager.getInstance().FIXTURE_DEF_SHIP);

		GameScene.getInstance().getWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
		this.scene = mScene;
		this.animate(200);
		
		this.currentCoordinatesX = pX;
		this.currentCoordinatesY = pY;
		this.setTexture(pTiledTextureRegion);
		this.shootingCoolDown = new ShootingCoolDown(fireSpeed);
		this.breakCoolDown = new MovementCoolDown(200);

		this.scene.registerTouchArea(this);
		this.scene.attachChild(this);
	}

	protected float getFireDistance() {
		return fireDistance;
	}

	protected void setFireDistance(float fireDistance) {
		this.fireDistance = fireDistance;
	}

	public Vector2 getCoordinates() {
		return new Vector2(this.getX(), this.getY());
	}

	protected void setCoordinates(Vector2 vector) {
		this.currentCoordinatesX = vector.x;
		this.currentCoordinatesY = vector.y;
	}

	protected float getSpeed() {
		return speed;
	}

	protected void setSpeed(float speed) {
		this.speed = speed;
	}

	protected long getFireSpeed() {
		return fireSpeed;
	}

	protected void setFireSpeed(long fireSpeed) {
		this.fireSpeed = fireSpeed;
	}

	public float getDamage() {
		return damage;
	}

	protected void setDamage(float damage) {
		this.damage = damage;
	}

	protected void resetHitsCount() {
		this.hitsCount = 0;
	}

	protected void addHitsCount() {
		this.hitsCount++;
	}

	protected long getHitsCount() {
		return this.hitsCount;
	}

	protected ITiledTextureRegion getTexture() {
		return texture;
	}

	protected void setTexture(ITiledTextureRegion texture) {
		this.texture = texture;
	}

	public void recalculateEnemiesDistances() {
		for (Enemy mEnemy : GameScene.getInstance().getAllEnemies()) {
			if ((mEnemy.isAlive() && (mEnemy.getDistance(this)) < this.fireDistance)) {
				if (shootingCoolDown.checkValidity()) {
					fireBullet(mEnemy);
				}
			}
		}

	}

	private void fireBullet(Enemy mEnemy) {
//		mEnemy.hit(damage);
//		new Bullet(currentCoordinatesX, currentCoordinatesY, getTiledTextureRegion(), getVertexBufferObjectManager(), scene).fire(mEnemy.getMyCoordinates());
//		GameScene.getInstance().addBulletToArray(new FighterBullet(this, mEnemy.getMyCoordinates(), scene));
		GameScene.getInstance().addBulletToArray(new FighterBullet(this, mEnemy, scene));
		this.playShot();
	}

	@SuppressWarnings("unused")
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		float currentX = pTouchAreaLocalX;
		float currentY = pTouchAreaLocalY;
//		drawDistance = true;
		return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		recalculateEnemiesDistances();
		autoSlowBody();
		if (drawDistance) {
			drawDistanceRadius();
		}
	}
	
	private void drawDistanceRadius() {
		CircleShape circle = new CircleShape();
		circle.setPosition(this.getCoordinates());
		circle.setRadius(this.getFireDistance());
	}

	public void reactOnTouchWave(Vector2 waveVector) {
		float currentX = this.getX();
		float currentY = this.getY();
		Vector2 direction = new Vector2((currentX - waveVector.x), (currentY - waveVector.y));
		direction.nor().mul(this.speed);
		body.applyLinearImpulse(direction, body.getPosition());
	}

	protected float getLinearDamping() {
		return this.linearDamping;
	}

	protected void setLinearDamping(float linearDamping) {
		this.linearDamping = linearDamping;
		body.setLinearDamping(1.5f);
	}

	protected void level(AttributesDictionary attr) {
		this.speed = attr.getSpeed();
		this.damage = attr.getDamage();
		this.fireDistance = attr.getFireDistance();
		this.fireSpeed = attr.getFireSpeed();
		this.linearDamping = attr.getLinerDamping();
		this.linearDamping = 500;
		this.expToLevel = attr.getExpToNextLevel();
		this.currentLevel++;
		expReset();
	}

	protected long getExp() {
		return exp;
	}

	private void expReset() {
		this.exp = 0;
	}
	
	protected void increaseExp(long count) {
		this.exp += count;
	}
	
	protected int getCurrentLevel() {
		return currentLevel;
	}

	protected void setShotSound(Music shot) {
		this.shot = shot;
		shot.setLooping(false);
	}
	
	private void playShot() {
		if (!shot.isPlaying()) {
			shot.play();
		}
	}
	
	private void autoSlowBody() {
		if ((body.getLinearVelocity().x != 0) || (body.getLinearVelocity().y != 0)) {
			if (breakCoolDown.checkValidity()) {
				Vector2 slowDirection = new Vector2(-body.getLinearVelocity().x, -body.getLinearVelocity().y);
				slowDirection.nor().mul(0.5f);
				body.applyLinearImpulse(slowDirection, body.getPosition());
			}
		}
		if (Math.abs(body.getLinearVelocity().x) < 0.1f) {
			body.setLinearVelocity(0, body.getLinearVelocity().y);
		}
		if (Math.abs(body.getLinearVelocity().y) < 0.1f) {
			body.setLinearVelocity(body.getLinearVelocity().y, 0);
		}

	}
	
}
