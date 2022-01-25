
// import java.awt.event.KeyEvent;
import java.util.List;

public class Wall extends Collider {

	String wallName;

	public Wall(int _x, int _y) {
		super(_x, _y);
		wallName = "WALL";
	}

	@Override
	public boolean playerCollision(Player p, int key, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {
		System.out.println("WALL COLLISION WITH PLAYER");

		// if true, stop moving
		return true;
	}

	@Override
	public String name() {
		return wallName;
	}

}
