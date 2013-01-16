package com.bzzup.fruityrain;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;

public class SplashScreen extends Scene {

private Main mainActivity;
	
	public SplashScreen() {
		setBackground(new Background(0.09804f, 0.6274f, 0));
//		mainActivity = Main.getInstance();
		Text title = new Text(0, 0, mainActivity.mFont, "Splash!", mainActivity.getVertexBufferObjectManager());
		title.setPosition(-title.getWidth(), mainActivity.CAMERA_HEIGHT / 2);
		attachChild(title);
		
		title.registerEntityModifier(new MoveXModifier(2, title.getX(), (mainActivity.CAMERA_WIDTH / 2) - title.getWidth() / 2));
		
		registerEntityModifier(mod);
	}
	
	DelayModifier mod = new DelayModifier(2) {
		protected void onModifierFinished(IEntity pItem) {
//			mainActivity.setCurrentScene(new MainMenu());
		};
	};
	
	
}
