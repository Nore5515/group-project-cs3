import java.util.List;

public class Enemy extends Collider{
	
	int x;
	int y;
	int ehp = 0;
	
	public Enemy (int _x, int _y){
		super(_x,_y);
		x = _x;
		y = _y;
	}
	public boolean playerCollision(Player p, int key, List<List<Collider>> colliders, OurKeyListener ourKeyListener){
		System.out.println("ENEMY HITS ON PLAYER ");
		if(ehp == 2){
		//removds enemy if hit by player 3 times (not a very gud hp system :( )
		removeSelf(colliders);	
		}
		ehp++;
		
		return true;
	}
	public boolean enemyCollision(Enemy e, int key, List<List<Collider>> colliders, EnemyAi enemyai){
		
		System.out.println("ENEMY HITS WALL");
		
		return true;
	}
	public void removeSelf(List<List<Collider>> colliders){
		//removes enemy
		int index = -1;
		for (int i = 0; i < colliders.get(4).size(); i++){
			System.out.println("Does " + x + "="+colliders.get(4).get(i).getX()+" as well as " + y + "=" + colliders.get(3).get(i).getY());
			if (x == colliders.get(4).get(i).getX() && y == colliders.get(4).get(i).getY()){
				index = i;
			}
		}
		colliders.get(4).remove(index);

		
		System.out.println("Collider Removed!");
		//System.out.println("Collider Moved to:"+colliders.get(4).get(index).getX()+","+colliders.get(4).get(index).getY());
	}
	//test pls
	public void movedaEnemy(int x1,int y1,int xBuffer,int yBuffer, List<List<Collider>> colliders){
	//moves collider(not player)
		colliders.get(4).get(0).setX(colliders.get(4).get(0).getX()+(x1*xBuffer));
		colliders.get(4).get(0).setY(colliders.get(4).get(0).getY()+(y1*yBuffer));
		System.out.println("enemy pos:"+colliders.get(4).get(0).getX()+","+colliders.get(4).get(0).getY());
		
	}
	public boolean canEnemynotMoveRight(int x1,int xBuffer, List<List<Collider>> colliders){
		for (int i = 0; i < colliders.size(); i++){
			System.out.println(colliders.get(i));
				if(colliders.get(i).get(0).getX() == colliders.get(4).get(0).getX()+(x1*xBuffer)){
					return true;
				}
		}
		return false;
	}
	public boolean canEnemynotMoveLeft(int x1,int xBuffer, List<List<Collider>> colliders){
		for (int i = 0; i < colliders.size(); i++){
			System.out.println(colliders.get(i));
				if(colliders.get(i).get(0).getX() == colliders.get(4).get(0).getX()-(x1*xBuffer)){
					return true;
				}
		}
		return false;
	}
	public boolean canEnemynotMoveUp(int y1,int yBuffer, List<List<Collider>> colliders){
		for (int i = 0; i < colliders.size(); i++){
			System.out.println(colliders.get(i));
				if(colliders.get(i).get(0).getY() == colliders.get(4).get(0).getY()+(y1*yBuffer)){
					return true;
				}
		}
		return false;
	}
	public boolean canEnemynotMoveDown(int y1,int yBuffer, List<List<Collider>> colliders){
		for (int i = 0; i < colliders.size(); i++){
			System.out.println(colliders.get(i));
				if(colliders.get(i).get(0).getY() == colliders.get(4).get(0).getY()+(y1*yBuffer)){
					return true;
				}
		}
		return false;
	}
}	
