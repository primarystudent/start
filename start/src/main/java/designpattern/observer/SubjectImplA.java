package designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class SubjectImplA implements SubjectInterface {

	private List<ObserverInterface> observers = new ArrayList<>();
	
	private boolean changed;
	
	@Override
	public void registObserver(ObserverInterface observer) {
		if (observer == null)
            throw new NullPointerException();
        if (!observers.contains(observer)) {
        	observers.add(observer);
        }

	}

	@Override
	public void removeObserver(ObserverInterface observer) {
		observers.remove(observer);

	}

	@Override
	public void notifyObservers() {
		for(ObserverInterface observer:observers){
			observer.update();
		}
		changed = false;
	}

	void infoChanged(){
		changed = System.currentTimeMillis()%2==0;
		if(changed){
			notifyObservers();	
		}
		
	}
}
