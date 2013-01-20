package com.bzzup.fruityrain.bullet;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;

import com.bzzup.fruityrain.enemy.Enemy;

public class FighterBulletMoveModifier extends MoveModifier {

	public FighterBulletMoveModifier(float pDuration, float pFromX, float pFromY, Enemy enemy) {
		super(pDuration, pFromX, enemy.getMyCoordinates().x, pFromY, enemy.getMyCoordinates().y);
	}
	
	@SuppressWarnings("unused")
	@Override
	protected void onSetValues(IEntity pEntity, float pPercentageDone, float pX, float pY) {
		float testx = pX;
		float testy = pY;
		super.onSetValues(pEntity, pPercentageDone, pX, pY);
	}
	


}
