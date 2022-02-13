package com.mycompany.app;

// import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class StatusScreen extends JPanel {

	Player p;

	public StatusScreen(Player _p) {
		p = _p;
	}

	public void updatePlayerStatus(Player _p) {
		p = _p;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.LIGHT_GRAY);
		this.add(new JLabel("X: " + p.getX()));
		this.add(new JLabel("Y: " + p.getY()));
	}

}
