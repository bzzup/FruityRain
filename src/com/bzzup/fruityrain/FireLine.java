package com.bzzup.fruityrain;

import org.andengine.entity.primitive.Line;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class FireLine extends Line {
	private static FireLine line;
	
	public static FireLine getInstance(Player player, Enemy enemy) {
		if (line == null) {
			line = new FireLine(player, enemy);
		}
		return line;
	}
	
	public FireLine(Player player, Enemy enemy) {
		super(player.getMyCoordinates().x + 10, player.getMyCoordinates().y + 10, enemy.getMyCoordinates().x, enemy.getMyCoordinates().y, ResourceManager.getInstance().getActivityReference().getVertexBufferObjectManager());
		this.line = this;
	}
	
	public FireLine(float pX1, float pY1, float pX2, float pY2, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX1, pY1, pX2, pY2, pVertexBufferObjectManager);
		this.line = this;
	}

}
