import java.util.List;

public class Exit extends Collider{

	int x;
	int y;
	
	public Exit(int _x, int _y) {
		super(_x, _y);
		x = _x;
		y = _y;
	}
	
	public boolean playerCollision(Player p, int key, List<List<Collider>> colliders, OurKeyListener ourKeyListener){
		
		System.out.println("EXIT HIT PLAYER");
		ourKeyListener.getGUI().nextLevel();
		//removeSelf(colliders);
		
		return false;
	}
	
	public void removeSelf(List<List<Collider>> colliders){
		int index = -1;
		if (colliders.get(2).size() > 0){
			for (int i = 0; i < colliders.get(2).size(); i++){
				System.out.println("Does " + x + "="+colliders.get(2).get(i).getX()+" as well as " + y + "=" + colliders.get(2).get(i).getY());
				if (x == colliders.get(2).get(i).getX() && y == colliders.get(2).get(i).getY()){
					index = i;
				}
			}
		}
		else{
			System.out.println("ERROR: ");
		}
		colliders.get(2).remove(index);
		System.out.println("Collider Removed!");
	}
	

}
