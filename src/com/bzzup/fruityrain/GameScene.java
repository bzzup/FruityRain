package com.bzzup.fruityrain;

import java.util.ArrayList;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bzzup.fruityrain.bullet.Bullet;
import com.bzzup.fruityrain.enemy.Enemy;
import com.bzzup.fruityrain.enemy.EnemyDictionary;
import com.bzzup.fruityrain.enemy.WaveController;
import com.bzzup.fruityrain.player.Player;
import com.bzzup.fruityrain.ship.Ship;
import com.bzzup.fruityrain.ship.ShipBattlecruiser;
import com.bzzup.fruityrain.ship.ShipDictionary;
import com.bzzup.fruityrain.ship.ShipFighter;

public class GameScene extends Scene implements IOnSceneTouchListener, IUpdateHandler {

	public GameHUD gameHud;

	private float dropSpeed = 2f; // sec
	private final float touchArea = 150;

	private static GameScene gameScene;
	private PhysicsWorld mWorld;
	private Main mainActivity;
	private ArrayList<Enemy> enemiesList = new ArrayList<Enemy>();
	private ArrayList<Ship> players = new ArrayList<Ship>();
	private ArrayList<Bullet> spriteBullets = new ArrayList<Bullet>();

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

		// mWorld.setContactListener(mContactListener);
		this.setOnSceneTouchListener(this);
		setTouchAreaBindingOnActionDownEnabled(true);
		setTouchAreaBindingOnActionMoveEnabled(true);
		this.registerUpdateHandler(sceneUpdateHandler);
		this.registerUpdateHandler(this.mWorld);
		enemyPool = new EnemyPool(ResourceManager.getInstance().baloonEnemy, this);
	}

	public GameHUD getHUD() {
		return this.gameHud;
	}

	private void createWalls() {
		final VertexBufferObjectManager vertexBufferObjectManager = ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager();
		final Rectangle ground = new Rectangle(0, ResolutionManager.getInstance().getCameraHeight() - 5, ResolutionManager.getInstance().getCameraWidth(), 5, vertexBufferObjectManager);
		final Rectangle roof = new Rectangle(0, 0, ResolutionManager.getInstance().getCameraWidth(), 5, vertexBufferObjectManager);
		final Rectangle left = new Rectangle(0, 0, 5, ResolutionManager.getInstance().getCameraHeight(), vertexBufferObjectManager);
		final Rectangle right_top = new Rectangle(ResolutionManager.getInstance().getCameraWidth() - 5, 0, 5, ResolutionManager.getInstance().getCameraHeight(), vertexBufferObjectManager);

		PhysicsFactory.createBoxBody(this.mWorld, ground, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL);
		PhysicsFactory.createBoxBody(this.mWorld, roof, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL);
		PhysicsFactory.createBoxBody(this.mWorld, left, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL);
		PhysicsFactory.createBoxBody(this.mWorld, right_top, BodyType.StaticBody, ResourceManager.getInstance().FIXTURE_DEF_WALL);

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
			ResourceManager.getInstance().getActivityReference().runOnUpdateThread(new Runnable() {

				@Override
				public void run() {
					try {
						updateHUD();
						for (Enemy mEnemy : enemiesList) {
							if (!mEnemy.isAlive()) {
								mEnemy.detachSelf();
							}
						}
						for (Bullet mBullet : spriteBullets) {
							if (!mBullet.isAlive()) {
								mBullet.detachSelf();
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});

		}
	};

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		if (this.mWorld != null) {
			if (pSceneTouchEvent.isActionDown()) {
				for (Ship player : this.players) {
					if ((Utils.Distance.calculateDistance(player.getCoordinates(), new Vector2(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()))) < this.touchArea) {
						player.reactOnTouchWave(new Vector2(pSceneTouchEvent.getX(), pSceneTouchEvent.getY()));
					}
				}
				return true;
			}
		}
		return false;
	}

	public void addBulletToArray(Bullet bullet) {
		spriteBullets.add(bullet);
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
		this.attachChild(gameHud.getIntroText());
		gameHud.getIntroTextModifier().reset();
	}

	public void cleanupAfterIntro() {
		// remove intro text
		this.detachChild(gameHud.getIntroText());
	}

	public void prepareSceneForPlay() {
		addShip(300, 300, ShipDictionary.ShipTypes.FIGHTER);
		// addShip(400, 400, ShipDictionary.ShipTypes.CRUISER);
		// createEnemiesWithPeriod();
		WaveController waveControl = new WaveController();
		createWalls();
		Road road = new Road();
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

	public void addShip(float x, float y, int type) {
		switch (type) {
		case ShipDictionary.ShipTypes.FIGHTER: {
			players.add(new ShipFighter(x, y, this));
			Player.Money.addMoney(-ShipDictionary.Fighter.cost);
		}
			break;
		case ShipDictionary.ShipTypes.CRUISER: {
			players.add(new ShipBattlecruiser(x, y, this));
			Player.Money.addMoney(-ShipDictionary.Cruiser.cost);
		}
			break;
		default:
			break;
		}
	}

	public void addEnemy(final float x, final float y, final int type) {
		TimerHandler enemy_handler = new TimerHandler(2, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				switch (type) {
				case EnemyDictionary.mob1: {
					enemiesList.add(new Enemy(x, y, ResourceManager.getInstance().baloonEnemy, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(), GameScene
							.getInstance()));
				}
					break;
				case EnemyDictionary.mob2: {
					enemiesList.add(new Enemy(x, y, ResourceManager.getInstance().baloonEnemy, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(), GameScene
							.getInstance()));
				}
					break;
				default:
					break;
				}

			}
		});
		ResourceManager.getInstance().getActivityReference().getEngine().registerUpdateHandler(enemy_handler);
		// switch (type) {
		// case EnemyDictionary.mob1: {
		// enemiesList.add(new Enemy(x, y,
		// ResourceManager.getInstance().baloonEnemy,
		// ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(),
		// GameScene.getInstance()));
		// }
		// break;
		// case EnemyDictionary.mob2: {
		// enemiesList.add(new Enemy(x, y,
		// ResourceManager.getInstance().baloonEnemy,
		// ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(),
		// GameScene.getInstance()));
		// }
		// break;
		// default:
		// break;
		// }
	}

	private void updateHUD() {
		gameHud.updateMoney(Player.Money.getTotalMoney());
	}

}
