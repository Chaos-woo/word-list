package main.src.utils.main.internet;

import main.src.bean.WordBean;
import main.src.common.Container;
import main.src.common.ExecutePoolServ;
import main.src.common.Semaphore;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QurryChineseByBing implements Runnable {
	private WordBean word;
	private static String url = "http://cn.bing.com/dict/search?q=";
	private static String regexChinese = "class=\"def\"><span>.*?</";
	private static String regexPronunciation = "英\\[.*?，";


	@Override
	public void run() {
		while (Semaphore.isRunning()){
			if(Container.oldWordList.size()<=0){
				Semaphore.setSearchByInternetFlag(false);
			}
			while (Container.wordCatch.size()<20 && Semaphore.isSearchByInternetFlag()){
				word = (WordBean) Container.oldWordList.remove(0);
				if(word.getChinese()!=null && word.getSound()!=null){
					continue;
				}
				word.setSound(word.getEnglish()+".mp3");
				word.setSelectedCount(word.getSelectedCount()+1);
				downloadChinese(word.getEnglish());
				ExecutePoolServ.getExecutorService().execute(
						new DownloadEnglishAudioByShanbay(word.getEnglish()));
			}
		}
	}

	/**
	 * make up a url to get html file
	 * use regex to get word's chinese and pronunciation
	 * maybe this method is slow and take some time
	 */
	private void downloadChinese(String w){
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = new URL(url + w).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String s = null;
			while((s=br.readLine())!=null){
				sb.append(s);
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Pattern pChinese = Pattern.compile(regexChinese);
		Pattern pPronunciation = Pattern.compile(regexPronunciation);
		Matcher mChinese = pChinese.matcher(sb);
		Matcher mPronunciation = pPronunciation.matcher(sb);
		if(mChinese.find()){
			String ch = mChinese.group();
			ch = ch.substring(15);
			int startIndex = ch.indexOf(">")+1;
			int endIndex = ch.indexOf("/")-1;
			String chinese = ch.substring(startIndex,endIndex);
			word.setChinese(chinese);
		}
		if(mPronunciation.find()){
			String pro = mPronunciation.group();
			String pronunciation = pro.substring(0,pro.length()-1);
			word.setPronunciation(pronunciation);
		}
	}
}
