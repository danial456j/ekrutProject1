package logic;

import java.util.ArrayList;

import client.ChatClient;
import client.ClientUI;
import clientGUI.logInController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.stage.Window;

public class TimerTwoMinutes implements Runnable {

	private long mTime;
	private long end;
	public Thread thread;

	@Override
	public void run() {
		mTime = System.currentTimeMillis();
		end = mTime + 120000; // 2 minutes

		while (mTime < end) {
			mTime = System.currentTimeMillis();
		}

		Platform.runLater(() -> {
			//ObservableList<Window> windows = Stage.getWindows();
			//windows.get(0).hide();
			ChatClient.primaryStage.close();

			ArrayList<String> list = new ArrayList<>();
			ChatClient.msgRecieved = "";
			list.add("logOut");
			ClientUI.chat.accept(list);
			logInController aFrame = new logInController(); // create EkrutFrame
			try {
				aFrame.start(new Stage());
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
	}
	
	public static void startNewTimer(Thread timer) {
		timer = new Thread(new TimerTwoMinutes());
		timer.start();
	}
	
	public static void stopTimer(Thread timer) {
		timer.stop();
	}
}
