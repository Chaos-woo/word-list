package main.src.common;

import java.io.*;

public class Semaphore {
	private static String audioPath;
	private static boolean searchByInternetFlag = false;
	private static String currentUser;
	private static boolean running = true;
	private static boolean readWordModel = false;

	static {
		try(BufferedReader br = new BufferedReader
				(new InputStreamReader(new FileInputStream("H:/JavaPro/wordlist/src/main/resource/property/audio_path.txt")))) {
			String s = null;
			while ((s=br.readLine())!=null){
				audioPath = s;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setSearchByInternetFlag(boolean searchByInternetFlag) {
		Semaphore.searchByInternetFlag = searchByInternetFlag;
	}

	public static boolean isSearchByInternetFlag() {
		return searchByInternetFlag;
	}

	public static String getAudioPath() {
		return audioPath;
	}

	public static void setAudioPath(String audioPath) {
		Semaphore.audioPath = audioPath;
		try(PrintWriter pw = new PrintWriter
				(new FileOutputStream("H:/JavaPro/wordlist/src/main/resource/property/audio_path.txt"),true)){
			pw.print(audioPath);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public static String getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(String currentUser) {
		Semaphore.currentUser = currentUser;
	}

	public static boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		Semaphore.running = running;
	}

	public static boolean isReadWordModel() {
		return readWordModel;
	}

	public static void setReadWordModel(boolean readWordModel) {
		Semaphore.readWordModel = readWordModel;
	}
}
