package com.bzzup.fruityrain;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;

import android.view.KeyEvent;

import com.bzzup.fruityrain.StateManager.StateType;

public class GameIntroState extends State {

	public GameScene gameScene;

	public TimerHandler introCountdownTimer;

	public Integer timerCountdown;

	public GameIntroState(Main activityReference) {
		super(activityReference);

	}

	@Override
	public void begin() {
		activityReference.getEngine().setScene(gameScene);

		gameScene.prepareSceneForIntro();

		//reset intro timer
		timerCountdown = 2; 

		gameScene.registerUpdateHandler(new TimerHandler(1.0f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				if (timerCountdown <= 1) {
					GameIntroState.this.gameScene.unregisterUpdateHandler(pTimerHandler);
					StateManager.getInstance().switchState(StateType.GAME_PLAYING);
				} else {
					timerCountdown -= 1;
				}					
			}
		}));
	}

	@Override
	public void end() {
		gameScene.cleanupAfterIntro();
	}

	@Override
	public Scene getScene() {
		return gameScene;
	}

	@Override
	public void setScene(Scene scene) {
		this.gameScene = (GameScene)scene;		
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
    	{
    		//StateManager.getInstance().switchState(StateType.GAME_PLAYING);
    	}

		return true;
	}

}
