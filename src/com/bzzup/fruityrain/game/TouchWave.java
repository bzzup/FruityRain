package com.bzzup.fruityrain.game;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;

import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;

public class TouchWave {
	final float startX;
	final float startY;
	private ParallelEntityModifier touchWaveModifier;
	
	public TouchWave(float x, float y) {
		this.startX = x;
		this.startY = y;
		showTouchArea(startX, startY);
	}
	
	private void showTouchArea(float x, float y) {
		touchWaveModifier = new ParallelEntityModifier(new AlphaModifier(1, 1, 0), new ScaleModifier(1, 0.2f, 1f), new RotationModifier(1f, 0f, 360f));
		Sprite touch_circle = new Sprite(x - ResourceManager.getInstance().touch_circle.getWidth() / 2, 
										 y - ResourceManager.getInstance().touch_circle.getHeight() / 2, 
										 ResourceManager.getInstance().touch_circle, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
		GameScene.getInstance().attachChild(touch_circle);
		touch_circle.registerEntityModifier(touchWaveModifier);
		
	}
}
