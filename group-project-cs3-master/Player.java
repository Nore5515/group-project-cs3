
public class Player {
	
	int x;
	int y;
	int hp;
	int maxHP;
	
	public Player (int _x, int _y){
		x = _x;
		y = _y;
		maxHP = 10;
		hp = maxHP;
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getHP(){
		return hp;
	}
	public void setX(int _x){
		x = _x;
	}
	public void setY(int _y){
		y = _y;
	}
	//returns true if alive, false if ded
	public boolean setHP(int _hp){
		hp = _hp;
		if (hp <= 0){
			return false;
		}
		if (hp > maxHP){
			hp = maxHP;
		}
		return true;
	}
	
}
