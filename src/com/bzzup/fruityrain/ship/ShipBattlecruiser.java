package com.bzzup.fruityrain.ship;

import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;


public class ShipBattlecruiser extends Ship {
	
	public ShipBattlecruiser(float x , float y, GameScene mScene) {
		super(x, y, ResourceManager.getInstance().baloonEnemy, 
				ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(), 
				mScene);
		
		this.setSpeed(1f);
		this.setFireDistance(300f);
		this.setFireSpeed(100);
		this.setDamage(200f);
		this.setLinearDamping(1f);
	}
	
	public void levelUp(){
		if (this.getCurrentLevel() <= this.MAX_LVL) {
			this.level(ShipDictionary.Fighter.getAttributesForLevel(this.getCurrentLevel()+1));
		}
	}

}
