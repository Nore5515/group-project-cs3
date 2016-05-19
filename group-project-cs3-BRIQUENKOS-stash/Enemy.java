import java.util.List;

public class Enemy extends Collider{
	
	int x;
	int y;
	
	public Enemy (int _x, int _y){
		super(_x,_y);
		x = _x;
		y = _y;
	}
}
