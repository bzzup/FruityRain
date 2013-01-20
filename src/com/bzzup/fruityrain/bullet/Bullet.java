package com.bzzup.fruityrain.bullet;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.bzzup.fruityrain.enemy.Enemy;

public class Bullet extends AnimatedSprite {
	
	private Scene scene;
	private boolean active;
	private float damage;
	
	public Bullet(float pX, float pY, ITiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager, Scene mScene) {
		super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);
		this.scene = mScene;
		this.scene.attachChild(this);
		this.active = true;
	}
	
	@SuppressWarnings("unused")
	protected void fire(final Enemy mEnemy) {
		Vector2 enemyPosition = mEnemy.getMyCoordinates();
		this.registerEntityModifier(new FighterBulletMoveModifier(0.1f, this.getX(), this.getY(), mEnemy) {
			@Override
			protected void onModifierFinished(IEntity pItem) {
				((Bullet) pItem).active = false;
				mEnemy.hitByDmg(damage);
				super.onModifierFinished(pItem);
			}
		});
	}
	
	public boolean isAlive() {
		return this.active;
	}

	protected float getDamage() {
		return damage;
	}

	protected void setDamage(float damage) {
		this.damage = damage;
	}
}
