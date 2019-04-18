package wenlong.concurrent;

import java.time.LocalTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
	
	public static ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3));
	public static void main(String[] args) {
//		arrayBlockingQueue();
//		TransferQueue();
		System.out.println(1^5);
	}
	
	/**
	 * put,take 阻塞
	 * add,remove,element抛异常
	 * offer返回true,false
	 * poll取出，peek返回头，没有return null
	 */
	public static void arrayBlockingQueue(){
		ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<15;i++){
					try {
						queue.put(i);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("add:"+i);
					
			}
				
			}
		});
		
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				for(int i=0;i<10;i++){
					try {
						System.out.println("take:"+queue.poll());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
	}
	
	public static void TransferQueue(){
		LinkedTransferQueue<Integer> queue = new LinkedTransferQueue<>();
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000L);
					System.out.println(queue.take());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		executor.submit(new Runnable() {
			
			@Override
			public void run() {
				try {
					for(int i=0;i<3;i++){
						System.out.println(LocalTime.now()+"等待消费:"+i);
						queue.transfer(i);
						System.out.println(LocalTime.now()+"消费完："+i);
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		executor.shutdown();
	}
}
