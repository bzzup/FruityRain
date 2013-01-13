package com.bzzup.fruityrain;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends AnimatedSprite {

	private Body body;
	private float accelerationSpeed = 1f;
	private float breakSpeed = 0.1f;
	private float fireDistance = 200f;
	private float damage = 100f;
	private float fireSpeed = 200;
	private FireLine fireLine;
	private ShootingCoolDown coolDown;
	private MovementCoolDown moveCoolDown;

	public Player(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.animate(200);
		GameScene scene = GameScene.getInstance();
		body = PhysicsFactory.createCircleBody(scene.getWorld(), this, BodyType.DynamicBody, Main.FIXTURE_DEF);
		body.setUserData("player");

		scene.registerTouchArea(this);
		scene.getWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
		scene.attachChild(this);
		GameScene.getInstance().addPlayerToTheWorld(this);
		coolDown = new ShootingCoolDown(200);
		moveCoolDown = new MovementCoolDown(1000);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		super.onManagedUpdate(pSecondsElapsed);
		autoSlowBody();
		recalculateEnemiesDistances();
	}

	public Body getBody() {
		return this.body;
	}

	private void moveToXY(float x, float y) {
		this.setPosition(x, y);

		this.body.setTransform(x, y, 0);
		// this.setPosition(x - this.getWidth()/2, y - this.getHeight()/2);
		// this.body.setTransform(x - this.getWidth()/2, y - this.getHeight()/2,
		// 0);
	}

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_MOVE: {
			// moveToXY(pSceneTouchEvent.getX() /
			// PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
			// pSceneTouchEvent.getY() /
			// PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);

			return true;
		}
		case TouchEvent.ACTION_DOWN: {
			// moveToXY(pSceneTouchEvent.getX() /
			// PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT,
			// pSceneTouchEvent.getY() /
			// PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			return true;
		}
		default:
			break;
		}
		return false;
	}

	public void applyForce(float x, float y) {
		float currentX = this.getX();
		float currentY = this.getY();
		Vector2 direction = new Vector2((currentX - x), (currentY - y));
		direction.nor().mul(getSpeed());
		body.applyLinearImpulse(direction, body.getPosition());
	}

	private void autoSlowBody() {
		if ((body.getLinearVelocity().x != 0) || (body.getLinearVelocity().y != 0)) {
			if (moveCoolDown.checkValidity()) {
				Vector2 slowDirection = new Vector2(-body.getLinearVelocity().x, -body.getLinearVelocity().y);
				slowDirection.nor().mul(this.getBreakSpeed());
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

	private float getSpeed() {
		return accelerationSpeed;
	}

	private void setSpeed(float speed) {
		this.accelerationSpeed = speed;
	}

	public Vector2 getMyCoordinates() {
		return new Vector2(this.getX(), this.getY());
	}

	public void recalculateEnemiesDistances() {
		for (Enemy mEnemy : GameScene.getInstance().getAllEnemies()) {
			if ((mEnemy.isAlive() && (mEnemy.getDistance(this)) < fireDistance)) {
				if (coolDown.checkValidity()) {
					Log.d("MY", "FIRE!!!");
					fireBullet(mEnemy);
				}
			}
		}

	}

	private void fireBullet(Enemy mEnemy) {
		if (fireLine != null) {
			fireLine.detachSelf();
			fireLine = null;
		}
		mEnemy.hit(damage);
		fireLine = new FireLine(getMyCoordinates().x + 10, getMyCoordinates().y + 10, mEnemy.getMyCoordinates().x + 15, mEnemy.getMyCoordinates().y + 15, Main.getInstance()
				.getVertexBufferObjectManager());
		fireLine.setColor(Color.WHITE);
		GameScene.getInstance().attachChild(fireLine);
	}

	private float getBreakSpeed() {
		return breakSpeed;
	}

	private void setBreakSpeed(float breakSpeed) {
		this.breakSpeed = breakSpeed;
	}

}
