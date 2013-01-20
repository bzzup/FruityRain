package com.bzzup.fruityrain;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.opengl.GLES20;

public class MenuSelectionScene extends Scene {
	protected Main activityReference;

	// Menu selection indices
	public static final int MENU_PLAY = 0;
	public static final int MENU_QUIT = MENU_PLAY + 1;

	// components
	public MenuScene menuOptionsScene;
	public AutoParallaxBackground autoParallaxBackground;
	public VertexBufferObjectManager vertexBufferObjectManager;
	public HUD menuHud;
	public Text versionLabel;
	public Text versionNumber;

	public MenuSelectionScene(Main activityReference) {
		super();

		this.activityReference = activityReference;

		menuOptionsScene = createMenuOptionsScene();
		this.setChildScene(menuOptionsScene, false, true, true);

		autoParallaxBackground = new AutoParallaxBackground(0, 0, 0, 5);
		vertexBufferObjectManager = activityReference.getVertexBufferObjectManager();

		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, ResolutionManager.getInstance().getCameraHeight()
				- ResourceManager.getInstance().mParallaxLayerBack.getHeight(), ResourceManager.getInstance().mParallaxLayerBack, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(5.0f, new Sprite(0, 80, ResourceManager.getInstance().mParallaxLayerMid, vertexBufferObjectManager)));
		autoParallaxBackground.attachParallaxEntity(new ParallaxEntity(-15.0f, new Sprite(0, ResolutionManager.getInstance().getCameraHeight()
				- ResourceManager.getInstance().mParallaxLayerFront.getHeight(), ResourceManager.getInstance().mParallaxLayerFront, vertexBufferObjectManager)));

		menuOptionsScene.setBackground(autoParallaxBackground);

		menuHud = new HUD();
		versionLabel = new Text(0, 0, ResourceManager.getInstance().splashBitmapFont, "version", new TextOptions(HorizontalAlign.LEFT), ResourceManager.getInstance().getActivityReference()
				.getVertexBufferObjectManager());
		versionNumber = new Text(150, 0, ResourceManager.getInstance().splashBitmapFont, "1.0", new TextOptions(HorizontalAlign.LEFT), ResourceManager.getInstance().getActivityReference()
				.getVertexBufferObjectManager());
		menuHud.attachChild(versionLabel);
		menuHud.attachChild(versionNumber);

	}

	public void prepareSceneForMenu() {
		EngineOptionsManager.getInstance().getCamera().setHUD(menuHud);
	}

	public void cleanupAfterMenu() {
		EngineOptionsManager.getInstance().getCamera().setHUD(null);
	}

	private MenuScene createMenuOptionsScene() {

		MenuScene menuOptionsScene = new MenuScene(EngineOptionsManager.getInstance().getCamera());

		final IMenuItem playMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_PLAY, ResourceManager.getInstance().mDroidFont, "PLAY", activityReference.getVertexBufferObjectManager()),
				new Color(1, 0, 0), new Color(1, 1, 1));
		playMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuOptionsScene.addMenuItem(playMenuItem);

		final IMenuItem quitMenuItem = new ColorMenuItemDecorator(new TextMenuItem(MENU_QUIT, ResourceManager.getInstance().mDroidFont, "QUIT", activityReference.getVertexBufferObjectManager()),
				new Color(1, 0, 0), new Color(1, 1, 1));
		quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		menuOptionsScene.addMenuItem(quitMenuItem);

		menuOptionsScene.buildAnimations();

		return menuOptionsScene;
	}
}
