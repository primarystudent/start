package designpattern.adapter;

public class Adapter implements TargetRequest{

	Adaptee adaptee;
	
	@Override
	public void request() {
		adaptee.adapteeRequest();
	}

}
