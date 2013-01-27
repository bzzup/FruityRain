package com.bzzup.fruityrain.enemy;

import java.util.ArrayList;

import android.util.Log;

import com.bzzup.fruityrain.GameLevels;
import com.bzzup.fruityrain.GameScene;

public class WaveController {

	SpawningCoolDown spawnCooldown;

	public WaveController() {
		GameLevels.level1.initialize();
		startSpawning();
	}

	private void startSpawning() {
		for (Wave curWave : GameLevels.level1.getWaves()) {
			int currentWave = curWave.getWavelevel();
			Log.d("MY", "Current wave = " + currentWave);
			int coolDown = curWave.getCooldown();
			ArrayList<Integer> mobs = curWave.getMobsPerWave();
			spawnCooldown = new SpawningCoolDown(coolDown * 1000);
			int i = 0;
			do {
//				if (spawnCooldown.checkValidity()) {

					GameScene.getInstance().addEnemy(GameLevels.level1.getStartPoint().x, GameLevels.level1.getStartPoint().y, mobs.get(i));
					i++;
//				}
			} while (i < mobs.size());
		}
	}
	
}
