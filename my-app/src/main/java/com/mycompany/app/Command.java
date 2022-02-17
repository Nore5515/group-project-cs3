package com.mycompany.app;

public class Command {

	String item;
	String commandText;
	GUI gui;

	public Command(String _item, String _command, GUI _gui) {
		item = _item;
		commandText = _command;
		gui = _gui;
		// System.out.println("New Command Binding: " + command + " bound for " + item);
	}

	public String getCommand() {
		return commandText;
	}

	public String getItem() {
		return item;
	}

	// HERE IS THA MAGIC
	public void activate() {
		if ("Coin".equals(item)) {
			// System.out.println("Yum!");
			gui.play.setHP(gui.play.getHP() + 1);
			gui.toggleCoin();
		} else if ("Torch".equals(item)) {
			gui.toggleTorch();
		} else if ("Bomb".equals(item)) {
			gui.toggleBomb();
		}
	}

}
