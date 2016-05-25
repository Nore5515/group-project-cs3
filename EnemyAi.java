import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class EnemyAi implements KeyListener{

	Enemy e;
	Player p;
	GUI gui;
	int gridSize;
	int xBuffer;
	int yBuffer;
	//fer edit
	int xpos;
	int ypos;
	//
	boolean collided;
	List<List<Collider>> colliders;
	List<Collider> enemy1;
	
	public EnemyAi(Player _p, Enemy _e, GUI _gui, List<List<Collider>> _colliders, int _gridSize){
		p =_p;
		e = _e;
		gui = _gui;
		gridSize = _gridSize;
		xBuffer = (gui.getFrame().getWidth())/(gridSize-1);
		yBuffer = (gui.getFrame().getHeight())/(gridSize-1);
		collided = false;
		colliders = _colliders;
		//System.out.println(colliders);
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		updateMovement();
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN||arg0.getKeyCode() == KeyEvent.VK_UP||arg0.getKeyCode() == KeyEvent.VK_LEFT||arg0.getKeyCode() == KeyEvent.VK_RIGHT){

				nextMove();
		}
		gui.callRepaint();
		collided = false;
	}

	public boolean isCollision(int _x, int _y, List<List<Collider>> _colliders, Enemy e, int key){
		colliders = _colliders;
		for (int x = 0; x < colliders.size(); x++){
			for (int y = 0; y < colliders.get(x).size(); y++){
				if (_x == colliders.get(x).get(y).getX() && _y == colliders.get(x).get(y).getY()){
				//	return colliders.get(x).get(y).enemyCollision(e, key, colliders, this);
				}
			}
			
		}
		return false;
	}
	public void nextMove(){			
		int valX= p.getX()-e.getX();
		int valY= p.getY()-e.getY();
		boolean movbuffcount = false;
		boolean cantmove = false;
		if (valX < 3 && valX > -3 && valY <3 && valY <-3){
			if(movbuffcount == false){
				if (Math.abs(valX) < Math.abs(valY)){
							if(valX < 0){
								//enemy moves left
								cantmove = e.canEnemynotMoveLeft(1,xBuffer,colliders);
								if(cantmove = false){	
									moveEnemy(-1,0);
									e.movedaEnemy(-1,0,xBuffer,yBuffer,colliders);
								}
								cantmove = false;
							}
							else if (valX > 0){
								//enemy moves right
								cantmove = e.canEnemynotMoveRight(1,xBuffer,colliders);
								if (cantmove = false){
									moveEnemy(1,0);
									e.movedaEnemy(1,0,xBuffer,yBuffer,colliders);
								}
								cantmove = false;
							}
							else{
								if(valY < 0){
								//enemy moves down
								cantmove = e.canEnemynotMoveDown(-1,yBuffer,colliders);
									if (cantmove = false){
										moveEnemy(0,-1);
										e.movedaEnemy(0,-1,xBuffer,yBuffer,colliders);	
									}
									cantmove = false;
								}
								else{
								//enemy moves up
								cantmove = e.canEnemynotMoveUp(-1,yBuffer,colliders);
									if (cantmove = false){
										moveEnemy(0,1);
										e.movedaEnemy(0,1,xBuffer,yBuffer,colliders);	
									}
									cantmove = false;	
								}		
							}
							movbuffcount = false;	
						}
					else if (Math.abs(valY) < Math.abs(valX)){
							if(valY < 0){
								//enemy moves down
								cantmove = e.canEnemynotMoveDown(-1,yBuffer,colliders);
									if (cantmove = false){
										moveEnemy(0,-1);
										e.movedaEnemy(0,-1,xBuffer,yBuffer,colliders);	
									}
									cantmove = false;
								}
							}
							else if (valY > 0){
								//enemy moves up
								cantmove = e.canEnemynotMoveUp(-1,yBuffer,colliders);
									if (cantmove = false){
										moveEnemy(0,1);
										e.movedaEnemy(0,1,xBuffer,yBuffer,colliders);	
									}
									cantmove = false;	
							}
							else{
								if(valX < 0){
								//enemy moves left
								cantmove = e.canEnemynotMoveLeft(1,xBuffer,colliders);
									if(cantmove = false){	
										moveEnemy(-1,0);
										e.movedaEnemy(-1,0,xBuffer,yBuffer,colliders);
									}
									cantmove = false;
								}
								else{
								//enemy moves right
								cantmove = e.canEnemynotMoveRight(1,xBuffer,colliders);
									if (cantmove = false){
										moveEnemy(1,0);
										e.movedaEnemy(1,0,xBuffer,yBuffer,colliders);
									}
									cantmove = false;
									}	
							}
							movbuffcount = false;	
						}
					else if(valY ==0 && valX ==0){
							//System.out.println("Enemy hits on player");	
							movbuffcount =true;
						}
					else {
							//enemy moves right
							cantmove = e.canEnemynotMoveRight(1,xBuffer,colliders);
								if (cantmove = false){
									moveEnemy(1,0);
									e.movedaEnemy(1,0,xBuffer,yBuffer,colliders);
								}
								cantmove = false;	
					   	 }
			}
			else{
				//enemy moves right
				cantmove = e.canEnemynotMoveRight(1,xBuffer,colliders);
					if (cantmove = false){
						moveEnemy(1,0);
						e.movedaEnemy(1,0,xBuffer,yBuffer,colliders);
					}
				cantmove = false;	
			}
		
	}			
	public void moveEnemy(int x, int y){
		/*int index = -1;
		for (int i = 0; i < colliders.get(4).size(); i++){
			System.out.println("Does " + x + "="+colliders.get(4).get(i).getX()+" as well as " + y + "=" + colliders.get(3).get(i).getY());
			if (x == colliders.get(4).get(i).getX() && y == colliders.get(4).get(i).getY()){
				index = i;
			}
		}*/
		e.setX(e.getX()+x);
		e.setY(e.getY()+y);
		//colliders.get(4).get(index).setX((colliders.get(4).get(index).getX()+x)*xBuffer);
		//colliders.get(4).get(index).setY((colliders.get(4).get(index).getY()+y)*yBuffer);
		//System.out.println("enemy at:"+e.getX()+","+e.getY());
		
	}
	
	
	public void updateMovement(){
		xBuffer = (gui.getFrame().getWidth())/(gridSize-1);
		yBuffer = (gui.getFrame().getHeight())/(gridSize-1);
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub0
		
	}

	public void updateColliders(List<List<Collider>> _colliders) {
		colliders = _colliders;
	}
	public void updateEnemy(List<Collider> _enemy1){
		enemy1 = _enemy1;
	}

	public GUI getGUI(){
		return gui;
	}
	
}
