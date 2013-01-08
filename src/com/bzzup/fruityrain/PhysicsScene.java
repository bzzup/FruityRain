package com.bzzup.fruityrain;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsScene extends Scene implements IOnSceneTouchListener {

	public PhysicsScene physScene;
	private Camera mCamera;
	private PhysicsWorld mWorld;
	private Main mainActivity;

	public PhysicsScene getInstance() {
		if (physScene == null) {
			physScene = new PhysicsScene();
		}
		return physScene;
	}

	public PhysicsScene() {
		mainActivity = Main.getInstance();
		this.setBackground(new Background(Color.BLACK));
		mWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH),
				false);
		this.setOnSceneTouchListener(this);

		final VertexBufferObjectManager vertexBufferObjectManager = mainActivity
				.getVertexBufferObjectManager();
		final Rectangle ground = new Rectangle(0,
				mainActivity.CAMERA_HEIGHT - 5, mainActivity.CAMERA_WIDTH, 5,
				vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, mainActivity.CAMERA_WIDTH,
				5, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 5,
				mainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right = new Rectangle(mainActivity.CAMERA_WIDTH - 5, 0,
				5, mainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle shelf = new Rectangle(300, 200, 100, 2,
				vertexBufferObjectManager);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0,
				0.5f, 0.5f);
		PhysicsFactory.createBoxBody(this.mWorld, ground, BodyType.StaticBody,
				wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, roof, BodyType.StaticBody,
				wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, left, BodyType.StaticBody,
				wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, right, BodyType.StaticBody,
				wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, shelf, BodyType.StaticBody,
				wallFixtureDef);

		this.attachChild(ground);
		this.attachChild(roof);
		this.attachChild(left);
		this.attachChild(right);

		this.registerUpdateHandler(this.mWorld);
		
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (this.mWorld != null) {
			if (pSceneTouchEvent.isActionDown()) {
				this.addFace(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				return true;
			}
		}
		return false;
	}

	private void addFace(final float pX, final float pY) {
		// this.mFaceCount++;
		// Debug.d("Faces: " + this.mFaceCount);

		final AnimatedSprite face;
		final Body body;

		face = new AnimatedSprite(pX, pY,
				mainActivity.mCircleFaceTextureRegion,
				mainActivity.getVertexBufferObjectManager());
		body = PhysicsFactory.createCircleBody(mWorld, face,
				BodyType.DynamicBody, mainActivity.FIXTURE_DEF);

		// if(this.mFaceCount % 4 == 0) {
		// face = new AnimatedSprite(pX, pY, this.mBoxFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// body = PhysicsFactory.createBoxBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// } else if (this.mFaceCount % 4 == 1) {
		// face = new AnimatedSprite(pX, pY, this.mCircleFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// body = PhysicsFactory.createCircleBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// } else if (this.mFaceCount % 4 == 2) {
		// face = new AnimatedSprite(pX, pY, this.mTriangleFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// body = MainActivity.createTriangleBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// } else {
		// face = new AnimatedSprite(pX, pY, this.mHexagonFaceTextureRegion,
		// this.getVertexBufferObjectManager());
		// body = MainActivity.createHexagonBody(this.mPhysicsWorld, face,
		// BodyType.DynamicBody, FIXTURE_DEF);
		// }

		face.animate(200);

		this.attachChild(face);
		this.mWorld.registerPhysicsConnector(new PhysicsConnector(face, body,
				true, true));

	}

}
