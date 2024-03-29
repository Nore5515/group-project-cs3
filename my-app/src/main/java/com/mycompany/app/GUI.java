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

	class PurchaseTileClass {
		int x;
		int y;
		int price;
		String item;

		PurchaseTileClass(int _x, int _y, int _price, String _item) {
			x = _x;
			y = _y;
			price = _price;
			item = _item;
		}
	}

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
	String msg = "";

	// Consts
	private static final String TORCH = "Torch";
	private static final String COIN = "Coin";
	private static final String BOMB = "Bomb";
	private static final String LEVEL = "Level";

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
	// item 5
	List<Collider> traps;
	// item 6
	List<Collider> bombPickups;
	// item 7
	List<Bomb> placedBombs;
	// item 8
	List<Collider> purchaseTiles;

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

	public JSONObject getStringLevelJSONArray(String levelTitle) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(
					"/Users/noah/projects/java/group-project-cs3/my-app/src/main/java/com/mycompany/app/levels.json"));

			JSONObject jsonFile = (JSONObject) obj;
			JSONObject level0 = (JSONObject) jsonFile.get(levelTitle);
			return (JSONObject) level0;
		} catch (Exception e) {
			e.printStackTrace();
			return new JSONObject();
		}
	}

	// TODO Refactor the hell outta these lmao
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

	public List<int[]> getTrapPositionArray(JSONArray jArray) {
		ArrayList<int[]> trapArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'T') {
					trapArray.add(new int[] { y, x });
				}
			}
		}
		return trapArray;
	}

	public List<int[]> getBombPickupPositionArray(JSONArray jArray) {
		ArrayList<int[]> bombPickupTileArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'b') {
					bombPickupTileArray.add(new int[] { y, x });
				}
			}
		}
		return bombPickupTileArray;
	}

	public List<int[]> getPurchaseTilePositionArray(JSONArray jArray) {
		ArrayList<int[]> purchaseTileArray = new ArrayList<>();
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'p') {
					purchaseTileArray.add(new int[] { y, x });
				}
			}
		}
		return purchaseTileArray;
	}

	public List<PurchaseTileClass> getPurchaseTileClassArray(JSONObject jObj) {
		// getStringLevelJSONArray
		// TODO
		JSONArray jArray = (JSONArray) jObj.get("rows");
		ArrayList<PurchaseTileClass> purchaseTileArray = new ArrayList<>();
		PurchaseTileClass ptc;
		char[] mapLine;
		for (int x = 0; x < jArray.size(); x++) {
			mapLine = ((String) jArray.get(x)).toCharArray();
			for (int y = 0; y < mapLine.length; y++) {
				if (mapLine[y] == 'p') {
					ptc = new PurchaseTileClass(y, x, 3, TORCH);
					purchaseTileArray.add(ptc);
				}
			}
		}
		// Iterate through each p, and create PTC's from the "shopContents" section of
		// the JSON.
		JSONArray shopContents = (JSONArray) jObj.get("shopContents");
		for (int x = 0; x < purchaseTileArray.size(); x++) {
			String shopLine = ((String) shopContents.get(x));
			System.out.println(shopLine);
			String[] parts = shopLine.split("-");
			if (parts.length == 2) {
				String item = parts[0];
				int price = Integer.parseInt(parts[1]);
				purchaseTileArray.get(x).item = item;
				purchaseTileArray.get(x).price = price;
			}
		}

		return purchaseTileArray;
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
		torchHealth = 30;
		coinUsed = false;

		play = new Player(gridSize / 2, gridSize / 2);
		// play = new Player(Math.round(gridSize / 2), Math.round(gridSize / 2));
		guiKList = new OurKeyListener(play, this, gridSize);
		gameOver = new JLabel("WINNER");
		gameOver.setFont(new Font("Verdana", 1, 20));
		level = 0;
		sightRange = 3;

		inventory = new ArrayList<>();

		// TO-DO
		// make an Update Function to simplify this
		colliders = new ArrayList<>();
		items = new ArrayList<>();
		walls = new ArrayList<>();
		exits = new ArrayList<>();
		doors = new ArrayList<>();
		traps = new ArrayList<>();
		bombs = new ArrayList<>();
		placedBombs = new ArrayList<>();
		purchaseTiles = new ArrayList<>();
		// Item 0
		colliders.add(items);
		// Item 1
		colliders.add(walls);
		// Item 2
		colliders.add(exits);
		// Item 3
		colliders.add(doors);
		// Item 4
		colliders.add(traps);
		// Item 5
		colliders.add(purchaseTiles);
		// Item 6??? maybe 5????
		colliders.add(bombs);

		// Status Screen
		// TODO Move movement events to the new movementTick function
		statusScreen = new JPanel() {

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				this.setBackground(Color.LIGHT_GRAY);

				int numLists = 0;

				// Displays Coords
				g.setFont(new Font("Arial", 0, 20));
				g.drawString("HP: " + play.hp + "/" + play.maxHP, 10, 20);
				g.drawString(msg, 240, 20);

				// Lists for Stackable Items (ex. Potions, Coins, etc)
				List<String> Coins = new ArrayList<>();
				List<String> Torches = new ArrayList<>();
				List<String> BombPickups = new ArrayList<>();

				List<Command> commandItems = new ArrayList<>();

				// Adds items to Stackable Items lists
				for (int x = 0; x < inventory.size(); x++) {
					if (inventory.get(x).compareTo(COIN) == 0) {
						Coins.add(inventory.get(x));
					}
					if (inventory.get(x).compareTo(TORCH) == 0) {
						Torches.add(inventory.get(x));
					}
					if (inventory.get(x).compareTo(BOMB) == 0) {
						BombPickups.add(inventory.get(x));
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
				if (!BombPickups.isEmpty()) {
					g.drawString(String.valueOf((char) (numLists + 65)) + ") Bombs: " + BombPickups.size(), 120,
							20 * (numLists + 1));
					// g.drawString("Torch Life: " + torchHealth, 270, 20 * (numLists + 1));
					numLists++;
					c = new Command(BOMB, String.valueOf((char) (numLists + 64)), getGUI());
					commandItems.add(c);
				}

				// WHY IS TORCH HEALTH BEING CALCULATED ON REDRAW
				// WHAT IS WRONG ME
				// PAST ME THAT IS
				// TODO Torch Health recalculated on redraw?
				// FUKIN A
				// ...i fixed it, somehow....
				// if torch is being used...
				movementTick(Torches);

				if (coinUsed) {
					// inventory.remove("Coin");
					// numLists--;
					// Not currently being used
					this.revalidate();
					// coinUsed = !coinUsed;
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

		// Add Traps
		updateTraps();

		// Add Bombs
		// updateBombs();

		// Add Bomb Pickups
		updateBombPickups();
		// Add Purchase Tiles
		updatePurchaseTiles();

		// HUGE ASS FUNCTION
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

				// GOTTA BE AN EASIER WAY vvv
				// It seems we use colliders.get(x) for getting position arrays
				// Surely this could be a for loop?

				// Draws coins Below
				g.setColor(Color.YELLOW);
				if (!colliders.get(0).isEmpty()) {
					for (int x = 0; x < colliders.get(0).size(); x++) {
						g.fillRect(colliders.get(0).get(x).getX(), colliders.get(0).get(x).getY(), xBuffer, yBuffer);
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

				// Draws Traps Below (if torch is active)
				if (torchUsed) {
					g.setColor(new Color(190, 60, 80));
					for (int x = 0; x < colliders.get(4).size(); x++) {
						g.fillRect(colliders.get(4).get(x).getX(), colliders.get(4).get(x).getY(), xBuffer, yBuffer);
					}
				}
				// Draws Traps Above

				// // Draws Bombs Below
				// g.setColor(new Color(50, 50, 50));
				// for (int x = 0; x < colliders.get(5).size(); x++) {
				// g.fillRect(colliders.get(5).get(x).getX(), colliders.get(5).get(x).getY(),
				// xBuffer, yBuffer);
				// }
				// // Draws Bombs Above

				// Draws Bombs at 3 life
				g.setColor(new Color(50, 0, 50));
				for (int x = 0; x < placedBombs.size(); x++) {
					if (placedBombs.get(x).life >= 2) {
						g.fillRect(placedBombs.get(x).getX(), placedBombs.get(x).getY(), xBuffer, yBuffer);
					}
				}
				// Draws Bombs at 2 life
				g.setColor(new Color(125, 0, 125));
				for (int x = 0; x < placedBombs.size(); x++) {
					if (placedBombs.get(x).life == 1) {
						g.fillRect(placedBombs.get(x).getX(), placedBombs.get(x).getY(), xBuffer, yBuffer);
					}
				}
				// Draws Bombs at 1 life
				g.setColor(new Color(250, 0, 250));
				for (int x = 0; x < placedBombs.size(); x++) {
					if (placedBombs.get(x).life <= 0) {
						g.fillRect(placedBombs.get(x).getX(), placedBombs.get(x).getY(), xBuffer, yBuffer);
					}
				}
				// Draw Purchase Tiles below
				g.setColor(new Color(30, 60, 240));
				for (int x = 0; x < colliders.get(5).size(); x++) {
					g.fillRect(colliders.get(5).get(x).getX(), colliders.get(5).get(x).getY(), xBuffer, yBuffer);
				}
				// Draws Purchase Tiles Above

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
				// System.out.println("Shadows painted at sight range: " + sightRange);
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
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> wallPositions = getWallPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < wallPositions.size(); x++) {
				walls.add(new Wall(wallPositions.get(x)[0] * xBuffer, wallPositions.get(x)[1] * yBuffer));
			}
		}
	}

	public void updateWalls() {
		jsonUpdateWalls();
	}

	public void addItem(String _name) {
		inventory.add(_name);
	}

	public void updateExits() {
		exits.clear();
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> exitPositions = getExitPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < exitPositions.size(); x++) {
				exits.add(new Exit(exitPositions.get(x)[0] * xBuffer, exitPositions.get(x)[1] * yBuffer));
			}
		}
	}

	public void toggleTorch() {
		// For some reason, adding this println makes it work...???
		// Theorizing it may have to do with re-draw
		// TODO wtf torch is weird
		System.out.println("Toggled Torch!");
		torchUsed = !torchUsed;

	}

	public void updateDoors() {
		doors.clear();
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> doorPositions = getDoorPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < doorPositions.size(); x++) {
				doors.add(new Door(doorPositions.get(x)[0] * xBuffer, doorPositions.get(x)[1] * yBuffer));
			}
		}
	}

	public void updateTraps() {
		traps.clear();
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> trapPositions = getTrapPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < trapPositions.size(); x++) {
				traps.add(new Trap(trapPositions.get(x)[0] * xBuffer, trapPositions.get(x)[1] * yBuffer));
			}
		}
	}

	// public void updateBombs() {
	// bombs.clear();
	// String levelTitle = LEVEL + level;
	// System.out.println("Update Bombs");

	// if (getStringJSONArray(levelTitle) != null) {
	// List<int[]> bombPositions =
	// getBombPositionArray(getStringJSONArray(levelTitle));
	// for (int x = 0; x < bombPositions.size(); x++) {
	// bombs.add(new Bomb(bombPositions.get(x)[0] * xBuffer, bombPositions.get(x)[1]
	// * yBuffer));
	// }
	// }

	// }

	public void updatePlacedBombs() {
		bombs.clear();
		System.out.println("Update Bombs");
		// Since Bombs are unique in that they can be placed
		// they have a special check for a special array of placed bombs.
		if (!placedBombs.isEmpty()) {
			for (int x = 0; x < placedBombs.size(); x++) {
				System.out.println("Placing bomb");
				bombs.add(placedBombs.get(x));
			}
		}
	}

	public void updateBombPickups() {
		// bombPickups.clear();
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> bombPickupPositions = getBombPickupPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < bombPickupPositions.size(); x++) {
				items.add(
						new Collectable(bombPickupPositions.get(x)[0] * xBuffer,
								bombPickupPositions.get(x)[1] * yBuffer, BOMB));
			}
		}
	}

	public void updatePurchaseTiles() {
		purchaseTiles.clear();
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<PurchaseTileClass> ptcList = getPurchaseTileClassArray(getStringLevelJSONArray(levelTitle));
			List<int[]> purchaseTilesPositions = new ArrayList<>();
			for (PurchaseTileClass ptc : ptcList) {
				purchaseTilesPositions.add(new int[] { ptc.x, ptc.y });
			}
			for (int x = 0; x < purchaseTilesPositions.size(); x++) {
				purchaseTiles.add(new Purchase(purchaseTilesPositions.get(x)[0] * xBuffer,
						purchaseTilesPositions.get(x)[1] * yBuffer, this, ptcList.get(x).item, ptcList.get(x).price));
			}
		}
	}

	public void updateCoins() {
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> coinPositions = getCoinPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < coinPositions.size(); x++) {
				items.add(new Collectable(coinPositions.get(x)[0] * xBuffer,
						coinPositions.get(x)[1] * yBuffer, COIN));
			}
		}
	}

	public void updateTorches() {
		String levelTitle = LEVEL + level;

		if (getStringJSONArray(levelTitle) != null) {
			List<int[]> torchPositions = getTorchPositionArray(getStringJSONArray(levelTitle));
			for (int x = 0; x < torchPositions.size(); x++) {
				items.add(new Collectable(torchPositions.get(x)[0] * xBuffer,
						torchPositions.get(x)[1] * yBuffer, TORCH));
			}
		}
	}

	public void updatePlayerSpawn() {
		String levelTitle = LEVEL + level;

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

	public void movementTick(List<String> Torches) {
		// System.out.println("movement tick");
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
				torchHealth = 30;
				sightRange = 2;
			} else {
				sightRange = 5;
			}
		} else {
			sightRange = 2;
		}

		// Bomb Countdown
		for (int x = 0; x < placedBombs.size(); x++) {
			bombCountdown(placedBombs.get(x));
		}
	}

	public void nextLevel() {
		msg = "";
		items.clear();
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
		updateTraps();
		updateBombPickups();
		updatePurchaseTiles();
	}

	public List<List<Collider>> getColliders() {
		return colliders;
	}

	public void updateColliders(List<List<Collider>> _colliders) {
		colliders = _colliders;
	}

	public void toggleBomb() {
		System.out.println("Bomb Toggled");
		placedBombs.add(new Bomb(play.getX() * xBuffer, play.getY() * yBuffer, this));
		updatePlacedBombs();
		inventory.remove(BOMB);
	}

	public boolean removePlacedBomb(Bomb b) {
		for (int x = placedBombs.size() - 1; x >= 0; x--) {
			if (placedBombs.get(x) == b) {
				placedBombs.remove(x);
				return true;
			}
		}
		return false;
	}

	public boolean bombCountdown(Bomb b) {
		if (b.life <= 0) {
			detonateBomb(b);
			b.playerCollision(play, getColliders(), guiKList);
			return true;
		} else {
			b.life -= 1;
		}
		return false;
	}

	// Return adjacent walls to a grid location by distance
	public List<Collider> getAdjacentWalls(int x, int y) {
		List<Collider> adja = new ArrayList<>();

		int[] loc = new int[] { x, y };

		for (int i = 0; i < colliders.get(1).size(); i++) {
			// Get distance from wall to loc
			System.out.println("Location: " + colliders.get(1).get(i).getX() + "," + colliders.get(1).get(i).getY());
			int distX = (colliders.get(1).get(i).getX() - loc[0]);
			int distY = (colliders.get(1).get(i).getY() - loc[1]);
			double dist = Math.sqrt((distX * distX) + (double) (distY * distY));
			System.out.println("Distance: " + dist);
			if (dist < xBuffer * 2) {
				System.out.println("Close enough!" + colliders.get(1).get(i).getX() + ","
						+ colliders.get(1).get(i).getY() + " to " + x + "," + y);
				adja.add(colliders.get(1).get(i));
			}
		}

		System.out.println(adja.size());
		return adja;
	}

	public boolean detonateBomb(Bomb b) {
		System.out.println("DETONATING");
		try {
			List<Collider> wallsToDestroy = getAdjacentWalls(b.getX(), b.getY());
			for (Collider wall : wallsToDestroy) {
				Wall w = (Wall) wall;
				w.destroySelf(getColliders());
			}
			return true;
		} catch (Exception e) {
			System.out.println("Error in detonation!");
		}
		return false;
	}

	public void toggleCoin() {
		coinUsed = !coinUsed;
	}

	public boolean attemptPurchase(String item, int price) {
		// TODO
		List<String> Coins = new ArrayList<>();

		// Adds items to Stackable Items lists
		for (int x = 0; x < inventory.size(); x++) {
			if (inventory.get(x).compareTo(COIN) == 0) {
				Coins.add(inventory.get(x));
			}
		}

		if (Coins.size() >= price) {
			msg = "Purchased " + item;
			int removedCoins = 0;
			for (int x = Coins.size() - 1; x >= 0; x--) {
				removedCoins++;
				inventory.remove(Coins.get(x));
				if (removedCoins >= price) {
					x = -1;
				}
			}
			if ("HP".equals(item)) {
				play.setHP(play.getHP() + 1);
			} else {
				addItem(item);
			}

		} else {
			msg = "Not enough coin!";
		}
		return true;
	}

}
