package wenlong.thread;

import java.time.LocalDateTime;

public class ThreadX implements Runnable{
	private LocalDateTime runDate;

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("线程X已启动》》》》》》》");
		System.out.println("启动时间："+LocalDateTime.now());
		
	}
	
}
