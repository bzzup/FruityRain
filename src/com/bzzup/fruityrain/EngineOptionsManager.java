package com.bzzup.fruityrain;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

public class EngineOptionsManager {
	private static EngineOptionsManager instance;

	// engine reference
	private Engine engineReference;

	// camera
	Camera camera;

	// Proper width and height for the game surface/camera
	public static final int CAMERA_WIDTH = 1280;
	public static final int CAMERA_HEIGHT = 720;

	EngineOptions engineOptions;

	public static void initializeEngineOptionsManager(Engine engineReference) {
		if (instance == null) {
			instance = new EngineOptionsManager(engineReference);
		}
	}

	public static synchronized EngineOptionsManager getInstance() {
		return instance;
	}

	private EngineOptionsManager(Engine engineReference) {
		this.engineReference = engineReference;

		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
	}

	public Camera getCamera() {
		return this.camera;
	}

	public EngineOptions getEngineOptions() {
		return engineOptions;
	}
}
