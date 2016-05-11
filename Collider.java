import java.util.List;

public class Collider {
	
	int x;
	int y;
	String name;
	
	public Collider (int _x, int _y){
		x = _x;
		y = _y;
		name = "DEFAULT";
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int _x){
		x = _x;
	}
	public void setY(int _y){
		y = _y;
	}

	//GENERIC COLLISION
	public boolean playerCollision(Player p, int key, List<Collider> colliders, OurKeyListener ourKeyListener) {
		System.out.println("DEFAULT COLLIDER HIT PLAYER");
		//if true, stop moving
		return false;
	}
	
	public String name(){
		return name;
	}
	
	
	
}
