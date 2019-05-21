package designpattern.command;

public class SimpleControl {

	private CommandInterface command;

	public void setCommand(CommandInterface command) {
		this.command = command;
	}
	
	public void control(){
		command.execute();
	}
	
	public static void main(String[] args) {
		Light light = new Light();
		LightCommand command = new LightCommand();
		command.setLight(light);
		SimpleControl control = new SimpleControl();
		control.setCommand(command);
		control.control();
	}
}
