
public class Command {

	String item;
	String command;
	GUI gui;
	
	public Command(String _item, String _command, GUI _gui){
		item = _item;
		command = _command;
		gui = _gui;
		//System.out.println("New Command Binding: " + command + " bound for " + item);
	}
	
	public String getCommand(){
		return command;
	}
	public String getItem(){
		return item;
	}
	
	//HERE IS THA MAGIC
	public void activate(){
		if (item == "Coin"){
			System.out.println("Yum!");
			gui.play.setHP(gui.play.getHP() + 1);
			gui.toggleCoin();
		}
		else if (item == "Torch"){
			gui.toggleTorch();
		}
		else if (item == "Bomb"){
			gui.toggleBomb();
		}
	}
	
}
