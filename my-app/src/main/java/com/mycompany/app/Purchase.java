package com.mycompany.app;

import java.util.List;

public class Purchase extends Collider {

    public Purchase(int _x, int _y) {
        super(_x, _y);
    }

    @Override
    public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {
        System.out.println("PURCHASE COLLISION WITH PLAYER");

        // if true, stop moving
        return true;
    }

}
