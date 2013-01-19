package com.bzzup.fruityrain.player;

public class Player {
	
	public static class Money {
		private long totalMoney;

		public long getTotalMoney() {
			return totalMoney;
		}

		public void addMoney(long money) {
			this.totalMoney += money;
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
