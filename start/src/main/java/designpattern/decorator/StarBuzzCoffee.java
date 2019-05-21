package designpattern.decorator;

public class StarBuzzCoffee {

	public static void main(String[] args) {
		Beverage espresso = new Espresso();
		espresso = new Mocha(espresso);
		espresso = new Whip(espresso);
		espresso = new Whip(espresso);
		System.out.println(espresso.getDescription()+" cost "+espresso.cost());
		Beverage houseBlend = new HouseBlend();
		houseBlend = new Mocha(houseBlend);
		houseBlend = new Whip(houseBlend);
		System.out.println(houseBlend.getDescription()+" cost "+houseBlend.cost());
	}
}
