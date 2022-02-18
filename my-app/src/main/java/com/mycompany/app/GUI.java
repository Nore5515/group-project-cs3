package com.mycompany.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.Iterator;

import javax.swing.*;

public class GUI {

	protected static final Component JLabel = null;
	JFrame f;
	JPanel p;
	JPanel transition;
	// JPanel inventoryScreen;
	JPanel statusScreen;
	JLabel gameOver;
	int gridSize;
	OurKeyListener guiKList;
	Player play;
	int xBuffer;
	int yBuffer;
	int level;
	int sightRange;
	int torchHealth;
	boolean torchUsed;
	boolean coinUsed;

	// Consts
	private static final String TORCH = "Torch";
	private static final String COIN = "Coin";

	// List of all held items
	List<String> inventory;

	// List of all other lists of colliders
	List<List<Collider>> colliders;
	// item 1
	List<Collider> walls;
	// item 0
	List<Collider> items;
	// item 2
	List<Collider> exits;
	// item 3
	List<Collider> doors;
	// item 4
	List<Collider> bombs;

	Random rand = new Random();

	public JSONArray getWallJSONArray() {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(
					"/Users/noah/projects/java/group-project-cs3/my-app/src/main/java/com/mycompany/app/levels.json"));

			JSONObject jsonFile = (JSONObject) obj;
			JSONObject level0 = (JSONObject) jsonFile.get("Level0");
			return (JSONArray) level0.get("walls");
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray();
		}
	}

	public JSONArray getStringJSONArray(String levelTitle) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(
					"/Users/noah/projects/java/group-project-cs3/my-app/src/main/java/com/mycompany/app/levels.json"));

			JSONObject jsonFile = (JSONObject) obj;
			JSONObject level0 = (JSONObject) jsonFile.get(levelTitle);
			return (JSONArray) level0.get("rows");
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONArray();
		}
	}

	public List<int[]> getWallPositionArray(JSONArray jArray) {
		ArrayList<int[]> wallArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == '#') {
					wallArray.add(new int[] { y, x });
				}
			}
		}
		return wallArray;
	}

	public List<int[]> getExitPositionArray(JSONArray jArray) {
		ArrayList<int[]> exitArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'E') {
					exitArray.add(new int[] { y, x });
				}
			}
		}
		return exitArray;
	}

	public List<int[]> getDoorPositionArray(JSONArray jArray) {
		ArrayList<int[]> doorArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'D') {
					doorArray.add(new int[] { y, x });
				}
			}
		}
		return doorArray;
	}

	public List<int[]> getCoinPositionArray(JSONArray jArray) {
		ArrayList<int[]> coinArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'c') {
					coinArray.add(new int[] { y, x });
				}
			}
		}
		return coinArray;
	}

	public List<int[]> getTorchPositionArray(JSONArray jArray) {
		ArrayList<int[]> torchArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 't') {
					torchArray.add(new int[] { y, x });
				}
			}
		}
		return torchArray;
	}

	public List<int[]> getSpawnPositionArray(JSONArray jArray) {
		ArrayList<int[]> spawnArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'P') {
					spawnArray.add(new int[] { y, x });
				}
			}
		}
		return spawnArray;
	}

	public GUI(int _gridSize) {
		// decodeJson();

		f = new JFrame();
		f.setVisible(true);
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		f.setSize(500, 500);
		f.setLayout(new BorderLayout());

		// Set up Transition screen BELOW
		transition = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.DARK_GRAY);
				g.fillRect(0, 0, this.getWidth(), this.getHeight() - 200);
				JLabel next = new JLabel("LEVEL " + level);
				this.add(next);
			}
		};
		// Set up transition screen ABOVE

		inventory = new ArrayList<>();
		inventory.add("a) Torch");
		inventory.add("b) Red Potion");
		inventory.add("c) Blue Potion");

		// Set up Inventory screen BELOW
		InventoryScreen inventoryScreen = new InventoryScreen(inventory);
		// Set up Inventory screen Above

		gridSize = _gridSize;
		xBuffer = (f.getWidth()) / (gridSize - 1);
		yBuffer = (f.getHeight()) / (gridSize - 1);

		torchUsed = false;
		torchHealth = 100;
		coinUsed = false;

		play = new Player(gridSize / 2, gridSize / 2);
		// play = new Player(Math.round(gridSize / 2), Math.round(gridSize / 2));
		guiKList = new OurKeyListener(play, this, gridSize);
		gameOver = new JLabel("WINNER");
		gameOver.setFont(new Font("Verdana", 1, 20));
		level = 0;
		sightRange = 3;

		inventory = new ArrayList<>();

		colliders = new ArrayList<>();
		items = new ArrayList<>();
		walls = new ArrayList<>();
		exits = new ArrayList<>();
		doors = new ArrayList<>();
		// Item 0
		colliders.add(items);
		// Item 1
		colliders.add(walls);
		// Item 2
		colliders.add(exits);
		// Item 3
		colliders.add(doors);

		// Status Screen
		statusScreen = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				this.setBackground(Color.LIGHT_GRAY);

				int numLists = 0;

				// Displays Coords
				g.setFont(new Font("Arial", 0, 20));
				g.drawString("HP: " + play.hp + "/" + play.maxHP, 10, 20);

				// Lists for Stackable Items (ex. Potions, Coins, etc)
				List<String> Coins = new ArrayList<>();
				List<String> Torches = new ArrayList<>();

				List<Command> commandItems = new ArrayList<>();

				// Adds items to Stackable Items lists
				for (int x = 0; x < inventory.size(); x++) {
					if (inventory.get(x).compareTo(COIN) == 0) {
						Coins.add(inventory.get(x));
					}
					if (inventory.get(x).compareTo(TORCH) == 0) {
						Torches.add(inventory.get(x));
					}

				}

				if (!Coins.isEmpty()) {
					g.drawString(String.valueOf((char) (numLists + 65)) + ") Coins: " + Coins.size(), 120,
							20 * (numLists + 1));
					numLists++;
					commandItems.add(new Command("Coin", String.valueOf((char) (numLists + 64)), getGUI()));
				}
				Command c = null;
				if (!Torches.isEmpty()) {
					g.drawString(String.valueOf((char) (numLists + 65)) + ") Torches: " + Torches.size(), 120,
							20 * (numLists + 1));
					g.drawString("Torch Life: " + torchHealth, 270, 20 * (numLists + 1));
					numLists++;
					c = new Command(TORCH, String.valueOf((char) (numLists + 64)), getGUI());
					commandItems.add(c);

				}

				// if torch is being used...
				if (torchUsed) {
					torchHealth--;
					if (torchHealth <= 0) {
						System.out.println("Your torch fizzles out..");
						torchUsed = false;
						inventory.remove(TORCH);
						// commandItems.remove(c);
						System.out.println(Torches.size());
						// numLists--;
						// Not currently being used
						torchHealth = 100;
						this.revalidate();
					} else {
						sightRange = 5;
					}
				} else {
					sightRange = 2;
				}

				if (coinUsed) {
					inventory.remove("Coin");
					// numLists--;
					// Not currently being used
					this.revalidate();
					coinUsed = !coinUsed;
				}

				guiKList.updateCommands(commandItems);

			}
		};

		// Adds Walls
		updateWalls();

		// Adds Coins to List
		updateCoins();

		// Add Torches to List
		updateTorches();

		// Adds Exit
		updateExits();

		// Adds Doors
		updateDoors();

		// Add Player Spawn
		updatePlayerSpawn();

		p = new JPanel() {
			// Below is Panel Painting
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				xBuffer = (f.getWidth()) / (gridSize - 1);
				yBuffer = (f.getHeight()) / (gridSize - 1);

				// New Graph Below
				for (int x = 0; x < gridSize; x++) {
					g.drawLine(x * xBuffer, 0, x * xBuffer, f.getHeight());
				}
				for (int y = 0; y < gridSize; y++) {
					g.drawLine(0, y * yBuffer, f.getWidth(), y * yBuffer);
				}
				// New Graph Above

				// Draws coins Below
				g.setColor(Color.YELLOW);
				if (!colliders.get(0).isEmpty()) {
					for (int x = 0; x < colliders.get(0).size(); x++) {
						// System.out.println("Does " +
						// Math.abs((colliders.get(0).get(x).getX()-(play.getX()*xBuffer))/xBuffer) +
						// "<=5 AND " +
						// Math.abs((colliders.get(0).get(x).getY()-(play.getY()*yBuffer))/yBuffer) +
						// "<=5?");
						// if (Math.abs((colliders.get(0).get(x).getX()-(play.getX()*xBuffer))/xBuffer)
						// <= 5 &&
						// Math.abs((colliders.get(0).get(x).getY()-(play.getY()*yBuffer))/yBuffer) <=
						// 5){
						// System.out.println("Found Coin " + x);
						g.fillRect(colliders.get(0).get(x).getX(), colliders.get(0).get(x).getY(), xBuffer, yBuffer);
						// }
						// else{
						// System.out.println("Hidden Coin " + x + "!");
						// }

					}
				}
				// Draws coins Above

				// Draws Exit Below
				g.setColor(Color.GREEN);
				for (int x = 0; x < colliders.get(2).size(); x++) {
					g.fillRect(colliders.get(2).get(x).getX(), colliders.get(2).get(x).getY(), xBuffer, yBuffer);
				}
				// Draws Exit Above

				// Draws Doors Below
				g.setColor(new Color(186, 127, 24));
				for (int x = 0; x < colliders.get(3).size(); x++) {
					g.fillRect(colliders.get(3).get(x).getX(), colliders.get(3).get(x).getY(), xBuffer, yBuffer);
				}
				// Draws Doors Above

				// Player Below
				g.setColor(Color.RED);
				g.fillRect(play.getX() * xBuffer, play.getY() * yBuffer, xBuffer, yBuffer);
				// Player Above

				// Walls Drawn Below
				g.setColor(Color.gray);
				for (int x = 0; x < colliders.get(1).size(); x++) {
					g.fillRect(colliders.get(1).get(x).getX(), colliders.get(1).get(x).getY(), xBuffer, yBuffer);
				}
				// Walls Drawn Above

				// Shadows Below
				g.setColor(Color.BLACK);
				for (int x = 0; x < gridSize * 2; x++) {
					for (int y = 0; y < gridSize * 2; y++) {
						if (Math.abs(x - play.getX()) > sightRange || Math.abs(y - play.getY()) > sightRange) {
							g.fillRect(x * xBuffer, y * yBuffer, xBuffer, yBuffer);
						}
					}
				}
				// Shadows Above

			}
			// Above is Panel Painting

		};
		inventoryScreen.setPreferredSize(new Dimension(40, 100));
		statusScreen.setPreferredSize(new Dimension(40, 100));
		f.addKeyListener(guiKList);
		f.add(p, BorderLayout.CENTER);
		f.add(inventoryScreen, BorderLayout.PAGE_END);
		f.add(statusScreen, BorderLayout.PAGE_END);
	}

	public GUI getGUI() {
		return this;
	}

	public JFrame getFrame() {
		return f;
	}

	public void jsonUpdateWalls() {
		walls.clear();

		List<int[]> wallPositions = getWallPositionArray(getStringJSONArray("testLevel"));
		List<int[]> wallPositionsLevel0 = getWallPositionArray(getStringJSONArray("Level0"));
		List<int[]> wallPositionsLevel1 = getWallPositionArray(getStringJSONArray("Level1"));

		if (level == 0) {
			for (int x = 0; x < gridSize; x++) {
				walls.add(new Wall(x * xBuffer, 0));
				walls.add(new Wall(0, x * yBuffer));
				walls.add(new Wall(x * xBuffer, yBuffer * (13)));
				walls.add(new Wall(xBuffer * (gridSize - 2), x * yBuffer));
			}
			play.setX(1);
			play.setY(1);

			for (int x = 0; x < wallPositionsLevel0.size(); x++) {
				// System.out.println(wallPositions.get(x));
				walls.add(new Wall(wallPositionsLevel0.get(x)[0] * xBuffer, wallPositionsLevel0.get(x)[1] * yBuffer));
			}
		} else if (level == 1) {
			for (int x = 0; x < wallPositionsLevel1.size(); x++) {
				// System.out.println(wallPositions.get(x));
				walls.add(new Wall(wallPositionsLevel1.get(x)[0] * xBuffer, wallPositionsLevel1.get(x)[1] * yBuffer));
			}
		} else {
			for (int x = 0; x < wallPositions.size(); x++) {
				// System.out.println(wallPositions.get(x));
				walls.add(new Wall(wallPositions.get(x)[0] * xBuffer, wallPositions.get(x)[1] * yBuffer));
			}
			// for (int x = 0; x < gridSize; x++) {
			// walls.add(new Wall(x * xBuffer, 0));
			// walls.add(new Wall(0, x * yBuffer));
			// walls.add(new Wall(x * xBuffer, yBuffer * (13)));
			// walls.add(new Wall(xBuffer * (gridSize - 2), x * yBuffer));
			// }
			/*
			 * for (int x = 0; x < gridSize; x++){
			 * walls.add(new Wall(x*xBuffer, 6*yBuffer));
			 * walls.add(new Wall(x*xBuffer, 10*yBuffer));
			 * }
			 */
		}
	}

	public void updateWalls() {
		jsonUpdateWalls();
	}

	public void addItem(String _name) {
		inventory.add(_name);
	}

	public void updateExits() {
		List<int[]> exitPositionsLevel0 = getExitPositionArray(getStringJSONArray("Level0"));

		exits.clear();
		if (level == 0) {
			for (int x = 0; x < exitPositionsLevel0.size(); x++) {
				exits.add(new Exit(exitPositionsLevel0.get(x)[0] * xBuffer, exitPositionsLevel0.get(x)[1] * yBuffer));
			}
		} else if (level == 1) {
			exits.add(new Exit(xBuffer * 9, yBuffer * 3));
		} else {
			exits.add(new Exit((level * 2) * xBuffer, (level * 2 * yBuffer)));
		}
	}

	public void toggleTorch() {
		torchUsed = !torchUsed;
	}

	public void updateDoors() {
		List<int[]> doorPositionsLevel0 = getDoorPositionArray(getStringJSONArray("Level0"));

		doors.clear();
		if (level == 0) {
			for (int x = 0; x < doorPositionsLevel0.size(); x++) {
				doors.add(new Door(doorPositionsLevel0.get(x)[0] * xBuffer, doorPositionsLevel0.get(x)[1] * yBuffer));
			}
		} else {
			doors.add(new Door(level * 1 * xBuffer, level * 1 * yBuffer));
		}
	}

	public void updateCoins() {
		List<int[]> coinPositionsLevel0 = getCoinPositionArray(getStringJSONArray("Level0"));

		if (level == 0) {
			for (int x = 0; x < coinPositionsLevel0.size(); x++) {
				items.add(new Collectable(coinPositionsLevel0.get(x)[0] * xBuffer,
						coinPositionsLevel0.get(x)[1] * yBuffer, "Coin"));
			}
		}
	}

	public void updateTorches() {
		List<int[]> torchPositionsLevel0 = getTorchPositionArray(getStringJSONArray("Level0"));

		if (level == 0) {
			for (int x = 0; x < torchPositionsLevel0.size(); x++) {
				items.add(new Collectable(torchPositionsLevel0.get(x)[0] * xBuffer,
						torchPositionsLevel0.get(x)[1] * yBuffer, "Torch"));
			}
		} else {
		}
	}

	public void updatePlayerSpawn() {
		String levelTitle = "Level" + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> spawnPositionsLevel = getSpawnPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < spawnPositionsLevel.size(); x++) {
				// move player here
				play.setX(spawnPositionsLevel.get(x)[0]);
				play.setY(spawnPositionsLevel.get(x)[1]);
			}
		}
	}

	public void addWall(List<Collider> wall, int x, int y) {
		wall.add(new Wall(x * xBuffer, y * yBuffer));
	}

	// Send an array of [x,y] (to better implement JSONification)
	public void addWall(List<Collider> wall, int[] pos) {
		wall.add(new Wall(pos[0] * xBuffer, pos[1] * yBuffer));
	}

	public void addDoor(List<Collider> door, int x, int y) {
		door.add(new Door(x * xBuffer, y * yBuffer));
	}

	public void callRepaint() {
		statusScreen.repaint();
		statusScreen.revalidate();
		// statusScreen.add(new JLabel("X: " + play.getX()));
		// statusScreen.add(new JLabel("Y: " + play.getY()));
		// statusScreen.validate();

		p.repaint();
		f.revalidate();
	}

	public void nextLevel() {
		level++;
		f.remove(p);
		// f.add(inventoryScreen);
		// inventoryScreen.repaint();
		f.pack();
		f.setSize(500, 500);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			System.out.println("Interrupted!" + ex);
			Thread.currentThread().interrupt();
		}
		// f.remove(inventoryScreen);
		f.add(p);
		System.out.println("LEVEL " + level);
		updateWalls();
		updateDoors();
		updateExits();
		updateCoins();
		updateTorches();
		updatePlayerSpawn();
	}

	public List<List<Collider>> getColliders() {
		return colliders;
	}

	public void updateColliders(List<List<Collider>> _colliders) {
		colliders = _colliders;
	}

	public void toggleBomb() {
		// Never implemented?
	}

	public void toggleCoin() {
		coinUsed = !coinUsed;
	}

}
