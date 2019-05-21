package designpattern.observer;

public interface SubjectInterface {

	void registObserver(ObserverInterface observer);
	
	void removeObserver(ObserverInterface observer);
	
	void notifyObservers();
}
