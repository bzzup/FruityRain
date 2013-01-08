package com.bzzup.fruityrain;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
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
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class GameScene extends Scene implements IOnSceneTouchListener {

	private float dropSpeed = 1f; // sec

	public GameScene gameScene;
	private Camera mCamera;
	private PhysicsWorld mWorld;
	private Main mainActivity;

	public GameScene getInstance() {
		if (gameScene == null) {
			gameScene = new GameScene();
		}
		return gameScene;
	}

	public GameScene() {
		mainActivity = Main.getInstance();
		this.setBackground(new Background(Color.BLACK));
		mWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH), false);
		this.setOnSceneTouchListener(this);

		final VertexBufferObjectManager vertexBufferObjectManager = mainActivity.getVertexBufferObjectManager();
		final Rectangle ground = new Rectangle(0, mainActivity.CAMERA_HEIGHT - 5, mainActivity.CAMERA_WIDTH, 5, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, mainActivity.CAMERA_WIDTH, 5, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 5, mainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right = new Rectangle(mainActivity.CAMERA_WIDTH - 5, 0, 5, mainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle shelf = new Rectangle(300, 200, 100, 2, vertexBufferObjectManager);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		PhysicsFactory.createBoxBody(this.mWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, right, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, shelf, BodyType.StaticBody, wallFixtureDef);

		this.attachChild(ground);
		this.attachChild(roof);
		this.attachChild(left);
		this.attachChild(right);

		this.registerUpdateHandler(this.mWorld);
		
		createFaceWithPeriod();
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (this.mWorld != null) {
			if (pSceneTouchEvent.isActionDown()) {
//				this.addFace(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
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

		face = new AnimatedSprite(pX, pY, mainActivity.mCircleFaceTextureRegion, mainActivity.getVertexBufferObjectManager());
		body = PhysicsFactory.createCircleBody(mWorld, face, BodyType.DynamicBody, mainActivity.FIXTURE_DEF);

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
		this.mWorld.registerPhysicsConnector(new PhysicsConnector(face, body, true, true));
		
	}
	
	private void createFaceWithPeriod() {
		TimerHandler timeHandler;
		timeHandler = new TimerHandler(dropSpeed, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				addFaceAtRandomPosition();
				
			}
		});
		mainActivity.getEngine().registerUpdateHandler(timeHandler);
	}

	private void addFaceAtRandomPosition() {
		final AnimatedSprite face;
		final Body body;

		face = new AnimatedSprite(new Random().nextInt(mainActivity.CAMERA_WIDTH - 10) + 5, 10, mainActivity.mCircleFaceTextureRegion,
				mainActivity.getVertexBufferObjectManager()){
			
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				this.detachSelf();

				return mFlippedHorizontal;};
		};
		
		body = PhysicsFactory.createCircleBody(mWorld, face, BodyType.DynamicBody, Main.FIXTURE_DEF);
		face.animate(200);

		this.attachChild(face);
		this.mWorld.registerPhysicsConnector(new PhysicsConnector(face, body, true, true));
		this.registerTouchArea(face);
	}


	
	

}
