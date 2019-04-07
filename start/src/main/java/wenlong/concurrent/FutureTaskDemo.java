package wenlong.concurrent;

import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
	
	public static void main(String[] args) {
		FutureTaskDemo demo = new FutureTaskDemo();
		System.out.println(LocalTime.now()+"start");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				demo.get();
				System.out.println(LocalTime.now()+"over");
			}
		}).start();
		demo.start();
		
	}
	
	class ProductInfo {
		
	}
	public ProductInfo loadProductInfo() throws InterruptedException{
		System.out.println("load productInfo....");
		Thread.currentThread().sleep(1000*10);
		return new ProductInfo();
	}
	
	private final FutureTask<ProductInfo> future = new FutureTask<>(new Callable<ProductInfo>() {

		@Override
		public ProductInfo call() throws Exception {
			// TODO Auto-generated method stub
			return loadProductInfo();
		}
	});
	
	private final Thread thread = new Thread(future);
	
	public void start(){
		thread.start();
	}
	
	public ProductInfo get(){
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
