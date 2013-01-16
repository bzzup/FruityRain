package com.bzzup.fruityrain;

import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.adt.pool.GenericPool;

public class EnemyPool extends GenericPool<Enemy> {

	private ITiledTextureRegion mTextureRegion;
	private PhysicsWorld world;
	private Main mainActivity;

	@Override
	protected Enemy onAllocatePoolItem() {
		return new Enemy(GameLevels.level1.getStartPoint().x, GameLevels.level1.getStartPoint().y, mTextureRegion, 
				ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(),
				world);
	}

	public EnemyPool(ITiledTextureRegion region, PhysicsWorld pWorld) {
		if (region == null) {
			// Need to be able to create a Sprite so the Pool needs to have a
			// TextureRegion
			throw new IllegalArgumentException("The texture region must not be NULL");
		}
		// mainActivity = Main.getInstance();
		this.mTextureRegion = region;
		this.world = pWorld;
	}

	/**
	 * Called when a Bullet is sent to the pool
	 */
	@Override
	protected void onHandleRecycleItem(final Enemy mEnemy) {
		mEnemy.setIgnoreUpdate(true);
		mEnemy.setVisible(false);
		// mEnemy.detachSelf();
	}

	/**
	 * Called just before a Bullet is returned to the caller, this is where you
	 * write your initialize code i.e. set location, rotation, etc.
	 */
	@Override
	protected void onHandleObtainItem(final Enemy mEnemy) {
		mEnemy.reset();
	}

}
