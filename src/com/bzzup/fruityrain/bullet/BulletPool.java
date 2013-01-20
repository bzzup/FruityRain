package com.bzzup.fruityrain.bullet;

import org.andengine.util.adt.pool.GenericPool;

public class BulletPool extends GenericPool<FighterBullet> {

	public BulletPool() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected FighterBullet onAllocatePoolItem() {
		return null;
	}
	
	@Override
	public synchronized FighterBullet obtainPoolItem() {
		// TODO Auto-generated method stub
		return super.obtainPoolItem();
	}

}
