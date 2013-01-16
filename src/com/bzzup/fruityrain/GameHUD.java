package com.bzzup.fruityrain;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;

public class GameHUD {
	protected HUD hud;
	public Text hudTimerTextLabel;
	public Text hudTimerTextValue;

	public Text introTextValue;
	public ParallelEntityModifier introTextModifier;
	public ParallelEntityModifier hudIntroModifier;

	public GameHUD() {

		hud = new HUD();

		hudTimerTextLabel = new Text(0, 0, ResourceManager.getInstance().splashBitmapFont, "TIME ROCKIN' THE CHOPPA:", new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance()
				.getActivityReference().getVertexBufferObjectManager());
		hudTimerTextValue = new Text(550, 0, ResourceManager.getInstance().mDroidFont, "0.0", new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance().getActivityReference()
				.getVertexBufferObjectManager());

		hud.attachChild(hudTimerTextLabel);
		hud.attachChild(hudTimerTextValue);

		// intro text & modifier
		introTextValue = new Text(220, 220, ResourceManager.getInstance().mDroidFont, "-GET READY-", new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance().getActivityReference()
				.getVertexBufferObjectManager());
		introTextModifier = new ParallelEntityModifier(new AlphaModifier(2, 1, 0), new ScaleModifier(2, 2, 0.5f));
		introTextValue.registerEntityModifier(introTextModifier);

	}

	public HUD getHud() {
		return this.hud;
	}

	public Text getIntroText() {
		return this.introTextValue;
	}

	public ParallelEntityModifier getIntroTextModifier() {
		return this.introTextModifier;
	}
}
