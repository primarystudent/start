package enumdemo;

public class EnumTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Size size = Enum.valueOf(Size.class, "SMALL");
		System.out.println("sizevalue=" +size);
		System.out.println("sizekey=" + size.getSize());
		
	}

}
