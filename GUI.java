
import java.awt.BorderLayout;
import java.awt.CardLayout;
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
	JPanel transition;
	JPanel inventoryScreen;
	JLabel gameOver;
	int gridSize;
	OurKeyListener gui_kList;
	Player play;
	int xBuffer;
	int yBuffer;
	int level;
	
	
	//List of all held items
	List<String> inventory;
	
	//List of all other lists of colliders
	List<List<Collider>> colliders;
	//item 1
	List<Collider> walls;
	//item 0
	List<Collider> coins;
	//item 2
	List<Collider> exits;
	//item 3
	List<Collider> doors;

	Random Rand = new Random();
	
	public GUI(int _gridSize){
		f = new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 500);
		f.setLayout(new BorderLayout());
		
		
		//Set up Transition screen BELOW
		transition = new JPanel(){
			
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				JLabel next = new JLabel("LEVEL " + level);
				this.add(next);
			}
		};
		//Set up transition screen ABOVE
		
		//Set up Inventory screen BELOW
		inventoryScreen = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g) ;
				this.setBackground(Color.YELLOW);
				for (int x = 0; x < inventory.size(); x++){
					f.add(new JLabel(inventory.get(x)));
					System.out.println(inventory.get(x));
				}
			}
		};
		//Set up Inventory screen Above
		
		gridSize = _gridSize;
		xBuffer = (f.getWidth())/(gridSize-1);
		yBuffer = (f.getHeight())/(gridSize-1);

		play = new Player(Math.round(gridSize/2),Math.round(gridSize/2));
		gui_kList = new OurKeyListener(play, this, gridSize);
		gameOver = new JLabel("WINNER");
		gameOver.setFont(new Font("Verdana", 1, 20));;
		level = 0;

		inventory = new ArrayList<String>();
		
		colliders = new ArrayList<List<Collider>>();
		coins = new ArrayList<Collider>();
		walls = new ArrayList<Collider>();
		exits = new ArrayList<Collider>();
		doors = new ArrayList<Collider>();
		//Item 0
		colliders.add(coins);
		//Item 1
		colliders.add(walls);
		//Item 2
		colliders.add(exits);
		//Item 3
		colliders.add(doors);
		
		//Adds Walls
		updateWalls();
		
		//Adds Coins to List
		coins.add(new Collectable(7*xBuffer, 7*yBuffer));
		for (int x = 6; x < 9; x++){
			if (Rand.nextInt(3) == 0){
				coins.add(new Collectable(x*xBuffer,x*yBuffer));
			}
		}
		
		//Adds Exit
		updateExits();
		
		//Adds Doors
		updateDoors();
		
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
				
				
				//Draws coins Below
				g.setColor(Color.YELLOW);
				if (colliders.get(0).size() > 0){
					for (int x = 0; x < colliders.get(0).size(); x++){
						g.fillRect(colliders.get(0).get(x).getX(), colliders.get(0).get(x).getY(), xBuffer, yBuffer);
					}
				}
				//Draws coins Above
				
				
				//Draws Exit Below
				g.setColor(Color.GRAY);
				for (int x = 0; x < colliders.get(2).size(); x++){
					g.fillRect(colliders.get(2).get(x).getX(), colliders.get(2).get(x).getY(), xBuffer, yBuffer);
				}
				//Draws Exit Above
				
				
				//Draws Doors Below
				g.setColor(new Color(186,127,24));
				for (int x = 0; x < colliders.get(3).size(); x++){
					g.fillRect(colliders.get(3).get(x).getX(), colliders.get(3).get(x).getY(), xBuffer, yBuffer);
				}
				//Draws Doors Above
				
				
				//Player Below
				g.setColor(Color.RED);
				g.fillRect(play.getX()*xBuffer, play.getY()*yBuffer, xBuffer, yBuffer);
				//Player Above
				
				
				//Walls Drawn Below
				g.setColor(Color.BLACK);
				for (int x = 0; x < colliders.get(1).size(); x++){
					g.fillRect(colliders.get(1).get(x).getX(), colliders.get(1).get(x).getY(), xBuffer, yBuffer);
				}
				//Walls Drawn Above
				
				
			}	
			//Above is Panel Painting
			
		};
		f.addKeyListener(gui_kList);
		f.add(p);
	}
	
	public JFrame getFrame(){
		return f;
	}
	
	public void updateWalls(){
		walls.clear();
		if (level == 0){
			for (int x = 0; x < gridSize; x++){
				walls.add(new Wall(x*xBuffer, 0));
				walls.add(new Wall(0, x*yBuffer));
				walls.add(new Wall(x*xBuffer, yBuffer*(gridSize-3)));
				walls.add(new Wall(xBuffer*(gridSize-2), x*yBuffer));
			}
		}
		else {
			for (int x = 0; x < gridSize; x++){
				walls.add(new Wall(x*xBuffer, 0));
				walls.add(new Wall(0, x*yBuffer));
				walls.add(new Wall(x*xBuffer, yBuffer*(gridSize-3)));
				walls.add(new Wall(xBuffer*(gridSize-2), x*yBuffer));
			}
			/*for (int x = 0; x < gridSize; x++){
				walls.add(new Wall(x*xBuffer, 6*yBuffer));
				walls.add(new Wall(x*xBuffer, 10*yBuffer));
			}*/
		}
	}
	
	public void addItem(String _name){
		inventory.add(_name);
	}
	
	public void updateExits(){
		exits.clear();
		if (level == 0){
			exits.add(new Exit(8*xBuffer, 8* yBuffer));
		}
		else{
			exits.add(new Exit((level*2)*xBuffer, (level*2*yBuffer)));
		}
	}
	
	public void updateDoors(){
		doors.clear();
		if (level == 0){
			doors.add(new Door(level*6*xBuffer,level*6*yBuffer));
		}
		else{
			doors.add(new Door(level*1*xBuffer,level*1*yBuffer));
		}
	}
	
	public void callRepaint() {
		// TODO Auto-generated method stub
		p.repaint();
	}
	
	public void nextLevel(){
		level++;
		f.remove(p);
		f.add(inventoryScreen);
		inventoryScreen.repaint();
		f.pack();
		f.resize(500, 500);
		try{
			Thread.sleep(1000);
		}
		catch(InterruptedException ex){
		}
		f.remove(inventoryScreen);
		f.add(p);
		System.out.println("LEVEL " + level);
		updateWalls();
		updateDoors();
		updateExits();
	}
	

	
	public List<List<Collider>> getColliders() {
		// TODO Auto-generated method stub
		return colliders;
	}
	
	public void updateColliders(List<List<Collider>> _colliders){
		colliders = _colliders;
	}
	
	
}
