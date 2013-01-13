package com.bzzup.fruityrain;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class PlayerShopItem extends AnimatedSprite {
	public PlayerShopItem(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.setScale(2.0f);
		this.animate(200);
		GameScene scene = GameScene.getInstance();
//		body = PhysicsFactory.createCircleBody(scene.getWorld(), this, BodyType.DynamicBody, Main.FIXTURE_DEF);
//		body.setUserData("player");

		scene.registerTouchArea(this);
//		scene.getWorld().registerPhysicsConnector(new PhysicsConnector(this, body, true, true));
		scene.attachChild(this);
		GameScene.getInstance().addItemToGameShop(this);
//		coolDown = new ShootingCoolDown(200);
//		moveCoolDown = new MovementCoolDown(1000);
	}
}
