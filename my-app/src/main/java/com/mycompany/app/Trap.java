package com.mycompany.app;

import java.util.List;

public class Trap extends Collider {

    int trapX;
    int trapY;

    public Trap(int _x, int _y) {
        super(_x, _y);
        trapX = _x;
        trapY = _y;
    }

    @Override
    public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {

        System.out.println("TRAP HIT PLAYER");
        removeSelf(colliders);
        p.setHP(p.getHP() - 1);

        return true;
    }

    public void removeSelf(List<List<Collider>> colliders) {
        int index = -1;
        // TODO this is EXTREMELY unsafe!!!
        for (int i = 0; i < colliders.get(4).size(); i++) {
            if (x == colliders.get(4).get(i).getX() && y == colliders.get(4).get(i).getY()) {
                index = i;
            }
        }
        colliders.get(4).remove(index);
    }
    // TODO
    // Colliders.get(x) is a HORRIBLE way of storing this...please make it better!
    // Maybe a dict?

}
