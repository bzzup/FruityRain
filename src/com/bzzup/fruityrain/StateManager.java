package com.bzzup.fruityrain;

import java.util.HashMap;
import java.util.Map;

import org.andengine.entity.scene.Scene;

import com.bzzup.fruityrain.SceneContainer.SceneType;

public class StateManager {
	private static StateManager instance;

	public static void initializeStateManager(Main activityReference) {
		if (instance == null) {
			instance = new StateManager(activityReference);
		}
	}

	public static synchronized StateManager getInstance() {
		return instance;
	}

	// Scene types
	public enum StateType {
		SPLASH, MENU, OPTIONS, GAME_INTRO, GAME_PLAYING, GAME_HIGHSCORES
	}

	private Main activityReference;

	// access the application
	public Main getActivityReference() {
		return this.activityReference;
	}

	// Our current state
	private StateType currentState = StateType.SPLASH;

	// Our scene container
	private Map<StateType, State> stateMap;

	private StateManager(Main activityReference) {
		this.activityReference = activityReference;
		initializeStates();
	}

	private void initializeStates() {

		// Splash scene
		SplashScene splashScene = new SplashScene(activityReference);
		SceneContainer.getInstance().addScene(SceneType.SPLASH, splashScene);

		// splash state
		SplashState splashState = new SplashState(activityReference);
		splashState.setScene(splashScene);

		// menu scene
		MenuSelectionScene menuSelectionScene = new MenuSelectionScene(activityReference);
		SceneContainer.getInstance().addScene(SceneType.MENU, menuSelectionScene);

		// menu state
		MenuSelectionState menuSelectionState = new MenuSelectionState(activityReference);
		menuSelectionState.setScene(menuSelectionScene);

		// Create game scene
		GameScene gameScene = new GameScene(activityReference);
		SceneContainer.getInstance().addScene(SceneType.GAME, gameScene);

		// game intro
		GameIntroState gameIntroState = new GameIntroState(activityReference);
		gameIntroState.setScene(gameScene);

		// game playing state
		GamePlayingState gamePlayingState = new GamePlayingState(activityReference);
		gamePlayingState.setScene(gameScene);

		stateMap = new HashMap<StateType, State>();
		stateMap.put(StateType.SPLASH, splashState);
		stateMap.put(StateType.MENU, menuSelectionState);
		stateMap.put(StateType.GAME_INTRO, gameIntroState);
		stateMap.put(StateType.GAME_PLAYING, gamePlayingState);
		/*
		 * stateMap.put(StateType.GAME_HIGHSCORES, gameHighScoresState);
		 */

		// force a switch state to the first splashscreen
		this.switchState(StateType.SPLASH);

	}

	public State getCurrentState() {
		return this.stateMap.get(this.currentState);
	}

	public State getState(StateType stateType) {
		return this.stateMap.get(stateType);
	}

	public void switchState(StateType newState) {

		// close off current state
		this.stateMap.get(this.currentState).end();

		// switch current state id
		this.currentState = newState;

		// begin new state
		getCurrentState().begin();

	}
}
