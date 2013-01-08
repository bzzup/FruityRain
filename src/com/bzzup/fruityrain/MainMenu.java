package com.bzzup.fruityrain;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.util.color.Color;

public class MainMenu extends MenuScene implements IOnMenuItemClickListener {

	Main activity;
	GameScene gameScene;

	public MainMenu() {
		super(Main.getInstance().mCamera);
		activity = Main.getInstance();
		this.setBackground(new Background(Color.CYAN));
		IMenuItem startButton = new TextMenuItem(0, activity.mFont, "Start!",
				activity.getVertexBufferObjectManager());
		IMenuItem resumeButton = new TextMenuItem(1, activity.mFont, "Resume",
				activity.getVertexBufferObjectManager());
		startButton.setPosition(
				activity.CAMERA_WIDTH / 2 - startButton.getWidth() / 2,
				activity.CAMERA_HEIGHT / 2 - startButton.getHeight() / 2);
		resumeButton.setPosition(activity.CAMERA_WIDTH / 2 - resumeButton.getWidth() / 2, 
				activity.CAMERA_HEIGHT / 2 + resumeButton.getHeight() / 2);
		addMenuItem(startButton);
		addMenuItem(resumeButton);
		setOnMenuItemClickListener(this);
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		switch (pMenuItem.getID()) {
		case 0:
			activity.setCurrentScene(new GameScene());
			break;
		case 1:
		{
			if (gameScene == null) {
				gameScene = new GameScene();
				activity.setCurrentScene(gameScene);
			}
			activity.setCurrentScene(new GameScene());
		}	break;
		default:
			break;
		}
		return false;
	}

}
