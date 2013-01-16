package com.bzzup.fruityrain;

import com.badlogic.gdx.math.Vector2;

public class Utils {
	public static class Distance {
		public static float calculateDistance(Vector2 a, Vector2 b) {
			return (float) Math.sqrt((Math.pow(Math.abs(a.x - b.x), 2) + Math.pow(Math.abs(a.y - b.y), 2)));
		}
	}
	
//	public static class RelativeSize {
//		public static float getRelativeScreenHeight(float percent) {
//			return Main.getInstance().CAMERA_HEIGHT * (percent / 100);
//		}
//		
//		public static float getRelativeScreenWidth() {
//			return Main.getInstance().CAMERA_WIDTH * (Variables.GameShopAreaPercentage / 100f);
//		}
//	}
	
	public static class GameShopArea {
		public static float getCenterX() {
			return 0;
		}
//		
//		public static float getTopX() {
//			return Main.getInstance().CAMERA_WIDTH - RelativeSize.getRelativeScreenWidth();
//		}
	}
	
	public static class Variables {
		public static final float GameShopAreaPercentage = 15f;
	}
}
