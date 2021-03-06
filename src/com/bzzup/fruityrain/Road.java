package com.bzzup.fruityrain;

import java.util.ArrayList;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;

public class Road {
	
	private ArrayList<Vector2> roadTop; 
	private ArrayList<Vector2> roadBottom;
	
	public Road() {
		roadTop = new ArrayList<Vector2>();
		roadBottom = new ArrayList<Vector2>();
		Path path = GameLevels.level1.getTraectory();
		for (int i = 0; i < path.getSize() - 1; i++) {
			float x = path.getCoordinatesX()[i];
			float y = path.getCoordinatesY()[i];
			float xNext = path.getCoordinatesX()[i + 1];
			float yNext = path.getCoordinatesY()[i + 1];
			System.out.println("Segment " + i + " FROM X: " + x + " | Y: " + y + " TO X: " + xNext + " | Y: " + yNext);
			Vector2 pointA = new Vector2(x, y);
			Vector2 pointB = new Vector2(xNext, yNext);
			roadTop.add(calculateTopPoint(pointA, pointB));
			roadBottom.add(calculateBottomPoint(pointA, pointB));
		}
		for (int i = 0; i < roadTop.size()-1; i++) {
			Vector2 topA = Vector2Pool.obtain(roadTop.get(i));
			Vector2 topB = Vector2Pool.obtain(roadTop.get(i+1));
			Vector2 bottomA = Vector2Pool.obtain(roadBottom.get(i));
			Vector2 bottomB = Vector2Pool.obtain(roadBottom.get(i+1));
			Line topLine = new Line(topA.x, topA.y, topB.x, topB.y, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
			Line bottomLine = new Line(bottomA.x, bottomA.y, bottomB.x, bottomB.y, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
			PhysicsFactory.createLineBody(GameScene.getInstance().getWorld(), topLine, ResourceManager.getInstance().FIXTURE_DEF_WALL);
			PhysicsFactory.createLineBody(GameScene.getInstance().getWorld(), bottomLine, ResourceManager.getInstance().FIXTURE_DEF_WALL);
			
//			topLine.setColor(Color.WHITE);
//			bottomLine.setColor(Color.WHITE);
			Sprite pointTop = new Sprite(topB.x, topB.y, ResourceManager.getInstance().road_point, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
			pointTop.setScale(0.5f);
			Sprite pointBottom = new Sprite(bottomB.x, bottomB.y, ResourceManager.getInstance().road_point, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
			pointBottom.setScale(0.5f);
			
			GameScene.getInstance().attachChild(pointTop);
			GameScene.getInstance().attachChild(pointBottom);
			
			AlphaModifier fading = new AlphaModifier(3f, 1, 0.1f);
			AlphaModifier clearer = new AlphaModifier(3f, 0.1f, 1);
			SequenceEntityModifier seq = new SequenceEntityModifier(fading, clearer);
			LoopEntityModifier loop = new LoopEntityModifier(seq);
			pointTop.registerEntityModifier(loop);
			pointBottom.registerEntityModifier(loop);
			
			Vector2Pool.recycle(topA);
			Vector2Pool.recycle(topB);
			Vector2Pool.recycle(bottomA);
			Vector2Pool.recycle(bottomB);
		}
	}

	private Vector2 calculateTopPoint(Vector2 start, Vector2 end) {
		float X = 0;
		float pointX = 0;
		float pointY = 0;
		
		if (end.y > start.y) {
			X = end.y - start.y;
		} else {
			X = start.y - end.y;
		}
		
		float Y = end.x - start.x;
		float C = (float) Math.sqrt(X * X + Y * Y);
		float angle = 90 - (float) Math.toDegrees(Math.asin(Y / C));
		System.out.println("Angle = " + angle);
		
		if (end.y >= start.y) {
			pointX = (float) (start.x + 60 * Math.sin(Math.toRadians(angle)));
			pointY = (float) (start.y - 60 * Math.cos(Math.toRadians(angle)));
		} else {
			pointX = (float) (start.x - 30 * Math.sin(Math.toRadians(angle)));
			pointY = (float) (start.y - 30 * Math.cos(Math.toRadians(angle)));
		}
		return Vector2Pool.obtain(pointX, pointY);
	}
	
	private Vector2 calculateBottomPoint(Vector2 start, Vector2 end) {
		float pointX = 0;
		float pointY = 0;
		float Y = end.x - start.x;
		float X = 0;
		if (end.y > start.y) {
			X = end.y - start.y;
		} else {
			X = start.y - end.y;
		}
		float C = (float) Math.sqrt(X * X + Y * Y);
		float angle = 90 - (float) Math.toDegrees(Math.asin(Y / C));
		System.out.println("Angle = " + angle);

		if (end.y >= start.y) {
			pointX = (float) (start.x - 60 * Math.sin(Math.toRadians(angle)));
			pointY = (float) (start.y + 60 * Math.cos(Math.toRadians(angle)));
		} else {
			pointX = (float) (start.x + 60 * Math.sin(Math.toRadians(angle)));
			pointY = (float) (start.y + 90 * Math.cos(Math.toRadians(angle)));
		}
		return Vector2Pool.obtain(pointX, pointY);
	}
	

}
