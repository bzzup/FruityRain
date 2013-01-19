package com.bzzup.fruityrain.ship;


public class AttributesDictionary {
	private float speed;
	private long fireSpeed;
	private float damage;
	private float fireDistance;
	private long hitsCount;
	private float linearDamping;
	private long expToLvl;
	
	public AttributesDictionary(float speed, long fireSpeed, float damage, float fireDistance, long hitsCount, float linearDamping, long exptolvl) {
		this.speed = speed;
		this.fireSpeed = fireSpeed;
		this.damage = damage;
		this.fireDistance = fireDistance;
		this.hitsCount = hitsCount;
		this.linearDamping = linearDamping;
		this.expToLvl = exptolvl;
	}
	
	public float getSpeed() {
		return this.speed;
	}
	
	public long getFireSpeed() {
		return this.fireSpeed;
	}
	
	public float getDamage() {
		return this.damage;
	}
	
	public float getFireDistance() {
		return this.fireDistance;
	}
	
	public long getHitsCount() {
		return this.hitsCount;
	}
	
	public float getLinerDamping() {
		return this.linearDamping;
	}

	public long getExpToNextLevel() {
		return this.expToLvl;
	}
}

