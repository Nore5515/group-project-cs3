package com.mycompany.app;

// import java.awt.event.KeyEvent;
import java.util.List;

public class Wall extends Collider {

	String wallName;

	public Wall(int _x, int _y) {
		super(_x, _y);
		wallName = "WALL";
	}

	// public boolean playerCollision(Player p, int key, List<List<Collider>>
	// colliders, OurKeyListener ourKeyListener) {
	// Oh this is so stupid i override it depending on the object...

	@Override
	public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {
		// System.out.println("WALL COLLISION WITH PLAYER");

		// if true, stop moving
		return true;
	}

	public boolean destroySelf(List<List<Collider>> colliders) {
		int index = -1;
		for (int i = 0; i < colliders.get(1).size(); i++) {
			if (x == colliders.get(1).get(i).getX() && y == colliders.get(1).get(i).getY()) {
				index = i;
			}
		}
		colliders.get(1).remove(index);
		return true;
	}

	@Override
	public String name() {
		return wallName;
	}

}
