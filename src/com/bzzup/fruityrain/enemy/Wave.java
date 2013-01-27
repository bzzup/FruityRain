package com.bzzup.fruityrain.enemy;

import java.util.ArrayList;

public class Wave {
	
	private int cooldown;
	private ArrayList<Integer> mobs;
	private int wavelevel;
	
	
	public Wave() {
		mobs = new ArrayList<Integer>();
	}


	public int getWavelevel() {
		return wavelevel;
	}


	public void setWavelevel(int wavelevel) {
		this.wavelevel = wavelevel;
	}


	public int getCooldown() {
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
}
