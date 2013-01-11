package com.bzzup.fruityrain;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class BaloonPlayer extends AnimatedSprite {

	private boolean active = true;
	private Body body;
	private float speedX = 0.2f;
	private float speedY = 0.2f;

	public BaloonPlayer(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);

	}

	public BaloonPlayer(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.animate(200);

		body = PhysicsFactory.createCircleBody(pWorld, this, BodyType.DynamicBody, Main.FIXTURE_DEF);
		GameScene scene = GameScene.getInstance();
//		body.applyLinearImpulse(((float)5),(float) 5, body.getWorldCenter().x,  body.getWorldCenter().y);
		// body.applyLinearImpulse(new Vector2(0, -10), body.getWorldCenter());
		scene.registerTouchArea(this);
		scene.getWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
		scene.attachChild(this);
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
//		body.applyForce(new Vector2(0,-SensorManager.GRAVITY_EARTH), new Vector2(body.getWorldCenter()));
		float y = body.getLinearVelocity().y;
		float x = body.getLinearVelocity().x;
//		if (y > Math.abs(speedY)) {
////			body.setLinearVelocity(0, -100);
//			body.applyForce(0, y * -100, body.getWorldCenter().x, body.getWorldCenter().y);
//		}
//		if (x > Math.abs(speedX)) {
////			body.setLinearVelocity(x * -40, 0);
//			body.applyForce(x * -100, 0, body.getWorldCenter().x, body.getWorldCenter().y);
//		}
//		body.setLinearVelocity(speedX, speedY);
        
		super.onManagedUpdate(pSecondsElapsed * 0.1f);
	}

	public void inactivate() {
		this.active = false;
	}

	public boolean isActive() {
		return this.active;
	}

	public void setRedColor() {
		this.setColor(Color.RED);
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
			moveToXY(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);

			return true;
		}
		case TouchEvent.ACTION_DOWN: {
			moveToXY(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			return true;
		}
		default:
			break;
		}
		return false;
	}
	
	public void applyForce(float x, float y) {
		float vectorX = body.getLocalCenter().x - (body.getLocalCenter().x - x);
		float vectorY = body.getLocalCenter().y - (body.getLocalCenter().y - y);
		body.applyForce(vectorX, vectorY, body.getWorldCenter().x, body.getWorldCenter().y);
	
	}
}
