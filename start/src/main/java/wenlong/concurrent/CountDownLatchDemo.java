package wenlong.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

	public static void main(String[] args) {
		try {
			demo(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void demo(int n) throws InterruptedException{
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch end = new CountDownLatch(n);
		for(int i=0;i<n;i++){
			Thread t = new Thread(){
				public void run(){
					try {
						start.await();
							System.out.println("await end");
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						end.countDown();
					}
				}
			};
			t.start();
		}
		long startT = System.currentTimeMillis();
		System.out.println(startT);
		start.countDown();
		end.await();
		long endt = System.currentTimeMillis();
		System.out.println(endt-startT);
		
	}
	
}
