package com.bzzup.fruityrain;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
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
import com.bzzup.fruityrain.StateManager.StateType;
import com.bzzup.fruityrain.bullet.Bullet;
import com.bzzup.fruityrain.enemy.Enemy;
import com.bzzup.fruityrain.ship.Ship;
import com.bzzup.fruityrain.ship.ShipBattlecruiser;
import com.bzzup.fruityrain.ship.ShipDictionary;
import com.bzzup.fruityrain.ship.ShipFighter;
import com.bzzup.fruityrain.ship.ShipDictionary.Fighter;

public class GameScene extends Scene implements IOnSceneTouchListener, IUpdateHandler {

	public GameHUD gameHud;

	private float dropSpeed = 2f; // sec
	private final float touchArea = 100;

	private static GameScene gameScene;
	private PhysicsWorld mWorld;
	private Main mainActivity;
	public ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	public ArrayList<Ship> players = new ArrayList<Ship>();

	private float mGravityX;
	private float mGravityY;

	private static EnemyPool enemyPool;

	public static GameScene getInstance() {
		return gameScene;
	}

	public GameScene(Main mainActivity) {
		this.mainActivity = mainActivity;
		gameScene = this;
		this.gameHud = new GameHUD();
		this.mWorld = new PhysicsWorld(new Vector2(0, 0), false);
		this.setBackground(new Background(Color.BLACK));

		 mWorld.setContactListener(mContactListener);
		this.setOnSceneTouchListener(this);
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		this.registerUpdateHandler(sceneUpdateHandler);
		this.registerUpdateHandler(this.mWorld);
		enemyPool = new EnemyPool(ResourceManager.getInstance().baloonEnemy, this);
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
//			Fixture a = contact.getFixtureA();
//			Fixture b = contact.getFixtureB();
//			Object objectA = a.getBody().getUserData();
//			Object objectB = b.getBody().getUserData();
//			if (objectA instanceof Bullet) {
//				((Bullet) objectA).inactivate();
//			}
//			if (objectB instanceof Bullet) {
//				((Bullet) objectB).inactivate();
//			}
//			
//			if ((a.getBody().getUserData() == "bullet") || (b.getBody().getUserData() == "bullet")) {
//				removeBody(a.getBody());
//
//			}
			
		}
	};

	private void createWalls() {
		final VertexBufferObjectManager vertexBufferObjectManager = ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager();
		final Rectangle ground = new Rectangle(0, EngineOptionsManager.getInstance().CAMERA_HEIGHT - 5, EngineOptionsManager.getInstance().CAMERA_WIDTH, 5, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, EngineOptionsManager.getInstance().CAMERA_WIDTH, 5, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 5, EngineOptionsManager.getInstance().CAMERA_HEIGHT, vertexBufferObjectManager);
		final Rectangle right_top = new Rectangle(EngineOptionsManager.getInstance().CAMERA_WIDTH - 5, 0, 5, EngineOptionsManager.getInstance().CAMERA_HEIGHT, vertexBufferObjectManager);

		PhysicsFactory.createBoxBody(this.mWorld, ground, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL).setUserData("wall");
		PhysicsFactory.createBoxBody(this.mWorld, roof, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL).setUserData("wall");
		PhysicsFactory.createBoxBody(this.mWorld, left, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL).setUserData("wall");
		PhysicsFactory.createBoxBody(this.mWorld, right_top, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL).setUserData("wall");

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
				for (Ship player : this.players) {
					if ((Utils.Distance.calculateDistance(player.getCoordinates(), new Vector2(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()))) < this.touchArea) {
						// iPlayer.applyForce(pSceneTouchEvent.getX(),
						// pSceneTouchEvent.getY());
						player.reactOnTouchWave(new Vector2(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()));
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
//				Enemy iEnemy = enemyPool.obtainPoolItem();
				Enemy iEnemy = new Enemy(GameLevels.level1.getStartPoint().x, GameLevels.level1.getStartPoint().y, ResourceManager.getInstance().baloonPlayer, 
						ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(),
						GameScene.getInstance());
				iEnemy.startMoving(GameLevels.level1.getTraectory(), GameLevels.level1.getTime());
			}
		});
//		mainActivity.getEngine().registerUpdateHandler(timeHandler);
		ResourceManager.getInstance().getActivityReference().getEngine().registerUpdateHandler(timeHandler);
	}

	public void addEnemyToTheWorld(Enemy mEnemy) {
		enemiesList.add(mEnemy);
	}

	public void addPlayerToTheWorld(Ship mPlayer) {
		players.add(mPlayer);
	}

	private void removeBody(final Body mBody) {
		mainActivity.runOnUpdateThread(new Runnable() {

			@Override
			public void run() {
				PhysicsConnectorManager connectorManager = mWorld.getPhysicsConnectorManager();
				// Body enemy = connectorManager.
//				mWorld.destroyBody(mBody);
			}
		});
	}

	public PhysicsWorld getWorld() {
		return this.mWorld;
	}

	public ArrayList<Enemy> getAllEnemies() {
		return this.enemiesList;
	}

	public ArrayList<Ship> getAllPlayers() {
		return this.players;
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
		addShip(300, 300, ShipDictionary.ShipTypes.FIGHTER);
		addShip(400, 400, ShipDictionary.ShipTypes.CRUISER);
		createEnemiesWithPeriod();
		createWalls();
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

	private void addShip(float x, float y, int type) {
		switch (type) {
		case ShipDictionary.ShipTypes.FIGHTER: {
			players.add(new ShipFighter(x, y, this));
		}
			break;
		case ShipDictionary.ShipTypes.CRUISER: {
			players.add(new ShipBattlecruiser(x, y, this));
		}
			break;
		default:
			break;
		}
	}

}
