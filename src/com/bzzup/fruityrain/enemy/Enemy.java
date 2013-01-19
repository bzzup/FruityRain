package com.bzzup.fruityrain.enemy;

import org.andengine.engine.handler.physics.PhysicsHandler;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;
import com.bzzup.fruityrain.ship.Ship;

public class Enemy extends AnimatedSprite {
	protected ScaleModifier scaleModifier;
	protected PhysicsHandler physicsHandler;
	private ITiledTextureRegion texture;
	private Scene scene;
	private Body body;

	private float currentCoordinatesX;
	private float currentCoordinatesY;

	private boolean isAlive = true;
	private long health;

	public Enemy(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager vertexBufferObjectManager, Scene mScene) {
		super(pX, pY, pTiledTextureRegion, vertexBufferObjectManager);
		body = PhysicsFactory.createCircleBody(GameScene.getInstance().getWorld(), this, BodyType.DynamicBody, ResourceManager.getInstance().FIXTURE_DEF_ENEMY);

		GameScene.getInstance().getWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
		this.scene = mScene;
		this.animate(200);

		this.currentCoordinatesX = pX;
		this.currentCoordinatesY = pY;
		this.setTexture(pTiledTextureRegion);

		// this.scene.registerTouchArea(this);
		this.scene.attachChild(this);
		GameScene.getInstance().addEnemyToTheWorld(this);
	}

	protected void setTexture(ITiledTextureRegion texture) {
		this.texture = texture;
	}

	public boolean isAlive() {
		return isAlive;
	}

	protected void kill() {
		this.isAlive = false;
	}

	protected long getHealth() {
		return health;
	}

	protected void setHealth(long health) {
		this.health = health;
	}

	public Vector2 getMyCoordinates() {
		return new Vector2(this.getX(), this.getY());
	}

	public float getDistance(Ship ship) {
		return (float) Math.sqrt((Math.pow(Math.abs(ship.getCoordinates().x - this.currentCoordinatesX), 2) + Math.pow(Math.abs(ship.getCoordinates().y - currentCoordinatesY), 2)));
	}

	public void startMoving(Path traectory, int time) {

		this.registerEntityModifier(new EnemyPathModifier(time, traectory));
	}

	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		// body.setTransform(this.getMyCoordinates(), 0);
		if (body != null) {
			final Vector2 vector = Vector2Pool.obtain((this.currentCoordinatesX) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (this.currentCoordinatesY) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			body.setTransform(vector, 0.0f);
			Vector2Pool.recycle(vector);

			final Vector2 vector2 = Vector2Pool.obtain(0, 0);
			body.setLinearVelocity(vector2);
			Vector2Pool.recycle(vector2);
		}
		super.onManagedUpdate(pSecondsElapsed);
	}
}
