import java.util.List;

public class Collectable extends Collider {

	int collectableX;
	int collectableY;
	String collectableName;

	public Collectable(int _x, int _y, String _name) {
		super(_x, _y);

		collectableX = _x;
		collectableY = _y;
		collectableName = _name;
	}

	@Override
	public boolean playerCollision(Player p, int key, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {
		System.out.println("OBJECT COLLISION WITH PLAYER");

		ourKeyListener.getGUI().addItem(collectableName);

		removeSelf(colliders);

		ourKeyListener.updateColliders(colliders);
		ourKeyListener.getGUI().updateColliders(colliders);
		// if true, stop moving

		return false;
	}

	public String getName() {
		return collectableName;
	}

	public void removeSelf(List<List<Collider>> colliders) {
		int index = -1;
		for (int i = 0; i < colliders.get(0).size(); i++) {
			// System.out.println("Does " + x + "="+colliders.get(0).get(i).getX()+" as well
			// as " + y + "=" + colliders.get(0).get(i).getY());
			if (collectableX == colliders.get(0).get(i).getX() && collectableY == colliders.get(0).get(i).getY()) {
				index = i;
			}
		}
		colliders.get(0).remove(index);
		System.out.println("Collider Removed!");
	}

}
