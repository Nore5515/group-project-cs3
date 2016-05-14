import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class EnemyAi implements KeyListener{

	Enemy e;
	GUI gui;
	int gridSize;
	int xBuffer;
	int yBuffer;
	boolean collided;
	List<List<Collider>> colliders;
	
	public EnemyAi(Enemy _e, GUI _gui, int _gridSize){
		e = _e;
		gui = _gui;
		gridSize = _gridSize;
		xBuffer = (gui.getFrame().getWidth())/(gridSize-1);
		yBuffer = (gui.getFrame().getHeight())/(gridSize-1);
		collided = false;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		updateMovement();
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN||arg0.getKeyCode() == KeyEvent.VK_UP||arg0.getKeyCode() == KeyEvent.VK_LEFT||arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			collided = isCollision(e.getX()*xBuffer, (e.getY()+1)*yBuffer, gui.getColliders(), e, KeyEvent.VK_DOWN);
			collided = isCollision(e.getX()*xBuffer, (e.getY()-1)*yBuffer, gui.getColliders(), e, KeyEvent.VK_UP);
			collided = isCollision((e.getX()-1)*xBuffer, e.getY()*yBuffer, gui.getColliders(), e, KeyEvent.VK_LEFT);
			collided = isCollision((e.getX()+1)*xBuffer, e.getY()*yBuffer, gui.getColliders(), e, KeyEvent.VK_RIGHT);
			if(!collided){
				moveEnemy(1,0);
			}
		}
		
		gui.callRepaint();
		collided = false;
	}

	public boolean isCollision(int _x, int _y, List<List<Collider>> _colliders, Enemy e, int key){
		colliders = _colliders;
		for (int x = 0; x < colliders.size(); x++){
			for (int y = 0; y < colliders.get(x).size(); y++){
				if (_x == colliders.get(x).get(y).getX() && _y == colliders.get(x).get(y).getY()){
					return colliders.get(x).get(y).enemyCollision(e, key, colliders, this);
				}
			}
			
		}
		return false;
	}
	
	public void moveEnemy(int x, int y){
		e.setX(e.getX()+x);
		e.setY(e.getY()+y);
		//gui.updateEnemy(e.getX(),e.getY());
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
		// TODO Auto-generated method stub
		
	}

	public void updateColliders(List<List<Collider>> _colliders) {
		colliders = _colliders;
	}

	public GUI getGUI(){
		return gui;
	}
	
}
