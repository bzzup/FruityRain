package com.bzzup.fruityrain;

import org.andengine.entity.modifier.PathModifier.Path;

import com.badlogic.gdx.math.Vector2;

public class GameLevels {
	public static class level1 {
		public static Path getTraectory() {
			return new Path(6).to(10, 10)
					.to(400, 10)
					.to(400, 400)
					.to(100, 400)
					.to(100, 600)
					.to(400, 600);
		}
		
		public static int getTime() {
			return 5;
		}
		
		public static Vector2 getStartPoint() {
//			return new Vector2(new Random().nextInt(500) + 10, 0);
			return new Vector2(0, 0);
		}
	}
}
