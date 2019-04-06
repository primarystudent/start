package wenlong.thread;

public class ThreadY extends Thread{
	
	private boolean isRunState = false;

	@Override
	public synchronized void start() {
		// TODO Auto-generated method stub
		this.isRunState = true;
		super.start();
	}

	@Override
	public void run() {
		int i = 0;
		while(isRunState){
			this.setName("Thread-" + i++);
			System.out.println("线程Y"+this.getName()+"正在运行");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(this.getName() + "运行结束》》");
	}
	
	public void setRunning(boolean isRunState){
		this.isRunState = isRunState;
	}
	
	public void startThreadY(){
		System.out.println("启动Y>>>>>>>");
		this.start();
	}
	public void stopThreadY(){
		System.out.println("结束线程Y>>>>>>>>");
		this.setRunning(false);
	}
}
