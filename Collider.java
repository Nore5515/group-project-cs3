import java.util.List;

public class Collider {
	
	int x;
	int y;
	
	public Collider (int _x, int _y){
		x = _x;
		y = _y;
	}
	
	public boolean isCollision(int _x, int _y, List<Collider> colliders){
		
		for (int x = 0; x < colliders.size(); x++){
			if (_x == colliders.get(x).getX() && _y == colliders.get(x).getY()){
				System.out.println("Collision at " + _x + "," + _y);
				return true;
			}
		}
		return false;
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
	
}
