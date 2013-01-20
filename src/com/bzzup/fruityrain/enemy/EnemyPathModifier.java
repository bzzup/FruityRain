package com.bzzup.fruityrain.enemy;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;

public class EnemyPathModifier extends PathModifier {

	public EnemyPathModifier(float pDuration, Path pPath) {
		super(pDuration, pPath);

	}

	@Override
	public float onUpdate(float pSecondsElapsed, IEntity pEntity) {
		
		return super.onUpdate(pSecondsElapsed, pEntity);
	}
}
