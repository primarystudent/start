package wenlong.start;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        ThreadLocal<Integer> tint = new ThreadLocal<>();
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i=0;i<1;i++){
        	final int ii = i;
        	executor.submit(new Runnable() {
				@Override
				public void run() {
					tint.set(ii);
					
				}
			});
        }
        for(int i=0;i<100;i++){
        	final int ii = i;
        	executor.submit(new Runnable() {
				
				@Override
				public void run() {
//					tint.set(ii);
//					System.out.println("threadId:"+Thread.currentThread().getId()+"@"+Thread.currentThread().hashCode()+" seq:"+tint.get());
					if(Thread.currentThread().getId()==14){
						try {
							while(true){
								Thread.currentThread().sleep(1000*5);
								System.err.println("sleep");
							}
							
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
        }
        System.out.println(Thread.getAllStackTraces());
//        executor.shutdown();
    }
}
