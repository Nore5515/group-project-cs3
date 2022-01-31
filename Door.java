import java.util.List;

public class Door extends Collider {

	int doorX;
	int doorY;

	public Door(int _x, int _y) {
		super(_x, _y);
		doorX = _x;
		doorY = _y;
	}

	// public boolean playerCollision(Player p, int key, List<List<Collider>>
	// colliders, OurKeyListener ourKeyListener) {
	// public boolean playerCollision(List<List<Collider>> colliders) {
	@Override
	public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {

		System.out.println("DOOR HIT PLAYER");
		removeSelf(colliders);

		return true;
	}

	public void removeSelf(List<List<Collider>> colliders) {
		int index = -1;
		for (int i = 0; i < colliders.get(3).size(); i++) {
			System.out.println("Does " + x + "=" + colliders.get(3).get(i).getX() + " as well as " + y + "="
					+ colliders.get(3).get(i).getY());
			if (x == colliders.get(3).get(i).getX() && y == colliders.get(3).get(i).getY()) {
				index = i;
			}
		}
		colliders.get(3).remove(index);
		System.out.println("Collider Removed!");
	}

}
