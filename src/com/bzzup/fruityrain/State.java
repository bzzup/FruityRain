package com.bzzup.fruityrain;

import org.andengine.entity.scene.Scene;

import android.view.KeyEvent;

public abstract class State {
	protected Main activityReference;

	public State(Main activityReference) {
		this.activityReference = activityReference;
	}

	abstract public void begin();

	abstract public void end();

	abstract public boolean onKeyDown(int keyCode, KeyEvent event);

	abstract public Scene getScene();

	abstract public void setScene(Scene scene);
}
