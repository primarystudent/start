package wenlong.concurrent;

import java.time.LocalTime;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceDemo {

	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3),new CallerRunsPolicy());
		
		for(int i=0;i<10;i++){
			System.out.println(executor.getPoolSize()+":"+LocalTime.now());
			executor.submit(new Runnable() {
				
				@Override
				public void run() {
					try {
						
						System.out.println("exe...."+LocalTime.now());
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
		}
		while(executor.getPoolSize()>0){
			System.out.println("end:"+executor.getPoolSize()+":"+LocalTime.now());
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("enddd:"+executor.getPoolSize()+":"+LocalTime.now());
	}
}
