package com.mycompany.app;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

// import javax.xml.crypto.dsig.keyinfo.KeyValue;

public class OurKeyListener implements KeyListener {

	Player p;
	GUI gui;
	int gridSize;
	int xBuffer;
	int yBuffer;
	boolean collided;
	List<List<Collider>> colliders;
	List<Command> commands;

	public OurKeyListener(Player _p, GUI _gui, int _gridSize) {
		p = _p;
		gui = _gui;
		gridSize = _gridSize;
		xBuffer = (gui.getFrame().getWidth()) / (gridSize - 1);
		yBuffer = (gui.getFrame().getHeight()) / (gridSize - 1);
		collided = false;
		commands = new ArrayList<>();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		updateMovement();
		if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			collided = isCollision(p.getX() * xBuffer, (p.getY() + 1) * yBuffer, gui.getColliders());
			if (!collided) {
				movePlayer(0, 1);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			collided = isCollision(p.getX() * xBuffer, (p.getY() - 1) * yBuffer, gui.getColliders());
			if (!collided) {
				movePlayer(0, -1);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			collided = isCollision((p.getX() - 1) * xBuffer, p.getY() * yBuffer, gui.getColliders());
			if (!collided) {
				movePlayer(-1, 0);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			collided = isCollision((p.getX() + 1) * xBuffer, p.getY() * yBuffer, gui.getColliders());
			if (!collided) {
				movePlayer(1, 0);
			}
		}

		// Definetly an easier way to do this...
		// TODO
		for (int i = 0; i < commands.size(); i++) {
			// System.out.println("Does " + arg0.toString().substring(55, 56) + "==" +
			// commands.get(i).getCommand());
			if (arg0.toString().substring(55, 56).compareTo(commands.get(i).getCommand()) == 0) {
				commands.get(i).activate();
			}
		}

		gui.callRepaint();
		collided = false;
	}

	// public boolean isCollision(int _x, int _y, List<List<Collider>> _colliders,
	// Player p, int key) {
	public boolean isCollision(int _x, int _y, List<List<Collider>> _colliders) {
		colliders = _colliders;
		for (int x = 0; x < colliders.size(); x++) {
			for (int y = 0; y < colliders.get(x).size(); y++) {
				if (_x == colliders.get(x).get(y).getX() && _y == colliders.get(x).get(y).getY()) {
					return colliders.get(x).get(y).playerCollision(p, colliders, this);
					// return colliders.get(x).get(y).playerCollision(p, key, colliders, this);
				}
			}

		}
		return false;
	}

	public void movePlayer(int x, int y) {
		p.setX(p.getX() + x);
		p.setY(p.getY() + y);
		// gui.movementTick();
	}

	public void updateMovement() {
		xBuffer = (gui.getFrame().getWidth()) / (gridSize - 1);
		yBuffer = (gui.getFrame().getHeight()) / (gridSize - 1);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// Darn
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// Oops

	}

	public void updateColliders(List<List<Collider>> _colliders) {
		colliders = _colliders;
	}

	public void updateCommands(List<Command> _commands) {
		commands = _commands;
	}

	public GUI getGUI() {
		return gui;
	}

}
