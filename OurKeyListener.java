import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class OurKeyListener implements KeyListener{

	Player p;
	GUI gui;
	int gridSize;
	int xBuffer;
	int yBuffer;
	boolean collided;
	Collider checker;
	
	public OurKeyListener(Player _p, GUI _gui, int _gridSize){
		p = _p;
		gui = _gui;
		gridSize = _gridSize;
		xBuffer = (gui.getFrame().getWidth())/(gridSize-1);
		yBuffer = (gui.getFrame().getHeight())/(gridSize-1);
		collided = false;
		checker = new Collider(-1,-1);
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN){
			updateMovement();
			collided = checker.isCollision(p.getX()*xBuffer, (p.getY()+1)*yBuffer, gui.getColliders());
			
			if (collided == false){
				p.setY(p.getY()+1);
			}
			gui.callRepaint();
			collided = false;
			
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP){
			updateMovement();
			collided = checker.isCollision(p.getX()*xBuffer, (p.getY()-1)*yBuffer, gui.getColliders());
			
			if (collided == false){
				p.setY(p.getY()-1);
			}
			gui.callRepaint();
			collided = false;
			
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT){
			updateMovement();
			collided = checker.isCollision((p.getX()-1)*xBuffer, p.getY()*yBuffer, gui.getColliders());
			
			if (collided == false){
				p.setX(p.getX()-1);
				
			}
			gui.callRepaint();
			collided = false;
			
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			updateMovement();
			collided = checker.isCollision((p.getX()+1)*xBuffer, p.getY()*yBuffer, gui.getColliders());
			
			if (collided == false){
				p.setX(p.getX()+1);
			}
			gui.callRepaint();
			collided = false;
		}
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

	
	
}
