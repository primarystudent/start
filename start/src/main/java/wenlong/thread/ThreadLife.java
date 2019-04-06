package wenlong.thread;

public class ThreadLife {

	public void startY(){
		ThreadY ty = new ThreadY();
		ty.startThreadY();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ty.stopThreadY();
	}
	public void startX(){
		Runnable runnX = new ThreadX();
		Thread threadX = new Thread(runnX);
		threadX.start();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ThreadLife test = new ThreadLife();
		test.startY();
		test.startX();
	}

}
