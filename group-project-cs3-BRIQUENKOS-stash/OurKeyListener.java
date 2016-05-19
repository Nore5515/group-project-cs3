import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class OurKeyListener implements KeyListener{

	Player p;
	GUI gui;
	int gridSize;
	int xBuffer;
	int yBuffer;
	boolean collided;
	List<List<Collider>> colliders;
	List<List<Collider>> enemies;
	List<Enemy> enemyList;
	
	public OurKeyListener(Player _p, GUI _gui, int _gridSize){
		p = _p;
		gui = _gui;
		gridSize = _gridSize;
		xBuffer = (gui.getFrame().getWidth())/(gridSize-1);
		yBuffer = (gui.getFrame().getHeight())/(gridSize-1);
		collided = false;
		enemyList = new ArrayList<Enemy>();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		updateMovement();
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN){
			collided = isCollision(p.getX()*xBuffer, (p.getY()+1)*yBuffer, gui.getColliders(), p, KeyEvent.VK_DOWN);
			if (!collided){
				movePlayer(0,1);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP){
			collided = isCollision(p.getX()*xBuffer, (p.getY()-1)*yBuffer, gui.getColliders(), p, KeyEvent.VK_UP);
			if (!collided){
				movePlayer(0,-1);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT){
			collided = isCollision((p.getX()-1)*xBuffer, p.getY()*yBuffer, gui.getColliders(), p, KeyEvent.VK_LEFT);
			if (!collided){
				movePlayer(-1,0);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			collided = isCollision((p.getX()+1)*xBuffer, p.getY()*yBuffer, gui.getColliders(), p, KeyEvent.VK_RIGHT);
			if (!collided){
				movePlayer(1,0);
			}
		}
		
		gui.callRepaint();
		collided = false;
	}

	public boolean isCollision(int _x, int _y, List<List<Collider>> _colliders, Player p, int key){
		colliders = _colliders;
		for (int x = 0; x < colliders.size(); x++){
			for (int y = 0; y < colliders.get(x).size(); y++){
				if (_x == colliders.get(x).get(y).getX() && _y == colliders.get(x).get(y).getY()){
					return colliders.get(x).get(y).playerCollision(p, key, colliders, this);
				}
			}
			
		}
		for (int x = 0; x < enemyList.size(); x++){
			System.out.println(p.getX() +","+ p.getY());
			if (p.getX() == enemyList.get(x).getX() && p.getY() == enemyList.get(x).getY()){
				System.out.println("NOSIR");
				return true;
			}
		}
		
		
		return false;
	}
	
	public void movePlayer(int x, int y){
		p.setX(p.getX()+x);
		p.setY(p.getY()+y);
	}
	
	
	public void updateMovement(){
		xBuffer = (gui.getFrame().getWidth())/(gridSize-1);
		yBuffer = (gui.getFrame().getHeight())/(gridSize-1);
		gui.updateEnemies();
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
	
	public void updateEnemies(List<Enemy> _enemyList){
		enemyList = _enemyList;
	}
	public GUI getGUI(){
		return gui;
	}
	
}
