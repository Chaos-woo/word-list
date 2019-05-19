package main.src.utils.main.internet;

import main.src.common.Semaphore;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadEnglishAudioByShanbay implements Runnable {
	private String word;
	private static String url = "http://dict.youdao.com/dictvoice?audio=";

	public DownloadEnglishAudioByShanbay(String word) {
		this.word = word;
	}

	/**
	 * make up pram("word") and static url to get a new url
	 * and i can get UK audio about this word through new url
	 * finally save this file(.mp3) to be assigned path
	 */
	@Override
	public void run() {
		if(Semaphore.isRunning()){
			try {
				InputStream is = new URL(url + word).openStream();
				StringBuilder fileName = new StringBuilder();
				if(word.contains("%20")){
					String[] strings = word.split("%20");
					for(int i=0;i<strings.length-1;i++){
						fileName.append(strings[i]).append(" ");
					}
					fileName.append(strings[strings.length-1]);
				}else {
					fileName.append(word);
				}
				FileOutputStream fos = new FileOutputStream(Semaphore.getAudioPath() + "/" + fileName + ".mp3");
				byte[] bt = new byte[1024];
				int b;
				while ((b = is.read(bt)) > 0) {
					fos.write(bt, 0, b);
				}
				fos.flush();
				fos.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
