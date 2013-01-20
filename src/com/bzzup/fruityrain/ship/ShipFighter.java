package com.bzzup.fruityrain.ship;

import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;

public class ShipFighter extends Ship {
	
	public ShipFighter(float x, float y, GameScene mScene) {
		super(x, y, ResourceManager.getInstance().baloonPlayer, 
				ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(), 
				mScene);
		this.levelUp();
	}

	public void levelUp(){
		if (this.getCurrentLevel() <= this.MAX_LVL) {
			this.level(ShipDictionary.Fighter.getAttributesForLevel(this.getCurrentLevel()+1));
		}
	}
		
}