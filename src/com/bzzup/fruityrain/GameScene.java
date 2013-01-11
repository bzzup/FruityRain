package com.bzzup.fruityrain;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
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
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class GameScene extends Scene implements IOnSceneTouchListener, IUpdateHandler {

	private float dropSpeed = 1f; // sec

	private static GameScene gameScene;
	private Camera mCamera;
	private PhysicsWorld mWorld;
	private Main mainActivity;
	private BaloonPlayer player;

	public static GameScene getInstance() {
		if (gameScene == null) {
			gameScene = new GameScene();
		}
		return gameScene;
	}

	public GameScene() {
		mainActivity = Main.getInstance();
		gameScene = this;
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

		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);

		this.registerUpdateHandler(sceneUpdateHandler);

		this.registerUpdateHandler(this.mWorld);
		addFaceAtRandomPosition();
	}

	IUpdateHandler sceneUpdateHandler = new IUpdateHandler() {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (this.mWorld != null) {
			if (pSceneTouchEvent.isActionDown()) {
				player.applyForce(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
				return true;
			}
		}
		return false;
	}

	private void addFace(final float pX, final float pY) {
		BaloonPlayer mBaloon = new BaloonPlayer(pX, pY, mainActivity.mCircleFaceTextureRegion, mainActivity.getVertexBufferObjectManager(), mWorld);

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
//		final BaloonPlayer face;
		// final Body body;

		player = new BaloonPlayer(200, 200, mainActivity.mCircleFaceTextureRegion, mainActivity.getVertexBufferObjectManager(), mWorld);
		// {
		//
		// @Override
		// public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float
		// pTouchAreaLocalX, float pTouchAreaLocalY) {
		// moveToXY(this, pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
		// return true;
		// };
		//
		// };

		// body = PhysicsFactory.createCircleBody(mWorld, face,
		// BodyType.DynamicBody, Main.FIXTURE_DEF);
		// face.animate(200);
	}

	private void removeBall(final BaloonPlayer iBall) {
		mainActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				PhysicsConnector physConnector = mWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(iBall);
				mWorld.destroyBody(physConnector.getBody());
				mWorld.unregisterPhysicsConnector(physConnector);
				iBall.detachSelf();
			}
		});
	}

	private void explode(final BaloonPlayer iBall) {
		iBall.registerEntityModifier(new ScaleModifier(0.2f, 1, 2) {

			@Override
			protected void onModifierFinished(IEntity pItem) {
				removeBall(iBall);
				super.onModifierFinished(pItem);
			}
		});
	}
	
	public PhysicsWorld getWorld() {
		return this.mWorld;
	}

	// private void moveToXY(final Ball iBall, float x, float y) {
	// iBall.setPosition(x - iBall.getWidth()/2, y - iBall.getHeight()/2);
	// }

}
