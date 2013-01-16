package com.bzzup.fruityrain;

import java.util.ArrayList;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnectorManager;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;

public class GameScene extends Scene implements IOnSceneTouchListener, IUpdateHandler {

	public GameHUD gameHud;

	private float dropSpeed = 2f; // sec
	private final float touchArea = 100;

	private static GameScene gameScene;
	private Camera mCamera;
	private PhysicsWorld mWorld;
	private Main mainActivity;
	private Player player;
	public ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	public ArrayList<Player> playersList = new ArrayList<Player>();

	private float mGravityX;
	private float mGravityY;

	private static EnemyPool enemyPool;

	public static GameScene getInstance() {
		// if (gameScene == null) {
		// gameScene = new GameScene();
		// }
		return gameScene;
	}

	public GameScene(Main mainActivity) {
		this.mainActivity = mainActivity;
		gameScene = this;
		this.gameHud = new GameHUD();

		this.setBackground(new Background(Color.BLACK));
		mWorld = new PhysicsWorld(new Vector2(0, 0), false);
		// mWorld.setContactListener(mContactListener);
		this.setOnSceneTouchListener(this);
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		this.registerUpdateHandler(sceneUpdateHandler);
		this.registerUpdateHandler(this.mWorld);

		addPlayerAtPosition(300, 300);
		addPlayerAtPosition(350, 350);
		addPlayerAtPosition(250, 250);
		createEnemiesWithPeriod();
		enemyPool = new EnemyPool(ResourceManager.getInstance().baloonEnemy, this.mWorld);
	}

	ContactListener mContactListener = new ContactListener() {

		@Override
		public void preSolve(Contact contact, Manifold oldManifold) {

		}

		@Override
		public void postSolve(Contact contact, ContactImpulse impulse) {
			// TODO Auto-generated method stub

		}

		@Override
		public void endContact(Contact contact) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beginContact(Contact contact) {
			Fixture a = contact.getFixtureA();
			Fixture b = contact.getFixtureB();
			if ((a.getBody().getUserData() == "enemy") && (b.getBody().getUserData() == "player")) {
				removeBody(a.getBody());
			}
			if ((a.getBody().getUserData() == "player") && (b.getBody().getUserData() == "enemy")) {
				removeBody(b.getBody());
			}
		}
	};

	private void createWalls() {
		final VertexBufferObjectManager vertexBufferObjectManager = mainActivity.getVertexBufferObjectManager();
		final Rectangle ground = new Rectangle(0, mainActivity.CAMERA_HEIGHT - 5, mainActivity.CAMERA_WIDTH, 5, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, mainActivity.CAMERA_WIDTH, 5, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 5, mainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right_top = new Rectangle(mainActivity.CAMERA_WIDTH - 5, 0, 5, mainActivity.CAMERA_HEIGHT, vertexBufferObjectManager);

		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
		PhysicsFactory.createBoxBody(this.mWorld, ground, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, roof, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, left, BodyType.StaticBody, wallFixtureDef);
		PhysicsFactory.createBoxBody(this.mWorld, right_top, BodyType.StaticBody, wallFixtureDef);

		this.attachChild(ground);
		this.attachChild(roof);
		this.attachChild(left);
		this.attachChild(right_top);

	}

	IUpdateHandler sceneUpdateHandler = new IUpdateHandler() {

		@Override
		public void reset() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onUpdate(float pSecondsElapsed) {
			// synchronized (enemiesList) {
			// for (Enemy mEnemy : enemiesList) {
			// if (!mEnemy.isAlive()) {
			// mEnemy.destroy();
			// }
			// }
			// }
		}
	};

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (this.mWorld != null) {
			if (pSceneTouchEvent.isActionDown()) {
				for (Player iPlayer : playersList) {
					if ((Utils.Distance.calculateDistance(iPlayer.getMyCoordinates(), new Vector2(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()))) < this.touchArea) {
						iPlayer.applyForce(pSceneTouchEvent.getX(), pSceneTouchEvent.getY());
					}
				}
				return true;
			}
		}
		return false;
	}

	private void createEnemiesWithPeriod() {
		TimerHandler timeHandler;

		timeHandler = new TimerHandler(dropSpeed, true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				Enemy iEnemy = enemyPool.obtainPoolItem();
				iEnemy.startMoving(GameLevels.level1.getTraectory(), GameLevels.level1.getTime());
			}
		});
		mainActivity.getEngine().registerUpdateHandler(timeHandler);
	}

	public void addEnemyToTheWorld(Enemy mEnemy) {
		enemiesList.add(mEnemy);
	}

	public void addPlayerToTheWorld(Player mPlayer) {
		playersList.add(mPlayer);
	}

	private void addPlayerAtPosition(float x, float y) {
		player = new Player(x, y, ResourceManager.getInstance().baloonPlayer, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
	}

	private void removeBody(final Body mBody) {
		mainActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				PhysicsConnectorManager connectorManager = mWorld.getPhysicsConnectorManager();
				// Body enemy = connectorManager.
				mWorld.destroyBody(mBody);
			}
		});
	}

	public PhysicsWorld getWorld() {
		return this.mWorld;
	}

	public ArrayList<Enemy> getAllEnemies() {
		return this.enemiesList;
	}

	public ArrayList<Player> getAllPlayers() {
		return this.playersList;
	}

	public void addItemToGameShop(AnimatedSprite aItem) {

	}

	public void prepareSceneForIntro() {
		// add countdown text
		createWalls();
		this.attachChild(gameHud.getIntroText());
		gameHud.getIntroTextModifier().reset();
	}

	public void cleanupAfterIntro() {
		// remove intro text
		this.detachChild(gameHud.getIntroText());
	}

	public void prepareSceneForPlay() {

		// this.attachChild(choppaJoe.getAnimatedSprite());
		// this.setChildScene(choppaJoeControlla.getAnalogOnScreenControl());
		EngineOptionsManager.getInstance().getCamera().setHUD(gameHud.getHud());

	}

	public void cleanupAfterPlay() {
		this.detachChildren();
		this.clearChildScene();
		// choppaJoe.getScaleModifier().reset();
		EngineOptionsManager.getInstance().getCamera().setHUD(null);
	}

}
