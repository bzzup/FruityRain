package com.bzzup.fruityrain.player;

import com.bzzup.fruityrain.GameScene;

public class Player {
	
	public static class Money {
		private static long totalMoney;

		public static long getTotalMoney() {
			return totalMoney;
		}

		public static void addMoney(long money) {
			totalMoney += money;
			GameScene.getInstance().getHUD().updateMoney(totalMoney);
		}
	}
	
	public static class Levels {
		private long totalExp;
		
		public void getAvailableLevels() {
			
		}
		
		public void getPassedLevels() {
			
		}
	}
	
	public static class Kills {
		private long totalKills;

		public long getTotalKills() {
			return totalKills;
		}

		public void addKills(long kills) {
			this.totalKills += kills;
		}
	}
}
