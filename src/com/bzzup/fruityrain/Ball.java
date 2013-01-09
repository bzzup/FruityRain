package com.bzzup.fruityrain;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Ball extends AnimatedSprite{

	private boolean active = true;
	private Body body;

	public Ball(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);

	}
	
	public Ball(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, PhysicsWorld pWorld) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		body = PhysicsFactory.createCircleBody(pWorld, this, BodyType.DynamicBody, Main.FIXTURE_DEF);
		this.animate(200);
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
		this.body.setTransform(x, y , 0);
//		this.setPosition(x - this.getWidth()/2, y - this.getHeight()/2);
//		this.body.setTransform(x - this.getWidth()/2, y - this.getHeight()/2, 0);
	}
	
	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		switch (pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_MOVE:
		{
			moveToXY(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			
			return true;
		}	
		case TouchEvent.ACTION_DOWN:
		{
			moveToXY(pSceneTouchEvent.getX() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, pSceneTouchEvent.getY() / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			return true;
		}
		default:
			break;
		}
		return false;
	}
}
