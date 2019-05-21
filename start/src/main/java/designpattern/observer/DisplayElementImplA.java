package designpattern.observer;

public class DisplayElementImplA implements ObserverInterface{

	private SubjectInterface subject;
	
	public DisplayElementImplA(SubjectInterface subject) {
		this.subject = subject;
		subject.registObserver(this);
	}
	
	@Override
	public void update() {
		System.out.println("DisPlay update,pull subjectInfo");
	}
	
	public void cancelObserver(){
		subject.removeObserver(this);
	}
	
	public static void main(String[] args) {
		SubjectImplA subjectA = new SubjectImplA();
		DisplayElementImplA observer1 = new DisplayElementImplA(subjectA);
		ObserverAImpl observer2 = new ObserverAImpl(subjectA);
		subjectA.infoChanged();
		observer1.cancelObserver();
		subjectA.infoChanged();
	}
}
