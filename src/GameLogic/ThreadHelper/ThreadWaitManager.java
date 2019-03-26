package GameLogic.ThreadHelper;

public class ThreadWaitManager {

	public void pauseThread(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
