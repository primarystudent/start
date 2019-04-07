package wenlong.concurrent;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
	
	private static final Semaphore sem = new Semaphore(10);
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		list.add(1);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int i =0;i<=100;i++){
					boolean add = false;
					try {
						sem.acquire();
						add = list.add(i);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						if(!add){
							sem.release();
						}
					}
				}
				
				
			}
		}).start();
		while(list.size()>0){
			
			System.out.println(LocalTime.now()+"size:"+list.size());
			boolean remove = list.remove(list.get(0));
			if(remove){
				sem.release();
			}
			try {
				Thread.sleep(1000L*1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(LocalTime.now()+"size:"+list.size());
			
			
		}
	}

}
