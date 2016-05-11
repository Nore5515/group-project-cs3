
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import javax.swing.*;

public class GUI {
	
	JFrame f;
	JPanel p;
	int gridSize;
	OurKeyListener gui_kList;
	Player play;
	List<Collider> colliders;
	int xBuffer;
	int yBuffer;
	
	public GUI(int _gridSize){
		f = new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 500);
		
		gridSize = _gridSize;
		xBuffer = (f.getWidth())/(gridSize-1);
		yBuffer = (f.getHeight())/(gridSize-1);

		colliders = new ArrayList<Collider>();
		play = new Player(Math.round(gridSize/2),Math.round(gridSize/2));
		gui_kList = new OurKeyListener(play, this, gridSize);

		System.out.println("Work?");
		p = new JPanel(){
			//Below is Panel Painting
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				
				xBuffer = (f.getWidth())/(gridSize-1);
				yBuffer = (f.getHeight())/(gridSize-1);
				
				//New Graph Below
				for (int x = 0; x < gridSize; x++){
					g.drawLine(x*xBuffer, 0, x*xBuffer, f.getHeight());
				}
				for (int y = 0; y < gridSize; y++){
					g.drawLine(0, y*yBuffer, f.getWidth(), y*yBuffer);
				}
				//New Graph Above
				
				
				//Adding a coin below
				g.setColor(Color.YELLOW);
				g.fillRect(7*xBuffer, 7*xBuffer, xBuffer, yBuffer);
				addCollider(new Collectable(7*xBuffer, 7*xBuffer));
				//Adding a coin above
				
				
				//Player Below
				g.setColor(Color.RED);
				g.fillRect(play.getX()*xBuffer, play.getY()*yBuffer, xBuffer, yBuffer);
				//Player Above

				
				//Walls Below
				g.setColor(Color.BLACK);
				for (int x = 0; x < gridSize; x++){
					g.fillRect(x*xBuffer, 0, xBuffer, yBuffer);
					colliders.add(new Wall(x*xBuffer, 0));
					addCollider(new Wall(x*xBuffer, 0));
					
					g.fillRect(0, x*yBuffer, xBuffer, yBuffer);
					colliders.add(new Wall(0, x*yBuffer));
					addCollider(new Wall(0, x*yBuffer));
					
					g.fillRect(x*xBuffer, yBuffer*(gridSize-3), xBuffer, yBuffer);
					colliders.add(new Wall(x*xBuffer, yBuffer*(gridSize-3)));
					addCollider(new Wall(x*xBuffer, yBuffer*(gridSize-3)));
					
					g.fillRect(xBuffer*(gridSize-2), x*yBuffer, xBuffer, yBuffer);
					colliders.add(new Wall(xBuffer*(gridSize-2), x*yBuffer));
					addCollider(new Wall(xBuffer*(gridSize-2), x*yBuffer));
				}
				//Walls Above
				
			}	
			//Above is Panel Painting
		};
		f.addKeyListener(gui_kList);
		f.add(p);
	}
	
	public JFrame getFrame(){
		return f;
	}

	public void addCollider(Collider c){
		boolean flag = false;
		for (int i = 0; i < colliders.size(); i++){
			if (colliders.get(i).getX() == c.getX() && colliders.get(i).getY() == c.getY()){
				flag = true;
			}
		}
		if (!flag){
			colliders.add(c);
		}
		
	}
	
	public void callRepaint() {
		// TODO Auto-generated method stub
		p.repaint();
	}

	public List<Collider> getColliders() {
		// TODO Auto-generated method stub
		return colliders;
	}
	
	
}
