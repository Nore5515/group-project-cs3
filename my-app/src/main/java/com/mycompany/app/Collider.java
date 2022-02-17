package com.mycompany.app;

import java.util.List;

abstract class Collider {

	int x;
	int y;
	String name;

	protected Collider(int _x, int _y) {
		x = _x;
		y = _y;
		name = "DEFAULT";
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int _x) {
		x = _x;
	}

	public void setY(int _y) {
		y = _y;
	}

	// GENERIC COLLISION
	// public boolean playerCollision() {
	// public boolean playerCollision(Player p, int key, List<List<Collider>>
	// colliders, OurKeyListener ourKeyListener) {
	// WHAT THE FRICK???? WH-WHY?! HOW?!
	// I had it so that everyone overrode it...

	// This is to be overridden by it's children/
	public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {
		System.out.println("DEFAULT COLLIDER HIT PLAYER");

		// if true, stop moving
		return playerCollision();
	}

	public boolean playerCollision() {
		return false;
	}

	public String name() {
		return name;
	}

}
