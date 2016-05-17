import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InventoryScreen extends JPanel{

	List<String> stuff;
	
	public InventoryScreen(List<String> inv){
		stuff = inv;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g) ;
		this.setBackground(Color.LIGHT_GRAY);
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, 10, 10);
		System.out.println("Filling Inventory");
		for (int x = 0; x < stuff.size(); x++){
			this.add(new JLabel(stuff.get(x)),BorderLayout.CENTER);
			System.out.println("INV:" + stuff.get(x));
		}
	}
	
}
