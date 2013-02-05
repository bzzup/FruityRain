package com.bzzup.fruityrain;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.IAnimationData;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;

import com.bzzup.fruityrain.player.Player;
import com.bzzup.fruityrain.ship.ShipDictionary;

public class GameHUD {
	protected HUD hud;
	public Text hudTimerTextLabel;
	public Text hudTimerTextValue;
	public Text hudMoneyTextValue;

	public Text introTextValue;
	public ParallelEntityModifier introTextModifier;
	public ParallelEntityModifier hudIntroModifier;

	public GameHUD() {

		hud = new HUD();

//		hudTimerTextLabel = new Text(0, 0, ResourceManager.getInstance().splashBitmapFont, "TIME ROCKIN' THE CHOPPA:", new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance()
//				.getActivityReference().getVertexBufferObjectManager());
//		hudTimerTextValue = new Text(550, 0, ResourceManager.getInstance().mDroidFont, "0.0", 10, new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance().getActivityReference()
//				.getVertexBufferObjectManager());
		hudMoneyTextValue = new Text(700, 0, ResourceManager.getInstance().mDroidFont, "Money: 0", 20, new TextOptions(HorizontalAlign.RIGHT), ResourceManager.getInstance().getActivityReference()
				.getVertexBufferObjectManager());

//		hud.attachChild(hudTimerTextLabel);
//		hud.attachChild(hudTimerTextValue);
		hud.attachChild(hudMoneyTextValue);

		// intro text & modifier
		introTextValue = new Text(500, 350, ResourceManager.getInstance().mDroidFont, "-GET READY-", new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance().getActivityReference()
				.getVertexBufferObjectManager());
		introTextModifier = new ParallelEntityModifier(new AlphaModifier(2, 1, 0), new ScaleModifier(2, 2, 0.5f));
		introTextValue.registerEntityModifier(introTextModifier);
		attachSprites();
		attachButtons();
	}
	
	private void attachButtons() {
		AnimatedSprite hud_button_pause = new AnimatedSprite(30, 10, ResourceManager.getInstance().hud_pause_button, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					if (this.getCurrentTileIndex() == 0) {
						this.setCurrentTileIndex(1);
					} else {
						this.setCurrentTileIndex(0);
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		hud_button_pause.setScale(1.5f);
		hud.attachChild(hud_button_pause);
		GameScene.getInstance().registerTouchArea(hud_button_pause);
	}

	private void attachSprites() {
		AnimatedSprite sampleShip = new AnimatedSprite(1200, 50, ResourceManager.getInstance().baloonPlayer, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					if (Player.Money.getTotalMoney() >= ShipDictionary.Fighter.cost) {
						GameScene.getInstance().addShip(200, 200, ShipDictionary.ShipTypes.FIGHTER);
					}
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		sampleShip.animate(200);
		sampleShip.setScale(1.5f);
		
		Text sampleShipCost = new Text(sampleShip.getX(), sampleShip.getY() + sampleShip.getHeightScaled() + 5, 
				ResourceManager.getInstance().splashBitmapFont, 
				String.valueOf(ShipDictionary.Fighter.cost), 10, new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance()
				.getActivityReference().getVertexBufferObjectManager());
		
		AnimatedSprite sampleShip2 = new AnimatedSprite(1200, 150, ResourceManager.getInstance().baloonEnemy, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (Player.Money.getTotalMoney() >= ShipDictionary.Cruiser.cost) {
					GameScene.getInstance().addShip(200, 200, ShipDictionary.ShipTypes.CRUISER);
				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		sampleShip2.animate(200);
		sampleShip2.setScale(1.5f);
		
		Text sampleShipCost2 = new Text(sampleShip2.getX(), sampleShip2.getY() + sampleShip2.getHeightScaled() + 5, 
				ResourceManager.getInstance().splashBitmapFont, 
				String.valueOf(ShipDictionary.Cruiser.cost), 10, new TextOptions(HorizontalAlign.CENTER), ResourceManager.getInstance()
				.getActivityReference().getVertexBufferObjectManager());
		
		hud.attachChild(sampleShip);
		hud.attachChild(sampleShipCost);
		hud.attachChild(sampleShip2);
		hud.attachChild(sampleShipCost2);
		GameScene.getInstance().registerTouchArea(sampleShip);
		GameScene.getInstance().registerTouchArea(sampleShip2);
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

	public void updateMoney(long count) {
		hudMoneyTextValue.setText("Money: " + count);
	}
}
