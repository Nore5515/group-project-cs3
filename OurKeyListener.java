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
	List<Collider> colliders;
	
	public OurKeyListener(Player _p, GUI _gui, int _gridSize){
		p = _p;
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

	public boolean isCollision(int _x, int _y, List<Collider> _colliders, Player p, int key){
		colliders = _colliders;
		for (int x = 0; x < colliders.size(); x++){
			if (_x == colliders.get(x).getX() && _y == colliders.get(x).getY()){
				return colliders.get(x).playerCollision(p, key, colliders, this);
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
	}
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void updateColliders(List<Collider> _colliders) {
		colliders = _colliders;
		for (int i = 0; i < colliders.size(); i++){
			System.out.println("/"+colliders.get(i).name());
		}
		System.out.println("/"+colliders.size());
	}

	
	
}
