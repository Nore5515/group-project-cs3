
public class Player {
	
	//THIS XY COORDS IS FOR GRID
	//WHEN USING COMPARE, MULTIPLY X BY XBUFFER AND Y BY YBUFFER
	int x;
	int y;
	
	public Player (int _x, int _y){
		x = _x;
		y = _y;
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
