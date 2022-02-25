package com.mycompany.app;

import java.util.List;

public class Purchase extends Collider {

    GUI gui;
    String item;
    int price;
    boolean primed = false;

    public Purchase(int _x, int _y, GUI _gui, String itemContained, int _price) {
        super(_x, _y);
        gui = _gui;
        item = itemContained;
        price = _price;
    }

    @Override
    public boolean playerCollision(Player p, List<List<Collider>> colliders, OurKeyListener ourKeyListener) {
        System.out.println("PURCHASE COLLISION WITH PLAYER");
        updateMessage(item + " at $" + price);
        return true;
    }

    public boolean updateMessage(String s) {
        if (!primed) {
            gui.msg = s;
            primed = true;
        } else {
            // gui.msg = "Sold!";
            gui.attemptPurchase(item, price);
            primed = false;
        }
        return true;
    }

}
