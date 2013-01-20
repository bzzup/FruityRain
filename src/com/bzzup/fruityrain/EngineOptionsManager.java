package com.bzzup.fruityrain;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;

public class EngineOptionsManager {
	private static EngineOptionsManager instance;

	// engine reference
	private Engine engineReference;

	// camera
	Camera camera;

	// Proper width and height for the game surface/camera
	// public static final int CAMERA_WIDTH = 800;
	// public static final int CAMERA_HEIGHT = 480;

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

		camera = new Camera(0, 0, 1280, 720);
		engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(1280, 720), camera);
//		engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		engineOptions.getAudioOptions().setNeedsMusic(true);
	}

	public Camera getCamera() {
		return this.camera;
	}

	public EngineOptions getEngineOptions() {
		return engineOptions;
	}
}
