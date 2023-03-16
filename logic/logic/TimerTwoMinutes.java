package logic;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.stage.Stage;

public class TimerTwoMinutes {
	
	private long mTime;
	private long end;
	private Lock lock = new ReentrantLock();
	
	public void startTimer(Stage primaryStage) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				mTime = System.currentTimeMillis();
				end = mTime + 120000; // 2 minutes

				while (mTime < end) 
				{
					lock.lock();
				    mTime = System.currentTimeMillis();
					lock.unlock();
				} 
				Platform.runLater(() -> {
					primaryStage.close();
                });
			}
		});
		thread.start();
	}
	
	public void zeroingTimer() {
		lock.lock();
		mTime = System.currentTimeMillis();
		end = mTime + 120000; // 2 minutes
		lock.unlock();
	}

}
