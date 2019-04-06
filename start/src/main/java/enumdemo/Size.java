package enumdemo;

public enum Size {
	SMALL("Z"),
	MEDIUM("M"),
	LARGE("L");
	
	private String size;
	private Size(String size){
		this.size = size;
	}
	public String getSize() {
		return size;
	}
	
}
