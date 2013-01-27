package com.bzzup.fruityrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.andengine.entity.modifier.PathModifier.Path;

import com.badlogic.gdx.math.Vector2;
import com.bzzup.fruityrain.enemy.EnemyDictionary;
import com.bzzup.fruityrain.enemy.Wave;

public class GameLevels {
	
	public static class level1 {
		
		private static ArrayList<Wave> waves;
		private static Wave wave;
		
		public static void initialize() {
			waves = new ArrayList<Wave>();
			
			wave = new Wave();
			wave.setWavelevel(1);
			wave.setCooldown(1);
			
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			
			waves.add(wave);
			
			wave = new Wave();
			wave.setWavelevel(2);
			wave.setCooldown(1);
			
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			wave.addMob(EnemyDictionary.mob1);
			
			waves.add(wave);
		}
		
		public static Path getTraectory() {
			return new Path(29).to(0, 0)
					.to(27, 45)
					.to(70, 93)
					.to(97, 158)
					.to(119, 232)
					.to(141, 310)
					.to(159, 380)
					.to(182, 464)
					.to(202, 500)
					.to(229, 533)
					.to(255, 560)
					.to(297, 578)
					.to(340, 578)
					.to(383, 570)
					.to(415, 548)
					.to(451, 518)
					.to(497, 449)
					.to(540, 361)
					.to(586, 275)
					.to(651, 214)
					.to(746, 198)
					.to(832, 204)
					.to(906, 241)
					.to(971, 289)
					.to(1032, 353)
					.to(1084, 425)
					.to(1139, 493)
					.to(1202, 572)
					.to(1259, 642);
		}
		
		public static int getTime() {
			return 10;
		}
		
		public static Vector2 getStartPoint() {
//			return new Vector2(new Random().nextInt(500) + 10, 0);
			return new Vector2(0, 0);
		}
	
		public static ArrayList<Wave> getWaves() {
			return waves;
		}
	}
}
