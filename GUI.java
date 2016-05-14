
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.*;

public class GUI {
	
	JFrame f;
	JPanel p;
	JLabel gameOver;
	int gridSize;
	OurKeyListener gui_kList;
	EnemyAi gui_eList;
	Player play;
	Enemy enemy;
	int xBuffer;
	int yBuffer;
	
	
	//List of all other lists of colliders
	List<List<Collider>> colliders;
	//item 0
	List<Collider> walls;
	//item 1
	List<Collider> coins;

	Random Rand = new Random();
	
	public GUI(int _gridSize){
		f = new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 500);

		gridSize = _gridSize;
		xBuffer = (f.getWidth())/(gridSize-1);
		yBuffer = (f.getHeight())/(gridSize-1);

		play = new Player(Math.round(gridSize/2),Math.round(gridSize/2));
		enemy = new Enemy(Math.round(gridSize/2),Math.round(gridSize/2));
		gui_kList = new OurKeyListener(play, this, gridSize);
		gui_eList = new EnemyAi(enemy, this, gridSize);
		gameOver = new JLabel("WINNER");
		gameOver.setFont(new Font("Verdana", 1, 20));;


		colliders = new ArrayList<List<Collider>>();
		coins = new ArrayList<Collider>();
		walls = new ArrayList<Collider>();
		//Item 0
		colliders.add(coins);
		//Item 1
		colliders.add(walls);
		
		//Adds Walls to List
		for (int x = 0; x < gridSize; x++){
			walls.add(new Wall(x*xBuffer, 0));
			walls.add(new Wall(0, x*yBuffer));
			walls.add(new Wall(x*xBuffer, yBuffer*(gridSize-3)));
			walls.add(new Wall(xBuffer*(gridSize-2), x*yBuffer));
		}	
		//Adds Coins to List
		coins.add(new Collectable(7*xBuffer, 7*yBuffer));
		for (int x = 3; x < 9; x++){
			if (Rand.nextInt(3) == 0){
				coins.add(new Collectable(x*xBuffer,x*2*yBuffer));
			}
		}
		
		
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
				
				
				//Adds coins Below
				g.setColor(Color.YELLOW);
				if (colliders.get(0).size() > 0){
					for (int x = 0; x < colliders.get(0).size(); x++){
						g.fillRect(colliders.get(0).get(x).getX(), colliders.get(0).get(x).getY(), xBuffer, yBuffer);
					}
				}
				//Adds coins Above
				
				
				//Player Below
				g.setColor(Color.RED);
				g.fillRect(play.getX()*xBuffer, play.getY()*yBuffer, xBuffer, yBuffer);
				//Player Above
				
				//Enemy Below
				g.setColor(Color.BLUE);
				g.fillRect(enemy.getX()*xBuffer/*-(3*xBuffer)*/, enemy.getY()*yBuffer/*-(3*yBuffer)*/, xBuffer, yBuffer);
				//Enemy Above

				//Walls Below
				g.setColor(Color.BLACK);
				for (int x = 0; x < gridSize; x++){
					g.fillRect(x*xBuffer, 0, xBuffer, yBuffer);
					g.fillRect(0, x*yBuffer, xBuffer, yBuffer);
					g.fillRect(x*xBuffer, yBuffer*(gridSize-3), xBuffer, yBuffer);
					g.fillRect(xBuffer*(gridSize-2), x*yBuffer, xBuffer, yBuffer);
				}
				//Walls Above
				
			}	
			//Above is Panel Painting
		};
		f.addKeyListener(gui_kList);
		f.addKeyListener(gui_eList);
		f.add(p);
	}
	
	public JFrame getFrame(){
		return f;
	}
	
	public void callRepaint() {
		// TODO Auto-generated method stub
		p.repaint();
	}
	
	public List<List<Collider>> getColliders() {
		// TODO Auto-generated method stub
		return colliders;
	}
	
	public void updateColliders(List<List<Collider>> _colliders){
		colliders = _colliders;
	}
	
}
