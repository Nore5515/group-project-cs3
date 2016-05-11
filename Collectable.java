import java.util.List;

public class Collectable extends Collider{

	int x;
	int y;
	String name;
	
	public Collectable(int _x, int _y) {
		super(_x, _y);
		
		x = _x;
		y = _y;
		name = "COIN";
		// TODO Auto-generated constructor stub
	}
	
	public boolean playerCollision(Player p, int key, List<Collider> colliders, OurKeyListener ourKeyListener){
		System.out.println("COIN COLLISION WITH PLAYER");
		
		for (int i = colliders.size()-1; i>=0; i--){
			if (colliders.get(i).getX() == x && colliders.get(i).getY() == y){
				colliders.remove(colliders.get(i));
				System.out.println("Collider Removed!");
				i = -1;
			}
		}
		ourKeyListener.updateColliders(colliders);
		//if true, stop moving
		return false;
	}
	
	public String name(){
		return name;
	}
	
	
	
}
