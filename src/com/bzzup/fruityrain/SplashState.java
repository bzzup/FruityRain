package com.bzzup.fruityrain;

import org.andengine.entity.scene.Scene;

import com.bzzup.fruityrain.StateManager.StateType;

import android.view.KeyEvent;

public class SplashState extends State {

	//Our scene
		protected SplashScene splashScene;

		public SplashState(Main activityReference) {
			super(activityReference);
	   	
		}

		@Override
		public Scene getScene() {
			return this.splashScene;
		}

		@Override
		public void setScene(Scene scene) {
			this.splashScene = (SplashScene)scene;
		}

		@Override
		public void begin() {

			splashScene.prepareSceneForSplash();

			activityReference.getEngine().setScene(splashScene);
		}

		@Override
		public void end() {
			//Do nothing

		}

		public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
	    	{
	    		StateManager.getInstance().switchState(StateType.MENU);	
	    	}

			return true;
		}

}
