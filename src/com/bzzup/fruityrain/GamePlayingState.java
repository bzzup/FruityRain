package com.bzzup.fruityrain;

import org.andengine.entity.scene.Scene;

import android.view.KeyEvent;

import com.bzzup.fruityrain.StateManager.StateType;

public class GamePlayingState extends State {
	public GameScene gameScene;

	public GamePlayingState(Main activityReference) {
		super(activityReference);

	}

	@Override
	public Scene getScene() {
		return gameScene;
	}

	@Override
	public void setScene(Scene scene) {
		this.gameScene = (GameScene)scene;

	}

	@Override
	public void begin() {
		activityReference.getEngine().setScene(gameScene);

		gameScene.prepareSceneForPlay();

	}

	@Override
	public void end() {
		gameScene.cleanupAfterPlay();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
    	{
    		StateManager.getInstance().switchState(StateType.MENU);	
    	}

		return true;
	}
}
