package com.bzzup.fruityrain.bullet;

import org.andengine.entity.scene.Scene;

import com.badlogic.gdx.math.Vector2;
import com.bzzup.fruityrain.ResourceManager;
import com.bzzup.fruityrain.enemy.Enemy;
import com.bzzup.fruityrain.ship.Ship;

public class FighterBullet extends Bullet {

	public FighterBullet(Ship ship, Enemy mEnemy, Scene mScene) {
		super(ship.getCoordinates().x, ship.getCoordinates().y, ResourceManager.getInstance().bullet_fighter, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager(), mScene);
		this.setDamage(ship.getDamage());
		this.fire(mEnemy);
	}
	
}
