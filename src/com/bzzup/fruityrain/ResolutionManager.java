package com.bzzup.fruityrain;

import android.view.Display;
import android.view.WindowManager;

public class ResolutionManager {

	private static ResolutionManager instance;
	private float mCameraWidth;
	private float mCameraHeight;
	private Main mainInstance;

	public ResolutionManager(Main activityReference) {
		mainInstance = activityReference;
		Display display = ((WindowManager) mainInstance.getSystemService(mainInstance.WINDOW_SERVICE)).getDefaultDisplay();

		mCameraWidth = display.getWidth();
		mCameraHeight = display.getHeight();
	}
	
	public static void initializeResolutionManager(Main activityReference) {
		if (instance == null) {
			instance = new ResolutionManager(activityReference);
		}
	}

	public static synchronized ResolutionManager getInstance() {
		return instance;
	}
	
	public float getCameraWidth() {
		return 1280;
	}
	
	public float getCameraHeight() {
		return 720;
	}

}
