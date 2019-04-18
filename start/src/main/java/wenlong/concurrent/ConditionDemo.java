package wenlong.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
	public static ReentrantLock lock = new ReentrantLock();
	public static Condition condition = lock.newCondition();
	public static void main(String[] args) {
		
		for(int i=0;i<5;i++){
			final int ii = i;
			 new Thread(new Runnable() {
				
				@Override
				public void run() {
					demo(ii);
				}
			}).start();
			 
		}
	}

	public static void demo(int ii){
		lock.lock();
		try {
			if(ii==4){
				 try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				condition.signalAll();
			}else if(ii==3){
				condition.signal();
			}
			System.out.println("等待"+ii);
			condition.await();
			System.out.println("等待结束："+ii);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
}
