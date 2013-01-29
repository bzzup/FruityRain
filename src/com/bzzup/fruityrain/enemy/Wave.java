package com.bzzup.fruityrain.enemy;

import java.util.ArrayList;

public class Wave {
	
	private float cooldown;
	private ArrayList<Integer> mobs;
	private int wavelevel;
	private float waveTimeout;
	
	
	public Wave() {
		mobs = new ArrayList<Integer>();
	}


	public int getWavelevel() {
		return wavelevel;
	}


	public void setWavelevel(int wavelevel) {
		this.wavelevel = wavelevel;
	}


	public float getCooldown() {
		return cooldown;
	}


	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}
	
	public void addMob(int mob) {
		mobs.add(mob);
	}
	
	public ArrayList<Integer> getMobsPerWave() {
		return mobs;
	}


	public float getWaveTimeout() {
		return waveTimeout;
	}


	public void setWaveTimeout(float waveTimeout) {
		this.waveTimeout = waveTimeout;
	}
}
