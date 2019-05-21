package designpattern.observer;

public class ObserverAImpl implements ObserverInterface{

	private SubjectInterface subject;
	
	public ObserverAImpl(SubjectInterface subject){
		this.subject = subject;
		subject.registObserver(this);
	}
	
	@Override
	public void update() {
		System.out.println("A update");
		
	}

}
