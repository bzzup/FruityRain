package com.bzzup.fruityrain.ship;



public class ShipDictionary {
	public static class Fighter {
		public static final long cost = 100;

		protected static AttributesDictionary getAttributesForLevel(int lvl) {
			switch (lvl) {
			case 1:
			{
				return new AttributesDictionary(3f, 200, 10f, 200f, 0, 3f, 200);
			}	
			case 2: {
				return new AttributesDictionary(2, 2, 2, 2, 2, 2, 200);
			}
			case 3: {
//				return new AttributesDictionary(speed, fireSpeed, damage, fireDistance, hitsCount, linearDamping);
			}
			default:
				break;
			}
			return null; 
		}
	
	
	}
	
	public static class Cruiser {
		public static final long cost = 200;
		
		protected static AttributesDictionary getAttributesForLevel(int lvl) {
			switch (lvl) {
			case 1:
			{
				return new AttributesDictionary(3f, 200, 10f, 200f, 0, 3f, 200);
			}	
			case 2: {
				return new AttributesDictionary(2, 2, 2, 2, 2, 2, 200);
			}
			case 3: {
//				return new AttributesDictionary(speed, fireSpeed, damage, fireDistance, hitsCount, linearDamping);
			}
			default:
				break;
			}
			return null;
		}
	}

	public static class ShipTypes {
		public static final int FIGHTER = 1;
		public static final int CRUISER = 2;
	}
}
