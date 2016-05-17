import java.util.List;

public class Collectable extends Collider{

	int x;
	int y;
	String name;
	
	public Collectable(int _x, int _y) {
		super(_x, _y);
		
		x = _x;
		y = _y;
		name = "Coin";
		// TODO Auto-generated constructor stub
	}
	
	public boolean playerCollision(Player p, int key, List<List<Collider>> colliders, OurKeyListener ourKeyListener){
		System.out.println("COIN COLLISION WITH PLAYER");
		
		ourKeyListener.getGUI().addItem(name);
		
		removeSelf(colliders);
		
		ourKeyListener.updateColliders(colliders);
		ourKeyListener.getGUI().updateColliders(colliders);
		//if true, stop moving
		return false;
	}
	
	public String getName(){
		return name;
	}
	
	public void removeSelf(List<List<Collider>> colliders){
		int index = -1;
		for (int i = 0; i < colliders.get(0).size(); i++){
			System.out.println("Does " + x + "="+colliders.get(0).get(i).getX()+" as well as " + y + "=" + colliders.get(0).get(i).getY());
			if (x == colliders.get(0).get(i).getX() && y == colliders.get(0).get(i).getY()){
				index = i;
			}
		}
		colliders.get(0).remove(index);
		System.out.println("Collider Removed!");
	}
	
	
	
}
