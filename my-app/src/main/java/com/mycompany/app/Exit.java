package com.mycompany.app;

import java.util.List;

public class Exit extends Collider {

	int exitX;
	int exitY;

	public Exit(int _x, int _y) {
		super(_x, _y);
		exitX = _x;
		exitY = _y;
	}

	@Override
	public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {

		System.out.println("EXIT HIT PLAYER");
		ourKeyListener.getGUI().nextLevel();
		// removeSelf(colliders);

		return false;
	}

	public void removeSelf(List<List<Collider>> colliders) {
		int index = -1;
		if (!colliders.get(2).isEmpty()) {
			for (int i = 0; i < colliders.get(2).size(); i++) {
				System.out.println("Does " + exitX + "=" + colliders.get(2).get(i).getX() + " as well as " + exitY + "="
						+ colliders.get(2).get(i).getY());
				if (exitX == colliders.get(2).get(i).getX() && exitY == colliders.get(2).get(i).getY()) {
					index = i;
				}
			}
		} else {
			System.out.println("ERROR: ");
		}
		colliders.get(2).remove(index);
		System.out.println("Collider Removed!");
	}

}
