package com.bzzup.fruityrain.enemy;

import java.util.ArrayList;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import android.util.Log;

import com.bzzup.fruityrain.GameLevels;
import com.bzzup.fruityrain.GameScene;

public class WaveController {

	TimerHandler waveTimer;
	TimerHandler timeoutTimer;
	float timerCooldown;
	
	private ArrayList<Wave> waves;
	private ArrayList<Integer> mobsInWave;
	private int wavesCount;
	private int currentWave = 0;
	private int mobsInWaveCount;
	private int currentMobInWave = 0;
	private float nextWaveCooldown;
	private float nextWaveTimeout = 1;

	public WaveController() {
		GameLevels.level1.initialize();
		waves = GameLevels.level1.getWaves();
		this.wavesCount = waves.size();
		startSpawning();

	}
	
	private void startSpawning() {
		waveTimer = new TimerHandler(waves.get(0).getCooldown(), true, new ITimerCallback() {

			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				int currentMob = getCurrentMob();
				if (currentMob != -1) {
					GameScene.getInstance().addEnemy(GameLevels.level1.getStartPoint().x, GameLevels.level1.getStartPoint().y, currentMob);
				} else {
					GameScene.getInstance().unregisterUpdateHandler(waveTimer);
					Log.d("MY", "Unregister waveTimer!");
				}

			}
		});
		

		timeoutTimer = new TimerHandler(nextWaveTimeout, false, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				Log.d("MY", "timeout!");
				GameScene.getInstance().registerUpdateHandler(waveTimer);
			}
		});
		
		GameScene.getInstance().registerUpdateHandler(timeoutTimer);
	}

	private int getCurrentMob() {
		if (currentMobInWave >= waves.get(currentWave).getMobsPerWave().size()) {
			currentWave++;
			currentMobInWave = 0;
		}
		if (currentWave >= waves.size()) {
			return -1;
		}
		
		int curMob = waves.get(currentWave).getMobsPerWave().get(currentMobInWave);
		currentMobInWave++;
		if (currentMobInWave >= waves.get(currentWave).getMobsPerWave().size()) {
			int nextWave = currentWave + 1;
			if (nextWave < waves.size()) {
				nextWaveCooldown = waves.get(nextWave).getCooldown();
				waveTimer.setTimerSeconds(nextWaveCooldown);
				Log.d("MY", "Next wave cooldown = "+nextWaveCooldown);
				nextWaveTimeout = waves.get(nextWave).getWaveTimeout();
				timeoutTimer.setTimerSeconds(nextWaveTimeout);
				Log.d("MY", "Next wave timeout = "+nextWaveTimeout);
				GameScene.getInstance().unregisterUpdateHandler(waveTimer);
				timeoutTimer.reset();
			}
		}
		Log.d("MY", "Current wave = "+currentWave+"; Current Mob = "+currentMobInWave);
		return curMob;
	}





}
