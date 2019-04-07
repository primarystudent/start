package wenlong.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

	public static void main(String[] args) {
		Runnable barRun = new Runnable() { public void run() {System.out.println("run..."); }};
		CyclicBarrier bar = new CyclicBarrier(5,barRun);
		
		for(int i=0;i<10;i++){
			System.out.println(bar.getParties()+",等待"+bar.getNumberWaiting());
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("到达");
					try {
						bar.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
