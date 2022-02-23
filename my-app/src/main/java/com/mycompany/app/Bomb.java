package com.mycompany.app;

import java.util.List;

public class Bomb extends Collider {

    int bombX;
    int bombY;
    int life;
    GUI gui;

    public Bomb(int _x, int _y) {
        super(_x, _y);
        bombX = _x;
        bombY = _y;
        life = 4;
    }

    // This is for placed bombs.
    public Bomb(int _x, int _y, GUI _gui) {
        super(_x, _y);
        bombX = _x;
        bombY = _y;
        gui = _gui;
        life = 4;
    }

    // public boolean playerCollision(Player p, int key, List<List<Collider>>
    // colliders, OurKeyListener ourKeyListener) {
    // public boolean playerCollision(List<List<Collider>> colliders) {
    @Override
    public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {

        System.out.println("BOMB HIT PLAYER");
        removeSelf(colliders);

        return true;
    }

    // TODO make the collider accessing better
    public void removeSelf(List<List<Collider>> colliders) {
        int index = -1;
        for (int i = 0; i < colliders.get(5).size(); i++) {
            // System.out.println("Does " + x + "=" + colliders.get(3).get(i).getX() + " as
            // well as " + y + "="
            // + colliders.get(3).get(i).getY());
            if (x == colliders.get(5).get(i).getX() && y == colliders.get(5).get(i).getY()) {
                index = i;
            }
        }
        colliders.get(5).remove(index);
        // System.out.println("Collider Removed!");

        if (gui != null) {
            try {
                gui.removePlacedBomb(this);
            } catch (Exception e) {
                System.out.println("Error:" + e);
            }

        }
    }

}
