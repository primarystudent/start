package designpattern.command;

public class LightCommand implements CommandInterface {

	private Light light;
	
	
	
	@Override
	public void execute() {
		light.on();

	}

	public void setLight(Light light) {
		this.light = light;
	}

}
