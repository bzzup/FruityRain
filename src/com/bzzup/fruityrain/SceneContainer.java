package com.bzzup.fruityrain;

import java.util.HashMap;
import java.util.Map;

import org.andengine.entity.scene.Scene;

public class SceneContainer {
	private static SceneContainer instance;

	public static synchronized SceneContainer getInstance() {
		if (instance == null) {
			instance = new SceneContainer();
		}
		return instance;
	}

    //Scene types
    public enum SceneType
    {
    	NONE,
    	SPLASH,
    	MENU,
    	OPTIONS,
    	GAME    	
    }
    
    //Our scene container
    private Map<SceneType, Scene> sceneMap;

    public void addScene(SceneType sceneType, Scene scene) {
    	sceneMap.put(sceneType, scene);
    }
    
    //Constructor
    private SceneContainer() {
    	sceneMap = new HashMap<SceneType, Scene>();
    }
    
    public Scene getScene(SceneType sceneType) {
    	return sceneMap.get(sceneType);
    }
}
