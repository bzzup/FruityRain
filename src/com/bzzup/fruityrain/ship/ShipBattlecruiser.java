package com.bzzup.fruityrain.ship;

import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;


public class ShipBattlecruiser extends Ship {
	
	public ShipBattlecruiser(float x , float y, GameScene mScene) {
		super(x, y, ResourceManager.getInstance().enemy_radiation, 
				ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(), 
				mScene);
		
		this.levelUp();
	}
	
	public void levelUp(){
		if (this.getCurrentLevel() <= this.MAX_LVL) {
			this.level(ShipDictionary.Cruiser.getAttributesForLevel(this.getCurrentLevel()+1));
		}
	}

}
