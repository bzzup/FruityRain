package com.bzzup.fruityrain.enemy;

import java.util.ArrayList;

import android.util.Log;

import com.bzzup.fruityrain.GameLevels;
import com.bzzup.fruityrain.GameScene;
import com.bzzup.fruityrain.ResourceManager;

public class WaveController {

	SpawningCoolDown spawnCooldown;

	public WaveController() {
		GameLevels.level1.initialize();
		startSpawning();
	}

	private void startSpawning() {

		ResourceManager.getInstance().getActivityReference().runOnUiThread(new Runnable() {

			@Override
			public void run() {
				for (Wave curWave : GameLevels.level1.getWaves()) {
					int currentWave = curWave.getWavelevel();
					Log.d("MY", "Current wave = " + currentWave);
					int coolDown = curWave.getCooldown() * 1000;
					ArrayList<Integer> mobs = curWave.getMobsPerWave();
					spawnCooldown = new SpawningCoolDown(coolDown * 1000);
					int i = 0;
					for (int mob : mobs) {
						GameScene.getInstance().addEnemy(GameLevels.level1.getStartPoint().x, GameLevels.level1.getStartPoint().y, mobs.get(i));
						try {
							synchronized (this) {
								wait(coolDown);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

}
