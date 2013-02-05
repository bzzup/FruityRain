package com.bzzup.fruityrain.game;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.Sprite;

import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;

public class TouchWave {
	private ParallelEntityModifier touchWaveModifier;
	private LoopEntityModifier loop;
	private static TouchWave instance;
	private Sprite touch_circle;
	
	public TouchWave() {
		instance = this;
	}
	
	public static TouchWave getInstance() {
		if (instance == null) {
			return new TouchWave();
		}
		return instance;
	}
	
	public void showTouchArea(float x, float y) {
		if (touch_circle != null) {
//			loop = null;
			touchWaveModifier = null;
			GameScene.getInstance().detachChild(touch_circle);
		}
//		touchWaveModifier = new ParallelEntityModifier(new RotationModifier(1f, 0f, 360f));
		touchWaveModifier = new ParallelEntityModifier(new AlphaModifier(1, 1, 0), new ScaleModifier(1, 0.2f, 1f), new RotationModifier(1f, 0f, 360f));
		touch_circle = new Sprite(x - ResourceManager.getInstance().touch_circle.getWidth() / 2, 
										 y - ResourceManager.getInstance().touch_circle.getHeight() / 2, 
										 ResourceManager.getInstance().touch_circle, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
//		touch_circle = new Sprite(x - ResourceManager.getInstance().radar_circle.getWidth() / 2, 
//				 y - ResourceManager.getInstance().radar_circle.getHeight() / 2, 
//				 ResourceManager.getInstance().radar_circle, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
//		loop = new LoopEntityModifier(touchWaveModifier);
//		touch_circle.setAlpha(0.3f);
//		touch_circle.setScale(2f);
//		touch_circle.setZIndex(1);
		GameScene.getInstance().attachChild(touch_circle);
		touch_circle.registerEntityModifier(touchWaveModifier);
		
	}
}
